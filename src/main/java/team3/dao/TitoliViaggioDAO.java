package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Abbonamento;
import team3.entities.Biglietto;
import team3.entities.TitoloViaggio;

import java.util.List;

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

    public List<Biglietto> getAllBiglietti() {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
        return query.getResultList();
    }

    public List<Biglietto> getAllAbbonamenti() {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
        return query.getResultList();
    }

    public List<Biglietto> getFirstBiglietto() {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b.id FROM Biglietto b ORDER BY b.id LIMIT 1", Biglietto.class);
        return query.getResultList();
    }

    public List<Abbonamento> getFirstAbbonamento() {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a.id FROM Abbonamento a ORDER BY a.id LIMIT 1", Abbonamento.class);
        return query.getResultList();
    }


}
