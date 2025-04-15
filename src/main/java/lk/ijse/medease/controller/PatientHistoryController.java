package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientHistoryController implements Initializable {
    private PrescriptionController prescriptionController;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, Number> colPrescriptionId;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colDosage;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colFrequency;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colName;

    @FXML
    private TableView<PrescriptionMedicineDTO> tblPresMed;



    @FXML
    private TableColumn<PrescriptionDTO, String> colDiagnosis;

    @FXML
    private TableColumn<PrescriptionDTO, String> colNotes;

    @FXML
    private TableColumn<PrescriptionDTO, Number> colPID;

    @FXML
    private TableColumn<PrescriptionDTO, Number> colPresID;

    @FXML
    private TableView<PrescriptionDTO> tblPatientHistory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prescriptionController = new PrescriptionController();

        colPresID.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        colPID.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        int patientId = Integer.parseInt(DoctorPrescriptionController.controller.txtPID.getText());

        try {
            ArrayList<PrescriptionDTO> prescriptionDTOs = prescriptionController.getPatientHistory(patientId);
            ObservableList<PrescriptionDTO> historyList = FXCollections.observableList(prescriptionDTOs);
            tblPatientHistory.setItems(historyList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        colPrescriptionId.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        try {
            ArrayList<PrescriptionMedicineDTO> prescriptionMedicineDTOs = prescriptionController.getPrescriptionHistory(patientId);
            ObservableList<PrescriptionMedicineDTO> presMedList = FXCollections.observableList(prescriptionMedicineDTOs);
            tblPresMed.setItems(presMedList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
