package com.alquiler.entidades;

import java.io.Serializable;
import java.util.Objects;

public class DetalleAlquilerId implements Serializable {

    private int alquiler;
    private int pelicula;

    public DetalleAlquilerId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleAlquilerId)) return false;
        DetalleAlquilerId that = (DetalleAlquilerId) o;
        return alquiler == that.alquiler && pelicula == that.pelicula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alquiler, pelicula);
    }
}

