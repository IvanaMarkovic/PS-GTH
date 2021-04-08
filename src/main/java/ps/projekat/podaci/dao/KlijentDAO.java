package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Klijent;
import ps.projekat.podaci.dto.Rezervacija;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class KlijentDAO implements DAO<Klijent> {

    private static final String NAZIV_KOLONE = "idKlijenta";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public KlijentDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }

    public List<Klijent> selektujSve() {
        return jpaUtil.selektujSve(Klijent.class);
    }

    public List<Klijent> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Klijent.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<Klijent> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Klijent.class, podaci, kolone);
    }

    @Override
    public void dodaj(Klijent klijent) {
        jpaUtil.dodaj(klijent);
    }

    @Override
    public boolean izmijeni(Klijent klijent) {
        return jpaUtil.izmijeni(klijent);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(Klijent klijent) {
        return jpaUtil.obrisi(klijent);
    }
}
