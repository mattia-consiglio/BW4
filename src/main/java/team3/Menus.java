package team3;

import team3.entities.*;
import team3.exceptions.EmettitoreException;

import java.time.LocalDate;
import java.util.List;

import static team3.Application.*;
import static team3.Utilities.*;

public class Menus {
    public static void mainMenu() {

        while (true) {
            System.out.println("--------- Menu principale ---------");
            System.out.println();
            System.out.println("Scegli un'opzione");
            System.out.println("1. Entra come utente");
            System.out.println("2. Entra come amministratore");
            System.out.println("0. Esci dall'applicazione");
            System.out.println();


            String option = Application.scanner.nextLine().trim();

            switch (option) {
                case "0": {
                    System.out.println("Chiusura applicazione in corso...");
                    return;
                }
                case "1": {
                    if (!userMenu()) {
                        System.out.println("Chiusura applicazione in corso...");
                        return;
                    }

                    break;
                }
                case "2": {
                    if (!adminMenu()) {
                        System.out.println("Chiusura applicazione in corso...");
                        return;
                    }

                    break;
                }
                default:
                    System.err.println("Opzione non valida, scegliere un'opzione valida");
            }
        }
    }

    public static boolean userMenu() {
        Tessera loggedUserTessera = null;

        while (true) {
            System.out.println();
            System.out.println("--------- Menu Utente ---------");
            System.out.println();
            System.out.println("Scegli un'opzione");
            System.out.println("1. Vidima biglietto");
            System.out.println("2. Acquista biglietto");
            System.out.println("3. Visualizza tratte");
            System.out.println("4. Entra");
            System.out.println("5. Registrati ed entra");
            System.out.println("0. Torna al menu principale");
            System.out.println("00. Esci dall'applicazione");
            System.out.println();
            String option = Application.scanner.nextLine().trim();


            switch (option) {
                case "0": {
                    return true;
                }
                case "00": {
                    return false;
                }
                case "1": {
                    Biglietto biglietto = askAndVerifyList("Segli un biglietto da vidimare", titoliViaggioDAO.listaBigliettiNonVidimitatiGenerici(), "Biglietto", true);
                    Mezzo mezzo = askAndVerifyList("Scegli il mezzo", mezziDAO.getAll(), "Mezzo", true);
                    assert biglietto != null;
                    biglietto.vidima(mezzo);
                    try {
                        titoliViaggioDAO.updateBiglietto(biglietto);
                    } catch (RuntimeException e) {
                        System.err.println("Errore durante la vidimazione del biglietto: " + e.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                }
                case "2": {
                    EmettitoreTipo emettitoreTipo = askAndVerifyEnum("Scegli dove acquistare il biglietto", EmettitoreTipo.class, 1);
                    Emettitore emettitore = null;
                    if (emettitoreTipo.equals(EmettitoreTipo.DISTRIBUTORE)) {
                        emettitore = askAndVerifyList("Scegli il distributore", emettitoriDAO.getAllDistributoriAttivi(), "Distributore", true);
                    }
                    if (emettitoreTipo.equals(EmettitoreTipo.RIVENDITORE)) {
                        emettitore = askAndVerifyList("Scegli il rivenditore", emettitoriDAO.getAllRivenditori(), "Rivenditore", true);
                    }
                    int quanity = askAndVerifyInt("Inserisci la quantità di biglietti da acquistare");
                    for (int i = 0; i < quanity; i++) {
                        Biglietto biglietto = new Biglietto(LocalDate.now(), emettitore, false, null, null, null);
                        titoliViaggioDAO.save(biglietto);
                    }
                    pressEnterToContinue();
                    break;
                }
                case "3": {
                    System.out.println("Lista tratte diponibili");
                    tratteDAO.getAll().forEach(System.out::println);
                    pressEnterToContinue();
                    break;
                }
                case "4": {
                    if (loggedUserTessera != null) {
                        System.out.println("Bentornato/a " + loggedUserTessera.getUtente().getNome() + " " + loggedUserTessera.getUtente().getCognome());
                    } else {
                        loggedUserTessera = askAndVerifyList("Inserici il tuo numero tessera", tessereDAO.getAll(), "Tessera", false);

                        assert loggedUserTessera != null;
                        System.out.println("Benventuto/a " + loggedUserTessera.getUtente().getNome() + " " + loggedUserTessera.getUtente().getCognome());
                    }
                    loggedUserTessera = loggedUserMenu(loggedUserTessera);
                    break;
                }
                case "5": {
                    String nome = askAndVerifyString("Inserisci il nome dell'utente");
                    String cognome = askAndVerifyString("Inserisci il cognome dell'utente");
                    LocalDate dataNascita = askAndVerifyDate("Inserisci la data di nascita dell'utente (aaaa-mm-gg)");
                    String via = askAndVerifyString("Inserisci la via dell'utente");
                    String provincia = askAndVerifyString("Inserisci la provincia dell'utente");
                    String citta = askAndVerifyString("Inserisci la citta dell'utente");
                    String cap = askAndVerifyString("Inserisci il cap dell'utente", 5);
                    String nazione = askAndVerifyString("Inserisci la nazione dell'utente");
                    Utente utente = new Utente(nome, cognome, dataNascita, via, provincia, citta, cap, nazione);

                    utente = utentiDAO.save(utente);
                    loggedUserTessera = new Tessera(utente, LocalDate.now(), true);
                    Application.tessereDAO.save(loggedUserTessera);

                    loggedUserTessera = loggedUserMenu(loggedUserTessera);
                    break;
                }
                default:
                    System.err.println("Opzione non valida, riprova");
            }
        }

    }


    public static Tessera loggedUserMenu(Tessera loggedUserTessera) {
        Utente loggedUser = loggedUserTessera.getUtente();
        while (true) {
            System.out.println();
            System.out.println("--------- Menu Utente [" + loggedUser.getNome() + " " + loggedUser.getCognome() + "]  ---------");
            System.out.println();
            System.out.println("Scegli un'opzione");
            System.out.println("1. Visualizza i tuoi dati");
            System.out.println("2. Visualizza i tuoi abbonamenti in corso di validità");
            System.out.println("3. Visualizza i tuoi abbonamenti");
            System.out.println("4. Visualizza biglietti acquistati online");
            System.out.println("5. Acquista un abbonamento");
            System.out.println("6. Acquista un biglietto (ONLINE)");
            System.out.println("7. Vidima un tuo biglietto");
            System.out.println("8. Visualizza tratte");
            System.out.println("0. Torna al menu utente");
            System.out.println("00. Esci dall'accont e torna al menu utente");

            System.out.println();
            String option = Application.scanner.nextLine().trim();

            switch (option) {
                case "0": {
                    return loggedUserTessera;
                }
                case "00": {
                    System.out.println("Logout effettuato con successo");
                    return null;
                }
                case "1": {
                    System.out.println("I tuoi dati sono: ");
                    System.out.println(loggedUser);
                    pressEnterToContinue();
                    break;
                }
                case "2": {
                    System.out.println("Lista abbonamenti in corso: ");
                    titoliViaggioDAO.getAbbonamentiValidiByUser(loggedUser).forEach(System.out::println);
                    pressEnterToContinue();
                    break;

                }
                case "3": {
                    System.out.println("Lista abbonamenti: ");
                    titoliViaggioDAO.getAbbonamentiByUser(loggedUser).forEach(System.out::println);
                    pressEnterToContinue();
                    break;

                }
                case "4": {
                    List<Biglietto> biglietti = titoliViaggioDAO.getBigliettiAcquistiOnlineByUtente(loggedUser);
                    if (biglietti.isEmpty()) {
                        System.out.println("Nessun biglietto acquistato online");
                    } else {
                        System.out.println("Lista bilietti acquistati online");
                        biglietti.forEach(System.out::println);
                    }
                    pressEnterToContinue();
                    break;

                }
                case "5": {

                    LocalDate dataInizio = LocalDate.now();
                    Emettitore emettitore = emettitoriDAO.findById(1);
                    TipoAbbonamento tipoAbbonamento = askAndVerifyEnum("Inserisci il tipo di abbonamento", TipoAbbonamento.class);
                    Abbonamento abbonamento = new Abbonamento(dataInizio, emettitore, tipoAbbonamento, loggedUserTessera);
                    titoliViaggioDAO.save(abbonamento);

                    pressEnterToContinue();
                    break;
                }
                case "6": {
                    int quanity = askAndVerifyInt("Inserisci la quantità di biglietti da acquistare");
                    for (int i = 0; i < quanity; i++) {
                        Biglietto biglietto = new Biglietto(LocalDate.now(), emettitoriDAO.findById(1), false, null, null, loggedUser);
                        titoliViaggioDAO.save(biglietto);
                    }
                    pressEnterToContinue();
                    break;
                }
                case "7": {
                    Biglietto biglietto = askAndVerifyList("Segli un biglietto da vidimare", titoliViaggioDAO.listaBigliettiNonVidimitatiUtente(loggedUser), "Biglietto", true);
                    if (biglietto == null) {
                        break;
                    }
                    Mezzo mezzo = askAndVerifyList("Scegli il mezzo", mezziDAO.getAll(), "Mezzo", true);

                    biglietto.vidima(mezzo);
                    try {
                        titoliViaggioDAO.updateBiglietto(biglietto);
                    } catch (RuntimeException e) {
                        System.err.println("Errore durante la vidimazione del biglietto: " + e.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                }
                case "8": {
                    System.out.println("Lista tratte diponibili");
                    tratteDAO.getAll().forEach(System.out::println);
                    pressEnterToContinue();
                    break;
                }
                default:
                    System.err.println("Opzione non valida, riprova");
            }
        }
    }

    public static boolean adminMenu() {
        while (true) {
            System.out.println();
            System.out.println("--------- Menu Amministratore ---------");
            System.out.println();
            System.out.println("Scegli un'opzione");
            System.out.println("1. Visualizza quantità titoli di viaggio emessi in un periodo di tempo e per punto di emissione");
            System.out.println("2. Visualizza quantità biglietti vidimati su un mezzo e in un periodo di tempo");
            System.out.println("3. Visualizza quantità biglietti vidimati su un mezzo");
            System.out.println("4. Visualizza quantità biglietti vidimati in un periodo di tempo");
            System.out.println("5. Verifica che ci sia almento un abbonamento in corso si valità su una tessera");
            System.out.println("6. Aggiungi titolo di viaggio (biglietto/ abbonamento)");
            System.out.println("7. Aggiungi punto di emissione (Rivenditore / Distributore)");
            System.out.println("8. Aggiungi tessera");
            System.out.println("9. Aggiungi utente");
            System.out.println("10. Aggiungi mezzo");
            System.out.println("11. Imposta stato mezzo");
            System.out.println("12. Aggiungi tratta");
            System.out.println("13. Aggiungi tratta percorsa");
            System.out.println("14. Visualizza storico manutenzioni mezzo");
            System.out.println("15. Rinnova tessera");
            System.out.println("16. Pratica tessera persa");
            System.out.println("17. Modifica utente");
            System.out.println("18. Modifica stato mezzo");
            System.out.println("19. Modifica tratta");
            System.out.println("20. Modifica punto di emissione");
            System.out.println("21. Contrassegna eliminato punto di emissione");
            System.out.println("22. Elimina utente");
            System.out.println("23. Elimina mezzo");
            System.out.println("0. Torna al menu pricipale");
            System.out.println("00. Esci dall'applicazione");
            System.out.println();
            String option = Application.scanner.nextLine().trim();
            switch (option) {
                case "00": {
                    return false;
                }
                case "0": {
                    return true;
                }

                case "1": {
                    TipoTitoloViaggio tipoTitoloViaggio = askAndVerifyEnum("Scegli il tipo di titolo di viaggio", TipoTitoloViaggio.class);

                    LocalDate dataInizio = askAndVerifyDate("Inserisci data inizio (aaaa-mm-gg)");
                    LocalDate dataFine = askAndVerifyDate("Inserisci data fine (aaaa-mm-gg)");
                    while (dataInizio.isAfter(dataFine)) {
                        System.err.println("Data fine non valida, inserisci una data valida");
                        dataFine = askAndVerifyDate("Inserisci data fine");
                    }
                    Emettitore emettitore = askAndVerifyList("Scegli l'ID dell'Emettitore", Application.emettitoriDAO.getAll(), "Emettitore", true);
                    assert emettitore != null;
                    long count;
                    if (tipoTitoloViaggio.equals(TipoTitoloViaggio.BIGLIETTO)) {
                        count = titoliViaggioDAO.getNumberBigliettiByPeriodo(dataInizio, dataFine, emettitore);
                        System.out.println("Numero biglietti emessi in questo periodo e per emettitore: " + count);
                    }
                    if (tipoTitoloViaggio.equals(TipoTitoloViaggio.ABBONAMENTO)) {
                        count = titoliViaggioDAO.getNumberAbbonamentiByPeriodo(dataInizio, dataFine, emettitore);
                        System.out.println("Numero abbonamenti emessi in questo periodo e per emettitore: " + count);
                    }
                    pressEnterToContinue();
                    break;
                }


                case "2": {
                    Mezzo mezzo = askAndVerifyList("Scegli l'ID di un mezzo", mezziDAO.getAll(), "Mezzo", true);
                    LocalDate dataInizio = askAndVerifyDate("Inserisci data inizio (aaaa-mm-gg)");
                    LocalDate dataFine = askAndVerifyDate("Inserisci data fine (aaaa-mm-gg)");
                    while (dataInizio.isAfter(dataFine)) {
                        System.err.println("Data fine non valida, inserisci una data valida");
                        dataFine = askAndVerifyDate("Inserisci data fine");
                    }
                    assert mezzo != null;
                    long count = titoliViaggioDAO.countBigliettiVidimatiSuMezzoEPeriodo(mezzo, dataInizio, dataFine);
                    System.out.println("Numero biglietti vidimati sul mezzo " + mezzo.getId() + " nel periodo " + dataInizio + " - " + dataFine + ": " + count);
                    pressEnterToContinue();
                    break;
                }
                case "3": {
                    Mezzo mezzo = askAndVerifyList("Scegli l'ID di un mezzo", mezziDAO.getAll(), "Mezzo", true);
                    assert mezzo != null;
                    long count = titoliViaggioDAO.countBigliettiVidimitatiSuMezzo(mezzo);
                    System.out.println("Numero abbonamenti emessi per il mezzo " + mezzo.getId() + ": " + count);

                    pressEnterToContinue();
                    break;
                }
                case "4": {
                    LocalDate dataInizio = askAndVerifyDate("Inserisci data inizio (aaaa-mm-gg)");
                    LocalDate dataFine = askAndVerifyDate("Inserisci data fine (aaaa-mm-gg)");
                    while (dataInizio.isAfter(dataFine)) {
                        System.err.println("Data fine non valida, inserisci una data valida");
                        dataFine = askAndVerifyDate("Inserisci data fine");
                    }
                    long count = titoliViaggioDAO.countBigliettiVidimatiInGenerale(dataInizio, dataFine);
                    System.out.println("Numero biglietti vidimati nel periodo" + dataInizio + " - " + dataFine + ": " + count);
                    pressEnterToContinue();
                    break;
                }
                case "5": {
                    Tessera tessera = askAndVerifyList("Inserisci il numero di tessera", Application.tessereDAO.getAll(), "Tessera", true);
                    if (titoliViaggioDAO.isAbbonamentoValidByTesseraNumero(tessera)) {
                        System.out.println("La tessera contiene almento un abbonamento in corso di validità");
                    } else {
                        System.out.println("La tessera non contiene abbonamenti validi");
                    }
                    pressEnterToContinue();
                    break;
                }
                case "6": {

                    TipoTitoloViaggio tipoTitoloViaggio = askAndVerifyEnum("Inserisci il tipo di titolo di viaggio", TipoTitoloViaggio.class);
                    LocalDate dataInizio = LocalDate.now();
                    Emettitore emettitore = askAndVerifyList("Scegli l'ID dell'emettitore", Application.emettitoriDAO.getAll(), "Emettitore", true);
                    if (tipoTitoloViaggio.equals(TipoTitoloViaggio.ABBONAMENTO)) {
                        TipoAbbonamento tipoAbbonamento = askAndVerifyEnum("Inserisci il tipo di abbonamento", TipoAbbonamento.class);
                        Tessera tessera = askAndVerifyList("Scegli l'ID di quale tessera asscociare l'abbonamento", Application.tessereDAO.getAll(), "Tessera", true);
                        Abbonamento abbonamento = new Abbonamento(dataInizio, emettitore, tipoAbbonamento, tessera);
                        titoliViaggioDAO.save(abbonamento);
                    } else {
                        Biglietto biglietto = new Biglietto(dataInizio, emettitore, false, null, null, null);
                        titoliViaggioDAO.save(biglietto);
                    }
                    pressEnterToContinue();
                    break;

                }
                case "7": {
                    EmettitoreTipo emettitoreTipo = askAndVerifyEnum("Segli un tipo di emettitore", EmettitoreTipo.class, 1);
                    String nome = askAndVerifyString("Inserisci il nome dell'emettitore");
                    String via = askAndVerifyString("Inserisci la via dell'emettitore");
                    String civico = askAndVerifyString("Inserisci il civico dell'emettitore");
                    String citta = askAndVerifyString("Inserisci la città dell'emettitore");
                    String provincia = askAndVerifyString("Inserisci la provincia dell'emettitore");
                    String cap = askAndVerifyString("Inserisci il cap dell'emettitore", 5);
                    String nazione = askAndVerifyString("Inserisci la nazione dell'emettitore");
                    EmettitoreStato emettitoreStato = null;
                    if (emettitoreTipo.equals(EmettitoreTipo.DISTRIBUTORE)) {
                        emettitoreStato = askAndVerifyEnum("Segli uno stato di emettitore", EmettitoreStato.class);
                    }


                    try {
                        Emettitore newEmettitore = new Emettitore(nome, via, civico, citta, provincia, cap, nazione, emettitoreTipo, emettitoreStato);
                        Application.emettitoriDAO.save(newEmettitore);
                    } catch (EmettitoreException e) {
                        System.err.println(e.getMessage());
                    }
                    pressEnterToContinue();
                    break;
                }
                case "8": {

                    Utente utente = askAndVerifyList("Scegli un ID utente", Application.utentiDAO.getAll(), "Utente", true);
                    Tessera newtessera = new Tessera(utente, LocalDate.now(), true);
                    Application.tessereDAO.save(newtessera);
                    pressEnterToContinue();
                    break;
                }
                case "9": {

                    String nome = askAndVerifyString("Inserisci il nome dell'utente");
                    String cognome = askAndVerifyString("Inserisci il cognome dell'utente");
                    LocalDate dataNascita = askAndVerifyDate("Inserisci la data di nascita dell'utente (aaaa-mm-gg)");
                    String via = askAndVerifyString("Inserisci la via dell'utente");
                    String provincia = askAndVerifyString("Inserisci la provincia dell'utente");
                    String citta = askAndVerifyString("Inserisci la citta dell'utente");
                    String cap = askAndVerifyString("Inserisci il cap dell'utente", 5);
                    String nazione = askAndVerifyString("Inserisci la nazione dell'utente");
                    Utente utente = new Utente(nome, cognome, dataNascita, via, provincia, citta, cap, nazione);
                    Application.utentiDAO.save(utente);
                    pressEnterToContinue();
                    break;
                }
                case "10": {

                    int capienzaMezzo = askAndVerifyInt("Inserisci capienza mezzo");


                    TipoMezzo tipoMezzoEnum = askAndVerifyEnum("Scegli tipo mezzo", TipoMezzo.class);
                    Mezzo mezzo = new Mezzo(capienzaMezzo, tipoMezzoEnum);
                    mezziDAO.save(mezzo);
                    pressEnterToContinue();
                    break;
                }
                case "11": {
                    //creazione stato mezzo

                    CondizioneMezzo condizioneMezzo = askAndVerifyEnum("Lista dei stati dei mezzi", CondizioneMezzo.class);

                    LocalDate dataInizio = askAndVerifyDate("Inserisci la data di inizio (aaaa-mm-gg)");

                    LocalDate dataFine = askAndVerifyDate("Inserisci la data di fine (aaaa-mm-gg)");
                    while (dataInizio.isAfter(dataFine)) {
                        System.out.println("La data di fine deve essere successiva a data di inizio (aaaa-mm-gg)");
                        dataFine = askAndVerifyDate("Inserisci la data di fine (aaaa-mm-gg)");
                    }

                    Mezzo mezzo = Utilities.askAndVerifyList("Scegli id mezzo", mezziDAO.getAll(), "Mezzo", true);

                    //creazione stato mezzo
                    StatoMezzo statoMezzo = new StatoMezzo(condizioneMezzo, dataInizio, dataFine, mezzo);
                    Application.statoMezzoDAO.save(statoMezzo);
                    pressEnterToContinue();
                    break;
                }
                case "12": {

                    String puntoPartenza = Utilities.askAndVerifyString("Inserisci punto partenza");
                    String capolinea = Utilities.askAndVerifyString("Inserisci capolinea");
                    int tempoMedioPercorrenza = Utilities.askAndVerifyInt("Inserisci tempo medio di percorrenza in minuti");
                    Tratta tratta = new Tratta(puntoPartenza, capolinea, tempoMedioPercorrenza);
                    Application.tratteDAO.save(tratta);
                    pressEnterToContinue();
                    break;

                }
                case "13": {

                    Mezzo mezzo = Utilities.askAndVerifyList("Scegli id mezzo", mezziDAO.getAll(), "Mezzo", true);
                    Tratta tratta = Utilities.askAndVerifyList("Scegli id tratta", Application.tratteDAO.getAll(), "Tratta", true);

                    int tempoEffettivoPercorrenza = Utilities.askAndVerifyInt("Inserisci tempo effettivo di percorrenza");
                    MezzoTratta trattaPercorrenza = new MezzoTratta(mezzo, tratta, tempoEffettivoPercorrenza);
                    Application.mezziTrattaDAO.save(trattaPercorrenza);
                    pressEnterToContinue();
                    break;
                }
                case "14": {
                    Mezzo mezzo = Utilities.askAndVerifyList("Scegli id mezzo", mezziDAO.getAll(), "Mezzo", true);
                    List<StatoMezzo> statiMezzo = statoMezzoDAO.getInManutenzione(mezzo);
                    assert mezzo != null;
                    if (statiMezzo.isEmpty()) {
                        System.out.println("Il mezzo scelto con id " + mezzo.getId() + " non è mai stato in manutenzione");
                    } else {
                        System.out.println("Il mezzo scelto con id " + mezzo.getId() + " ha i seguenti stati di manutenzione:");
                        statiMezzo.forEach(System.out::println);
                    }
                    pressEnterToContinue();
                    break;
                }
                case "15": {
                    Tessera tessera = Utilities.askAndVerifyList("Scegli id tessera", Application.tessereDAO.getValid(), "Tessera", true);
                    assert tessera != null;
                    tessera.rinnova();
                    tessereDAO.rinnova(tessera);

                    pressEnterToContinue();
                    break;
                }
                case "16": {
                    Tessera tesseraDaInvalidare = Utilities.askAndVerifyList("Scegli id tessera da invalidare", Application.tessereDAO.getValid(), "Tessera", true);
                    assert tesseraDaInvalidare != null;
                    tessereDAO.invalida(tesseraDaInvalidare);

                    Tessera newTessera = new Tessera(tesseraDaInvalidare.getUtente(), LocalDate.now(), true);
                    tessereDAO.save(newTessera);
                    pressEnterToContinue();
                    break;
                }
                case "17": {
                    Utente utente = Utilities.askAndVerifyList("Scegli id utente da modificare", Application.utentiDAO.getAll(), "Utente", true);
                    assert utente != null;
                    System.out.println("Utente scelto");
                    System.out.println(utente);
                    System.out.println();
                    utente.setNome(askAndVerifyString("Inserisci nuovo nome"));
                    utente.setCognome(askAndVerifyString("Inserisci nuovo cognome"));
                    utente.setDataNascita(askAndVerifyDate("Inserisci nuova data di nascita (aaaa-mm-gg)"));
                    utente.setVia(askAndVerifyString("Inserisci nuova via"));
                    utente.setCitta(askAndVerifyString("Inserisci nuova citta"));
                    utente.setProvincia(askAndVerifyString("Inserisci nuova provincia"));
                    utente.setCap(askAndVerifyString("Inserisci nuovo cap", 5));
                    utente.setNazione(askAndVerifyString("Inserisci nuova nazione"));

                    utentiDAO.update(utente);

                    pressEnterToContinue();
                    break;
                }

                case "18": {
                    StatoMezzo statoMezzo = Utilities.askAndVerifyList("Scegli id stato mezzo da modificare", Application.statoMezzoDAO.getAll(), "Stato mezzo", true);
                    assert statoMezzo != null;
                    System.out.println("Stato mezzo scelto");
                    System.out.println(statoMezzo);
                    statoMezzo.setDataFine(askAndVerifyDate("Inserisci nuova data di fine (aaaa-mm-gg)"));
                    statoMezzoDAO.updateDataFine(statoMezzo);
                    pressEnterToContinue();
                    break;
                }

                case "19": {
                    Tratta tratta = Utilities.askAndVerifyList("Scegli id tratta da modificare", Application.tratteDAO.getAll(), "Tratta", true);
                    assert tratta != null;
                    System.out.println("Tratta scelta");
                    System.out.println(tratta);
                    tratta.setPuntoPartenza(askAndVerifyString("Inserisci nuovo punto di partenza"));
                    tratta.setCapolinea(askAndVerifyString("Inserisci nuova capolinea"));
                    tratta.setTempoMedioPercorrenza(askAndVerifyInt("Inserisci nuovo tempo medio di percorrenza in minuti"));
                    tratteDAO.update(tratta);
                    pressEnterToContinue();
                    break;
                }
                case "20": {
                    Emettitore emettitore = Utilities.askAndVerifyList("Scegli id emettitore da modificare", Application.emettitoriDAO.getAll(), "Emettitore", true);
                    assert emettitore != null;
                    System.out.println("Emettitore scelto");
                    System.out.println(emettitore);
                    emettitore.setNome(askAndVerifyString("Inserisci nuovo nome"));
                    if (emettitore.getTipologia().equals(EmettitoreTipo.DISTRIBUTORE)) {
                        emettitore.setStato(askAndVerifyEnum("Inserisci nuovo stato", EmettitoreStato.class));
                    }
                    emettitoriDAO.update(emettitore);

                    pressEnterToContinue();
                    break;
                }
                case "21": {
                    Emettitore emettitore = Utilities.askAndVerifyList("Scegli id emettitore da contrassegnare eliminato", Application.emettitoriDAO.getAll(), "Emettitore", true);
                    assert emettitore != null;

                    pressEnterToContinue();
                    break;
                }
                case "22": {
                    Utente utente = Utilities.askAndVerifyList("Scegli id utente da eliminare", Application.utentiDAO.getAll(), "Utente", true);
                    assert utente != null;

                    pressEnterToContinue();
                    break;
                }

                case "23": {
                    Mezzo mezzo = Utilities.askAndVerifyList("Scegli id mezzo da contrassegnare eliminato", Application.mezziDAO.getAll(), "Mezzo", true);
                    assert mezzo != null;

                    pressEnterToContinue();
                    break;
                }
                default:
                    System.err.println("Opzione non valida, riprova");
            }
        }
    }
}
