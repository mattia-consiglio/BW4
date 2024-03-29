package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Mezzo;

import java.util.List;

public class MezziDAO {
    private EntityManager em;

    public MezziDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(mezzo);
            t.commit();
            System.out.println("Mezzo inserito: " + mezzo);
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

    public void update(Mezzo mezzo) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(mezzo);
            t.commit();
            System.out.println("Mezzo aggiornato: " + mezzo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Mezzo> getAll() {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class);
        return query.getResultList();
    }

    public List<Mezzo> getDisponibili() {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE m.disponibile = true", Mezzo.class);
        return query.getResultList();
    }


}
