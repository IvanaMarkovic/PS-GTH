package ps.projekat.podaci.dao;

import javafx.scene.control.RadioButton;
import ps.projekat.podaci.dto.Racun;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class RacunDAO implements DAO<Racun> {

    private static EntityManagerFactory fabrika;
    private static final String NAZIV_KOLONE = "idKlijenta";
    private JPAUtil jpaUtil;

    public RacunDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }

    public List<Racun> selektujSve() {
        return jpaUtil.selektujSve(Racun.class);
    }

    @Override
    public List<Racun> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Racun.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<Racun> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Racun.class, podaci, kolone);
    }

    @Override
    public void dodaj(Racun racun) {
        jpaUtil.dodaj(racun);
    }

    @Override
    public boolean izmijeni(Racun racun) {
        return jpaUtil.izmijeni(racun);
    }

    @Override
    public boolean obrisi(Racun racun) {
        return jpaUtil.obrisi(racun);
    }
}
