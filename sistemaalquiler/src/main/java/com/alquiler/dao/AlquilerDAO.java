package com.alquiler.dao;

import com.alquiler.entidades.Alquiler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.util.List;

public class AlquilerDAO {

    // Singleton para EntityManagerFactory
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistemaalquilerPU");

    // Guardar un alquiler
    public void guardar(Alquiler alquiler) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(alquiler);  // Guardar la entidad
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Revertir si hay error
            }
            throw e;  // Lanzamos la excepción para manejo posterior
        } finally {
            em.close();  // Aseguramos que se cierre el EntityManager
        }
    }

    // Buscar alquiler por ID
    public Alquiler buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Alquiler.class, id);  // Buscar por ID
        } finally {
            em.close();  // Cerrar EntityManager
        }
    }

    // Listar todos los alquileres
    public List<Alquiler> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Alquiler a", Alquiler.class).getResultList();  // Consultar todos
        } finally {
            em.close();  // Cerrar EntityManager
        }
    }

    // Actualizar un alquiler
    public void actualizar(Alquiler alquiler) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(alquiler);  // Actualizar la entidad
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Revertir si hay error
            }
            throw e;  // Lanzamos la excepción para manejo posterior
        } finally {
            em.close();  // Cerrar EntityManager
        }
    }

    // Eliminar un alquiler por ID
    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Alquiler alquiler = em.find(Alquiler.class, id);  // Buscar el alquiler
            if (alquiler != null) {
                em.remove(alquiler);  // Eliminar la entidad
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Revertir si hay error
            }
            throw e;  // Lanzamos la excepción para manejo posterior
        } finally {
            em.close();  // Cerrar EntityManager
        }
    }

    // Método estático para cerrar el EntityManagerFactory al finalizar la aplicación
    public static void cerrarEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();  // Cerramos la fábrica de EntityManager
        }
    }
}
