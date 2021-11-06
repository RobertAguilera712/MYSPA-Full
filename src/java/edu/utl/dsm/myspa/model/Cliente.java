package edu.utl.dsm.myspa.model;

public class Cliente {
    private int id, estatus;
    private String numeroUni, correo;
    private Persona persona;
    private Usuario usuario;

    public Cliente() {
    }

    public Cliente(int id, int estatus, String numeroUni, String correo, Persona persona, Usuario usuario) {
        this.id = id;
        this.estatus = estatus;
        this.numeroUni = numeroUni;
        this.correo = correo;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Cliente(int estatus, String numeroUni, String correo, Persona persona, Usuario usuario) {
        this.estatus = estatus;
        this.numeroUni = numeroUni;
        this.correo = correo;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getNumeroUni() {
        return numeroUni;
    }

    public void setNumeroUni(String numeroUni) {
        this.numeroUni = numeroUni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", estatus=" + estatus + ", numeroUni=" + numeroUni + ", correo=" + correo + ", persona=" + persona + ", usuario=" + usuario + '}';
    }
}
