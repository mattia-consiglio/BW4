package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietto extends TitoloViaggio {
    private boolean vidimato;
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Biglietto(LocalDate dataEmissione, Emettitore emettitore, boolean vidimato, LocalDate dataVidimazione, Mezzo mezzo) {
        super(dataEmissione, emettitore);
        this.vidimato = vidimato;
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
    }
}
