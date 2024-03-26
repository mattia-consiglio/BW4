package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.Tratta;

public class TrattaDAO {
    private EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Tratta tratta) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(tratta);
            tx.commit();
            System.out.println("Tratta salvata con successo");
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante il salvataggio della tratta");
        }
    }
}
