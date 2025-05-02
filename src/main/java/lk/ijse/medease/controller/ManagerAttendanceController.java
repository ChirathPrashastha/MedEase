package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerAttendanceController implements Initializable {

    @FXML
    private ComboBox<String> cbMonth;

    @FXML
    private ComboBox<Integer> cbYear;

    @FXML
    private ComboBox<Integer> cbDay;

    @FXML
    private TableColumn<?, ?> colAttendDate;

    @FXML
    private TableColumn<?, ?> colEID;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<?> tblAttendance;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    void btnPresentOnAction(ActionEvent event) {
        String status = cbMonth.getSelectionModel().getSelectedItem();
        System.out.println(status);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnAbsentOnlyOnAction(ActionEvent event) {

    }

    @FXML
    void btnLowestAttendanceOnAction(ActionEvent event) {

    }

    @FXML
    void btnPresentOnlyOnAction(ActionEvent event) {

    }

    @FXML
    void getYearOnAction(ActionEvent event) {

    }

    @FXML
    void getMonthOnAction(ActionEvent event) {
        setDaysOfMonth(cbMonth.getSelectionModel().getSelectedItem(), cbYear.getSelectionModel().getSelectedItem());
    }

    @FXML
    void getDayOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbMonth.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");

        int currentYear = Year.now().getValue();
        String currentMonth = LocalDate.now().getMonth().toString();
        int currentDay = LocalDate.now().getDayOfMonth();

        int beginYear = 2020; // Attendance reporting began year

        for (int year = currentYear; year >= beginYear; year--) {
            cbYear.getItems().add(year);
        }

        cbMonth.setValue(currentMonth); // Set current month as default
        cbYear.setValue(currentYear);  // Set current year as default
        cbDay.setValue(currentDay);

        setDaysOfMonth(currentMonth, currentYear);

    }

    private void setDaysOfMonth(String monthName, int year) {
        Month month = Month.valueOf(monthName);
        YearMonth yearMonth = YearMonth.of(year, month);

        int totalDays = yearMonth.lengthOfMonth();

        ArrayList<Integer> daysOfMonth = new ArrayList<>();
        for (int day = 1; day <= totalDays; day++) {
            daysOfMonth.add(day);
        }

        for (Integer day : daysOfMonth) { // setting days
            cbDay.getItems().add(day);
        }
    }
}
