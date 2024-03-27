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
    private static final EmettitoreDAO emettitoriDAO = new EmettitoreDAO(em);
    private static final MezziDAO mezziDAO = new MezziDAO(em);
    private static final TitoliViaggioDAO titoliViaggioDAO = new TitoliViaggioDAO(em);
    private static final UtentiDAO utentiDAO = new UtentiDAO(em);
    private static final TessereDAO tessereDAO = new TessereDAO(em);
    private static final StatoMezzoDAO statoMezzoDAO = new StatoMezzoDAO(em);
    private static final TratteDAO tratteDAO = new TratteDAO(em);
    private static final MezziTrattaDAO mezziTrattaDAO = new MezziTrattaDAO(em);
    private static final Faker faker = new Faker();
    public static final Supplier<Tratta> trattaSupplier = () -> {
        return new Tratta(faker.address().fullAddress(), faker.address().fullAddress(), faker.number().numberBetween(1, 180));
    };
    public static final MezzoTrattaSupplier mezzoTrattaSupplier = (List<Mezzo> mezzi, List<Tratta> tratte) -> {
        Tratta tratta = tratte.get(new Random().nextInt(tratte.size()));
        Mezzo mezzo = mezzi.get(new Random().nextInt(mezzi.size()));
        return new MezzoTratta(mezzo, tratta, faker.number().numberBetween(1, 180));
    };
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

    public static void main(String[] args) {
        fillDatabase();

        em.close();
    }

    public static int mainMenu(Scanner scanner) {

        while (true) {
            System.out.println("--------- Main menu ---------");
            System.out.println();
            System.out.println("Scegli un' opzione");
            System.out.println("1. sei utente");
            System.out.println("2. sei amministratore");


            String option = scanner.nextLine();
            option = option.trim();
            switch (option) {
                case "0": {
                    System.out.println("Chiusura programma in corso...");
                    break;
                }
                case "1": {

                }
                case "2": {
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
                    String option2 = scanner.nextLine();
                    switch (option2) {
                        case "0": {
                            System.out.println("Chiusura programma in corso...");
                            break;
                        }

                        case "1": {
                            System.out.println("inserire data inizio");
                            LocalDate dataInizio = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserire data fine");
                            LocalDate dataFine = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserire punto di emissione");
                            String puntoEmissione = scanner.nextLine();
                            break;
                        }
                        case "2": {
                            System.out.println();
                            System.out.println("Inserire numero tessera");
                            String numeroTessera = scanner.nextLine();
                            break;
                        }
                        case "3": {
                            System.out.println("inserire data inizio");
                            LocalDate dataInizio = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserire data fine");
                            LocalDate dataFine = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserire mezzo");
                            String mezzo = scanner.nextLine();
                            break;
                        }
                        case "4": {
                            System.out.println("inserisci tipo di mezzo");
                            String tipoMezzo = scanner.nextLine();
                            break;
                        }
                        case "5": {
                            System.out.println("inserire data inizio");
                            LocalDate dataInizio = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserire data fine");
                            LocalDate dataFine = LocalDate.parse(scanner.nextLine());
                            break;
                        }
                        case "6": {
                            System.out.println("inserire tipo di titolo biglietto/abbonamento");
                            String tipoTitolo = scanner.nextLine();
                            if (tipoTitolo.equals("biglietto")) {
                                //salva biglietto
                            } else {
                                //salva abbonamento
                            }
                            break;

                        }
                        case "7": {
                            System.out.println("inserisci tipo di emettitore rivenditore/distributore");
                            String tipoEmettitore = scanner.nextLine();
                            if (tipoEmettitore.equalsIgnoreCase("rivenditore")) {
                                //salva tipo emettitore rivenditore
                            } else {
                                // salva tipo emettitore distributore
                            }
                        }
                        case "8": {
                            //creazione utente
                            System.out.println("inserisci nome");
                            String nome = scanner.nextLine();
                            System.out.println("inserisci cognome");
                            String cognome = scanner.nextLine();
                            System.out.println("inserisci data di nascita");
                            LocalDate dataNascita = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserisci via");
                            String via = scanner.nextLine();
                            System.out.println("inserisci provincia");
                            String provincia = scanner.nextLine();
                            System.out.println("inserisci citta");
                            String citta = scanner.nextLine();
                            System.out.println("inserisci cap");
                            String cap = scanner.nextLine();
                            System.out.println();
                            System.out.println("inserisci nazione");
                            String nazione = scanner.nextLine();
                            Utente utente = new Utente(nome, cognome, dataNascita, via, provincia, citta, cap, nazione);
                            //creazione tessera
                            LocalDate dataInizio = LocalDate.now();
                            boolean validita = true;
                            Tessera newtessera = new Tessera(utente, dataInizio, validita);
                            break;
                        }
                        case "9": {
                            System.out.println("inserisci nome");
                            String nome = scanner.nextLine();
                            System.out.println("inserisci cognome");
                            String cognome = scanner.nextLine();
                            System.out.println("inserisci data di nascita");
                            LocalDate dataNascita = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserisci via");
                            String via = scanner.nextLine();
                            System.out.println("inserisci provincia");
                            String provincia = scanner.nextLine();
                            System.out.println("inserisci citta");
                            String citta = scanner.nextLine();
                            System.out.println("inserisci cap");
                            String cap = scanner.nextLine();
                            System.out.println();
                            System.out.println("inserisci nazione");
                            String nazione = scanner.nextLine();
                            Utente utente = new Utente(nome, cognome, dataNascita, via, provincia, citta, cap, nazione);
                            break;
                        }
                        case "10": {
                            System.out.println("inserisci capienza mezzo");
                            int capienzaMezzo = scanner.nextInt();
                            System.out.println("inserisci tipo mezzo autobus/tram");
                            String tipoMezzo = scanner.nextLine();
                            TipoMezzo tipoMezzoEnum = TipoMezzo.valueOf(tipoMezzo.toUpperCase());
                            Mezzo mezzo = new Mezzo(capienzaMezzo, tipoMezzoEnum);
                            break;
                        }
                        case "11": {
                            //creazione stato mezzo
                            System.out.println("inserisci lo stato del mezzo nel segente modo in_manutenzione/in_servizio");
                            String condizioneMezzo = scanner.nextLine();
                            CondizioneMezzo statoMezzoEnum = CondizioneMezzo.valueOf(condizioneMezzo.toUpperCase());
                            System.out.println("inserisci la data di inizio");
                            LocalDate dataInizio = LocalDate.parse(scanner.nextLine());
                            System.out.println("inserisci la data di fine");
                            LocalDate dataFine = LocalDate.parse(scanner.nextLine());

                            //creazione del mezzo
                            System.out.println("inserisci capienza mezzo");
                            int capienzaMezzo = scanner.nextInt();
                            System.out.println("inserisci tipo mezzo autobus/tram");
                            String tipoMezzo = scanner.nextLine();
                            TipoMezzo tipoMezzoEnum = TipoMezzo.valueOf(tipoMezzo.toUpperCase());
                            Mezzo mezzo = new Mezzo(capienzaMezzo, tipoMezzoEnum);

                            //creazione stato mezzo
                            StatoMezzo statoMezzo = new StatoMezzo(statoMezzoEnum, dataInizio, dataFine, mezzo);
                            break;
                        }
                        case "12": {

                            System.out.println("inserisci punto partenza");
                            String puntoPartenza = scanner.nextLine();
                            System.out.println("inserisci capolinea");
                            String capolinea = scanner.nextLine();
                            System.out.println("inserisci tempo medio di percorrenza in minuti");
                            int tempoMedioPercorrenza = scanner.nextInt();
                            Tratta tratta = new Tratta(puntoPartenza, capolinea, tempoMedioPercorrenza);

                        }
                        case "13": {
                            List<Mezzo> listaMezzi = mezziDAO.getAll();
                            System.out.println("scegli id mezzo");
                            long idMezzo = scanner.nextInt();
                            Mezzo mezzo = listaMezzi.stream().filter(m -> m.getId() == idMezzo).findFirst().get();
                            List<Tratta> listaTratte = tratteDAO.getAll();
                            System.out.println("scegli id tratta");
                            long idTratta = scanner.nextInt();
                            Tratta tratta = listaTratte.stream().filter(t -> t.getId() == idTratta).findFirst().get();
                            System.out.println("inserisci tempo effettivo di percorrenza");
                            int tempoEffettivoPercorrenza = scanner.nextInt();
                            MezzoTratta trattaPercorrenza = new MezzoTratta(mezzo, tratta, tempoEffettivoPercorrenza);
                            mezziTrattaDAO.save(trattaPercorrenza);
                            break;

                        }
                    }
                    break;
                }
                default:
                    System.err.println("Opzione non valida, scegliere un'opzione valida");
            }


        }
    }

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
