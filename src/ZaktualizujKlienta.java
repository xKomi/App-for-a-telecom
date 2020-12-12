package wbd_vol2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ZaktualizujKlienta extends Application {

    public static Klient getKlientToUpdate(Connection conn, Integer Nr_klienta) {
        Klient klient = new Klient();

        String sql = "SELECT NR_KLIENTA, IMIE, NAZWISKO, PESEL, NR_TELEFONU, ID_FIRMY FROM KLIENCI"
                + " WHERE NR_KLIENTA LIKE ?";

        PreparedStatement stmt;
        ResultSet rs;
        Boolean sprawdzenie = false;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Nr_klienta);

            rs = stmt.executeQuery();

            while (rs.next()) {
                klient.setNr_klienta(rs.getInt(1));
                klient.setImie(rs.getString(2));
                klient.setNazwisko(rs.getString(3));
                klient.setPesel(rs.getLong(4));
                klient.setNr_telefonu(rs.getString(5));
                klient.setId_firmy(rs.getInt(6));
                sprawdzenie = true;
            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        if (sprawdzenie) {
            return klient;
        } else {
            return null;
        }
    }

    public static int updateKlient(Connection conn, Klient klient, String Imie, String Nazwisko, String PESEL, String Nr_telefonu) {
        int res = 0;

        String sql = "UPDATE KLIENCI SET IMIE = ?, NAZWISKO = ?, PESEL = ?, NR_TELEFONU = ?, ID_FIRMY = ?"
                + " WHERE NR_KLIENTA = ?";

        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);
            if (Imie.length() < 1) {
                stmt.setString(1, klient.getImie());
            } else {
                stmt.setString(1, Imie);
            }
            if (Nazwisko.length() < 1) {
                stmt.setString(2, klient.getNazwisko());
            } else {
                stmt.setString(2, Nazwisko);
            }
            if (PESEL.length() < 1) {
                stmt.setLong(3, klient.getPesel());
            } else if (PESEL.length() != 11) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad PESELU");
                alert.setContentText("Szczegoly: " + "PESEL musi miec 11 cyfr");
                alert.show();
                return 0;
            } else {
                try {
                    stmt.setLong(3, Long.parseLong(PESEL));
                } catch (Exception exc) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad PESELU");
                    alert.setContentText("Szczegoly: " + "dopuszczalne sa tylko cyfry");
                    alert.show();
                    return 0;
                }
            }

            if (Nr_telefonu.length() < 1) {
                stmt.setString(4, klient.getNr_telefonu());
            } else if (Nr_telefonu.length() != 9) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad nr telefonu");
                alert.setContentText("Szczegoly: " + "numer musi miec 9 cyfr");
                alert.show();
                return 0;
            } else {
                stmt.setString(4, Nr_telefonu);
            }

            stmt.setInt(5, klient.getId_firmy());
            stmt.setInt(6, klient.getNr_klienta());
            res = stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pomyslnie zaktualizowano dane");
            alert.show();

            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad aktualizacji");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return res;

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ZaktualizujKlienta.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Operator telekomunikacyjny");
        stage.setResizable(false);
        stage.show();
    }

}
