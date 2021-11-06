DROP DATABASE IF EXISTS myspa;

CREATE DATABASE myspa;

USE myspa;

CREATE TABLE persona
(
    idPersona           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre              VARCHAR(64) NOT NULL DEFAULT '',
    apellidoPaterno     VARCHAR(64) NOT NULL DEFAULT '',
    apellidoMaterno     VARCHAR(64) NOT NULL DEFAULT '',
    genero              VARCHAR(2)  NOT NULL DEFAULT 'O',
    domicilio           VARCHAR(200) NOT NULL DEFAULT '',
    telefono            VARCHAR(25) NOT NULL DEFAULT '',
    rfc                 VARCHAR(14) NOT NULL DEFAULT ''
);

CREATE TABLE usuario
(
    idUsuario           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreUsuario       VARCHAR(48) NOT NULL,
    contrasenia         VARCHAR(48) NOT NULL DEFAULT '',
    token               TEXT,
    rol                 VARCHAR(24) NOT NULL DEFAULT ''
);

CREATE TABLE empleado
(
    idEmpleado          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    numeroEmpleado      VARCHAR(65),
    puesto              VARCHAR(20),
    estatus             INT NOT NULL DEFAULT 1, 
    foto                LONGTEXT,
    rutaFoto            TEXT,
    idPersona           INT NOT NULL,
    idUsuario           INT NOT NULL,
    CONSTRAINT  fk_empleado_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_empleado_usuario  FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cliente
(
    idCliente       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    numeroUnico     VARCHAR(70) NOT NULL,
    correo          VARCHAR(200) NOT NULL DEFAULT '',
    estatus         INT NOT NULL DEFAULT 1,
    idPersona       INT NOT NULL,
    idUsuario       INT NOT NULL,
    CONSTRAINT  fk_cliente_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_cliente_usuario  FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE	
);

CREATE TABLE producto
(
    idProducto      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(255) NOT NULL DEFAULT '',
    marca           VARCHAR(255) NOT NULL DEFAULT '',
    estatus         INT NOT NULL DEFAULT 1,
    precioUso       FLOAT NOT NULL DEFAULT 0.0 
);

CREATE TABLE tratamiento
(
    idTratamiento   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(65) NOT NULL DEFAULT '',
    descripcion     TEXT NOT NULL,
    costo           FLOAT NOT NULL DEFAULT 0,
    estatus         INT NOT NULL DEFAULT 1
);

CREATE TABLE sucursal
(
    idSucursal      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(129) NOT NULL DEFAULT '',
    domicilio       VARCHAR(255),
    latitud         DOUBLE NOT NULL DEFAULT 0.0,
    longitud        DOUBLE NOT NULL DEFAULT 0.0,
    estatus         INT NOT NULL DEFAULT 1    
);

CREATE TABLE sala
(
    idSala          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(129) NOT NULL DEFAULT '',
    descripcion     TEXT,
    foto            LONGTEXT,
    rutaFoto        TEXT,
    estatus         INT NOT NULL DEFAULT 1,
    idSucursal      INT NOT NULL,
    CONSTRAINT  fk_sala_sucursal  FOREIGN KEY (idSucursal) 
                REFERENCES sucursal(idSucursal) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE horario
(
    idHorario       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    horaInicio      VARCHAR(10),
    horaFin         VARCHAR(10)
);

CREATE TABLE sala_horario
(
    idSala      INT NOT NULL,
    idHorario   INT NOT NULL,
    CONSTRAINT  fk_salahorario_sala      FOREIGN KEY (idSala) 
                REFERENCES sala(idSala) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_salahorario_horario   FOREIGN KEY (idHorario) 
                REFERENCES horario(idHorario) ON DELETE CASCADE ON UPDATE CASCADE	
);

CREATE TABLE reservacion
(
    idReservacion   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fechaHoraInicio DATETIME,
    fechaHoraFin    DATETIME,
    estatus         INT,
    idCliente       INT NOT NULL,
    idSala          INT NOT NULL,
    CONSTRAINT  fk_reservacion_cliente   FOREIGN KEY (idCliente)
                REFERENCES cliente(idCliente) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_reservacion_sala      FOREIGN KEY (idSala) 
                REFERENCES sala(idSala) ON DELETE CASCADE ON UPDATE CASCADE    
);

CREATE TABLE servicio
(
    idServicio      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fecha           DATETIME NOT NULL,
    idReservacion   INT NOT NULL,
    idEmpleado      INT NOT NULL,
    CONSTRAINT  fk_servicio_reservacion  FOREIGN KEY (idReservacion) 
                REFERENCES reservacion(idReservacion) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_servicio_empleado  FOREIGN KEY (idEmpleado) 
                REFERENCES empleado(idEmpleado) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE servicio_tratamiento
(
    idServicioTratamiento   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idTratamiento           INT NOT NULL,
    idServicio              INT NOT NULL,
    CONSTRAINT  fk_serviciotratamiento_tratamiento  FOREIGN KEY (idTratamiento) 
                REFERENCES tratamiento(idTratamiento) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_tratamientoservicio_servicio  FOREIGN KEY (idServicio) 
                REFERENCES servicio(idServicio) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE servicio_tratamiento_producto
(
    idServicioTratamiento       INT NOT NULL,
    idProducto                  INT NOT NULL,
    precioUso                   FLOAT NOT NULL DEFAULT 0.0,
    CONSTRAINT  fk_serviciotratamientoproducto_serviciotratamiento  FOREIGN KEY (idServicioTratamiento) 
                REFERENCES servicio_tratamiento (idServicioTratamiento) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_serviciotratamientoproducto_producto  FOREIGN KEY (idProducto) 
                REFERENCES producto(idProducto) ON DELETE CASCADE ON UPDATE CASCADE
);
