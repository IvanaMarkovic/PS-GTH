package ps.projekat.podaci.baza;

import ps.projekat.util.CitanjePropertiesFajla;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {

    private String jdbcURL;
    private String korisnickoIme;
    private String lozinka;
    private int brojPredkonekcija;
    private int maxIdleKonekcija;
    private int maxKonekcija;

    private int brojKonekcija;
    private List<Connection> zauzeteKonekcije;
    private List<Connection> slobodneKonekcije;

    private static ConnectionPool instanca;

    public static ConnectionPool getInstanca() {
        if (instanca == null)
            instanca = new ConnectionPool();
        return instanca;
    }

    private ConnectionPool() {
        konfiguracijaParametara();
        try {
            slobodneKonekcije = new ArrayList<>();
            zauzeteKonekcije = new ArrayList<>();

            for (int i = 0; i < brojPredkonekcija; i++) {
                Connection konekcija = DriverManager.getConnection(jdbcURL, korisnickoIme, lozinka);
                slobodneKonekcije.add(konekcija);
            }
            brojKonekcija = brojPredkonekcija;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void konfiguracijaParametara() {
        Properties konfiguracija =  (new CitanjePropertiesFajla("bazaPodataka.properties")).getKonfiguracija();
        jdbcURL = konfiguracija.getProperty("jdbcURL");
        korisnickoIme = konfiguracija.getProperty("korisnickoIme");
        lozinka = konfiguracija.getProperty("lozinka");
        brojPredkonekcija = 0;
        maxIdleKonekcija = 10;
        maxKonekcija = 10;
        try {
            brojPredkonekcija = Integer.parseInt(konfiguracija.getProperty("brojPredkonekcija"));
            maxIdleKonekcija = Integer.parseInt(konfiguracija.getProperty("maxIdleKonekcija"));
            maxKonekcija = Integer.parseInt(konfiguracija.getProperty("maxKonekcija"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection provjeraKonekcije() throws SQLException {
        Connection konekcija = null;
        if (slobodneKonekcije.size() > 0) {
            konekcija = slobodneKonekcije.remove(0);
            zauzeteKonekcije.add(konekcija);
        } else {
            if (brojKonekcija < maxKonekcija) {
                konekcija = DriverManager.getConnection(jdbcURL, korisnickoIme, lozinka);
                zauzeteKonekcije.add(konekcija);
                brojKonekcija++;
            } else {
                try {
                    wait();
                    konekcija = slobodneKonekcije.remove(0);
                    zauzeteKonekcije.add(konekcija);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return konekcija;
    }

    public synchronized void prijavljivanjeKonekcije(Connection konekcija) {
        if (konekcija == null)
            return;
        if (zauzeteKonekcije.remove(konekcija)) {
            slobodneKonekcije.add(konekcija);
            while (slobodneKonekcije.size() > maxIdleKonekcija) {
                int lastOne = slobodneKonekcije.size() - 1;
                Connection c = slobodneKonekcije.remove(lastOne);
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            notify();
        }
    }
}
