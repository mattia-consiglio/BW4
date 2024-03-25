package team3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tratte")
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "punto_partenza")
    private String puntoPartenza;

    private String capolinea;

    @Column(name = "tempo_medio_percorrenza")
    private int tempoMedioPercorrenza;


}
