package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Mezzo;
import team3.entities.StatoMezzo;

import java.util.List;

public class StatoMezzoDAO {
    private EntityManager em;

    public StatoMezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(StatoMezzo statoMezzo) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(statoMezzo);
            t.commit();
            System.out.println("Stato mezzo inserito" + statoMezzo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public StatoMezzo findById(long id) {
        return em.find(StatoMezzo.class, id);
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            StatoMezzo found = em.find(StatoMezzo.class, id);
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

    public List<StatoMezzo> getAll() {
        TypedQuery<StatoMezzo> query = em.createQuery("SELECT s FROM StatoMezzo s", StatoMezzo.class);
        return query.getResultList();
    }

    public List<StatoMezzo> getInManutenzione(Mezzo mezzo) {
        TypedQuery<StatoMezzo> query = em.createQuery("SELECT s FROM StatoMezzo s WHERE s.condizioneMezzo = CondizioneMezzo.IN_MANUTENZIONE AND s.mezzo = :mezzo ORDER BY s.dataInizio DESC", StatoMezzo.class);
        query.setParameter("mezzo", mezzo);
        return query.getResultList();
    }
}
