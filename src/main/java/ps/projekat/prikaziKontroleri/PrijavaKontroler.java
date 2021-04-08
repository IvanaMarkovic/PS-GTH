package ps.projekat.prikaziKontroleri;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.NalogAdministratora;
import ps.projekat.podaci.model.ApstraktnaFabrika;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class PrijavaKontroler extends Kontroler implements Initializable {

    @FXML
    ImageView imgPrikaziLozinku, imgSakrijLozinku;

    @FXML
    PasswordField passFldLozinka;

    @FXML
    TextField txtFldLozinka, txtFldKorIme;

    @FXML
    Button btnPrijava;

    @FXML
    Label lblUnosKorImena, lblUnosLozinke;

    @FXML
    Hyperlink hypLnkRegistracija;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passFldLozinka.textProperty().bindBidirectional(txtFldLozinka.textProperty());
        imgPrikaziLozinku.setImage(new Image(new File(konfiguracija.getProperty("slike") + "seePassword.jpg").toURI().toString()));
        imgSakrijLozinku.setImage(new Image(new File(konfiguracija.getProperty("slike") + "hidePassword.jpg").toURI().toString()));
    }

    public void onImgPrikaziLozinkuClicked() {
        postaviPolja(true, false, true, false);
    }

    public void onImgSakrijLozinkuClicked() {
        postaviPolja(false, true, false, true);

    }

    private void postaviPolja(boolean txtFldLozinka, boolean passFldLozinka, boolean imgSakrijLozinku, boolean imgPrikaziLozinku) {
        this.txtFldLozinka.setVisible(txtFldLozinka);
        this.passFldLozinka.setVisible(passFldLozinka);
        this.imgSakrijLozinku.setVisible(imgSakrijLozinku);
        this.imgPrikaziLozinku.setVisible(imgPrikaziLozinku);
    }

    public void onBtnPrijavaClicked() {
        ArrayList<String> podaci = new ArrayList<>(FXCollections.observableArrayList(
                txtFldKorIme.getText()
        ));
        ArrayList<String> kolone = new ArrayList<>(FXCollections.observableArrayList(
                "korisnickoIme"
        ));
        List<NalogAdministratora> nalozi = fasada.pretrazivanjeObjekata(TipAkcije.PRONADJI_OBJEKAT_NAZIV, podaci, kolone, 0);
        if(nalozi.size() == 1) {
            lblUnosKorImena.setVisible(false);
            fasada.setPrijavljenKorisnik(nalozi.get(0));
            String hashLozinka = napraviHashLozinke(passFldLozinka.getText());
            if(podaci.get(0).equals(nalozi.get(0).getKorisnickoIme()) && hashLozinka.equals(nalozi.get(0).getLozinka())
                    && !nalozi.get(0).isOznaciObrisan()) {
                lblUnosLozinke.setVisible(false);
                Kontroler.prikaziNarednuScenu(btnPrijava, "pocetnaStrana.fxml");
            } else if(nalozi.get(0).isOznaciObrisan()) {
                lblUnosKorImena.setVisible(true);
            } else {
                lblUnosLozinke.setVisible(true);
            }
        } else {
            lblUnosKorImena.setVisible(true);
        }
    }

    public void onHypLnkRegistracijaClicked() throws IOException {
        Kontroler.prikaziNarednuScenu(hypLnkRegistracija,"registracija.fxml");
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
}
