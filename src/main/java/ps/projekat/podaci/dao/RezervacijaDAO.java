package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Racun;
import ps.projekat.podaci.dto.Rezervacija;
import ps.projekat.prikaziKontroleri.RegistracijaKontroler;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class RezervacijaDAO implements DAO<Rezervacija> {

    private static final String NAZIV_KOLONE = "idRezervacije";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public RezervacijaDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }
    
    public List<Rezervacija> selektujSve() {
        return jpaUtil.selektujSve(Rezervacija.class);
    }

    public List<Rezervacija> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Rezervacija.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<Rezervacija> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Rezervacija.class, podaci, kolone);
    }

    @Override
    public void dodaj(Rezervacija rezervacija) {
        jpaUtil.dodaj(rezervacija);
    }

    @Override
    public boolean izmijeni(Rezervacija rezervacija) {
        return jpaUtil.izmijeni(rezervacija);
    }

    @Override
    public boolean obrisi(Rezervacija rezervacija) {
        return jpaUtil.obrisi(rezervacija);
    }
}
