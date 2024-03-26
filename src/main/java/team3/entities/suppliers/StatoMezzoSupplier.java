package team3.entities.suppliers;

import team3.entities.Mezzo;
import team3.entities.StatoMezzo;

import java.util.List;

@FunctionalInterface
public interface StatoMezzoSupplier {
    StatoMezzo get(List<Mezzo> mezzi);
}
