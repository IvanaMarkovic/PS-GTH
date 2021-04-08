package ps.projekat.prikaziKontroleri;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.Cjenovnik;
import ps.projekat.podaci.dto.Edukacija;
import ps.projekat.podaci.dto.Klijent;
import ps.projekat.podaci.dto.Sala;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Kontroler {

    protected static Fasada fasada;
    protected static Properties konfiguracija;
    protected static HashMap<Long,String> klijenti = new HashMap<>();
    protected static HashMap<Long,String> cjenovnici = new HashMap<>();
    protected static HashMap<Long,String> sale = new HashMap<>();
    protected static HashMap<Long,String> edukacije = new HashMap<>();

    static {
        fasada = Fasada.getInstance();
        konfiguracija = (new CitanjePropertiesFajla("putanjeFajlova.properties")).getKonfiguracija();
        inicijalizacijaHashMapa();
    }

    private static void inicijalizacijaHashMapa() {
        fasada.setFabrika(TipFabrike.KLIJENT);
        List<Klijent> klijenti = fasada.pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        klijenti.forEach(k -> Kontroler.klijenti.put(k.getIdKlijenta(), k.getIme() + " " + k.getPrezime()));
        fasada.setFabrika(TipFabrike.CJENOVNIK);
        List<Cjenovnik> cjenovnici = fasada.pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        cjenovnici.forEach(c -> Kontroler.cjenovnici.put(c.getIdCjenovnika(), c.getNaziv()));
        fasada.setFabrika(TipFabrike.SALA);
        List<Sala> sale = fasada.pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        sale.forEach(s -> Kontroler.sale.put(s.getIdSale(), s.getNazivSale()));
        fasada.setFabrika(TipFabrike.EDUKACIJA);
        List<Edukacija> edukacije = fasada.pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        edukacije.forEach(e-> Kontroler.edukacije.put(e.getIdEdukacije(), e.getNazivEdukacije()));
    }

    protected static void prikaziNarednuScenu(Node element, String fxmlFajl) {
        try {
            Stage prozor = (Stage) element.getScene().getWindow();
            URL url = new File(konfiguracija.getProperty("fxmlFajlovi") + fxmlFajl).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = FXMLLoader.load(url);
            Scene scena = new Scene(root, element.getScene().getWidth(), element.getScene().getHeight());
            scena.getStylesheets().add(new File(konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
            prozor.setScene(scena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void prikaziProzorZaPotvrdu(Node element, Rezim rezim) {
        URL url = null;
        try {
            url = new File(konfiguracija.getProperty("fxmlFajlovi") + "izlazSaSistema.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            IzlazKontroler kontroler = loader.getController();
            kontroler.setRezim(rezim);
            kontroler.setElement(element);
            Scene scena = new Scene(root);
            Stage prozorZaIzmjenu = new Stage();
            prozorZaIzmjenu.setScene(scena);
            prozorZaIzmjenu.setResizable(false);
            prozorZaIzmjenu.setTitle("Global Thinkers Helper");
            prozorZaIzmjenu.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected long odredjivjanjeIdentifikatora(String vrijednost, HashMap<Long,String> mapa) {
        for(Map.Entry<Long,String> polje : mapa.entrySet()) {
            if(polje.getValue().equals(vrijednost)) {
                return polje.getKey();
            }
        }
        return 0L;
    }
}
