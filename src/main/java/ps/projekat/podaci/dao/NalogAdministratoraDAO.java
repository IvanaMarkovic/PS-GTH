package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Edukacija;
import ps.projekat.podaci.dto.NalogAdministratora;
import ps.projekat.prikaziKontroleri.NalogKontroler;
import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class NalogAdministratoraDAO implements DAO<NalogAdministratora> {

    private static final String NAZIV_KOLONE = "idAdministratora";
    private static EntityManagerFactory fabrika;
    private JPAUtil jpaUtil;

    public NalogAdministratoraDAO() {
        jpaUtil = new JPAUtil();
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    public JPAUtil getJpaUtil() {
        return jpaUtil;
    }

    public List<NalogAdministratora> selektujSve() {
        return jpaUtil.selektujSve(NalogAdministratora.class);
    }

    public List<NalogAdministratora> selektujPoId(long id) {
        return jpaUtil.selektujPoId(NalogAdministratora.class, id, NAZIV_KOLONE);
    }

    @Override
    public List<NalogAdministratora> selektujPoKriterijumu(List<String> podaci, List<String> kolone) {
        return jpaUtil.selektujPoKriterijumu(NalogAdministratora.class, podaci, kolone);
    }

    public Long provjeraKorisnickogImena(String korisnickoIme) {
        return jpaUtil.provjeraKorisnickogImena(NalogAdministratora.class, korisnickoIme);
    }

    @Override
    public void dodaj(NalogAdministratora nalogAdministratora) {
        jpaUtil.dodaj(nalogAdministratora);
    }

    @Override
    public boolean izmijeni(NalogAdministratora nalogAdministratora) {
        return jpaUtil.izmijeni(nalogAdministratora);
    }

    @Override
    public boolean obrisi(NalogAdministratora nalogAdministratora) {
        return jpaUtil.obrisi(nalogAdministratora);
    }
}
