package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.NalogAdministratora;
import ps.projekat.podaci.dto.Sala;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class SalaDAO implements DAO<Sala> {

    private static final String NAZIV_KOLONE = "idSale";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public SalaDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }


    @Override
    public List<Sala> selektujSve() {
        return jpaUtil.selektujSve(Sala.class);
    }

    @Override
    public List<Sala> selektujPoId(long id) {
        return jpaUtil.selektujPoId(Sala.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<Sala> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(Sala.class, podaci, kolone);
    }

    @Override
    public void dodaj(Sala sala) {
        jpaUtil.dodaj(sala);
    }

    @Override
    public boolean izmijeni(Sala sala) {
        return jpaUtil.izmijeni(sala);
    }

    @Override
    public boolean obrisi(Sala sala) {
        return jpaUtil.obrisi(sala);
    }
}
