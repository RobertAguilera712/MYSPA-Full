package edu.utl.dsm.myspa.model;

import java.util.List;

public class ServicioT {
    private int id;
    private List<Tratamiento> tratamiento;
    private Servicio servicio;

    public ServicioT() {
    }

    public ServicioT(int id, List<Tratamiento> tratamiento, Servicio servicio) {
        this.id = id;
        this.tratamiento = tratamiento;
        this.servicio = servicio;
    }

    public ServicioT(List<Tratamiento> tratamiento, Servicio servicio) {
        this.tratamiento = tratamiento;
        this.servicio = servicio;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tratamiento> getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(List<Tratamiento> tratamiento) {
        this.tratamiento = tratamiento;
    }

    @Override
    public String toString() {
        return "ServicioT{" + "id=" + id + ", tratamiento=" + tratamiento + ", servicio=" + servicio + '}';
    }
}
