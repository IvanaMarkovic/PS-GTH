package ps.projekat.kontroleri;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PrijavaKontroler {
    @FXML
    TextField txtFldKorIme, txtFldPrikazLozinke;

    @FXML
    PasswordField passFldLozinka;

    @FXML
    Button btnPrijava;

    @FXML
    Hyperlink hyplRegistracija;

    @FXML
    void prijava(ActionEvent event) {
        String korisnickoIme = txtFldKorIme.getText();
        String lozinka = passFldLozinka.getText();
        String hashLozinka = hashIt(lozinka); // Lozinka je hasirana SHA-256 algoritmom i base64 kodovana
        // verifikacijaUBazi() - vraca true ako su uneseni podaci u redu

        System.out.println(hashLozinka);
    }

    @FXML
    void registrujSe(ActionEvent event) {
        Parent rootReg = null;
        try {
            URL url = new File("src/main/java/ps/projekat/prikazi/Registracija.fxml").toURI().toURL();
            Parent rootReq = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle("Registracija");
            stage.setScene(new Scene(rootReq));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void prikaziLozinku(MouseEvent event) {
        if (txtFldPrikazLozinke.isVisible()) {
            txtFldPrikazLozinke.setVisible(false);
        } else {
            String lozinka = passFldLozinka.getText();
            if (!lozinka.isEmpty()) {
                txtFldPrikazLozinke.setVisible(true);
                txtFldPrikazLozinke.setText(lozinka);
            }
        }
    }

    private String hashIt(String lozinka) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(lozinka.getBytes());

            String hash_lozinka = Base64.getEncoder().encodeToString(encodedhash);
            return hash_lozinka;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

}