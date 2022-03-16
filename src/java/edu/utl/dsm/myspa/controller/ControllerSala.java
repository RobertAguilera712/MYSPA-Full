
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Sala;
import edu.utl.dsm.myspa.model.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class ControllerSala {
     public int insert(Sala s) throws Exception{
        //Definir la sentencia SQL para realizar la insercion de una sucursal en la BD
        String query = "INSERT INTO sala(nombre, descripcion, foto, estatus, idSucursal)"
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
        pstmt.setString(2, s.getDescripcion());
        pstmt.setString(3, s.getFoto());
         pstmt.setInt(4, s.getEstatus());
         pstmt.setInt(5, s.getSucursal().getId());
       

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
    
    public List<Sala> getAll(int estatus) throws Exception{
        //Definir la consulta SQL
        String query = "SELECT * FROM v_sucursales_salas WHERE estatusSala = "+estatus+"";
        
        //Generar la lista de sucursales que se va a obtener con la consulta
        List<Sala> sala = new ArrayList<Sala>();
        
        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutar la sentencia y recibir resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de las salas
        while(rs.next()){
            //Generar una variable temporal para crear nuevas instancias de sala
            Sala s = fill(rs);
            
            //Se agrega ese objeto de Sucursal a la lista de salas
            sala.add(s);
        }
        
        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de salas
        return sala;
    }
    
    /**
     * Metodo que elimina de forma lógica un registro de sala
     * @param id
     * @throws Exception 
     */
    
    public void delete(int id) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE sala SET estatus = 0 WHERE idSala="+id+";";
        
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
    
    public void update(Sala s) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE sala SET nombre = ?, descripcion = ?, foto = ?, idSucursal = ? WHERE idSala = ?;";
        //Generar el objeto de conexión y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, s.getNombre());
        pstmt.setString(2, s.getDescripcion());
        pstmt.setString(3, s.getFoto());
        pstmt.setInt(4, s.getSucursal().getId());
        pstmt.setInt(5, s.getId());
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión
        pstmt.close();
        connMySQL.close();
    }
    
    public List<Sala> search(String filter, int status) throws Exception{
        //Generar la consulta SQL
        String query = "SELECT * FROM v_sucursales_salas WHERE estatusSala = ? AND "+filter+";";
        
        //Generar la lista de sucursales que se va a obtener con la consulta
        List<Sala> sala = new ArrayList<Sala>();
        
        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, status);
        
        //Ejecutar la sentencia y recibir resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de las salas
        while(rs.next()){
            //Generar una variable temporal para crear nuevas instancias de Sucursal
            Sala s = fill(rs);
            
            //Se agrega ese objeto de Sucursal a la lista de sala
            sala.add(s);
        }
        
        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de sala
        return sala;
    }
    
    
    static Sala fill(ResultSet rs) throws Exception
    {
        //Una variable temporal para crear nuevos objetos de tipo Sucursal:
        Sucursal s = new Sucursal();
        
        //Una variable temporal para crear nuevos objetos de tipo Sala:
        Sala sa = new Sala();
        
        

        //Llenamos sus datos:
        s.setId(rs.getInt("idSucursal"));
        s.setNombre(rs.getString("nombre"));
        s.setDomicilio(rs.getString("domicilio"));
        s.setLatitud(rs.getDouble("latitud"));
        s.setLongitud(rs.getDouble("longitud"));
        s.setEstatus(rs.getInt("estatus"));

        //Creamos un nuevo objeto de tipo Sala:
        sa.setId(rs.getInt("idSala"));
        sa.setNombre(rs.getString("nombreSala"));
        sa.setDescripcion(rs.getString("descripcion"));
        sa.setFoto(rs.getString("foto"));
        sa.setEstatus(rs.getInt("estatusSala"));
        sa.setSucursal(s);

        return sa;
    }
}
