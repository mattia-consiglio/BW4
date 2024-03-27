// @author <FRANCESCO>

package team3.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "stato_mezzi")
public class StatoMezzo implements HasId {
    // attributi
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "condizioni_mezzo")
    @Enumerated(EnumType.STRING)
    private CondizioneMezzo condizioneMezzo;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    // costruttore
    public StatoMezzo() {
    }

    public StatoMezzo(CondizioneMezzo condizioneMezzo, LocalDate dataInizio, LocalDate dataFine, Mezzo mezzo) {
        this.condizioneMezzo = condizioneMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.mezzo = mezzo;
    }

    // setter e getter
    public long getId() {
        return id;
    }

    public CondizioneMezzo getCondizioneMezzo() {
        return condizioneMezzo;
    }

    public void setCondizioneMezzo(CondizioneMezzo condizioneMezzo) {
        this.condizioneMezzo = condizioneMezzo;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    //toString
    @Override
    public String toString() {
        return "StatoMezzi{" +
                "id=" + id +
                ", condizioneMezzo=" + condizioneMezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + mezzo +
                '}';
    }
}
