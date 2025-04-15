package lk.ijse.medease.controller;

import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;
import lk.ijse.medease.model.PrescriptionModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionController {
    private PrescriptionModel prescriptionModel;

    public PrescriptionController() {
        prescriptionModel = new PrescriptionModel();
    }

    public String addPrescription(PrescriptionDTO prescriptionDTO, ArrayList<PrescriptionMedicineDTO> presMedArray) throws Exception {
        String response = prescriptionModel.addPrescription(prescriptionDTO, presMedArray);
        return response;
    }

    public ArrayList<PrescriptionDTO> getPatientHistory(int patientId) throws ClassNotFoundException, SQLException {
        ArrayList<PrescriptionDTO> response = prescriptionModel.getPatientHistory(patientId);
        return response;
    }
}
