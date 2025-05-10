package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.medease.dto.PatientDTO;
import lk.ijse.medease.dto.tm.PatientTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceptionPatientController implements Initializable {

    private final String namePattern = "^[A-Za-z ]+$";
    private final String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private final String contactPattern = "^[0-9]{10}$";
    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private PatientController patientController;

    @FXML
    private TableColumn<PatientTM, String> colAllergies;

    @FXML
    private TableColumn<PatientTM, String> colContact;

    @FXML
    private TableColumn<PatientTM, Date> colDOB;

    @FXML
    private TableColumn<PatientTM, String> colEmail;

    @FXML
    private TableColumn<PatientTM, String> colName;

    @FXML
    private TableColumn<PatientTM, String> colPID;

    @FXML
    private TableView<PatientTM> tblPatient;

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
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        PatientTM patientTM = tblPatient.getSelectionModel().getSelectedItem();
        if (patientTM != null) {
            txtPatientId.setText(patientTM.getPatientId());
            txtName.setText(patientTM.getName());
            txtBirthDate.setText(String.valueOf(patientTM.getBirthDate()));
            txtContact.setText(patientTM.getContact());
            txtEmail.setText(patientTM.getEmail());
            txtAllergies.setText(patientTM.getAllergies());

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void nameOnKeyReleased(KeyEvent event) {
        String name = txtName.getText();
        boolean isNameValid = name.matches(namePattern);

        txtName.setStyle(txtName.getStyle() + "-fx-text-fill: white;");
        if (!isNameValid) {
            txtName.setStyle(txtName.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void birthDateOnKeyReleased(KeyEvent event) {
        txtBirthDate.setStyle(txtName.getStyle() + "-fx-text-fill: white;");
        if (!(txtBirthDate.getText().matches(datePattern))) {
            txtBirthDate.setStyle(txtName.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void contactOnKeyReleased(KeyEvent event) {
        txtContact.setStyle(txtName.getStyle() + "-fx-text-fill: white;");
        if (!(txtContact.getText().matches(contactPattern))) {
            txtContact.setStyle(txtName.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void emailOnKeyReleased(KeyEvent event) {
        txtEmail.setStyle(txtName.getStyle() + "-fx-text-fill: white;");
        if (!(txtEmail.getText().matches(emailPattern))) {
            txtEmail.setStyle(txtName.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtPatientId.getText().isEmpty() || txtName.getText().isEmpty() || txtBirthDate.getText().isEmpty() || txtContact.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("MISSING FIELDS");
            alert.setContentText("Please enter all the details");
            alert.showAndWait();

        }else if (txtName.getText().matches(namePattern) && txtBirthDate.getText().matches(datePattern) && txtContact.getText().matches(contactPattern) && txtEmail.getText().matches(emailPattern)) {
            addPatient();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID");
            alert.setHeaderText("INVALID FIELDS");
            alert.setContentText("Please check the details");
            alert.showAndWait();
        }
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

        PatientDTO patientDTO = new PatientDTO(txtPatientId.getText(), txtName.getText(), Date.valueOf(txtBirthDate.getText()),txtContact.getText(),txtEmail.getText(),txtAllergies.getText());

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
            String response = patientController.deletePatient(txtPatientId.getText());

            loadData();
            clearFields();
            setPatientId();
            disableButtons();

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
        PatientDTO patientDTO = new PatientDTO(txtPatientId.getText(), txtName.getText(), Date.valueOf(txtBirthDate.getText()),txtContact.getText(),txtEmail.getText(),txtAllergies.getText());

        try {
            String response = patientController.updatePatient(patientDTO);

            loadData();
            clearFields();
            setPatientId();
            disableButtons();

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
        disableButtons();

        patientController = new PatientController();

        setPatientId();

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

            ObservableList<PatientTM> patientTMObList = FXCollections.observableArrayList();

            for (PatientDTO dto : patientDTOs) {
                PatientTM patientTM = new PatientTM(dto.getPatientId(), dto.getName(), dto.getBirthDate(), dto.getContact(), dto.getEmail(), dto.getAllergies());
                patientTMObList.add(patientTM);
            }

            tblPatient.setItems(patientTMObList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setPatientId(){
        try {
            String patientId = patientController.getNextId();
            txtPatientId.setText(patientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableButtons() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
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
