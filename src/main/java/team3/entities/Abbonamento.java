package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloViaggio {
    private LocalDate dataInizio;
    private LocalDate dataFine;
    @Enumerated(value = EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;
    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    public Abbonamento(LocalDate dataEmissione, Emettitore emettitore, LocalDate dataInizio, LocalDate dataFine, TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        super(dataEmissione, emettitore);
        this.dataInizio = dataInizio;
        this.tipoAbbonamento = tipoAbbonamento;
        if(this.tipoAbbonamento.equals(TipoAbbonamento.SETTIMANALE)) {
            this.dataFine = this.dataInizio.plusDays(7);
        } else {
            this.dataFine = this.dataInizio.plusDays(30);
        }
        this.tessera = tessera;
    }
}
