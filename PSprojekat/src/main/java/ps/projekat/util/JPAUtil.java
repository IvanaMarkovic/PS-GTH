package ps.projekat.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "GLobalThinkersHelper";
    private static EntityManagerFactory fabrika;

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
}
