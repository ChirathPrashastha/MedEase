package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lk.ijse.medease.dto.PatientDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorPrescriptionController implements Initializable {

    private PatientController patientController;

    public static DoctorPrescriptionController controller;

    @FXML
    private TableColumn<?, ?> colDosage;

    @FXML
    private TableColumn<?, ?> colFrequency;

    @FXML
    private TableColumn<?, ?> colMedId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPresId;

    @FXML
    private ImageView imgAlert;

    @FXML
    private ImageView imgAvailability;

    @FXML
    private TableView<?> tblPresMed;

    @FXML
    public TextField txtAID;

    @FXML
    public Label txtAge;

    @FXML
    public Label txtAllergies;

    @FXML
    private Label txtAvailability;

    @FXML
    private TextField txtDiagnosis;

    @FXML
    private TextField txtDosage;

    @FXML
    private TextField txtFrequency;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNotes;

    @FXML
    public TextField txtPID;

    @FXML
    public Label txtPatientName;

    @FXML
    void btnAddMedOnAction(ActionEvent event) {

    }

    @FXML
    void btnRXOnAction(ActionEvent event) {

    }

    @FXML
    void checkHistoryOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;

    }

}
