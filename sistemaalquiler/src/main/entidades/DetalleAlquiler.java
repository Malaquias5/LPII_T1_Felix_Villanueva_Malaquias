package com.alquiler.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_alquiler")
@IdClass(DetalleAlquilerId.class)
public class DetalleAlquiler {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    private int cantidad;

    // Getters y Setters
    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

