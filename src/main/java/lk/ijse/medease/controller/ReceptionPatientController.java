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
import javafx.scene.shape.Circle;
import lk.ijse.medease.dto.PatientDTO;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ReceptionPatientController implements Initializable {

    private ObservableList<PatientDTO> allPatients = FXCollections.observableArrayList();

    private PatientController patientController;

    @FXML
    private TableColumn<?, ?> colAllergies;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPID;

    @FXML
    private TableView<?> tblPatient;

    @FXML
    private TextField txtAllergies;

    @FXML
    private TextField txtBirthDate;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPatientId;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        addPatient();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deletePatient();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        updatePatient();
    }

    private void addPatient() {

        PatientDTO patientDTO = new PatientDTO(txtName.getText(), Date.valueOf(txtBirthDate.getText()),txtContact.getText(),txtEmail.getText(),txtAllergies.getText());

        try {
            String response = patientController.addPatient(patientDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Patient");
            alert.setContentText(response);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Patient");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void deletePatient() {
        try {
            String response = patientController.deletePatient(txtPatientId.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Patient");
            alert.setContentText(response);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Patient");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void updatePatient() {
        PatientDTO patientDTO = new PatientDTO(txtName.getText(), Date.valueOf(txtBirthDate.getText()),txtContact.getText(),txtEmail.getText(),txtAllergies.getText());

        try {
            String response = patientController.updatePatient(patientDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Patient");
            alert.setContentText(response);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Patient");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientController = new PatientController();

    }
}
