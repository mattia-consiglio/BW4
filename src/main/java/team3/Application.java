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
import java.util.logging.Level;
import java.util.logging.Logger;

import static team3.Menus.mainMenu;

public class Application {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4t3");
    public static final EntityManager em = emf.createEntityManager();

    public static final Scanner scanner = new Scanner(System.in);
    public static final Faker faker = new Faker();
    public static final EmettitoriDAO emettitoriDAO = new EmettitoriDAO(em);
    public static final MezziDAO mezziDAO = new MezziDAO(em);
    public static final TitoliViaggioDAO titoliViaggioDAO = new TitoliViaggioDAO(em);
    public static final UtentiDAO utentiDAO = new UtentiDAO(em);
    public static final TessereDAO tessereDAO = new TessereDAO(em);
    public static final StatoMezzoDAO statoMezzoDAO = new StatoMezzoDAO(em);
    public static final TratteDAO tratteDAO = new TratteDAO(em);
    public static final MezziTrattaDAO mezziTrattaDAO = new MezziTrattaDAO(em);
    public static final Supplier<Tratta> trattaSupplier = () -> {
        return new Tratta(faker.address().fullAddress(), faker.address().fullAddress(), faker.number().numberBetween(1, 180));
    };
    public static final MezzoTrattaSupplier mezzoTrattaSupplier = (List<Mezzo> mezzi, List<Tratta> tratte) -> {
        Tratta tratta = tratte.get(new Random().nextInt(tratte.size()));
        Mezzo mezzo = mezzi.get(new Random().nextInt(mezzi.size()));
        return new MezzoTratta(mezzo, tratta, faker.number().numberBetween(1, 180));
    };
    public static final Supplier<Emettitore> emettitoreSupplier = () -> {
        EmettitoreTipo tipologia = EmettitoreTipo.values()[new Random().nextInt(EmettitoreTipo.values().length - 1)]; //escudo il tipo ONLINE
        EmettitoreStato stato = null;
        if (tipologia == EmettitoreTipo.DISTRIBUTORE) {
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
    public static final Supplier<Mezzo> mezzoSupplier = () -> {
        TipoMezzo tipoMezzo = TipoMezzo.values()[new Random().nextInt(EmettitoreStato.values().length)];
        return new Mezzo(faker.number().numberBetween(45, 150), tipoMezzo);
    };

    public static final BigliettoSupplier bigliettoSupplier = (List<Emettitore> emettitoreList, List<Mezzo> mezzi, List<Utente> utenti) -> {
        Emettitore emettitore = emettitoreList.get(new Random().nextInt(emettitoreList.size()));
        LocalDate dataEmissione = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean vidimato = faker.bool().bool();
        LocalDate dataVidimazione = null;
        Mezzo mezzo = null;
        Utente utente = null;
        if (vidimato) {
            dataVidimazione = dataEmissione.plusDays(faker.number().numberBetween(0, 365));
            mezzo = mezzi.get(new Random().nextInt(mezzi.size()));
        }

        if (emettitore.equals(EmettitoreTipo.ONLINE)) {
            utente = utenti.get(new Random().nextInt(utenti.size()));
        }

        return new Biglietto(dataEmissione, emettitore, vidimato, dataVidimazione, mezzo, utente);
    };

    public static final Supplier<Utente> utentiSupplier = () -> {
        LocalDate dataNascita = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return new Utente(faker.name().firstName(), faker.name().lastName(), dataNascita, faker.address().streetAddress(), faker.address().cityName(), faker.address().city(), faker.address().zipCode(), faker.address().country());
    };

    public static final TesseraSupplier tesserasupplier = (List<Utente> utenti) -> {
        Utente utente = utenti.get(new Random().nextInt(utenti.size()));
        LocalDate dataEmissione = faker.date().past(700, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return new Tessera(utente, dataEmissione, faker.bool().bool());
    };

    public static final AbbonamentoSupplier abbonamentoSupplier = (List<Tessera> tessere, List<Emettitore> emettitori) -> {
        Tessera tessera = tessere.get(new Random().nextInt(tessere.size()));
        LocalDate dataEmissione = faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Emettitore emettitore = emettitori.get(new Random().nextInt(emettitori.size()));
        TipoAbbonamento tipoAbbonamento = TipoAbbonamento.values()[new Random().nextInt(TipoAbbonamento.values().length)];
        return new Abbonamento(dataEmissione, emettitore, tipoAbbonamento, tessera);
    };

    public static final StatoMezzoSupplier statoMezzoSupplier = (List<Mezzo> mezzi) -> {
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

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.WARNING);
        fillDatabase();

        mainMenu();
        em.close();
    }

    private static void fillDatabase() {

        final int dataQuantity = 150;

        // get all Emittitori
        List<Emettitore> emettitoreList = emettitoriDAO.getAll();

        // add Emittitori to DB if the list is empty
        if (emettitoreList.isEmpty()) {
            try {
                emettitoriDAO.save(new Emettitore("Online shop", "", "", "", "", "", "", EmettitoreTipo.ONLINE, null));
            } catch (EmettitoreException e) {
                System.err.println(e.getMessage());
            }
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


        List<Utente> utenteList = utentiDAO.getAll();

        if (utenteList.isEmpty()) {
            for (int i = 0; i < dataQuantity; i++) {
                utentiDAO.save(utentiSupplier.get());
            }
            utenteList = utentiDAO.getAll();
        }

        List<Biglietto> bigliettoList = titoliViaggioDAO.getFirstBiglietto();

        if (bigliettoList.isEmpty()) {
            for (int i = 0; i < dataQuantity * 3; i++) {
                titoliViaggioDAO.save(bigliettoSupplier.get(emettitoreList, mezzoList, utenteList));
            }
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
