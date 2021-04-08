package ps.projekat.podaci.model;

import ps.projekat.podaci.dao.TerminDAO;
import ps.projekat.podaci.dto.Termin;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TerminFabrika extends ApstraktnaFabrika<Termin> {

    public TerminFabrika() {
        dao = new TerminDAO();
    }

    @Override
    public Termin kreiraj(List<String> podaci) {
        SimpleDateFormat formatDatuma = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatVremena = new SimpleDateFormat("HH:mm:ss");
        try {
            Termin noviTermin = new Termin(Long.parseLong(podaci.get(0)),
                    new Date(formatDatuma.parse(podaci.get(1)).getTime()),
                    new Time(formatVremena.parse(podaci.get(2)).getTime()),
                    new Time(formatVremena.parse(podaci.get(3)).getTime()));
            return noviTermin;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Termin izmijeni(Termin objekat, List<String> podaci) {
        return objekat = kreiraj(podaci);
    }

    @Override
    public void izbrisi(long id) {
        Termin termin = pronadjiPoId(id).get(0);
        if(termin != null) {
            termin.setOznaciObrisan(true);
            dao.obrisi(termin);
        }
    }
}
