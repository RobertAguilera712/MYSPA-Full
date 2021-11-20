
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ControllerLogin {
   
    public Empleado login(String nombreU, String contra)throws Exception{
        //Definir la consulta que se va a ejecutar
        String query = "SELECT * FROM v_empleados WHERE nombreUsuario=? AND contrasenia=? AND estatus=1;";
        //Generar el objeto de la conexion
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrir Conexión
        Connection conn = connMySQL.open();
        //Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        //Llenar los parametros de la consulta
        pstmt.setString(1, nombreU);
        pstmt.setString(2, contra);
        //Objeto para recibir los datos de la consulta
        ResultSet rs = pstmt.executeQuery();
        //objeto de tipo empleado
        Empleado e = null;
        if(rs.next()){
            e=fill(rs);
        }
        //cerrar los objetos de uso para la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        //devuelve el objeto de tipo empleado
        return e;
    }

    private Empleado fill(ResultSet rs) throws Exception{
    
    //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = new Empleado();
        
        //Una variable temporal para crear nuevos objetos de tipo Persona:
        Persona p = new Persona();
        
        //Una variable temporal para crear nuevos objetos de tipo Usuario:
        Usuario u = new Usuario();
        

        //Llenamos sus datos:
        p.setApellidoM(rs.getString("apellidoMaterno"));
        p.setApellidoP(rs.getString("apellidoPaterno"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setGenero(rs.getString("genero"));
        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setRfc(rs.getString("rfc"));
        p.setTelefono(rs.getString("telefono"));

        //Creamos un nuevo objeto de tipo Usuario:
        u.setContrasenia(rs.getString("contrasenia"));
        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsu(rs.getString("nombreUsuario"));
        u.setRol(rs.getString("rol"));

        //Establecemos sus datos personales:
        e.setFoto(rs.getString("foto"));
        e.setId(rs.getInt("idEmpleado"));
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));           
        e.setPuesto(rs.getString("puesto"));
        e.setRutaFoto(rs.getString("rutaFoto"));
        e.setEstatus(rs.getInt("estatus"));

        //Establecemos su persona:
        e.setPersona(p);

        //Establecemos su Usuario:
        e.setUsuario(u);
        
        return e;
    }
}
