package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloViaggio {
    private LocalDate dataFine;
    @Enumerated(value = EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;
    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;


    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataEmissione, Emettitore emettitore, TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        super(dataEmissione, emettitore);
        this.dataEmissione = dataEmissione;
        this.tipoAbbonamento = tipoAbbonamento;
        if (tipoAbbonamento.equals(TipoAbbonamento.SETTIMANALE)) {
            this.dataFine = this.dataEmissione.plusDays(7);
        } else {
            this.dataFine = this.dataEmissione.plusDays(30);
        }
        this.tessera = tessera;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", dataFine=" + dataFine +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", tessera=" + tessera +
                ", emettitore=" + emettitore +
                '}';
    }
}