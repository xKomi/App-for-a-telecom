package wbd_vol2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class Waski {
    
    private Integer nr_klienta;
    private String imie;
    private String nazwisko;
    private Long pesel;
    private String nr_telefonu;
    private Integer id_firmy = 2;

    public Integer getNr_klienta() {
        return nr_klienta;
    }

    public void setNr_klienta(Integer nr_klienta) {
        this.nr_klienta = nr_klienta;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }

    public Integer getId_firmy() {
        return id_firmy;
    }

    public void setId_firmy(Integer id_firmy) {
        this.id_firmy = id_firmy;
    }
    
    public ObservableList<Waski> getAll(Connection conn) {
        ObservableList<Waski> listaWaski = FXCollections.observableArrayList();

        String sql = "SELECT NR_KLIENTA, IMIE, NAZWISKO, PESEL, NR_TELEFONU, ID_FIRMY FROM KLIENCI ORDER BY NR_KLIENTA";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Waski waski = new Waski();
                waski.setNr_klienta(rs.getInt(1));
                waski.setImie(rs.getString(2));
                waski.setNazwisko(rs.getString(3));
                waski.setPesel(rs.getLong(4));
                waski.setNr_telefonu(rs.getString(5));
                waski.setId_firmy(rs.getInt(6));

                listaWaski.add(waski);

            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return listaWaski;
    }
    
    public ObservableList<Waski> getRestrictedList(Connection conn, String Parametr) {
        ObservableList<Waski> listaWaski = FXCollections.observableArrayList();

        String sql = "SELECT NR_KLIENTA, IMIE, NAZWISKO, PESEL, NR_TELEFONU, ID_FIRMY FROM KLIENCI"
                + " WHERE UPPER(IMIE) LIKE ? OR UPPER(NAZWISKO) LIKE ? OR PESEL LIKE ? OR NR_TELEFONU LIKE ? ORDER BY NR_KLIENTA";

        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Parametr.toUpperCase() + "%");
            stmt.setString(2, Parametr.toUpperCase() + "%");
            stmt.setString(3, Parametr.toUpperCase() + "%");
            stmt.setString(4, Parametr.toUpperCase() + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Waski waski = new Waski();
                waski.setNr_klienta(rs.getInt(1));
                waski.setImie(rs.getString(2));
                waski.setNazwisko(rs.getString(3));
                waski.setPesel(rs.getLong(4));
                waski.setNr_telefonu(rs.getString(5));
                waski.setId_firmy(rs.getInt(6));

                listaWaski.add(waski);

            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return listaWaski;
    }
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Waski.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Operator telekomunikacyjny");
        stage.setResizable(false);
        stage.show();
    }
}
