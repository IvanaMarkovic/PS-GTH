package ps.projekat.prikaziKontroleri;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.hibernate.tuple.CreationTimestampGeneration;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.Rezervacija;
import ps.projekat.podaci.dto.Termin;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MjesecniIDnevniPrikaz extends Kontroler implements Initializable {

    @FXML
    private VBox vBoxKalendar;

    @FXML
    ImageView imgNazadNaPocetnu;

    private static Scene scena = null;
    private static short brojSale = 1;
    private static CalendarView kalendar;

    public static Scene getScena() {
        return scena;
    }

    public static void setScena(Scene scena) {
        MjesecniIDnevniPrikaz.scena = scena;
    }

    public static CalendarView getKalendar() {
        return kalendar;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgNazadNaPocetnu.setImage(new Image(new File(konfiguracija.getProperty("slike") + "left.png").toURI().toString()));
        if(kalendar == null) {
            inicijalizacijaKalendara();
            vBoxKalendar.getChildren().add(kalendar);
            vBoxKalendar.setMargin(kalendar, new Insets(0, 20, 20 ,20));
        }
    }

    private void inicijalizacijaKalendara() {
        kalendar = new CalendarView();
        kalendar.setShowSearchField(false);
        kalendar.setShowPrintButton(false);
        kalendar.setShowDeveloperConsole(false);
        kalendar.setShowAddCalendarButton(false);
        kalendar.getDayPage().getToolBarControls().setVisible(false);
        kalendar.getDayPage().setShowDayPageLayoutControls(false);
        kalendar.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
        kalendar.getWeekPage().setHidden(true);
        kalendar.getYearPage().getToolBarControls().setVisible(false);
        kalendar.getYearPage().setHidden(true);
        kalendar.getCalendarSources().clear();
        CalendarSource sale = new CalendarSource("Sale");
        kalendar.getCalendarSources().add(sale);
        kalendar.setEntryDetailsPopOverContentCallback(p -> new PrikazEntry(p.getPopOver(), p.getDateControl(), p.getEntry()));
        Kontroler.sale.values().forEach(s -> upravljanjeSalama(s, true));
        Kontroler.fasada.setFabrika(TipFabrike.REZERVACIJA);
        List<Rezervacija> rezervacije = Kontroler.fasada.<Rezervacija>pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        rezervacije.forEach(r -> {
            Kontroler.fasada.setRezervacija(r);
            kreiranjeRezervacije();
        });
    }

    public void onImgNazadNaPocetnuClicked(MouseEvent mouseEvent) {
        scena = imgNazadNaPocetnu.getScene();
        Kontroler.prikaziNarednuScenu(imgNazadNaPocetnu, "pocetnaStrana.fxml");
    }

    public void upravljanjeSalama(String nazivSale, boolean kreiranje) {
        if(kreiranje) {
            Calendar sala = new Calendar(nazivSale);
            int broj = brojSale % 6;
            switch (broj) {
                case 1:
                    sala.setStyle(Calendar.Style.STYLE1);
                    break;
                case 2:
                    sala.setStyle(Calendar.Style.STYLE2);
                    break;
                case 3:
                    sala.setStyle(Calendar.Style.STYLE3);
                    break;
                case 4:
                    sala.setStyle(Calendar.Style.STYLE4);
                    break;
                case 5:
                    sala.setStyle(Calendar.Style.STYLE5);
                    break;
                default:
                    sala.setStyle(Calendar.Style.STYLE6);
                    break;
            }
            brojSale++;
            kalendar.getCalendarSources().get(0).getCalendars().add(sala);
        } else {
            List<Calendar> sale = kalendar.getCalendarSources().get(0).getCalendars();
            sale.removeIf(s -> s.getName().equals(nazivSale));
        }
    }

    public static void kreiranjeRezervacije() {
        Rezervacija rezervacija = fasada.getRezervacija();
        rezervacija.getTermini().forEach(t -> {
            Entry<String> termin = new Entry<>();
            termin.setId(String.valueOf(rezervacija.getIdRezervacije()));
            termin.setTitle(rezervacija.getKlijent().getIme() + " " + rezervacija.getKlijent().getPrezime());
            termin.setLocation("Rezervacija " + rezervacija.getIdRezervacije() + " - Termin " + (rezervacija.getTermini().indexOf(t) + 1));
            termin.setInterval(t.getDatumIznajmljivanjaPocetak().toLocalDate());
            termin.setInterval(t.getVrijemeIznajmljivanjaPocetak().toLocalTime(), t.getVrijemeIznajmljivanjaKraj().toLocalTime());
            List<Calendar> sale = kalendar.getCalendarSources().get(0).getCalendars();
            sale.forEach(s -> {
                if(s.getName().equals(Kontroler.sale.get(t.getIdSale()))) {
                    s.addEntry(termin);
                }
            });
        });
        scena = kalendar.getScene();
    }

    public static void brisanjeRezervacije() {
        Rezervacija rezervacija = fasada.getRezervacija();
        rezervacija.getTermini().forEach(t -> {
            String uslov = rezervacija.getKlijent().getIme() + " " + rezervacija.getKlijent().getPrezime();
            kalendar.getCalendarSources().get(0).getCalendars().forEach(s -> {
                s.findEntries(uslov).stream().filter(e -> e.getId().equals(String.valueOf(rezervacija.getIdRezervacije())))
                       .collect(Collectors.toList()).forEach(Entry::removeFromCalendar);
            });
        });
    }
}
