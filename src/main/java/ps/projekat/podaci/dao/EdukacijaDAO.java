package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.BrojTelefona;
import ps.projekat.podaci.dto.Edukacija;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class EdukacijaDAO implements DAO<Edukacija> {

    private static final String NAZIV_KOLONE = "idEdukacije";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public EdukacijaDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }

    public List<Edukacija> selektujSve() {
        return jpaUtil.selektujSve(Edukacija.class);
    }

    public List<Edukacija> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Edukacija.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<Edukacija> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Edukacija.class, podaci, kolone);
    }

    @Override
    public void dodaj(Edukacija edukacija) {
        jpaUtil.dodaj(edukacija);
    }

    @Override
    public boolean izmijeni(Edukacija edukacija) {
        return jpaUtil.izmijeni(edukacija);
    }

    @Override
    public boolean obrisi(Edukacija edukacija) {
        return jpaUtil.obrisi(edukacija);
    }
}
