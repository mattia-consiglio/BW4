package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.Mezzo;
import team3.entities.StatoMezzi;

public class MezzoDAO {
    private EntityManager em;
    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(mezzo);
            t.commit();
            System.out.println("Mezzo inserito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Mezzo findById(long id) {
        return em.find(Mezzo.class, id);
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            Mezzo found = em.find(Mezzo.class, id);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Mezzo eliminato");
            } else System.out.println("Mezzo non trovato");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
