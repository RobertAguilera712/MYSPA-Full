package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Horario;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Reservacion;
import edu.utl.dsm.myspa.model.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerReservacion {

	public List<Horario> getHourAv(String f, int idSala) throws Exception {
		String query = "SELECT H1.* FROM horario H1 "
				+ " LEFT JOIN "
				+ "(SELECT H.* "
				+ " FROM horario H "
				+ " INNER JOIN reservacion R "
				+ " ON H.idHorario = R.idHorario "
				+ " WHERE R.idSala=" + idSala + " AND R.fecha=STR_TO_DATE('" + f + "','%Y-%m-%d') ) AS SQ2 "
				+ "ON H1.idHorario=SQ2.idHorario "
				+ "WHERE SQ2.idHorario IS NULL;";
		ConexionMySQL obConexionMySQL = new ConexionMySQL();
		Connection conn = obConexionMySQL.open();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		List<Horario> horarios = new ArrayList<>();

		while (rs.next()) {
			Horario h = new Horario();
			h.setId(rs.getInt("idHorario"));
			h.setHoraI(rs.getString("horaInicio"));
			h.setHoraF(rs.getString("horaFin"));

			horarios.add(h);
		}

		rs.close();
		stmt.close();
		conn.close();
		obConexionMySQL.close();

		return horarios;
	}

	public void insert(Reservacion r) throws SQLException, Exception {
		String query = "INSERT INTO reservacion (estatus, idCliente, idSala, fecha, idHorario) "
				+ "VALUES (" + r.getEstatus() + ", " + r.getCliente().getId() + ", " + r.getSala().getId()
				+ ", STR_TO_DATE('" + r.getFecha() + "','%Y-%m-%d'), " + r.getHorario().getId() + ")";
		ConexionMySQL obConexionMySQL = new ConexionMySQL();
		Connection conn = obConexionMySQL.open();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
		conn.close();
		obConexionMySQL.close();
	}

	public List<Reservacion> getAll(int estatus) throws Exception {
		//Definir la consulta SQL
		String query = "SELECT * FROM v_reservacion WHERE estatus = " + estatus + "";

		//Generar la lista de sucursales que se va a obtener con la consulta
		List<Reservacion> reservaciones = new ArrayList<>();

		//Crear un objeto de la conexión a la BD y abrirla
		ConexionMySQL connMySQL = new ConexionMySQL();
		Connection conn = connMySQL.open();

		//Generamos el objeto que nos permite enviar y ejecutar la consulta
		PreparedStatement pstmt = conn.prepareStatement(query);

		//Ejecutar la sentencia y recibir resultado
		ResultSet rs = pstmt.executeQuery();

		//Recorremos el RS para sacar los datos de las salas
		while (rs.next()) {
			reservaciones.add(fill(rs));
		}

		//Cerra los objetos de conexion de la BD
		rs.close();
		pstmt.close();
		connMySQL.close();

		//Se devuelve la lista de salas
		return reservaciones;
	}
        
        public void delete(int id) throws Exception {
		String query = "UPDATE reservacion SET estatus = 0 WHERE idReservacion = ?";

		ConexionMySQL conexionMySQL = new ConexionMySQL();
		Connection conn = conexionMySQL.open();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setInt(1, id);

		pstmt.executeUpdate();

		pstmt.close();
		conexionMySQL.close();
	}

	private Reservacion fill(ResultSet rs) throws Exception {
		Reservacion reservacion = new Reservacion();

		Cliente c = new Cliente();
		Persona p = new Persona();

		p.setApellidoM(rs.getString("apellidoMaterno"));
		p.setApellidoP(rs.getString("apellidoPaterno"));
		p.setNombreCompleto(rs.getString("nombreCompleto"));
		p.setDomicilio(rs.getString("domicilio"));
		p.setGenero(rs.getString("genero"));
		p.setId(rs.getInt("idPersona"));
		p.setNombre(rs.getString("nombre"));
		p.setRfc(rs.getString("rfc"));
		p.setTelefono(rs.getString("telefono"));
		c.setPersona(p);

		Sala sa = new Sala();

        //Creamos un nuevo objeto de tipo Sala:
        sa.setId(rs.getInt("idSala"));
        sa.setNombre(rs.getString("nombreSala"));
        sa.setDescripcion(rs.getString("descripcion"));
        sa.setFoto(rs.getString("fotoSala"));
        sa.setEstatus(rs.getInt("estatusSala"));

		Horario h = new Horario();

		//Llenamos los atributos del objeto Sucursal con los datos del RS
		h.setId(rs.getInt("idHorario"));
		h.setHoraI(rs.getString("horaInicio"));
		h.setHoraF(rs.getString("horaFin"));

		reservacion.setId(rs.getInt("idReservacion"));
		reservacion.setEstatus(rs.getInt("estatus"));
		reservacion.setFecha(rs.getString("fecha"));
		reservacion.setCliente(c);
		reservacion.setSala(sa);
		reservacion.setHorario(h);

		return reservacion;
	}

}
