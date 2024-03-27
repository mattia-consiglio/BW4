package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "titoli_viaggio")
public abstract class TitoloViaggio implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;
    @Column(name = "data_emissione")
    protected LocalDate dataEmissione;
    @ManyToOne
    @JoinColumn(name = "id_emettitore")
    protected Emettitore emettitore;

    public TitoloViaggio() {
    }

    public TitoloViaggio(LocalDate dataEmissione, Emettitore emettitore) {
        this.dataEmissione = dataEmissione;
        this.emettitore = emettitore;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public Emettitore getEmettitore() {
        return emettitore;
    }

    public void setEmettitore(Emettitore emettitore) {
        this.emettitore = emettitore;
    }
}
