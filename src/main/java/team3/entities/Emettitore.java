package team3.entities;

import jakarta.persistence.*;
import team3.exceptions.EmettitoreException;

@Entity
@Table(name = "emettitori")
public class Emettitore implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String nome;
    private String via;
    private String civico;
    private String provincia;
    private String citta;
    private String cap;
    private String nazione;
    @Enumerated(value = EnumType.STRING)
    private EmettitoreTipo tipologia;
    @Enumerated(value = EnumType.STRING)
    private EmettitoreStato stato;


    public Emettitore() {
    }


    public Emettitore(String nome, String via, String civico, String provincia, String citta, String cap, String nazione, EmettitoreTipo tipologia, EmettitoreStato stato) throws EmettitoreException {
        this.nome = nome;
        this.via = via;
        this.civico = civico;
        this.provincia = provincia;
        this.citta = citta;
        this.cap = cap;
        this.nazione = nazione;
        this.tipologia = tipologia;
        if (tipologia == EmettitoreTipo.DISTRIBUTORE && stato == null) {
            throw new EmettitoreException("è necessario avere uno stato");
        }
        this.stato = stato;
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

    public String getVia() {
        return via;
    }

    public String getCivico() {
        return civico;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCitta() {
        return citta;
    }

    public String getCap() {
        return cap;
    }

    public String getNazione() {
        return nazione;
    }

    public EmettitoreTipo getTipologia() {
        return tipologia;
    }

    public EmettitoreStato getStato() {
        return stato;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public void setTipologia(EmettitoreTipo tipologia) {
        this.tipologia = tipologia;
    }

    public void setStato(EmettitoreStato stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Emettitore{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", via='" + via + '\'' +
                ", civico='" + civico + '\'' +
                ", provincia='" + provincia + '\'' +
                ", citta='" + citta + '\'' +
                ", cap='" + cap + '\'' +
                ", nazione='" + nazione + '\'' +
                ", tipologia=" + tipologia +
                ", stato=" + stato +
                '}';
    }
}
