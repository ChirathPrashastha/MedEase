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
import lk.ijse.medease.dto.tm.PrescriptionMedicineTM;
import lk.ijse.medease.dto.tm.PrescriptionTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientHistoryController implements Initializable {
    private PrescriptionController prescriptionController;

    @FXML
    private TableColumn<PrescriptionMedicineTM, Number> colPrescriptionId;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colDosage;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colFrequency;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colName;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colDuration;

    @FXML
    private TableView<PrescriptionMedicineTM> tblPresMed;



    @FXML
    private TableColumn<PrescriptionTM, String> colDiagnosis;

    @FXML
    private TableColumn<PrescriptionTM, String> colNotes;

    @FXML
    private TableColumn<PrescriptionTM, Number> colPID;

    @FXML
    private TableColumn<PrescriptionTM, Number> colPresID;

    @FXML
    private TableView<PrescriptionTM> tblPatientHistory;

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

            ObservableList<PrescriptionTM> historyObList = FXCollections.observableArrayList();

            for (PrescriptionDTO dto : prescriptionDTOs){
                PrescriptionTM prescriptionTM = new PrescriptionTM(dto.getPrescriptionId(),dto.getPatientId(),dto.getDiagnosis(), dto.getNotes());
                historyObList.add(prescriptionTM);
            }

            tblPatientHistory.setItems(historyObList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        colPrescriptionId.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        try {
            ArrayList<PrescriptionMedicineDTO> prescriptionMedicineDTOs = prescriptionController.getPrescriptionHistory(patientId);
            ObservableList<PrescriptionMedicineTM> presMedObList = FXCollections.observableArrayList();

            for (PrescriptionMedicineDTO dto : prescriptionMedicineDTOs){
                PrescriptionMedicineTM prescriptionMedicineTM = new PrescriptionMedicineTM(dto.getPrescriptionId(), dto.getName(), dto.getDosage(), dto.getFrequency(), dto.getDuration());
                presMedObList.add(prescriptionMedicineTM);
            }

            tblPresMed.setItems(presMedObList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
