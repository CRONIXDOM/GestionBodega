-- ===========================================================================
--  TransLog Ecuador S.A.
--
--  Solo las tablas que hacen falta para sostener la logica del caso y, sobre
--  todo, la regla de negocio del despacho:
--
--    "Un despacho podra registrarse unicamente cuando tenga una ruta definida,
--     un vehiculo en estado disponible y un conductor habilitado. Los envios
--     asociados al despacho deben corresponder al origen y destino de la ruta
--     seleccionada y no pueden encontrarse asignados previamente a otro
--     despacho activo o finalizado. Ademas, la suma del peso de los envios
--     asignados no debe superar la capacidad maxima del vehiculo."
--
--  Seis tablas, y cada una esta porque la regla la nombra:
--    ciudad     -> el origen y el destino que se comparan entre envio y ruta
--    ruta       -> "una ruta definida"
--    vehiculo   -> "en estado disponible" y "capacidad maxima"
--    conductor  -> "un conductor habilitado"
--    despacho   -> lo que se valida, con su estado activo o finalizado
--    envio      -> lo que se asigna, con su peso y su origen y destino
--
--  No hay tabla de clientes: el enunciado enumera los datos del envio -origen,
--  destino, peso, fecha de registro y valor declarado- y el cliente no esta
--  entre ellos, ni la regla de negocio lo menciona.
-- ===========================================================================

DROP TABLE IF EXISTS envio;
DROP TABLE IF EXISTS despacho;
DROP TABLE IF EXISTS conductor;
DROP TABLE IF EXISTS vehiculo;
DROP TABLE IF EXISTS ruta;
DROP TABLE IF EXISTS ciudad;

-- Las ciudades entre las que la empresa mueve paquetes. Estan en su propia
-- tabla porque la regla compara el origen y el destino del envio con los de la
-- ruta: comparando identificadores la regla es exacta, comparando texto
-- dependeria de como se haya escrito el nombre.
CREATE TABLE ciudad (
    id_ciudad SERIAL PRIMARY KEY,
    nombre    VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE ruta (
    id_ruta           SERIAL PRIMARY KEY,
    id_ciudad_origen  INTEGER NOT NULL REFERENCES ciudad(id_ciudad),
    id_ciudad_destino INTEGER NOT NULL REFERENCES ciudad(id_ciudad),
    distancia_km      NUMERIC(10,2) NOT NULL,
    CONSTRAINT ruta_distancia_positiva CHECK (distancia_km > 0),
    CONSTRAINT ruta_ciudades_distintas CHECK (id_ciudad_origen <> id_ciudad_destino),
    CONSTRAINT ruta_no_repetida UNIQUE (id_ciudad_origen, id_ciudad_destino)
);

CREATE TABLE vehiculo (
    id_vehiculo      SERIAL PRIMARY KEY,
    placa            VARCHAR(15) NOT NULL UNIQUE,
    capacidad_maxima NUMERIC(10,2) NOT NULL,
    estado           VARCHAR(20) NOT NULL,
    CONSTRAINT vehiculo_capacidad_positiva CHECK (capacidad_maxima > 0),
    CONSTRAINT vehiculo_estado_valido CHECK (estado IN ('DISPONIBLE', 'EN_RUTA', 'MANTENIMIENTO'))
);

CREATE TABLE conductor (
    id_conductor SERIAL PRIMARY KEY,
    nombre       VARCHAR(120) NOT NULL,
    licencia     VARCHAR(20) NOT NULL UNIQUE,
    estado       VARCHAR(20) NOT NULL,
    CONSTRAINT conductor_estado_valido CHECK (estado IN ('HABILITADO', 'NO_HABILITADO'))
);

CREATE TABLE despacho (
    id_despacho    SERIAL PRIMARY KEY,
    fecha_despacho DATE NOT NULL,
    id_ruta        INTEGER NOT NULL REFERENCES ruta(id_ruta),
    id_vehiculo    INTEGER NOT NULL REFERENCES vehiculo(id_vehiculo),
    id_conductor   INTEGER NOT NULL REFERENCES conductor(id_conductor),
    estado         VARCHAR(20) NOT NULL,
    CONSTRAINT despacho_estado_valido CHECK (estado IN ('ACTIVO', 'FINALIZADO', 'CANCELADO'))
);

-- El envio nace suelto y se asigna despues a un despacho: por eso id_despacho
-- admite nulos. Como la columna es una sola, un envio no puede estar en dos
-- despachos a la vez, que es justo lo que pide la regla.
CREATE TABLE envio (
    id_envio          SERIAL PRIMARY KEY,
    id_ciudad_origen  INTEGER NOT NULL REFERENCES ciudad(id_ciudad),
    id_ciudad_destino INTEGER NOT NULL REFERENCES ciudad(id_ciudad),
    peso              NUMERIC(10,2) NOT NULL,
    fecha_registro    DATE NOT NULL,
    valor_declarado   NUMERIC(12,2) NOT NULL,
    estado            VARCHAR(20) NOT NULL,
    id_despacho       INTEGER REFERENCES despacho(id_despacho),
    CONSTRAINT envio_peso_positivo CHECK (peso > 0),
    CONSTRAINT envio_valor_no_negativo CHECK (valor_declarado >= 0),
    CONSTRAINT envio_ciudades_distintas CHECK (id_ciudad_origen <> id_ciudad_destino),
    CONSTRAINT envio_estado_valido CHECK (estado IN ('REGISTRADO', 'EN_TRANSITO', 'ENTREGADO', 'CON_NOVEDAD'))
);

CREATE INDEX idx_envio_despacho ON envio(id_despacho);
CREATE INDEX idx_despacho_ruta ON despacho(id_ruta);

-- ---------------------------------------------------------------------------
--  Datos de arranque
-- ---------------------------------------------------------------------------
INSERT INTO ciudad (nombre) VALUES
    ('QUITO'), ('GUAYAQUIL'), ('CUENCA'), ('AMBATO'), ('MANTA');

INSERT INTO ruta (id_ciudad_origen, id_ciudad_destino, distancia_km) VALUES
    (1, 2, 420.00),
    (1, 3, 445.00),
    (2, 5, 195.00),
    (1, 4, 137.00);

INSERT INTO vehiculo (placa, capacidad_maxima, estado) VALUES
    ('PBA-1234', 3500.00, 'DISPONIBLE'),
    ('GYE-5678', 1200.00, 'DISPONIBLE'),
    ('AZU-9012', 8000.00, 'MANTENIMIENTO');

INSERT INTO conductor (nombre, licencia, estado) VALUES
    ('LUIS ANDRADE', 'LIC-001', 'HABILITADO'),
    ('MARIA TORRES', 'LIC-002', 'HABILITADO'),
    ('JORGE PAREDES', 'LIC-003', 'NO_HABILITADO');

INSERT INTO envio (id_ciudad_origen, id_ciudad_destino, peso, fecha_registro, valor_declarado, estado) VALUES
    (1, 2, 120.50, '2026-02-02', 850.00, 'REGISTRADO'),
    (1, 2,  75.00, '2026-02-03', 300.00, 'REGISTRADO'),
    (1, 3, 240.00, '2026-02-03', 1500.00, 'REGISTRADO'),
    (2, 5,  60.00, '2026-02-04', 220.00, 'REGISTRADO');
