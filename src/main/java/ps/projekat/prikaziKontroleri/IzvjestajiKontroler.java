package ps.projekat.prikaziKontroleri;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.*;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class IzvjestajiKontroler extends Kontroler implements Initializable {

    @FXML
    ToggleGroup tgGrpPretazivanje;

    @FXML
    RadioButton rdBtnKlijenti, rdBtnCjenovnici, rdBtnRezervacije;

    @FXML
    TextField txtFldKlijent, txtFldNazivSale, txtFldCjenovnik;

    @FXML
    Label lblRezultati1, lblRezultati2, lblRezultati3, lblDatumi, lblOdabranRed;

    @FXML
    Button btnIzmijeni, btnObrisi, btnPotvrdi;

    @FXML
    DatePicker dtPckPocetak, dtPckKraj;

    @FXML
    ImageView imgNazadNaPocetnu;

    @FXML
    BorderPane brdPnIzvjestaji;

    @FXML
    TableView<?> tblTabela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgNazadNaPocetnu.setImage(new Image(new File(konfiguracija.getProperty("slike") + "left.png").toURI().toString()));
        TextFields.bindAutoCompletion(txtFldKlijent, Kontroler.klijenti.values().toArray());
        TextFields.bindAutoCompletion(txtFldCjenovnik, Kontroler.cjenovnici.values().toArray());
        TextFields.bindAutoCompletion(txtFldNazivSale, Kontroler.sale.values().toArray());
        postavljanjePolja(true);
    }

    public void onImgNazadNaPocetnuClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(imgNazadNaPocetnu, "pocetnaStrana.fxml");
    }

    public void onRdBtnKlijentiClicked(MouseEvent mouseEvent) {
        postavljanjePolja(true);
    }

    public void onRdBtnCjenovniciClicked(MouseEvent mouseEvent) {
        postavljanjePolja(true);
    }

    public void onRdBtnRezervacijeClicked(MouseEvent mouseEvent) {
        postavljanjePolja(false);
    }

    private void postavljanjePolja(boolean opcije) {
        txtFldKlijent.setText("");
        txtFldKlijent.setDisable(opcije);
        txtFldNazivSale.setText("");
        txtFldNazivSale.setDisable(opcije);
        txtFldCjenovnik.setText("");
        txtFldCjenovnik.setDisable(opcije);
        dtPckPocetak.setValue(null);
        dtPckPocetak.setDisable(opcije);
        dtPckKraj.setValue(null);
        dtPckKraj.setDisable(opcije);
    }

    public void onTxtFldKlijentPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(!Kontroler.klijenti.containsValue(txtFldKlijent.getText())) {
                lblRezultati1.setVisible(true);
            } else {
                lblRezultati1.setVisible(false);
            }
        }
    }

    public void onTxtNazivSalePressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(!Kontroler.sale.containsValue(txtFldNazivSale.getText())) {
                lblRezultati2.setVisible(true);
            } else {
                lblRezultati2.setVisible(false);
            }
        }
    }

    public void ontxtFldCjenovnikPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            if(Kontroler.cjenovnici.containsValue(txtFldNazivSale.getText())) {
                lblRezultati3.setVisible(true);
            } else {
                lblRezultati3.setVisible(false);
            }
        }
    }

    public void onBtnPotvrdiClicked(MouseEvent mouseEvent) {
        if(rdBtnKlijenti.isSelected()) {
            kreiranjeTabeleKlijent();
        } else if(rdBtnCjenovnici.isSelected()) {
            kreiranjeTabeleCjenovnik();
        } else if(rdBtnRezervacije.isSelected()) {
            List<Termin> listaRezervacija = selektujTermine();
            if(listaRezervacija != null) {
                kreiranjeTabeleRezervacija(listaRezervacija);
            }
        }
    }

    private List<Termin> selektujTermine() {
        List<Termin> listaRezervacija = null;
        fasada.setFabrika(TipFabrike.TERMIN);
        if((dtPckPocetak.getValue() != null && dtPckKraj.getValue() == null) || (dtPckPocetak.getValue() == null && dtPckKraj.getValue() != null)) {
            lblDatumi.setVisible(true);
        } else if(dtPckPocetak.getValue() != null && dtPckKraj.getValue() != null){
            lblDatumi.setVisible(false);
            List<String> datumi = new ArrayList<>(FXCollections.observableArrayList(
                    dtPckPocetak.getValue().toString(),
                    dtPckKraj.getValue().toString()
            ));
            listaRezervacija = fasada.selektujPoDatumu(datumi);
            listaRezervacija = provjeraFiltera(listaRezervacija);
        } else {
            lblDatumi.setVisible(false);
            listaRezervacija = fasada.<Termin>pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
            listaRezervacija = provjeraFiltera(listaRezervacija);
        }
        txtFldKlijent.setText("");
        txtFldCjenovnik.setText("");
        txtFldNazivSale.setText("");
        dtPckPocetak.setValue(null);
        dtPckKraj.setValue(null);
        return listaRezervacija;
    }

    private List<Termin> provjeraFiltera(List<Termin> listaTermina) {
        long idKlijenta = Kontroler.klijenti.containsValue(txtFldKlijent.getText()) ? odredjivjanjeIdentifikatora(txtFldKlijent.getText(), Kontroler.klijenti) : 0L;
        long idCjenovnika = Kontroler.cjenovnici.containsValue(txtFldCjenovnik.getText()) ? odredjivjanjeIdentifikatora(txtFldCjenovnik.getText(), Kontroler.cjenovnici) : 0L;
        long idSale = Kontroler.sale.containsValue(txtFldNazivSale.getText()) ? odredjivjanjeIdentifikatora(txtFldNazivSale.getText(), Kontroler.sale) : 0L;;
        if(idKlijenta != 0L) {
            listaTermina = listaTermina.stream().filter(t -> t.getRezervacija().getKlijent().getIdKlijenta() == idKlijenta).collect(Collectors.toList());
        }
        if(idCjenovnika != 0L) {
            listaTermina =  listaTermina.stream().filter(t -> t.getRezervacija().getCjenovnik().getIdCjenovnika() == idCjenovnika).collect(Collectors.toList());
        }
        if(idSale != 0L) {
            listaTermina = listaTermina.stream().filter(t -> t.getIdSale() == idSale).collect(Collectors.toList());
        }
        return listaTermina;
    }

    private void kreiranjeTabeleKlijent() {
        fasada.setFabrika(TipFabrike.KLIJENT);
        TableView<Klijent> tblTabela = new TableView<>();
        List<Klijent> listaKlijenata = fasada.<Klijent>pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        TableColumn<Klijent, String> tColIme = new TableColumn<>("Ime");
        tColIme.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColIme.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Klijent, String> tColPrezime = new TableColumn<>("Prezime");
        tColPrezime.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        TableColumn<Klijent, String> tColFirma = new TableColumn<>("Naziv firme");
        tColFirma.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColFirma.setCellValueFactory(new PropertyValueFactory<>("nazivFirme"));

        TableColumn<Klijent, String> tColEmail = new TableColumn<>("E-mail adresa");
        tColEmail.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColEmail.setCellValueFactory(new PropertyValueFactory<>("emailAdresa"));

        tblTabela.getColumns().addAll(tColIme, tColPrezime, tColFirma, tColEmail);
        BorderPane.setMargin(tblTabela, new Insets(0,20,20,20));
        listaKlijenata.forEach(k -> tblTabela.getItems().add(k));
        this.tblTabela = tblTabela;
        brdPnIzvjestaji.setCenter(tblTabela);
    }

    private void kreiranjeTabeleCjenovnik() {
        fasada.setFabrika(TipFabrike.CJENOVNIK);
        TableView<Cjenovnik> tblTabela = new TableView<>();
        List<Cjenovnik> listaCjenovnika = fasada.<Cjenovnik>pretrazivanjeObjekata(TipAkcije.DOHVATI_SVE_OBJEKTE, null, null, 0);
        TableColumn<Cjenovnik, String> tColNaziv = new TableColumn<>("Naziv");
        tColNaziv.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        TableColumn<Cjenovnik, Float> tColCijenaSat = new TableColumn<>("Cijena za sat");
        tColCijenaSat.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColCijenaSat.setCellValueFactory(new PropertyValueFactory<>("cijenaPoSatu"));
        TableColumn<Cjenovnik, Float> tColCijenaDan = new TableColumn<>("Cijena za dan");
        tColCijenaDan.prefWidthProperty().bind(tblTabela.widthProperty().divide(4));
        tColCijenaDan.setCellValueFactory(new PropertyValueFactory<>("cijenaPoDanu"));
        tblTabela.getColumns().addAll(tColNaziv, tColCijenaSat, tColCijenaDan);
        BorderPane.setMargin(tblTabela, new Insets(0,20,20,20));
        listaCjenovnika.forEach(c -> tblTabela.getItems().add(c));
        this.tblTabela = tblTabela;
        brdPnIzvjestaji.setCenter(tblTabela);
    }

    private void kreiranjeTabeleRezervacija(List<Termin> listaRezervacija) {
        TableView<Termin> tblTabela = new TableView<>();
        TableColumn<Termin, String> tColIdentifikator = new TableColumn<>("Id rezervacije");
        tColIdentifikator.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColIdentifikator.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRezervacija().getIdRezervacije())));
        TableColumn<Termin, String> tColNaziv = new TableColumn<>("Ime klijenta");
        tColNaziv.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColNaziv.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRezervacija().getKlijent().getIme()));
        TableColumn<Termin, String> tColPrezime = new TableColumn<>("Prezime klijenta");
        tColPrezime.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColPrezime.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRezervacija().getKlijent().getPrezime()));
        TableColumn<Termin, String> tColNazivCjenovnika = new TableColumn<>("Naziv Cjenovnika");
        tColNazivCjenovnika.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColNazivCjenovnika.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRezervacija().getCjenovnik().getNaziv()));
        TableColumn<Termin, String> tColSala = new TableColumn<>("Naziv sale");
        tColSala.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColSala.setCellValueFactory(p -> new SimpleStringProperty(Kontroler.sale.get(p.getValue().getIdSale())));
        TableColumn<Termin, String> tColDatum = new TableColumn<>("Datum odrzavanja");
        tColDatum.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColDatum.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDatumIznajmljivanjaPocetak().toString()));
        TableColumn<Termin, String> tColVrijemePocetak = new TableColumn<>("Vrijeme - početak");
        tColVrijemePocetak.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColVrijemePocetak.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getVrijemeIznajmljivanjaPocetak().toString()));
        TableColumn<Termin, String> tColVrijemeKraj = new TableColumn<>("Vrijeme - kraj");
        tColVrijemeKraj.prefWidthProperty().bind(tblTabela.widthProperty().divide(8));
        tColVrijemeKraj.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getVrijemeIznajmljivanjaKraj().toString()));
        tblTabela.getColumns().addAll(tColIdentifikator, tColNaziv, tColPrezime, tColNazivCjenovnika, tColSala, tColDatum, tColVrijemePocetak, tColVrijemeKraj);
        BorderPane.setMargin(tblTabela, new Insets(0,20,20,20));
        listaRezervacija.forEach(c -> tblTabela.getItems().add(c));
        this.tblTabela = tblTabela;
        brdPnIzvjestaji.setCenter(tblTabela);
    }

    public void onBtnIzmijeniClicked(MouseEvent mouseEvent) {
        if(tblTabela.getSelectionModel().getSelectedItem() == null) {
            lblOdabranRed.setVisible(true);
        } else {
            lblOdabranRed.setVisible(false);
            URL url = null;
            try {
                url = new File(konfiguracija.getProperty("fxmlFajlovi") + "obrasci.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                ObrasciKontroler kontroler = loader.getController();
                Object objekat = tblTabela.getSelectionModel().getSelectedItem();
                if (rdBtnKlijenti.isSelected() && objekat instanceof Klijent) {
                    fasada.setKlijent((Klijent)objekat);
                    kontroler.onRdBtnIzmjenaKlijentaClicked(null);
                    kontroler.rdBtnIzmjenaKlijenta.setSelected(true);
                    kontroler.postaviPoljaKlijent();
                } else if (rdBtnCjenovnici.isSelected() && objekat instanceof Cjenovnik) {
                    fasada.setCjenovnik((Cjenovnik) tblTabela.getSelectionModel().getSelectedItem());
                    kontroler.onAnchPnCjenovnikClicked(null);
                    kontroler.onRdBtnIzmjenaCjenovnikaClicked(null);
                    kontroler.rdBtnIzmjenaCjenovnika.setSelected(true);
                    kontroler.postaviPoljaCjenovnik();
                } else if (rdBtnRezervacije.isSelected() && objekat instanceof Termin) {
                    fasada.setRezervacija((((Termin)objekat).getRezervacija()));
                    fasada.setKlijent(fasada.getRezervacija().getKlijent());
                    fasada.setCjenovnik(fasada.getRezervacija().getCjenovnik());
                    kontroler.onAnchPnRezervacijaClicked(null);
                    kontroler.onRdBtnIzmjenaRezervacijeClicked(null);
                    kontroler.rdBtnIzmjenaRezervacije.setSelected(true);
                    kontroler.postaviPoljaRezervaicija();
                }
                Scene scena = new Scene(root, btnIzmijeni.getScene().getWidth(), btnIzmijeni.getScene().getHeight());
                scena.getStylesheets().add(new File(konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
                ((Stage) btnIzmijeni.getScene().getWindow()).setScene(scena);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onBtnObrisiClicked(MouseEvent mouseEvent) {
        if(tblTabela.getSelectionModel().getSelectedItem() == null) {
            lblOdabranRed.setVisible(true);
        } else {
            lblOdabranRed.setVisible(false);
            URL url = null;
            try {
                url = new File(konfiguracija.getProperty("fxmlFajlovi") + "obrasci.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                ObrasciKontroler kontroler = loader.getController();
                Object objekat = tblTabela.getSelectionModel().getSelectedItem();
                if (rdBtnKlijenti.isSelected() && objekat instanceof Klijent) {
                    fasada.setKlijent((Klijent)objekat);
                    kontroler.onRdBtnBrisanjeKlijentaClicked(null);
                    kontroler.rdBtnBrisanjeKlijenta.setSelected(true);
                    kontroler.postaviPoljaKlijent();
                } else if (rdBtnCjenovnici.isSelected() && objekat instanceof Cjenovnik) {
                    fasada.setCjenovnik((Cjenovnik)objekat);
                    kontroler.onAnchPnCjenovnikClicked(null);
                    kontroler.onRdBtnBrisanjeCjenovnikaClicked(null);
                    kontroler.rdBtnBrisanjeCjenovnika.setSelected(true);
                    kontroler.postaviPoljaCjenovnik();
                } else if (rdBtnRezervacije.isSelected() && objekat instanceof Termin) {
                    fasada.setRezervacija((((Termin)objekat).getRezervacija()));
                    fasada.setKlijent(fasada.getRezervacija().getKlijent());
                    fasada.setCjenovnik(fasada.getRezervacija().getCjenovnik());
                    kontroler.onAnchPnRezervacijaClicked(null);
                    kontroler.onRdBtnBrisanjeRezervacijeClicked(null);
                    kontroler.rdBtnBrisanjeRezervacije.setSelected(true);
                    kontroler.postaviPoljaRezervaicija();
                }
                Scene scena = new Scene(root, btnObrisi.getScene().getWidth(), btnObrisi.getScene().getHeight());
                scena.getStylesheets().add(new File(konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
                ((Stage) btnObrisi.getScene().getWindow()).setScene(scena);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
