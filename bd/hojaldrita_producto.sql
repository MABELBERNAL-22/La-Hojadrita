-- Base de datos y tabla Producto para el modulo web (GA7-220501096-AA2-EV02)
-- La tabla es la misma que se hizo en GA6-AA2-EV02

CREATE DATABASE IF NOT EXISTS hojaldrita_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hojaldrita_db;

CREATE TABLE IF NOT EXISTS Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio_venta DECIMAL(10,2) NOT NULL CHECK (precio_venta >= 0),
    categoria VARCHAR(20)
);

-- datos de ejemplo para probar la lista
INSERT INTO Producto (nombre, descripcion, precio_venta, categoria) VALUES
('Pan Hojaldrado', 'Pan de hojaldre recién horneado', 2000, 'Panadería'),
('Almojábana', 'Almojábana de queso', 2000, 'Panadería'),
('Jugo de Naranja', 'Jugo natural en vaso', 5000, 'Bebidas'),
('Torta Tres Leches', 'Porción de torta tres leches', 8000, 'Pastelería'),
('Pan de Bono', 'Pan de bono tradicional', 2500, 'Panadería');
