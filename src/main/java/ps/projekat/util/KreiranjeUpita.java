package ps.projekat.util;

import java.util.ArrayList;
import java.util.List;

public class KreiranjeUpita<T> {

    private static final String SELECT = "select ";
    private static final String FROM = "from ";
    private static final String INNER_JOIN = " inner join ";
    private static final String WHERE =" where ";
    private static final String CONDITION = " = :";
    private static final String LIKE = " like %";
    private StringBuilder upit;
    private byte objekat;
    private Class<T> klasa;

    public KreiranjeUpita(Class<T> klasa) {
        this.klasa = klasa;
        objekat = 'a';
        upit = new StringBuilder();
    }

    public String selektovanjeSvihRedova() {
        return upit.append(FROM + klasa.getSimpleName()).toString();
    }

}
