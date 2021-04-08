package ps.projekat.podaci.dao;

import ps.projekat.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DAO<T> {

    private static EntityManagerFactory fabrika;
    private EntityManager menadzer;

    protected DAO() {
        fabrika = JPAUtil.getEntityManagerFactory();
    }

    protected List<T> selektujSve(Class<T> klasa) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<T> rezultat = menadzer.createQuery("from " + klasa.getSimpleName(), klasa).getResultList();
        menadzer.getTransaction().commit();
        return rezultat;
    }
    protected T selektujJedan(Class<T> klasa, int id, String nazivKolone) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        List<T> rezultat = menadzer.createQuery("select p from " + klasa.getSimpleName() + " p where p."
                + nazivKolone + " = :" + nazivKolone, klasa).setParameter(nazivKolone, id).getResultList();
        menadzer.getTransaction().commit();
        return rezultat.get(0);
    }

    protected void dodaj(T t) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        menadzer.persist(t);
        menadzer.flush();
        menadzer.getTransaction().commit();
    }

    public boolean izmijeni(T t) {
        menadzer = fabrika.createEntityManager();
        menadzer.getTransaction().begin();
        T izmijenjenObjekat = menadzer.merge(t);
        menadzer.getTransaction().commit();
        if(izmijenjenObjekat != null) {
            return true;
        }
        return false;
    }

    public boolean obrisi(T t) {
        return izmijeni(t);
    }

    public void upravljanjeMenaderom() {
        if(menadzer != null) {
            menadzer.close();
        }
    }
}
