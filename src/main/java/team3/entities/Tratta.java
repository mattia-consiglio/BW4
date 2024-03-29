package team3.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tratte")
public class Tratta implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "punto_partenza")
    private String puntoPartenza;

    private String capolinea;

    @Column(name = "tempo_medio_percorrenza")
    private int tempoMedioPercorrenza;

    public Tratta() {
    }


    public Tratta(String puntoPartenza, String capolinea, int tempoMedioPercorrenza) {
        this.puntoPartenza = puntoPartenza;
        this.capolinea = capolinea;
        this.tempoMedioPercorrenza = tempoMedioPercorrenza;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPuntoPartenza() {
        return puntoPartenza;
    }

    public void setPuntoPartenza(String puntoPartenza) {
        this.puntoPartenza = puntoPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public int getTempoMedioPercorrenza() {
        return tempoMedioPercorrenza;
    }

    public void setTempoMedioPercorrenza(int tempoMedioPercorrenza) {
        this.tempoMedioPercorrenza = tempoMedioPercorrenza;
    }


    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", puntoPartenza='" + puntoPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoMedioPercorrenza=" + tempoMedioPercorrenza +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tratta tratta)) return false;
        return id == tratta.id && tempoMedioPercorrenza == tratta.tempoMedioPercorrenza && Objects.equals(puntoPartenza, tratta.puntoPartenza) && Objects.equals(capolinea, tratta.capolinea);
    }
}
