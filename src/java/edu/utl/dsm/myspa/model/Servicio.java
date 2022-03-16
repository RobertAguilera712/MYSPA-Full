package edu.utl.dsm.myspa.model;

import java.util.List;

public class Servicio {
    private int id;
    private String fecha;
    private Empleado empleado;
    private Reservacion reservacion;
	private List<ServicioT> ServiciosT;
	private float total;

	public Servicio() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Reservacion getReservacion() {
		return reservacion;
	}

	public void setReservacion(Reservacion reservacion) {
		this.reservacion = reservacion;
	}

	public List<ServicioT> getServiciosT() {
		return ServiciosT;
	}

	public void setServiciosT(List<ServicioT> ServiciosT) {
		this.ServiciosT = ServiciosT;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
}
