package wbd_vol2;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class WaskiController implements Initializable {

    private Connection conn;
    private ObservableList<Waski> listaWaski = FXCollections.observableArrayList();
    
    
    @FXML
    private TableView<Waski> waskiTable;

    @FXML
    private TableColumn<Waski, Integer> tableCollumnWaskiNrKlienta;

    @FXML
    private TableColumn<Waski, String> tableCollumnWaskiImie;

    @FXML
    private TableColumn<Waski, String> tableCollumnWaskiNazwisko;

    @FXML
    private TableColumn<Waski, Long> tableCollumnWaskiPESEL;

    @FXML
    private TableColumn<Waski, String> tableCollumnWaskiNrTelefonu;

    @FXML
    private TextField waskiTextField;

    @FXML
    private Button wyszukajWaskiButton;

    @FXML
    void wyszukajWaskiButtonOnAction(ActionEvent event) {
        conn = DBConnection.getConnection();
        listaWaski = new Waski().getRestrictedList(conn, waskiTextField.getText().trim());
        setTableViewWaski(listaWaski);
    }
    
    @FXML
    private Button wylogujWaskiButton;

    @FXML
    void wylogujWaskiButtonOnAction(ActionEvent event) throws Exception {
        OknoLogowania oknoLogowania = new OknoLogowania();
        Stage oknoLogowaniaStage = new Stage();
        oknoLogowania.start(oknoLogowaniaStage);
        Stage stage = (Stage) wylogujWaskiButton.getScene().getWindow();
        stage.close();
    }
    
    private void setTableViewWaski(ObservableList<Waski> listaWaski) {
        tableCollumnWaskiNrKlienta.setCellValueFactory(new PropertyValueFactory<>("nr_klienta"));
        tableCollumnWaskiImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        tableCollumnWaskiNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        tableCollumnWaskiPESEL.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        tableCollumnWaskiNrTelefonu.setCellValueFactory(new PropertyValueFactory<>("nr_telefonu"));
        //tableCollumnWaskiIdFirmy.setCellValueFactory(new PropertyValueFactory<>("id_firmy"));

        waskiTable.setItems(listaWaski);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getConnection();
        listaWaski = new Waski().getAll(conn);
        setTableViewWaski(listaWaski);
    }

}
