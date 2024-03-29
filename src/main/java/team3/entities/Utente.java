package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "utenti")
public class Utente implements HasId {

    //LISTA ATTRIBUTI:
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String via;
    private String provincia;
    private String citta;
    private String cap;
    private String nazione;
    @OneToMany(mappedBy = "utente")
    private List<Tessera> tessere = new ArrayList<>();


    //COSTRUTTORI:
    public Utente() {
    }

    public Utente(String nome, String cognome, LocalDate dataNascita, String via, String provincica, String citta, String cap, String nazione) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.via = via;
        this.provincia = provincica;
        this.citta = citta;
        this.cap = cap;
        this.nazione = nazione;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", via='" + via + '\'' +
                ", provincia='" + provincia + '\'' +
                ", città='" + citta + '\'' +
                ", cap='" + cap + '\'' +
                ", nazione='" + nazione + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente utente)) return false;
        return id == utente.id && Objects.equals(nome, utente.nome) && Objects.equals(cognome, utente.cognome) && Objects.equals(dataNascita, utente.dataNascita) && Objects.equals(via, utente.via) && Objects.equals(provincia, utente.provincia) && Objects.equals(citta, utente.citta) && Objects.equals(cap, utente.cap) && Objects.equals(nazione, utente.nazione) && Objects.equals(tessere, utente.tessere);
    }
}
