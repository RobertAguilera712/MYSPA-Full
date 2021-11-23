package edu.utl.dsm.myspa.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.utl.dsm.myspa.model.Producto;
import edu.utl.dsm.myspa.db.ConexionMySQL;
import java.sql.SQLException;

public class ControllerProducto {

	public int insert(Producto p) throws Exception {
		String query = "INSERT INTO producto (nombre, marca, estatus, precioUso) VALUES (?, ?, ?, ?)";
		int idGenerado = -1;

		ConexionMySQL connMySQL = new ConexionMySQL();
		Connection conn = connMySQL.open();

		PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1, p.getNombre());
		pstmt.setString(2, p.getMarca());
		pstmt.setInt(3, p.getEstatus());
		pstmt.setFloat(4, p.getPrecioUso());

		ResultSet rs = null;

		pstmt.executeUpdate();

		rs = pstmt.getGeneratedKeys();

		if (rs.next()) {
			idGenerado = rs.getInt(1);
			p.setId(1);
		}

		rs.close();
		pstmt.close();
		connMySQL.close();

		return idGenerado;
	}

	public List<Producto> getAll(int estatus) throws Exception {
		String query = "SELECT * FROM producto WHERE estatus = ?";

		List<Producto> productos = new ArrayList<>();

		ConexionMySQL conexionMySQL = new ConexionMySQL();
		Connection conn = conexionMySQL.open();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setInt(1, estatus);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			productos.add(fill(rs));
		}

		rs.close();
		pstmt.close();
		conexionMySQL.close();

		return productos;
	}

	public void delete(int id) throws Exception {
		String query = "UPDATE producto SET estatus = 0 WHERE idProducto = ?";

		ConexionMySQL conexionMySQL = new ConexionMySQL();
		Connection conn = conexionMySQL.open();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setInt(1, id);

		pstmt.executeUpdate();

		pstmt.close();
		conexionMySQL.close();
	}

	public void update(Producto p) throws Exception {
		String query = "UPDATE producto SET nombre = ?, marca = ?, precioUso = ? WHERE idProducto = ?";

		ConexionMySQL conexionMySQL = new ConexionMySQL();
		Connection conn = conexionMySQL.open();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setString(1, p.getNombre());
		pstmt.setString(2, p.getMarca());
		pstmt.setFloat(3, p.getPrecioUso());
		pstmt.setInt(4, p.getId());

		pstmt.executeUpdate();

		pstmt.close();
		conexionMySQL.close();
	}

	public List<Producto> search(String filter, int estatus) throws Exception {
		String query = String.format("SELECT * FROM producto WHERE estatus = ? AND %s", filter);

		List<Producto> productos = new ArrayList<>();

		ConexionMySQL conexionMySQL = new ConexionMySQL();
		Connection connection = conexionMySQL.open();

		PreparedStatement pStatement = connection.prepareStatement(query);

		pStatement.setInt(1, estatus);

		ResultSet rs = pStatement.executeQuery();

		while (rs.next()) {
			productos.add(fill(rs));
		}

		rs.close();
		pStatement.close();
		conexionMySQL.close();

		return productos;
	}

	private Producto fill(ResultSet rs) throws SQLException {
		Producto producto = new Producto();

		producto.setId(rs.getInt("idProducto"));
		producto.setNombre(rs.getString("nombre"));
		producto.setMarca(rs.getString("marca"));
		producto.setPrecioUso(rs.getFloat("precioUso"));
		producto.setEstatus(rs.getInt("estatus"));

		return producto;
	}
}
