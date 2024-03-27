package team3;

import team3.entities.*;
import team3.exceptions.EmettitoreException;

import java.time.LocalDate;

import static team3.Application.titoliViaggioDAO;
import static team3.Utilities.*;

public class Menus {
    public static void mainMenu() {

        while (true) {
            System.out.println("--------- Menu principale ---------");
            System.out.println();
            System.out.println("Scegli un' opzione");
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

    public static boolean adminMenu() {
        while (true) {
            System.out.println();
            System.out.println("--------- Menu Amministratore ---------");
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
                        System.out.println("Data fine non valida, inserisci una data valida");
                        dataFine = askAndVerifyDate("Inserisci data fine");
                    }
                    Emettitore emettitore = askAndVerifyList("Scegli l'ID dell'Emettitore", Application.emettitoriDAO.getAll(), "Emettitore");
                    assert emettitore != null;
                    long count = 0;
                    if (tipoTitoloViaggio.equals(TipoTitoloViaggio.BIGLIETTO)) {
                        count = titoliViaggioDAO.getNumberBigliettiByPeriodo(dataInizio, dataFine, emettitore);
                        System.out.println("Numero biglietti emessi in questo periodo e per emettitore: " + count);
                    }
                    if (tipoTitoloViaggio.equals(TipoTitoloViaggio.ABBONAMENTO)) {
                        count = titoliViaggioDAO.getNumberAbbonamentiByPeriodo(dataInizio, dataFine, emettitore);
                        System.out.println("Numero abbonamenti emessi in questo periodo e per emettitore: " + count);
                    }
                    break;
                }
                case "2": {
                    Mezzo mezzo = askAndVerifyList("Scegli l'ID di un mezzo", Application.mezziDAO.getAll(), "Mezzo");
                    assert mezzo != null;
                    long count = titoliViaggioDAO.countBigliettiVidimitateSuDeterminatoMezzo(mezzo);
                    break;
                }

                case "3": {
                    Mezzo mezzo = askAndVerifyList("Scegli l'ID di un mezzo", Application.mezziDAO.getAll(), "Mezzo");
                    LocalDate dataInizio = askAndVerifyDate("Inserisci data inizio (aaaa-mm-gg)");
                    LocalDate dataFine = askAndVerifyDate("Inserisci data fine (aaaa-mm-gg)");
                    while (dataInizio.isAfter(dataFine)) {
                        System.out.println("Data fine non valida, inserisci una data valida");
                        dataFine = askAndVerifyDate("Inserisci data fine");
                    }
                    assert mezzo != null;
                    long count = titoliViaggioDAO.countBigliettiVidimatiSuMezzo(mezzo, dataInizio, dataFine);
                    break;
                }
                case "4": {
                    System.out.println("inserire data inizio");
                    LocalDate dataInizio = LocalDate.parse(Application.scanner.nextLine());
                    System.out.println("inserire data fine");
                    LocalDate dataFine = LocalDate.parse(Application.scanner.nextLine());
                    System.out.println("inserire mezzo");
                    String mezzo = Application.scanner.nextLine();
                    break;
                }
                case "5": {
                    LocalDate dataInizio = askAndVerifyDate("Inserisci data inizio (aaaa-mm-gg)");
                    LocalDate dataFine = askAndVerifyDate("Inserisci data fine (aaaa-mm-gg)");
                    while (dataInizio.isAfter(dataFine)) {
                        System.out.println("Data fine non valida, inserisci una data valida");
                        dataFine = askAndVerifyDate("Inserisci data fine");
                    }
                    long count = titoliViaggioDAO.countBigliettiVidimatiInGenerale(dataInizio, dataFine);
                    System.out.println("Numero biglietti vidimati nel periodo di tempo indicato: " + count);
                    break;
                }
                case "6": {

                    TipoTitoloViaggio tipoTitoloViaggio = askAndVerifyEnum("Inserisci il tipo di titolo di viaggio", TipoTitoloViaggio.class);
                    LocalDate dataInizio = LocalDate.now();
                    Emettitore emettitore = askAndVerifyList("Scegli l'ID dell'emettitore", Application.emettitoriDAO.getAll(), "Emettitore");
                    if (tipoTitoloViaggio.equals(TipoTitoloViaggio.ABBONAMENTO)) {
                        TipoAbbonamento tipoAbbonamento = askAndVerifyEnum("Inserisci il tipo di abbonamento", TipoAbbonamento.class);
                        Tessera tessera = askAndVerifyList("Scegli l'ID di quale tessera asscociare l'abbonamento", Application.tessereDAO.getAll(), "Tessera");
                        Abbonamento abbonamento = new Abbonamento(dataInizio, emettitore, tipoAbbonamento, tessera);
                        titoliViaggioDAO.save(abbonamento);
                    } else {
                        Biglietto biglietto = new Biglietto(dataInizio, emettitore, false, null, null);
                        titoliViaggioDAO.save(biglietto);
                    }
                    break;

                }
                case "7": {
                    EmettitoreTipo emettitoreTipo = askAndVerifyEnum("Segli un tipo di emettitore", EmettitoreTipo.class);
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
                }
                case "8": {

                    Utente utente = askAndVerifyList("Scegli un ID utente", Application.utentiDAO.getAll(), "Utente");
                    Tessera newtessera = new Tessera(utente, LocalDate.now(), true);
                    Application.tessereDAO.save(newtessera);
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
                    break;
                }
                case "10": {

                    int capienzaMezzo = askAndVerifyInt("Inserisci capienza mezzo");


                    TipoMezzo tipoMezzoEnum = askAndVerifyEnum("Scegli tipo mezzo", TipoMezzo.class);
                    Mezzo mezzo = new Mezzo(capienzaMezzo, tipoMezzoEnum);
                    Application.mezziDAO.save(mezzo);
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

                    Mezzo mezzo = Utilities.askAndVerifyList("Scegli id mezzo", Application.mezziDAO.getAll(), "Mezzo");

                    //creazione stato mezzo
                    StatoMezzo statoMezzo = new StatoMezzo(condizioneMezzo, dataInizio, dataFine, mezzo);
                    Application.statoMezzoDAO.save(statoMezzo);
                    break;
                }
                case "12": {

                    String puntoPartenza = Utilities.askAndVerifyString("Inserisci punto partenza");
                    String capolinea = Utilities.askAndVerifyString("Inserisci capolinea");
                    int tempoMedioPercorrenza = Utilities.askAndVerifyInt("Inserisci tempo medio di percorrenza in minuti");
                    Tratta tratta = new Tratta(puntoPartenza, capolinea, tempoMedioPercorrenza);
                    Application.tratteDAO.save(tratta);

                    break;

                }
                case "13": {

                    Mezzo mezzo = Utilities.askAndVerifyList("Scegli id mezzo", Application.mezziDAO.getAll(), "Mezzo");
                    Tratta tratta = Utilities.askAndVerifyList("Scegli id tratta", Application.tratteDAO.getAll(), "Tratta");

                    int tempoEffettivoPercorrenza = Utilities.askAndVerifyInt("Inserisci tempo effettivo di percorrenza");
                    MezzoTratta trattaPercorrenza = new MezzoTratta(mezzo, tratta, tempoEffettivoPercorrenza);
                    Application.mezziTrattaDAO.save(trattaPercorrenza);
                    break;

                }
                default:
                    System.err.println("Opzione non valida, riprova");
            }
        }
    }
}
