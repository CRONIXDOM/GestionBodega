# Gaseosas Andinas S.A.

Sistema de control de produccion: recetas, materias primas, ordenes, lotes,
control de calidad e inventario de productos terminados.

Son dos proyectos separados:

| Proyecto     | Que es                        | Puerto |
|--------------|-------------------------------|--------|
| `andianaApi` | La API con toda la logica     | 8090   |
| `andinaweb`  | Las pantallas para el usuario | 8091   |

La web **no toca la base de datos**: todo lo que necesita se lo pide a la API.

---

## Como se levanta

```bash
# 1. la base de datos (solo si hay que crearla desde cero)
psql -h localhost -p 5433 -U postgres -d Andiana -f basedatos/esquema.sql
psql -h localhost -p 5433 -U postgres -d Andiana -f basedatos/datos-ejemplo.sql   # opcional

# 2. la API
cd andianaApi && ./mvnw spring-boot:run

# 3. la web, en otra terminal
cd andinaweb && ./mvnw spring-boot:run
```

Despues se entra a **http://localhost:8091**.

La conexion esta en `andianaApi/src/main/resources/application.properties`:
base `Andiana`, puerto `5433`.

### La base de datos no se modifica

La API arranca con `spring.jpa.hibernate.ddl-auto=none`. Hibernate no crea, no
altera y no borra ninguna tabla: se limita a leer y escribir filas sobre el
esquema que ya existe.

El codigo se adapto al esquema tal cual esta, no al reves:

| Tabla                      | Clave           | Detalles que condicionan la aplicacion       |
|----------------------------|-----------------|----------------------------------------------|
| `producto`                 | `id_producto`   | `UNIQUE(nombre, presentacion)`                |
| `materia_prima`            | `id_materia`    | `CHECK(stock_actual >= 0)`                    |
| `receta_produccion`        | `id_receta`     | `version` es INTEGER, `UNIQUE(producto,version)` |
| `detalle_receta`           | `id_detalle`    | borra en cascada al eliminar la receta        |
| `movimiento_materia_prima` | `id_movimiento` | `fecha` TIMESTAMP, `CHECK(cantidad > 0)`      |
| `orden_produccion`         | `id_orden`      | estados `PLANIFICADA / EN_PROCESO / FINALIZADA / CANCELADA` |
| `lote_produccion`          | `id_lote`       | estados `EN_PROCESO / FINALIZADO / RECHAZADO` |
| `control_calidad`          | `id_control`    | varios controles por lote: manda el mas reciente |
| `inventario_producto`      | `id_inventario` | varias filas por lote: un lote se reparte en ubicaciones |

Las listas desplegables de la web usan exactamente los valores de los `CHECK`,
para que la pantalla no ofrezca nada que la base vaya a rechazar despues.

---

## Arquitectura de `andianaApi`

Cuatro capas, y las flechas van siempre hacia adentro: cada capa conoce a la de
adentro, nunca al reves.

```
presentacion   ->  controladores REST. Solo traducen HTTP.
     |
aplicacion     ->  los casos de uso: aqui viven TODAS las reglas del negocio.
     |             Java puro, sin una sola anotacion.
dominio        ->  el modelo (Producto, Receta, Lote...) y los puertos, que son
     ^             las interfaces de repositorio que el negocio necesita.
     |
infraestructura -> JPA, PostgreSQL y el armado de los beans. Implementa los
                   puertos del dominio.
```

```
com.andiana.api
├── dominio
│   ├── modelo/          POJOs del negocio, sin JPA ni Spring
│   ├── puerto/          interfaces de repositorio (lo que el negocio necesita)
│   ├── Validar.java     las reglas de datos, en Java puro
│   └── ReglaNegocioException.java
├── aplicacion
│   ├── puerto/          lo que la presentacion puede pedir
│   └── servicio/        los casos de uso, SIN anotaciones
├── infraestructura
│   ├── entidad/         @Entity: el reflejo exacto de las tablas
│   ├── jpa/             repositorios de Spring Data
│   ├── mapeador/        traduce tabla <-> modelo del dominio
│   ├── adaptador/       implementa los puertos usando JPA
│   └── configuracion/   el UNICO sitio donde Spring toca los casos de uso
└── presentacion
    └── controlador/     @RestController
```

### Por que "independiente de frameworks"

Los servicios de `aplicacion` no llevan `@Service` ni ninguna otra anotacion:
se construyen a mano en `ConfiguracionCasosUso`, pasandoles los repositorios
por el constructor. Eso da dos cosas concretas:

1. Se pueden probar sin levantar Spring ni PostgreSQL, con un repositorio en un
   `HashMap` (ver `src/test/.../RepositorioEnMemoria.java`).
2. Cambiar de base de datos, o incluso de framework, es reescribir solo
   `infraestructura`. El negocio no se entera.

Y no es una promesa: `IndependenciaDeFrameworksTest` lee el codigo fuente de
`dominio` y `aplicacion` y **falla** si aparece un import de Spring, de JPA o de
Hibernate.

### Reglas del negocio que hacen el trabajo

- **El stock nunca se escribe a mano.** Sale siempre de reproducir el historial
  de movimientos de la materia prima, de principio a fin. Se hace asi porque un
  AJUSTE fija el stock en un valor absoluto: si despues se corrige o se borra un
  movimiento *anterior* a ese ajuste, lo unico que da el resultado correcto es
  volver a pasar la pelicula entera.
- **Un consumo no puede dejar el stock en negativo**, ni siquiera de forma
  indirecta al editar un ingreso anterior. La tabla ademas lo prohibe con un
  `CHECK`, asi que el caso de uso lo corta antes de llegar ahi.
- **Al inventario solo entran los lotes APROBADOS.** Vale el control de calidad
  mas reciente, porque el laboratorio puede volver a inspeccionar un lote
  despues de una observacion.
- **Un lote se puede repartir en varias ubicaciones**, pero entre todas no se
  puede guardar mas de lo que ese lote produjo.
- **Un lote que ya esta en el inventario no puede pasar a OBSERVADO o
  RECHAZADO** sin sacarlo antes: si no, quedaria mercaderia no apta guardada
  como buena.
- **Un producto no puede tener dos recetas con la misma version**, ni una receta
  la misma materia prima dos veces.
- La unidad de cada linea de receta la pone la materia prima, para que no se
  pueda pedir "2 LITROS" de algo que se mide en gramos.
- Nada se borra si algo depende de ello: se avisa y se explica que hacer.

---

## Las pantallas de `andinaweb`

**Gestion (CRUD):** Productos · Materias primas · Recetas · Detalle de recetas ·
Movimientos de materia prima · Ordenes de produccion · Lotes de produccion ·
Control de calidad · Inventario de terminados.

**Consultas que pidio la gerencia:**

1. **Materias de una receta** (`/consulta/materias-de-receta`): se elige la
   receta y salen sus materias primas con la cantidad, la unidad y el stock que
   hay hoy de cada una.
2. **Materias por receta** (`/consulta/conteo-materias`): cuantas materias
   primas lleva cada receta, marcando cual es la formula vigente y cuales son
   historicas.

### Un solo controlador para las nueve pantallas

Las nueve secciones se describen en `catalogo/Catalogo.java`: que campos tiene
cada una, de que tipo son y cual apunta a otra seccion. Con eso, un unico
`GestionControlador` y dos plantillas (`lista.html` y `formulario.html`) sirven
para todas.

Agregar una entidad nueva es agregar una entrada a esa lista. No hay que
escribir otro controlador ni otras dos plantillas.

---

## Pruebas

```bash
cd andianaApi && ./mvnw test
```

27 pruebas, sin base de datos ni Spring:

- `ServicioMovimientoMateriaPrimaTest` — el stock y el historial (10)
- `ServicioInventarioTest` — solo entran los lotes aprobados (9)
- `ServicioConsultaRecetasTest` — las dos consultas (5)
- `IndependenciaDeFrameworksTest` — la arquitectura se respeta (3)
