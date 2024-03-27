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

    public List<Abbonamento> getAllAbbonamenti() {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class);
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

    public boolean isAbbonamentoValidByTesseraNumero(String numeroTessera) {
        TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tessera.id = :numeroTessera " + //seleziona gli abbonamenti associati alla tessera con il numero specificato
                        "AND a.dataFine >= CURRENT_DATE", Abbonamento.class); //verifica se la data di fine dell'abbonamento è maggiore o uguale alla data corrente
        query.setParameter("numeroTessera", numeroTessera);

        List<Abbonamento> abbonamenti = query.getResultList();
        return !abbonamenti.isEmpty(); // se la lista degli abbonamenti non è vuota, significa che c'è almeno un abbonamento valido per la tessera cercata
    }
}
