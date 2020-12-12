package wbd_vol2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OknoLogowaniaController implements Initializable {

    @FXML
    private Button zalogujOknoLogowaniaButton;

    @FXML
    private PasswordField hasloPasswordField;

    @FXML
    private TextField loginTextField;

    @FXML
    void zalogujOknoLogowaniaButtonOnAction(ActionEvent event) throws Exception {
        String login = loginTextField.getText();
        String haslo = hasloPasswordField.getText();
        
        if (login.equals("admin") && haslo.equals("admin")) {
            OknoGlowne oknoGlowne = new OknoGlowne();
            Stage oknoGlowneStage = new Stage();
            oknoGlowne.start(oknoGlowneStage);
            Stage stage = (Stage) zalogujOknoLogowaniaButton.getScene().getWindow();
            stage.close();
        } 
        else if(login.equals("kowalski") && haslo.equals("haslo123")){ 
            Waski waski = new Waski();
            Stage waskiStage = new Stage();
            waski.start(waskiStage);
            Stage stage = (Stage) zalogujOknoLogowaniaButton.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bledny login lub haslo");
            alert.setContentText("Bledny login lub haslo, spróbuj ponownie");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
