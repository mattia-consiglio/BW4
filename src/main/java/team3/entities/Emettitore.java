package team3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "emettitori")
public class Emettitore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String nome;

    public Emettitore() {
    }

    public long getId() {
        return id;
    }


}
