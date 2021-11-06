package edu.utl.dsm.myspa.model;

public class Servicio {
    private int id;
    private String fecha;
    private Empleado empleado;
    private Reservacion reservacion;

    public Servicio() {
    }

    public Servicio(int id, String fecha, Empleado empleado, Reservacion reservacion) {
        this.id = id;
        this.fecha = fecha;
        this.empleado = empleado;
        this.reservacion = reservacion;
    }

    public Servicio(String fecha, Empleado empleado, Reservacion reservacion) {
        this.fecha = fecha;
        this.empleado = empleado;
        this.reservacion = reservacion;
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
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

    @Override
    public String toString() {
        return "Servicio{" + "id=" + id + ", fecha=" + fecha + ", empleado=" + empleado.toString() + ", reservacion=" + reservacion.toString() + '}';
    }
}
