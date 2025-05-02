package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.EmployeeDTO;
import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManagerAddEmployeeController implements Initializable {

    private EmployeeController employeeController;

    private JobRole jobRole;

    @FXML
    private RadioButton btnDoctor;

    @FXML
    private RadioButton btnManager;

    @FXML
    private RadioButton btnNurse;

    @FXML
    private RadioButton btnReceptionist;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        addEmployee();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void radioBtnDoctorOnAction(ActionEvent event) {
        jobRole = JobRole.DOCTOR;

        btnManager.setDisable(true);
        btnNurse.setDisable(true);
        btnReceptionist.setDisable(true);

        try {
            loadDoctorDetailsWindow();
            ManagerAddDoctorController.managerAddDoctorController.lblEmployeeId.setText(lblEmployeeId.getText());

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Can not load doctor details");
            alert.showAndWait();
        }
    }

    @FXML
    void radioBtnManagerOnAction(ActionEvent event) {
        jobRole = JobRole.MANAGER;

        btnDoctor.setDisable(true);
        btnNurse.setDisable(true);
        btnReceptionist.setDisable(true);
    }

    @FXML
    void radioBtnNurseOnAction(ActionEvent event) {
        jobRole = JobRole.NURSE;

        btnDoctor.setDisable(true);
        btnManager.setDisable(true);
        btnReceptionist.setDisable(true);
    }

    @FXML
    void radioBtnReceptionistOnAction(ActionEvent event) {
        jobRole = JobRole.RECEPTIONIST;

        btnDoctor.setDisable(true);
        btnManager.setDisable(true);
        btnNurse.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeController = new EmployeeController();

        try {
            String employeeId = employeeController.getNextId();
            lblEmployeeId.setText(employeeId);
        }catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Failed to Generate Employee ID");
            alert.showAndWait();
        }
    }

    private void loadDoctorDetailsWindow() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/ManagerAddDoctor.fxml"));
        Stage stage = new Stage();

        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("Doctor Details");
        stage.show();
    }

    private void addEmployee() {

        LocalDate recruitedDate = LocalDate.now();

        EmployeeDTO employeeDTO = new EmployeeDTO(lblEmployeeId.getText(), txtName.getText(), jobRole, Date.valueOf(recruitedDate), txtAddress.getText(), txtContact.getText(), txtEmail.getText());
        UserDTO userDTO = new UserDTO(lblEmployeeId.getText(), txtPassword.getText(), txtUsername.getText());

        if (jobRole != JobRole.DOCTOR) {
            try {
                String response = employeeController.addEmployee(employeeDTO, userDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait();

                clearFields();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText(e.getMessage());
            }
        }else {
            DoctorDTO doctorDTO = ManagerAddDoctorController.managerAddDoctorController.doctorDTO;

            try {
                String response = employeeController.addEmployee(employeeDTO, doctorDTO, userDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait();

                clearFields();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void clearFields() {

        lblEmployeeId.setText("");
        txtName.setText("");
        txtPassword.setText("");
        txtUsername.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");

        btnDoctor.setDisable(false);
        btnManager.setDisable(false);
        btnNurse.setDisable(false);
        btnReceptionist.setDisable(false);

        btnDoctor.setSelected(false);
        btnManager.setSelected(false);
        btnNurse.setSelected(false);
        btnReceptionist.setSelected(false);

        jobRole = null;
    }
}