package wbd_vol2;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DodajKlientaController implements Initializable {

    private Connection conn;

    @FXML
    private TextField dodajKlientaImieTextField;

    @FXML
    private TextField dodajKlientaNazwiskoTextField;

    @FXML
    private TextField dodajKlientaPESELTextField;

    @FXML
    private TextField dodajKlientaNrTelefonuTextField;

    @FXML
    private Button commitdodajKlientaButton;

    @FXML
    private Button wyczyscDodajKlientaButton;

    @FXML
    void commitdodajKlientaButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        Klient klient = new Klient();
        klient = getNewKlientData();
        klient.insertKlient(conn, klient);
    }

    @FXML
    void wyczyscDodajKlientaButtonOnAction(ActionEvent event) {
        dodajKlientaNazwiskoTextField.clear();
        dodajKlientaImieTextField.clear();
        dodajKlientaPESELTextField.clear();
        dodajKlientaNrTelefonuTextField.clear();
    }

    private Klient getNewKlientData() {
        Klient klient = new Klient();

        String Imie;
        String Nazwisko;
        Long PESEL;
        Integer Nr_telefonu;

        Imie = dodajKlientaImieTextField.getText().trim();
        if (Imie.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad imienia");
            alert.setContentText("Szczegoly: " + "pole nie moze byc puste");
            alert.show();
            return null;
        }

        Nazwisko = dodajKlientaNazwiskoTextField.getText().trim();
        if (Nazwisko.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad nazwiska");
            alert.setContentText("Szczegoly: " + "pole nie moze byc puste");
            alert.show();
            return null;
        }

        try {
            if (dodajKlientaPESELTextField.getText().trim().length() == 0) {
                PESEL = null;
            } else if (dodajKlientaPESELTextField.getText().trim().length() != 0 && dodajKlientaPESELTextField.getText().trim().length() != 11) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad PESELU");
                alert.setContentText("Szczegoly: " + "PESEL musi miec 11 cyfr");
                alert.show();
                return null;
            } else {
                PESEL = Long.parseLong(dodajKlientaPESELTextField.getText().trim());
            }
        } catch (Exception exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad PESELU");
            alert.setContentText("Szczegoly: " + "dopuszczalne sa tylko cyfry");
            alert.show();
            return null;
        }

        try {
            Nr_telefonu = Integer.parseInt(dodajKlientaNrTelefonuTextField.getText().trim());
            if (Nr_telefonu.toString().length() != 9) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad nr telefonu");
                alert.setContentText("Szczegoly: " + "numer musi miec 9 cyfr");
                alert.show();
                return null;
            }
        } catch (Exception exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad nr telefonu");
            alert.setContentText("Szczegoly: " + "numer musi sie skladac z samych cyfr");
            alert.show();
            return null;
        }

        klient.setImie(Imie);
        klient.setNazwisko(Nazwisko);
        klient.setPesel(PESEL);
        klient.setNr_telefonu(Nr_telefonu.toString());
        return klient;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
