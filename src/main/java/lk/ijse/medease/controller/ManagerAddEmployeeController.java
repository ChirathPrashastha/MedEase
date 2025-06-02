package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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

    private final String contactPattern = "^[0-9]{10}$";
    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private final String namePattern = "^[A-Za-z ]+$";
    private final String usernamePattern = "^[A-Za-z0-9]{8}$";
    private final String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8}$";

    private boolean isFieldsValid = true;

    private EmployeeController employeeController;

    private JobRole jobRole;

    @FXML
    private Button btnAdd;

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
    void contactOnKeyReleased(KeyEvent event) {
        txtContact.setStyle(txtContact.getStyle() + "-fx-text-fill: white;");
        if (!(txtContact.getText().matches(contactPattern))) {
            txtContact.setStyle(txtContact.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void emailOnKeyReleased(KeyEvent event) {
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-text-fill: white;");
        if (!(txtEmail.getText().matches(emailPattern))) {
            txtEmail.setStyle(txtEmail.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void nameOnKeyReleased(KeyEvent event) {
        txtName.setStyle(txtName.getStyle() + "-fx-text-fill: white;");
        if (!(txtName.getText().matches(namePattern))) {
            txtName.setStyle(txtName.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void usernameOnKeyReleased(KeyEvent event) {
        txtUsername.setStyle(txtUsername.getStyle() + "-fx-text-fill: white;");
        if (!(txtUsername.getText().matches(usernamePattern))) {
            txtUsername.setStyle(txtUsername.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void passwordOnKeyReleased(KeyEvent event) {
        txtPassword.setStyle(txtPassword.getStyle() + "-fx-text-fill: white;");
        if (!(txtPassword.getText().matches(passwordPattern))) {
            txtPassword.setStyle(txtPassword.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (isFieldsValid) {
            addEmployee();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please check the fields").showAndWait();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearInputs();
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

        generateEmployeeId();
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
                generateEmployeeId();

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
                generateEmployeeId();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void generateEmployeeId() {
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

    private void clearFields() {
        lblEmployeeId.setText("");
        clearInputs();
    }

    private void clearInputs() {
        txtName.clear();
        txtPassword.clear();
        txtUsername.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();

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