# PROYECTO BDD II — Análisis de los scripts

Notas de lectura de los 8 scripts SQL entregados (copiados sin cambios en
`sql/`). Es un sistema de inventario de farmacia sobre PostgreSQL: tablas,
carga de datos, funciones, triggers, vistas, índices y consultas con JOIN.

Este documento resume qué hace cada pieza, qué problemas tiene y qué de todo
eso aplica al proyecto GestionBodega.

## 1. Modelo de datos (`sql/creacion-tablas.sql`)

Siete tablas:

| Tabla | Contenido | Reglas propias |
|---|---|---|
| `categorias` | Catálogo de familias de producto | `nombre` UNIQUE |
| `proveedores` | Proveedor con contacto y teléfono | — |
| `productos` | Producto con precio y **stock consolidado** | `codigo` UNIQUE, `precio_venta >= 0`, `stock_actual >= 0`, FK a categoría y proveedor |
| `lotes` | Lote físico de un producto | UNIQUE `(id_producto, numero_lote)`, `cantidad >= 0`, `precio_compra >= 0`, `fecha_vencimiento > fecha_ingreso` |
| `ventas` | Cabecera de venta | `total >= 0`, `fecha` con default |
| `detalle_venta` | Línea de venta: producto + lote + cantidad | `cantidad > 0`, FK a venta con `ON DELETE CASCADE` |
| `auditoria_movimientos` | Bitácora de cambios | `operacion IN ('INSERT','UPDATE','DELETE')` |

Decisión de diseño central: **el stock vive en dos lugares a la vez** —
`productos.stock_actual` (consolidado) y `lotes.cantidad` (por lote). Esa
redundancia es la fuente de casi todos los problemas de la sección 6.

Relaciones:

```
categorias ─┐
            ├─< productos ─< lotes ─┐
proveedores ┘        │              │
                     └──< detalle_venta >── ventas
```

## 2. Datos de prueba (`sql/insert-values-tablas.sql`)

10 categorías, 10 proveedores, 50 productos (5 por categoría, códigos tipo
`MED001`, `ANT001`…), 50 lotes, 60 líneas de detalle y 20 ventas. Las fechas
son relativas (`CURRENT_DATE-40`, `CURRENT_DATE+180`), así que el juego de
datos no caduca con el tiempo — buena práctica que vale la pena copiar.

## 3. Funciones

`sql/funciones.sql` — consulta, sin efectos secundarios:

- `fn_dias_restantes(id_lote)` → días hasta el vencimiento; `NULL` si el lote
  no existe. En PostgreSQL restar dos `DATE` ya devuelve `integer`.
- `fn_productos_criticos()` → tabla de lotes que vencen dentro de 30 días
  (`RETURNS TABLE` + `RETURN QUERY`).
- `fn_contar_lotes(id_producto)` → número de lotes del producto.
- `fn_stock_producto(id_producto)` → stock, con `0` como valor por defecto.

`sql/funcion-venta-y-lote.sql` — transaccionales, con manejo de errores:

- `registrar_lote(...)` → valida producto y cantidad, inserta el lote, suma al
  stock del producto y devuelve `'OK: ...'` o `'ERROR: ...'`. Usa un bloque
  `BEGIN … EXCEPTION WHEN unique_violation … WHEN OTHERS` para traducir el
  fallo a texto en lugar de propagar la excepción.
- `procesar_venta(id_producto, cantidad)` → valida existencia, cantidad y
  stock suficiente antes de descontar.

El patrón que se enseña aquí es "validar primero, devolver un texto de
resultado": mensajes de negocio en vez de errores crudos del motor.

## 4. Triggers (`sql/triggers.sql`)

| Trigger | Momento | Función | Efecto |
|---|---|---|---|
| `trg_validar_fecha_vencimiento` | BEFORE INSERT en `lotes` | `fn_validar_fecha_vencimiento()` | `RAISE EXCEPTION` si el lote ya está vencido |
| `trg_actualizar_stock` | AFTER INSERT en `detalle_venta` | `fn_actualizar_stock()` | Resta la cantidad vendida de `productos.stock_actual` |
| `trg_auditoria_productos` | AFTER UPDATE en `productos` | `fn_auditoria_inventario()` | Escribe en `auditoria_movimientos` usando `TG_TABLE_NAME`, `TG_OP` y `CURRENT_USER` |

El `CHECK` de tabla y el trigger son complementarios, no redundantes: el
`CHECK` compara vencimiento contra ingreso (coherencia interna de la fila) y
el trigger contra `CURRENT_DATE` (regla de negocio del momento de la carga).

## 5. Vistas, índices y JOINs

Vistas (`sql/vistas.sql`): `vw_stock_actual`, `vw_productos_proximos_vencer`
(ventana de 30 días) y `vw_resumen_ventas` (agregado por producto).

Índices (`sql/indices.sql`): `idx_codigo_producto` sobre `productos(codigo)` e
`idx_fecha_vencimiento` sobre `lotes(fecha_vencimiento)`, cada uno con
`EXPLAIN ANALYZE` antes y después y una consulta a `pg_indexes` para ver el
tamaño. La metodología (medir → crear → volver a medir) es lo valioso.

JOINs (`sql/joins.sql`): ventas por producto, por proveedor y por categoría;
productos con sus lotes; ventas completas. El caso interesante es
**productos sin ventas**, con `LEFT JOIN … WHERE dv.id_producto IS NULL`
(anti-join), y el de ventas por categoría, que parte de `categorias` con
`LEFT JOIN` + `COALESCE(SUM(...),0)` para no perder las categorías vacías.

## 6. Problemas detectados

Ordenados por gravedad. Los cuatro primeros impiden que los scripts corran o
dejan los datos inconsistentes.

1. **Orden de carga inválido** (`insert-values-tablas.sql`): `detalle_venta`
   se inserta en la línea 155 y `ventas` en la 239. Como `fk_detalle_venta`
   apunta a `ventas(id_venta)`, la carga falla con violación de clave foránea.
   El bloque de `ventas` debe ir antes que el de `detalle_venta`.

2. **La auditoría no compila** (`triggers.sql`): `fn_auditoria_inventario()`
   inserta en la columna `detalle`, pero la tabla define `detalle_cambio`.
   Cada `UPDATE` sobre `productos` aborta con «column "detalle" does not
   exist» — y como el trigger es AFTER UPDATE, tumba también la actualización
   original.

   ```sql
   INSERT INTO auditoria_movimientos(tabla_afectada, operacion,
       usuario_bd, fecha, detalle_cambio)
   ```

3. **Doble descuento de stock**: `trg_actualizar_stock` resta al insertar en
   `detalle_venta` y `procesar_venta()` vuelve a restar por su cuenta. Si se
   registra una venta con las dos vías, el stock baja el doble. Hay que elegir
   una: o el trigger es el dueño del stock, o lo es la función (y entonces
   la función debería insertar el detalle y dejar que el trigger descuente).

4. **`lotes.cantidad` nunca se descuenta**: la venta guarda de qué lote salió
   la mercadería, pero solo toca `productos.stock_actual`. A partir de la
   primera venta, `SUM(lotes.cantidad)` deja de coincidir con el stock del
   producto. Faltaría, dentro de `fn_actualizar_stock()`:

   ```sql
   UPDATE lotes SET cantidad = cantidad - NEW.cantidad
   WHERE id_lote = NEW.id_lote;
   ```

5. **El lote no se valida contra el producto**: nada impide una fila de
   `detalle_venta` cuyo `id_lote` pertenezca a otro `id_producto`. Se corrige
   con un trigger de validación o con una FK compuesta hacia
   `lotes(id_lote, id_producto)` (requiere un UNIQUE sobre ese par).

6. **`ventas.total` siempre queda en 0**: los datos de prueba lo insertan en 0
   y ningún trigger lo recalcula desde `detalle_venta`. Sería un trigger
   AFTER INSERT/UPDATE/DELETE sobre el detalle que haga
   `SUM(cantidad * precio_unitario)`.

7. **Stock insuficiente da un error feo**: `fn_actualizar_stock()` no valida
   nada; cuando la cantidad supera el stock, quien salta es el
   `CHECK(stock_actual >= 0)` con un mensaje del motor, no una regla de
   negocio. Conviene un `RAISE EXCEPTION 'Stock insuficiente para el producto %'`.

8. **Criterio de "por vencer" inconsistente**: `fn_productos_criticos()` usa
   `<= CURRENT_DATE + 30`, que **incluye los ya vencidos**;
   `vw_productos_proximos_vencer` usa `BETWEEN CURRENT_DATE AND CURRENT_DATE + 30`,
   que los excluye. Las dos consultas responden distinto a la misma pregunta.

9. **`idx_codigo_producto` es redundante**: `productos.codigo` ya es UNIQUE y
   la restricción crea su propio índice B-tree. Sirve para la demostración de
   `EXPLAIN ANALYZE`, pero en un esquema real duplica escrituras sin ganancia.

10. **`registrar_lote()` se traga los errores**: `WHEN OTHERS THEN RETURN
    'ERROR: ' || SQLERRM` convierte cualquier fallo en un texto. El cliente
    recibe una cadena, no una excepción, así que un `INSERT` fallido puede
    pasar por exitoso si nadie lee el retorno.

11. **`triggers.sql` no es idempotente**: incluye un `INSERT` pensado para
    fallar (lote `L101` con fecha vencida) y usa `CREATE TRIGGER` sin
    `DROP … IF EXISTS`. Ejecutar el archivo completo dos veces, o de una sola
    vez dentro de una transacción, aborta el resto del script.

## 7. Qué aplica a GestionBodega

GestionBodega (`control` + `controlweb`, Spring Boot sobre PostgreSQL) resuelve
un dominio parecido pero con un modelo distinto:

| PROYECTO BDD II | GestionBodega | Diferencia |
|---|---|---|
| `productos.stock_actual` | `producto` sin stock consolidado | GestionBodega deriva el stock de los lotes; evita el problema 4 |
| `lotes.cantidad` | `lote.cantidad_lote` + `cantidad_reservada` | Ya distingue existencia de compromiso |
| `ventas` / `detalle_venta` | `solicitud`/`detalle_solicitud` y `entrega`/`detalle_entrega` | La salida es interna (entrega a sede), no una venta |
| `auditoria_movimientos` por trigger | `registro` escrito por la aplicación | Bitácora en la capa de negocio, no en la base |
| — | `sede`, `zona`, `ubicacion` | GestionBodega ubica físicamente el lote |

Lo que sí conviene llevarse:

- **Índice sobre `lote.fecha_vencimiento`.** Es exactamente el caso del
  script: la consulta de próximos a vencer filtra por rango de fecha y hoy
  hace recorrido secuencial.
- **La ventana de vencimiento definida en un solo lugar.** El problema 8 es
  fácil de repetir: hoy el criterio de "por vencer" está en el código de
  servicio; si aparece un segundo cálculo hay que compartir la constante.
- **El anti-join** de `joins.sql` para reportes del tipo "productos sin
  movimiento" o "lotes nunca entregados".
- **Datos de prueba con fechas relativas** a `CURRENT_DATE`.
- **Validar coherencia lote↔producto** (problema 5): en GestionBodega el
  riesgo equivalente es que un `detalle_entrega` referencie un lote que no
  corresponde al producto solicitado.

Advertencia de integración: `control` corre con
`spring.jpa.hibernate.ddl-auto=update`, que gestiona tablas y columnas pero
**no** vistas, triggers ni funciones. Si se adopta cualquier objeto de estos
scripts hay que versionarlo en archivos SQL aparte (o con Flyway/Liquibase);
Hibernate no lo va a recrear ni lo va a borrar.
