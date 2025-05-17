package lk.ijse.medease.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.medease.dto.JobRole;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReceptionHomeController implements Initializable {

    private final AttendanceController attendanceController = new AttendanceController();
    private final AppointmentController appointmentController = new AppointmentController();

    @FXML
    private AnchorPane homeAnc;

    @FXML
    private Label lblAppointmentCount;

    @FXML
    private Label lblDoctorCount;

    @FXML
    private Label lblNurseCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int doctorCount = attendanceController.getTodayAttendanceCountByJobRole(JobRole.DOCTOR);
            int nurseCount = attendanceController.getTodayAttendanceCountByJobRole(JobRole.NURSE);
            int appointmentCount = appointmentController.getTodayAppointmentCount();

            if (doctorCount == -1 || nurseCount == -1 || appointmentCount == -1) {
                new Alert(Alert.AlertType.ERROR, "Unable to calculate attendance").showAndWait();
            }else {
                lblDoctorCount.setText(String.valueOf(doctorCount));
                lblNurseCount.setText(String.valueOf(nurseCount));
                lblAppointmentCount.setText(String.valueOf(appointmentCount));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
