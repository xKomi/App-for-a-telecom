package wbd_vol2;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ZaktualizujPracownikaController implements Initializable {

    private Connection conn;
    Pracownik pracownik = new Pracownik();
    ObservableList<String> rodzajZatrudnienia = FXCollections.observableArrayList("UMOWA_O_PRACE", "UMOWA_O_DZIELO", "UMOWA_ZLECENIE");
    @FXML
    private TextField zaktualizujPracownikIdPracownikaTextField;

    @FXML
    private TextField zaktualizujPracownikImieTextField;

    @FXML
    private TextField zaktualizujPracownikNazwiskoTextField;

    @FXML
    private TextField zaktualizujPracownikNrTelefonuTextField;

    @FXML
    private Button zaktualizujPracownikButton;

    @FXML
    private DatePicker zaktualizujPracownikDataZatrudnieniaDatePicker;

    @FXML
    private ChoiceBox<String> zaktualizujPracownikRodzajZatrudnieniaChoiceBox;

    @FXML
    private Button wyczyscAktualizujPracownikaButton;

    @FXML
    void wyczyscAktualizujPracownikaButtonOnAction(ActionEvent event) {
        zaktualizujPracownikIdPracownikaTextField.clear();
        zaktualizujPracownikImieTextField.clear();
        zaktualizujPracownikNazwiskoTextField.clear();
        zaktualizujPracownikNrTelefonuTextField.clear();
        zaktualizujPracownikDataZatrudnieniaDatePicker.setValue(null);
        zaktualizujPracownikRodzajZatrudnieniaChoiceBox.setValue(null);
    }

    @FXML
    void zaktualizujPracownikButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        Integer Nr_klienta = Integer.parseInt(zaktualizujPracownikIdPracownikaTextField.getText().trim());
        String Imie = zaktualizujPracownikImieTextField.getText().trim();
        String Nazwisko = zaktualizujPracownikNazwiskoTextField.getText().trim();
        LocalDate Data_zatrudnienia = zaktualizujPracownikDataZatrudnieniaDatePicker.getValue();
        String Rodzaj_zatrudnienia = zaktualizujPracownikRodzajZatrudnieniaChoiceBox.getValue();
        String Nr_telefonu = zaktualizujPracownikNrTelefonuTextField.getText().trim();

        pracownik = ZaktualizujPracownika.getPracownikToUpdate(conn, Nr_klienta);
        if (pracownik == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad aktualizowania danych");
            alert.setContentText("Szczegoly: " + "Nie ma pracownika o takim ID");
            alert.show();
        } else {
            ZaktualizujPracownika.updatePracownik(conn, pracownik, Imie, Nazwisko, Data_zatrudnienia, Rodzaj_zatrudnienia, Nr_telefonu);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zaktualizujPracownikRodzajZatrudnieniaChoiceBox.setItems(rodzajZatrudnienia);
    }

}
