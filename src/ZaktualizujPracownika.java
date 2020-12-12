package wbd_vol2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ZaktualizujPracownika {

    public static Pracownik getPracownikToUpdate(Connection conn, Integer Nr_pracownika) {
        Pracownik pracownik = new Pracownik();

        String sql = "SELECT NR_PRACOWNIKA, IMIE, NAZWISKO, DATA_ZATRUDNIENIA, RODZAJ_ZATRUDNIENIA, NR_TELEFONU FROM PRACOWNICY"
                + " WHERE NR_PRACOWNIKA LIKE ?";

        PreparedStatement stmt;
        ResultSet rs;
        Boolean sprawdzenie = false;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Nr_pracownika);

            rs = stmt.executeQuery();

            while (rs.next()) {
                pracownik.setNr_pracownika(rs.getInt(1));
                pracownik.setImie(rs.getString(2));
                pracownik.setNazwisko(rs.getString(3));
                pracownik.setData_zatrudnienia(rs.getDate(4).toLocalDate());
                pracownik.setRodzaj_zatrudnienia(rs.getString(5));
                pracownik.setNr_telefonu(rs.getString(6));
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
            return pracownik;
        } else {
            return null;
        }
    }

    public static int updatePracownik(Connection conn, Pracownik pracownik, String Imie, String Nazwisko, LocalDate Data_zatrudnienia, String Rodzaj_zatrudnienia, String Nr_telefonu) {
        int res = 0;

        String sql = "UPDATE PRACOWNICY SET IMIE = ?, NAZWISKO = ?, DATA_ZATRUDNIENIA = ?, RODZAJ_ZATRUDNIENIA = ?, NR_TELEFONU = ?"
                + " WHERE NR_PRACOWNIKA = ?";

        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);
            if (Imie.length() < 1) {
                stmt.setString(1, pracownik.getImie());
            } else {
                stmt.setString(1, Imie);
            }
            if (Nazwisko.length() < 1) {
                stmt.setString(2, pracownik.getNazwisko());
            } else {
                stmt.setString(2, Nazwisko);
            }
            if (Data_zatrudnienia == null) {
                stmt.setDate(3, Date.valueOf(pracownik.getData_zatrudnienia()));
            } else {
                stmt.setDate(3, Date.valueOf(Data_zatrudnienia));
            }
            if (Rodzaj_zatrudnienia == null) {
                stmt.setString(4, pracownik.getRodzaj_zatrudnienia());
            } else {
                stmt.setString(4, Rodzaj_zatrudnienia);
            }
            if (Nr_telefonu.length() < 1) {
                stmt.setString(5, pracownik.getNr_telefonu());
            } else {
                stmt.setString(5, Nr_telefonu);
            }

            stmt.setInt(6, pracownik.getNr_pracownika());
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

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ZaktualizujPracownika.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Operator telekomunikacyjny");
        stage.setResizable(false);
        stage.show();
    }
}
