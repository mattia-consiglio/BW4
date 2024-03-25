package team3;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.EmettitoreDAO;
import team3.entities.Emettitore;
import team3.entities.EmettitoreEnum;
import team3.entities.EmettitoreStato;
import team3.entities.Emettitore;
import team3.entities.EmettitoreEnum;
import team3.exceptions.EmettitoreException;

import java.util.Random;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4t3");
    private static final EntityManager em = emf.createEntityManager();
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        Supplier<Emettitore> emettitoreSupplier = () -> {
            EmettitoreEnum tipologia = EmettitoreEnum.values()[new Random().nextInt(EmettitoreEnum.values().length)];
            EmettitoreStato stato = null;
            if (tipologia == EmettitoreEnum.DISTRIBUTORE) {
                stato = EmettitoreStato.values()[new Random().nextInt(EmettitoreStato.values().length)];
            }
            String city = faker.address().cityName();

            return new Emettitore(faker.company().name(), faker.address().streetAddress(), faker.number().digits(3), city, city, faker.number().digits(5), faker.address().country(), tipologia, stato);
        };

        System.out.println("Hello World!");
        EmettitoreDAO ed = new EmettitoreDAO(em);
        Emettitore emettitore = null;
        try {
            emettitore = new Emettitore("nome","via Roma", "20", "Roma", "Roma", "12345", "Italia", EmettitoreEnum.DISTRIBUTORE, EmettitoreStato.ATTIVO);
            ed.save(emettitore);
        } catch (EmettitoreException e) {
            System.out.println(e.getMessage());
        }

        em.close();
    }
}
