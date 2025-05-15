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

CREATE TABLE Inventario (
    IDInventario INT PRIMARY KEY,
    IDAuto INT,
    CantidadAutos INT,
    FOREIGN KEY (IDAuto) REFERENCES Autos(IDAuto)
);

CREATE TABLE Venta (
    IDVenta INT PRIMARY KEY,
    Precio DECIMAL(10,2),
    IDCliente INT,
    IDAuto INT,
    TipoComprobante VARCHAR(20),
    FOREIGN KEY (IDCliente) REFERENCES clientes(id_cli),
    FOREIGN KEY (IDAuto) REFERENCES Autos(IDAuto)
);

CREATE TABLE Boleta (
    IDBoleta INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroBoleta VARCHAR(50),
    IDVenta INT,
    FOREIGN KEY (IDVenta) REFERENCES Venta(IDVenta)
);

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

CREATE TABLE Factura (
    IDFactura INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroFactura VARCHAR(50),
    RUC VARCHAR(20),
    IDVenta INT,
    FOREIGN KEY (IDVenta) REFERENCES Venta(IDVenta)
);

CREATE TABLE DetalleFactura (
    IDDetalleFactura INT PRIMARY KEY,
    IDFactura INT,
    Cantidad INT,
    PrecioUnitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    Total DECIMAL(10,2),
    FOREIGN KEY (IDFactura) REFERENCES Factura(IDFactura)
);

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

CREATE TABLE BoletaAlquiler (
    IDBoletaAlquiler INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroBoletaAlquiler VARCHAR(50),
    IDAlquiler INT,
    FOREIGN KEY (IDAlquiler) REFERENCES Alquiler(IDAlquiler)
);

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

CREATE TABLE FacturaAlquiler (
    IDFacturaAlquiler INT PRIMARY KEY,
    FechaEmision DATE,
    NumeroFactura VARCHAR(50),
    RUC VARCHAR(20),
    IDAlquiler INT,
    FOREIGN KEY (IDAlquiler) REFERENCES Alquiler(IDAlquiler)
);

CREATE TABLE DetalleFacturaAlquiler (
    IDDetalleFacturaAlquiler INT PRIMARY KEY,
    IDFacturaAlquiler INT,
    Cantidad INT,
    PrecioUnitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    Total DECIMAL(10,2),
    FOREIGN KEY (IDFacturaAlquiler) REFERENCES FacturaAlquiler(IDFacturaAlquiler)
);

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








