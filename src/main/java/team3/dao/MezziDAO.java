package team3.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.Biglietto;
import team3.entities.Mezzo;
import team3.entities.StatoMezzi;

import java.time.LocalDate;
import java.util.List;

public class MezziDAO {
    private EntityManager em;

    public MezziDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(mezzo);
            t.commit();
            System.out.println("Mezzo inserito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Mezzo findById(long id) {
        return em.find(Mezzo.class, id);
    }

    public void findByIdAndDelete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            Mezzo found = em.find(Mezzo.class, id);
            if (found != null) {
                t.begin();
                em.remove(found);
                t.commit();
                System.out.println("Mezzo eliminato");
            } else System.out.println("Mezzo non trovato");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // QUERY

    //QUERY per ottenere tutti i biglietti che sono stati vidimati su un mezzo
    public List<Biglietto> tessereVidimitateSuDeterminatoMezzo(Mezzo mezzo) {
        TypedQuery<Biglietto> list = em.createQuery("SELECT p.oggetto FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS null", Biglietto.class);
        list.setParameter("numeroTessera", mezzo);
        return list.getResultList();
    }

    //QUERY per CONTARE i biglietti che sono stati vidimati su un mezzo in un determinato periodo di tempo
    public Long countBigliettiVidimatiSuMezzo(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.vidimato = true AND b.mezzo = :mezzo AND b.dataVidimazione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("mezzo", mezzo);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getSingleResult();
    }


        //QUERY per CONTARE tutti i biglietti che sono stati vidimati su tutti i mezzi in un determinato periodo di tempo
    public Long countBigliettiVidimatiInGenerale(LocalDate dataInizio, LocalDate dataFine){
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.vidimato = true AND b.dataVidimazione BETWEEN :dataInizio AND :dataFine", Long.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        return query.getSingleResult();
    }


    public List<Mezzo> getAll() {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class);
        return query.getResultList();
    }

}
