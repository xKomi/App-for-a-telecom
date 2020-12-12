package wbd_vol2;

import java.sql.*;
import javafx.scene.control.Alert;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {

        String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        String DB_USER = "jkoman";
        String DB_PASSWORD = "jkoman";

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Zostales polaczony z baza!");
            //alert.show();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad polaczenia z baza");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return conn;
    }
}
