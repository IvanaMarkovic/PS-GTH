package ps.projekat.podaci.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import ps.projekat.podaci.dao.KlijentDAO;
import ps.projekat.podaci.dto.BrojTelefona;
import ps.projekat.podaci.dto.Klijent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * KlijentFabrika je klasa za rad sa objektima tipa Klijent.
 */
public class KlijentFabrika extends ApstraktnaFabrika<Klijent> {

    public KlijentFabrika() {
        dao = new KlijentDAO();
    }


    /**
     * Metoda za kreiranje objekta tipa Klijent. Parsira podatke i na osnovu njih kreira klijenta.
     * Takodje, ovdje se poziva i funckija koja dodaje kreiranog klijenta u bazu podataka.
     * @param podaci podaci o klijentu
     * @return vraca klijenta koji je kreiran
     */
    @Override
    public Klijent kreiraj(List<String> podaci) {
        ArrayList<String> brojeviTelefona = new ArrayList<>();
        Arrays.asList(podaci.get(5).split(", ")).stream().filter(e -> !e.equals("")).forEach(e ->  brojeviTelefona.add(e));
        Klijent klijent = new Klijent(Integer.parseInt(podaci.get(0)), podaci.get(1), podaci.get(2),
                podaci.get(3), podaci.get(4), brojeviTelefona, podaci.get(6));
        dao.dodaj(klijent);
        return klijent;

    }

    /**
     * Metoda koja na osnovu prosliedjenih podatak mijenja sve atribute klijenta, te poziva odgovarajucu metodu
     * za unos u bazu podataka.
     * @param objekat objekat koji se mijenja
     * @param podaci novi podaci koji trebaju biti dodijljeni
     * @return vraca klijenta sa izmijenjenim podacima
     */
    @Override
    public Klijent izmijeni(Klijent objekat, List<String> podaci) {
        objekat.setIdAdminstratora(Long.parseLong(podaci.get(0)));
        objekat.setIme(podaci.get(1));
        objekat.setPrezime(podaci.get(2));
        objekat.setNazivFirme(podaci.get(3));
        objekat.setEmailAdresa(podaci.get(4));
        List<String> pomocnaLista = Arrays.asList(podaci.get(5).split(", ")).stream().filter(e -> !e.equals("")).collect(Collectors.toList());
        objekat.getBrojeviTelefona().removeAll(objekat.getBrojeviTelefona());
        pomocnaLista.stream().filter(b -> !b.equals("") && !objekat.getBrojeviTelefona().contains(b)).forEach(b -> objekat.getBrojeviTelefona().add(new BrojTelefona(b, objekat)));
        objekat.setEdukacije(podaci.get(6));
        dao.izmijeni(objekat);
        return objekat;
    }

    /**
     * Metoda koja se poziva u slucaju da zelimo da obrisemo klijenta, pri cemu njegov atribut oznaciObrisan setujemo na true.
     * Pored toga se savki broj telefona takodje setuje da treba biti obisan.
     * @param id id klijenta kojeg zelimo obrisati
     */
    @Override
    public void izbrisi(long id) {
        Klijent klijent = pronadjiPoId(id).get(0);
        if(klijent != null) {
            klijent.setOznaciObrisan(true);
            klijent.getBrojeviTelefona().forEach(b -> b.setOznaciObrisan(true));
            dao.obrisi(klijent);
        }
    }
}
