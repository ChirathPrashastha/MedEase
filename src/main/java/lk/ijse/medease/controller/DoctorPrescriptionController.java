package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;
import lk.ijse.medease.dto.tm.PrescriptionMedicineTM;
import lk.ijse.medease.util.EmailSender;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorPrescriptionController implements Initializable {

    private final EmailSender emailSender = new EmailSender();

    public static DoctorPrescriptionController controller;

    private MedicineController medicineController;
    private String medicineId;

    private PrescriptionController prescriptionController;

    private PatientController patientController;

    private ArrayList<PrescriptionMedicineDTO> presMedDtoArray;

    private ObservableList<PrescriptionMedicineTM> presMedList ;

    private final String patientIdPattern = "^P\\d{4}$";

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colDosage;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colFrequency;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colDuration;


    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colMedId;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colName;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colPresId;

    @FXML
    private ImageView imgAlert;

    @FXML
    private ImageView imgAvailability;

    @FXML
    private TableView<PrescriptionMedicineTM> tblPresMed;

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
    private TextField txtDuration;


    @FXML
    private TextField txtName;

    @FXML
    public TextField txtPID;


    @FXML
    private TextField txtPrescriptionId;

    @FXML
    public Label txtPatientName;

    @FXML
    void checkHistoryOnKeyReleased(KeyEvent event) {
        txtPID.setStyle(txtPID.getStyle() + "-fx-text-fill: white;");
        if (!(txtPID.getText().matches(patientIdPattern))) {
            txtPID.setStyle(txtPID.getStyle() + "-fx-text-fill: red;");
        }else {
            try {
                if (!(prescriptionController.getPatientHistory(txtPID.getText()).isEmpty()) ) {
                    imgAlert.setVisible(true);
                } else {
                    imgAlert.setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void checkAvailabilityOnKeyReleased(KeyEvent event) {
        try {
            String medId = medicineController.getMedicineIdByMedicineName(txtName.getText());

            if (medId != null){
                txtAvailability.setText("Available");
                txtAvailability.setStyle("-fx-text-fill: #8fce00");
                Image availableImg = new Image(getClass().getResourceAsStream("/images/yes.png"));
                imgAvailability.setImage(availableImg);
            } else {
                txtAvailability.setText("Unavailable");
                txtAvailability.setStyle("-fx-text-fill: red");
                Image unavailableImg = new Image(getClass().getResourceAsStream("/images/no.png"));
                imgAvailability.setImage(unavailableImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddMedOnAction(ActionEvent event) {

        try {
            if (patientController.searchPatient(txtPID.getText()) == null){
                new Alert(Alert.AlertType.ERROR, "Patient not found!", ButtonType.OK).showAndWait();
                return;
            }

            medicineId = medicineController.getMedicineIdByMedicineName(txtName.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (medicineId != null) { // adding medicine that are available at inventory
            PrescriptionMedicineTM prescriptionMedicineTM = new PrescriptionMedicineTM(txtPrescriptionId.getText(), medicineId, txtName.getText(), txtDosage.getText(), txtFrequency.getText(), txtDuration.getText()); // here
            presMedList.add(prescriptionMedicineTM); //for the table

            addToTable();

            PrescriptionMedicineDTO presMedDTO = new PrescriptionMedicineDTO(txtPrescriptionId.getText(), medicineId, txtName.getText(), txtDosage.getText(), txtFrequency.getText(), txtDuration.getText());
            presMedDtoArray.add(presMedDTO); // for the database
            clearFields();
        } else { // adding medicine that unavailable at inventory
            PrescriptionMedicineTM prescriptionMedicineTM = new PrescriptionMedicineTM(txtPrescriptionId.getText(), txtName.getText(), txtDosage.getText(), txtFrequency.getText(), txtDuration.getText());
            presMedList.add(prescriptionMedicineTM);

            addToTable();

            PrescriptionMedicineDTO presMedDTO = new PrescriptionMedicineDTO(txtPrescriptionId.getText(), txtName.getText(), txtDosage.getText(), txtFrequency.getText(), txtDuration.getText());
            presMedDtoArray.add(presMedDTO);
            clearFields();
        }
    }

    @FXML
    void btnRXOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/PatientHistory.fxml"));
        Stage stage = new Stage();

        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("Patient History");
        stage.show();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (presMedDtoArray.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Unable to add the prescription").showAndWait();
        }else {
            addPrescription();
            fullyClearFields();
        }
    }

    @FXML
    void btnMedicineOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MedicineList.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Medicine List");
        stage.show();
    }

    @FXML
    void btnDeleteRowOnAction(ActionEvent event) {
        PrescriptionMedicineTM selectedTM = tblPresMed.getSelectionModel().getSelectedItem();

        if (selectedTM != null) {
            presMedList.remove(selectedTM); // removes from table view

            for (int i = 0; i < presMedDtoArray.size(); i++) {
                PrescriptionMedicineDTO dto = presMedDtoArray.get(i);
                if ((selectedTM.getPrescriptionId().equals(dto.getPrescriptionId())) && (selectedTM.getMedicineId().equals(dto.getMedicineId()))) {
                    presMedDtoArray.remove(i);
                    break;
                }
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("FAILED");
            alert.setContentText("Please select a row to delete");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;
        prescriptionController = new PrescriptionController();
        patientController = new PatientController();

        try {
            String prescriptionId = prescriptionController.getNextId();
            txtPrescriptionId.setText(prescriptionId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        medicineController = new MedicineController();

        presMedList = FXCollections.observableArrayList();
        presMedDtoArray = new ArrayList<>();

        colPresId.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        colMedId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

    }

    private void addToTable(){
        tblPresMed.setItems(presMedList);
    }

    private void addPrescription() {
        String doctorId = DoctorAppointmentController.doctorAppointmentController.doctorId;
        int checkInNo = DoctorAppointmentController.doctorAppointmentController.checkInNo;

        PrescriptionDTO prescriptionDTO = new PrescriptionDTO(txtPrescriptionId.getText(), doctorId, txtPID.getText(), Integer.parseInt(txtAge.getText()), txtDiagnosis.getText(), txtAID.getText());

        try {
            String response = prescriptionController.addPrescription(prescriptionDTO, presMedDtoArray, checkInNo, doctorId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Prescription");
            alert.setHeaderText("SUCCESS");
            alert.setContentText(response);
            alert.showAndWait();

            emailSender.sendCheckOutEmail(doctorId); //Sending emails for rest of the patients

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Prescription");
            alert.setHeaderText("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void clearFields(){
        txtName.clear();
        txtDosage.clear();
        txtFrequency.clear();
        txtDuration.clear();
        txtAvailability.setText("");
        imgAvailability.setImage(null);
    }

    private void fullyClearFields() {
        txtAID.clear();
        txtPrescriptionId.clear();
        txtPID.clear();
        txtDiagnosis.clear();
        txtName.clear();
        txtDosage.clear();
        txtFrequency.clear();
        txtDuration.clear();
        txtPatientName.setText("Empty");
        txtAge.setText("Empty");
        txtAllergies.setText("Empty");
        imgAlert.setVisible(false);
        txtAvailability.setText("");
        imgAvailability.setImage(null);
        presMedList.clear();
        presMedDtoArray.clear();
    }

}
