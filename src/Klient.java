package wbd_vol2;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Klient {

    private Integer nr_klienta;
    private String imie;
    private String nazwisko;
    private Long pesel;
    private String nr_telefonu;
    private Integer id_firmy = 2;

    public Integer getId_firmy() {
        return id_firmy;
    }

    public void setId_firmy(Integer id_firmy) {
        this.id_firmy = id_firmy;
    }

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

    public ObservableList<Klient> getAll(Connection conn) {
        ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();

        String sql = "SELECT NR_KLIENTA, IMIE, NAZWISKO, PESEL, NR_TELEFONU, ID_FIRMY FROM KLIENCI ORDER BY NR_KLIENTA";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Klient klient = new Klient();
                klient.setNr_klienta(rs.getInt(1));
                klient.setImie(rs.getString(2));
                klient.setNazwisko(rs.getString(3));
                klient.setPesel(rs.getLong(4));
                klient.setNr_telefonu(rs.getString(5));
                klient.setId_firmy(rs.getInt(6));

                listaKlientow.add(klient);

            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return listaKlientow;
    }

    public ObservableList<Klient> getRestrictedList(Connection conn, String Parametr) {
        ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();

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
                Klient klient = new Klient();
                klient.setNr_klienta(rs.getInt(1));
                klient.setImie(rs.getString(2));
                klient.setNazwisko(rs.getString(3));
                klient.setPesel(rs.getLong(4));
                klient.setNr_telefonu(rs.getString(5));
                klient.setId_firmy(rs.getInt(6));

                listaKlientow.add(klient);

            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return listaKlientow;
    }

    public int insertKlient(Connection conn, Klient klient) {
        Integer res = 0;
        String sql;
        if (klient != null) {
            if (klient.getPesel() != null) {
                sql = "INSERT INTO KLIENCI(IMIE,NAZWISKO,PESEL,NR_TELEFONU, ID_FIRMY) VALUES(?,?,?,?,?)";
                PreparedStatement stmt;

                try {
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, klient.getImie());
                    stmt.setString(2, klient.getNazwisko());
                    stmt.setLong(3, klient.getPesel());
                    stmt.setString(4, klient.getNr_telefonu());
                    stmt.setInt(5, id_firmy);
                    res = stmt.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pomyslnie dodano dane");
                    alert.show();

                    stmt.close();
                } catch (SQLException exc) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad dodawania danych");
                    alert.setContentText("Szczegoly: " + exc.getMessage());
                    alert.show();
                }
                return res;
            } else {
                sql = "INSERT INTO KLIENCI(IMIE,NAZWISKO,NR_TELEFONU,ID_FIRMY) VALUES(?,?,?,?)";
                PreparedStatement stmt;

                try {
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, klient.getImie());
                    stmt.setString(2, klient.getNazwisko());
                    stmt.setString(3, klient.getNr_telefonu());
                    stmt.setInt(4, id_firmy);
                    res = stmt.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pomyslnie dodano dane");
                    alert.show();

                    stmt.close();
                } catch (SQLException exc) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Blad dodawania danych 10");
                    alert.setContentText("Szczegoly: " + exc.getMessage());
                    alert.show();
                }
                return res;
            }
        } else {
            return res;
        }
    }

    public static void usunKlient(Connection conn, Integer Id_klienta) {

        String sql = "DELETE FROM KLIENCI WHERE NR_KLIENTA LIKE ?";

        PreparedStatement stmt;
        Integer rs;

        try {

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Id_klienta);
            rs = stmt.executeUpdate();
            if (rs > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pomyslnie usunieto klienta");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad usuwania klienta");
                alert.setContentText("Szczegoly: " + "Nie ma klienta o takim ID");
                alert.show();
            }
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad usuwania klienta");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLKlient.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Operator telekomunikacyjny");
        stage.setResizable(false);
        stage.show();
    }
}
