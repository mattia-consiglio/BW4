package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.MezzoTratta;

import java.util.List;

public class MezziTrattaDAO {
    private final EntityManager em;


    public MezziTrattaDAO(EntityManager em) {
        this.em = em;
    }


    public void save(MezzoTratta mezzoTratta) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(mezzoTratta);
            t.commit();
            System.out.println("Mezzo tratta inserito: " + mezzoTratta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<MezzoTratta> getAll() {
        return em.createQuery("SELECT m FROM MezzoTratta m", MezzoTratta.class).getResultList();
    }
}
