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

    public String addPrescription(PrescriptionDTO prescriptionDTO, ArrayList<PrescriptionMedicineDTO> presMedArray, int checkInNo, String doctorId) throws Exception {
        String response = prescriptionModel.addPrescription(prescriptionDTO, presMedArray, checkInNo, doctorId);
        return response;
    }

    public ArrayList<PrescriptionDTO> getPatientHistory(String patientId) throws ClassNotFoundException, SQLException {
        ArrayList<PrescriptionDTO> response = prescriptionModel.getPatientHistory(patientId);
        return response;
    }

    public ArrayList<PrescriptionMedicineDTO> getPrescriptionHistory(String patientId) throws ClassNotFoundException, SQLException {
        ArrayList<PrescriptionMedicineDTO> response = prescriptionModel.getPrescriptionHistory(patientId);
        return response;
    }

    public ArrayList<PrescriptionMedicineDTO> getPrescriptionById(String prescriptionId) throws ClassNotFoundException, SQLException {
        ArrayList<PrescriptionMedicineDTO> response = prescriptionModel.getPrescriptionById(prescriptionId);
        return response;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        return prescriptionModel.getNextId();
    }
}
