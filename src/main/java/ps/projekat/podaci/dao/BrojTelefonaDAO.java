package ps.projekat.podaci.dao;

import javafx.scene.layout.BackgroundSize;
import ps.projekat.podaci.dto.BrojTelefona;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class BrojTelefonaDAO implements DAO<BrojTelefona> {

    private static EntityManagerFactory fabrika;
    private static final String NAZIV_KOLONE = "idKlijenta";
    private JPAUtil jpaUtil;

    public BrojTelefonaDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }

    public List<BrojTelefona> selektujSve() {
        return jpaUtil.selektujSve(BrojTelefona.class);
    }

    @Override
    public List<BrojTelefona> selektujPoId(long id) {
        return jpaUtil.selektujPoId(BrojTelefona.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<BrojTelefona> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(BrojTelefona.class, podaci, kolone);
    }

    @Override
    public void dodaj(BrojTelefona brojTelefona) {
        jpaUtil.dodaj(brojTelefona);
    }

    @Override
    public boolean izmijeni(BrojTelefona brojTelefona) {
        return jpaUtil.izmijeni(brojTelefona);
    }

    @Override
    public boolean obrisi(BrojTelefona BrojTelefona) {
        return jpaUtil.obrisi(BrojTelefona);
    }
}
