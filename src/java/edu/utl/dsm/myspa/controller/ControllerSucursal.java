/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author kasparov
 */
public class ControllerSucursal {
    
    /**
     * 
     * @param s Se recibe un parámetro de tipo Sucursal, que contenga los datos a almacenar
     * @return Devuelve el ID generado con la inserción del registro
     * @throws Exception 
     */
    
    public int insert(Sucursal s) throws Exception{
        //Definir la sentencia SQL para realizar la insercion de una sucursal en la BD
        String query = "INSERT INTO sucursal(nombre, domicilio, latitud, longitud, estatus)"
                + "VALUES(?, ?, ?, ?, ?);";
        //Se declara la variable sobre la que almacena el ID generado
        int idGenerado = -1;
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generar un objeto que lleve la consulta a la BD
        //Que me permita recibir el ID generado
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, s.getNombre());
        pstmt.setString(2, s.getDomicilio());
        pstmt.setDouble(3, s.getLatitud());
        pstmt.setDouble(4, s.getLongitud());
        pstmt.setInt(5, s.getEstatus());

        //Generar un objeto que va a guardar el resultado devuelto de la consulta
        ResultSet rs = null;
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        //Solicitar al PreparedStatement el valor que se generó
        rs = pstmt.getGeneratedKeys();
        
        //Tomar el valor generado
        if(rs.next()){
            idGenerado = rs.getInt(1);
            s.setId(idGenerado);
        }
        
        //Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos el id
        return idGenerado;
    }
    
    public List<Sucursal> getAll(int estatus) throws Exception{
        //Definir la consulta SQL
        String query = "SELECT * FROM sucursal WHERE estatus = "+estatus+"";
        
        //Generar la lista de sucursales que se va a obtener con la consulta
        List<Sucursal> sucursales = new ArrayList<Sucursal>();
        
        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutar la sentencia y recibir resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de las sucursales
        while(rs.next()){
            //Generar una variable temporal para crear nuevas instancias de Sucursal
            Sucursal s = new Sucursal();
            
            //Llenamos los atributos del objeto Sucursal con los datos del RS
            s.setId(rs.getInt("idSucursal"));
            s.setNombre(rs.getString("nombre"));
            s.setDomicilio(rs.getString("domicilio"));
            s.setLatitud(rs.getDouble("latitud"));
            s.setLongitud(rs.getDouble("Longitud"));
            s.setEstatus(rs.getInt("estatus"));
            
            //Se agrega ese objeto de Sucursal a la lista de sucursales
            sucursales.add(s);
        }
        
        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de sucursales
        return sucursales;
    }
    
    /**
     * Metodo que elimina de forma lógica un registro de sucursal
     * @param id
     * @throws Exception 
     */
    
    public void delete(int id) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE sucursal SET estatus = 0 WHERE idSucursal="+id+";";
        
        //Generar el objeto de conexión y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión
        pstmt.close();
        connMySQL.close();
    }
    
    public void update(Sucursal s) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE sucursal SET nombre = ?, domicilio = ?, latitud = ?, longitud = ?, estatus = ? WHERE idSucursal = ?;";
        
        //Generar el objeto de conexión y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, s.getNombre());
        pstmt.setString(2, s.getDomicilio());
        pstmt.setDouble(3, s.getLatitud());
        pstmt.setDouble(4, s.getLongitud());
        pstmt.setInt(5, s.getEstatus());
        pstmt.setInt(6, s.getId());
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión
        pstmt.close();
        connMySQL.close();
    }
    
    public List<Sucursal> search(String filter, int status) throws Exception{
        //Generar la consulta SQL
        String query = "SELECT * FROM sucursal WHERE estatus = ? AND "+filter+";";
        
        //Generar la lista de sucursales que se va a obtener con la consulta
        List<Sucursal> sucursales = new ArrayList<Sucursal>();
        
        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, status);
        
        //Ejecutar la sentencia y recibir resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de las sucursales
        while(rs.next()){
            //Generar una variable temporal para crear nuevas instancias de Sucursal
            Sucursal s = new Sucursal();
            
            //Llenamos los atributos del objeto Sucursal con los datos del RS
            s.setId(rs.getInt("idSucursal"));
            s.setNombre(rs.getString("nombre"));
            s.setDomicilio(rs.getString("domicilio"));
            s.setLatitud(rs.getDouble("latitud"));
            s.setLongitud(rs.getDouble("longitud"));
            s.setEstatus(rs.getInt("estatus"));
            
            //Se agrega ese objeto de Sucursal a la lista de sucursales
            sucursales.add(s);
        }
        
        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de sucursales
        return sucursales;
    }
}
