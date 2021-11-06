package edu.utl.dsm.myspa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    
    Connection conexion;
    
    
    public Connection open() throws Exception{
        //Establecer el driver con el que vamos a trabajar
        String driver = "com.mysql.cj.jdbc.Driver";
        //Establecer la ruta de conexión
        String url = "jdbc:mysql://127.0.0.1:3306/myspa";
        //Establecer los valores para los permisos de acceso
        String user = "root";
        String password = "12345";
        
        //Damos de alta el uso del driver
        Class.forName(driver);
        
        //Abrimos la conexión
        conexion = DriverManager.getConnection(url, user, password);
        //Retornamos la conexión que se ha creado y abierto
        return conexion;
    }  
    
    /**
     * Este metodo es para cerrar una conexión a BD de MySQL que este abierta
     */
    
    public void close(){
        try{
            if(conexion != null){
            conexion.close();
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
