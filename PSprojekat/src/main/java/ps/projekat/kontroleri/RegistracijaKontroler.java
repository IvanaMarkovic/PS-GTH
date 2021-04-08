package ps.projekat.kontroleri;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RegistracijaKontroler {
    @FXML
    private TextField txtFldIme, txtFldPrezime, txtFldKorIme;

    @FXML
    private PasswordField passFldLozinka, passFldPonovljenaLozinka;

    @FXML
    private Button btnRegistracija;

    @FXML
     Label lblUpozorenja, lblIme, lblPrezime, lblKorIme, lblLozinka, lblPonLozinka;

    @FXML
    void registracija(ActionEvent event) {
        ArrayList<String> unosi = new ArrayList<>();
        ArrayList<String> regex = new ArrayList<>();
        ArrayList<String> poruke = new ArrayList<>();

        ugasiObavjestenja();

        unosi.add(txtFldIme.getText());
        unosi.add(txtFldPrezime.getText());
        unosi.add(txtFldKorIme.getText());
        unosi.add(passFldLozinka.getText());
        unosi.add(passFldPonovljenaLozinka.getText());
        regex.add("[a-zA-Z]{0,10}");
        regex.add("[a-zA-Z]{0,10}");
        regex.add("[A-Za-z0-9]{0,10}");

        //Potrebno je provjeriti da li korisnicko ime postoji u bazi
        //provjeriImeUBazi(); vraca true ako postoji

        if (!(unosi.stream().filter(s -> s.length()>0).collect(Collectors.toList()).size()==5)){
            lblUpozorenja.setVisible(true);
        }
        /* else if (provjeriImeUBazi()) {
            lblIme.SetText("Ime vec postoji u bazi.");
            lblIme.setVisible(true);
        }*/
        else if(unosi.get(0).length()<3){
            lblIme.setText("Ime mora da sadrži najmanje 3 karaktera.");
            lblIme.setVisible(true);
        }
        else if (!unosi.get(0).matches(regex.get(0))){
            lblIme.setText("Ime može da sadrži samo karaktere.");
            lblIme.setVisible(true);
        }
        else if(unosi.get(1).length()<3){
            lblPrezime.setText("Prezime mora da sadrži najmanje 3 karaktera");
            lblPrezime.setVisible(true);
        }
        else if(!unosi.get(1).matches(regex.get(1))) {
            lblPrezime.setText("Prezime može da sadrži samo karaktere.");
            lblPrezime.setVisible(true);
        }
        else if(unosi.get(2).length() <3){
            lblKorIme.setText("Korisničko ime mora da sadrži najmanje 3 karaktera.");
            lblKorIme.setVisible(true);
        }
        else if(!unosi.get(2).matches(regex.get(2))){
            lblKorIme.setText("Korisničko ime ne smije da sadrži simbole.");
            lblKorIme.setVisible(true);
        }
        else if (unosi.get(3).length()<8){
            lblUpozorenja.setText("Lozinka mora da sadrzi vise od 8 karaktera.");
            lblUpozorenja.setVisible(true);
        } else if (!unosi.get(3).equals(unosi.get(4))){
            lblLozinka.setText("Lozinke se ne poklapaju.");
            lblLozinka.setVisible(true);
            lblPonLozinka.setText("Lozinke se ne poklapaju.");
            lblPonLozinka.setVisible(true);
        } else{
            lblUpozorenja.setTextFill(Color.GREEN);
            lblUpozorenja.setText("Uspjesna registracija.");
            lblUpozorenja.setVisible(true);
        }


    }

    private void ugasiObavjestenja(){
        lblUpozorenja.setVisible(false);
        lblIme.setVisible(false);
        lblPrezime.setVisible(false);
        lblKorIme.setVisible(false);
        lblLozinka.setVisible(false);
        lblPonLozinka.setVisible(false);
    }

    @FXML
    void vratiNaPocetnu(MouseEvent event) {
        try{
            URL url = new File("src/main/java/ps/projekat/prikazi/Prijava.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle("Prijava");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(e -> {
                e.consume();
                stage.close();
                System.exit(0);
            });
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
