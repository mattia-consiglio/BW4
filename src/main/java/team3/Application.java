package team3;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.EmettitoreDAO;
import team3.entities.Emettitore;
import team3.entities.EmettitoreEnum;
import team3.entities.EmettitoreStato;
import team3.exceptions.EmettitoreException;

import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4t3");
    private static final EntityManager em = emf.createEntityManager();
    private static final Faker faker = new Faker();


    public static void main(String[] args) {
        EmettitoreDAO ed = new EmettitoreDAO(em);

        for (int i = 0; i < 100; i++) {

            ed.save(emettitoreSupplier.get());
        }


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
    public static int mainMenu(Scanner scanner) {

        while (true) {
            System.out.println("--------- Main menu ---------");
            System.out.println();
            System.out.println("Scegli un' opzione");
            System.out.println("1. Visualizza titoli di viaggio emessi in un periodo di tempo e per punto di emissione");
            System.out.println("2. Verifica validità abbonamento da numero tessera");
            System.out.println("3. Visualizza numero biglietti vidimati su un mezzo e in un periodo di tempo");
            System.out.println("4. Visualizza numero biglietti vidimati su un mezzo");
            System.out.println("5. Visualizza numero biglietti vidimati in un periodo di tempo");
            System.out.println("6. Aggiungi titolo di viaggio (biglietto/ abbonamento)");
            System.out.println("7. Aggiungi emettitore (Rivenditore / Distributore)");
            System.out.println("8. Aggiungi tessera");
            System.out.println("9. Aggiungi utente");
            System.out.println("10. Aggiungi mezzo");
            System.out.println("11. Imposta stato mezzo");
            System.out.println("12. Aggiungi tratta");
            System.out.println("13. Aggiungi tratta percorsa");
            System.out.println("0. Chiudi");


            String option = scanner.nextLine();

            switch (option) {
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13": {
                    return Integer.parseInt(option);
                }
                default:
                    System.err.println("Opzione non valida, scegliere un'opzione valida");
            }
        }
    }

    

}
