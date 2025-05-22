SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


CREATE DATABASE concesionaria_v4;
use concesionaria_v4;

DROP DATABASE concesionaria_v4;

DROP table clientes;

select * from clientes;

CREATE TABLE IF NOT EXISTS `clientes` (
    `id_cli` int AUTO_INCREMENT PRIMARY KEY,
    `nombre_cli` varchar(30) NOT NULL,
    `apellidos_cli` varchar(30) NOT NULL,
    `dni` char(8) NOT NULL,
    `fecha_nac` date NOT NULL,
    `celular` char(9) NOT NULL,
    `email` varchar(100) NOT NULL,
    `usuario_cli` varchar(30) NOT NULL,
    `pass_cli` varchar(250) NOT NULL,
    UNIQUE (`dni`),  
    UNIQUE (`email`),
    UNIQUE (`celular`),
    UNIQUE (`usuario_cli`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO clientes (nombre_cli, apellidos_cli, dni, fecha_nac, celular, email, usuario_cli, pass_cli) VALUES 
('Juan', 'Pérez Gómez', '12345678', '1990-04-15', '987654321', 'juan.perez@example.com', 'juanp', 'pass1234'),
('Ana', 'Rodríguez Soto', '87654321', '1985-09-30', '912345678', 'ana.rs@example.com', 'anar', 'clave2023'),
('Luis', 'Martínez Ruiz', '11223344', '1992-01-25', '900111222', 'luis.mr@example.com', 'luism', 'luispass'),
('María', 'García López', '33445566', '1995-06-20', '911222333', 'maria.gl@example.com', 'mariagl', 'maria2025'),
('Carlos', 'Fernández Díaz', '55667788', '1988-12-10', '922333444', 'carlos.fd@example.com', 'carlosf', 'carlosclave');

SELECT * FROM clientes;


CREATE TABLE Autos (
    IDAuto INT PRIMARY KEY,
    Modelo VARCHAR(50),
    Color VARCHAR(30),
    Marca VARCHAR(50),
    Año INT,
    Precio DECIMAL(10,2),
    Kilometraje INT,
    Transmision VARCHAR(20),
    Combustible VARCHAR(20),
    Equipamiento1 VARCHAR(50),
    Equipamiento2 VARCHAR(50),
    Equipamiento3 VARCHAR(50),
    Equipamiento4 VARCHAR(50),
    Imagen VARCHAR(255),
    Categoria VARCHAR(30),
    Estado VARCHAR(30)
);

INSERT INTO Autos (
    IDAuto, Modelo, Color, Marca, Año, Precio, Kilometraje, Transmision, Combustible,
    Equipamiento1, Equipamiento2, Equipamiento3, Equipamiento4,
    Imagen, Categoria, Estado
) VALUES
(1, 'GRAND NOMADE', 'gris', 'SUZUKI', 2014, 36000.00, 120000, 'Mecanica', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '', 'imagenes/AUTO1.jpg', 'SUV', 'Seminuevo'),

(2, 'OUTBACK – X-EDITION', 'azul', 'SUBARU', 2019, 22500.00, 48623, 'Automatica', 'GASOLINA',
 'Aire acondicionado', '4x4', 'Transmision Automatica', 'Alzavidrios Eléctrico', 'imagenes/AUTO2.jpg', 'SUV', 'Seminuevo'),

(3, 'QASHQAI FULL', 'blanco', 'NISSAN', 2017, 15900.00, 68459, 'Automatica', 'GASOLINA',
 'Aire acondicionado', 'Espejos eléctricos', 'Alzavidrios Eléctrico', 'Transmision Automatica', 'imagenes/AUTO3.jpg', 'SUV', 'Seminuevo'),

(4, 'HR-V LX', 'Acero gris', 'HONDA', 2018, 15900.00, 67016, 'Mecanica', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '', 'imagenes/AUTO4.jpg', 'SUV', 'Seminuevo'),

(5, 'PICANTO 1.2 MT EX CROSS', 'ROJO', 'KIA', 2020, 11900.00, 73498, 'Mecanica', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '', 'imagenes/AUTO5.jpg', 'Hatchback', 'Seminuevo'),

(6, 'FRONTIER S 4X4 MT (NEW BA', 'GRIS METALICO', 'NISSAN', 2024, 33900.00, 25, 'Mecanica', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '4x4', 'imagenes/AUTO6.jpg', 'Pick-Up', 'Nuevo'),

(7, 'RUSH 1.5 FULL MT GNV', 'Plata', 'TOYOTA', 2021, 18900.00, 42254, 'Mecanica', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '', 'imagenes/AUTO7.jpg', 'SUV', 'Seminuevo'),

(8, 'XV 2.0 CVT LIMITED', 'GRIS PLATA', 'SUBARU', 2014, 14500.00, 66700, 'Automatica', 'GASOLINA',
 'Aire acondicionado', 'Espejos eléctricos', 'Alzavidrios Eléctrico', 'Transmision Automatica', 'imagenes/AUTO8.jpg', 'SUV', 'Seminuevo'),

(9, 'NEW QASHQAI ADVANCE CVT', 'PLATA SATIN', 'NISSAN', 2024, 27900.00, 3854, 'Variable Continua', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '', 'imagenes/AUTO9.jpg', 'SUV', 'Nuevo'),

(10, 'SANTA FE 2.5 AT GL 4X2', 'GRIS', 'HYUNDAI', 2024, 44900.00, 1032, 'Automatica', 'GASOLINA',
 'Aire acondicionado', 'Alzavidrios Eléctrico', 'Espejos eléctricos', '4x4', 'imagenes/AUTO10.jpg', 'SUV', 'Nuevo'),

(11, 'SORENTO MQ4 2.5 AT – EX 4', 'AZUL MINERAL', 'KIA', 2022, 27900.00, 74179, 'Automatica', 'GAS (G.L.P.)',
 'Aire acondicionado', 'Espejos eléctricos', 'Alzavidrios Eléctrico', 'Transmision Automatica', 'imagenes/AUTO11.jpg', 'SUV', 'Seminuevo'),

(12, 'NEW TUCSON', 'AZUL', 'HYUNDAI', 2022, 23900.00, 35224, 'Mecanica', 'GAS (G.L.P.)',
 'Aire acondicionado', 'Espejos eléctricos', 'Alzavidrios Eléctrico', '', 'imagenes/AUTO12.jpg', 'SUV', 'Seminuevo');
 
SELECT * FROM Autos;


CREATE TABLE Inventario (
    IDInventario INT PRIMARY KEY,
    IDAuto INT,
    CantidadAutos INT,
    FOREIGN KEY (IDAuto) REFERENCES Autos(IDAuto)
);

INSERT INTO Inventario (IDInventario, IDAuto, CantidadAutos) VALUES
(1, 1, 3),
(2, 2, 2),
(3, 3, 1),
(4, 4, 4),
(5, 5, 2);

SELECT* FROM Inventario;


CREATE TABLE Venta (
    IDVenta INT PRIMARY KEY,
    Precio DECIMAL(10,2),
    IDCliente INT,
    IDAuto INT,
    TipoComprobante VARCHAR(20),
    FOREIGN KEY (IDCliente) REFERENCES clientes(id_cli),
    FOREIGN KEY (IDAuto) REFERENCES Autos(IDAuto)
);

INSERT INTO Venta (IDVenta, Precio, IDCliente, IDAuto, TipoComprobante) VALUES
(1, 15000.00, 1, 1, 'Boleta'),
(2, 16000.00, 2, 2, 'Factura'),
(3, 35000.00, 3, 3, 'Boleta'),
(4, 22000.00, 4, 4, 'Factura'),
(5, 27000.00, 5, 5, 'Boleta');

select * from Venta;



CREATE TABLE Boleta (
    IDBoleta INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroBoleta VARCHAR(50),
    IDVenta INT,
    FOREIGN KEY (IDVenta) REFERENCES Venta(IDVenta)
);

INSERT INTO Boleta (IDBoleta, FechaEmision, NumeroBoleta, IDVenta) VALUES
(1, '2025-01-15', 'B001-0001', 1),
(2, '2025-01-18', 'B001-0002', 3),
(3, '2025-02-01', 'B001-0003', 5),
(4, '2025-02-10', 'B001-0004', 4),
(5, '2025-03-05', 'B001-0005', 2);

select * from Boleta;



CREATE TABLE DetalleBoleta (
    IDDetalleBoleta INT PRIMARY KEY,
    IDBoleta INT,
    Cantidad INT,
    PrecioUnitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    IGV DECIMAL(10,2),
    Total DECIMAL(10,2),
    FOREIGN KEY (IDBoleta) REFERENCES Boleta(IDBoleta)
);

INSERT INTO DetalleBoleta (IDDetalleBoleta, IDBoleta, Cantidad, PrecioUnitario, Subtotal, IGV, Total) VALUES
(1, 1, 1, 15000.00, 15000.00, 2700.00, 17700.00),
(2, 2, 1, 35000.00, 35000.00, 6300.00, 41300.00),
(3, 3, 1, 27000.00, 27000.00, 4860.00, 31860.00),
(4, 4, 1, 22000.00, 22000.00, 3960.00, 25960.00),
(5, 5, 1, 16000.00, 16000.00, 2880.00, 18880.00);

select * from DetalleBoleta;


CREATE TABLE Factura (
    IDFactura INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroFactura VARCHAR(50),
    RUC VARCHAR(20),
    IDVenta INT,
    FOREIGN KEY (IDVenta) REFERENCES Venta(IDVenta)
);

INSERT INTO Factura (IDFactura, FechaEmision, NumeroFactura, RUC, IDVenta) VALUES
(1, '2025-01-20', 'F001-0001', '20123456789', 2),
(2, '2025-01-25', 'F001-0002', '20123456789', 4),
(3, '2025-02-15', 'F001-0003', '20987654321', 1),
(4, '2025-03-01', 'F001-0004', '20987654321', 3),
(5, '2025-03-10', 'F001-0005', '20345678901', 5);

select * from Factura;


CREATE TABLE DetalleFactura (
    IDDetalleFactura INT PRIMARY KEY,
    IDFactura INT,
    Cantidad INT,
    PrecioUnitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    Total DECIMAL(10,2),
    FOREIGN KEY (IDFactura) REFERENCES Factura(IDFactura)
);

INSERT INTO DetalleFactura (IDDetalleFactura, IDFactura, Cantidad, PrecioUnitario, Subtotal, Total) VALUES
(1, 1, 1, 16000.00, 16000.00, 18880.00),
(2, 2, 1, 22000.00, 22000.00, 25960.00),
(3, 3, 1, 15000.00, 15000.00, 17700.00),
(4, 4, 1, 35000.00, 35000.00, 41300.00),
(5, 5, 1, 27000.00, 27000.00, 31860.00);

select * from DetalleFactura;


CREATE TABLE Alquiler (
    IDAlquiler INT PRIMARY KEY,
    Precio DECIMAL(10,2),
    IDCliente INT,
    IDAuto INT,
    TiempoAlquilado INT,
    TipoComprobante VARCHAR(20),
    FOREIGN KEY (IDCliente) REFERENCES clientes(id_cli),
    FOREIGN KEY (IDAuto) REFERENCES Autos(IDAuto)
);

INSERT INTO Alquiler (IDAlquiler, Precio, IDCliente, IDAuto, TiempoAlquilado, TipoComprobante) VALUES
(1, 500.00, 1, 2, 3, 'Boleta'),
(2, 800.00, 2, 3, 2, 'Factura'),
(3, 1200.00, 3, 4, 5, 'Boleta'),
(4, 1000.00, 4, 5, 4, 'Factura'),
(5, 600.00, 5, 1, 2, 'Boleta');

select * from Alquiler;




CREATE TABLE BoletaAlquiler (
    IDBoletaAlquiler INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroBoletaAlquiler VARCHAR(50),
    IDAlquiler INT,
    FOREIGN KEY (IDAlquiler) REFERENCES Alquiler(IDAlquiler)
);

INSERT INTO BoletaAlquiler (IDBoletaAlquiler, FechaEmision, NumeroBoletaAlquiler, IDAlquiler) VALUES
(1, '2025-03-15', 'BA001-0001', 1),
(2, '2025-03-16', 'BA001-0002', 3),
(3, '2025-03-17', 'BA001-0003', 5),
(4, '2025-03-18', 'BA001-0004', 4),
(5, '2025-03-19', 'BA001-0005', 2);

select * from BoletaAlquiler;




CREATE TABLE DetalleBoletaAlquiler (
    IDDetalleBoletaAlquiler INT PRIMARY KEY,
    IDBoletaAlquiler INT,
    Cantidad INT,
    PrecioUnitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    IGV DECIMAL(10,2),
    Total DECIMAL(10,2),
    FOREIGN KEY (IDBoletaAlquiler) REFERENCES BoletaAlquiler(IDBoletaAlquiler)
);

INSERT INTO DetalleBoletaAlquiler (IDDetalleBoletaAlquiler, IDBoletaAlquiler, Cantidad, PrecioUnitario, Subtotal, IGV, Total) VALUES
(1, 1, 1, 500.00, 500.00, 90.00, 590.00),
(2, 2, 1, 1200.00, 1200.00, 216.00, 1416.00),
(3, 3, 1, 600.00, 600.00, 108.00, 708.00),
(4, 4, 1, 1000.00, 1000.00, 180.00, 1180.00),
(5, 5, 1, 800.00, 800.00, 144.00, 944.00);

select * from DetalleBoletaAlquiler;


CREATE TABLE FacturaAlquiler (
    IDFacturaAlquiler INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroFactura VARCHAR(50),
    RUC VARCHAR(20),
    IDAlquiler INT,
    FOREIGN KEY (IDAlquiler) REFERENCES Alquiler(IDAlquiler)
);

INSERT INTO FacturaAlquiler (IDFacturaAlquiler, FechaEmision, NumeroFactura, RUC, IDAlquiler) VALUES
(1, '2025-03-20', 'FA001-0001', '20123456789', 2),
(2, '2025-03-21', 'FA001-0002', '20123456789', 4),
(3, '2025-03-22', 'FA001-0003', '20987654321', 1),
(4, '2025-03-23', 'FA001-0004', '20987654321', 3),
(5, '2025-03-24', 'FA001-0005', '20345678901', 5);

select * from FacturaAlquiler;






CREATE TABLE DetalleFacturaAlquiler (
    IDDetalleFacturaAlquiler INT PRIMARY KEY,
    IDFacturaAlquiler INT,
    Cantidad INT,
    PrecioUnitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    Total DECIMAL(10,2),
    FOREIGN KEY (IDFacturaAlquiler) REFERENCES FacturaAlquiler(IDFacturaAlquiler)
);

INSERT INTO DetalleFacturaAlquiler (IDDetalleFacturaAlquiler, IDFacturaAlquiler, Cantidad, PrecioUnitario, Subtotal, Total) VALUES
(1, 1, 1, 800.00, 800.00, 944.00),
(2, 2, 1, 1000.00, 1000.00, 1180.00),
(3, 3, 1, 500.00, 500.00, 590.00),
(4, 4, 1, 1200.00, 1200.00, 1416.00),
(5, 5, 1, 600.00, 600.00, 708.00);

select * from DetalleFacturaAlquiler;



CREATE TABLE Reclamo (
    IDReclamo INT PRIMARY KEY,
    IDCliente INT,
    MotivoReclamo VARCHAR(100),
    Placa VARCHAR(20),
    Marca VARCHAR(50),
    Modelo VARCHAR(50),
    Detalle TEXT,
    EstadoReclamo VARCHAR(50),
    Fecha DATE,
    FOREIGN KEY (IDCliente) REFERENCES clientes(id_cli)
);

INSERT INTO Reclamo (IDReclamo, IDCliente, MotivoReclamo, Placa, Marca, Modelo, Detalle, EstadoReclamo, Fecha) VALUES
(1, 1, 'Demora en entrega', 'ABC123', 'Honda', 'Civic', 'No entregaron el vehículo en la fecha acordada.', 'Pendiente', '2025-04-01'),
(2, 2, 'Mal estado del auto', 'XYZ987', 'Toyota', 'Corolla', 'El vehículo tenía rayones y abolladuras.', 'Resuelto', '2025-04-02'),
(3, 3, 'Problema con frenos', 'TES456', 'Tesla', 'Model 3', 'Frenos no respondieron correctamente.', 'En revisión', '2025-04-03'),
(4, 4, 'Aire acondicionado fallando', 'MAZ789', 'Mazda', 'CX-5', 'El aire no enfría.', 'Pendiente', '2025-04-04'),
(5, 5, 'Fuga de aceite', 'HIL159', 'Toyota', 'Hilux', 'Se detectó fuga en el motor.', 'Resuelto', '2025-04-05');

select * from Reclamo;









