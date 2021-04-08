package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Cjenovnik;

import java.util.List;

public class CjenovnikDAO extends DAO<Cjenovnik> {

    private static final String NAZIV_KOLONE = "idCjenovnika";

    public List<Cjenovnik> selektujSve() {
        return super.selektujSve(Cjenovnik.class); // treba Cjenovnik umjesto CjenovnikDAO - zamijeniti kada se doda Klijent
    }

    public Cjenovnik selektujJedan(int id) {
        return super.selektujJedan(Cjenovnik.class, id, NAZIV_KOLONE);
    }

    @Override
    public void dodaj(Cjenovnik cjenovnik) {
        super.dodaj(cjenovnik);
    }

    @Override
    public boolean izmijeni(Cjenovnik cjenovnik) {
        return super.izmijeni(cjenovnik);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(Cjenovnik cjenovnik) {
        return super.obrisi(cjenovnik);
    }
}
