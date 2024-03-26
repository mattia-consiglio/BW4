package team3;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.EmettitoreDAO;
import team3.dao.MezzoDAO;
import team3.entities.*;
import team3.exceptions.EmettitoreException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4t3");
    private static final EntityManager em = emf.createEntityManager();
    private static final Faker faker = new Faker();


    private static final EmettitoreDAO emettitoreDAO = new EmettitoreDAO(em);
    private static final MezzoDAO mezzoDAO = new MezzoDAO(em);

    public static void main(String[] args) {


        em.close();
    }

    private static final Supplier<Emettitore> emettitoreSupplier = () -> {
        EmettitoreEnum tipologia = EmettitoreEnum.values()[new Random().nextInt(EmettitoreEnum.values().length)];
        EmettitoreStato stato = null;
        if (tipologia == EmettitoreEnum.DISTRIBUTORE) {
            stato = EmettitoreStato.values()[new Random().nextInt(EmettitoreStato.values().length)];
        }
        String city = faker.address().city();

        String cap = faker.address().zipCode();
        String country = faker.address().country();

        try {
            return new Emettitore(faker.company().name(), faker.address().streetAddress(), faker.number().digits(3), faker.address().state(), city, cap, country, tipologia, stato);
        } catch (EmettitoreException e) {
            System.out.println(e.getMessage());
        }
        return null;
    };


    private static final BigliettoSupplier bigliettoSupplier = (Emettitore emettitore, Mezzo mezzo) -> {
        LocalDate dataEmissione = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean vidimato = faker.bool().bool();
        LocalDate dataVidimazione = null;
        if (vidimato) {
            dataVidimazione = dataEmissione.plusDays(faker.number().numberBetween(0, 365));
        } else {
            mezzo = null;
        }

        return new Biglietto(dataEmissione, emettitore, vidimato, dataVidimazione, mezzo);
    };


    private static void fillDatabase() {

        List<Emettitore> emettitoreList = emettitoreDAO.getFirst();
        if (emettitoreList.isEmpty()) {
            for (int i = 0; i < 100; i++) {
                emettitoreDAO.save(emettitoreSupplier.get());
            }
        }

    }

}
