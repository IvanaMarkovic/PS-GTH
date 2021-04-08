package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.EdukacijaDAO;
import ps.projekat.podaci.dto.Edukacija;

import java.util.ArrayList;
import java.util.List;

public class EdukacijaFabrika extends ApstraktnaFabrika<Edukacija> {

    public EdukacijaFabrika() {
        dao = new EdukacijaDAO();
    }

    @Override
    public Edukacija kreiraj(List<String> podaci) {
        Edukacija edukacija = new Edukacija(podaci.get(0));
        dao.dodaj(edukacija);
        return edukacija;
    }

    @Override
    public Edukacija izmijeni(Edukacija objekat, List<String> podaci) {
        objekat.setNazivEdukacije(podaci.get(0));
        dao.izmijeni(objekat);
        return objekat;
    }

    @Override
    public void izbrisi(long id) {
        Edukacija edukacija = pronadjiPoId(id).get(0);
        if(edukacija != null) {
            edukacija.setOznaciObrisan(true);
            dao.obrisi(edukacija);
        }
    }
}
