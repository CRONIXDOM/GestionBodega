-- Datos minimos para ver el sistema funcionando.
-- Opcional: la aplicacion arranca igual con la base vacia.

INSERT INTO producto (nombre, tipo, presentacion, volumen_ml, estado) VALUES
    ('Cola Andina',    'GASEOSA', 'BOTELLA PET', 350,  TRUE),
    ('Cola Andina',    'GASEOSA', 'BOTELLA 2L',  2000, TRUE),
    ('Naranja Andina', 'GASEOSA', 'BOTELLA PET', 500,  TRUE),
    ('Limon Andina',   'GASEOSA', 'BOTELLA 1L',  1000, TRUE);

INSERT INTO materia_prima (nombre, unidad_medida, stock_actual, stock_minimo) VALUES
    ('Agua purificada',    'LITRO',     5000, 500),
    ('Azucar',             'KILOGRAMO',  800, 100),
    ('Dioxido de carbono', 'KILOGRAMO',  300,  50),
    ('Colorante caramelo', 'GRAMO',    12000, 2000),
    ('Saborizante cola',   'GRAMO',     9000, 1500),
    ('Acido citrico',      'GRAMO',     7000, 1000);

INSERT INTO receta_produccion (id_producto, version, fecha_vigencia, estado) VALUES
    (1, 1, DATE '2026-01-15', FALSE),
    (1, 2, DATE '2026-06-01', TRUE),
    (3, 1, DATE '2026-03-10', TRUE);

-- La version 1 de la cola lleva 5 materias primas; la version 2 quito el colorante.
INSERT INTO detalle_receta (id_receta, id_materia, cantidad, unidad) VALUES
    (1, 1, 0.320, 'LITRO'), (1, 2, 0.035, 'KILOGRAMO'), (1, 3, 0.006, 'KILOGRAMO'),
    (1, 4, 1.200, 'GRAMO'), (1, 5, 0.800, 'GRAMO'),
    (2, 1, 0.325, 'LITRO'), (2, 2, 0.030, 'KILOGRAMO'), (2, 3, 0.006, 'KILOGRAMO'),
    (2, 5, 0.850, 'GRAMO'),
    (3, 1, 0.470, 'LITRO'), (3, 2, 0.045, 'KILOGRAMO'), (3, 6, 1.100, 'GRAMO');

INSERT INTO movimiento_materia_prima (id_materia, fecha, tipo, cantidad, observacion) VALUES
    (1, TIMESTAMP '2026-06-20 08:00', 'INGRESO', 5000, 'Compra mensual'),
    (2, TIMESTAMP '2026-06-20 08:30', 'INGRESO',  800, 'Compra mensual');

INSERT INTO orden_produccion (id_producto, fecha_programada, cantidad_programada, estado, responsable) VALUES
    (1, DATE '2026-07-01', 10000, 'FINALIZADA', 'Planificacion'),
    (3, DATE '2026-07-05',  4000, 'EN_PROCESO', 'Planificacion');

INSERT INTO lote_produccion (id_orden, numero_lote, fecha_inicio, fecha_fin, cantidad_producida, estado) VALUES
    (1, 'LT-0001', TIMESTAMP '2026-07-01 06:00', TIMESTAMP '2026-07-01 14:00', 6000, 'FINALIZADO'),
    (1, 'LT-0002', TIMESTAMP '2026-07-02 06:00', TIMESTAMP '2026-07-02 14:00', 4000, 'FINALIZADO'),
    (2, 'LT-0003', TIMESTAMP '2026-07-05 06:00', NULL,                         4000, 'EN_PROCESO');

INSERT INTO control_calidad (id_lote, fecha_control, ph, brix, temperatura, resultado, observaciones) VALUES
    (1, TIMESTAMP '2026-07-01 15:00', 2.85, 10.50, 4.00, 'APROBADO',  'Dentro de parametros'),
    (2, TIMESTAMP '2026-07-02 15:00', 3.60, 10.20, 4.50, 'OBSERVADO', 'pH por encima de lo habitual');

INSERT INTO inventario_producto (id_lote, cantidad, ubicacion, fecha_ingreso) VALUES
    (1, 6000, 'PASILLO A - ESTANTE 1', DATE '2026-07-02');
