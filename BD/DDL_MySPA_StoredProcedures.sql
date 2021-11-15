USE myspa;
 
DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(	IN	var_nombre          VARCHAR(64),    -- 1
                                    IN	var_apellidoPaterno VARCHAR(64),    -- 2
                                    IN	var_apellidoMaterno VARCHAR(64),    -- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN	var_domicilio       VARCHAR(200),   -- 5
                                    IN	var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 8
                                    IN	var_contrasenia     VARCHAR(48),    -- 9
                                    IN	var_rol             VARCHAR(24),    -- 10
                                    
                                    IN	var_puesto          VARCHAR(20),    -- 11
                                    IN	var_foto            LONGTEXT,       -- 12
                                    IN	var_rutaFoto        TEXT,           -- 13
                                    
                                    OUT	var_idPersona       INT,            -- 14
                                    OUT	var_idUsuario       INT,            -- 15
                                    OUT	var_idEmpleado      INT,            -- 16
                                    OUT var_numeroEmpleado  VARCHAR(70)     -- 17
				)                                    
    BEGIN        
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             domicilio, telefono, rfc)
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                            var_genero, var_domicilio, var_telefono, var_rfc);
        SET var_idPersona = LAST_INSERT_ID();

        INSERT INTO usuario ( nombreUsuario, contrasenia, rol) 
                    VALUES( var_nombreUsuario, var_contrasenia, var_rol);
        SET var_idUsuario = LAST_INSERT_ID();

        SET var_numeroEmpleado = 'E';        
        IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroEmpleado = CONCAT(var_numeroEmpleado, SUBSTRING(var_rfc, 1, 4));
        END IF;
        SET var_numeroEmpleado = CONCAT(var_numeroEmpleado, CAST(UNIX_TIMESTAMP() AS CHAR));

        INSERT INTO empleado ( numeroEmpleado, puesto, estatus, foto, rutaFoto, 
                               idPersona, idUsuario) 
                    VALUES( var_numeroEmpleado, var_puesto, 1, var_foto, 
                            var_rutaFoto, var_idPersona, var_idUsuario);
        SET var_idEmpleado = LAST_INSERT_ID();
    END
$$
DELIMITER ;



DROP PROCEDURE IF EXISTS actualizarEmpleado;
DELIMITER $$
CREATE PROCEDURE actualizarEmpleado(IN  var_nombre          VARCHAR(64),    -- 1
                                    IN  var_apellidoPaterno VARCHAR(64),    -- 2
                                    IN  var_apellidoMaterno VARCHAR(64),    -- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN  var_domicilio       VARCHAR(200),   -- 5
                                    IN  var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 8
                                    IN	var_contrasenia     VARCHAR(48),    -- 9
                                    IN	var_rol             VARCHAR(24),    -- 10
                                    
                                    IN  var_puesto          VARCHAR(20),    -- 11
                                    IN  var_foto            LONGTEXT,       -- 12
                                    IN  var_rutaFoto        TEXT,           -- 13
                                    IN	var_idPersona       INT,            -- 14
                                    IN	var_idUsuario       INT,            -- 15
                                    IN	var_idEmpleado      INT             -- 16
                                )                                    
    BEGIN
        UPDATE persona  SET     nombre = var_nombre, 
                                apellidoPaterno = var_apellidoPaterno,
                                apellidoMaterno = var_apellidoMaterno,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono, 
                                rfc = var_rfc
                        WHERE   idPersona = var_idPersona;

        UPDATE usuario  SET     nombreUsuario = var_nombreUsuario,
                                contrasenia = var_contrasenia,
                                rol = var_rol
                        WHERE   idUsuario = var_idUsuario;

        UPDATE empleado SET     puesto = var_puesto, 
                                foto = var_foto, 
                                rutaFoto = var_rutaFoto 
                        WHERE   idEmpleado = var_idEmpleado;        
    END
$$
DELIMITER ;



DROP PROCEDURE IF EXISTS insertarCliente;
DELIMITER $$
CREATE PROCEDURE insertarCliente(   IN  var_nombre          VARCHAR(64),
                                    IN  var_apellidoPaterno VARCHAR(64),
                                    IN  var_apellidoMaterno VARCHAR(64),
                                    IN  var_genero          VARCHAR(2),
                                    IN  var_domicilio       VARCHAR(200),
                                    IN	var_telefono        VARCHAR(25),
                                    IN	var_rfc             VARCHAR(14),
                                    
                                    IN  var_nombreUsuario   VARCHAR(48),
                                    IN  var_contrasenia     VARCHAR(48),
                                    IN  var_rol             VARCHAR(24),
                                    
                                    IN  var_correo          VARCHAR(68),
                                    IN  var_numeroUnico     VARCHAR(14),
                                                                        
                                    OUT var_idPersona       INT,
                                    OUT var_idUsuario       INT,
                                    OUT var_idCliente       INT
                                )
    BEGIN
        INSERT INTO persona ( nombre, apellidoPaterno, apellidoMaterno, genero,
                              domicilio, telefono, rfc) 
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                            var_genero, var_domicilio, var_telefono, var_rfc);
        SET var_idPersona = LAST_INSERT_ID();


        INSERT INTO usuario ( nombreUsuario, contrasenia, rol) 
                    VALUES( var_nombreUsuario, var_contrasenia, var_rol);
        SET var_idUsuario = LAST_INSERT_ID();


        INSERT INTO cliente ( correo, numeroUnico, estatus, idPersona, idUsuario) 
                    VALUES( var_correo, var_numeroUnico, 1, var_idPersona, var_idUsuario);
        SET var_idCliente = LAST_INSERT_ID();
    END
$$
DELIMITER ;



DROP PROCEDURE IF EXISTS actualizarCliente;
DELIMITER $$
CREATE PROCEDURE actualizarCliente(     IN  var_nombre          VARCHAR(64),
                                        IN  var_apellidoPaterno VARCHAR(64),
                                        IN  var_apellidoMaterno VARCHAR(64),
                                        IN  var_genero          VARCHAR(2),
                                        IN  var_domicilio       VARCHAR(200),
                                        IN  var_telefono        VARCHAR(25),
                                        IN  var_rfc             VARCHAR(14),

                                        IN  var_nombreUsuario   VARCHAR(48),
                                        IN  var_contrasenia     VARCHAR(48),
                                        IN  var_rol             VARCHAR(24),

                                        IN  var_correo          VARCHAR(68),
                                        IN  var_numeroUnico     VARCHAR(11),
                                        
                                        IN var_idPersona        INT,
                                        IN var_idUsuario        INT,
                                        IN var_idCliente        INT
                                    )
    BEGIN
        UPDATE  persona SET     nombre = var_nombre, 
                                apellidoPaterno = var_apellidoPaterno,
                                apellidoMaterno = var_apellidoMaterno,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono,
                                rfc = var_rfc
                        WHERE   idPersona = var_idPersona;

        UPDATE  usuario SET     nombreUsuario = var_nombreUsuario, 
                                contrasenia = var_contrasenia,
                                rol = var_rol
                        WHERE   idUsuario = var_idUsuario;

        UPDATE  cliente SET     correo = var_correo,
                                numeroUnico = var_numeroUnico
                        WHERE   idCliente = var_idCliente;

    END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS insertarReservacion;
DELIMITER $$
CREATE PROCEDURE insertarReservacion(   IN var_fechaHoraInicio  VARCHAR(21),
                                        IN var_fechaHoraFin     VARCHAR(21),
                                        IN var_idCliente        INT,
                                        IN var_idSala           INT,
                                        OUT var_idReservacion   INT
                                )
    BEGIN
        INSERT INTO reservacion (fechaHoraInicio, fechaHoraFin, estatus, idCliente, idSala) 
                    VALUES(STR_TO_DATE(var_fechaHoraInicio, '%d/%m/%Y %H:%i:%s'), STR_TO_DATE(var_fechaHoraFin, '%d/%m/%Y %H:%i:%s'), 1, var_idCliente, var_idSala);
        SET var_idReservacion = LAST_INSERT_ID();
    END
$$
DELIMITER ;

