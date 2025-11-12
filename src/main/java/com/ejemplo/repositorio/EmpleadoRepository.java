package com.ejemplo.repositorio;

import com.ejemplo.modelo.Empleado;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@ApplicationScoped
public class EmpleadoRepository {

    @Inject
    EntityManager em;
    public List<Empleado> listar() {
        return em.createQuery("SELECT e FROM Empleado e",
                Empleado.class).getResultList();
    }
    public void crear(Empleado e) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(e);
        tx.commit();
    }
    public void actualizar(Empleado e) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(e);
        tx.commit();
    }
    public void eliminar(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Empleado e = em.find(Empleado.class, id);
        if (e != null) em.remove(e);
        tx.commit();
    }

}
