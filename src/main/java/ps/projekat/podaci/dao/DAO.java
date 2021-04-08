package ps.projekat.podaci.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> selektujSve();
    List<T> selektujPoId(long id);
    List<T> selektujPoKriterijumu(List<String> podaci, List<String> kolone);
    void dodaj(T t);
    boolean izmijeni(T t);
    boolean obrisi(T t);
}
