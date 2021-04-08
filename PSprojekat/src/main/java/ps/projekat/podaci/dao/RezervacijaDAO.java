package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.Rezervacija;

import java.util.List;

public class RezervacijaDAO extends DAO<Rezervacija> {

    private static final String NAZIV_KOLONE = "idRezervacije";
    public List<Rezervacija> selektujSve() {
        return super.selektujSve(Rezervacija.class); // treba Klijent umjesto RezervacijaDAO - zamijeniti kada se doda Klijent
    }

    public Rezervacija selektujJedan(int id) {
        return super.selektujJedan(Rezervacija.class, id, NAZIV_KOLONE);
    }

    @Override
    public void dodaj(Rezervacija rezervacija) {
        super.dodaj(rezervacija);
        RacunDAO rdao = new RacunDAO();
        rezervacija.getRacun().setRezervacija(rezervacija);
        rdao.izmijeni(rezervacija.getRacun());
    }

    @Override
    public boolean izmijeni(Rezervacija rezervacija) {
        return super.izmijeni(rezervacija);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(Rezervacija rezervacija) {
        return super.obrisi(rezervacija);
    }
}
