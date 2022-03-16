package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase contiene los métodos necesarios para mantener la persistencia y
 * consulta de informacion de los clientes en la base de datos.
 *
 * @author Jean_
 */
public class ControllerCliente {

	public int insert(Cliente c) throws Exception {
		//Definimos la consulta SQL que invoca al Stored Procedure:
		String sql = "{call insertarCliente(?, ?, ?, ?, ?, ?, ?, "
				+ //Datos Persona
				"?, ?, ?, "
				+ //Datos Usuario
				"?,"
				+ //Datos Cliente
				"?, ?, ?, ?)}"; //Valores de Retorno

		//Aquí guardaremoslos ID's que se generarán:
		int idPersonaGenerado = -1;
		int idUsuarioGenerado = -1;
		int idClienteGenerado = -1;
		String numeroUnicoGenerado = "";

		//Con este objeto nos vamos a conectar a la Base de Datos:
		ConexionMySQL connMySQL = new ConexionMySQL();

		//Abrimos la conexión con la Base de Datos:
		Connection conn = connMySQL.open();

		//Con este objeto invocaremos al StoredProcedure:
		CallableStatement cstmt = conn.prepareCall(sql);

		//Establecemos los parámetros de los datos personales en el orden
		//en que los pide el procedimiento almacenado, comenzando en 1:
		cstmt.setString(1, c.getPersona().getNombre());
		cstmt.setString(2, c.getPersona().getApellidoP());
		cstmt.setString(3, c.getPersona().getApellidoM());
		cstmt.setString(4, c.getPersona().getGenero());
		cstmt.setString(5, c.getPersona().getDomicilio());
		cstmt.setString(6, c.getPersona().getTelefono());
		cstmt.setString(7, c.getPersona().getRfc());

		//Establecemos los parámetros de los datos de Usuario:
		cstmt.setString(8, c.getUsuario().getNombreUsu());
		cstmt.setString(9, c.getUsuario().getContrasenia());
		cstmt.setString(10, c.getUsuario().getRol());

		//Establecemos los parámetros de los datos de Cliente:        
		cstmt.setString(11, c.getCorreo());

		//Registramos los parámetros de salida:
		cstmt.registerOutParameter(12, Types.INTEGER);
		cstmt.registerOutParameter(13, Types.INTEGER);
		cstmt.registerOutParameter(14, Types.INTEGER);
		cstmt.registerOutParameter(15, Types.VARCHAR);

		//Ejecutamos el Stored Procedure:
		cstmt.executeUpdate();

		//Recuperamos los ID's generados:
		idPersonaGenerado = cstmt.getInt(12);
		idUsuarioGenerado = cstmt.getInt(13);
		idClienteGenerado = cstmt.getInt(14);
		numeroUnicoGenerado = cstmt.getString(15);

		//Los guardamos en el objeto Cliente que nos pasaron como parámetro:
		c.getPersona().setId(idPersonaGenerado);
		c.getUsuario().setId(idUsuarioGenerado);
		c.setId(idClienteGenerado);
		c.setNumeroUni(numeroUnicoGenerado);

		//Cerramos los objetos de Base de Datos:
		cstmt.close();
		connMySQL.close();

		//Devolvemos el ID de Cliente generado:
		return idClienteGenerado;
	}

	/**
	 * Inserta un registro de {@link Cliente} en la base de datos.
	 *
	 * @param e Es el objeto de tipo {@link Cliente}, el cual contiene los datos
	 * que seran insertados dentro del nuevo registro.
	 * @return Devuelve el ID que se genera para el Cliente, después de su
	 * insercion.
	 * @throws Exception
	 */
	/**
	 * Actualiza un registro de {@link Cliente}, previamente existente, en la
	 * base de datos.
	 *
	 * @param e Es el objeto de tipo {@link Cliente}, el cual contiene los datos
	 * que seran insertados dentro del nuevo registro.
	 * @throws Exception
	 */
	public void update(Cliente c) throws Exception {
		//Definimos la consulta SQL que invoca al Stored Procedure:
		String sql = "{call actualizarCliente(?, ?, ?, ?, ?, ?, ?, "
				+ //Datos Persona
				"?, ?, ?, "
				+ //Datos Usuario
				"?, "
				+ //Datos Cliente
				"?, ?, ?)}"; //IDs de tablas relacionadas

		//Con este objeto nos vamos a conectar a la Base de Datos:
		ConexionMySQL connMySQL = new ConexionMySQL();

		//Abrimos la conexión con la Base de Datos:
		Connection conn = connMySQL.open();

		//Con este objeto invocaremos al StoredProcedure:
		CallableStatement cstmt = conn.prepareCall(sql);

		//Establecemos los parámetros de los datos personales en el orden
		//en que los pide el procedimiento almacenado, comenzando en 1:
		cstmt.setString(1, c.getPersona().getNombre());
		cstmt.setString(2, c.getPersona().getApellidoP());
		cstmt.setString(3, c.getPersona().getApellidoM());
		cstmt.setString(4, String.valueOf(c.getPersona().getGenero()));
		cstmt.setString(5, c.getPersona().getDomicilio());
		cstmt.setString(6, c.getPersona().getTelefono());
		cstmt.setString(7, c.getPersona().getRfc());

		//Establecemos los parámetros de los datos de Usuario:
		cstmt.setString(8, c.getUsuario().getNombreUsu());
		cstmt.setString(9, c.getUsuario().getContrasenia());
		cstmt.setString(10, c.getUsuario().getRol());

		//Establecemos los parámetros de los datos de Cliente:        
		cstmt.setString(11, c.getCorreo());

		//Establecemos los ID's de las tablas relacionadas:
		cstmt.setInt(12, c.getPersona().getId());
		cstmt.setInt(13, c.getUsuario().getId());
		cstmt.setInt(14, c.getId());

		//Ejecutamos el Stored Procedure:
		cstmt.executeUpdate();

		//Cerramos los objetos de Base de Datos:
		cstmt.close();
		connMySQL.close();
	}

	/**
	 * Elimina un registro de {@link Cliente} en la base de datos.
	 *
	 * @param id Es el ID del {@link Cliente} que se desea eliminar.
	 * @throws Exception
	 */
	public void delete(int id) throws Exception {
		//Generar la consulta SQL
		String query = "UPDATE v_clientes SET estatus = 0 WHERE idCliente=" + id + ";";

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

	/**
	 * Busca un registro de {@link Cliente} en la base de datos.
	 *
	 * @param filter Es el la palabra del {@link Cliente} que se desea buscar.
	 * @return Devuelve el {@link Cliente} que se encuentra en la base de datos,
	 * basado en la coincidencia filter pasado como parámetro. Si no es
	 * encontrado un {@link Cliente} , el método devolvera <code>null</code>.
	 * @throws Exception
	 */
	public List<Cliente> search(String filter, int status) throws Exception {
		//Generar la consulta SQL
		String query = "SELECT * FROM v_clientes WHERE estatus = ? AND " + filter + ";";

		//Generar la lista de clientes que se va a obtener con la consulta
		List<Cliente> clientes = new ArrayList<Cliente>();

		//Crear un objeto de la conexión a la BD y abrirla
		ConexionMySQL connMySQL = new ConexionMySQL();
		Connection conn = connMySQL.open();

		//Generamos el objeto que nos permite enviar y ejecutar la consulta
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, status);

		//Ejecutar la sentencia y recibir resultado
		ResultSet rs = pstmt.executeQuery();

		//Recorremos el RS para sacar los datos de los clientes
		while (rs.next()) {
			Cliente c = fill(rs);
			clientes.add(c);
		}

		//Cerra los objetos de conexion de la BD
		rs.close();
		pstmt.close();
		connMySQL.close();

		//Se devuelve la lista de clientes
		return clientes;
	}

	/**
	 * Consulta y devuelve los registros de clientes encontrados.
	 *
	 * Los registros encontrados se devuelven en forma de una lista dinamica
	 * (List&lt;{@link Cliente}&rt;) que contiene dentro los objetos de tipo
	 * {@link Cliente}.
	 *
	 * @param filtro Es el termino de coincidencia parcial que condicionara
	 * @return Devuelve el listado de clientes encontrados en la base de datos,
	 * en forma de una lista dinamica <code>List&lt;{@link Cliente}&rt;</code>.
	 * Si la base de datos no tiene algun registro de cliente, se devuelve una
	 * lista vacia (NO SE DEVUELVE <code>null</code>!).
	 * @throws Exception
	 */
	public List<Cliente> getAll(int status) throws Exception {
		//La consulta SQL a ejecutar:
		String sql = "SELECT * FROM v_clientes where estatus = ?";

		//La lista dinámica donde guardaremos objetos de tipo Cliente
		//por cada registro que devuelva la BD:
		List<Cliente> clientes = new ArrayList<Cliente>();

		//Una variable temporal para crear nuevos objetos de tipo Empleado:
		Cliente c = null;

		//Con este objeto nos vamos a conectar a la Base de Datos:
		ConexionMySQL connMySQL = new ConexionMySQL();

		//Abrimos la conexión con la Base de Datos:
		Connection conn = connMySQL.open();

		//Con este objeto ejecutaremos la consulta:
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, status);

		//Aquí guardaremos los resultados de la consulta:
		ResultSet rs = pstmt.executeQuery();

		//Iteramos el conjunto de registros devuelto por la BD.
		//"Si hay un siguiente registro, nos movemos":
		while (rs.next()) {
			c = fill(rs);
			//Agregamos el objeto de tipo Empleado a la lista dinámica:
			clientes.add(c);
		}

		//Cerramos los objetos de BD:
		rs.close();
		pstmt.close();
		connMySQL.close();

		//Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
		return clientes;
	}

	static Cliente fill(ResultSet rs) throws Exception {
		//Una variable temporal para crear nuevos objetos de tipo Cliente:
		Cliente c = new Cliente();

		//Una variable temporal para crear nuevos objetos de tipo Persona:
		Persona p = new Persona();

		//Una variable temporal para crear nuevos objetos de tipo Usuario:
		Usuario u = new Usuario();

		//Llenamos sus datos:
		p.setApellidoM(rs.getString("apellidoMaterno"));
		p.setApellidoP(rs.getString("apellidoPaterno"));
		p.setNombreCompleto(rs.getString("nombreCompleto"));
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
                u.setToken();

		//Establecemos sus datos personales:
		c.setId(rs.getInt("idCliente"));
		c.setNumeroUni(rs.getString("numeroUnico"));
		c.setCorreo(rs.getString("correo"));
		c.setEstatus(rs.getInt("estatus"));

		//Establecemos su persona:
		c.setPersona(p);

		//Establecemos su Usuario:
		c.setUsuario(u);

		return c;
	}

	public Cliente login(String nombreU, String contra) throws Exception {
		//Definir la consulta que se va a ejecutar
		String query = "SELECT * FROM v_clientes WHERE nombreUsuario=? AND contrasenia=? " + " AND estatus=1 AND (token IS NULL OR token = '');";
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
		//objeto de tipo cliente
		Cliente c = null;
		if (rs.next()) {
                    c = fill(rs);
                    saveToken(c.getUsuario());    
		}
		//cerrar los objetos de uso para la BD
		rs.close();
		pstmt.close();
		connMySQL.close();
		//devuelve el objeto de tipo cliente
		return c;
	}
        
        public void saveToken(Usuario u) throws Exception {
		//consulta
		String query = "UPDATE usuario SET token ='" + u.getToken() + "' WHERE idUsuario=" + u.getId() + ";";
		//generar el objeto de la conexion
		ConexionMySQL connMySQL = new ConexionMySQL();
		//Abrir conexion
		Connection conn = connMySQL.open();
		//oobjeto para ejecutar la consulta
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.execute();
		pstmt.close();
		conn.close();
		connMySQL.close();
	}

	public void deleteToken(Usuario u) throws Exception {
		String query = "UPDATE usuario SET token=NULL WHERE idUsuario=" + u.getId() + ";";
		ConexionMySQL connMySQL = new ConexionMySQL();
		Connection conn = connMySQL.open();
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.execute();
		pstmt.close();
		conn.close();
		connMySQL.close();
	}

	public boolean validateToken(String token) throws Exception {
		boolean valid = false;
		//Generamos la consulta
		String query = "SELECT * FROM v_clientes WHERE token='" + token + "';";
		//Generar el objeto de la conexion
		ConexionMySQL connMySQL = new ConexionMySQL();
		//Abrir la conexion
		Connection conn = connMySQL.open();
		//Objeto para ejecutar la consulta
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
                    valid = true;
		}
		pstmt.close();
		conn.close();
		connMySQL.close();

		return valid;
	}
}
