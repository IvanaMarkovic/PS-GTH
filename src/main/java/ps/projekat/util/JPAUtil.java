package ps.projekat.util;

import javafx.collections.FXCollections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "GTH";
    private static EntityManagerFactory fabrika;
    private EntityManager menadzer;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (fabrika == null) {
            fabrika = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return fabrika;
    }

    public static void zatvori() {
        if (fabrika != null) {
            fabrika.close();
        }
    }

    public EntityManager getMenadzer() {
        return menadzer;
    }

    public void setMenadzer(EntityManager menadzer) {
        this.menadzer = menadzer;
    }

    public <T> List<T> selektujSve(Class<T> klasa) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<T> rezultat = menadzer.createQuery("from " + klasa.getSimpleName() +" where oznaciObrisan = 0", klasa).getResultList();
        menadzer.getTransaction().commit();
        return rezultat;
    }

    public <T> List<T> selektujPoId(Class<T> klasa, long id, String nazivKolone) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<T> rezultat = menadzer.createQuery("select p from " + klasa.getSimpleName() + " p where p."
                + nazivKolone + " = :" + nazivKolone + " and  p.oznaciObrisan = 0", klasa).setParameter(nazivKolone, id).getResultList();
        menadzer.getTransaction().commit();
        return rezultat;
    }

    public <T> List<T> selektujPoKriterijumu(Class<T> klasa, List<String> podaci, List<String> kolone) {
        StringBuilder upit = new StringBuilder(" p where p.");
        for(int i = 0; i < podaci.size(); i++) {
            upit.append(kolone.get(i) + " like '" + podaci.get(i) + "'");
            if(i != podaci.size() - 1) {
                upit.append(" and p.");
            }
        }
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<T> rezultat = menadzer.createQuery("select p from " + klasa.getSimpleName() + upit.toString() + " and oznaciObrisan = 0", klasa).getResultList();
        menadzer.getTransaction().commit();
        return rezultat;
    }

    public <T> List<T> selektujPoDatumu(Class<T> klasa, List<String> podaci) {
        StringBuilder upit = new StringBuilder(" p where p.");
        SimpleDateFormat formatDatuma = new SimpleDateFormat("yyyy-MM-dd");
        upit.append("datumIznajmljivanjaPocetak" + " >= '" + podaci.get(0) + "'");
        upit.append(" and p.datumIznajmljivanjaPocetak" + " <= '" + podaci.get(1) + "'");
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<T> rezultat = menadzer.createQuery("select p from " + klasa.getSimpleName() + upit.toString() + " and p.oznaciObrisan = 0", klasa).getResultList();
        menadzer.getTransaction().commit();
        return rezultat;
    }

    public <T> long provjeraKorisnickogImena(Class<T> klasa, String korisnickoIme) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<Long> rezultat = menadzer.createQuery("select count(*) from " + klasa.getSimpleName() + " p where p.korisnickoIme like '" + korisnickoIme + "' and p.oznaciObrisan = :oznaciObrisan", Long.class)
                .setParameter("oznaciObrisan", false).getResultList();
        menadzer.getTransaction().commit();
        return rezultat.get(0);
    }

    public <T> void dodaj(T t) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        menadzer.persist(t);
        menadzer.getTransaction().commit();
    }

    public <T> boolean izmijeni(T t) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        T izmijenjenObjekat = menadzer.merge(t);
        menadzer.getTransaction().commit();
        if(izmijenjenObjekat != null) {
            return true;
        }
        return false;
    }

    public <T> boolean obrisi(T t) {
        return izmijeni(t);
    }

    public void upravljanjeMenadzerom() {
        if(menadzer != null) {
            menadzer.close();
        }
    }
}
