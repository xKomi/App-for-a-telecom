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

public class DodajPracownikaController implements Initializable {

    private Connection conn;

    ObservableList<String> rodzajZatrudnienia = FXCollections.observableArrayList("UMOWA_O_PRACE", "UMOWA_O_DZIELO", "UMOWA_ZLECENIE");
    ObservableList<String> plec = FXCollections.observableArrayList("K", "M");
    @FXML
    private TextField dodajPracownikaImieTextField;

    @FXML
    private TextField dodajPracownikaNazwiskoTextField;

    @FXML
    private DatePicker dodajPracownikaDataZatrudnieniaDataPicker;

    @FXML
    private DatePicker dodajPracownikaDataUrodzeniaDataPicker;

    @FXML
    private TextField dodajPracownikaNrTelefonuTextField;

    @FXML
    private TextField dodajPracownikaNrKontaTextField;

    @FXML
    private TextField dodajPracownikaPeselTextField;

    @FXML
    private Button commitdodajPracownikaButton;

    @FXML
    private ChoiceBox<String> dodajPracownikaChoiceBox;

    @FXML
    private ChoiceBox<String> dodajPracownikaPlecChoiceBox;

    @FXML
    private Button wyczyscDodajPracownikaButton;

    @FXML
    void wyczyscDodajPracownikaButtonOnAction(ActionEvent event) {
        dodajPracownikaImieTextField.clear();
        dodajPracownikaNazwiskoTextField.clear();
        dodajPracownikaDataZatrudnieniaDataPicker.setValue(null);
        dodajPracownikaDataUrodzeniaDataPicker.setValue(null);
        dodajPracownikaNrTelefonuTextField.clear();
        dodajPracownikaNrKontaTextField.clear();
        dodajPracownikaPeselTextField.clear();
        dodajPracownikaChoiceBox.setValue(null);
        dodajPracownikaPlecChoiceBox.setValue(null);
    }

    @FXML
    void commitdodajPracownikaButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        Pracownik pracownik = new Pracownik();
        pracownik = getNewPracownikData();
        pracownik.insertPracownik(conn, pracownik);
    }

    private Pracownik getNewPracownikData() {
        Pracownik pracownik = new Pracownik();

        String Imie;
        String Nazwisko;
        String Plec;
        LocalDate Data_zatrudnienia;
        String Rodzaj_zatrudnienia;
        LocalDate Data_urodzenia;
        String Nr_telefonu;
        Long Nr_konta;
        Long PESEL;

        Imie = dodajPracownikaImieTextField.getText().trim();
        if (Imie.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad imienia");
            alert.setContentText("Szczegoly: " + "pole nie moze byc puste");
            alert.show();
            return null;
        }

        Nazwisko = dodajPracownikaNazwiskoTextField.getText().trim();
        if (Nazwisko.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad nazwiska");
            alert.setContentText("Szczegoly: " + "pole nie moze byc puste");
            alert.show();
            return null;
        }

        Plec = dodajPracownikaPlecChoiceBox.getValue();

        Data_zatrudnienia = dodajPracownikaDataZatrudnieniaDataPicker.getValue();

        Rodzaj_zatrudnienia = dodajPracownikaChoiceBox.getValue();

        Data_urodzenia = dodajPracownikaDataUrodzeniaDataPicker.getValue();

        try {
            if (dodajPracownikaPeselTextField.getText().trim().length() == 0) {
                PESEL = Long.parseLong("0");
            } else if (dodajPracownikaPeselTextField.getText().trim().length() != 0 && dodajPracownikaPeselTextField.getText().trim().length() != 11) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad PESELU");
                alert.setContentText("Szczegoly: " + "PESEL musi miec 11 cyfr");
                alert.show();
                return null;
            } else {
                PESEL = Long.parseLong(dodajPracownikaPeselTextField.getText().trim());
            }
        } catch (Exception exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad PESELU");
            alert.setContentText("Szczegoly: " + "dopuszczalne sa tylko cyfry");
            alert.show();
            return null;
        }

        Nr_telefonu = dodajPracownikaNrTelefonuTextField.getText().trim();
        if (Nr_telefonu.length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad nr telefonu");
            alert.setContentText("Szczegoly: " + "numer musi miec 9 cyfr");
            alert.show();
            return null;
        }

        try {
            Nr_konta = Long.parseLong(dodajPracownikaNrKontaTextField.getText().trim());
            if (dodajPracownikaNrKontaTextField.getText().trim().length() != 16) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad nr konta");
                alert.setContentText("Szczegoly: " + "numer konta musi miec 16 cyfr");
                alert.show();
                return null;
            }
        } catch (Exception exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad nr konta");
            alert.setContentText("Szczegoly: " + "dopuszczalne sa tylko cyfry");
            alert.show();
            return null;
        }

        pracownik.setImie(Imie);
        pracownik.setNazwisko(Nazwisko);
        pracownik.setPlec(Plec);
        pracownik.setData_urodzenia(Data_urodzenia);
        pracownik.setData_zatrudnienia(Data_zatrudnienia);
        pracownik.setRodzaj_zatrudnienia(Rodzaj_zatrudnienia);
        pracownik.setNr_konta(Nr_konta);
        pracownik.setPESEL(PESEL);
        pracownik.setNr_telefonu(Nr_telefonu);
        return pracownik;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dodajPracownikaChoiceBox.setItems(rodzajZatrudnienia);
        dodajPracownikaPlecChoiceBox.setItems(plec);
    }

}
