package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Cjenovnik;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class CjenovnikDAO implements DAO<Cjenovnik> {

    private static final String NAZIV_KOLONE = "idCjenovnika";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public CjenovnikDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }

    @Override
    public List<Cjenovnik> selektujSve() {
        return jpaUtil.selektujSve(Cjenovnik.class); // treba Cjenovnik umjesto CjenovnikDAO - zamijeniti kada se doda Klijent
    }

    @Override
    public List<Cjenovnik> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Cjenovnik.class, id, NAZIV_KOLONE);
    }


    @Override
    public List<Cjenovnik> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Cjenovnik.class, podaci, kolone);
    }

    @Override
    public void dodaj(Cjenovnik cjenovnik) {
        jpaUtil.dodaj(cjenovnik);
    }

    @Override
    public boolean izmijeni(Cjenovnik cjenovnik) {
        return jpaUtil.izmijeni(cjenovnik);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(Cjenovnik cjenovnik) {
        return jpaUtil.obrisi(cjenovnik);
    }
}
