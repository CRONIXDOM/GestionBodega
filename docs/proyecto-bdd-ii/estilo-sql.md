# Estilo de programación SQL (extraído del PROYECTO BDD II)

Convenciones deducidas de los scripts en `sql/`. No son reglas inventadas:
cada una está tomada del código tal como está escrito. Sirven para que el SQL
nuevo del proyecto se lea igual que el existente.

## Nomenclatura

| Elemento | Convención | Ejemplos |
|---|---|---|
| Tabla | Plural, minúscula, `snake_case` | `productos`, `lotes`, `proveedores` |
| Tabla de detalle | Singular compuesto | `detalle_venta`, `auditoria_movimientos` |
| Clave primaria | `id_<entidad en singular>`, siempre `SERIAL PRIMARY KEY` | `id_producto`, `id_lote`, `id_detalle` |
| Clave foránea | Mismo nombre que la PK referenciada | `productos.id_categoria` → `categorias.id_categoria` |
| Constraint FK | `fk_<tabla>_<referencia>` | `fk_producto_categoria`, `fk_lote_producto` |
| Constraint UNIQUE | `uq_<...>` | `uq_lote_producto` |
| Constraint CHECK | `chk_<...>` | `chk_fechas_lote` |
| Función de consulta | `fn_<qué devuelve>` | `fn_dias_restantes`, `fn_stock_producto` |
| Función de negocio | **Sin prefijo**, verbo en infinitivo | `registrar_lote`, `procesar_venta` |
| Trigger | `trg_<acción>` + función `fn_<misma acción>` | `trg_actualizar_stock` / `fn_actualizar_stock` |
| Vista | `vw_<contenido>` | `vw_stock_actual`, `vw_resumen_ventas` |
| Índice | `idx_<columna>` | `idx_codigo_producto`, `idx_fecha_vencimiento` |
| Parámetro | Prefijo `p_` | `p_id_producto`, `p_cantidad` |
| Variable local | Prefijo `v_` | `v_stock`, `v_fecha`, `v_total` |

Las columnas descriptivas no repiten el nombre de la tabla: es
`productos.nombre`, no `productos.nombre_producto`. Todo en español, y los
identificadores van **sin tildes** aunque los comentarios y los datos sí las
llevan.

## Formato

- Palabras clave en MAYÚSCULAS, identificadores en minúscula, sin comillas
  dobles nunca.
- Indentación de 4 espacios.
- Una columna por línea en la lista `SELECT`, con la coma al final:

  ```sql
  SELECT
      p.codigo,
      p.nombre,
      SUM(dv.cantidad) AS cantidad_vendida
  FROM detalle_venta dv
  INNER JOIN productos p ON dv.id_producto=p.id_producto
  GROUP BY p.codigo, p.nombre
  ORDER BY p.nombre;
  ```

- **Sin espacios alrededor de los operadores** en `ON` y `WHERE`
  (`ON dv.id_producto=p.id_producto`, `WHERE l.fecha_vencimiento<=CURRENT_DATE+30`),
  pero sí en el resto de la sentencia.
- Alias de tabla de una o dos letras, tomadas del nombre: `p` productos,
  `l` lotes, `v` ventas, `dv` detalle_venta, `pr` proveedores, `c` categorias.
- `AS` explícito solo para alias de columna; nunca para alias de tabla.
- `ORDER BY` al cierre de toda consulta de reporte.

En `CREATE TABLE`: una columna por línea, el `CHECK` en la línea siguiente con
8 espacios, y las constraints al final separadas por líneas en blanco:

```sql
CREATE TABLE lotes (
    id_lote SERIAL PRIMARY KEY,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL
        CHECK(cantidad >= 0),

    CONSTRAINT fk_lote_producto
        FOREIGN KEY (id_producto)
        REFERENCES productos(id_producto)
);
```

En funciones, el cierre va compacto en una sola línea —
`END; $$ LANGUAGE plpgsql;` — y el `SELECT ... INTO` se parte en tres:

```sql
CREATE OR REPLACE FUNCTION fn_stock_producto(p_id_producto INT)
RETURNS INT
AS $$
DECLARE
    v_stock INT;
BEGIN
    SELECT stock_actual
    INTO v_stock
    FROM productos WHERE id_producto = p_id_producto;
    IF v_stock IS NULL THEN
        RETURN 0;
    END IF;
    RETURN v_stock;
END; $$ LANGUAGE plpgsql;
```

## Comentarios

Dos formas, y cada una significa algo distinto:

- `--TEXTO EN MAYÚSCULAS` sin espacio: encabezado de sección o título del
  objeto que viene abajo. `--FUNCIONES`, `--CALCULAR DÍAS RESTANTES PARA EL
  VENCIMIENTO`, `--CREACIÓN DEL ÍNDICE`.
- `-- Texto normal` con espacio: nota puntual sobre la línea siguiente.
  `-- Correcto`, `-- Debe generar ERROR`, `-- Buscar un producto por código`.

No hay comentarios explicando *qué hace* una línea de SQL: el comentario
siempre dice *para qué sirve* el bloque.

## Lógica

**Validar primero, trabajar después.** Toda función empieza con guardas que
salen temprano, en orden: existe → dato válido → hay saldo. Solo después se
toca la tabla.

```sql
IF v_stock IS NULL THEN
    RETURN 'ERROR: Producto inexistente';
END IF;
IF p_cantidad <= 0 THEN
    RETURN 'ERROR: Cantidad inválida';
END IF;
IF v_stock < p_cantidad THEN
    RETURN 'ERROR: Stock insuficiente';
END IF;
```

**El resultado se devuelve como texto con prefijo**: `'OK: ...'` cuando sale
bien, `'ERROR: ...'` cuando no. Nunca códigos numéricos ni booleanos.

**Las excepciones van de lo específico a lo general**: los casos conocidos
primero, `WHEN OTHERS THEN RETURN 'ERROR: ' || SQLERRM` como último recurso.

**En los triggers**: la función siempre termina en `RETURN NEW`, y para
rechazar se usa `RAISE EXCEPTION` con el mensaje en formato
`'ERROR: <qué pasó>'`. La función se escribe completa antes del
`CREATE TRIGGER`.

## Organización de los archivos

Un archivo por tipo de objeto (`FUNCIONES`, `TRIGGERS`, `VISTAS`, `INDICES`,
`JOINS`), con el nombre en mayúsculas. Dentro de cada archivo el patrón se
repite sin excepción:

1. Comentario de sección con el título.
2. El objeto (`CREATE OR REPLACE ...`).
3. **Sus pruebas, inmediatamente debajo.**
4. Al final del archivo, una sección `--PRUEBAS` o `--CONSULTAS FINALES` que
   vuelve a ejercitar todo junto.

Las pruebas van en tríos que cubren rangos distintos —
`fn_contar_lotes(1)`, `fn_contar_lotes(15)`, `fn_contar_lotes(50)` — y en las
funciones de negocio cubren un caso por cada guarda: correcto, producto
inexistente, cantidad negativa, stock insuficiente. En los triggers se
escribe el caso que funciona y el que debe fallar, cada uno rotulado.

Para los índices el patrón es **medir → crear → volver a medir**:
`EXPLAIN ANALYZE` de la consulta, `CREATE INDEX`, el mismo `EXPLAIN ANALYZE`,
y una consulta a `pg_indexes` con `pg_size_pretty` para ver el costo en disco.

Los datos de prueba se agrupan en bloques de 5 filas separados por líneas en
blanco, y las fechas siempre son relativas (`CURRENT_DATE-40`,
`CURRENT_DATE+180`) para que el juego de datos no caduque.

## Al escribir SQL nuevo para GestionBodega

Hay un choque de convenciones que conviene resolver antes, no después: el
esquema real de GestionBodega lo genera Hibernate y **no** sigue las reglas de
arriba. Sus tablas son singulares (`producto`, `lote`, `registro`), las
columnas repiten la entidad (`nombre_producto`, `cantidad_lote`) y las FK se
llaman `producto_idproducto` — el `@JoinColumn(name = "PRODUCTO_idPRODUCTO")`
va sin comillas, así que PostgreSQL lo pliega a minúsculas.

La regla práctica: **los nombres los manda el esquema existente, el formato lo
manda esta guía.** Es decir, se escribe contra `lote` y `cantidad_lote`, pero
con el mismo sangrado, las mismas mayúsculas, los mismos prefijos `fn_`/`vw_`/
`idx_`/`p_`/`v_`, las mismas guardas tempranas y el mismo bloque de pruebas
debajo de cada objeto.
