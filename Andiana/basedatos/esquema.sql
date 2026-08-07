-- ==========================================
-- BASE DE DATOS PRODUCCIÓN DE BEBIDAS
-- PostgreSQL
--
-- Este es el esquema tal cual existe. La aplicacion NO lo modifica:
-- andianaApi arranca con spring.jpa.hibernate.ddl-auto=none, asi que
-- Hibernate solo lee y escribe filas, nunca crea ni altera tablas.
-- ==========================================

DROP TABLE IF EXISTS inventario_producto CASCADE;
DROP TABLE IF EXISTS control_calidad CASCADE;
DROP TABLE IF EXISTS lote_produccion CASCADE;
DROP TABLE IF EXISTS orden_produccion CASCADE;
DROP TABLE IF EXISTS movimiento_materia_prima CASCADE;
DROP TABLE IF EXISTS detalle_receta CASCADE;
DROP TABLE IF EXISTS receta_produccion CASCADE;
DROP TABLE IF EXISTS materia_prima CASCADE;
DROP TABLE IF EXISTS producto CASCADE;

-- ==========================================
-- PRODUCTOS
-- ==========================================

CREATE TABLE producto(
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    presentacion VARCHAR(50) NOT NULL,
    volumen_ml INTEGER NOT NULL,
    estado BOOLEAN DEFAULT TRUE,

    CONSTRAINT uk_producto UNIQUE(nombre,presentacion)
);

-- ==========================================
-- MATERIAS PRIMAS
-- ==========================================

CREATE TABLE materia_prima(
    id_materia SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    unidad_medida VARCHAR(20) NOT NULL,
    stock_actual NUMERIC(12,2) DEFAULT 0,
    stock_minimo NUMERIC(12,2) DEFAULT 0,

    CHECK(stock_actual>=0),
    CHECK(stock_minimo>=0)
);

-- ==========================================
-- RECETAS
-- ==========================================

CREATE TABLE receta_produccion(
    id_receta SERIAL PRIMARY KEY,
    id_producto INTEGER NOT NULL,
    version INTEGER NOT NULL,
    fecha_vigencia DATE NOT NULL,
    estado BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_receta_producto
        FOREIGN KEY(id_producto)
        REFERENCES producto(id_producto),

    CONSTRAINT uk_receta UNIQUE(id_producto,version)
);

-- ==========================================
-- DETALLE RECETA
-- ==========================================

CREATE TABLE detalle_receta(
    id_detalle SERIAL PRIMARY KEY,
    id_receta INTEGER NOT NULL,
    id_materia INTEGER NOT NULL,
    cantidad NUMERIC(12,3) NOT NULL,
    unidad VARCHAR(20) NOT NULL,

    CONSTRAINT fk_detalle_receta
        FOREIGN KEY(id_receta)
        REFERENCES receta_produccion(id_receta)
        ON DELETE CASCADE,

    CONSTRAINT fk_detalle_materia
        FOREIGN KEY(id_materia)
        REFERENCES materia_prima(id_materia),

    CHECK(cantidad>0)
);

-- ==========================================
-- MOVIMIENTOS MATERIA PRIMA
-- ==========================================

CREATE TABLE movimiento_materia_prima(

    id_movimiento SERIAL PRIMARY KEY,
    id_materia INTEGER NOT NULL,

    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    tipo VARCHAR(20) NOT NULL,

    cantidad NUMERIC(12,2) NOT NULL,

    observacion TEXT,

    CONSTRAINT fk_movimiento_materia
        FOREIGN KEY(id_materia)
        REFERENCES materia_prima(id_materia),

    CHECK(tipo IN ('INGRESO','CONSUMO','AJUSTE')),

    CHECK(cantidad>0)

);

-- ==========================================
-- ORDENES DE PRODUCCIÓN
-- ==========================================

CREATE TABLE orden_produccion(

    id_orden SERIAL PRIMARY KEY,

    id_producto INTEGER NOT NULL,

    fecha_programada DATE NOT NULL,

    cantidad_programada NUMERIC(12,2) NOT NULL,

    estado VARCHAR(20) DEFAULT 'PLANIFICADA',

    responsable VARCHAR(100),

    CONSTRAINT fk_orden_producto
        FOREIGN KEY(id_producto)
        REFERENCES producto(id_producto),

    CHECK(estado IN
    (
        'PLANIFICADA',
        'EN_PROCESO',
        'FINALIZADA',
        'CANCELADA'
    )),

    CHECK(cantidad_programada>0)

);

-- ==========================================
-- LOTES
-- ==========================================

CREATE TABLE lote_produccion(

    id_lote SERIAL PRIMARY KEY,

    id_orden INTEGER NOT NULL,

    numero_lote VARCHAR(40) UNIQUE NOT NULL,

    fecha_inicio TIMESTAMP,

    fecha_fin TIMESTAMP,

    cantidad_producida NUMERIC(12,2),

    estado VARCHAR(20) DEFAULT 'EN_PROCESO',

    CONSTRAINT fk_lote_orden
        FOREIGN KEY(id_orden)
        REFERENCES orden_produccion(id_orden),

    CHECK(estado IN
    (
        'EN_PROCESO',
        'FINALIZADO',
        'RECHAZADO'
    ))

);

-- ==========================================
-- CONTROL DE CALIDAD
-- ==========================================

CREATE TABLE control_calidad(

    id_control SERIAL PRIMARY KEY,

    id_lote INTEGER NOT NULL,

    fecha_control TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    ph NUMERIC(4,2),

    brix NUMERIC(5,2),

    temperatura NUMERIC(5,2),

    resultado VARCHAR(20),

    observaciones TEXT,

    CONSTRAINT fk_control_lote
        FOREIGN KEY(id_lote)
        REFERENCES lote_produccion(id_lote)
        ON DELETE CASCADE,

    CHECK(resultado IN
    (
        'APROBADO',
        'RECHAZADO',
        'OBSERVADO'
    ))

);

-- ==========================================
-- INVENTARIO PRODUCTO TERMINADO
-- ==========================================

CREATE TABLE inventario_producto(

    id_inventario SERIAL PRIMARY KEY,

    id_lote INTEGER NOT NULL,

    cantidad NUMERIC(12,2) NOT NULL,

    ubicacion VARCHAR(80),

    fecha_ingreso DATE DEFAULT CURRENT_DATE,

    CONSTRAINT fk_inventario_lote
        FOREIGN KEY(id_lote)
        REFERENCES lote_produccion(id_lote),

    CHECK(cantidad>=0)

);

-- ==========================================
-- ÍNDICES
-- ==========================================

CREATE INDEX idx_producto_nombre
ON producto(nombre);

CREATE INDEX idx_materia_nombre
ON materia_prima(nombre);

CREATE INDEX idx_receta_producto
ON receta_produccion(id_producto);

CREATE INDEX idx_detalle_receta
ON detalle_receta(id_receta);

CREATE INDEX idx_detalle_materia
ON detalle_receta(id_materia);

CREATE INDEX idx_movimiento_fecha
ON movimiento_materia_prima(fecha);

CREATE INDEX idx_orden_estado
ON orden_produccion(estado);

CREATE INDEX idx_lote_numero
ON lote_produccion(numero_lote);

CREATE INDEX idx_control_resultado
ON control_calidad(resultado);

CREATE INDEX idx_inventario_fecha
ON inventario_producto(fecha_ingreso);
