/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Horario;
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
public class ControllerHorario {
    
    /**
     * 
     * @param s Se recibe un parámetro de tipo Sucursal, que contenga los datos a almacenar
     * @return Devuelve el ID generado con la inserción del registro
     * @throws Exception 
     */
    
    public int insert(Horario s) throws Exception{
        //Definir la sentencia SQL para realizar la insercion de una sucursal en la BD
        String query = "INSERT INTO horario (horaInicio, horaFin, estatus)"
                + "VALUES(?, ?, ?);";
        //Se declara la variable sobre la que almacena el ID generado
        int idGenerado = -1;
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generar un objeto que lleve la consulta a la BD
        //Que me permita recibir el ID generado
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, s.getHoraI());
        pstmt.setString(2, s.getHoraF());
        pstmt.setInt(3, s.getEstatus());
      

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
    
    public List<Horario> getAll(int estatus) throws Exception{
        //Definir la consulta SQL
        String query = "SELECT * FROM horario WHERE estatus = "+estatus+"";
        
        //Generar la lista de sucursales que se va a obtener con la consulta
        List<Horario> horarios = new ArrayList<Horario>();
        
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
            Horario s = new Horario();
            
            //Llenamos los atributos del objeto Sucursal con los datos del RS
            s.setId(rs.getInt("idHorario"));
            s.setHoraI(rs.getString("horaInicio"));
            s.setHoraF(rs.getString("horaFin"));
			s.setEstatus(rs.getInt("estatus"));
 
            
            //Se agrega ese objeto de Sucursal a la lista de sucursales
            horarios.add(s);
        }
        
        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de sucursales
        return horarios;
    }
    
    /**
     * Metodo que elimina de forma lógica un registro de sucursal
     * @param id
     * @throws Exception 
     */
    
    public void delete(int id) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE horario SET estatus = 0 WHERE idHorario="+id+";";
        
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
    
    public void update(Horario s) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE horario SET horaInicio = ?, horaFin = ? WHERE idHorario = ?;";
        
        //Generar el objeto de conexión y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, s.getHoraI());
        pstmt.setString(2, s.getHoraF());
        pstmt.setInt(3, s.getId());
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión
        pstmt.close();
        connMySQL.close();
    }
    
    public List<Horario> search(String filter, int status) throws Exception{
        //Generar la consulta SQL
        String query = "SELECT * FROM horario WHERE estatus = ? AND "+filter+";";
        
        //Generar la lista de sucursales que se va a obtener con la consulta
        List<Horario> Horarios = new ArrayList<Horario>();
        
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
            Horario s = new Horario();
            
            //Llenamos los atributos del objeto Sucursal con los datos del RS
            s.setId(rs.getInt("idHorario"));
            s.setHoraI(rs.getString("horaInicio"));
            s.setHoraF(rs.getString("horaFin"));
			s.setEstatus(rs.getInt("estatus"));
            
            //Se agrega ese objeto de Sucursal a la lista de sucursales
            Horarios.add(s);
        }
        
        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de sucursales
        return Horarios;
    }
}
