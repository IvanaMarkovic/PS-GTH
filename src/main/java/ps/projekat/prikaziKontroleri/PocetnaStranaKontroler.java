package ps.projekat.prikaziKontroleri;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class PocetnaStranaKontroler extends Kontroler implements Initializable {

    @FXML
    AnchorPane anchPnObrasci, anchPnRaspored, anchPnNapomene, anchPnIzvjestaji;

    @FXML
    ImageView imgNalog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgNalog.setImage(new Image(new File(konfiguracija.getProperty("slike") + "user.png").toURI().toString()));
        ArrayList<AnchorPane> meni = new ArrayList<>(FXCollections.observableArrayList(
                anchPnObrasci,
                anchPnRaspored,
                anchPnIzvjestaji
        ));
        meni.forEach(m -> m.setOnMouseEntered(e -> m.setStyle("-fx-border-color: #00a455")));
        meni.forEach(m -> m.setOnMouseExited(e -> m.setStyle("-fx-border: none")));
    }

    
    public void onImgNalogClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(imgNalog, "nalog.fxml");
    }

    public void onAnchPnObrasciClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(anchPnObrasci, "obrasci.fxml");
    }

    public void onAnchPnIzvjestajiClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(anchPnIzvjestaji, "izvjestaji.fxml");
    }

    public void onAnchPnRasporedClicked(MouseEvent mouseEvent) {
        if(MjesecniIDnevniPrikaz.getScena() != null) {
            ((Stage)anchPnRaspored.getScene().getWindow()).setScene(MjesecniIDnevniPrikaz.getScena());
        } else {
            Kontroler.prikaziNarednuScenu(anchPnRaspored, "mjesecniIDnevniPrikaz.fxml");
        }
    }

    public void onAnchPnNapomeneClicked(MouseEvent mouseEvent) {
        Kontroler.prikaziNarednuScenu(anchPnNapomene, "napomene.fxml");
    }

}
