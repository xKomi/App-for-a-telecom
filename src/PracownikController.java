package wbd_vol2;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PracownikController implements Initializable {

    private Connection conn;
    private ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();

    @FXML
    private TableView<Pracownik> tablePracownik;

    @FXML
    private TableColumn<Pracownik, Integer> tableCollumnPracownikNrPracownika;

    @FXML
    private TableColumn<Pracownik, String> tableCollumnPracownikImie;

    @FXML
    private TableColumn<Pracownik, String> tableCollumnPracownikNazwisko;

    @FXML
    private TableColumn<Pracownik, String> tableCollumnPracownikDataZatrudnienia;

    @FXML
    private TableColumn<Pracownik, String> tableCollumnPracownikRodzajZatrudnienia;

    @FXML
    private TableColumn<Pracownik, String> tableCollumnPracownikNrTelefonu;

    @FXML
    private TextField wyszukajPracownikTextField;

    @FXML
    private Button wyszukajPracownikButton;

    @FXML
    private Button dodajPracownikButton;

    @FXML
    void dodajPracownikButtonOnAction(ActionEvent event) throws Exception {
        DodajPracownika dodajPracownika = new DodajPracownika();
        Stage dodajPracownikaStage = new Stage();
        dodajPracownika.start(dodajPracownikaStage);
    }

    @FXML
    void wyszukajPracownikButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        listaPracownikow = new Pracownik().getRestrictedList(conn, wyszukajPracownikTextField.getText().trim());
        setTableViewPracownik(listaPracownikow);
    }

    @FXML
    private TextField usunPracownikTextField;

    @FXML
    void usunPracownikButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        if (usunPracownikTextField.getText().trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad usuwania danych");
            alert.setContentText("Szczegoly: " + "Podaj ID pracownika do usuniecia");
            alert.show();
        } else {
            Pracownik.usunPracownik(conn, Integer.parseInt(usunPracownikTextField.getText().trim()));
            listaPracownikow = new Pracownik().getAll(conn);
            setTableViewPracownik(listaPracownikow);
        }
    }

    @FXML
    private Button zaktualizujPracownikButton;

    @FXML
    void zaktualizujPracownikButtonOnAction(ActionEvent event) throws Exception {
        ZaktualizujPracownika zaktualizujPracownika = new ZaktualizujPracownika();
        Stage zaktualizujPracownikaStage = new Stage();
        zaktualizujPracownika.start(zaktualizujPracownikaStage);
    }

    private void setTableViewPracownik(ObservableList<Pracownik> listaPracownikow) {
        tableCollumnPracownikNrPracownika.setCellValueFactory(new PropertyValueFactory<>("Nr_pracownika"));
        tableCollumnPracownikImie.setCellValueFactory(new PropertyValueFactory<>("Imie"));
        tableCollumnPracownikNazwisko.setCellValueFactory(new PropertyValueFactory<>("Nazwisko"));
        tableCollumnPracownikDataZatrudnienia.setCellValueFactory(new PropertyValueFactory<>("Data_zatrudnienia"));
        tableCollumnPracownikRodzajZatrudnienia.setCellValueFactory(new PropertyValueFactory<>("Rodzaj_zatrudnienia"));
        tableCollumnPracownikNrTelefonu.setCellValueFactory(new PropertyValueFactory<>("Nr_telefonu"));

        tablePracownik.setItems(listaPracownikow);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = DBConnection.getConnection();
        listaPracownikow = new Pracownik().getAll(conn);
        setTableViewPracownik(listaPracownikow);
    }

}
