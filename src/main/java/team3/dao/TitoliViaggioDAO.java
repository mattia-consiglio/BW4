package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.TitoloViaggio;

public class TitoliViaggioDAO {
    private final EntityManager em;

    public TitoliViaggioDAO(EntityManager em) {
        this.em = em;
    }

    public void save(TitoloViaggio titoloViaggio) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(titoloViaggio);
        transaction.commit();
        System.out.println("Titolo viaggio  " + titoloViaggio.getId() + " salvato con successo!");
        System.out.println(titoloViaggio);
    }
}
