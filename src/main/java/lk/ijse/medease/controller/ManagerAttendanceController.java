package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.AttendStatus;
import lk.ijse.medease.dto.AttendanceDTO;
import lk.ijse.medease.dto.tm.AttendanceTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerAttendanceController implements Initializable {

    private AttendanceController attendanceController;

    private AttendStatus attendStatus;

    private ArrayList<AttendanceDTO> attendanceDTOs;

    private ObservableList<AttendanceTM> attendanceObList;

    @FXML
    private ComboBox<String> cbMonth;

    @FXML
    private ComboBox<Integer> cbYear;

    @FXML
    private ComboBox<Integer> cbDay;

    @FXML
    private TableColumn<AttendanceTM, Date> colAttendDate;

    @FXML
    private TableColumn<AttendanceTM, String> colEID;

    @FXML
    private TableColumn<AttendanceTM, AttendStatus> colStatus;

    @FXML
    private TableView<AttendanceTM> tblAttendance;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    void btnPresentOnAction(ActionEvent event) {

    }

    @FXML
    void btnDayOffOnAction(ActionEvent event) {

    }

    @FXML
    void btnAbsentOnlyOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadFilteredTable(AttendStatus.ABSENT);
    }

    @FXML
    void btnLowestAttendanceOnAction(ActionEvent event) {

    }

    @FXML
    void btnPresentOnlyOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadFilteredTable(AttendStatus.PRESENT);
    }

    @FXML
    void btnDayOffOnlyOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadFilteredTable(AttendStatus.DAY_OFF);
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadTableData();
    }

    @FXML
    void getYearOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadTableData();
    }

    @FXML
    void getMonthOnAction(ActionEvent event) {
        setDaysOfMonth(cbMonth.getSelectionModel().getSelectedItem(), cbYear.getSelectionModel().getSelectedItem()); // setting combo box days according to month

        tblAttendance.getItems().clear();
        loadTableData();
    }

    @FXML
    void getDayOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadTableData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attendanceController = new AttendanceController();

        attendanceObList = FXCollections.observableArrayList();

        cbMonth.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");

        int currentYear = Year.now().getValue();
        String currentMonth = LocalDate.now().getMonth().toString();
        int currentDay = LocalDate.now().getDayOfMonth();

        int beginYear = 2023; // Attendance reporting began year

        for (int year = currentYear; year >= beginYear; year--) {
            cbYear.getItems().add(year);
        }

        cbMonth.setValue(currentMonth); // Set current month as default
        cbYear.setValue(currentYear);  // Set current year as default
        cbDay.setValue(currentDay);

        setDaysOfMonth(currentMonth, currentYear);

        colAttendDate.setCellValueFactory(new PropertyValueFactory<>("attendDate"));
        colEID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTableData();
    }

    private void loadTableData() {
        try {
            int selectedYear = cbYear.getValue();
            Month selectedMonth = Month.valueOf(cbMonth.getValue());
            int selectedDay = cbDay.getValue();

            LocalDate localDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
            Date selectedDate = Date.valueOf(localDate);

            attendanceDTOs = attendanceController.getAttendanceByDate(selectedDate);

            for (AttendanceDTO dto : attendanceDTOs) {
                AttendanceTM attendanceTM = new AttendanceTM(dto.getAttendDate(), dto.getEmployeeId(), dto.getStatus());
                attendanceObList.add(attendanceTM);
            }

            tblAttendance.setItems(attendanceObList);

        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadFilteredTable(AttendStatus status){
        try {
            int selectedYear = cbYear.getValue();
            Month selectedMonth = Month.valueOf(cbMonth.getValue());
            int selectedDay = cbDay.getValue();

            LocalDate localDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
            Date selectedDate = Date.valueOf(localDate);

            attendanceDTOs = attendanceController.filterAttendance(status, selectedDate);

            for (AttendanceDTO dto : attendanceDTOs) {
                AttendanceTM attendanceTM = new AttendanceTM(dto.getAttendDate(), dto.getEmployeeId(), dto.getStatus());
                attendanceObList.add(attendanceTM);
            }

            tblAttendance.setItems(attendanceObList);

        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
