package ps.projekat.prikaziKontroleri;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.dto.NalogAdministratora;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class NalogKontroler extends Kontroler implements Initializable {

    @FXML
    ImageView imgNazadNaPocetnu;

    @FXML
    AnchorPane anchPnIzmjena, anchPnBrisanje, anchPnOdjava;

    @FXML
    Label lblIme, lblPrezime, lblKorisnickoIme;

    @FXML
    StackPane stkPnNalog;

    private static Stage prozor = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgNazadNaPocetnu.setImage(new Image(new File(konfiguracija.getProperty("slike") + "left.png").toURI().toString()));
        new ArrayList<>(FXCollections.observableArrayList(
                anchPnIzmjena,
                anchPnBrisanje,
                anchPnOdjava
        )).forEach(m -> {
            m.setOnMouseEntered(e -> m.setStyle("-fx-border-color: #00a455"));
            m.setOnMouseExited(e -> m.setStyle("-fx-border: none"));
        });
        postaviVrijednostiLabela();
    }

    public static Stage getProzor() {
        return prozor;
    }

    public void postaviVrijednostiLabela() {
        NalogAdministratora admin = fasada.getPrijavljenKorisnik();
        lblIme.setText(admin.getIme());
        lblPrezime.setText(admin.getPrezime());
        lblKorisnickoIme.setText(admin.getKorisnickoIme());
    }
    
    public void onImgNazadNaPocetnuClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(imgNazadNaPocetnu, "pocetnaStrana.fxml");
    }

    public void onAnchPnIzmjenaClicked(MouseEvent mouseEvent) {
        URL url = null;
        try {
            url = new File(Kontroler.konfiguracija.getProperty("fxmlFajlovi") + "registracija.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            RegistracijaKontroler kontroler = loader.getController();
            kontroler.postaviVrijednosti();
            Scene scena = new Scene(root, anchPnIzmjena.getScene().getWidth(), anchPnIzmjena.getScene().getHeight());
            scena.getStylesheets().add(new File(Kontroler.konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
            ((Stage)anchPnIzmjena.getScene().getWindow()).setScene(scena);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAnchPnOdjavaClicked(MouseEvent mouseEvent) {
        prikaziProzorZaPotvrdu(anchPnOdjava, Rezim.ODJAVA_SA_SISTEMA);
    }

    public void onAnchPnBrisanjeClicked(MouseEvent mouseEvent) {
        prikaziProzorZaPotvrdu(anchPnBrisanje, Rezim.BRISANJE_NALOGA);
    }
}
