package ps.projekat.prikaziKontroleri;

import com.calendarfx.model.Entry;
import com.calendarfx.util.Util;
import com.calendarfx.view.Messages;
import com.calendarfx.view.RecurrenceView;
import com.calendarfx.view.TimeField;
import com.calendarfx.view.popover.EntryPopOverPane;
import com.calendarfx.view.popover.RecurrencePopup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.Rezervacija;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntryDetalji extends EntryPopOverPane {

    Hyperlink hypLnkKreiraj;
    Hyperlink hypLnkIzmijeni;
    Hyperlink hypLnkObrisi;

    public EntryDetalji(Entry<?> termin) {
        super();
        getStyleClass().add("entry-details-view");

        VBox vBoxDetalji = new VBox();
        vBoxDetalji.setAlignment(Pos.TOP_LEFT);
        vBoxDetalji.setSpacing(10);

        hypLnkKreiraj = new Hyperlink("Kreiraj rezervaciju");
        hypLnkKreiraj.setId("hypLnkKreiraj");
        hypLnkIzmijeni = new Hyperlink("Izmijeni rezervaciju");
        hypLnkIzmijeni.setId("hypLnkIzmijeni");
        hypLnkObrisi = new Hyperlink("Obriši rezervaciju");
        hypLnkObrisi.setId("hypLnkObrisi");

        (FXCollections.observableArrayList(
                hypLnkKreiraj,
                hypLnkIzmijeni,
                hypLnkObrisi
        )).forEach(hl -> {
            vBoxDetalji.setMargin(hl, new Insets(0,0,0,20));
        });

        if(termin.getTitle().equals("")) {
            omoguciPolja(true);
        } else {
            omoguciPolja(false);
        }
        vBoxDetalji.getChildren().addAll(hypLnkKreiraj, hypLnkIzmijeni, hypLnkObrisi);
        hypLnkKreiraj.setOnMouseClicked(e -> upravljanjeRezervacijom(termin, hypLnkKreiraj));
        hypLnkIzmijeni.setOnMouseClicked(e -> upravljanjeRezervacijom(termin, hypLnkIzmijeni));
        hypLnkObrisi.setOnMouseClicked(e -> upravljanjeRezervacijom(termin, hypLnkObrisi));
        getChildren().add(vBoxDetalji);
    }

    public void omoguciPolja(boolean opcija) {
        hypLnkKreiraj.setDisable(!opcija);
        hypLnkIzmijeni.setDisable(opcija);
        hypLnkObrisi.setDisable(opcija);
    }

    private void upravljanjeRezervacijom(Entry<?> termin, Node element) {
        StringBuilder podaciOTerminu = new StringBuilder();
        podaciOTerminu.append(odredjivjanjeIdentifikatora(termin.getCalendar().getName(), Kontroler.sale) + ",");
        podaciOTerminu.append(termin.getStartDate() + "," + termin.getStartTime() + ":00" + ","  + termin.getEndTime() + ":00");
        String[] indeksi = termin.getLocation() != null ? termin.getLocation().split(" ") : new String[]{};
        String id = element.getId();
        switch (id) {
            case "hypLnkKreiraj":
                unosTermina(podaciOTerminu.toString(), 0, 0, hypLnkKreiraj);
                break;
            case "hypLnkIzmijeni":

                unosTermina(podaciOTerminu.toString(), Integer.parseInt(indeksi[indeksi.length - 1]) - 1, Integer.parseInt(indeksi[1]), hypLnkIzmijeni);
            case "hypLnkObrisi":
                unosTermina(podaciOTerminu.toString(), Integer.parseInt(indeksi[indeksi.length - 1]) - 1, Integer.parseInt(indeksi[1]), hypLnkObrisi);
                break;
        }
        termin.setCalendar(null);
    }

    private long odredjivjanjeIdentifikatora(String vrijednost, HashMap<Long,String> mapa) {
        for(Map.Entry<Long,String> polje : mapa.entrySet()) {
            if(polje.getValue().equals(vrijednost)) {
                return polje.getKey();
            }
        }
        return 0L;
    }

    public void unosTermina(String podaciOTerminu, int indeks, long idRezervacije, Node element) {
        URL url = null;
        try {
            url = new File(Kontroler.konfiguracija.getProperty("fxmlFajlovi") + "obrasci.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            ObrasciKontroler kontroler = loader.getController();
            kontroler.onAnchPnRezervacijaClicked(null);
            String id = element.getId();
            Rezervacija rezervacija = null;
            switch (id) {
                case "hypLnkKreiraj":
                    kontroler.onRdBtnKreiranjeRezervacijeClicked(null);
                    kontroler.rdBtnKreiranjeRezervacije.setSelected(true);
                    kontroler.cmbBoxTermini.getItems().add(podaciOTerminu);
                    break;
                case "hypLnkIzmijeni":
                    kontroler.onRdBtnIzmjenaRezervacijeClicked(null);
                    kontroler.rdBtnIzmjenaRezervacije.setSelected(true);
                    Kontroler.fasada.setFabrika(TipFabrike.REZERVACIJA);
                    Kontroler.fasada.setRezervacija(rezervacija = Kontroler.fasada.<Rezervacija>pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, idRezervacije).get(0));
                    Kontroler.fasada.setKlijent(rezervacija.getKlijent());
                    Kontroler.fasada.setCjenovnik(rezervacija.getCjenovnik());
                    kontroler.postaviPoljaRezervaicija();
                    kontroler.cmbBoxTermini.getItems().remove(indeks);
                    kontroler.cmbBoxTermini.getItems().add(podaciOTerminu);
                    break;
                case "hypLnkObrisi":
                    kontroler.onRdBtnBrisanjeRezervacijeClicked(null);
                    kontroler.rdBtnBrisanjeRezervacije.setSelected(true);
                    Kontroler.fasada.setFabrika(TipFabrike.REZERVACIJA);
                    Kontroler.fasada.setRezervacija(rezervacija = Kontroler.fasada.<Rezervacija>pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, idRezervacije).get(0));
                    Kontroler.fasada.setKlijent(rezervacija.getKlijent());
                    Kontroler.fasada.setCjenovnik(rezervacija.getCjenovnik());
                    kontroler.postaviPoljaRezervaicija();
                    break;
            }
            Scene scena = new Scene(root,  MjesecniIDnevniPrikaz.getKalendar().getScene().getWidth(),  MjesecniIDnevniPrikaz.getKalendar().getScene().getHeight());
            scena.getStylesheets().add(new File(Kontroler.konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
            ((Stage)MjesecniIDnevniPrikaz.getKalendar().getScene().getWindow()).setScene(scena);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
