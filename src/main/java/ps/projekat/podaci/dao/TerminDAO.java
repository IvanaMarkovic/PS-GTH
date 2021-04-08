package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Termin;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public class TerminDAO implements DAO<Termin> {

    public static final String NAZIV_KOLONE = "idTermina";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public TerminDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }
    @Override
    public List<Termin> selektujSve() {
        return jpaUtil.selektujSve(Termin.class);
    }

    @Override
    public List<Termin> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Termin.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<Termin> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Termin.class, podaci, kolone);
    }

    public List<Termin> selektujPoDatumu(List<String> datumi) {
        return jpaUtil.selektujPoDatumu(Termin.class, datumi);
    }

    @Override
    public void dodaj(Termin termin) {
        jpaUtil.dodaj(termin);
    }

    @Override
    public boolean izmijeni(Termin termin) {
        return jpaUtil.izmijeni(termin);
    }

    @Override
    public boolean obrisi(Termin termin) {
        return jpaUtil.obrisi(termin);
    }
}
