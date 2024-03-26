package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.Utente;
import team3.exceptions.NotFoundException;

public class UtentiDAO {
    private final EntityManager em;

    public UtentiDAO(EntityManager em) {
        this.em = em;
    }

    public void save (Utente utente) {
        try{
          EntityTransaction transaction = em.getTransaction();
          transaction.begin();
          em.persist(utente);
          transaction.commit();
          System.out.println("Utente " + utente + " salvato!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Utente findById (long id) {
        Utente utente = em.find(Utente.class, id);
        if(utente == null) {
            throw new NotFoundException(id);
        }
        return utente;
    }

    public void deleteById (long id) {
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
}
