-- ===================================================================
--  Gaseosas Andinas S.A.  -  base de datos "Andiana"
--
--  La aplicacion NO modifica la base de datos: arranca con
--  spring.jpa.hibernate.ddl-auto=none. Este script existe solo para
--  crear el esquema la primera vez.
--
--  Ejecutar:  psql -h localhost -p 5433 -U postgres -d Andiana -f esquema.sql
-- ===================================================================

-- ---------- Lo que se fabrica ----------
CREATE TABLE IF NOT EXISTS producto (
    id_producto   SERIAL PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    presentacion  VARCHAR(20)  NOT NULL,   -- 350 ML, 500 ML, 1 LITRO, 2 LITROS
    CONSTRAINT uk_producto_nombre_presentacion UNIQUE (nombre, presentacion)
);

-- ---------- Con que se fabrica ----------
CREATE TABLE IF NOT EXISTS materia_prima (
    id_materia_prima SERIAL PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL UNIQUE,
    unidad_medida    VARCHAR(20)  NOT NULL,   -- LITRO, KILOGRAMO, GRAMO...
    stock            NUMERIC(12,3) NOT NULL DEFAULT 0
);

-- ---------- La formula ----------
-- Un producto puede tener varias recetas porque la formula cambia con el
-- tiempo; por eso cada una lleva version y fecha.
CREATE TABLE IF NOT EXISTS receta (
    id_receta   SERIAL PRIMARY KEY,
    id_producto INTEGER NOT NULL REFERENCES producto (id_producto),
    version     VARCHAR(20) NOT NULL,
    fecha       DATE        NOT NULL,
    activa      BOOLEAN     NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_receta_producto_version UNIQUE (id_producto, version)
);

-- Cuanta materia prima lleva la receta. Una misma materia prima no puede
-- aparecer dos veces en la misma receta.
CREATE TABLE IF NOT EXISTS receta_detalle (
    id_receta_detalle SERIAL PRIMARY KEY,
    id_receta         INTEGER NOT NULL REFERENCES receta (id_receta),
    id_materia_prima  INTEGER NOT NULL REFERENCES materia_prima (id_materia_prima),
    cantidad          NUMERIC(12,3) NOT NULL,
    CONSTRAINT uk_receta_materia UNIQUE (id_receta, id_materia_prima)
);

-- ---------- Como se mueve el inventario de materia prima ----------
-- El stock de materia_prima nunca se edita a mano: es el resultado de
-- estos movimientos.
CREATE TABLE IF NOT EXISTS movimiento_inventario (
    id_movimiento    SERIAL PRIMARY KEY,
    id_materia_prima INTEGER NOT NULL REFERENCES materia_prima (id_materia_prima),
    tipo             VARCHAR(10) NOT NULL,   -- INGRESO, CONSUMO, AJUSTE
    cantidad         NUMERIC(12,3) NOT NULL,
    fecha            DATE        NOT NULL,
    observacion      VARCHAR(200)
);

-- ---------- Que se manda a producir ----------
CREATE TABLE IF NOT EXISTS orden_produccion (
    id_orden            SERIAL PRIMARY KEY,
    codigo              VARCHAR(30) NOT NULL UNIQUE,
    id_producto         INTEGER     NOT NULL REFERENCES producto (id_producto),
    cantidad_programada INTEGER     NOT NULL,
    fecha_produccion    DATE        NOT NULL,
    estado              VARCHAR(15) NOT NULL   -- PLANIFICADA, EN PROCESO, FINALIZADA
);

-- Una orden puede salir en varios lotes segun la capacidad de las lineas.
CREATE TABLE IF NOT EXISTS lote_produccion (
    id_lote            SERIAL PRIMARY KEY,
    codigo_lote        VARCHAR(30) NOT NULL UNIQUE,
    id_orden           INTEGER     NOT NULL REFERENCES orden_produccion (id_orden),
    cantidad_producida INTEGER     NOT NULL,
    fecha_fabricacion  DATE        NOT NULL
);

-- ---------- Que dice el laboratorio ----------
CREATE TABLE IF NOT EXISTS control_calidad (
    id_control       SERIAL PRIMARY KEY,
    id_lote          INTEGER NOT NULL UNIQUE REFERENCES lote_produccion (id_lote),
    ph               NUMERIC(4,2)  NOT NULL,
    grados_brix      NUMERIC(5,2)  NOT NULL,
    temperatura      NUMERIC(5,2)  NOT NULL,
    resultado        VARCHAR(12)   NOT NULL,  -- APROBADO, OBSERVADO, RECHAZADO
    fecha_inspeccion DATE          NOT NULL
);

-- ---------- Donde queda lo aprobado ----------
-- Solo entran aqui los lotes con control de calidad APROBADO.
CREATE TABLE IF NOT EXISTS almacen_producto_terminado (
    id_almacen       SERIAL PRIMARY KEY,
    id_lote          INTEGER NOT NULL UNIQUE REFERENCES lote_produccion (id_lote),
    cantidad         INTEGER      NOT NULL,
    ubicacion_fisica VARCHAR(50)  NOT NULL,
    fecha_ingreso    DATE         NOT NULL
);
