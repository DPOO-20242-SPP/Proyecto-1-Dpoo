USE evento_venue;
select * from usuario;

-- VENUE
CREATE TABLE venue (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    direccion VARCHAR(255)
);

-- USUARIO
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    tipo ENUM('Cliente','Organizador','Administrador') NOT NULL
);

-- EVENTO
CREATE TABLE evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha DATE NOT NULL,
    organizador_id INT NOT NULL,
    venue_id INT NOT NULL,
    FOREIGN KEY (organizador_id) REFERENCES usuario(id),
    FOREIGN KEY (venue_id) REFERENCES venue(id)
);

-- LOCALIDAD (cada localidad tiene capacidad y precio unitario)
CREATE TABLE localidad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    capacidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    evento_id INT NOT NULL,
    FOREIGN KEY (evento_id) REFERENCES evento(id)
);

-- TIQUETE
CREATE TABLE tiquete (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Normal','Multiple','Deluxe') NOT NULL,
    codigo VARCHAR(100) NOT NULL UNIQUE,
    localidad_id INT NOT NULL,
    asiento_num INT NULL, -- si la localidad es numerada, opcional
    paquete_id INT NULL,  -- para tiquetes múltiples que comparten paquete
    vendido BOOLEAN DEFAULT FALSE,
    propietario_id INT NULL, -- usuario que actualmente posee (null = sin vender)
    FOREIGN KEY (localidad_id) REFERENCES localidad(id),
    FOREIGN KEY (propietario_id) REFERENCES usuario(id)
);

-- TRANSACCION
CREATE TABLE transaccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    tiquete_id INT NOT NULL,
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    monto DECIMAL(10,2) NOT NULL,
    es_reserva_organiz BOOLEAN DEFAULT FALSE, -- si el comprador es organizador del evento (no genera ingresos)
    FOREIGN KEY (cliente_id) REFERENCES usuario(id),
    FOREIGN KEY (tiquete_id) REFERENCES tiquete(id)
);