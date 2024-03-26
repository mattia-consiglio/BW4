package team3.entities;

@FunctionalInterface
public interface BigliettoSupplier {
    Biglietto get(Emettitore emettitore, Mezzo mezzo);
}
