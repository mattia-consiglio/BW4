package team3.entities;


import jakarta.persistence.*;


@Entity
@Table(name = "mezzi_tratta")
public class MezziTratta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;
    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @Column(name = "tempo_percorrenza")
    private int tempoPercorrenzaEffettivo;


    public MezziTratta() {
    }

    public MezziTratta(Mezzo mezzo, Tratta tratta, int tempoPercorrenzaEffettivo) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
    }

}
