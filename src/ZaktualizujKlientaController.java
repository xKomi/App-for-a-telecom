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

public class ZaktualizujKlientaController implements Initializable {

    private Connection conn;
    Klient klient = new Klient();

    @FXML
    private TextField zauktualizujKlientIdTextField;

    @FXML
    private TextField zaktualizujKlientImieTextField;

    @FXML
    private TextField zaktualizujKlientNazwiskoTextField;

    @FXML
    private TextField zaktualizujKlientPESELTextField;

    @FXML
    private TextField zaktualizujKlientNrTelefonuTextField;

    @FXML
    private Button wyczyscZaktualizujKlientButton;

    @FXML
    void wyczyscZaktualizujKlientButtonOnAction(ActionEvent event) {
        zauktualizujKlientIdTextField.clear();
        zaktualizujKlientImieTextField.clear();
        zaktualizujKlientNazwiskoTextField.clear();
        zaktualizujKlientPESELTextField.clear();
        zaktualizujKlientNrTelefonuTextField.clear();
    }

    @FXML
    private Button zaktualizujKlientButton;

    @FXML
    void zaktualizujKlientButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        Integer Nr_klienta = Integer.parseInt(zauktualizujKlientIdTextField.getText().trim());
        String Imie = zaktualizujKlientImieTextField.getText().trim();
        String Nazwisko = zaktualizujKlientNazwiskoTextField.getText().trim();
        String PESEL = zaktualizujKlientPESELTextField.getText().trim();
        String Nr_telefonu = zaktualizujKlientNrTelefonuTextField.getText().trim();

        klient = ZaktualizujKlienta.getKlientToUpdate(conn, Nr_klienta);
        if (klient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad aktualizowania danych");
            alert.setContentText("Szczegoly: " + "Nie ma klienta o takim ID");
            alert.show();
        } else {
            ZaktualizujKlienta.updateKlient(conn, klient, Imie, Nazwisko, PESEL, Nr_telefonu);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
