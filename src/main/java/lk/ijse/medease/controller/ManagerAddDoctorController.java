package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.Shift;
import lk.ijse.medease.model.DoctorModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerAddDoctorController implements Initializable {

    public static ManagerAddDoctorController managerAddDoctorController;

    private DoctorModel doctorModel;

    public DoctorDTO doctorDTO;

    @FXML
    private ComboBox<Shift> cbShift;

    @FXML
    private Label lblDoctorId;

    @FXML
    public Label lblEmployeeId;

    @FXML
    private TextField txtHospital;

    @FXML
    private TextField txtRegistNo;

    @FXML
    private TextField txtSpeciality;

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        doctorDTO = new DoctorDTO(lblDoctorId.getText(), lblEmployeeId.getText(), txtSpeciality.getText(), txtRegistNo.getText(), txtHospital.getText(), cbShift.getSelectionModel().getSelectedItem());

        if (doctorDTO != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Doctor Details");
            alert.setHeaderText("SUCCESS");
            alert.showAndWait();

            clearFields();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR");
            alert.setContentText("Recheck the information");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAddDoctorController = this;
        doctorModel = new DoctorModel();

        cbShift.getItems().addAll(Shift.values());

        try {
            String doctorId = doctorModel.getNextId();
            lblDoctorId.setText(doctorId);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to generate doctor ID");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        lblDoctorId.setText("");
        lblEmployeeId.setText("");
        txtHospital.setText("");
        txtRegistNo.setText("");
        txtSpeciality.setText("");

        cbShift.getSelectionModel().clearSelection();
    }
}