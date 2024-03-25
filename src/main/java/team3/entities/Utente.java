package team3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
@Entity
public class Utente {

    //LISTA ATTRIBUTI:
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String via;
    private String provincia;
    private String città;
    private String cap;
    private String nazione;

    //COSTRUTTORI:
    public Utente() {}

    public Utente( String nome, String cognome, LocalDate dataNascita, String via, String provincica, String città, String cap, String nazione) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.via = via;
        this.provincia = provincica;
        this.città = città;
        this.cap = cap;
        this.nazione = nazione;
    }
}
