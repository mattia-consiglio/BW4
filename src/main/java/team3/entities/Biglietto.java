package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietto extends TitoloViaggio {
    private boolean vidimato;
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;


    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, Emettitore emettitore, boolean vidimato, LocalDate dataVidimazione, Mezzo mezzo) {
        super(dataEmissione, emettitore);
        this.vidimato = vidimato;
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
    }

    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", vidimato=" + vidimato +
                ", dataVidimazione=" + dataVidimazione +
                ", emettitore=" + emettitore +
                ", mezzo=" + mezzo +
                '}';
    }
}