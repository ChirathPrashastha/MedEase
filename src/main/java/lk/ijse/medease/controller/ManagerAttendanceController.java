package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
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
        markAttendance();
    }

    @FXML
    void btnDayOffOnAction(ActionEvent event) {
        addDayOff();
    }

    @FXML
    void btnAbsentOnlyOnAction(ActionEvent event) {
        tblAttendance.getItems().clear();
        loadFilteredTable(AttendStatus.ABSENT);
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

    private void markAttendance() {
        try {
            int selectedYear = cbYear.getValue();
            Month selectedMonth = Month.valueOf(cbMonth.getValue());
            int selectedDay = cbDay.getValue();

            LocalDate localDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
            Date selectedDate = Date.valueOf(localDate);

            String response = attendanceController.markAttendance(txtEmployeeId.getText(), selectedDate);

            if (response.equals("success")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ATTENDANCE");
                alert.setHeaderText("Attendance Marked Successfully");

                Image image = new Image(getClass().getResource("/images/success.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);

                alert.setGraphic(imageView);

                alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #85ff7a");
                alert.getDialogPane().setStyle("-fx-background-color: #2db83d");

                alert.showAndWait();

                txtEmployeeId.clear();
                tblAttendance.getItems().clear();
                loadTableData();

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ATTENDANCE");
                alert.setHeaderText("Failed to Mark Attendance");

                Image image = new Image(getClass().getResource("/images/failed.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);

                alert.setGraphic(imageView);

                alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #f87c7c");
                alert.getDialogPane().setStyle("-fx-background-color: #f83e3e");

                alert.showAndWait();
            }

        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void addDayOff() {
        int selectedYear = cbYear.getValue();
        Month selectedMonth = Month.valueOf(cbMonth.getValue());
        int selectedDay = cbDay.getValue();

        LocalDate localDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
        Date selectedDate = Date.valueOf(localDate);

        try {
            String response = attendanceController.addDayOff(txtEmployeeId.getText(), selectedDate);

            if (response.equals("success")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DAY OFF");
                alert.setHeaderText("Day Off Scheduled Successfully");

                Image image = new Image(getClass().getResource("/images/success.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);

                alert.setGraphic(imageView);

                alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #85ff7a");
                alert.getDialogPane().setStyle("-fx-background-color: #2db83d");

                alert.showAndWait();
                txtEmployeeId.clear();

            }else if (response.equals("failed")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Day Off");
                alert.setHeaderText("Failed to Schedule Day Off");

                Image image = new Image(getClass().getResource("/images/failed.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);

                alert.setGraphic(imageView);

                alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #f87c7c");
                alert.getDialogPane().setStyle("-fx-background-color: #f83e3e");

                alert.showAndWait();
            }else if (response.equals("ineligible")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Day Off");
                alert.setHeaderText("Failed to Schedule Day Off");
                alert.setContentText("Employee's Maximum Day Off Count is Reached");

                Image image = new Image(getClass().getResource("/images/failed.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);

                alert.setGraphic(imageView);

                alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #f87c7c");
                alert.getDialogPane().setStyle("-fx-background-color: #f83e3e");

                alert.showAndWait();
            }else if (response == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Day Off");
                alert.setHeaderText("Database Error");
                alert.setContentText("Failed to retrieve data");

                Image image = new Image(getClass().getResource("/images/failed.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);

                alert.setGraphic(imageView);

                alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #f87c7c");
                alert.getDialogPane().setStyle("-fx-background-color: #f83e3e");

                alert.showAndWait();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Day Off");
            alert.setHeaderText("Database Error");
            alert.setContentText("Please check the details");

            Image image = new Image(getClass().getResource("/images/failed.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);

            alert.setGraphic(imageView);

            alert.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #f87c7c");
            alert.getDialogPane().setStyle("-fx-background-color: #f83e3e");

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
