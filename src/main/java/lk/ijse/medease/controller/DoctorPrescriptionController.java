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
import javafx.stage.Stage;
import lk.ijse.medease.dto.CheckInDTO;
import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorPrescriptionController implements Initializable {

    public static DoctorPrescriptionController controller;

    private MedicineController medicineController;
    private int medicineId;

    private PrescriptionController prescriptionController;

    private ArrayList<PrescriptionMedicineDTO> presMedDtoArray;

    private ObservableList<PrescriptionMedicineDTO> presMedList ;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colDosage;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colFrequency;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colDuration;


    @FXML
    private TableColumn<PrescriptionMedicineDTO, Number> colMedId;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colName;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, Number> colPresId;

    @FXML
    private ImageView imgAlert;

    @FXML
    private ImageView imgAvailability;

    @FXML
    private TableView<PrescriptionMedicineDTO> tblPresMed;

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
    private TextField txtNotes;

    @FXML
    public TextField txtPID;


    @FXML
    private TextField txtPrescriptionId;

    @FXML
    public Label txtPatientName;

    @FXML
    void btnAddMedOnAction(ActionEvent event) {
        try {
            medicineId = medicineController.getMedicineIdByMedicineName(txtName.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (medicineId != -1) { // adding medicine that are available at inventory
            PrescriptionMedicineDTO presMedDTO = new PrescriptionMedicineDTO(Integer.parseInt(txtPrescriptionId.getText()), medicineId, txtName.getText(), txtDosage.getText(), txtFrequency.getText(), txtDuration.getText()); // here
            presMedList.add(presMedDTO); //for the table

            addToTable();

            presMedDtoArray.add(presMedDTO); // for the database
            clearFields();
        } else { // adding medicine that unavailable at inventory
            PrescriptionMedicineDTO presMedDTO = new PrescriptionMedicineDTO(Integer.parseInt(txtPrescriptionId.getText()), txtName.getText(), txtDosage.getText(), txtFrequency.getText(), txtDuration.getText());
            presMedList.add(presMedDTO);

            addToTable();

            presMedDtoArray.add(presMedDTO);
            clearFields();
        }
    }

    @FXML
    void checkHistoryOnAction(ActionEvent event) {
        try {
            if (!(prescriptionController.getPatientHistory(Integer.parseInt(txtPID.getText())).isEmpty()) ) {
                imgAlert.setVisible(true);
            } else {
                imgAlert.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void checkAvailabilityOnAction(ActionEvent event) {

        try {
            int medId = medicineController.getMedicineIdByMedicineName(txtName.getText());

            if (medId != -1){
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
        addPrescription();
        fullyClearFields();
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
        PrescriptionMedicineDTO selectedDTO = tblPresMed.getSelectionModel().getSelectedItem();

        if (selectedDTO != null) {
            presMedList.remove(selectedDTO); // removes from table view
            presMedDtoArray.remove(selectedDTO); // removes from database
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

        PrescriptionDTO prescriptionDTO = new PrescriptionDTO(doctorId, Integer.parseInt(txtPID.getText()), Integer.parseInt(txtAge.getText()), txtDiagnosis.getText(), txtNotes.getText());

        try {
            String response = prescriptionController.addPrescription(prescriptionDTO, presMedDtoArray, checkInNo, doctorId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Prescription");
            alert.setHeaderText("SUCCESS");
            alert.setContentText(response);
            alert.showAndWait();

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
        txtNotes.clear();
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
        txtNotes.clear();
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
