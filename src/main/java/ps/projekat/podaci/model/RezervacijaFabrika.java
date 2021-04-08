package ps.projekat.podaci.model;

import org.w3c.dom.ls.LSOutput;
import ps.projekat.podaci.dao.RezervacijaDAO;
import ps.projekat.podaci.dto.Cjenovnik;
import ps.projekat.podaci.dto.Klijent;
import ps.projekat.podaci.dto.Rezervacija;
import ps.projekat.podaci.dto.Termin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RezervacijaFabrika extends ApstraktnaFabrika<Rezervacija> {

    public RezervacijaFabrika() {
        dao = new RezervacijaDAO();
    }

    @Override
    public Rezervacija kreiraj(List<String> podaci) {
        ApstraktnaFabrika fabrika = ApstraktnaFabrika.kreirajFabriku(TipFabrike.KLIJENT);
        Klijent klijent = (Klijent)fabrika.pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, Long.parseLong(podaci.get(0))).get(0);
        fabrika = ApstraktnaFabrika.kreirajFabriku(TipFabrike.CJENOVNIK);
        Cjenovnik cjenovnik = (Cjenovnik)fabrika.pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, Long.parseLong(podaci.get(1))).get(0);
        ArrayList<Termin> terminiRezervacije = kreiranjeTermina(null, podaci.get(2), TipAkcije.KREIRAJ_NOVI_OBJEKAT);
        Rezervacija rezervacija = new Rezervacija(terminiRezervacije, klijent, cjenovnik, Integer.parseInt(podaci.get(3)));
        dao.dodaj(rezervacija);
        return rezervacija;
    }

    @Override
    public Rezervacija izmijeni(Rezervacija objekat, List<String> podaci) {
        ApstraktnaFabrika fabrika;
        if(objekat.getKlijent().getIdKlijenta() != Long.parseLong(podaci.get(0))) {
            fabrika = ApstraktnaFabrika.kreirajFabriku(TipFabrike.KLIJENT);
            objekat.setKlijent((Klijent) fabrika.pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, Long.parseLong(podaci.get(0))).get(0));
        }
        if(objekat.getCjenovnik().getIdCjenovnika() != Long.parseLong(podaci.get(1))) {
            fabrika = ApstraktnaFabrika.kreirajFabriku(TipFabrike.CJENOVNIK);
            objekat.setCjenovnik((Cjenovnik) fabrika.pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, Long.parseLong(podaci.get(1))).get(0));
        }
        kreiranjeTermina(objekat, podaci.get(2), TipAkcije.IZMIJENI_OBJEKAT);
        dao.izmijeni(objekat);
        return objekat;
    }

    private ArrayList<Termin> kreiranjeTermina(Rezervacija objekat, String podaci, TipAkcije tipAkcije) {
        List<String> terminiString = Arrays.asList(podaci.split("#"));
        ArrayList<Termin> termini = new ArrayList<>();
        terminiString.forEach(t -> {
            ApstraktnaFabrika terminFabrika = ApstraktnaFabrika.kreirajFabriku(TipFabrike.TERMIN);
            List<String> podaciOTerminu = Arrays.asList(t.split(","));
            termini.add((Termin)terminFabrika.akcija(TipAkcije.KREIRAJ_NOVI_OBJEKAT, null, podaciOTerminu, 0).get(0));
        });
        if(tipAkcije.equals(TipAkcije.IZMIJENI_OBJEKAT)) {
            objekat.setTermini(termini);
            objekat.getTermini().forEach(t -> t.setRezervacija(objekat));
        }
        return termini;
    }

    @Override
    public void izbrisi(long id) {
        Rezervacija rezervacija = pronadjiPoId(id).get(0);
        if(rezervacija != null) {
            rezervacija.setOznaciObrisana(true);
            rezervacija.getTermini().forEach(t -> t.setOznaciObrisan(true));
            rezervacija.getRacun().setOznaciObrisan(true);
            dao.obrisi(rezervacija);
        }
    }
}
