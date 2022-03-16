package edu.utl.dsm.myspa.model;

public class Persona {
    private int id;
    private String nombre, apellidoP, apellidoM, domicilio, telefono, rfc;
    private String genero;
    private String nombreCompleto;

    public Persona() {
    }

	public Persona(int id, String nombre, String apellidoP, String apellidoM, String domicilio, String telefono, String rfc, String genero) {
		this.id = id;
		this.nombre = nombre;
		this.apellidoP = apellidoP;
		this.apellidoM = apellidoM;
		this.domicilio = domicilio;
		this.telefono = telefono;
		this.rfc = rfc;
		this.genero = genero;
	}

	public Persona(String nombre, String apellidoP, String apellidoM, String domicilio, String telefono, String rfc, String genero) {
		this.nombre = nombre;
		this.apellidoP = apellidoP;
		this.apellidoM = apellidoM;
		this.domicilio = domicilio;
		this.telefono = telefono;
		this.rfc = rfc;
		this.genero = genero;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", nombre=" + nombre + ", apellidoP=" + apellidoP + ", apellidoM=" + apellidoM + ", domicilio=" + domicilio + ", telefono=" + telefono + ", rfc=" + rfc + ", genero=" + genero + '}';
    }
    
}
