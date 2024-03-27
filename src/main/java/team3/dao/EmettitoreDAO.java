package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Emettitore;

import java.util.List;

public class EmettitoreDAO {
    private EntityManager em;

    public EmettitoreDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Emettitore emettitore) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(emettitore);
            t.commit();
            System.out.println("Emettitore inserito: " + emettitore);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Emettitore findById(long id) {
        return em.find(Emettitore.class, id);
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            Emettitore found = em.find(Emettitore.class, id);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Emettitore eliminato");
            } else {
                System.out.println("Emettitore non trovato");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Emettitore> getAll() {
        TypedQuery<Emettitore> query = em.createQuery("SELECT e FROM Emettitore e", Emettitore.class);
        return query.getResultList();
    }
}
