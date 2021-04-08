package ps.projekat.prikaziKontroleri;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import ps.projekat.podaci.dto.Cjenovnik;
import ps.projekat.podaci.dto.Klijent;
import ps.projekat.podaci.dto.Rezervacija;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.podaci.model.TipFabrike;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class IzlazKontroler extends Kontroler {

    @FXML
    AnchorPane anchPnNastavi, anchPnOdustani;

    @FXML
    Label lblPoruka, lblNastavi, lblOdustani;

    Stage prozor = new Stage();
    private Rezim rezim;
    private Node element;

    public void setRezim(Rezim rezim) {
        this.rezim = rezim;
    }

    public void setElement(Node element) {
        this.element = element;
    }

    public void onAnchPnOdustaniClicked(MouseEvent mouseEvent) {
        ((Stage)anchPnOdustani.getScene().getWindow()).close();
    }

    public void onAnchPnNastaviClicked(MouseEvent mouseEvent) {
        anchPnNastavi.setVisible(false);
        anchPnOdustani.setVisible(false);
        switch (rezim) {
            case BRISANJE_NALOGA:
                lblPoruka.setText("Uspješno ste obrisali nalog!");
                fasada.akcija(TipAkcije.OBRISI_OBJEKAT, null, null, fasada.getPrijavljenKorisnik().getIdAdministratora());
                Kontroler.prikaziNarednuScenu(element, "prijava.fxml");
                break;
            case BRISANJE_KLIJENTA:
                lblPoruka.setText("Uspješno ste obrisali klijenta!");
                fasada.setFabrika(TipFabrike.KLIJENT);
                fasada.<Klijent>akcija(TipAkcije.OBRISI_OBJEKAT, null, null, fasada.getKlijent().getIdKlijenta());
                break;
            case BRISANJE_CJENOVNIKA:
                lblPoruka.setText("Uspješno ste obrisali cjenovnik!");
                fasada.setFabrika(TipFabrike.CJENOVNIK);
                fasada.<Cjenovnik>akcija(TipAkcije.OBRISI_OBJEKAT, null, null, fasada.getCjenovnik().getIdCjenovnika());
                break;
            case BRISANJE_REZERVACIJE:
                lblPoruka.setText("Uspješno ste obrisali rezervaciju!");
                fasada.setFabrika(TipFabrike.REZERVACIJA);
                fasada.<Rezervacija>akcija(TipAkcije.OBRISI_OBJEKAT, null, null, fasada.getRezervacija().getIdRezervacije());
                break;
            case ODJAVA_SA_SISTEMA:
                lblPoruka.setText("Uspješno ste se odjavili sa sistema!");
                Kontroler.prikaziNarednuScenu(element, "prijava.fxml");
                break;
        }
    }
}
