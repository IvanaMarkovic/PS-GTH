package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Racun;

import java.util.List;

public class RacunDAO extends DAO<Racun> {

    private static final String NAZIV_KOLONE = "IdentifikatorRezervacije";

    public List<Racun> selektujSve() {
        return super.selektujSve(Racun.class); // treba Klijent umjesto KlijentDAO - zamijeniti kada se doda Klijent
    }

    public Racun selektujJedan(int id) {
        return super.selektujJedan(Racun.class, id, NAZIV_KOLONE);
    }

    @Override
    public void dodaj(Racun racun) {
        super.dodaj(racun);
    }

    @Override
    public boolean izmijeni(Racun racun) {
        return super.izmijeni(racun);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(Racun racun) {
        return super.obrisi(racun);
    }
}
