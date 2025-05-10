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
    `email` varchar(30) NOT NULL,
    `usuario_cli` varchar(30) NOT NULL,
    `pass_cli` varchar(50) NOT NULL,
    UNIQUE (`dni`),  
    UNIQUE (`email`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO clientes (nombre_cli, apellidos_cli, dni, fecha_nac, celular, email, usuario_cli, pass_cli) VALUES 
('Juan', 'Pérez Gómez', '12345678', '1990-04-15', '987654321', 'juan.perez@example.com', 'juanp', 'pass1234'),
('Ana', 'Rodríguez Soto', '87654321', '1985-09-30', '912345678', 'ana.rs@example.com', 'anar', 'clave2023'),
('Luis', 'Martínez Ruiz', '11223344', '1992-01-25', '900111222', 'luis.mr@example.com', 'luism', 'luispass'),
('María', 'García López', '33445566', '1995-06-20', '911222333', 'maria.gl@example.com', 'mariagl', 'maria2025'),
('Carlos', 'Fernández Díaz', '55667788', '1988-12-10', '922333444', 'carlos.fd@example.com', 'carlosf', 'carlosclave');

SELECT * FROM clientes;


CREATE TABLE IF NOT EXISTS `admin` (
    `id_admin` int AUTO_INCREMENT PRIMARY KEY,
    `usuario_admin` varchar(30) NOT NULL,
    `pass_admin` varchar(50) NOT NULL 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `autos` (
    `id_auto` int AUTO_INCREMENT PRIMARY KEY,
    `modelo` varchar(100) NOT NULL, 
    `marca` varchar(100) NOT NULL, 
    `ano` int NOT NULL,
    `precio` decimal(10,2) NOT NULL,
    `kilometraje` int NOT NULL,
    `transmision` varchar(30) NOT NULL,
    `combustible` varchar(30) NOT NULL,
    `color` varchar(30) NOT NULL,
    `equipamiento1` varchar(100) NOT NULL, 
    `equipamiento2` varchar(100) NOT NULL,
    `equipamiento3` varchar(100) NOT NULL,
    `equipamiento4` varchar(100) NOT NULL,
    `imagen` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `reclamos` (
    `id_reclamo` int AUTO_INCREMENT PRIMARY KEY,
    `id_cli` int NOT NULL,
    `motivo_reclamo` varchar(30) NOT NULL,
    `placa` varchar(30) NOT NULL,
    `marca` varchar(30) NOT NULL,
    `modelo` varchar(30) NOT NULL,
    `detalle` varchar(100) NOT NULL,
    FOREIGN KEY (`id_cli`) REFERENCES `clientes`(`id_cli`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `ventas` (
    `id_venta` int AUTO_INCREMENT PRIMARY KEY,
    `fecha_venta` date NOT NULL,
    `hora_venta` time NOT NULL,
    `id_cli` int NOT NULL,
    `id_auto` int NOT NULL,
    `precio` decimal(10,2) NOT NULL,
    FOREIGN KEY (`id_cli`) REFERENCES `clientes`(`id_cli`) ON DELETE CASCADE,
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `alquiler` (
    `id_alquiler` int AUTO_INCREMENT PRIMARY KEY,
    `fecha_alquiler` date NOT NULL,
    `duracion_alquiler` time NOT NULL, 
    `id_cli` int NOT NULL,
    `id_auto` int NOT NULL,
    `precio` decimal(10,2) NOT NULL,
    FOREIGN KEY (`id_cli`) REFERENCES `clientes`(`id_cli`) ON DELETE CASCADE,
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `inventario` (
    `id_inv` int AUTO_INCREMENT PRIMARY KEY,
    `id_auto` int NOT NULL,
    `cantidad_auto` int NOT NULL,
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `facturas` (
    `id_factura` INT AUTO_INCREMENT PRIMARY KEY,
    `fecha_emision` DATE NOT NULL,
    `numero_factura` VARCHAR(30) NOT NULL UNIQUE,
    `id_cli` INT NOT NULL,
    `remitente` VARCHAR(100) NOT NULL,
    `ruc_remitente` VARCHAR(20) NOT NULL,  
    `subtotal` DECIMAL(10,2) NOT NULL, 
    `impuesto` DECIMAL(10,2) NOT NULL, 
    `total` DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (`id_cli`) REFERENCES `clientes`(`id_cli`) ON DELETE CASCADE  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `detalle_factura` (
    `id_detalle` INT AUTO_INCREMENT PRIMARY KEY,
    `id_factura` INT NOT NULL, 
    `id_auto` INT NOT NULL,
    `descripcion` TEXT NOT NULL,
    `cantidad` INT NOT NULL, 
    `precio_unitario` DECIMAL(10,2) NOT NULL, 
    `total_producto` DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (`id_factura`) REFERENCES `facturas`(`id_factura`) ON DELETE CASCADE,
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `boletas` (
    `id_boleta` INT AUTO_INCREMENT PRIMARY KEY,
    `fecha_emision` DATE NOT NULL,
    `numero_boleta` VARCHAR(30) NOT NULL UNIQUE,  
    `id_cli` INT NOT NULL,  
    `remitente` VARCHAR(100) NOT NULL,  
    `ruc_remitente` VARCHAR(20) NOT NULL,
    `subtotal` DECIMAL(10,2) NOT NULL, 
    `impuesto` DECIMAL(10,2) NOT NULL,  
    `total` DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (`id_cli`) REFERENCES `clientes`(`id_cli`) ON DELETE CASCADE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `detalle_boleta` (
    `id_detalle` INT AUTO_INCREMENT PRIMARY KEY,
    `id_boleta` INT NOT NULL,
    `id_auto` INT NOT NULL,
    `descripcion` TEXT NOT NULL,  
    `cantidad` INT NOT NULL,  
    `precio_unitario` DECIMAL(10,2) NOT NULL, 
    `total_producto` DECIMAL(10,2) NOT NULL, 
    FOREIGN KEY (`id_boleta`) REFERENCES `boletas`(`id_boleta`) ON DELETE CASCADE,
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;







