package ps.projekat.podaci.model;

import java.sql.Connection;
import java.util.List;

public abstract class AbstraktnaFabrika<T> {
    public abstract T action(TipAkcije tipAkcije, List<String> data);
    public static Connection createConnection(){
        //tijelo
        return null;
    }

}
