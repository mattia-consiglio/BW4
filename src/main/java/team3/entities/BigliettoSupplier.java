package team3.entities;

import java.util.List;

@FunctionalInterface
public interface BigliettoSupplier {
    Biglietto get(Emettitore emettitore, List<Mezzo> mezzi);
}
