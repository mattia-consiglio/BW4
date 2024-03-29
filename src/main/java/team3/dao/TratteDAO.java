package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.Tratta;

import java.util.List;

public class TratteDAO {
    private final EntityManager em;

    public TratteDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Tratta tratta) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(tratta);
            t.commit();
            System.out.println("Tratta inserita: " + tratta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Tratta> getAll() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    public void update(Tratta tratta) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(tratta);
            t.commit();
            System.out.println("Tratta aggiornata: " + tratta);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
