package edu.utl.dsm.myspa.model;

public class Sala {
    private int id;
    private String nombre;
    private String descripcion;
    private String foto;
    private int estatus;
    private Sucursal sucursal;

    public Sala() {
    }

    public Sala(int id, String nombre, String descripcion, String foto, int estatus, Sucursal sucursal) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.estatus = estatus;
        this.sucursal = sucursal;
    }

    public Sala(String nombre, String descripcion, String foto, int estatus, Sucursal sucursal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.estatus = estatus;
        this.sucursal = sucursal;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Sala{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", foto=" + foto + ", estatus=" + estatus + ", sucursal=" + sucursal + '}';
    }
}
