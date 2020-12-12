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

public class FXMLKlientController implements Initializable {

    private Connection conn;
    private ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();

    @FXML
    private TableView<Klient> tableKlient;

    @FXML
    private TableColumn<Klient, Integer> tableCollumnKlientNr_klienta;

    @FXML
    private TableColumn<Klient, String> tableCollumnKlientImie;

    @FXML
    private TableColumn<Klient, String> tableCollumnKlientNazwisko;

    @FXML
    private TableColumn<Klient, Long> tableCollumnKlientPesel;

    @FXML
    private TableColumn<Klient, String> tableCollumnKlientNr_telefonu;

    @FXML
    private TextField wyszukajKlientTextField;

    @FXML
    private Button wyszukajKlientButton;

    @FXML
    void wyszukajKlientButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        listaKlientow = new Klient().getRestrictedList(conn, wyszukajKlientTextField.getText().trim());
        setTableViewKlient(listaKlientow);
    }

    @FXML
    private Button dodajKlientButton;

    @FXML
    void dodajKlientButtonOnAction(ActionEvent event) throws Exception {
        DodajKlienta dodajKlienta = new DodajKlienta();
        Stage dodajKlientaStage = new Stage();
        dodajKlienta.start(dodajKlientaStage);
    }

    @FXML
    private TextField usunKlientTextField;

    @FXML
    void usunKlientButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        if (usunKlientTextField.getText().trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad usuwania danych");
            alert.setContentText("Szczegoly: " + "Podaj ID klienta do usuniecia");
            alert.show();
        } else {
            Klient.usunKlient(conn, Integer.parseInt(usunKlientTextField.getText().trim()));
            listaKlientow = new Klient().getAll(conn);
            setTableViewKlient(listaKlientow);
        }
    }
    @FXML
    private Button zaktualizujKlientButton;

    @FXML
    void zaktualizujKlientButtonOnAction(ActionEvent event) throws Exception {
        ZaktualizujKlienta zaktualizujKlienta = new ZaktualizujKlienta();
        Stage zaktualizujKlientaStage = new Stage();
        zaktualizujKlienta.start(zaktualizujKlientaStage);
    }

    private void setTableViewKlient(ObservableList<Klient> listaKlientow) {
        tableCollumnKlientNr_klienta.setCellValueFactory(new PropertyValueFactory<>("nr_klienta"));
        tableCollumnKlientImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        tableCollumnKlientNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        tableCollumnKlientPesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        tableCollumnKlientNr_telefonu.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        //tableCollumnKlientId_firmy.setCellValueFactory(new PropertyValueFactory<>("id_firmy"));

        tableKlient.setItems(listaKlientow);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = DBConnection.getConnection();
        listaKlientow = new Klient().getAll(conn);
        setTableViewKlient(listaKlientow);
    }
}
