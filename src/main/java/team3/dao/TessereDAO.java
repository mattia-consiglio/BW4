package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import team3.entities.Tessera;
import team3.entities.Utente;
import team3.exceptions.NotFoundException;

import java.util.List;

public class TessereDAO {
    private final EntityManager em;

    public TessereDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(tessera);
            transaction.commit();
            System.out.println("Tessera " + tessera + " salvata correttamente!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Tessera findById(long id) {
        Tessera tessera = em.find(Tessera.class, id);
        if (tessera == null) {
            throw new NotFoundException(id);
        }
        return tessera;
    }

    public void deleteById(long id) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Tessera found = em.find(Tessera.class, id);
            if (found != null) {
                em.remove(found);
                transaction.commit();
                System.out.println("Tessera eliminata");
            } else {
                System.out.println("Tessera non trovata");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Tessera> getAll() {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t", Tessera.class);
        return query.getResultList();
    }

    public List<Tessera> getByUser(Utente utente) {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t WHERE t.utente = :utente", Tessera.class);
        query.setParameter("utente", utente);
        return query.getResultList();
    }

    public void invalida(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("UPDATE Tessera t SET t.validita = false WHERE t.id = :id");
        query.setParameter("id", tessera.getId());

        if (query.executeUpdate() == 1) {
            System.out.println("Tessera " + tessera + " invalidata con successo");
        } else {
            throw new RuntimeException("Errore durante l'invalidamento della tessera");
        }
        transaction.commit();
    }

    public void rinnova(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("UPDATE Tessera t SET t.dataInizio = :dataInizio, t.dataFine = :dataFine WHERE t.id = :id");
        query.setParameter("id", tessera.getId());
        query.setParameter("dataInizio", tessera.getDataInizio());
        query.setParameter("dataFine", tessera.getDataFine());

        if (query.executeUpdate() == 1) {
            System.out.println("Tessera " + tessera + " rinnovata con successo");
            System.out.println(tessera);
        } else {
            throw new RuntimeException("Errore durante il rinnovo della tessera");
        }
        transaction.commit();
    }

    public List<Tessera> getValidEpired() {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t WHERE t.validita = true AND t.dataScadenza < CURRENT_DATE ORDER BY t.dataFine", Tessera.class);
        return query.getResultList();
    }

    public List<Tessera> getValid() {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t WHERE t.validita = true ORDER BY t.dataFine", Tessera.class);
        return query.getResultList();
    }
}
