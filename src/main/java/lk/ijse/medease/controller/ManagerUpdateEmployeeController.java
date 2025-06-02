package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
import java.util.ResourceBundle;

public class ManagerUpdateEmployeeController implements Initializable {

    private final String employeeIdPattern = "^E\\d{4}$";
    private final String contactPattern = "^[0-9]{10}$";
    private final String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private final String namePattern = "^[A-Za-z ]+$";
    private final String usernamePattern = "^[A-Za-z0-9]{8}$";
    private final String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8}$";

    private boolean isFieldsValid = true;

    private DoctorController doctorController;
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
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRecruitedDate;

    @FXML
    private TextField txtUsername;


    @FXML
    void employeeIdOnKeyReleased(KeyEvent event) {
        txtEmployeeId.setStyle(txtEmployeeId.getStyle() + "-fx-text-fill: white;");
        if (!(txtEmployeeId.getText().matches(employeeIdPattern))) {
            txtEmployeeId.setStyle(txtEmployeeId.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

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
    void recruitedDateOnKeyReleased(KeyEvent event) {
        txtRecruitedDate.setStyle(txtRecruitedDate.getStyle() + "-fx-text-fill: white;");
        if (!(txtRecruitedDate.getText().matches(datePattern))) {
            txtRecruitedDate.setStyle(txtRecruitedDate.getStyle() + "-fx-text-fill: red;");
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
    void btnUpdateOnAction(ActionEvent event) {
        if (isFieldsValid) {
            updateEmployee();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please check the fields").showAndWait();
        }
    }

    @FXML
    void radioBtnDoctorOnAction(ActionEvent event) {
        jobRole = JobRole.DOCTOR;

        btnManager.setDisable(true);
        btnNurse.setDisable(true);
        btnReceptionist.setDisable(true);

        try {
            loadDoctorDetailsWindow();
            ManagerAddDoctorController.managerAddDoctorController.lblEmployeeId.setText(txtEmployeeId.getText());
            String doctorId = doctorController.getDoctorId(txtEmployeeId.getText());
            if (doctorId == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Employee ID does not match with Doctor ID");
                alert.showAndWait();

                ManagerAddDoctorController.managerAddDoctorController.lblDoctorId.setText("INVALID");
            }else {
                ManagerAddDoctorController.managerAddDoctorController.lblDoctorId.setText(doctorId);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("System Error");
            alert.setContentText("Can not load doctor details");
            alert.showAndWait();
        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
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

    private void loadDoctorDetailsWindow() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/ManagerAddDoctor.fxml"));
        Stage stage = new Stage();

        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("Doctor Details");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doctorController = new DoctorController();
        employeeController = new EmployeeController();
    }

    private void updateEmployee() {

        EmployeeDTO employeeDTO = new EmployeeDTO(txtEmployeeId.getText(), txtName.getText(), jobRole, Date.valueOf(txtRecruitedDate.getText()), txtAddress.getText(), txtContact.getText(), txtEmail.getText());
        UserDTO userDTO = new UserDTO(txtEmployeeId.getText(), txtPassword.getText(), txtUsername.getText());

        if (jobRole != JobRole.DOCTOR) {
            try {
                String response = employeeController.updateEmployee(employeeDTO, userDTO);
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
                String response = employeeController.updateEmployee(employeeDTO, doctorDTO, userDTO);
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

        txtEmployeeId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtRecruitedDate.clear();

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