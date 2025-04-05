package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ReceptionAppointmentController implements Initializable {
    private AppointmentController appointmentController;

    @FXML
    private TableView<?> tblAppointment;

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
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnAppCheckOnAction(ActionEvent event) {
        checkNumber();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

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
        appointmentController = new AppointmentController();
    }
}
