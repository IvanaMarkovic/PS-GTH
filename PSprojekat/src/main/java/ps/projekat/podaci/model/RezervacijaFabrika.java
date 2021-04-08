package ps.projekat.podaci.model;

import ps.projekat.podaci.dto.Rezervacija;

import java.util.List;

public class RezervacijaFabrika extends AbstraktnaFabrika<Rezervacija> {

    @Override
    public Rezervacija action(TipAkcije tipAkcije, List<String> data) {
        return null;
    }
    public void kreirajRezervaciju(List<String> data){

    }
    public void izmjeniRezervaciju(List<String> data){

    }
    public void izbrisiRezervaciju(String idRezervacije){

    }
}
