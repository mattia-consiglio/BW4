package team3.entities;


import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "mezzi_tratta")
public class MezzoTratta implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;
    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @Column(name = "tempo_percorrenza")
    private int tempoPercorrenzaEffettivo;


    public MezzoTratta() {
    }

    public MezzoTratta(Mezzo mezzo, Tratta tratta, int tempoPercorrenzaEffettivo) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
    }

    public long getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public int getTempoPercorrenzaEffettivo() {
        return tempoPercorrenzaEffettivo;
    }

    public void setTempoPercorrenzaEffettivo(int tempoPercorrenzaEffettivo) {
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
    }

    @Override
    public String toString() {
        return "MezzoTratta{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", tratta=" + tratta +
                ", tempoPercorrenzaEffettivo=" + tempoPercorrenzaEffettivo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MezzoTratta that)) return false;
        return id == that.id && tempoPercorrenzaEffettivo == that.tempoPercorrenzaEffettivo && Objects.equals(mezzo, that.mezzo) && Objects.equals(tratta, that.tratta);
    }

}
