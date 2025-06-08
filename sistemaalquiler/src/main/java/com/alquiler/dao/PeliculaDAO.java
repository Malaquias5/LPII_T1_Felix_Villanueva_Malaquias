package com.alquiler.dao;

import com.alquiler.entidades.Pelicula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class PeliculaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistemaalquilerPU");

    public void guardar(Pelicula pelicula) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(pelicula);
        em.getTransaction().commit();
        em.close();
    }

    public Pelicula buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        Pelicula pelicula = em.find(Pelicula.class, id);
        em.close();
        return pelicula;
    }

    public List<Pelicula> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Pelicula> peliculas = em.createQuery("SELECT p FROM Pelicula p", Pelicula.class).getResultList();
        em.close();
        return peliculas;
    }

    public void actualizar(Pelicula pelicula) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(pelicula);
        em.getTransaction().commit();
        em.close();
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Pelicula pelicula = em.find(Pelicula.class, id);
        if (pelicula != null) {
            em.remove(pelicula);
        }
        em.getTransaction().commit();
        em.close();
    }
}

