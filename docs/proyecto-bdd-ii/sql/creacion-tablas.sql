CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE proveedores (
    id_proveedor SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    contacto VARCHAR(150),
    telefono VARCHAR(20)
);

CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    id_categoria INT NOT NULL,
    id_proveedor INT NOT NULL,
    precio_venta NUMERIC(10,2) NOT NULL
        CHECK(precio_venta >= 0),
    stock_actual INT NOT NULL DEFAULT 0
        CHECK(stock_actual >= 0),

    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria),

    CONSTRAINT fk_producto_proveedor
        FOREIGN KEY (id_proveedor)
        REFERENCES proveedores(id_proveedor)
);

CREATE TABLE lotes (
    id_lote SERIAL PRIMARY KEY,
    id_producto INT NOT NULL,
    numero_lote VARCHAR(50) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    cantidad INT NOT NULL
        CHECK(cantidad >= 0),
    precio_compra NUMERIC(10,2) NOT NULL
        CHECK(precio_compra >= 0),

    CONSTRAINT fk_lote_producto
        FOREIGN KEY (id_producto)
        REFERENCES productos(id_producto),

    CONSTRAINT uq_lote_producto
        UNIQUE(id_producto, numero_lote),

    CONSTRAINT chk_fechas_lote
        CHECK(fecha_vencimiento > fecha_ingreso)
);

CREATE TABLE ventas (
    id_venta SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cliente VARCHAR(150) NOT NULL,
    total NUMERIC(12,2) NOT NULL
        CHECK(total >= 0),
    usuario_responsable VARCHAR(100) NOT NULL
);

CREATE TABLE detalle_venta (
    id_detalle SERIAL PRIMARY KEY,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    id_lote INT NOT NULL,
    cantidad INT NOT NULL
        CHECK(cantidad > 0),
    precio_unitario NUMERIC(10,2) NOT NULL
        CHECK(precio_unitario >= 0),

    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (id_venta)
        REFERENCES ventas(id_venta)
        ON DELETE CASCADE,

    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (id_producto)
        REFERENCES productos(id_producto),

    CONSTRAINT fk_detalle_lote
        FOREIGN KEY (id_lote)
        REFERENCES lotes(id_lote)
);

CREATE TABLE auditoria_movimientos (
    id_auditoria SERIAL PRIMARY KEY,
    tabla_afectada VARCHAR(50) NOT NULL,
    operacion VARCHAR(10) NOT NULL
        CHECK(operacion IN ('INSERT','UPDATE','DELETE')),
    usuario_bd VARCHAR(100) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalle_cambio TEXT
);