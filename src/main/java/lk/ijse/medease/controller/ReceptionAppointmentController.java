package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.medease.dto.AppointmentDTO;
import lk.ijse.medease.dto.tm.AppointmentTM;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ReceptionAppointmentController implements Initializable {
    private AppointmentController appointmentController;
    private PatientController patientController;
    private DoctorController doctorController;
    private AttendanceController attendanceController;

    private final String patientIdPattern = "^P\\d{4}$";
    private final String doctorIdPattern = "^D\\d{4}$";
    private final String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    private String appointmentId;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<AppointmentTM, String> colAppId;

    @FXML
    private TableColumn<AppointmentTM, Date> colDate;

    @FXML
    private TableColumn<AppointmentTM, String> colDocId;

    @FXML
    private TableColumn<AppointmentTM, Number> colNum;

    @FXML
    private TableColumn<AppointmentTM, String> colPatientId;

    @FXML
    private TableColumn<AppointmentTM, String> colTime;



    @FXML
    private TableView<AppointmentTM> tblAppointment;

    @FXML
    private TextField txtAppointmentId;

    @FXML
    private TextField txtCheckInNo;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDoctorId;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtTime;

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        AppointmentTM selectedAppointment = tblAppointment.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            txtAppointmentId.setText(selectedAppointment.getAppointmentId());
            txtPatientId.setText(selectedAppointment.getPatientId());
            txtDoctorId.setText(selectedAppointment.getDoctorId());
            txtDate.setText(String.valueOf(selectedAppointment.getDate()));
            txtCheckInNo.setText(String.valueOf(selectedAppointment.getCheckInNo()));
            txtTime.setText(String.valueOf(selectedAppointment.getTime()));

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtAppointmentId.getText().isEmpty() || txtPatientId.getText().isEmpty() || txtDoctorId.getText().isEmpty() || txtDate.getText().isEmpty() || txtCheckInNo.getText().isEmpty() || txtTime.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).showAndWait();
        }else {
            addAppointment();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteAppointment();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        updateAppointment();
    }


    @FXML
    void dateOnKeyReleased(KeyEvent event) {
        txtDate.setStyle(txtDate.getStyle() + "-fx-text-fill: white;");
        if (!(txtDate.getText().matches(datePattern))) {
            txtDate.setStyle(txtDate.getStyle() + "-fx-text-fill: red;");
        }else {
            checkNumber();
        }
    }

    @FXML
    void doctorIdOnKeyReleased(KeyEvent event) {
        txtDoctorId.setStyle(txtDoctorId.getStyle() + "-fx-text-fill: white;");
        if (!(txtDoctorId.getText().matches(doctorIdPattern))){
            txtDoctorId.setStyle(txtDoctorId.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void patientIdOnKeyReleased(KeyEvent event) {
        txtPatientId.setStyle(txtPatientId.getStyle() + "-fx-text-fill: white;");
        if (!(txtPatientId.getText().matches(patientIdPattern))) {
            txtPatientId.setStyle(txtPatientId.getStyle() + "-fx-text-fill: red;");
        }
    }

    private void checkNumber() {
        try {
            String employeeId = doctorController.getEmployeeIdByDoctorId(txtDoctorId.getText());

            if (employeeId != null) {
                boolean isADayOff = attendanceController.checkEmployeeIsOffOn(employeeId, Date.valueOf(txtDate.getText()));

                if (!isADayOff) {
                    int num = appointmentController.checkNo(txtDoctorId.getText(), Date.valueOf(txtDate.getText()));
                    num++;

                    if (num > 60) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Limit Reached");
                        alert.setContentText("You have reached the limit of 60 appointments");
                        alert.showAndWait();
                    } else{
                        txtCheckInNo.setText(String.valueOf(num));
                        String time = appointmentController.getTime(txtDoctorId.getText());
                        txtTime.setText(time);
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Doctor Not Available");
                    alert.setContentText("Doctor has a day off");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Doctor Not Found");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void addAppointment() {

        try {
            if (patientController.searchPatient(txtPatientId.getText()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("NOT FOUND");
                alert.setContentText("Patient Not Found");
                alert.showAndWait();
            }else {
                AppointmentDTO appointmentDTO = new AppointmentDTO(appointmentId, txtPatientId.getText(), txtDoctorId.getText(), Date.valueOf(txtDate.getText()), Integer.parseInt(txtCheckInNo.getText()), txtTime.getText());

                String response = appointmentController.addAppointment(appointmentDTO);
                loadData();
                clearFields();
                setAppointmentId();
                disableButtons();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment");
                alert.setHeaderText("APPOINTMENT ADD");
                alert.setContentText(response);
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateAppointment() {
        AppointmentDTO appointmentDTO = new AppointmentDTO(txtPatientId.getText(), txtDoctorId.getText(), Date.valueOf(txtDate.getText()), Integer.parseInt(txtCheckInNo.getText()), txtTime.getText());

        try {
            String response = appointmentController.updateAppointment(appointmentDTO);
            loadData();
            clearFields();
            setAppointmentId();
            disableButtons();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment");
            alert.setHeaderText("APPOINTMENT UPDATE");
            alert.setContentText(response);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void deleteAppointment() {
        try {
            AppointmentDTO appointmentDTO = appointmentController.searchAppointment(txtAppointmentId.getText());
            if (appointmentDTO == null){
                new Alert(Alert.AlertType.ERROR, "Appointment Not Found!").showAndWait();
            }else {
                String response = appointmentController.deleteAppointment(appointmentDTO);

                loadData();
                clearFields();
                setAppointmentId();
                disableButtons();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment");
                alert.setHeaderText("APPOINTMENT DELETE");
                alert.setContentText(response);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableButtons();

        appointmentController = new AppointmentController();
        patientController = new PatientController();
        doctorController = new DoctorController();
        attendanceController = new AttendanceController();

        setAppointmentId();

        colAppId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDocId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("checkInNo"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadData();
    }

    private void loadData() {
        try {
            ArrayList<AppointmentDTO> appointmentDTOs = appointmentController.getAllAppointments();

            ObservableList<AppointmentTM> appointmentObList = FXCollections.observableArrayList();

            for (AppointmentDTO dto: appointmentDTOs){
                AppointmentTM appointmentTM = new AppointmentTM(dto.getAppointmentId(), dto.getPatientId(), dto.getDoctorId(), dto.getDate(), dto.getCheckInNo(), dto.getTime());
                appointmentObList.add(appointmentTM);
            }

            tblAppointment.setItems(appointmentObList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableButtons() {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void setAppointmentId() {
        try {
            appointmentId = appointmentController.getNextId();
            txtAppointmentId.setText(appointmentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields(){
        txtPatientId.clear();
        txtDoctorId.clear();
        txtDate.clear();
        txtCheckInNo.clear();
        txtAppointmentId.clear();
        txtTime.clear();
    }
}
