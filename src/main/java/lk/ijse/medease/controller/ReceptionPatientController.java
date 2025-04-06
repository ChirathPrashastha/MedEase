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
import lk.ijse.medease.dto.PatientDTO;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceptionPatientController implements Initializable {

    private PatientController patientController;

    @FXML
    private TableColumn<PatientDTO, String> colAllergies;

    @FXML
    private TableColumn<PatientDTO, String> colContact;

    @FXML
    private TableColumn<PatientDTO, Date> colDOB;

    @FXML
    private TableColumn<PatientDTO, String> colEmail;

    @FXML
    private TableColumn<PatientDTO, String> colName;

    @FXML
    private TableColumn<PatientDTO, Number> colPID;

    @FXML
    private TableView<PatientDTO> tblPatient;

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
            loadData();
            clearFields();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Patient");
            alert.setContentText(response);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Patient");
            alert.setHeaderText("ADD PATIENT FAILED");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void deletePatient() {
        try {
            String response = patientController.deletePatient(Integer.parseInt(txtPatientId.getText()));
            loadData();
            clearFields();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Patient");
            alert.setHeaderText("DELETE PATIENT");
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
            loadData();
            clearFields();
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

        colPID.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAllergies.setCellValueFactory(new PropertyValueFactory<>("allergies"));

        loadData();
    }

    private void loadData() {
        try {
            ArrayList<PatientDTO> patientDTOs = patientController.getAllPatients();
            //System.out.println(patientDTOs);
            ObservableList<PatientDTO> allPatients = FXCollections.observableArrayList(patientDTOs);
            tblPatient.setItems(allPatients);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearFields(){
        txtPatientId.clear();
        txtName.clear();
        txtBirthDate.clear();
        txtContact.clear();
        txtEmail.clear();
        txtAllergies.clear();
    }
}
