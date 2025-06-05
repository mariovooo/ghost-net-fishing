package de.mariovogel.ghostnetfishing.Service;

import java.util.List;

import de.mariovogel.ghostnetfishing.Model.GhostNet;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class GhostNetService {

    @PersistenceContext
    private EntityManager em;

    public void save(GhostNet net) {
        em.persist(net);
        System.out.println("Request saved");
    }
    
    public List<GhostNet> findAll() {
        return em.createQuery("SELECT g FROM GhostNet g", GhostNet.class).getResultList();
    }
   
}

