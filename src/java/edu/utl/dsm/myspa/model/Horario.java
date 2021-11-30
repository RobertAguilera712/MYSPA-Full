package edu.utl.dsm.myspa.model;

public class Horario {
    private int id;
    private String horaI;
    private String horaF;
	private int estatus;

    public Horario() {
    }

    public Horario(int id, String horaI, String horaF) {
        this.id = id;
        this.horaI = horaI;
        this.horaF = horaF;
    }

    public Horario(String horaI, String horaF) {
        this.horaI = horaI;
        this.horaF = horaF;
    }

    public String getHoraF() {
        return horaF;
    }

    public void setHoraF(String horaF) {
        this.horaF = horaF;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoraI() {
        return horaI;
    }

    public void setHoraI(String horaI) {
        this.horaI = horaI;
    }

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	

    @Override
    public String toString() {
        return "Horario{" + "id=" + id + ", horaI=" + horaI + ", horaF=" + horaF + '}';
    }
}
