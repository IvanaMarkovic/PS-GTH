package ps.projekat.prikaziKontroleri;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ps.projekat.podaci.Fasada;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class IzlazSaSistemaKontroler {

    @FXML
    AnchorPane anchPnNastavi, anchPnOdustani;

    @FXML
    Label lblPoruka;

    private Stage prozor;
    private byte rezim;
    private Fasada fasada;

    public void setProzor(Stage prozor) {
        this.prozor = prozor;
    }

    public void setRezim(byte rezim) {
        this.rezim = rezim;
    }

    public void onAnchPnOdustaniClicked(MouseEvent mouseEvent) {
        ((Stage)anchPnOdustani.getScene().getWindow()).close();
    }

    public void onAnchPnNastaviClicked(MouseEvent mouseEvent) {
        fasada = Fasada.getInstance();
        anchPnNastavi.setVisible(false);
        anchPnOdustani.setVisible(false);
        if(rezim == 1) {
            fasada.akcija(TipAkcije.OBRISI_OBJEKAT,null,null, fasada.getPrijavljenKorisnik().getIdAdministratora());
            lblPoruka.setText("Uspješno ste obrisali nalog!");
        } else {
            lblPoruka.setText("Uspješno ste se odjavili sa sistema!");
        }
        Stage prozor = NalogKontroler.getProzor();
        Stage trenutniProzor = ((Stage)anchPnNastavi.getScene().getWindow());
        trenutniProzor.setOnCloseRequest(e -> {
            Properties konfiguracija = (new CitanjePropertiesFajla("putanjeFajlova.properties")).getKonfiguracija();
            try {
                URL url = new File(konfiguracija.getProperty("fxmlFajlovi") + "prijava.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Scene scena = new Scene(root, prozor.getScene().getWidth(), prozor.getScene().getHeight());
                scena.getStylesheets().add(new File(konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
                prozor.setScene(scena);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}
