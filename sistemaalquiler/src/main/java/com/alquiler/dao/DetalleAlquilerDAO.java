package com.alquiler.dao;

import com.alquiler.entidades.DetalleAlquiler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class DetalleAlquilerDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistemaalquilerPU");

    // Guardar un detalle de alquiler
    public void guardar(DetalleAlquiler detalleAlquiler) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(detalleAlquiler);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Buscar por ID autogenerado
    public DetalleAlquiler buscarPorId(int idDetalle) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(DetalleAlquiler.class, idDetalle);
        } finally {
            em.close();
        }
    }

    // Listar todos los detalles
    public List<DetalleAlquiler> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM DetalleAlquiler d", DetalleAlquiler.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar detalle
    public void actualizar(DetalleAlquiler detalleAlquiler) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(detalleAlquiler);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar por ID
    public void eliminar(int idDetalle) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DetalleAlquiler detalle = em.find(DetalleAlquiler.class, idDetalle);
            if (detalle != null) {
                em.remove(detalle);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
