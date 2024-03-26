package team3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloViaggio {
    private LocalDate dataInizio;
    private LocalDate dataFine;
    @Enumerated(value = EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

}
