--ÍNDICE
--CONSULTA SIN ÍNDICE
EXPLAIN ANALYZE
SELECT * FROM productos
WHERE codigo='MED001';

--CREACIÓN DEL ÍNDICE
CREATE INDEX idx_codigo_producto
ON productos(codigo);

--CONSULTA CON ÍNDICE
EXPLAIN ANALYZE
SELECT * FROM productos
WHERE codigo='MED001';

-- INFORMACIÓN DEL ÍNDICE
SELECT indexname,
pg_size_pretty(pg_relation_size(indexname::text)) AS tamanio
FROM pg_indexes WHERE schemaname='public' 
AND indexname='idx_codigo_producto';

--INDICE 2 FECHA DE VENCIMIENTO
--CONSULTA SIN ÍNDICE
EXPLAIN ANALYZE
SELECT * FROM lotes
WHERE fecha_vencimiento<=CURRENT_DATE+30;

--CREACIÓN DEL ÍNDICE
CREATE INDEX idx_fecha_vencimiento
ON lotes(fecha_vencimiento);

--CONSULTA CON ÍNDICE
EXPLAIN ANALYZE
SELECT * FROM lotes
WHERE fecha_vencimiento<=CURRENT_DATE+30;
      
--INFORMACIÓN DEL ÍNDICE
SELECT indexname,
pg_size_pretty(pg_relation_size(indexname::text)) AS tamanio
FROM pg_indexes WHERE schemaname='public'
AND indexname='idx_fecha_vencimiento';

--CONSULTAR TODOS LOS ÍNDICES
SELECT indexname, tablename,
pg_size_pretty(pg_relation_size(indexname::text)) AS tamanio
FROM pg_indexes WHERE schemaname='public'
ORDER BY pg_relation_size(indexname::text) DESC;

--PRUEBAS
-- Buscar un producto por código
SELECT * FROM productos 
WHERE codigo='MED010';

-- Buscar productos próximos a vencer
SELECT * FROM lotes
WHERE fecha_vencimiento<=CURRENT_DATE+30;
