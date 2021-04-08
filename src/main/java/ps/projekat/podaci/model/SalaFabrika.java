package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.SalaDAO;
import ps.projekat.podaci.dto.Sala;

import java.util.ArrayList;
import java.util.List;

public class SalaFabrika extends ApstraktnaFabrika<Sala> {

    public SalaFabrika() {
        dao = new SalaDAO();
    }

    @Override
    public Sala kreiraj(List<String> podaci) {
        Sala sala = new Sala(podaci.get(0));
        dao.dodaj(sala);
        return sala;
    }

    @Override
    public Sala izmijeni(Sala objekat, List<String> podaci) {
        if(!objekat.getNazivSale().equals(podaci.get(0))) {
            objekat.setNazivSale(podaci.get(0));
            dao.izmijeni(objekat);
        }
        return objekat;
    }

    @Override
    public void izbrisi(long id) {
        Sala sala = pronadjiPoId(id).get(0);
        if(sala != null) {
            sala.setOznaciObrisan(true);
            dao.obrisi(sala);
        }
    }
}
