package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import team3.entities.Abbonamento;
import team3.entities.Biglietto;
import team3.entities.Tessera;
import team3.entities.Utente;
import team3.exceptions.NotFoundException;

import java.util.List;

public class UtentiDAO {
    private final EntityManager em;

    public UtentiDAO(EntityManager em) {
        this.em = em;
    }

    public Utente save(Utente utente) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(utente);
            transaction.commit();
            System.out.println("Utente " + utente + " salvato!");
            return utente;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Utente findById(long id) {
        Utente utente = em.find(Utente.class, id);
        if (utente == null) {
            throw new NotFoundException(id);
        }
        return utente;
    }

    public void deleteById(long id) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Utente found = em.find(Utente.class, id);
            if (found != null) {
                em.remove(found);
                transaction.commit();
                System.out.println("Utente eliminato!");
            } else {
                System.out.println("Utente non trovato!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Utente> getFirst() {
        TypedQuery<Utente> query = em.createQuery("SELECT u.id FROM Utente u ORDER BY u.id LIMIT 1", Utente.class);
        return query.getResultList();
    }


    public List<Utente> getAll() {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u", Utente.class);
        return query.getResultList();
    }

    public Utente getByTessera(Tessera tessera) {
        TypedQuery<Utente> query = em.createQuery("SELECT t.utente FROM Tessera t WHERE t.id = :id", Utente.class);
        query.setParameter("id", tessera.getId());
        return query.getSingleResult();
    }

    // metodo per eliminare un utente ma che tiene in memoria tutti gli oggetti ad esso associati
    public void cancellaUtenteTranneSuoiOggetti(Utente utente) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Utente found = em.find(Utente.class, utente.getId());
            if (found != null) {
                // abbonamento
                Query queryAbbonamenti = em.createQuery("UPDATE Abbonamento a SET a.tessera.utente = null WHERE a.tessera.utente.id = :id");
                queryAbbonamenti.setParameter("utente", utente);
                queryAbbonamenti.executeUpdate();

                // tessera
                Query queryTessere = em.createQuery("UPDATE Tessera t SET t.utente = null WHERE t.utente.id = :id");
                queryTessere.setParameter("id", utente.getId());
                queryTessere.executeUpdate();

                // biglietto online
                Query queryBiglietto = em.createQuery("UPDATE Biglietto b SET b.utente = null WHERE b.utente.id = :id");
                queryBiglietto.setParameter("id", utente.getId());
                queryBiglietto.executeUpdate();

                // eliminiamo utente
                em.remove(found);

                transaction.commit();
                System.out.println("Utente eliminato e tutti gli oggetti ad esso associati restano salvati nello storico");
            } else {
                System.out.println("Utente non trovato");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void update(Utente utente) {

        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(utente);
            transaction.commit();
            System.out.println("Utente " + utente + " aggiornato!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
