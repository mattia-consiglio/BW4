package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.MezziTratta;

public class MezziTrattaDAO {
    private EntityManager em;

    public MezziTrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(MezziTratta mezziTratta) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(mezziTratta);
            t.commit();
            System.out.println("Mezzo inserito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
