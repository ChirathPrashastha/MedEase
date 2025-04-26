package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.AppointmentDTO;
import lk.ijse.medease.dto.tm.AppointmentTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ReceptionAppointmentController implements Initializable {
    private AppointmentController appointmentController;

    @FXML
    private TableColumn<AppointmentTM, Number> colAppId;

    @FXML
    private TableColumn<AppointmentTM, Date> colDate;

    @FXML
    private TableColumn<AppointmentTM, String> colDocId;

    @FXML
    private TableColumn<AppointmentTM, Number> colNum;

    @FXML
    private TableColumn<AppointmentTM, Number> colPatientId;

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
    void btnAddOnAction(ActionEvent event) {
        addAppointment();
    }

    @FXML
    void btnAppCheckOnAction(ActionEvent event) {
        checkNumber();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteAppointment();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        updateAppointment();
    }

    private void checkNumber() {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void addAppointment() {
        AppointmentDTO appointmentDTO = new AppointmentDTO(Integer.parseInt(txtPatientId.getText()),txtDoctorId.getText(), Date.valueOf(txtDate.getText()), Integer.parseInt(txtCheckInNo.getText()), txtTime.getText());

        try {
            String response = appointmentController.addAppointment(appointmentDTO);
            loadData();
            clearFields();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment");
            alert.setHeaderText("APPOINTMENT ADD");
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

    private void updateAppointment() {
        AppointmentDTO appointmentDTO = new AppointmentDTO(Integer.parseInt(txtPatientId.getText()),txtDoctorId.getText(), Date.valueOf(txtDate.getText()), Integer.parseInt(txtCheckInNo.getText()), txtTime.getText());

        try {
            String response = appointmentController.updateAppointment(appointmentDTO);
            loadData();
            clearFields();

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
            String response = appointmentController.deleteAppointment(Integer.parseInt(txtAppointmentId.getText()));
            loadData();
            clearFields();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment");
            alert.setHeaderText("APPOINTMENT DELETE");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentController = new AppointmentController();

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

    private void clearFields(){
        txtPatientId.clear();
        txtDoctorId.clear();
        txtDate.clear();
        txtCheckInNo.clear();
        txtAppointmentId.clear();
        txtTime.clear();
    }
}
