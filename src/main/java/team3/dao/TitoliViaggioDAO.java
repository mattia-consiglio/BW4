package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.*;

import java.time.LocalDate;
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

    public Long getNumberBigliettiByPeriodo(LocalDate dataInizio, LocalDate dataFine, Emettitore emettitore) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(b.id) FROM Biglietto b WHERE b.dataEmissione BETWEEN :dataInizio " +
                        "AND :dataFine AND b.emettitore.id = :emettitore", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("emettitore", emettitore.getId());
        return query.getSingleResult();
    }

    public Long getNumberAbbonamentiByPeriodo(LocalDate dataInizio, LocalDate dataFine, Emettitore emettitore) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a.id) FROM Abbonamento a WHERE a.dataEmissione BETWEEN :dataInizio " +
                        "AND :dataFine AND b.emettitore.id = :emettitore", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        query.setParameter("emettitore", emettitore.getId());
        return query.getSingleResult();
    }


    //QUERY per ottenere una LISTA tutti i biglietti che sono stati vidimati su un mezzo
    public List<Biglietto> listaBigliettiVidimitateSuDeterminatoMezzo(Mezzo mezzo) {
        TypedQuery<Biglietto> list = em.createQuery("SELECT b FROM Biglietto b WHERE b.vidimato = true AND b.mezzo.id = :mezzo", Biglietto.class);
        list.setParameter("mezzo", mezzo.getId());
        return list.getResultList();
    }

    //QUERY per ottenere una LISTA tutti i biglietti che sono stati vidimati su un mezzo
    public Long countBigliettiVidimitateSuDeterminatoMezzo(Mezzo mezzo) {
        TypedQuery<Long> list = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.vidimato = true AND b.mezzo.id = :mezzo", Long.class);
        list.setParameter("mezzo", mezzo.getId());
        return list.getSingleResult();
    }

    //QUERY per CONTARE i biglietti che sono stati vidimati su un mezzo in un determinato periodo di tempo
    public Long countBigliettiVidimatiSuMezzo(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.vidimato = true AND b.mezzo.id = :mezzo AND b.dataVidimazione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("mezzo", mezzo.getId());
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getSingleResult();
    }

    //QUERY per CONTARE tutti i biglietti che sono stati vidimati su tutti i mezzi in un determinato periodo di tempo
    public Long countBigliettiVidimatiInGenerale(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.vidimato = true AND b.dataVidimazione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getSingleResult();
    }
}
