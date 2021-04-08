package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.BrojTelefona;

import java.util.List;

public class BrojTelefonaDAO extends DAO<BrojTelefona> {

    private static final String NAZIV_KOLONE = "IdentifikatorKlijenta";
    public List<BrojTelefona> selektujSve() {
        return super.selektujSve(BrojTelefona.class); // treba Klijent umjesto KlijentDAO - zamijeniti kada se doda Klijent
    }

    public BrojTelefona selektujJedan(int id) {
        return super.selektujJedan(BrojTelefona.class, id, NAZIV_KOLONE);
    }

    @Override
    public void dodaj(BrojTelefona brojTelefona) {
        super.dodaj(brojTelefona);
    }

    @Override
    public boolean izmijeni(BrojTelefona brojTelefona) {
        return super.izmijeni(brojTelefona);
    }

    @Override
    public boolean obrisi(BrojTelefona BrojTelefona) {
        return super.obrisi(BrojTelefona);
    }
}
