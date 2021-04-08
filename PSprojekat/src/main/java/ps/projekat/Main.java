package ps.projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = new File("src/main/java/ps/projekat/prikazi/Prijava.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Prijava");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setOnCloseRequest(e -> {
                e.consume();
                primaryStage.close();
                System.exit(0);
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Main funkcija za testiranje");
        launch(args);
        /*try {
            throw new Exception("greska1");
        } catch (Exception e) {
            LoggerUtil.log(Level.WARNING, logger.getName(), e, LoggerUtil.getNazivPaketa() + File.separator + "error1.log");
        }

        try {
            throw new Exception("greska2");
        } catch (Exception e) {
            LoggerUtil.log(Level.WARNING, logger.getName(), e, LoggerUtil.getNazivPaketa() + File.separator + "error2.log");
        }*/
    }


}
