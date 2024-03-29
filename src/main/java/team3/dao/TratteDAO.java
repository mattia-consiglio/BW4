package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.Tratta;

import java.util.List;

public class TratteDAO {
    private final EntityManager em;

    public TratteDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Tratta tratta) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(tratta);
            t.commit();
            System.out.println("Tratta inserita: " + tratta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Tratta> getAll() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    public void updateTrattaById(long id, String newPuntoPartenza, String newCapolinea, int newTempoMedioPercorrenza) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            // Recupera la tratta con l'ID specificato
            Tratta tratta = em.find(Tratta.class, id);

            if (tratta != null) {
                // Imposta i nuovi valori per i campi puntoPartenza, capolinea e tempoMedioPercorrenza
                tratta.setPuntoPartenza(newPuntoPartenza);
                tratta.setCapolinea(newCapolinea);
                tratta.setTempoMedioPercorrenza(newTempoMedioPercorrenza);

                // Esegui l'aggiornamento effettivo utilizzando il metodo merge
                em.merge(tratta);

                // Fai il commit della transazione
                transaction.commit();
                System.out.println("Punto di partenza, capolinea e tempo medio della tratta con ID " + id + " aggiornati");
            } else {
                System.out.println("Tratta con ID " + id + " non trovata");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
