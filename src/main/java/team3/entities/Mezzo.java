package team3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mezzi")
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int capienza;

    public Mezzo() {
    }
}
