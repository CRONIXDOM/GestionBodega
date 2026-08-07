-- Datos minimos para poder ver el sistema funcionando.
-- Opcional: la aplicacion arranca igual con la base vacia.

INSERT INTO producto (nombre, presentacion) VALUES
    ('Cola Andina', '350 ML'),
    ('Cola Andina', '2 LITROS'),
    ('Naranja Andina', '500 ML'),
    ('Limon Andina', '1 LITRO')
ON CONFLICT DO NOTHING;

INSERT INTO materia_prima (nombre, unidad_medida, stock) VALUES
    ('Agua purificada', 'LITRO', 5000),
    ('Azucar', 'KILOGRAMO', 800),
    ('Dioxido de carbono', 'KILOGRAMO', 300),
    ('Colorante caramelo', 'GRAMO', 12000),
    ('Saborizante cola', 'GRAMO', 9000),
    ('Acido citrico', 'GRAMO', 7000)
ON CONFLICT DO NOTHING;

INSERT INTO receta (id_producto, version, fecha, activa) VALUES
    (1, 'V1', DATE '2026-01-15', FALSE),
    (1, 'V2', DATE '2026-06-01', TRUE),
    (3, 'V1', DATE '2026-03-10', TRUE)
ON CONFLICT DO NOTHING;

-- La V1 de la cola lleva 5 materias primas; la V2 quito el colorante.
INSERT INTO receta_detalle (id_receta, id_materia_prima, cantidad) VALUES
    (1, 1, 0.320), (1, 2, 0.035), (1, 3, 0.006), (1, 4, 1.200), (1, 5, 0.800),
    (2, 1, 0.325), (2, 2, 0.030), (2, 3, 0.006), (2, 5, 0.850),
    (3, 1, 0.470), (3, 2, 0.045), (3, 6, 1.100)
ON CONFLICT DO NOTHING;

INSERT INTO orden_produccion (codigo, id_producto, cantidad_programada, fecha_produccion, estado) VALUES
    ('OP-2026-001', 1, 10000, DATE '2026-07-01', 'FINALIZADA'),
    ('OP-2026-002', 3,  4000, DATE '2026-07-05', 'EN PROCESO')
ON CONFLICT DO NOTHING;

INSERT INTO lote_produccion (codigo_lote, id_orden, cantidad_producida, fecha_fabricacion) VALUES
    ('LT-0001', 1, 6000, DATE '2026-07-01'),
    ('LT-0002', 1, 4000, DATE '2026-07-02'),
    ('LT-0003', 2, 4000, DATE '2026-07-05')
ON CONFLICT DO NOTHING;

INSERT INTO control_calidad (id_lote, ph, grados_brix, temperatura, resultado, fecha_inspeccion) VALUES
    (1, 2.85, 10.50, 4.00, 'APROBADO',  DATE '2026-07-01'),
    (2, 3.60, 10.20, 4.50, 'OBSERVADO', DATE '2026-07-02')
ON CONFLICT DO NOTHING;

INSERT INTO almacen_producto_terminado (id_lote, cantidad, ubicacion_fisica, fecha_ingreso) VALUES
    (1, 6000, 'PASILLO A - ESTANTE 1', DATE '2026-07-02')
ON CONFLICT DO NOTHING;

INSERT INTO movimiento_inventario (id_materia_prima, tipo, cantidad, fecha, observacion) VALUES
    (1, 'INGRESO', 5000, DATE '2026-06-20', 'Compra mensual'),
    (2, 'INGRESO',  800, DATE '2026-06-20', 'Compra mensual')
ON CONFLICT DO NOTHING;
