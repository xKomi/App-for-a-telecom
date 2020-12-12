package wbd_vol2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Pracownik {

    public Integer getNr_pracownika() {
        return Nr_pracownika;
    }

    public void setNr_pracownika(Integer Nr_pracownika) {
        this.Nr_pracownika = Nr_pracownika;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String Imie) {
        this.Imie = Imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String Nazwisko) {
        this.Nazwisko = Nazwisko;
    }

    public LocalDate getData_zatrudnienia() {
        return Data_zatrudnienia;
    }

    public void setData_zatrudnienia(LocalDate Data_zatrudnienia) {
        this.Data_zatrudnienia = Data_zatrudnienia;
    }

    public String getRodzaj_zatrudnienia() {
        return Rodzaj_zatrudnienia;
    }

    public void setRodzaj_zatrudnienia(String Rodzaj_zatrudnienia) {
        this.Rodzaj_zatrudnienia = Rodzaj_zatrudnienia;
    }

    public String getNr_telefonu() {
        return Nr_telefonu;
    }

    public void setNr_telefonu(String Nr_telefonu) {
        this.Nr_telefonu = Nr_telefonu;
    }

    public String getPlec() {
        return Plec;
    }

    public void setPlec(String Plec) {
        this.Plec = Plec;
    }

    public LocalDate getData_urodzenia() {
        return Data_urodzenia;
    }

    public void setData_urodzenia(LocalDate Data_urodzenia) {
        this.Data_urodzenia = Data_urodzenia;
    }

    public Long getNr_konta() {
        return Nr_konta;
    }

    public void setNr_konta(Long Nr_konta) {
        this.Nr_konta = Nr_konta;
    }

    public Long getPESEL() {
        return PESEL;
    }

    public void setPESEL(Long PESEL) {
        this.PESEL = PESEL;
    }

    public Integer getId_firmy() {
        return Id_firmy;
    }

    public void setId_firmy(Integer Id_firmy) {
        this.Id_firmy = Id_firmy;
    }

    public Integer getNr_adresu() {
        return Nr_adresu;
    }

    public void setNr_adresu(Integer Nr_adresu) {
        this.Nr_adresu = Nr_adresu;
    }

    Integer Nr_pracownika;
    String Imie;
    String Nazwisko;
    String Plec;
    LocalDate Data_zatrudnienia;
    String Rodzaj_zatrudnienia;
    LocalDate Data_urodzenia;
    String Nr_telefonu;
    Long Nr_konta;
    Long PESEL;
    Integer Id_firmy = 2;
    Integer Nr_adresu = 1;

    public ObservableList<Pracownik> getAll(Connection conn) {
        ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();

        String sql = "SELECT NR_PRACOWNIKA, IMIE, NAZWISKO, DATA_ZATRUDNIENIA, RODZAJ_ZATRUDNIENIA, NR_TELEFONU FROM PRACOWNICY ORDER BY NR_PRACOWNIKA";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.setNr_pracownika(rs.getInt(1));
                pracownik.setImie(rs.getString(2));
                pracownik.setNazwisko(rs.getString(3));
                pracownik.setData_zatrudnienia(rs.getDate(4).toLocalDate());
                pracownik.setRodzaj_zatrudnienia(rs.getString(5));
                pracownik.setNr_telefonu(rs.getString(6));

                listaPracownikow.add(pracownik);

            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return listaPracownikow;
    }

    public ObservableList<Pracownik> getRestrictedList(Connection conn, String Parametr) {
        ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();

        String sql = "SELECT NR_PRACOWNIKA, IMIE, NAZWISKO, DATA_ZATRUDNIENIA, RODZAJ_ZATRUDNIENIA, NR_TELEFONU FROM PRACOWNICY"
                + " WHERE UPPER(IMIE) LIKE ? OR UPPER(NAZWISKO) LIKE ? OR RODZAJ_ZATRUDNIENIA LIKE ? OR NR_TELEFONU LIKE ? ORDER BY NR_PRACOWNIKA";

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
                Pracownik pracownik = new Pracownik();
                pracownik.setNr_pracownika(rs.getInt(1));
                pracownik.setImie(rs.getString(2));
                pracownik.setNazwisko(rs.getString(3));
                pracownik.setData_zatrudnienia(rs.getDate(4).toLocalDate());
                pracownik.setRodzaj_zatrudnienia(rs.getString(5));
                pracownik.setNr_telefonu(rs.getString(6));

                listaPracownikow.add(pracownik);

            }

            rs.close();
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pobierania danych");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
        return listaPracownikow;
    }

    public int insertPracownik(Connection conn, Pracownik pracownik) {
        Integer res = 0;
        String sql = "INSERT INTO PRACOWNICY(DATA_ZATRUDNIENIA,RODZAJ_ZATRUDNIENIA,PESEL,PLEC,IMIE,NAZWISKO,NR_TELEFONU,DATA_URODZENIA,NR_KONTA,ID_FIRMY,NR_ADRESU) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(pracownik.getData_zatrudnienia()));
            stmt.setString(2, pracownik.getRodzaj_zatrudnienia());
            stmt.setLong(3, pracownik.getPESEL());
            stmt.setString(4, pracownik.getPlec());
            stmt.setString(5, pracownik.getImie());
            stmt.setString(6, pracownik.getNazwisko());
            stmt.setString(7, pracownik.getNr_telefonu());
            stmt.setDate(8, Date.valueOf(pracownik.getData_urodzenia()));
            stmt.setLong(9, pracownik.getNr_konta());
            stmt.setInt(10, Id_firmy);
            stmt.setInt(11, Nr_adresu);
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
    }

    public static void usunPracownik(Connection conn, Integer Id_pracownika) {

        String sql = "DELETE FROM PRACOWNICY WHERE NR_PRACOWNIKA LIKE ?";

        PreparedStatement stmt;
        Integer rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Id_pracownika);
            rs = stmt.executeUpdate();

            if (rs > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pomyslnie usunieto pracownika");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blad usuwania pracownika");
                alert.setContentText("Szczegoly: " + "Nie ma pracownika o takim ID");
                alert.show();
            }
            stmt.close();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad usuwania pracownika");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.show();
        }
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Pracownik.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Operator telekomunikacyjny");
        stage.setResizable(false);
        stage.show();
    }
}
