--FUNCIONES

--CALCULAR DÍAS RESTANTES PARA EL VENCIMIENTO
CREATE OR REPLACE FUNCTION fn_dias_restantes(p_id_lote INT)
RETURNS INT
AS $$
DECLARE
    v_fecha DATE;
BEGIN
    SELECT fecha_vencimiento
    INTO v_fecha
    FROM lotes WHERE id_lote = p_id_lote;
    IF v_fecha IS NULL THEN
        RETURN NULL;
    END IF;
    RETURN v_fecha - CURRENT_DATE;
END;
$$ LANGUAGE plpgsql;


SELECT fn_dias_restantes(1);
SELECT fn_dias_restantes(10);
SELECT fn_dias_restantes(50);

--PRODUCTOS EN ESTADO CRÍTICO
CREATE OR REPLACE FUNCTION fn_productos_criticos()
RETURNS TABLE(
    codigo VARCHAR,
    producto VARCHAR,
    lote VARCHAR,
    fecha_vencimiento DATE,
    dias_restantes INT
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.codigo,
        p.nombre,
        l.numero_lote,
        l.fecha_vencimiento,
        (l.fecha_vencimiento - CURRENT_DATE)::INT
    FROM productos p
    INNER JOIN lotes l ON p.id_producto = l.id_producto
    WHERE l.fecha_vencimiento <= CURRENT_DATE + 30
    ORDER BY l.fecha_vencimiento;
END; $$ LANGUAGE plpgsql;

SELECT * FROM fn_productos_criticos();

--CONTAR LOTES POR PRODUCTO
CREATE OR REPLACE FUNCTION fn_contar_lotes( p_id_producto INT)
RETURNS INT
AS $$
DECLARE
    v_total INT;
BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM lotes
    WHERE id_producto = p_id_producto;
    RETURN v_total;
END; $$ LANGUAGE plpgsql;

SELECT fn_contar_lotes(1);
SELECT fn_contar_lotes(15);
SELECT fn_contar_lotes(50);

--CONSULTAR STOCK DE UN PRODUCTO
CREATE OR REPLACE FUNCTION fn_stock_producto( p_id_producto INT)
RETURNS INT
AS $$
DECLARE
    v_stock INT;
BEGIN
    SELECT stock_actual
    INTO v_stock
    FROM productos WHERE id_producto = p_id_producto;
    IF v_stock IS NULL THEN
        RETURN 0;
    END IF;
    RETURN v_stock;
END; $$ LANGUAGE plpgsql;

SELECT fn_stock_producto(1);
SELECT fn_stock_producto(20);
SELECT fn_stock_producto(50);

--CONSULTAS FINALES

SELECT fn_dias_restantes(1);
SELECT * FROM fn_productos_criticos();
SELECT fn_contar_lotes(5);
SELECT fn_stock_producto(10);

