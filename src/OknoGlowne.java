package wbd_vol2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class OknoGlowne extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("OknoGlowne.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Operator telekomunikacyjny");
        stage.setResizable(false);
        stage.show();
    }
}
