# Gaseosas Andinas S.A.

Sistema de control de produccion: recetas, materias primas, ordenes, lotes,
control de calidad y almacen de productos terminados.

Son dos proyectos separados:

| Proyecto     | Que es                            | Puerto |
|--------------|-----------------------------------|--------|
| `andianaApi` | La API con toda la logica          | 8090   |
| `andinaweb`  | Las pantallas para el usuario      | 8091   |

La web **no toca la base de datos**: todo lo que necesita se lo pide a la API.

---

## Como se levanta

```bash
# 1. la base de datos (solo la primera vez)
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
esquema que ya existe. El script `esquema.sql` esta aparte justamente para eso,
por si hay que crear la base desde cero.

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
│   ├── entidad/         @Entity: el reflejo de las tablas
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
  indirecta al editar un ingreso anterior.
- **Al almacen solo entran los lotes APROBADOS**, y nunca mas unidades de las
  que el lote produjo.
- **Un lote que ya esta en el almacen no puede pasar a OBSERVADO o RECHAZADO**
  sin sacarlo antes: si no, quedaria mercaderia no apta guardada como buena.
- **Un producto no puede tener dos recetas con la misma version**, ni una receta
  la misma materia prima dos veces.
- Nada se borra si algo depende de ello: se avisa y se explica que hacer.

---

## Las pantallas de `andinaweb`

**Gestion (CRUD):** Productos · Materias primas · Recetas · Materias de la
receta · Movimientos de inventario · Ordenes de produccion · Lotes de
produccion · Control de calidad · Almacen de terminados.

**Consultas que pidio la gerencia:**

1. **Materias de una receta** (`/consulta/materias-de-receta`): se elige la
   receta y salen sus materias primas con la cantidad y la unidad.
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

24 pruebas, sin base de datos ni Spring:

- `ServicioMovimientoInventarioTest` — el stock y el historial (9)
- `ServicioAlmacenTest` — solo entran los lotes aprobados (7)
- `ServicioConsultaRecetasTest` — las dos consultas (5)
- `IndependenciaDeFrameworksTest` — la arquitectura se respeta (3)
