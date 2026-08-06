--STOCK ACTUAL POR PRODUCTO
CREATE OR REPLACE VIEW vw_stock_actual AS
SELECT
    codigo,
    nombre,
    stock_actual
FROM productos;

SELECT * FROM vw_stock_actual;

--PRODUCTOS PRÓXIMOS A VENCER
CREATE OR REPLACE VIEW vw_productos_proximos_vencer AS
SELECT
    p.codigo,
    p.nombre,
    l.numero_lote,
    l.fecha_ingreso,
    l.fecha_vencimiento,
    l.cantidad
FROM productos p
INNER JOIN lotes l ON p.id_producto=l.id_producto
WHERE l.fecha_vencimiento
BETWEEN CURRENT_DATE
AND CURRENT_DATE+30;

SELECT * FROM vw_productos_proximos_vencer;

--RESUMEN DE VENTAS POR PRODUCTO
CREATE OR REPLACE VIEW vw_resumen_ventas AS
SELECT
    p.codigo,
    p.nombre,
    SUM(dv.cantidad) AS cantidad_vendida
FROM detalle_venta dv
INNER JOIN productos p ON dv.id_producto=p.id_producto
GROUP BY
    p.codigo,
    p.nombre;

SELECT * FROM vw_resumen_ventas;
SELECT * FROM vw_stock_actual;
SELECT * FROM vw_productos_proximos_vencer;


