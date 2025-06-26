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

CREATE TABLE IF NOT EXISTS `alquiler_auto` (
    `id_alquiler` INT PRIMARY KEY AUTO_INCREMENT,
    `id_auto` INT NOT NULL,
    `pago_alquiler` DECIMAL(10,2),
    `disponible_alquiler` ENUM('sí', 'no') DEFAULT 'sí',
    FOREIGN KEY (`id_auto`) REFERENCES `autos`(`id_auto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO alquiler_auto (id_auto, disponible_alquiler, pago_alquiler) VALUES
(1, 'sí', 600),   -- Mercedes-Benz S-Class S580
(2, 'sí', 550),   -- BMW 7 Series 750i
(3, 'sí', 500),   -- Audi A8 L
(4, 'no', 700),   -- Porsche Panamera 4 E-Hybrid (Este auto está alquilado)
(5, 'sí', 800),   -- Tesla Model S Plaid
(6, 'no', 650),   -- Land Rover Range Rover Autobiography (Este auto está alquilado)
(7, 'sí', 750),   -- Maserati Levante Trofeo
(8, 'sí', 550);   -- Lexus LC 500h

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
    alq.pago_alquiler,
    GROUP_CONCAT(DISTINCT c.nombre_color SEPARATOR ', ') AS colores,
    GROUP_CONCAT(DISTINCT i.nombre_archivo SEPARATOR ', ') AS imagenes
FROM autos a
LEFT JOIN alquiler_auto alq ON a.id_auto = alq.id_auto
LEFT JOIN imagen_auto_color ca ON a.id_auto = ca.id_auto
LEFT JOIN colores c ON ca.id_color = c.id_color
LEFT JOIN imagen_auto_color i ON a.id_auto = i.id_auto AND ca.id_color = i.id_color
GROUP BY a.id_auto;

/*
INSERT INTO inventario (id_auto, cantidad_autos) VALUES
(1, 3),
(2, 2),
(3, 1),
(4, 4),
(5, 2);
*/

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
    estado ENUM('pendiente', 'en proceso', 'entregado') DEFAULT 'pendiente',
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

INSERT INTO pedido (id_usuario, id_auto, colorauto, fecha, estado, total) VALUES
(1, 1, 'Negro', '2025-06-20 14:30:00', 'pendiente', 115650.00),
(2, 3, 'Gris', '2025-06-21 10:15:00', 'en proceso', 98100.00),
(3, 5, 'Blanco', '2025-06-22 09:45:00', 'entregado', 130650.00),
(4, 7, 'Rojo', '2025-06-23 16:20:00', 'pendiente', 125100.00),
(5, 6, 'Negro', '2025-06-24 11:00:00', 'en proceso', 140650.00);


INSERT INTO pedido_item (id_pedido, id_acc, coloracc, precio_unitario) VALUES
(1, 1, 'Rojo', 500.00),
(1, 2, 'Negro', 150.00),
(2, 3, 'Verde', 100.00),
(3, 1, 'Azul', 500.00),
(3, 2, 'Rojo', 150.00),
(4, 3, 'Negro', 100.00),
(5, 2, 'Azul', 150.00),
(5, 1, 'Negro', 500.00);

SELECT * FROM autos;
SELECT * FROM accesorio;
SELECT * FROM pedido;
SELECT * FROM pedido_item;