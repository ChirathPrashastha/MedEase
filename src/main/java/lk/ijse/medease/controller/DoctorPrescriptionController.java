package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;

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
    private TextField txtName;

    @FXML
    private TextField txtNotes;

    @FXML
    public TextField txtPID;

    @FXML
    public Label txtPatientName;

    @FXML
    void btnAddMedOnAction(ActionEvent event) {
        try {
            medicineId = medicineController.getMedicineIdByMedicineName(txtName.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!txtName.getText().equals("")) {
            PrescriptionMedicineDTO presMedDTO = new PrescriptionMedicineDTO(Integer.parseInt(txtPID.getText()), medicineId, txtName.getText(), txtDosage.getText(), txtFrequency.getText());
            presMedList.add(presMedDTO); //for the table

            addToTable();

            presMedDtoArray.add(presMedDTO); // for the database
        }
    }

    @FXML
    void checkHistoryOnAction(ActionEvent event) {
        try {
            if (prescriptionController.getPatientHistory(Integer.parseInt(txtPID.getText())) != null ) {
                imgAlert.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRXOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        addPrescription();
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {

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
    }

    private void addToTable(){
        tblPresMed.setItems(presMedList);
    }

    private void addPrescription() {
        String doctorId = DoctorAppointmentController.doctorAppointmentController.doctorId;
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO(doctorId, Integer.parseInt(txtPID.getText()), Integer.parseInt(txtAge.getText()), txtDiagnosis.getText(), txtNotes.getText());

        try {
            String response = prescriptionController.addPrescription(prescriptionDTO, presMedDtoArray);
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


}
