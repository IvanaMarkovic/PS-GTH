package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.RacunDAO;
import ps.projekat.podaci.dto.Racun;

import java.util.ArrayList;
import java.util.List;

public class RacunFabrika extends ApstraktnaFabrika<Racun> {

    public RacunFabrika() {
        dao = new RacunDAO();
    }

    @Override
    public Racun kreiraj(List<String> podaci) {
        return new Racun();
    }

    @Override
    public Racun izmijeni(Racun objekat, List<String> podaci) {
        objekat.setUkupanIznos(Float.parseFloat(podaci.get(0)));
        objekat.setIzdatRacun(Boolean.parseBoolean(podaci.get(1)));
        return objekat;
    }

    @Override
    public void izbrisi(long id) {
        Racun racun = pronadjiPoId(id).get(0);
        if(racun != null) {
            racun.setOznaciObrisan(true);
            dao.obrisi(racun);
        }
    }
}
