package ps.projekat.podaci.dao;

import ps.projekat.podaci.dto.NalogAdministratora;

import java.util.List;

public class NalogAdministratoraDAO extends DAO<NalogAdministratora> {

    private static final String NAZIV_KOLONE = "idAdministratora";
    public List<NalogAdministratora> selektujSve() {
        return super.selektujSve(NalogAdministratora.class);
    }

    public NalogAdministratora selektujJedan(int id) {
        return super.selektujJedan(NalogAdministratora.class, id, NAZIV_KOLONE);
    }

    @Override
    public void dodaj(NalogAdministratora nalogAdministratora) {
        super.dodaj(nalogAdministratora);
    }

    @Override
    public boolean izmijeni(NalogAdministratora nalogAdministratora) {
        return super.izmijeni(nalogAdministratora);  //mozda se promijeni da vraca String kako bi dobili poruku na view-u
    }

    @Override
    public boolean obrisi(NalogAdministratora nalogAdministratora) {
        return super.obrisi(nalogAdministratora);
    }
}
