package wbd_vol2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OknoGlowneController implements Initializable {

    @FXML
    private Button pracownicyOknoGlowneButton;

    @FXML
    private Button klienciOknoGlowneButton;
    
    @FXML
    private Button wylogujOknoGlowneButton;

    @FXML
    void klienciOknoGlowneButtonOnAction(ActionEvent event) throws Exception {
        Klient klient = new Klient();
        Stage klientStage = new Stage();
        klient.start(klientStage);
    }

    @FXML
    void pracownicyOknoGlowneButtonOnAction(ActionEvent event) throws Exception {
        Pracownik pracownik = new Pracownik();
        Stage pracownikStage = new Stage();
        pracownik.start(pracownikStage);
    }
    
    @FXML
    void wylogujOknoGlowneButtonOnAction (ActionEvent event) throws Exception {
        OknoLogowania oknoLogowania = new OknoLogowania();
        Stage oknoLogowaniaStage = new Stage();
        oknoLogowania.start(oknoLogowaniaStage);
        Stage stage = (Stage) wylogujOknoGlowneButton.getScene().getWindow();
        stage.close();
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
