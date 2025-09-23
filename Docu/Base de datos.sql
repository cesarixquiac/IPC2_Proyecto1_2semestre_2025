
DROP DATABASE IF EXISTS gestor_congreso;
CREATE DATABASE IF NOT EXISTS gestor_congreso;
USE gestor_congreso;

CREATE TABLE usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_completo VARCHAR(100) NOT NULL,
  correo VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  telefono VARCHAR(20),
  identificacion VARCHAR(50), 
  organizacion VARCHAR(150),
  foto VARCHAR(255),
  rol ENUM('ADMIN_SISTEMA','ADMIN_CONGRESO','COMITE','PARTICIPANTE') DEFAULT 'PARTICIPANTE',
  activo BOOLEAN DEFAULT TRUE,
  saldo DECIMAL(10,2) DEFAULT 0.00
);


CREATE TABLE institucion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT
);


CREATE TABLE congreso (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(200) NOT NULL,
  descripcion TEXT NOT NULL,
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE NOT NULL,
  ubicacion VARCHAR(150) NOT NULL,
  precio DECIMAL(10,2) NOT NULL CHECK (precio >= 35.00),
  institucion_id INT,
  admin_id INT,
  FOREIGN KEY (institucion_id) REFERENCES institucion(id),
  FOREIGN KEY (admin_id) REFERENCES usuario(id)
);


CREATE TABLE salon (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  ubicacion VARCHAR(150),
  capacidad INT,
  congreso_id INT,
  FOREIGN KEY (congreso_id) REFERENCES congreso(id)
);


CREATE TABLE actividad (
  id INT AUTO_INCREMENT PRIMARY KEY,
  congreso_id INT,
  nombre VARCHAR(200) NOT NULL,
  descripcion TEXT,
  tipo ENUM('PONENCIA','TALLER') NOT NULL,
  hora_inicio DATETIME NOT NULL,
  hora_fin DATETIME NOT NULL,
  salon_id INT,
  cupo_maximo INT,
  FOREIGN KEY (congreso_id) REFERENCES congreso(id),
  FOREIGN KEY (salon_id) REFERENCES salon(id),
  CHECK (hora_inicio < hora_fin)
);


CREATE TABLE actividad_encargado (
  actividad_id INT,
  usuario_id INT,
  PRIMARY KEY (actividad_id, usuario_id),
  FOREIGN KEY (actividad_id) REFERENCES actividad(id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);


CREATE TABLE inscripcion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT,
  congreso_id INT,
  fecha DATE NOT NULL,
  estado ENUM('PENDIENTE','VALIDADA') DEFAULT 'PENDIENTE',
  UNIQUE (usuario_id, congreso_id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (congreso_id) REFERENCES congreso(id)
);


CREATE TABLE pago (
  id INT AUTO_INCREMENT PRIMARY KEY,
  inscripcion_id INT,
  monto DECIMAL(10,2) NOT NULL,
  comision DECIMAL(10,2) NOT NULL,
  fecha DATE NOT NULL,
  FOREIGN KEY (inscripcion_id) REFERENCES inscripcion(id)
);


CREATE TABLE propuesta (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT,
  congreso_id INT,
  titulo VARCHAR(200),
  descripcion TEXT,
  tipo ENUM('PONENCIA','TALLER'),
  estado ENUM('EN_REVISION','APROBADO','RECHAZADO') DEFAULT 'EN_REVISION',
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (congreso_id) REFERENCES congreso(id)
);


CREATE TABLE reserva_taller (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT,
  actividad_id INT,
  fecha DATE NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (actividad_id) REFERENCES actividad(id),
  UNIQUE (usuario_id, actividad_id)
);


CREATE TABLE asistencia (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT,
  actividad_id INT,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (actividad_id) REFERENCES actividad(id)
);

CREATE TABLE certificado (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT,
  congreso_id INT,
  actividad_id INT NULL,
  fecha DATE NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (congreso_id) REFERENCES congreso(id),
  FOREIGN KEY (actividad_id) REFERENCES actividad(id)
);


INSERT INTO usuario (nombre_completo, correo, password, rol, activo, saldo)
VALUES ('Administrador Principal', 'admin@congreso.com', 'admin123', 'ADMIN_SISTEMA', TRUE, 0.00);


INSERT INTO institucion (nombre, descripcion) 
VALUES ('Universidad San Carlos de Guatemala', 'Institución universitaria organizadora de congresos');
