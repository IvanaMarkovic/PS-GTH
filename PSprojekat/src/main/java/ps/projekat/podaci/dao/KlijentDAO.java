package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.BrojTelefona;
import ps.projekat.podaci.dto.Klijent;
import ps.projekat.podaci.dto.NalogAdministratora;

import java.util.List;

public class KlijentDAO extends DAO<Klijent> {

    private static final String NAZIV_KOLONE = "idKlijenta";

    public List<Klijent> selektujSve() {
        return super.selektujSve(Klijent.class);
    }

    public Klijent selektujJedan(int id) {
        return super.selektujJedan(Klijent.class, id, NAZIV_KOLONE);
    }

    @Override
    public void dodaj(Klijent klijent) {
        super.dodaj(klijent);
        BrojTelefonaDAO btdao = new BrojTelefonaDAO();
        for(BrojTelefona broj : klijent.getBrojeviTelefona()) {
            broj.setKlijent(klijent);
            btdao.izmijeni(broj);
        }
    }

    @Override
    public boolean izmijeni(Klijent klijent) {
        return super.izmijeni(klijent);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(Klijent klijent) {
        return super.obrisi(klijent);
    }
}
