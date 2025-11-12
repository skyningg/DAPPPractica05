//JPACONFIG

package com.ejemplo.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAConfig {

    @Produces
    @ApplicationScoped
    public EntityManagerFactory emf() {
        return Persistence.createEntityManagerFactory("empleadosPU");
    }

    @Produces
    @RequestScoped
    public EntityManager em(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) em.close();
    }

}
