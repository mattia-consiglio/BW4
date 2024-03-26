package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Tessera {
    //LISTA ATTRIBUTI:
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long numeroTessera;
    @ManyToOne
    private Utente utente;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean validità;
    @ManyToOne
    @JoinColumn(name = "id_abbonamento")
    private Abbonamento abbonamento;

    //COSTRUTTORI:
    public Tessera() {
    }

    public Tessera(Utente utente, Abbonamento abbonamento) {
        this.utente = utente;
        this.dataInizio = LocalDate.now();
        this.dataFine = LocalDate.now().plusDays(365);
        this.validità = true;
        this.abbonamento = abbonamento;
    }
}
