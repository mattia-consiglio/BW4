// @author <FRANCESCO>

package team3.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "mezzi")
public class Mezzo {
    // attributi
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int capienza;
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipoMezzo;

    // costruttore
    public Mezzo() {}
    public Mezzo(int capienza, TipoMezzo tipoMezzo) {
        this.capienza = capienza;
        this.tipoMezzo = tipoMezzo;
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

    // toString

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", capienza=" + capienza +
                ", tipoMezzo=" + tipoMezzo +
                '}';
    }
}
