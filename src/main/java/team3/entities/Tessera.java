package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tessere")
public class Tessera implements HasId {
    //LISTA ATTRIBUTI:
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    private Utente utente;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validita;


    //COSTRUTTORI:
    public Tessera() {
    }


    public Tessera(Utente utente, LocalDate dataInizio, boolean validita) {
        this.utente = utente;
        this.dataInizio = dataInizio;

        this.dataFine = LocalDate.now().plusYears(1);
        this.validita = validita;

    }

    public long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public boolean isValidita() {
        return validita;
    }

    public void setValidita(boolean validita) {
        this.validita = validita;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", utente=" + utente +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", validità=" + validita +
                '}';
    }
}