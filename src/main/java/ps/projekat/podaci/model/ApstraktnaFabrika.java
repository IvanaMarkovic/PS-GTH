package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.DAO;
import ps.projekat.podaci.dao.NalogAdministratoraDAO;
import ps.projekat.podaci.dao.TerminDAO;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public abstract class ApstraktnaFabrika<T> {

    protected DAO<T> dao;

    public DAO<T> getDao() {
        return dao;
    }

    public void setDao(DAO<T> dao) {
        this.dao = dao;
    }

    public static ApstraktnaFabrika kreirajFabriku(TipFabrike tip) {

        switch (tip) {
            case BROJ_TELEFONA:
                return new BrojTelefonaFabrika();
            case CJENOVNIK:
                return new CjenovnikFabrika();
            case EDUKACIJA:
                return new EdukacijaFabrika();
            case KLIJENT:
                return new KlijentFabrika();
            case ADMIN:
                return new NalogFabrika();
            case RACUN:
                return new RacunFabrika();
            case REZERVACIJA:
                return new RezervacijaFabrika();
            case SALA:
                return new SalaFabrika();
            case TERMIN:
                return new TerminFabrika();
            default:
                return null;
        }
    }

    public List<T> akcija(TipAkcije tipAkcije, T objekat, List<String> podaci, long id) {
        List<T>  t = new ArrayList<>();
        switch (tipAkcije) {
            case KREIRAJ_NOVI_OBJEKAT:
                t.add(kreiraj(podaci));
                break;
            case IZMIJENI_OBJEKAT:
                t.add(izmijeni(objekat, podaci));
                break;
            case OBRISI_OBJEKAT:
                izbrisi(id);
                break;
        }
        return t;
    }

    public List<T> pretrazivanjeObjekata(TipAkcije tipAkcije, List<String> podaci, List<String> kolone, long id) {
        List<T>  t = new ArrayList<>();
        switch (tipAkcije) {
            case DOHVATI_SVE_OBJEKTE:
                t = dao.selektujSve();
                break;
            case PRONADJI_OBJEKAT_ID:
                t = pronadjiPoId(id);
                break;
            case PRONADJI_OBJEKAT_NAZIV:
                t = pronadjiPoNazivu(podaci, kolone);
                break;
        }
        return t;
    }

    protected abstract T kreiraj(List<String> podaci);
    protected abstract T izmijeni(T objekat, List<String> podaci);
    protected abstract void izbrisi(long id);

    protected List<T> pronadjiPoId(long id) {
        return dao.selektujPoId(id);
    }

    protected List<T> pronadjiPoNazivu(List<String> podaci, List<String> kolone) {
        return dao.selektujPoKriterijumu(podaci, kolone);
    }

}