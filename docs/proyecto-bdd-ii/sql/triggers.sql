--TRIGGERS
--RECHAZAR LOTES VENCIDOS
CREATE OR REPLACE FUNCTION fn_validar_fecha_vencimiento()
RETURNS TRIGGER
AS $$
BEGIN
    IF NEW.fecha_vencimiento < CURRENT_DATE THEN
        RAISE EXCEPTION
        'ERROR: No se puede registrar un lote vencido';
    END IF;
    RETURN NEW;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validar_fecha_vencimiento
BEFORE INSERT ON lotes
FOR EACH ROW
EXECUTE FUNCTION fn_validar_fecha_vencimiento();

-- Correcto
INSERT INTO lotes
(id_producto,numero_lote,fecha_ingreso,fecha_vencimiento,cantidad,precio_compra)
VALUES (1,'L100',CURRENT_DATE,CURRENT_DATE+100,20,1.50);

-- Debe generar ERROR

INSERT INTO lotes
(id_producto,numero_lote,fecha_ingreso,fecha_vencimiento,cantidad,precio_compra)
VALUES (1,'L101',CURRENT_DATE,CURRENT_DATE-10,20,1.50);

--ACTUALIZAR STOCK DESPUÉS DE UNA VENTA
CREATE OR REPLACE FUNCTION fn_actualizar_stock()
RETURNS TRIGGER
AS $$
BEGIN
    UPDATE productos
    SET stock_actual = stock_actual - NEW.cantidad
    WHERE id_producto = NEW.id_producto;
    RETURN NEW;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_stock
AFTER INSERT ON detalle_venta
FOR EACH ROW
EXECUTE FUNCTION fn_actualizar_stock();

INSERT INTO detalle_venta
(id_venta,id_producto,id_lote,cantidad,precio_unitario)
VALUES (1,2,2,3,3.20);

SELECT * FROM productos WHERE id_producto=2;

--MOVIMIENTOS DEL INVENTARIO
CREATE OR REPLACE FUNCTION fn_auditoria_inventario()
RETURNS TRIGGER
AS $$
BEGIN
    INSERT INTO auditoria_movimientos(tabla_afectada,operacion,
        usuario_bd,fecha,detalle)
    VALUES(TG_TABLE_NAME,TG_OP,CURRENT_USER,CURRENT_TIMESTAMP,
        'Producto ID: ' || NEW.id_producto || ' Stock actual: '
        || NEW.stock_actual);
    RETURN NEW;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auditoria_productos
AFTER UPDATE
ON productos
FOR EACH ROW
EXECUTE FUNCTION fn_auditoria_inventario();

UPDATE productos
SET stock_actual=stock_actual+10
WHERE id_producto=1;

SELECT * FROM auditoria_movimientos ORDER BY id_auditoria DESC;

--CONSULTAS
SELECT * FROM productos;
SELECT * FROM lotes;
SELECT * FROM auditoria_movimientos;

	