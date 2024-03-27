package team3;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.*;
import team3.entities.*;
import team3.entities.suppliers.*;
import team3.exceptions.EmettitoreException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4t3");
    private static final EntityManager em = emf.createEntityManager();
    private static final Faker faker = new Faker();
    private static final EmettitoreDAO emettitoriDAO = new EmettitoreDAO(em);
    private static final MezziDAO mezziDAO = new MezziDAO(em);
    private static final TitoliViaggioDAO titoliViaggioDAO = new TitoliViaggioDAO(em);
    private static final UtentiDAO utentiDAO = new UtentiDAO(em);
    private static final TessereDAO tessereDAO = new TessereDAO(em);
    private static final StatoMezzoDAO statoMezzoDAO = new StatoMezzoDAO(em);
    private static final TratteDAO tratteDAO = new TratteDAO(em);
    private static final MezziTrattaDAO mezziTrattaDAO = new MezziTrattaDAO(em);


    public static void main(String[] args) {
        fillDatabase();
        List<Biglietto> ListaBigliettiVidimitateSuDeterminatoMezzo = mezziDAO.ListaBigliettiVidimitateSuDeterminatoMezzo(111L);
        System.out.println(ListaBigliettiVidimitateSuDeterminatoMezzo);
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
            option = option.trim();
            switch (option) {
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13": {
                    return Integer.parseInt(option);
                }
                default:
                    System.err.println("Opzione non valida, scegliere un'opzione valida");
            }
        }
    }


    private static final Supplier<Mezzo> mezzoSupplier = () -> {
        TipoMezzo tipoMezzo = TipoMezzo.values()[new Random().nextInt(EmettitoreStato.values().length)];
        return new Mezzo(faker.number().numberBetween(45, 150), tipoMezzo);
    };

    private static final BigliettoSupplier bigliettoSupplier = (List<Emettitore> emettitoreList, List<Mezzo> mezzi) -> {
        Emettitore emettitore = emettitoreList.get(new Random().nextInt(emettitoreList.size()));
        LocalDate dataEmissione = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean vidimato = faker.bool().bool();
        LocalDate dataVidimazione = null;
        Mezzo mezzo = null;
        if (vidimato) {
            dataVidimazione = dataEmissione.plusDays(faker.number().numberBetween(0, 365));
            mezzo = mezzi.get(new Random().nextInt(mezzi.size()));
        }

        return new Biglietto(dataEmissione, emettitore, vidimato, dataVidimazione, mezzo);
    };

    private static final Supplier<Utente> utentiSupplier = () -> {
        LocalDate dataNascita = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return new Utente(faker.name().firstName(), faker.name().lastName(), dataNascita, faker.address().streetAddress(), faker.address().cityName(), faker.address().city(), faker.address().zipCode(), faker.address().country());
    };

    private static final TesseraSupplier tesserasupplier = (List<Utente> utenti) -> {
        Utente utente = utenti.get(new Random().nextInt(utenti.size()));
        LocalDate dataEmissione = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return new Tessera(utente, dataEmissione, faker.bool().bool());
    };

    private static final AbbonamentoSupplier abbonamentoSupplier = (List<Tessera> tessere, List<Emettitore> emettitori) -> {
        Tessera tessera = tessere.get(new Random().nextInt(tessere.size()));
        LocalDate dataEmissione = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Emettitore emettitore = emettitori.get(new Random().nextInt(emettitori.size()));
        TipoAbbonamento tipoAbbonamento = TipoAbbonamento.values()[new Random().nextInt(TipoAbbonamento.values().length)];
        return new Abbonamento(dataEmissione, emettitore, tipoAbbonamento, tessera);
    };

    private static final StatoMezzoSupplier statoMezzoSupplier = (List<Mezzo> mezzi) -> {
        Mezzo mezzo = mezzi.get(new Random().nextInt(mezzi.size()));

        CondizioneMezzo condizioneMezzo = CondizioneMezzo.values()[new Random().nextInt(CondizioneMezzo.values().length)];
        LocalDate dataInizio = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dataFine = null;
        if (condizioneMezzo.equals(CondizioneMezzo.IN_MANUTENZIONE)) {

            dataFine = dataInizio.plusDays(faker.number().numberBetween(1, 30));
        } else {
            dataFine = dataInizio.plusDays(faker.number().numberBetween(1, 365));

        }
        return new StatoMezzo(condizioneMezzo, dataInizio, dataFine, mezzo);
    };

    public static final Supplier<Tratta> trattaSupplier = () -> {
        return new Tratta(faker.address().fullAddress(), faker.address().fullAddress(), faker.number().numberBetween(1, 180));
    };

    public static final MezzoTrattaSupplier mezzoTrattaSupplier = (List<Mezzo> mezzi, List<Tratta> tratte) -> {
        Tratta tratta = tratte.get(new Random().nextInt(tratte.size()));
        Mezzo mezzo = mezzi.get(new Random().nextInt(mezzi.size()));
        return new MezzoTratta(mezzo, tratta, faker.number().numberBetween(1, 180));
    };


    private static void fillDatabase() {

        final int dataQuantity = 150;

        // get all Emittitori
        List<Emettitore> emettitoreList = emettitoriDAO.getAll();

        // add Emittitori to DB if the list is empty
        if (emettitoreList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                emettitoriDAO.save(emettitoreSupplier.get());
            }
            emettitoreList = emettitoriDAO.getAll();
        }

        List<Mezzo> mezzoList = mezziDAO.getAll();

        if (mezzoList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                mezziDAO.save(mezzoSupplier.get());
            }
            mezzoList = mezziDAO.getAll();
        }

        List<Biglietto> bigliettoList = titoliViaggioDAO.getFirstBiglietto();

        if (bigliettoList.isEmpty()) {
            for (int i = 0; i < dataQuantity * 3; i++) {
                titoliViaggioDAO.save(bigliettoSupplier.get(emettitoreList, mezzoList));
            }
        }

        List<Utente> utenteList = utentiDAO.getAll();

        if (utenteList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                utentiDAO.save(utentiSupplier.get());
            }
            utenteList = utentiDAO.getAll();
        }

        List<Tessera> tesseraList = tessereDAO.getAll();

        if (tesseraList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                tessereDAO.save(tesserasupplier.get(utenteList));
            }
            tesseraList = tessereDAO.getAll();
        }

        List<Abbonamento> abbonamentoList = titoliViaggioDAO.getAllAbbonamenti();

        if (abbonamentoList.isEmpty()) {
            for (int i = 0; i < dataQuantity * 2; i++) {
                titoliViaggioDAO.save(abbonamentoSupplier.get(tesseraList, emettitoreList));
            }
        }

        List<StatoMezzo> statoMezzoList = statoMezzoDAO.getAll();

        if (statoMezzoList.isEmpty()) {
            for (int i = 0; i < dataQuantity * 2; i++) {
                statoMezzoDAO.save(statoMezzoSupplier.get(mezzoList));
            }
        }

        List<Tratta> trattaList = tratteDAO.getAll();
        if (trattaList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                tratteDAO.save(trattaSupplier.get());
            }
            trattaList = tratteDAO.getAll();
        }


        List<MezzoTratta> mezzoTrattaList = mezziTrattaDAO.getAll();
        if (mezzoTrattaList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                mezziTrattaDAO.save(mezzoTrattaSupplier.get(mezzoList, trattaList));
            }
        }
    }

}
