package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.NalogAdministratoraDAO;
import ps.projekat.podaci.dto.NalogAdministratora;
import ps.projekat.podaci.dto.Sala;

import java.util.ArrayList;
import java.util.List;

public class NalogFabrika extends ApstraktnaFabrika<NalogAdministratora> {

    public NalogFabrika() {
        dao = new NalogAdministratoraDAO();
    }

    @Override
    public NalogAdministratora kreiraj(List<String> podaci) {
        NalogAdministratora nalog = new NalogAdministratora(podaci.get(0),podaci.get(1), podaci.get(2), podaci.get(3));
        dao.dodaj(nalog);
        return nalog;
    }

    @Override
    public NalogAdministratora izmijeni(NalogAdministratora objekat, List<String> podaci) {
        objekat.setIme(podaci.get(0));
        objekat.setPrezime(podaci.get(1));
        objekat.setKorisnickoIme(podaci.get(2));
        objekat.setLozinka(podaci.get(3));
        objekat.setOznaciObrisan(Boolean.parseBoolean(podaci.get(4)));
        dao.izmijeni(objekat);
        return objekat;
    }

    @Override
    public void izbrisi(long id) {
        NalogAdministratora nalog = pronadjiPoId(id).get(0);
        if(nalog != null) {
            nalog.setOznaciObrisan(true);
            dao.obrisi(nalog);
        }
    }
}
