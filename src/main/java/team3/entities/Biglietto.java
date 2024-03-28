package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "biglietti")
public class Biglietto extends TitoloViaggio {
    @Column(nullable = false)
    private boolean vidimato;
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;


    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, Emettitore emettitore, boolean vidimato, LocalDate dataVidimazione, Mezzo mezzo, Utente utente) {
        super(dataEmissione, emettitore);
        this.vidimato = vidimato;
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
        this.utente = utente;
    }

    public void vidima(Mezzo mezzo) {
        this.vidimato = true;
        this.dataVidimazione = LocalDate.now();
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
                ", utente=" + utente +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Biglietto biglietto)) return false;
        return vidimato == biglietto.vidimato && Objects.equals(dataVidimazione, biglietto.dataVidimazione) && Objects.equals(mezzo, biglietto.mezzo) && Objects.equals(utente, biglietto.utente);
    }

}
