package team3.entities.suppliers;

import team3.entities.Abbonamento;
import team3.entities.Emettitore;
import team3.entities.Tessera;

import java.util.List;

@FunctionalInterface
public interface AbbonamentoSupplier {
    Abbonamento get(List<Tessera> tessere, List<Emettitore> emettitori);
}
