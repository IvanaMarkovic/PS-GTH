package ps.projekat;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ps.projekat.podaci.dao.DAO;
import ps.projekat.podaci.dao.KlijentDAO;
import ps.projekat.podaci.dao.NalogAdministratoraDAO;
import ps.projekat.podaci.dao.TerminDAO;
import ps.projekat.podaci.dto.*;
import ps.projekat.podaci.model.ApstraktnaFabrika;
import ps.projekat.podaci.model.KlijentFabrika;
import ps.projekat.podaci.model.TipAkcije;
import ps.projekat.prikaziKontroleri.PrikazEntry;
import ps.projekat.util.CitanjePropertiesFajla;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Properties konfiguracija = (new CitanjePropertiesFajla("putanjeFajlova.properties")).getKonfiguracija();
        URL url = new File(konfiguracija.get("fxmlFajlovi") + "pocetnaStrana.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(new File(konfiguracija.getProperty("stylesheets") + "prijava.css").toURI().toString());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(700);
        stage.setTitle("Global Thinkers Helper");
        stage.show();
    }

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws ParseException {
        launch(args);
    }
}