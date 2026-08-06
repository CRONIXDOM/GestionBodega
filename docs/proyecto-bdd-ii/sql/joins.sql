--JOINS
--VENTAS POR PRODUCTO
SELECT
    p.codigo,
    p.nombre,
    SUM (dv.cantidad) AS cantidad_vendida
FROM detalle_venta dv
INNER JOIN productos p ON dv.id_producto=p.id_producto
GROUP BY p.codigo, p.nombre
ORDER BY p.nombre;

--VENTAS POR PROVEEDOR
SELECT
    pr.nombre AS proveedor,
    SUM(dv.cantidad) AS cantidad_vendida
FROM detalle_venta dv
INNER JOIN productos p
ON dv.id_producto=p.id_producto
INNER JOIN proveedores pr ON p.id_proveedor=pr.id_proveedor
GROUP BY pr.nombre ORDER BY pr.nombre;

--VENTAS POR CATEGORIA
SELECT
    c.nombre AS categoria,
    COUNT(dv.id_detalle) AS total_registros,
    COALESCE(SUM(dv.cantidad),0) AS cantidad_vendida
FROM categorias c
LEFT JOIN productos p
ON c.id_categoria=p.id_categoria
LEFT JOIN detalle_venta dv ON p.id_producto=dv.id_producto
GROUP BY c.nombre ORDER BY c.nombre;

--PRODUCTOS Y SUS LOTES
SELECT
    p.codigo,
    p.nombre,
    l.numero_lote,
    l.fecha_ingreso,
    l.fecha_vencimiento,
    l.cantidad
FROM productos p
INNER JOIN lotes l ON p.id_producto=l.id_producto
ORDER BY p.nombre;

--PRODUCTOS SIN VENTAS

SELECT
    p.codigo,
    p.nombre
FROM productos p
LEFT JOIN detalle_venta dv ON p.id_producto=dv.id_producto
WHERE dv.id_producto IS NULL;

--VENTAS COMPLETAS
SELECT
    v.id_venta,
    v.fecha,
    v.cliente,
    p.codigo,
    p.nombre,
    dv.cantidad,
    dv.precio_unitario,
    l.numero_lote
FROM ventas v
INNER JOIN detalle_venta dv ON v.id_venta=dv.id_venta
INNER JOIN productos p ON dv.id_producto=p.id_producto
INNER JOIN lotes l ON dv.id_lote=l.id_lote
ORDER BY v.id_venta;

--PRUEBAS
-- Ventas por producto

SELECT
    p.codigo,
    p.nombre,
    SUM(dv.cantidad)
FROM detalle_venta dv
INNER JOIN productos p ON dv.id_producto=p.id_producto
GROUP BY p.codigo,p.nombre;

-- Ventas por proveedor

SELECT
    pr.nombre
FROM detalle_venta dv
INNER JOIN productos p ON dv.id_producto=p.id_producto
INNER JOIN proveedores pr ON p.id_proveedor=pr.id_proveedor
GROUP BY pr.nombre;

-- Productos sin ventas

SELECT
    p.codigo,
    p.nombre
FROM productos p
LEFT JOIN detalle_venta dv ON p.id_producto=dv.id_producto

WHERE dv.id_producto IS NULL;
