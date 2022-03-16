package edu.utl.dsm.myspa.model;

import java.util.List;

public class Servicio {
    private int id;
    private String fecha;
    private Empleado empleado;
    private Reservacion reservacion;
	private List<ServicioT> serviciosT;
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
		return serviciosT;
	}

	public void setServiciosT(List<ServicioT> ServiciosT) {
		this.serviciosT = ServiciosT;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

    @Override
    public String toString() {
        return "Servicio{" + "id=" + id + ", fecha=" + fecha + ", empleado=" + empleado + ", reservacion=" + reservacion + ", ServiciosT=" + serviciosT + ", total=" + total + '}';
    }
	
}
