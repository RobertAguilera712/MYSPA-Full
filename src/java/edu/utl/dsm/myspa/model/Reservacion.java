package edu.utl.dsm.myspa.model;

public class Reservacion {
    private int id;
    private String fecha;
    private int estatus;
    private Sala sala;
    private Cliente cliente;
    private Horario horario;

    public Reservacion() {
    }

    public Reservacion(int id, String fecha, int estatus, Sala sala, Cliente cliente) {
        this.id = id;
        this.fecha = fecha;
        this.estatus = estatus;
        this.sala = sala;
        this.cliente = cliente;
    }

    public Reservacion(String fecha, int estatus, Sala sala, Cliente cliente) {
        this.fecha = fecha;
        this.estatus = estatus;
        this.sala = sala;
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Reservacion{" + "id=" + id + ", fecha=" + fecha + ", estatus=" + estatus + ", sala=" + sala.toString() + ", cliente=" + cliente.toString() + '}';
    }

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	
}
