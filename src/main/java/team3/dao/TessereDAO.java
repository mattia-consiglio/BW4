package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Tessera;
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
}
