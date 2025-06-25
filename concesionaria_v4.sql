SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP DATABASE concesionaria_v4;

CREATE DATABASE IF NOT EXISTS concesionaria_v4;
USE concesionaria_v4;

CREATE TABLE roles (
    id_rol INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (nombre) VALUES 
('ROLE_USER'),
('ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(30) NOT NULL,
    apellidos_usuario VARCHAR(30) NOT NULL,
    dni CHAR(8) NOT NULL UNIQUE,
    fecha_nac DATE NOT NULL,
    celular CHAR(9) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    pass VARCHAR(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO usuario (nombre_usuario, apellidos_usuario, dni, fecha_nac, celular, email, usuario, pass) VALUES 
('Juan', 'Pérez Gómez', '12345678', '1990-04-15', '987654321', 'juan.perez@gmail.com', 'Juanp', 'pass1234'),
('Ana', 'Rodríguez Soto', '87654321', '1985-09-30', '912345678', 'ana.rs@gmail.com', 'Anar', 'clave2023'),
('Luis', 'Martínez Ruiz', '11223344', '1992-01-25', '900111222', 'luis.mr@gmail.com', 'Luism', 'luispass'),
('María', 'García López', '33445566', '1995-06-20', '911222333', 'maria.gl@gmail.com', 'Mariagl', 'maria2025'),
('Carlos', 'Fernández Díaz', '55667788', '1988-12-10', '922333444', 'carlos.fd@gmail.com', 'Carlosf', 'carlosclave'),
('Admin', 'Principal', '99999999', '1990-01-01', '999997800', 'admin@admin.com', 'Admin', 'admin123');

CREATE TABLE usuario_roles (
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE CASCADE
);

INSERT INTO usuario_roles (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuario u
JOIN roles r ON r.nombre = 'ROLE_USER'
WHERE u.email != 'admin@admin.com';

INSERT INTO usuario_roles (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuario u
JOIN roles r ON r.nombre = 'ROLE_ADMIN'
WHERE u.email = 'admin@admin.com';


CREATE TABLE IF NOT EXISTS `autos` (
    `id_auto` INT PRIMARY KEY AUTO_INCREMENT,
    `modelo` VARCHAR(50),
    `marca` VARCHAR(50),
    `ano` INT,
    `precio` DECIMAL(10,2),
    `kilometraje` INT,
    `transmision` VARCHAR(20),
    `combustible` VARCHAR(20),
    `equipamiento1` VARCHAR(50),
    `equipamiento2` VARCHAR(50),
    `equipamiento3` VARCHAR(50),
    `equipamiento4` VARCHAR(50),
    `categoria` VARCHAR(30),
    `estado` VARCHAR(30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO autos (modelo, marca, ano, precio, kilometraje, transmision, combustible,equipamiento1, equipamiento2, equipamiento3, equipamiento4, categoria, estado) VALUES
	('S-Class S580', 'Mercedes-Benz', 2022, 115000, 18000, 'Automática', 'Gasolina', 'Asientos de piel', 'Cámara 360', 'Piloto automático', 'Sonido Burmester', 'Sedán', 'Disponible'),
	('7 Series 750i', 'BMW', 2021, 105000, 25000, 'Automática', 'Gasolina', 'Pantalla táctil', 'Asistente de parqueo', 'Asientos ventilados', 'Head-Up Display', 'Sedán', 'Disponible'),
	('A8 L', 'Audi', 2022, 98000, 22000, 'Automática', 'Gasolina', 'Suspensión adaptativa', 'Sonido Bang & Olufsen', 'Control crucero adaptativo', 'Asientos masajeadores', 'Sedán', 'Disponible'),
	('Panamera 4 E-Hybrid', 'Porsche', 2021, 120000, 20000, 'Automática', 'Híbrido', 'Techo panorámico', 'Sistema BOSE', 'Navegación premium', 'Cámara de reversa', 'Deportivo', 'Disponible'),
	('Model S Plaid', 'Tesla', 2023, 130000, 12000, 'Automática', 'Eléctrico', 'Piloto automático', 'Pantalla táctil', 'Cámara 360', 'Modo Ludicrous', 'Sedán', 'Disponible'),
	('Range Rover Autobiography', 'Land Rover', 2022, 140000, 17000, 'Automática', 'Gasolina', 'Interior de lujo', 'Suspensión neumática', 'Sistema Meridian', 'Cámara 360', 'SUV', 'Disponible'),
	('Levante Trofeo', 'Maserati', 2020, 125000, 30000, 'Automática', 'Gasolina', 'Motor V8', 'Sonido Harman Kardon', 'Alerón deportivo', 'Asientos deportivos', 'SUV', 'Disponible'),
	('LC 500h', 'Lexus', 2022, 97000, 19000, 'Automática', 'Híbrido', 'Diseño coupé', 'Asistente de carril', 'Sonido Mark Levinson', 'Head-Up Display', 'Coupé', 'Disponible');
SELECT * FROM autos;

CREATE TABLE IF NOT EXISTS `alquiler_auto` (
    `id_alquiler` INT PRIMARY KEY AUTO_INCREMENT,
    `id_auto` INT NOT NULL,
    `disponible_alquiler` ENUM('sí', 'no') DEFAULT 'sí',
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO alquiler_auto (id_auto, disponible_alquiler) VALUES
(1, 'sí'),
(2, 'sí'),
(3, 'sí'),
(4, 'no'),  -- Este auto está alquilado
(5, 'sí'),
(6, 'no'),  -- Este también está alquilado
(7, 'sí'),
(8, 'sí');

ALTER TABLE autos DROP COLUMN imagen;

CREATE TABLE IF NOT EXISTS `colores`	 (
    `id_color` INT PRIMARY KEY AUTO_INCREMENT,
    `nombre_color` VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO colores (nombre_color) VALUES 
('Rojo'),
('Azul'),
('Negro'),
('Blanco'),
('Gris'),
('Verde');

/*
CREATE TABLE IF NOT EXISTS `color_auto` (
    `id_auto` INT NOT NULL,
    `id_color` INT NOT NULL,
    PRIMARY KEY (`id_auto`, `id_color`),
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE,
    FOREIGN KEY (`id_color`) REFERENCES `colores`(`id_color`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- El auto con id 1 tendrá los colores Negro (3), Gris (5), y Blanco (4)
INSERT INTO color_auto (id_auto, id_color) VALUES
-- S-Class
(1, 3), -- Negro
(1, 5), -- Gris
(1, 4), -- Blanco

-- BMW 7 Series
(2, 3), -- Negro
(2, 2), -- Azul
(2, 5), -- Gris

-- Audi A8
(3, 4), -- Blanco
(3, 5), -- Gris
(3, 6), -- Verde

-- Porsche Panamera
(4, 1), -- Rojo
(4, 3), -- Negro
(4, 5), -- Gris
(4, 2), -- Azul

-- Tesla Model S
(5, 3), -- Negro
(5, 4), -- Blanco

-- Range Rover
(6, 2), -- Azul
(6, 5), -- Gris
(6, 4), -- Blanco
(6, 3), -- Negro

-- Maserati Levante
(7, 1), -- Rojo
(7, 3), -- Negro
(7, 5), -- Gris

-- Lexus LC 500h
(8, 2), -- Azul
(8, 4), -- Blanco
(8, 6); -- Verde


select * from color_auto;

-- Supongamos que el auto tiene id 1 y el color es rojo con id 1
INSERT INTO color_auto (id_auto, id_color) VALUES (1, 1);

-- Eliminación de la relación entre el auto con ID 1 y el color rojo id 1
DELETE FROM color_auto WHERE id_auto = 1 AND id_color = 1;

SELECT 
    a.modelo,
    c.nombre_color
FROM autos a
JOIN color_auto ca ON a.id_auto = ca.id_auto
JOIN colores c ON ca.id_color = c.id_color
WHERE a.id_auto = 1;
*/

CREATE TABLE IF NOT EXISTS `imagen_auto_color` (
    `id_imagen` INT PRIMARY KEY AUTO_INCREMENT,
    `id_auto` INT NOT NULL,
    `id_color` INT NOT NULL,
    `nombre_archivo` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE,
    FOREIGN KEY (`id_color`) REFERENCES `colores`(`id_color`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 
drop table imagen_auto_color;


INSERT INTO imagen_auto_color (id_auto, id_color, nombre_archivo) VALUES
-- S-Class
(1, 3, 'SClass_negro.jpg'),
(1, 5, 'SClass_gris.jpg'),
(1, 4, 'SClass_blanco.jpg'),

-- BMW 7 Series
(2, 3, 'BMW7_negro.jpg'),
(2, 2, 'BMW7_azul.jpg'),
(2, 5, 'BMW7_gris.jpg'),

-- Audi A8
(3, 4, 'A8_blanco.jpg'),
(3, 5, 'A8_gris.jpg'),
(3, 6, 'A8_verde.jpg'),

-- Porsche Panamera
(4, 1, 'Panamera_rojo.jpg'),
(4, 3, 'Panamera_negro.jpg'),
(4, 5, 'Panamera_gris.jpg'),
(4, 2, 'Panamera_azul.jpg'),

-- Tesla Model S
(5, 3, 'ModelS_negro.jpg'),
(5, 4, 'ModelS_blanco.jpg'),

-- Range Rover
(6, 2, 'RangeRover_azul.jpg'),
(6, 5, 'RangeRover_gris.jpg'),
(6, 4, 'RangeRover_blanco.jpg'),
(6, 3, 'RangeRover_negro.jpg'),

-- Maserati Levante
(7, 1, 'Levante_rojo.jpg'),
(7, 3, 'Levante_negro.jpg'),
(7, 5, 'Levante_gris.jpg'),

-- Lexus LC 500h
(8, 2, 'LC500h_azul.jpg'),
(8, 4, 'LC500h_blanco.jpg'),
(8, 6, 'LC500h_verde.jpg');

SELECT 
    a.id_auto,
    a.modelo,
    a.marca,
    a.ano,
    a.precio,
    a.kilometraje,
    a.transmision,
    a.combustible,
    a.equipamiento1,
    a.equipamiento2,
    a.equipamiento3,
    a.equipamiento4,
    a.categoria,
    a.estado,
    alq.disponible_alquiler,
    GROUP_CONCAT(DISTINCT c.nombre_color SEPARATOR ', ') AS colores,
    GROUP_CONCAT(DISTINCT i.nombre_archivo SEPARATOR ', ') AS imagenes
FROM autos a
LEFT JOIN alquiler_auto alq ON a.id_auto = alq.id_auto
LEFT JOIN imagen_auto_color ca ON a.id_auto = ca.id_auto
LEFT JOIN colores c ON ca.id_color = c.id_color
LEFT JOIN imagen_auto_color i ON a.id_auto = i.id_auto AND ca.id_color = i.id_color
GROUP BY a.id_auto;


INSERT INTO inventario (id_auto, cantidad_autos) VALUES
(1, 3),
(2, 2),
(3, 1),
(4, 4),
(5, 2);

select*from autos;

select*from colores;

select*from color_auto;

select*from imagen_auto_color;

CREATE TABLE IF NOT EXISTS reclamo (
    id_reclamo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_incidente DATE NOT NULL,
    motivo_reclamo VARCHAR(100) NOT NULL,
    tipo_vehiculo ENUM('nuevo', 'alquilado') NOT NULL,
    detalle TEXT NOT NULL,
    estado ENUM('pendiente', 'en_proceso', 'resuelto') DEFAULT 'pendiente',
    fecha_reclamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci; 


INSERT INTO reclamo (id_usuario, fecha_incidente, motivo_reclamo, tipo_vehiculo, detalle, estado)
VALUES
(1, '2025-05-20', 'Fallo en el sistema de frenos', 'nuevo', 'El vehículo presentó un fallo en el sistema de frenos a los 5 días de uso.', 'pendiente'),
(2, '2025-05-15', 'Retraso en la entrega', 'alquilado', 'El vehículo alquilado no fue entregado en el horario pactado.', 'en_proceso'),
(3, '2025-05-18', 'Problemas con el aire acondicionado', 'nuevo', 'El aire acondicionado dejó de funcionar a los pocos minutos de encenderlo.', 'resuelto'),
(1, '2025-05-25', 'Documento incompleto', 'alquilado', 'No se entregó el contrato completo al momento del alquiler.', 'pendiente'),
(2, '2025-05-28', 'Luces defectuosas', 'nuevo', 'Las luces delanteras parpadean de forma intermitente.', 'pendiente');




CREATE TABLE venta (
    id_venta INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    precio DECIMAL(10,2) NOT NULL,
    id_usuario INT NOT NULL,
    id_auto INT NOT NULL,
    tipo_comprobante VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_auto) REFERENCES autos(id_auto)
);

INSERT INTO venta (precio, id_usuario, id_auto, tipo_comprobante) VALUES
(15000.00, 1, 1, 'Boleta'),
(16000.00, 2, 2, 'Factura'),
(35000.00, 3, 3, 'Boleta'),
(22000.00, 4, 4, 'Factura'),
(27000.00, 5, 5, 'Boleta');

CREATE TABLE boleta (
    id_boleta INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_emision DATE NOT NULL,
    numero_boleta VARCHAR(50) NOT NULL,
    id_venta INT NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta)
);

INSERT INTO boleta (fecha_emision, numero_boleta, id_venta) VALUES
('2025-01-15', 'B001-0001', 1),
('2025-01-18', 'B001-0002', 3),
('2025-02-01', 'B001-0003', 5),
('2025-02-10', 'B001-0004', 4),
('2025-03-05', 'B001-0005', 2);

CREATE TABLE detalle_boleta (
    id_detalle_boleta INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_boleta INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    igv DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_boleta) REFERENCES boleta(id_boleta)
);

INSERT INTO detalle_boleta (id_boleta, cantidad, precio_unitario, subtotal, igv, total) VALUES
(1, 1, 15000.00, 15000.00, 2700.00, 17700.00),
(2, 1, 35000.00, 35000.00, 6300.00, 41300.00),
(3, 1, 27000.00, 27000.00, 4860.00, 31860.00),
(4, 1, 22000.00, 22000.00, 3960.00, 25960.00),
(5, 1, 16000.00, 16000.00, 2880.00, 18880.00);

CREATE TABLE factura (
    id_factura INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_emision DATE NOT NULL,
    numero_factura VARCHAR(50) NOT NULL,
    ruc VARCHAR(20) NOT NULL,
    id_venta INT NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta)
);

INSERT INTO factura (fecha_emision, numero_factura, ruc, id_venta) VALUES
('2025-01-20', 'F001-0001', '20123456789', 2),
('2025-01-25', 'F001-0002', '20123456789', 4),
('2025-02-15', 'F001-0003', '20987654321', 1),
('2025-03-01', 'F001-0004', '20987654321', 3),
('2025-03-10', 'F001-0005', '20345678901', 5);

CREATE TABLE detalle_factura (
    id_detalle_factura INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_factura INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura)
);

INSERT INTO detalle_factura (id_factura, cantidad, precio_unitario, subtotal, total) VALUES
(1, 1, 16000.00, 16000.00, 18880.00),
(2, 1, 22000.00, 22000.00, 25960.00),
(3, 1, 15000.00, 15000.00, 17700.00),
(4, 1, 35000.00, 35000.00, 41300.00),
(5, 1, 27000.00, 27000.00, 31860.00);

CREATE TABLE alquiler (
    id_alquiler INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    precio DECIMAL(10,2) NOT NULL,
    id_usuario INT NOT NULL,
    id_auto INT NOT NULL,
    tiempo_alquilado INT NOT NULL,
    tipo_comprobante VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_auto) REFERENCES autos(id_auto)
);

INSERT INTO alquiler (precio, id_usuario, id_auto, tiempo_alquilado, tipo_comprobante) VALUES
(500.00, 1, 2, 3, 'Boleta'),
(800.00, 2, 3, 2, 'Factura'),
(1200.00, 3, 4, 5, 'Boleta'),
(1000.00, 4, 5, 4, 'Factura'),
(600.00, 5, 1, 2, 'Boleta');

CREATE TABLE boleta_alquiler (
    id_boleta_alquiler INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_emision DATE NOT NULL,
    numero_boleta_alquiler VARCHAR(50) NOT NULL,
    id_alquiler INT NOT NULL,
    FOREIGN KEY (id_alquiler) REFERENCES alquiler(id_alquiler)
);

INSERT INTO boleta_alquiler (fecha_emision, numero_boleta_alquiler, id_alquiler) VALUES
('2025-03-15', 'BA001-0001', 1),
('2025-03-16', 'BA001-0002', 3),
('2025-03-17', 'BA001-0003', 5),
('2025-03-18', 'BA001-0004', 4),
('2025-03-19', 'BA001-0005', 2);

CREATE TABLE detalle_boleta_alquiler (
    id_detalle_boleta_alquiler INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_boleta_alquiler INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    igv DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_boleta_alquiler) REFERENCES boleta_alquiler(id_boleta_alquiler)
);

INSERT INTO detalle_boleta_alquiler (id_boleta_alquiler, cantidad, precio_unitario, subtotal, igv, total) VALUES
(1, 1, 500.00, 500.00, 90.00, 590.00),
(2, 1, 1200.00, 1200.00, 216.00, 1416.00),
(3, 1, 600.00, 600.00, 108.00, 708.00),
(4, 1, 1000.00, 1000.00, 180.00, 1180.00),
(5, 1, 800.00, 800.00, 144.00, 944.00);

CREATE TABLE accesorio (
    id_acc BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    imagen VARCHAR(1000),
    colores VARCHAR(255),
    precio DOUBLE
);

INSERT INTO accesorio (nombre, descripcion, imagen, colores, precio) VALUES
('Asiento Deportivo', 'Funda Premium para asiento', 'asiento-rojo.jpg,asiento-negro.jpg,asiento-azul.jpg', 'Rojo,Negro,Azul', 500),
('Volante Deportivo', 'Cubre volante premium', 'volante-rojo.jpg,volante-negro.jpg,volante-azul.jpg', 'Rojo,Negro,Azul', 150),
('Palanca de Cambio Deportiva', 'Diseño premium', 'palanca-roja.jpg,palanca-negra.jpg,palanca-verde.jpg', 'Rojo,Negro,Verde', 100);

SELECT * FROM ACCESORIO;

CREATE TABLE IF NOT EXISTS pedido (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_auto INT NOT NULL,
    colorauto VARCHAR(30) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_auto) REFERENCES autos(id_auto) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS pedido_item (
    id_item BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_acc BIGINT NOT NULL,
    coloracc VARCHAR(30) NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_acc) REFERENCES accesorio(id_acc) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

select*from pedido;