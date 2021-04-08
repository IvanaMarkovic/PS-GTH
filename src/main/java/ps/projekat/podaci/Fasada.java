package ps.projekat.podaci;

import ps.projekat.podaci.dao.DAO;
import ps.projekat.podaci.dao.NalogAdministratoraDAO;
import ps.projekat.podaci.dao.TerminDAO;
import ps.projekat.podaci.dto.*;
import ps.projekat.podaci.model.ApstraktnaFabrika;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;

import java.util.List;

public class Fasada {

    private ApstraktnaFabrika fabrika;
    private NalogAdministratora prijavljenKorisnik;
    private Klijent klijent;
    private Cjenovnik cjenovnik;
    private Rezervacija rezervacija;
    private static Fasada fasada;

    public static Fasada getInstance() {
        if(fasada ==  null) {
            fasada = new Fasada();
        }
        return fasada;
    }

    public Fasada() {
        fabrika = ApstraktnaFabrika.kreirajFabriku(TipFabrike.ADMIN);
        prijavljenKorisnik = new NalogAdministratora();
    }

    public void setFabrika(TipFabrike tipFabrike) {
        fabrika = ApstraktnaFabrika.kreirajFabriku(tipFabrike);
    }

    public NalogAdministratora getPrijavljenKorisnik() {
        return prijavljenKorisnik;
    }

    public void setPrijavljenKorisnik(NalogAdministratora prijavljenKorisnik) {
        this.prijavljenKorisnik = prijavljenKorisnik;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public Cjenovnik getCjenovnik() {
        return cjenovnik;
    }

    public void setCjenovnik(Cjenovnik cjenovnik) {
        this.cjenovnik = cjenovnik;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public <T> List<T> akcija(TipAkcije tipAkcije, T objekat, List<String> podaci, long id) {
        return fabrika.akcija(tipAkcije, objekat, podaci, id);
    }

    public <T> List<T> pretrazivanjeObjekata(TipAkcije tipAkcije, List<String> podaci, List<String> kolone, long id) {
        return fabrika.pretrazivanjeObjekata(tipAkcije, podaci, kolone, id);
    }

    public List<Termin> selektujPoDatumu(List<String> datumi) {
        TerminDAO dao = new TerminDAO();
        return dao.selektujPoDatumu(datumi);
    }

    public Long provjeraKorisnickogImena(String korisnickoIme) {
        NalogAdministratoraDAO dao = new NalogAdministratoraDAO();
        return dao.provjeraKorisnickogImena(korisnickoIme);
    }
}
