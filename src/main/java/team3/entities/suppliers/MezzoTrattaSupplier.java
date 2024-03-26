package team3.entities.suppliers;

import team3.entities.Mezzo;
import team3.entities.MezzoTratta;
import team3.entities.Tratta;

import java.util.List;

@FunctionalInterface
public interface MezzoTrattaSupplier {
    MezzoTratta get(List<Mezzo> mezzi, List<Tratta> tratte);
}
