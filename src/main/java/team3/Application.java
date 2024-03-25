package team3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.EmettitoreDAO;
import team3.entities.Emettitore;
import team3.entities.EmettitoreEnum;
import team3.entities.EmettitoreStato;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4t3");
    private static final EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        EmettitoreDAO ed = new EmettitoreDAO(em);
        Emettitore emettitore = new Emettitore("nome","via Roma", "20", "Roma", "Roma", "12345", "Italia", EmettitoreEnum.DISTRIBUTORE, EmettitoreStato.ATTIVO);
        ed.save(emettitore);
        em.close();
    }
}
