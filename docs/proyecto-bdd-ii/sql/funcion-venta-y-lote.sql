--FUNCIÓN  REGISTRAR LOTE
CREATE OR REPLACE FUNCTION registrar_lote( p_id_producto INT,
    p_numero_lote VARCHAR, p_fecha_ingreso DATE,
    p_fecha_vencimiento DATE, p_cantidad INT, p_precio_compra NUMERIC)
RETURNS TEXT
AS $$
DECLARE
    v_producto INT;
BEGIN
    SELECT id_producto
    INTO v_producto
    FROM productos WHERE id_producto = p_id_producto;
    IF v_producto IS NULL THEN
        RETURN 'ERROR: Producto inexistente';
    END IF;
    IF p_cantidad <= 0 THEN
        RETURN 'ERROR: Cantidad inválida';
    END IF;
    BEGIN
        INSERT INTO lotes(id_producto,numero_lote,fecha_ingreso,fecha_vencimiento,
            cantidad,precio_compra)
        VALUES(p_id_producto,p_numero_lote,p_fecha_ingreso,
            p_fecha_vencimiento,p_cantidad,p_precio_compra
        );
        UPDATE productos
        SET stock_actual = stock_actual + p_cantidad
        WHERE id_producto = p_id_producto;
        RETURN 'OK: Lote registrado correctamente';
    EXCEPTION
        WHEN unique_violation THEN
            RETURN 'ERROR: El número de lote ya existe';
        WHEN OTHERS THEN
            RETURN 'ERROR: ' || SQLERRM;
    END;
END; $$ LANGUAGE plpgsql;

SELECT registrar_lote(1,'L051',CURRENT_DATE,CURRENT_DATE+180,20,1.80);


--FUNCIÓN PROCESAR VENTA
CREATE OR REPLACE FUNCTION procesar_venta(p_id_producto INT,
    p_cantidad INT)
RETURNS TEXT
AS $$
DECLARE
    v_stock INT;
BEGIN
    SELECT stock_actual
    INTO v_stock
    FROM productos WHERE id_producto = p_id_producto;
    IF v_stock IS NULL THEN
        RETURN 'ERROR: Producto inexistente';
    END IF;
    IF p_cantidad <= 0 THEN
        RETURN 'ERROR: Cantidad inválida';
    END IF;
    IF v_stock < p_cantidad THEN
        RETURN 'ERROR: Stock insuficiente';
    END IF;
    UPDATE productos
    SET stock_actual = stock_actual - p_cantidad
    WHERE id_producto = p_id_producto;
    RETURN 'OK: Venta procesada correctamente';
END;
$$ LANGUAGE plpgsql;

SELECT procesar_venta(1,5);
SELECT procesar_venta(999,5);
SELECT procesar_venta(1,1000);
SELECT procesar_venta(1,-5);

--CONSULTAS DE VERIFICACIÓN
SELECT * FROM productos ORDER BY id_producto;
SELECT * FROM lotes ORDER BY id_lote DESC;
