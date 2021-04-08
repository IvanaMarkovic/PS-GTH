package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.BrojTelefonaDAO;
import ps.projekat.podaci.dto.BrojTelefona;
import ps.projekat.podaci.dto.Klijent;

import java.util.ArrayList;
import java.util.List;

public class BrojTelefonaFabrika extends ApstraktnaFabrika<BrojTelefona> {

    public BrojTelefonaFabrika() {
        dao = new BrojTelefonaDAO();
    }

    @Override
    public BrojTelefona kreiraj(List<String> podaci) {
        BrojTelefona brojTelefona = new BrojTelefona(podaci.get(0), null);
        return brojTelefona;
    }

    @Override
    public BrojTelefona izmijeni(BrojTelefona objekat, List<String> podaci) {
        objekat.setBrojTelefona(podaci.get(0));
        return objekat;
    }

    @Override
    public void izbrisi(long id) {
        BrojTelefona broj = pronadjiPoId(id).get(0);
        if(broj != null) {
            broj.setOznaciObrisan(true);
            dao.obrisi(broj);
        }
    }
}
