// @author <FRANCESCO>

package team3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mezzi")
public class Mezzo implements HasId {
    // attributi
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int capienza;
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipoMezzo;
    boolean disponibile;

    // costruttore
    public Mezzo() {
    }

    public Mezzo(int capienza, TipoMezzo tipoMezzo) {
        this.capienza = capienza;
        this.tipoMezzo = tipoMezzo;
        this.disponibile = true;
    }

    // setter e getter
    public long getId() {
        return id;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    // toString
    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", capienza=" + capienza +
                ", tipoMezzo=" + tipoMezzo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mezzo mezzo)) return false;
        return id == mezzo.id && capienza == mezzo.capienza && tipoMezzo == mezzo.tipoMezzo;
    }

}
