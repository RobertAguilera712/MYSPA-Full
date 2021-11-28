package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Sucursal;
import edu.utl.dsm.myspa.model.Tratamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase contiene los métodos necesarios para mantener la persistencia y
 * consulta de informacion de los tratamientos en la base de datos.
 *
 * @author Carlos Uriel Vargas López
 */
public class ControllerTratamiento {

    /**
     * Inserta un registro de {@link Tratamiento} en la base de datos.
     *
     * @param t Se recibe un parámetro de tipo Tratamiento, que contenga los
     * datos a almacenar
     * @return Devuelve el ID generado con la inserción del registro
     * @throws Exception
     */
    public int insert(Tratamiento t) throws Exception {
        //Definir la sentencia SQL para realizar la insercion de un tratamiento en la BD
        String query = "INSERT INTO tratamiento(nombre, descripcion, costo, estatus)"
                + "VALUES(?, ?, ?, ?);";
        //Se declara la variable sobre la que almacena el ID generado
        int idGenerado = -1;
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();

        //Generar un objeto que lleve la consulta a la BD
        //Que me permita recibir el ID generado
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, t.getNombre());
        pstmt.setString(2, t.getDescripcion());
        pstmt.setDouble(3, t.getCosto());
        pstmt.setInt(4, t.getEstatus());

        //Generar un objeto que va a guardar el resultado devuelto de la consulta
        ResultSet rs = null;

        //Ejecutamos la consulta
        pstmt.executeUpdate();
        //Solicitar al PreparedStatement el valor que se generó
        rs = pstmt.getGeneratedKeys();

        //Tomar el valor generado
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            t.setId(idGenerado);
        }

        //Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Devolvemos el id
        return idGenerado;
    }

    public List<Tratamiento> getAll(int estatus) throws Exception {
        //Definir la consulta SQL
        String query = "SELECT * FROM tratamiento WHERE estatus = " + estatus + "";

        //Generar la lista de tratamientos que se va a obtener con la consulta
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();

        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();

        //Generamos el objeto que nos permite enviar y ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);

        //Ejecutar la sentencia y recibir resultado
        ResultSet rs = pstmt.executeQuery();

        //Recorremos el RS para sacar los datos de las sucursales
        while (rs.next()) {
            //Generar una variable temporal para crear nuevas instancias de Tratamiento
            Tratamiento t = new Tratamiento();

            //Llenamos los atributos del objeto Tratamiento con los datos del RS
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getDouble("costo"));
            t.setEstatus(rs.getInt("estatus"));

            //Se agrega ese objeto de Sucursal a la lista de sucursales
            tratamientos.add(t);
        }

        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Se devuelve la lista de sucursales
        return tratamientos;
    }

    /**
     * Metodo que elimina de forma lógica un registro de tratamiento
     *
     * @param id
     * @throws Exception
     */
    public void delete(int id) throws Exception {
        //Generar la consulta SQL
        String query = "UPDATE tratamiento SET estatus = 0 WHERE idTratamiento=" + id + ";";

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

    public void update(Tratamiento t) throws Exception {
        //Generar la consulta SQL
        String query = "UPDATE tratamiento SET nombre = ?, descripcion = ?, costo = ? WHERE idTratamiento = ?;";

        //Generar el objeto de conexión y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();

        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);

        //Terminar la sentencia / cargar el objeto pstmt
        pstmt.setString(1, t.getNombre());
        pstmt.setString(2, t.getDescripcion());
        pstmt.setDouble(3, t.getCosto());
        pstmt.setInt(4, t.getId());

        //Ejecutamos la consulta
        pstmt.executeUpdate();

        //Cerrar los objetos de conexión
        pstmt.close();
        connMySQL.close();
    }

    public List<Tratamiento> search(String filter, int status) throws Exception {
        //Generar la consulta SQL
        String query = "SELECT * FROM tratamiento WHERE estatus = ? AND " + filter + ";";

        //Generar la lista de tratamientos que se va a obtener con la consulta
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();

        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();

        //Generamos el objeto que nos permite enviar y ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, status);

        //Ejecutar la sentencia y recibir resultado
        ResultSet rs = pstmt.executeQuery();

        //Recorremos el RS para sacar los datos de las sucursales
        while (rs.next()) {
            //Generar una variable temporal para crear nuevas instancias de Tratamiento
            Tratamiento t = new Tratamiento();

            //Llenamos los atributos del objeto Tratamiento con los datos del RS
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getDouble("costo"));
            t.setEstatus(rs.getInt("estatus"));

            //Se agrega ese objeto de Sucursal a la lista de sucursales
            tratamientos.add(t);
        }

        //Cerra los objetos de conexion de la BD
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Se devuelve la lista de sucursales
        return tratamientos;
    }
}
