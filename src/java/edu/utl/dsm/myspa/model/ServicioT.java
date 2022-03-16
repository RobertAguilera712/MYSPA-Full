package edu.utl.dsm.myspa.model;

import java.util.List;

public class ServicioT {
    private int id;
    private Tratamiento tratamiento;
    private List<Producto> productos;

	public ServicioT() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tratamiento getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
