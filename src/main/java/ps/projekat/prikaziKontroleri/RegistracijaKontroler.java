package ps.projekat.prikaziKontroleri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.NalogAdministratora;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegistracijaKontroler extends Kontroler implements Initializable {

    @FXML
    TextField txtFldIme, txtFldPrezime, txtFldKorIme, txtFldLozinka1, txtFldLozinka2;

    @FXML
    PasswordField passFldLozinka1, passFldLozinka2;

    @FXML
    Button btnRegistracija;

    @FXML
    Label lblUpozorenja, lblIme, lblPrezime, lblKorIme, lblLozinka1, lblLozinka2;

    @FXML
    ImageView imgNazadNaPrijavu, imgPrikaziLozinku1, imgPrikaziLozinku2, imgSakrijLozinku1, imgSakrijLozinku2;

    private ArrayList<String> regexIzrazi;
    ArrayList<Label> poruke;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passFldLozinka1.textProperty().bindBidirectional(txtFldLozinka1.textProperty());
        passFldLozinka2.textProperty().bindBidirectional(txtFldLozinka2.textProperty());
        String prikaziLozinku = new File(konfiguracija.getProperty("slike") + "seePassword.jpg").toURI().toString();
        String sakrijLozinku = new File(konfiguracija.getProperty("slike") + "hidePassword.jpg").toURI().toString();
        imgPrikaziLozinku1.setImage(new Image(prikaziLozinku));
        imgPrikaziLozinku2.setImage(new Image(prikaziLozinku));
        imgSakrijLozinku1.setImage(new Image(sakrijLozinku));
        imgSakrijLozinku2.setImage(new Image(sakrijLozinku));
        imgNazadNaPrijavu.setImage(new Image(new File(konfiguracija.getProperty("slike") + "left.png").toURI().toString()));
        regexIzrazi = new ArrayList<>(FXCollections.observableArrayList(
                "[^\\d\\(\\)`~!@#\\$%\\^\\&\\*_\\+{}\\|:\"\\[\\];',\\.\\/\\\\\\?\\*]+",
                "[^\\s\\(\\)`~!@#\\$%\\^\\&\\*_\\+{}\\|:\"\\-\\[\\];',\\.\\/\\\\\\?\\*]+",
                ".{8,}"
        ));
        poruke = new ArrayList<>(FXCollections.observableArrayList(
                lblIme,
                lblPrezime,
                lblKorIme,
                lblLozinka1,
                lblLozinka2
        ));
    }

    public void postaviVrijednosti() {
        btnRegistracija.setText("Potvrdi");
        NalogAdministratora nalog = fasada.getPrijavljenKorisnik();
        txtFldIme.setText(nalog.getIme());
        txtFldPrezime.setText(nalog.getPrezime());
        txtFldKorIme.setText(nalog.getKorisnickoIme());
    }

    public void onBtnRegistracijaClicked(MouseEvent mouseEvent) {
        ArrayList<String> unosi = new ArrayList<>(FXCollections.observableArrayList(
                txtFldIme.getText(),
                txtFldPrezime.getText(),
                txtFldKorIme.getText(),
                passFldLozinka1.getText(),
                passFldLozinka2.getText()
        ));
        boolean provjeraPolja = btnRegistracija.getText().equals("Potvrdi") && !txtFldIme.getText().equals("") && !txtFldPrezime.getText().equals("") && !txtFldKorIme.getText().equals("") &&
                passFldLozinka1.getText().equals("") && passFldLozinka2.getText().equals("");
        if (unosi.contains("") && !provjeraPolja){
            lblUpozorenja.setVisible(true);
        } else {
            lblUpozorenja.setVisible(false);
            short pozicija = 0;
            ArrayList<Boolean> provjeraUnosa = new ArrayList<>(FXCollections.observableArrayList(
                    provjera(unosi.get(0),regexIzrazi.get(0),0),
                    provjera(unosi.get(1),regexIzrazi.get(0),1),
                    provjera(unosi.get(2),regexIzrazi.get(1),2)
            ));
            boolean provjeraLozinke = provjeraLozinke(unosi.get(3), unosi.get(4), regexIzrazi.get(2), unosi.size());
            if(provjeraUnosa.contains(false) || !provjeraLozinke) {
                for (pozicija = 0; pozicija < provjeraUnosa.size(); pozicija++) {
                    if(provjeraUnosa.get(pozicija) || (pozicija == 2 && !provjeraUnosa.get(pozicija))) {
                        poruke.get(pozicija).setVisible(false);
                    }
                    else if(!provjeraUnosa.get(pozicija)) {
                        poruke.get(pozicija).setVisible(true);
                    }
                }
            } else {
                poruke.forEach(p -> p.setVisible(false));
                unosi.remove(unosi.size()-1);
                NalogAdministratora nalog = fasada.getPrijavljenKorisnik();
                Boolean dugmePotvrdi = btnRegistracija.getText().equals("Potvrdi");
                ArrayList<String> podaciOKorisniku = new ArrayList<>(FXCollections.observableArrayList(
                        unosi.get(0).substring(0,1).toUpperCase() + unosi.get(0).substring(1).toLowerCase(),
                        unosi.get(1).substring(0,1).toUpperCase() + unosi.get(1).substring(1).toLowerCase(),
                        unosi.get(2),
                        (dugmePotvrdi && passFldLozinka1.getText().equals("") && passFldLozinka2.getText().equals("")) ? nalog.getLozinka() : napraviHashLozinke(passFldLozinka2.getText()),
                        String.valueOf(!provjeraUnosa.get(2))
                ));
                fasada.setPrijavljenKorisnik(fasada.akcija(TipAkcije.IZMIJENI_OBJEKAT, nalog, podaciOKorisniku, 0).get(0));
                if(dugmePotvrdi) {
                    btnRegistracija.setText("Registracija");
                    Kontroler.prikaziNarednuScenu(btnRegistracija, "nalog.fxml");
                } else {
                    Kontroler.prikaziNarednuScenu(btnRegistracija, "pocetnaStrana.fxml");
                }
            }
        }
    }

    private boolean provjera(String vrijednost, String regexIzraz, int pozicija) {
        Pattern pravilanUnos = Pattern.compile(regexIzraz);
        boolean provjera = pravilanUnos.matcher(vrijednost).matches();
        if(pozicija == 2) {
            Long provjeraNaloga = 0L;
            if(provjera && !btnRegistracija.getText().equals("Potvrdi")) {
                provjeraNaloga = fasada.provjeraKorisnickogImena(vrijednost);
                if (provjeraNaloga != 0) {
                    lblKorIme.setText("Uneseno korisničko ime već postoji!");
                }
            }
            provjera = provjera && (provjeraNaloga == 0);
        }
        return provjera;
    }

    private boolean provjeraLozinke(String lozinka1, String lozinka2, String regexIzraz, int pozicija) {
        Pattern pravilanUnos = Pattern.compile(regexIzraz);
        if(btnRegistracija.getText().equals("Potvrdi") && lozinka1.equals("") && lozinka2.equals("")) {
            return true;
        }
        if(!pravilanUnos.matcher(lozinka1).matches()) {
            poruke.get(pozicija - 2).setVisible(true);
            return false;
        } else {
            poruke.get(pozicija - 2).setVisible(false);
            if(lozinka1.equals(lozinka2)) {
                poruke.get(pozicija - 1).setVisible(false);
                return true;
            } else {
                poruke.get(pozicija - 1).setVisible(true);
            }
        }
        return false;
    }

    private String napraviHashLozinke(String lozinka) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] enkododovanaLozinka = digest.digest(lozinka.getBytes());

            String hash_lozinka = Base64.getEncoder().encodeToString(enkododovanaLozinka);
            return hash_lozinka;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void onImgNazadNaPrijavuClicked(MouseEvent event) {
        if(btnRegistracija.getText().equals("Potvrdi")) {
            Kontroler.prikaziNarednuScenu(imgNazadNaPrijavu, "nalog.fxml");
        } else {
            Kontroler.prikaziNarednuScenu(imgNazadNaPrijavu, "prijava.fxml");
        }
    }

    public void onImgPrikaziLozinku1Clicked(MouseEvent mouseEvent) {
        postaviPolja(Byte.parseByte("1"), true, false, true, false);
    }

    public void onImgSakrijLozinku1Clicked(MouseEvent mouseEvent) {
        postaviPolja(Byte.parseByte("1"),false, true, false, true);
    }

    public void onImgPrikaziLozinku2Clicked(MouseEvent mouseEvent) {
        postaviPolja(Byte.parseByte("2"),true, false, true, false);
    }

    public void onImgSakrijLozinku2Clicked(MouseEvent mouseEvent) {
        postaviPolja(Byte.parseByte("2"),false, true, false, true);
    }

    private void postaviPolja(byte rezim, boolean txtFldLozinka, boolean passFldLozinka, boolean imgSakrijLozinku, boolean imgPrikaziLozinku) {
        switch (rezim) {
            case 1:
                txtFldLozinka1.setVisible(txtFldLozinka);
                passFldLozinka1.setVisible(passFldLozinka);
                imgSakrijLozinku1.setVisible(imgSakrijLozinku);
                imgPrikaziLozinku1.setVisible(imgPrikaziLozinku);
                break;
            case 2:
                txtFldLozinka2.setVisible(txtFldLozinka);
                passFldLozinka2.setVisible(passFldLozinka);
                imgSakrijLozinku2.setVisible(imgSakrijLozinku);
                imgPrikaziLozinku2.setVisible(imgPrikaziLozinku);
                break;
        }
    }


}
