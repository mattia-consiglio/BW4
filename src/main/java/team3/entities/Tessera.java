package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tessere")
public class Tessera {
    //LISTA ATTRIBUTI:
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    private Utente utente;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validità;


    //COSTRUTTORI:
    public Tessera() {
    }

    public Tessera(Utente utente, LocalDate dataInizio, boolean validità) {
        this.utente = utente;
        this.dataInizio = dataInizio;
        this.dataFine = LocalDate.now().plusYears(1);
        this.validità = validità;
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

    public boolean isValidità() {
        return validità;
    }

    public void setValidità(boolean validità) {
        this.validità = validità;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", utente=" + utente +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", validità=" + validità +
                '}';
    }
}
