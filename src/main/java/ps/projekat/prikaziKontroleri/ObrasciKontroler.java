package ps.projekat.prikaziKontroleri;

import com.calendarfx.model.Entry;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.*;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObrasciKontroler extends Kontroler implements Initializable {
    
    @FXML
    ImageView imgNazadNaPocetnu, imgDodajBroj, imgIzmjenaBroja, imgBrisanjeBroja, imgDodajEdukaciju, imgIzmjenaEdukacije, imgBrisanjeEdukacije,
                imgDodajSalu, imgBrisanjeSale, imgDodajTermin, imgIzmjenaTermina, imgBrisanjeTermina;

    @FXML
    Button btnPotvrdi1, btnPotvrdi2, btnPotvrdi3;

    @FXML
    TextField txtFldPretraga1, txtFldPretraga2,
            txtFldIme, txtFldPrezime, txtFldFirma, txtFldEmail, txtFldBrojTelefona, txtFldEdukacije,
            txtFldCjenovnik, txtFldCijenaPoDanu, txtFldCijenaPoSatu,
            txtFldBirajKlijenta, txtFldBirajCjenovnik, txtFldPocetakSati, txtFldPocetakMinute, txtFldKrajSati, txtFldKrajMinute, txtFldSala;

    @FXML
    Label lblPretraga1, lblPretraga2, lblRezPretrage1, lblRezPretrage2,
            lblIme, lblPrezime, lblEmail, lblBrojTelefona, lblCijenaSat, lblCijenaDan, lblRezultati1, lblRezultati2, lblTermini, lblPoljaTermina;

    @FXML
    ToggleGroup tgGrpKlijent, tgGrpCjenovnik, tgGrpRezervacija, tgGrpTermin;

    @FXML
    RadioButton rdBtnKreiranjeKlijenta, rdBtnIzmjenaKlijenta, rdBtnBrisanjeKlijenta,
            rdBtnKreiranjeCjenovnika, rdBtnIzmjenaCjenovnika, rdBtnBrisanjeCjenovnika,
            rdBtnKreiranjeRezervacije, rdBtnIzmjenaRezervacije, rdBtnBrisanjeRezervacije,
            rdBtnPojedinacniDani, rdBtnUzastopniDani;

    @FXML
    ComboBox<String> cmbBoxBrojevi, cmbBoxEdukacije, cmbBoxTermini;

    @FXML
    CheckBox chckBoxSatnica;

    @FXML
    DatePicker dtPckPocetak, dtPckKraj;

    @FXML
    StackPane stkPnTab1, stkPnTab2, stkPnTab3, sckPnTermini;

    @FXML
    AnchorPane anchPnKlijenti, anchPnCjenovnik, anchPnRezervacija;

    @FXML
    TabPane tabPnObrasci;

    @FXML
    Tab tabKlijenti, tabCjenovnici, tabRezervacije;

    private List<String> regexIzrazi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(imgDodajTermin.getImage() ==  null) {
            inicijalizacijaElemenata();
        }
        tabPnObrasci.getTabs().setAll(tabKlijenti);
        stkPnTab1.setVisible(false);
        stkPnTab2.setVisible(false);
        stkPnTab3.setVisible(false);
    }

    private void inicijalizacijaElemenata() {
        FXCollections.observableArrayList(
                imgDodajBroj,
                imgDodajEdukaciju,
                imgDodajTermin,
                imgDodajSalu
        ).forEach(e -> {
            e.setImage(new Image(new File(konfiguracija.getProperty("slike") + "add.png").toURI().toString()));
        });
        FXCollections.observableArrayList(
                imgIzmjenaBroja,
                imgIzmjenaEdukacije,
                imgIzmjenaTermina
        ).forEach(e -> {
            e.setImage(new Image(new File(konfiguracija.getProperty("slike") + "edit.png").toURI().toString()));
        });
        FXCollections.observableArrayList(
                imgBrisanjeBroja,
                imgBrisanjeEdukacije,
                imgBrisanjeSale,
                imgBrisanjeTermina
        ).forEach(e -> {
            e.setImage(new Image(new File(konfiguracija.getProperty("slike") + "delete.png").toURI().toString()));
        });
        imgNazadNaPocetnu.setImage(new Image(new File(konfiguracija.getProperty("slike") + "left.png").toURI().toString()));
        FXCollections.observableArrayList(
                anchPnKlijenti,
                anchPnCjenovnik,
                anchPnRezervacija
        ).forEach(m -> {
            m.setOnMouseEntered(e -> m.setStyle("-fx-border-color: #00a455"));
            m.setOnMouseExited(e -> m.setStyle("-fx-border: none"));
        });
        regexIzrazi = new ArrayList<>(FXCollections.observableArrayList(
                "[^\\d\\(\\)`~!@#\\$%\\^\\&\\*_\\+{}\\|:\"\\[\\];',\\.\\/\\\\\\?\\*]+",
                "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$",
                "^[\\+(00)]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",
                "-?\\d+(\\.\\d+)?"
        ));
        TextFields.bindAutoCompletion(txtFldPretraga1, Kontroler.klijenti.values().toArray());
        TextFields.bindAutoCompletion(txtFldPretraga2, Kontroler.cjenovnici.values().toArray());
        TextFields.bindAutoCompletion(txtFldEdukacije, Kontroler.edukacije.values().toArray());
        TextFields.bindAutoCompletion(txtFldSala, Kontroler.sale.values().toArray());
        TextFields.bindAutoCompletion(txtFldBirajKlijenta, Kontroler.klijenti.values().toArray());
        TextFields.bindAutoCompletion(txtFldBirajCjenovnik, Kontroler.cjenovnici.values().toArray());
        cmbBoxBrojevi.getSelectionModel().selectedItemProperty().addListener((broj, noviBroj, stariBroj) -> {
            txtFldBrojTelefona.setText(stariBroj);
        });
        cmbBoxEdukacije.getSelectionModel().selectedItemProperty().addListener((edukacija, novaEdukacija, staraEdukacija) -> {
            txtFldEdukacije.setText(staraEdukacija);
        });
    }

    public void onRdBtnKreiranjeKlijentaClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnKreiranjeKlijenta.getId());
    }

    public void onRdBtnIzmjenaKlijentaClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnIzmjenaKlijenta.getId());
    }

    public void onRdBtnBrisanjeKlijentaClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnBrisanjeKlijenta.getId());
    }

    public void onRdBtnKreiranjeCjenovnikaClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnKreiranjeCjenovnika.getId());
    }

    public void onRdBtnIzmjenaCjenovnikaClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnIzmjenaCjenovnika.getId());
    }

    public void onRdBtnBrisanjeCjenovnikaClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnBrisanjeCjenovnika.getId());
    }

    public void onRdBtnKreiranjeRezervacijeClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnKreiranjeRezervacije.getId());
    }

    public void onRdBtnIzmjenaRezervacijeClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnIzmjenaRezervacije.getId());
    }

    public void onRdBtnBrisanjeRezervacijeClicked(MouseEvent mouseEvent) {
        selektovanjePrikaza(rdBtnBrisanjeRezervacije.getId());
    }

    private void selektovanjePrikaza(String id) {
        switch (id) {
            case "rdBtnKreiranjeKlijenta":
                postavljanjePolja(false, true, Byte.valueOf("1"), id);
                break;
            case "rdBtnIzmjenaKlijenta":
                postavljanjePolja(true, true,Byte.valueOf("1"), id);
                break;
            case"rdBtnBrisanjeKlijenta":
                postavljanjePolja(true, false, Byte.valueOf("1"), id);
                break;
            case "rdBtnKreiranjeCjenovnika":
                postavljanjePolja(false, true, Byte.valueOf("2"), id);
                break;
            case "rdBtnIzmjenaCjenovnika":
                postavljanjePolja(true, true, Byte.valueOf("2"), id);
                break;
            case "rdBtnBrisanjeCjenovnika":
                postavljanjePolja(true, false, Byte.valueOf("2"), id);
                break;
            case "rdBtnKreiranjeRezervacije":
                postavljanjePolja(false, true, Byte.valueOf("3"), id);
                break;
            case "rdBtnIzmjenaRezervacije":
                postavljanjePolja(true, true, Byte.valueOf("3"), id);
                break;
            case "rdBtnBrisanjeRezervacije":
                postavljanjePolja(true, false, Byte.valueOf("3"), id);
                break;
        }
    }

    private void postavljanjePolja(boolean pretraga, boolean opcije, byte rezim, String id) {
        switch (rezim) {
            case 1:
                stkPnTab1.setVisible(true);
                lblPretraga1.setVisible(pretraga);
                txtFldPretraga1.setVisible(pretraga);
                lblRezPretrage1.setVisible(false);
                ciscenjePoljaKlijent();
                imgDodajBroj.setVisible(opcije);
                imgIzmjenaBroja.setVisible(opcije);
                imgBrisanjeBroja.setVisible(opcije);
                imgDodajEdukaciju.setVisible(opcije);
                imgIzmjenaEdukacije.setVisible(opcije);
                imgBrisanjeEdukacije.setVisible(opcije);
                txtFldBrojTelefona.setVisible(opcije);
                txtFldEdukacije.setVisible(opcije);
                break;
            case 2:
                stkPnTab2.setVisible(true);
                lblPretraga2.setVisible(pretraga);
                txtFldPretraga2.setVisible(pretraga);
                lblRezPretrage2.setVisible(false);
                ciscenjePoljaCjenovnik();
                break;
            case 3:
                stkPnTab3.setVisible(true);
                imgDodajTermin.setVisible(opcije);
                imgBrisanjeSale.setVisible(opcije);
                imgDodajSalu.setVisible(opcije);
                imgIzmjenaTermina.setVisible(opcije);
                imgBrisanjeTermina.setVisible(opcije);
                ciscenjePoljaRezervacija(false);
                brisanjeRezervacije(opcije);
                break;
        }
    }

    public void onImgNazadNaPocetnuClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(imgNazadNaPocetnu, "pocetnaStrana.fxml");
    }

    public void onAnchPnKlijentiClicked(MouseEvent mouseEvent) {
        tabPnObrasci.getTabs().setAll(tabKlijenti);
        ciscenjePoljaKlijent();
    }

    public void onAnchPnCjenovnikClicked(MouseEvent mouseEvent) {
        tabPnObrasci.getTabs().setAll(tabCjenovnici);
        ciscenjePoljaCjenovnik();
    }

    public void onAnchPnRezervacijaClicked(MouseEvent mouseEvent) {
        tabPnObrasci.getTabs().setAll(tabRezervacije);
        ciscenjePoljaRezervacija(false);
    }

    public void onTxtFldPretraga1Pressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(!klijenti.containsValue(txtFldPretraga1.getText())) {
                lblRezPretrage1.setVisible(true);
            } else {
                lblRezPretrage1.setVisible(false);
                Kontroler.fasada.setFabrika(TipFabrike.KLIJENT);
                long idKlijenta = odredjivjanjeIdentifikatora(txtFldPretraga1.getText(), Kontroler.klijenti);
                Kontroler.fasada.setKlijent(Kontroler.fasada.<Klijent>pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, idKlijenta).get(0));
                postaviPoljaKlijent();
            }
            txtFldPretraga1.setText("");
        }
    }

    public void postaviPoljaKlijent() {
        System.out.println(txtFldIme ==  null);
        txtFldIme.setText(Kontroler.fasada.getKlijent().getIme());
        txtFldPrezime.setText(Kontroler.fasada.getKlijent().getPrezime());
        txtFldFirma.setText(Kontroler.fasada.getKlijent().getNazivFirme());
        txtFldEmail.setText(Kontroler.fasada.getKlijent().getEmailAdresa());
        Kontroler.fasada.getKlijent().getBrojeviTelefona().forEach(t -> cmbBoxBrojevi.getItems().add(t.getBrojTelefona()));
        Arrays.stream(Kontroler.fasada.getKlijent().getEdukacije().split("#")).filter(e -> !e.equals("")).forEach(e -> cmbBoxEdukacije.getItems().add(this.edukacije.get(Long.parseLong(e))));
    }

    public void onBtnPotvrdi1Clicked(MouseEvent mouseEvent) {
        Kontroler.fasada.setFabrika(TipFabrike.KLIJENT);
        if(tgGrpKlijent.getSelectedToggle() == rdBtnBrisanjeKlijenta) {
            prikaziProzorZaPotvrdu(btnPotvrdi1, Rezim.BRISANJE_KLIJENTA);
            ciscenjePoljaKlijent();
            Kontroler.klijenti.remove(Kontroler.fasada.getKlijent().getIdKlijenta());
            TextFields.bindAutoCompletion(txtFldPretraga1, Kontroler.klijenti.values().toArray());
            return;
        }
        List<String> unosi = new ArrayList<>(FXCollections.observableArrayList(
                txtFldIme.getText(),
                txtFldPrezime.getText(),
                txtFldFirma.getText(),
                txtFldEmail.getText()
        ));
        List<Label> poruke = new ArrayList<>(FXCollections.observableArrayList(
                lblIme,
                lblPrezime,
                new Label(""),
                lblEmail
        ));
        List<Boolean> provjeraUnosa = new ArrayList<>(FXCollections.observableArrayList(
                provjeraPolja(unosi.get(0), regexIzrazi.get(0), 0),
                provjeraPolja(unosi.get(1), regexIzrazi.get(0), 1),
                true,
                provjeraPolja(unosi.get(3), regexIzrazi.get(1), 3)
        ));
        if(provjeraUnosa.contains(false)) {
            for (int pozicija = 0; pozicija < provjeraUnosa.size(); pozicija++) {
                if (provjeraUnosa.get(pozicija) || (pozicija == 2 && !provjeraUnosa.get(pozicija))) {
                    poruke.get(pozicija).setVisible(false);
                } else if (!provjeraUnosa.get(pozicija)) {
                    poruke.get(pozicija).setVisible(true);
                }
            }
        } else {
            poruke.forEach(p -> p.setVisible(false));
            String brojevi = cmbBoxBrojevi.getItems().toString();
            String brojeviTelefona = brojevi.substring(1, brojevi.length() - 1);
            StringBuilder edukacija = new StringBuilder();
            cmbBoxEdukacije.getItems().forEach( e -> {
                long idEdukacije = odredjivjanjeIdentifikatora(e, Kontroler.edukacije);
                edukacija.append(idEdukacije + "#");
            });
            List<String> podaci = new ArrayList<>(FXCollections.observableArrayList(
                    "1",        //String.valueOf(Kontroler.fasada.getPrijavljenKorisnik().getIdAdministratora())
                    unosi.get(0).substring(0,1).toUpperCase() + unosi.get(0).substring(1).toLowerCase(),
                    unosi.get(1).substring(0,1).toUpperCase() + unosi.get(1).substring(1).toLowerCase(),
                    unosi.get(2),
                    unosi.get(3),
                    brojeviTelefona,
                    edukacija.toString()
            ));
            if(tgGrpKlijent.getSelectedToggle() == rdBtnKreiranjeKlijenta) {
                Kontroler.fasada.setKlijent(Kontroler.fasada.<Klijent>akcija(TipAkcije.KREIRAJ_NOVI_OBJEKAT, null, podaci, 0).get(0));
                Kontroler.klijenti.put(Kontroler.fasada.getKlijent().getIdKlijenta(), Kontroler.fasada.getKlijent().getIme() + " " + Kontroler.fasada.getKlijent().getPrezime());
            } else if(tgGrpKlijent.getSelectedToggle() == rdBtnIzmjenaKlijenta) {
                Kontroler.fasada.setKlijent(Kontroler.fasada.akcija(TipAkcije.IZMIJENI_OBJEKAT, Kontroler.fasada.getKlijent(), podaci, 0).get(0));
                Kontroler.klijenti.replace(Kontroler.fasada.getKlijent().getIdKlijenta(), Kontroler.fasada.getKlijent().getIme() + " " + Kontroler.fasada.getKlijent().getPrezime());
            }
            ciscenjePoljaKlijent();
            TextFields.bindAutoCompletion(txtFldPretraga1, Kontroler.klijenti.values().toArray());
        }
    }

    private boolean provjeraPolja(String vrijednost, String regexIzraz, int pozicija) {
        Pattern pravilanUnos = Pattern.compile(regexIzraz);
        boolean provjera = pravilanUnos.matcher(vrijednost).matches();
        if(pozicija == 3) {
            if(!provjera && vrijednost.equals("") && cmbBoxBrojevi.getItems().size() == 0) {
                lblEmail.setText("Morate unijeti e-mail!");
            }
            if(!provjera) {
                lblEmail.setText("E-mail ne ispunjava format!");
            }
        }
        return provjera;
    }

    private void ciscenjePoljaKlijent() {
        new ArrayList<TextField>(FXCollections.observableArrayList(
                txtFldPretraga1,
                txtFldIme,
                txtFldPrezime,
                txtFldFirma,
                txtFldEmail,
                txtFldBrojTelefona,
                txtFldEdukacije
        )).forEach(t -> t.setText(""));
        new ArrayList<ComboBox>(FXCollections.observableArrayList(
                cmbBoxBrojevi,
                cmbBoxEdukacije
        )).forEach(c -> c.getItems().clear());
        lblRezPretrage1.setVisible(false);
    }

    public void onImgDodajBrojClicked(MouseEvent mouseEvent) {
        Pattern pravilanUnos = Pattern.compile(regexIzrazi.get(2));
        boolean provjera = pravilanUnos.matcher(txtFldBrojTelefona.getText()).matches();
        if(!provjera) {
            lblBrojTelefona.setVisible(true);
        } else {
            lblBrojTelefona.setVisible(false);
            if(!cmbBoxBrojevi.getItems().contains(txtFldBrojTelefona.getText())) {
                cmbBoxBrojevi.getItems().add(txtFldBrojTelefona.getText());
            }
            txtFldBrojTelefona.setText("");
        }
    }

    public void onImgIzmjenaBrojaClicked(MouseEvent mouseEvent) {
        if(!txtFldBrojTelefona.getText().equals("") && !cmbBoxBrojevi.getItems().contains(txtFldBrojTelefona.getText())) {
            onImgDodajBrojClicked(null);
            cmbBoxBrojevi.getItems().remove(cmbBoxBrojevi.getSelectionModel().getSelectedItem());
        }
        txtFldBrojTelefona.setText("");
        cmbBoxBrojevi.getSelectionModel().clearSelection();
    }

    public void onImgBrisanjeBrojaClicked(MouseEvent mouseEvent) {
        if(!txtFldBrojTelefona.getText().equals("")) {
            cmbBoxBrojevi.getItems().remove(txtFldBrojTelefona.getText());
            txtFldBrojTelefona.setText("");
        }
    }

    public void onImgDodajEdukacijuClicked(MouseEvent mouseEvent) {
        String nazivEdukacije = txtFldEdukacije.getText().substring(0,1).toUpperCase() + txtFldEdukacije.getText().substring(1).toLowerCase();
        if(!cmbBoxEdukacije.getItems().contains(txtFldEdukacije.getText())) {
            cmbBoxEdukacije.getItems().add(nazivEdukacije);
        }
        if(!edukacije.containsValue(txtFldEdukacije.getText())) {
            Kontroler.fasada.setFabrika(TipFabrike.EDUKACIJA);
            List<String> podaci = new ArrayList<>();
            podaci.add(nazivEdukacije);
            Edukacija edukacija = Kontroler.fasada.<Edukacija>akcija(TipAkcije.KREIRAJ_NOVI_OBJEKAT, null, podaci, 0).get(0);
            Kontroler.edukacije.put(edukacija.getIdEdukacije(), edukacija.getNazivEdukacije());
            TextFields.bindAutoCompletion(txtFldEdukacije, Kontroler.edukacije.values().toArray());
        }
        txtFldEdukacije.setText("");
        cmbBoxEdukacije.getSelectionModel().clearSelection();
    }

    public void onImgIzmjenaEdukacijeClicked(MouseEvent mouseEvent) {
        if(!txtFldEdukacije.getText().equals("") && !cmbBoxEdukacije.getItems().contains(txtFldEdukacije.getText())) {
            if(edukacije.values().contains(txtFldEdukacije.getText())) {
                onImgDodajEdukacijuClicked(null);
            } else {
                String nazivEdukacije = txtFldEdukacije.getText().substring(0,1).toUpperCase() + txtFldEdukacije.getText().substring(1).toLowerCase();
                cmbBoxEdukacije.getItems().add(nazivEdukacije);
                long idEdukacije = odredjivjanjeIdentifikatora(cmbBoxEdukacije.getSelectionModel().getSelectedItem(), Kontroler.edukacije);
                Edukacija edukacija = new Edukacija();
                edukacija.setIdEdukacije(idEdukacije);
                List<String> podaci = new ArrayList<>();
                podaci.add(nazivEdukacije);
                Kontroler.fasada.setFabrika(TipFabrike.EDUKACIJA);
                Kontroler.fasada.akcija(TipAkcije.IZMIJENI_OBJEKAT, edukacija, podaci, 0);
                Kontroler.edukacije.replace(idEdukacije, txtFldEdukacije.getText());
                TextFields.bindAutoCompletion(txtFldEdukacije, Kontroler.edukacije.values().toArray());
            }
        }
        cmbBoxEdukacije.getItems().remove(cmbBoxEdukacije.getSelectionModel().getSelectedItem());
        txtFldEdukacije.setText("");
        cmbBoxEdukacije.getSelectionModel().clearSelection();
    }

    public void onImgBrisanjeEdukacijeClicked(MouseEvent mouseEvent) {
        if(!txtFldEdukacije.getText().equals("")) {
            if(edukacije.containsValue(txtFldEdukacije.getText())) {
                Kontroler.fasada.setFabrika(TipFabrike.EDUKACIJA);
                long idEdukacije = odredjivjanjeIdentifikatora(cmbBoxEdukacije.getSelectionModel().getSelectedItem(), Kontroler.edukacije);
                cmbBoxEdukacije.getItems().remove(txtFldEdukacije.getText());
                Edukacija edukacija = Kontroler.fasada.<Edukacija>akcija(TipAkcije.OBRISI_OBJEKAT, null, null, idEdukacije).get(0);
                Kontroler.edukacije.remove(edukacija.getIdEdukacije());
                TextFields.bindAutoCompletion(txtFldEdukacije, Kontroler.edukacije.values().toArray());
            }
            txtFldEdukacije.setText("");
        }
    }

    public void onTxtFldPretraga2Pressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(!cjenovnici.containsValue(txtFldPretraga2.getText())) {
                lblRezPretrage2.setVisible(true);
            } else {
                lblRezPretrage2.setVisible(false);
                Kontroler.fasada.setFabrika(TipFabrike.CJENOVNIK);
                long idCjenovnika = odredjivjanjeIdentifikatora(txtFldPretraga2.getText(), Kontroler.cjenovnici);
                Kontroler.fasada.setCjenovnik(Kontroler.fasada.<Cjenovnik>pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_ID, null, null, idCjenovnika).get(0));
                postaviPoljaCjenovnik();
            }
            txtFldPretraga2.setText("");
        }
    }

    public void postaviPoljaCjenovnik() {
        txtFldCjenovnik.setText(Kontroler.fasada.getCjenovnik().getNaziv());
        txtFldCijenaPoSatu.setText(String.valueOf(Kontroler.fasada.getCjenovnik().getCijenaPoSatu()));
        txtFldCijenaPoDanu.setText(String.valueOf(Kontroler.fasada.getCjenovnik().getCijenaPoDanu()));
    }

    public void onBtnPotvrdi2Clicked(MouseEvent mouseEvent) {
        Kontroler.fasada.setFabrika(TipFabrike.CJENOVNIK);
        if(tgGrpCjenovnik.getSelectedToggle() == rdBtnBrisanjeCjenovnika) {
            prikaziProzorZaPotvrdu(btnPotvrdi2, Rezim.BRISANJE_CJENOVNIKA);
            ciscenjePoljaCjenovnik();
            Kontroler.cjenovnici.remove(Kontroler.fasada.getCjenovnik().getIdCjenovnika());
            TextFields.bindAutoCompletion(txtFldPretraga2, Kontroler.cjenovnici.values().toArray());
            return;
        }
        List<String> unosi = new ArrayList<>(FXCollections.observableArrayList(
                txtFldCjenovnik.getText(),
                txtFldCijenaPoSatu.getText(),
                txtFldCijenaPoDanu.getText()
        ));
        List<Label> poruke = new ArrayList<>(FXCollections.observableArrayList(
                lblCijenaSat,
                lblCijenaDan
        ));
        List<Boolean> provjeraUnosa = new ArrayList<>(FXCollections.observableArrayList(
                true,
                provjeraPolja(unosi.get(1), regexIzrazi.get(3), 1),
                provjeraPolja(unosi.get(2), regexIzrazi.get(3), 2)
        ));
        if(provjeraUnosa.contains(false)) {
            for (int pozicija = 1; pozicija < provjeraUnosa.size(); pozicija++) {
                if (provjeraUnosa.get(pozicija)) {
                    poruke.get(pozicija - 1).setVisible(false);
                } else if (!provjeraUnosa.get(pozicija)) {
                    poruke.get(pozicija - 1).setVisible(true);
                }
            }
        } else {
            poruke.forEach(p -> p.setVisible(false));
            List<String> podaci = new ArrayList<>(FXCollections.observableArrayList(
                    "1",        //String.valueOf(Kontroler.fasada.getPrijavljenKorisnik().getIdAdministratora())
                    unosi.get(0).substring(0,1).toUpperCase() + unosi.get(0).substring(1).toLowerCase(),
                    unosi.get(1),
                    unosi.get(2)
            ));
            if(tgGrpCjenovnik.getSelectedToggle() == rdBtnKreiranjeCjenovnika) {
                Kontroler.fasada.setCjenovnik(Kontroler.fasada.<Cjenovnik>akcija(TipAkcije.KREIRAJ_NOVI_OBJEKAT, null, podaci, 0).get(0));
                Kontroler.cjenovnici.put(Kontroler.fasada.getCjenovnik().getIdCjenovnika(), Kontroler.fasada.getCjenovnik().getNaziv());
            } else if(tgGrpCjenovnik.getSelectedToggle() == rdBtnIzmjenaCjenovnika) {
                Kontroler.cjenovnici.replace(Kontroler.fasada.getCjenovnik().getIdCjenovnika(), podaci.get(1));
                Kontroler.fasada.setCjenovnik(Kontroler.fasada.akcija(TipAkcije.IZMIJENI_OBJEKAT, Kontroler.fasada.getCjenovnik(), podaci, 0).get(0));
            }
            TextFields.bindAutoCompletion(txtFldPretraga2, Kontroler.cjenovnici.values().toArray());
            ciscenjePoljaCjenovnik();
        }
    }

    private void ciscenjePoljaCjenovnik() {
        new ArrayList<>(FXCollections.observableArrayList(
                txtFldPretraga2,
                txtFldCjenovnik,
                txtFldCijenaPoSatu,
                txtFldCijenaPoDanu
        )).forEach(t -> t.setText(""));
        lblRezPretrage2.setVisible(false);
    }

    public void onTxtFldBirajKlijentaPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(klijenti.containsValue(txtFldBirajKlijenta.getText())) {
                lblRezultati1.setVisible(true);
            } else {
                lblRezultati1.setVisible(false);
            }
        }
    }

    public void onTxtFldBirajCjenovnikPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(cjenovnici.containsValue(txtFldBirajCjenovnik.getText())) {
                lblRezultati2.setVisible(true);
            } else {
                lblRezultati2.setVisible(false);
            }
        }
    }

    private void brisanjeRezervacije(Boolean opcija) {
        sckPnTermini.setVisible(opcija);
        if(sckPnTermini.isVisible()) {
           stkPnTab3.setMargin(btnPotvrdi3, new Insets(410, 0, 0, 200));
        } else {
            stkPnTab3.setMargin(btnPotvrdi3, new Insets(175, 0, 0, 200));
        }
    }

    private void ciscenjePoljaRezervacija(boolean trebaTermin) {
        new ArrayList<>(FXCollections.observableArrayList(
                txtFldPocetakSati,
                txtFldPocetakMinute,
                txtFldKrajSati,
                txtFldKrajMinute,
                txtFldSala
        )).forEach(t -> t.setText(""));
        if(!trebaTermin) {
            txtFldBirajKlijenta.setText("");
            txtFldBirajCjenovnik.setText("");
            cmbBoxTermini.getItems().clear();
        }
        rdBtnPojedinacniDani.setSelected(true);
        chckBoxSatnica.setSelected(false);
        (FXCollections.observableArrayList(
                dtPckPocetak,
                dtPckKraj
        )).forEach(d -> {
            d.setDayCellFactory(p -> new DateCell() {
                public void updateItem(LocalDate datum, boolean prazanDatum) {
                    super.updateItem(datum, prazanDatum);
                    setDisable(prazanDatum || datum.compareTo(LocalDate.now()) < 0);
                }
            });
            d.setValue(null);
        });
        dtPckKraj.setDisable(true);
    }

    public void onRdBtnUzastopniDaniClicked(MouseEvent mouseEvent) {
        dtPckKraj.setDisable(false);
    }

    public void onRdBtnPojedinackiDaniClicked(MouseEvent mouseEvent) {
        dtPckKraj.setDisable(true);
    }

    public void postaviPoljaRezervaicija() {
        txtFldBirajKlijenta.setText(Kontroler.fasada.getKlijent().getIme() + " " + Kontroler.fasada.getKlijent().getPrezime());
        txtFldBirajCjenovnik.setText(Kontroler.fasada.getCjenovnik().getNaziv());
        Kontroler.fasada.getRezervacija().getTermini().forEach(t -> cmbBoxTermini.getItems().add(t.getIdSale() + "," + t.getDatumIznajmljivanjaPocetak() + "," +
                t.getVrijemeIznajmljivanjaPocetak() + "," + t.getVrijemeIznajmljivanjaKraj()));
    }

    public void onImgDodajTerminClicked(MouseEvent mouseEvent) {
        if(tgGrpTermin.getSelectedToggle() == rdBtnPojedinacniDani) {
            if(!cmbBoxTermini.getItems().contains(dtPckPocetak.getValue().toString())) {
                cmbBoxTermini.getItems().add(dtPckPocetak.getValue().toString());
            }
        } else if(tgGrpTermin.getSelectedToggle() == rdBtnUzastopniDani) {
            if(dtPckPocetak.getValue() != null && dtPckKraj.getValue() != null) {
                Stream.iterate(dtPckPocetak.getValue(), datum -> datum.plusDays(1))
                        .limit(ChronoUnit.DAYS.between(dtPckPocetak.getValue(), dtPckKraj.getValue().plusDays(1)))
                        .collect(Collectors.toList()).forEach(d -> {
                    if (!cmbBoxTermini.getItems().contains(d.toString())) {
                        cmbBoxTermini.getItems().add(d.toString());
                    }
                });
            }
        }
        dtPckPocetak.setValue(null);
        dtPckKraj.setValue(null);
    }

    public void onImgIzmjenaTerminaClicked(MouseEvent mouseEvent) {
        if(cmbBoxTermini.getSelectionModel().getSelectedItem() !=  null) {
            String[] podaci = cmbBoxTermini.getSelectionModel().getSelectedItem().split(",");
            String[] datum = podaci[1].split("-");
            String[] vrijeme = podaci[2].split(":");
            txtFldSala.setText(sale.get(Long.parseLong(podaci[0])));
            dtPckPocetak.setValue(LocalDate.of(Integer.parseInt(datum[0]), Integer.parseInt(datum[1]), Integer.parseInt(datum[2])));
            dtPckPocetak.setDisable(true);
            imgDodajTermin.setDisable(true);
            txtFldPocetakSati.setText(vrijeme[0]);
            txtFldPocetakMinute.setText(vrijeme[1]);
            vrijeme = podaci[3].split(":");
            txtFldKrajSati.setText(vrijeme[0]);
            txtFldKrajMinute.setText(vrijeme[1]);
        }
    }

    public void onImgBrisanjeTerminaClicked(MouseEvent mouseEvent) {
        if(cmbBoxTermini.getSelectionModel().getSelectedItem() !=  null) {
            cmbBoxTermini.getItems().remove(cmbBoxTermini.getSelectionModel().getSelectedItem());
            cmbBoxTermini.getSelectionModel().clearSelection();
        }
    }

    public void onImgDodajSaluClicked(MouseEvent mouseEvent) {
        if(!sale.containsValue(txtFldSala.getText())) {
            Kontroler.fasada.setFabrika(TipFabrike.SALA);
            List<String> podaci = new ArrayList<>();
            List<String> kolone = new ArrayList<>();
            String nazivSale = txtFldSala.getText().substring(0,1).toUpperCase() + txtFldSala.getText().substring(1).toLowerCase();
            txtFldSala.setText(nazivSale);
            podaci.add(nazivSale);
            kolone.add("nazivSale");
            Sala sala = Kontroler.fasada.<Sala>pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_NAZIV, podaci, kolone, 0).get(0);
            sala  = Kontroler.fasada.<Sala>akcija(TipAkcije.IZMIJENI_OBJEKAT, sala, podaci, 0).get(0);
            Kontroler.sale.put(sala.getIdSale(), sala.getNazivSale());
            TextFields.bindAutoCompletion(txtFldSala, Kontroler.sale.values().toArray());
            upravljanjeSalama(nazivSale,true);
        }
    }

    public void onImgBrisanjeSaleClicked(MouseEvent mouseEvent) {
        if(!txtFldSala.getText().equals("")) {
            if(sale.containsValue(txtFldSala.getText())) {
                Kontroler.fasada.setFabrika(TipFabrike.SALA);
                long idSale = odredjivjanjeIdentifikatora(txtFldSala.getText(), Kontroler.sale);
                Kontroler.fasada.<Sala>akcija(TipAkcije.OBRISI_OBJEKAT, null, null, idSale);
                Kontroler.sale.remove(idSale);
                TextFields.bindAutoCompletion(txtFldSala, Kontroler.sale.values().toArray());
                upravljanjeSalama(txtFldSala.getText(),false);
            }
            txtFldSala.setText("");
        }
    }

    private void upravljanjeSalama(String nazivSale, boolean kreiranje) {
        URL url = null;
        try {
            url = new File(konfiguracija.getProperty("fxmlFajlovi") + "mjesecniIDnevniPrikaz.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            MjesecniIDnevniPrikaz kontroler = loader.getController();
            kontroler.upravljanjeSalama(nazivSale, kreiranje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBtnPotvrdiTerminClicked(MouseEvent mouseEvent) {
        Kontroler.fasada.setFabrika(TipFabrike.REZERVACIJA);
        ArrayList<String> sati = new ArrayList<>(FXCollections.observableArrayList(
                txtFldPocetakSati.getText(),
                txtFldKrajSati.getText()
        ));
        ArrayList<String> minute = new ArrayList<>(FXCollections.observableArrayList(
                txtFldPocetakMinute.getText(),
                txtFldKrajMinute.getText()
        ));
        minute.stream().filter(m -> m.equals("")).forEach(m -> m = "0");
        if(provjeraVremenaTermina(sati, minute)) {
            StringBuilder vrijemeOdrzavanja = new StringBuilder();
            sati.forEach(s -> {
                String min = minute.get(sati.indexOf(s));
                vrijemeOdrzavanja.append((s.length() < 2 ? "0" + s : s) + ":" + (min.length() < 2 ? "0" + min : min) + ":00,");
            });
            lblPoljaTermina.setVisible(false);
            Long idSale = odredjivjanjeIdentifikatora(txtFldSala.getText(), sale);
            if(chckBoxSatnica.isSelected()) {
                List<String> datumi = new ArrayList<>();
                cmbBoxTermini.getItems().forEach(d -> {
                    String[] podaciODatumu = d.split(",");
                    if(podaciODatumu.length == 4) {
                        datumi.add(podaciODatumu[1]);
                    } else {
                        datumi.add(d);
                    }
                });
                cmbBoxTermini.getItems().clear();
                datumi.forEach(t -> cmbBoxTermini.getItems().add(idSale + "," + t +
                        "," + vrijemeOdrzavanja.toString().substring(0, vrijemeOdrzavanja.toString().length() - 1)));
                System.out.println(cmbBoxTermini.getItems());
            } else if(!chckBoxSatnica.isSelected() && cmbBoxTermini.getSelectionModel().getSelectedItem() != null) {
                String datum = cmbBoxTermini.getSelectionModel().getSelectedItem();
                if(datum.split(",").length == 4) {
                    datum = datum.split(",")[1];
                }
                cmbBoxTermini.getItems().remove(cmbBoxTermini.getSelectionModel().getSelectedItem());
                cmbBoxTermini.getItems().add(idSale + "," + datum + "," + vrijemeOdrzavanja.toString().substring(0, vrijemeOdrzavanja.toString().length() - 1));
            }
            cmbBoxTermini.getSelectionModel().clearSelection();
        }
        ciscenjePoljaRezervacija(true);
        dtPckPocetak.setDisable(false);
        imgDodajTermin.setDisable(false);
    }

    public void onBtnPotvrdi3Clicked(MouseEvent mouseEvent) {
        if(tgGrpRezervacija.getSelectedToggle() == rdBtnBrisanjeRezervacije) {
            MjesecniIDnevniPrikaz.brisanjeRezervacije();
            prikaziProzorZaPotvrdu(btnPotvrdi3, Rezim.BRISANJE_REZERVACIJE);
            ciscenjePoljaRezervacija(false);
            return;
        }
        if(cmbBoxTermini.getItems().stream().filter(t -> (t.split(",").length != 4)).collect(Collectors.toList()).size() > 0) {
            lblTermini.setVisible(true);
            return;
        }
        lblTermini.setVisible(false);
        StringBuilder termini =  new StringBuilder();
        cmbBoxTermini.getItems().forEach(t -> termini.append(t + "#"));
        ArrayList<String> podaci = new ArrayList<>(FXCollections.observableArrayList(
                String.valueOf(odredjivjanjeIdentifikatora(txtFldBirajKlijenta.getText(), klijenti)),
                String.valueOf(odredjivjanjeIdentifikatora(txtFldBirajCjenovnik.getText(), cjenovnici)),
                termini.substring(0, termini.toString().length() - 1),
                "1" //String.valueOf(Kontroler.fasada.getPrijavljenKorisnik().getIdAdministratora())
        ));
        Kontroler.fasada.setFabrika(TipFabrike.REZERVACIJA);
        if(rdBtnKreiranjeRezervacije.isSelected()) {
            Kontroler.fasada.setRezervacija(Kontroler.fasada.<Rezervacija>akcija(TipAkcije.KREIRAJ_NOVI_OBJEKAT, null, podaci, 0).get(0));
            MjesecniIDnevniPrikaz.kreiranjeRezervacije();
        } else if(rdBtnIzmjenaRezervacije.isSelected()) {
            MjesecniIDnevniPrikaz.brisanjeRezervacije();
            Kontroler.fasada.setRezervacija(Kontroler.fasada.<Rezervacija>akcija(TipAkcije.IZMIJENI_OBJEKAT, Kontroler.fasada.getRezervacija(), podaci, 0).get(0));
            MjesecniIDnevniPrikaz.kreiranjeRezervacije();
        }
        ciscenjePoljaRezervacija(false);
    }

    private boolean provjeraVremenaTermina(ArrayList<String> sati, ArrayList<String> minute) {
        Pattern pravilanUnos = Pattern.compile("\\d{1,2}");
        boolean provjera;
        if(!chckBoxSatnica.isSelected() && cmbBoxTermini.getSelectionModel().getSelectedItem() == null) {
            lblPoljaTermina.setText("Morate oznaciti jedan termin!");
            lblPoljaTermina.setVisible(true);
            return false;
        }
        lblPoljaTermina.setVisible(false);
        lblPoljaTermina.setText("Nisu popunjena sva polja ispravno!");
        if(sati.contains("") || (sati.stream().filter(s -> !pravilanUnos.matcher(s).matches() ||
                Integer.parseInt(s) < 6 || Integer.parseInt(s) > 23).collect(Collectors.toList()).size() > 0)) {
            provjera = false;
        } else {
            provjera = true;
        }
        if(!provjera) {
            lblPoljaTermina.setVisible(!provjera);
            return provjera;
        }
        if(minute.stream().filter(s -> !pravilanUnos.matcher(s).matches() ||
                Integer.parseInt(s) < 0 || Integer.parseInt(s) > 59).collect(Collectors.toList()).size() > 0) {
            provjera = false;
        } else {
            provjera = true;
        }
        if(!provjera) {
            lblPoljaTermina.setVisible(!provjera);
            return provjera;
        }
        if((Integer.parseInt(sati.get(0)) > Integer.parseInt(sati.get(1))) ||
                ((Integer.parseInt(sati.get(0)) == Integer.parseInt(sati.get(1))) && ((Integer.parseInt(minute.get(0)) >= Integer.parseInt(minute.get(1)))))) {
            provjera = false;
        } else {
            provjera = true;
        }
        lblPoljaTermina.setVisible(!provjera);
        return provjera;
    }
}
