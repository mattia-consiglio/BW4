package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.StatoMezzi;

public class StatoMezzoDAO {
    private EntityManager em;
    public StatoMezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(StatoMezzi statoMezzi) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(statoMezzi);
            t.commit();
            System.out.println("Stato mezzo inserito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public StatoMezzi findById(long id) {
        return em.find(StatoMezzi.class, id);
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            StatoMezzi found = em.find(StatoMezzi.class, id);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Stato eliminato");
            } else System.out.println("Stato non trovato");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
