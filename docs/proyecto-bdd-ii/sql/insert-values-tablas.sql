INSERT INTO categorias(nombre,descripcion)
VALUES
('Analgésicos','Medicamentos para aliviar el dolor'),
('Antibióticos','Medicamentos para infecciones'),
('Vitaminas','Suplementos vitamínicos'),
('Antialérgicos','Tratamiento para alergias'),
('Dermatológicos','Productos para la piel'),
('Cuidado Personal','Higiene personal'),
('Bebés','Productos infantiles'),
('Diabetes','Control de glucosa'),
('Cardiología','Medicamentos cardiovasculares'),
('Gastrointestinal','Tratamiento digestivo');

INSERT INTO proveedores(nombre,contacto,telefono)
VALUES
('Bayer Ecuador','Carlos Pérez','0991111111'),
('Pfizer Ecuador','Ana López','0991111112'),
('Roche Ecuador','Luis Morales','0991111113'),
('Novartis Ecuador','Andrea Ruiz','0991111114'),
('Genfar','María Gómez','0991111115'),
('Difare','Juan Castro','0991111116'),
('Farmaenlace','Daniel Silva','0991111117'),
('Acromax','Paola Herrera','0991111118'),
('Life','Pedro Sánchez','0991111119'),
('Bagó','Diana Torres','0991111120');

INSERT INTO productos
(codigo,nombre,id_categoria,id_proveedor,precio_venta,stock_actual)
VALUES

('MED001','Paracetamol 500 mg',1,1,2.50,100),
('MED002','Ibuprofeno 400 mg',1,2,3.20,80),
('MED003','Diclofenaco Gel',1,3,6.50,40),
('MED004','Ketorolaco',1,4,4.80,70),
('MED005','Naproxeno',1,5,5.10,65),

('ANT001','Amoxicilina 500 mg',2,2,8.50,90),
('ANT002','Azitromicina',2,3,10.25,60),
('ANT003','Cefalexina',2,4,7.80,55),
('ANT004','Ciprofloxacina',2,5,9.30,50),
('ANT005','Clindamicina',2,1,11.20,45),

('VIT001','Vitamina C',3,6,6.50,120),
('VIT002','Complejo B',3,6,7.00,90),
('VIT003','Vitamina D',3,7,9.00,75),
('VIT004','Calcio + D',3,8,12.00,40),
('VIT005','Omega 3',3,9,18.50,35),

('ALE001','Loratadina',4,2,5.60,85),
('ALE002','Cetirizina',4,4,6.10,70),
('ALE003','Desloratadina',4,5,8.30,45),
('ALE004','Fexofenadina',4,6,9.20,30),
('ALE005','Clorfenamina',4,7,4.70,55),

('DER001','Protector Solar FPS50',5,8,18.00,25),
('DER002','Crema Hidratante',5,9,14.50,40),
('DER003','Pomada Antibiótica',5,10,12.75,30),
('DER004','Gel Limpiador Facial',5,1,11.90,28),
('DER005','Crema para Quemaduras',5,3,15.80,20),

('HIG001','Alcohol Antiséptico',6,6,3.50,150),
('HIG002','Agua Oxigenada',6,6,2.20,120),
('HIG003','Jabón Antibacterial',6,7,4.10,90),
('HIG004','Gel Antibacterial',6,8,3.80,95),
('HIG005','Algodón',6,9,2.80,130),

('BEB001','Pañales Talla P',7,3,15.00,40),
('BEB002','Pañales Talla M',7,3,16.00,35),
('BEB003','Pañales Talla G',7,3,17.00,30),
('BEB004','Toallitas Húmedas',7,4,5.00,70),
('BEB005','Talco para Bebé',7,5,6.80,45),

('DIA001','Glucómetro',8,6,45.00,12),
('DIA002','Tiras Reactivas',8,6,18.00,25),
('DIA003','Lancetas',8,7,8.50,60),
('DIA004','Insulina',8,8,35.00,18),
('DIA005','Alcohol Pads',8,9,4.50,55),

('CAR001','Losartán',9,2,11.50,45),
('CAR002','Enalapril',9,3,9.20,60),
('CAR003','Amlodipino',9,4,8.90,50),
('CAR004','Aspirina Cardio',9,5,6.70,75),
('CAR005','Atorvastatina',9,6,14.50,30),

('GAS001','Omeprazol',10,7,5.90,80),
('GAS002','Pantoprazol',10,8,7.30,50),
('GAS003','Sales de Rehidratación',10,9,3.80,120),
('GAS004','Loperamida',10,10,4.20,65),
('GAS005','Antiácido Líquido',10,1,6.90,40);

INSERT INTO lotes
(id_producto,numero_lote,fecha_ingreso,fecha_vencimiento,cantidad,precio_compra)
VALUES

(1,'L001',CURRENT_DATE-40,CURRENT_DATE+180,40,1.20),
(2,'L002',CURRENT_DATE-35,CURRENT_DATE+160,35,1.80),
(3,'L003',CURRENT_DATE-30,CURRENT_DATE+140,20,3.50),
(4,'L004',CURRENT_DATE-28,CURRENT_DATE+120,25,2.60),
(5,'L005',CURRENT_DATE-20,CURRENT_DATE+100,30,2.80),

(6,'L006',CURRENT_DATE-25,CURRENT_DATE+220,40,4.50),
(7,'L007',CURRENT_DATE-18,CURRENT_DATE+200,35,5.60),
(8,'L008',CURRENT_DATE-15,CURRENT_DATE+180,28,4.80),
(9,'L009',CURRENT_DATE-10,CURRENT_DATE+160,30,5.20),
(10,'L010',CURRENT_DATE-8,CURRENT_DATE+150,25,6.30),

(11,'L011',CURRENT_DATE-20,CURRENT_DATE+90,50,3.10),
(12,'L012',CURRENT_DATE-18,CURRENT_DATE+85,35,3.20),
(13,'L013',CURRENT_DATE-16,CURRENT_DATE+80,28,4.60),
(14,'L014',CURRENT_DATE-15,CURRENT_DATE+75,20,6.50),
(15,'L015',CURRENT_DATE-14,CURRENT_DATE+70,18,9.20),

(16,'L016',CURRENT_DATE-10,CURRENT_DATE+60,40,2.90),
(17,'L017',CURRENT_DATE-9,CURRENT_DATE+55,35,3.10),
(18,'L018',CURRENT_DATE-8,CURRENT_DATE+50,20,5.00),
(19,'L019',CURRENT_DATE-7,CURRENT_DATE+45,18,6.50),
(20,'L020',CURRENT_DATE-6,CURRENT_DATE+40,22,2.70),

(21,'L021',CURRENT_DATE-20,CURRENT_DATE+35,15,10.20),
(22,'L022',CURRENT_DATE-18,CURRENT_DATE+32,18,8.80),
(23,'L023',CURRENT_DATE-17,CURRENT_DATE+28,15,7.30),
(24,'L024',CURRENT_DATE-15,CURRENT_DATE+25,18,6.80),
(25,'L025',CURRENT_DATE-13,CURRENT_DATE+20,20,9.10),

(26,'L026',CURRENT_DATE-12,CURRENT_DATE+180,60,1.30),
(27,'L027',CURRENT_DATE-11,CURRENT_DATE+170,55,1.10),
(28,'L028',CURRENT_DATE-10,CURRENT_DATE+160,40,2.20),
(29,'L029',CURRENT_DATE-8,CURRENT_DATE+150,38,1.90),
(30,'L030',CURRENT_DATE-6,CURRENT_DATE+140,50,1.50),

(31,'L031',CURRENT_DATE-20,CURRENT_DATE+300,20,10.00),
(32,'L032',CURRENT_DATE-18,CURRENT_DATE+290,20,10.80),
(33,'L033',CURRENT_DATE-16,CURRENT_DATE+280,18,11.50),
(34,'L034',CURRENT_DATE-15,CURRENT_DATE+270,30,2.90),
(35,'L035',CURRENT_DATE-14,CURRENT_DATE+260,22,4.30),

(36,'L036',CURRENT_DATE-12,CURRENT_DATE+250,10,30.00),
(37,'L037',CURRENT_DATE-10,CURRENT_DATE+240,20,12.00),
(38,'L038',CURRENT_DATE-9,CURRENT_DATE+230,40,3.50),
(39,'L039',CURRENT_DATE-8,CURRENT_DATE+220,15,24.00),
(40,'L040',CURRENT_DATE-7,CURRENT_DATE+210,30,1.50),

(41,'L041',CURRENT_DATE-15,CURRENT_DATE+200,30,8.50),
(42,'L042',CURRENT_DATE-14,CURRENT_DATE+190,35,6.50),
(43,'L043',CURRENT_DATE-13,CURRENT_DATE+180,30,6.20),
(44,'L044',CURRENT_DATE-12,CURRENT_DATE+170,40,4.20),
(45,'L045',CURRENT_DATE-11,CURRENT_DATE+160,25,10.30),

(46,'L046',CURRENT_DATE-10,CURRENT_DATE+150,45,2.80),
(47,'L047',CURRENT_DATE-9,CURRENT_DATE+140,30,3.60),
(48,'L048',CURRENT_DATE-8,CURRENT_DATE+130,60,1.90),
(49,'L049',CURRENT_DATE-7,CURRENT_DATE+120,28,2.10),
(50,'L050',CURRENT_DATE-6,CURRENT_DATE+110,25,4.00);

INSERT INTO detalle_venta
(id_venta,id_producto,id_lote,cantidad,precio_unitario)
VALUES

(1,1,1,2,2.50),
(1,2,2,1,3.20),
(1,11,11,3,6.50),

(2,6,6,2,8.50),
(2,16,16,1,5.60),
(2,21,21,1,18.00),

(3,26,26,4,3.50),
(3,27,27,2,2.20),
(3,31,31,1,15.00),

(4,36,36,1,45.00),
(4,37,37,2,18.00),
(4,38,38,5,8.50),

(5,41,41,2,11.50),
(5,42,42,2,9.20),
(5,46,46,3,5.90),

(6,47,47,2,7.30),
(6,48,48,4,3.80),
(6,49,49,2,4.20),

(7,3,3,2,6.50),
(7,4,4,2,4.80),
(7,5,5,1,5.10),

(8,7,7,2,10.25),
(8,8,8,3,7.80),
(8,9,9,2,9.30),

(9,10,10,2,11.20),
(9,12,12,2,7.00),
(9,13,13,1,9.00),

(10,14,14,2,12.00),
(10,15,15,1,18.50),
(10,17,17,2,6.10),

(11,18,18,2,8.30),
(11,19,19,1,9.20),
(11,20,20,2,4.70),

(12,22,22,2,14.50),
(12,23,23,1,12.75),
(12,24,24,2,11.90),

(13,25,25,2,15.80),
(13,28,28,4,4.10),
(13,29,29,3,3.80),

(14,30,30,4,2.80),
(14,32,32,2,16.00),
(14,33,33,2,17.00),

(15,34,34,3,5.00),
(15,35,35,2,6.80),
(15,39,39,1,35.00),

(16,40,40,5,4.50),
(16,43,43,2,8.90),
(16,44,44,2,6.70),

(17,45,45,1,14.50),
(17,50,50,3,6.90),
(17,1,1,4,2.50),

(18,6,6,2,8.50),
(18,11,11,3,6.50),
(18,16,16,2,5.60),

(19,21,21,2,18.00),
(19,26,26,5,3.50),
(19,31,31,2,15.00),

(20,36,36,1,45.00),
(20,41,41,2,11.50),
(20,46,46,3,5.90);

INSERT INTO ventas
(fecha,cliente,total,usuario_responsable)
VALUES

(CURRENT_DATE-10,'Juan Pérez',0,'admin'),
(CURRENT_DATE-9,'María Gómez',0,'admin'),
(CURRENT_DATE-8,'Carlos Ruiz',0,'admin'),
(CURRENT_DATE-7,'Ana Torres',0,'admin'),
(CURRENT_DATE-6,'Pedro Silva',0,'admin'),
(CURRENT_DATE-5,'Clínica Central',0,'admin'),
(CURRENT_DATE-4,'Hospital Quito',0,'admin'),
(CURRENT_DATE-3,'Andrea López',0,'admin'),
(CURRENT_DATE-2,'Luis Herrera',0,'admin'),
(CURRENT_DATE-1,'Farmacia Norte',0,'admin'),

(CURRENT_DATE,'Gabriela Paz',0,'admin'),
(CURRENT_DATE,'José Andrade',0,'admin'),
(CURRENT_DATE,'Mónica León',0,'admin'),
(CURRENT_DATE,'Pablo Rojas',0,'admin'),
(CURRENT_DATE,'Andrea Castillo',0,'admin'),
(CURRENT_DATE,'Daniel Molina',0,'admin'),
(CURRENT_DATE,'Patricia Vaca',0,'admin'),
(CURRENT_DATE,'Clínica Vida',0,'admin'),
(CURRENT_DATE,'Hospital Sur',0,'admin'),
(CURRENT_DATE,'Juan Carlos',0,'admin');

