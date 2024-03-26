package team3.entities.suppliers;

import team3.entities.Tessera;
import team3.entities.Utente;

import java.util.List;

@FunctionalInterface
public interface TesseraSupplier {
    Tessera get(List<Utente> utenti);

}
