package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.CjenovnikDAO;
import ps.projekat.podaci.dto.Cjenovnik;

import java.util.ArrayList;
import java.util.List;

public class CjenovnikFabrika extends ApstraktnaFabrika<Cjenovnik> {

    public CjenovnikFabrika() {
        dao = new CjenovnikDAO();
    }

    @Override
    public Cjenovnik kreiraj(List<String> podaci) {
        Cjenovnik noviCjenovnik = new Cjenovnik(Integer.parseInt(podaci.get(0)), podaci.get(1), Float.parseFloat(podaci.get(2)), Float.parseFloat(podaci.get(3)));
        dao.dodaj(noviCjenovnik);
        return noviCjenovnik;
    }

    @Override
    public Cjenovnik izmijeni(Cjenovnik objekat, List<String> podaci) {
        objekat.setIdAdministratora(Long.parseLong(podaci.get(0)));
        objekat.setNaziv(podaci.get(1));
        objekat.setCijenaPoSatu(Float.parseFloat(podaci.get(2)));
        objekat.setCijenaPoDanu(Float.parseFloat(podaci.get(3)));
        dao.izmijeni(objekat);
        return objekat;
    }

    @Override
    public void izbrisi(long id) {
        Cjenovnik cjenovnik = pronadjiPoId(id).get(0);
        if(cjenovnik != null) {
            cjenovnik.setOznaciObrisan(true);
            dao.obrisi(cjenovnik);
        }
    }
}
