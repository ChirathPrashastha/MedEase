package lk.ijse.medease.controller;

import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.model.PrescriptionModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionController {
    PrescriptionModel prescriptionModel;

    public PrescriptionController () {
        prescriptionModel = new PrescriptionModel();
    }

    public String addPrescription(PrescriptionDTO prescriptionDTO) throws ClassNotFoundException, SQLException {
        String response = prescriptionModel.addPrescription(prescriptionDTO);
        return response;
    }

    public String updatePrescription(PrescriptionDTO prescriptionDTO) throws ClassNotFoundException, SQLException {
        String response = prescriptionModel.updatePrescription(prescriptionDTO);
        return response;
    }

    public String deletePrescription(int prescriptionId) throws ClassNotFoundException, SQLException {
        String response = prescriptionModel.deletePrescription(prescriptionId);
        return response;
    }

    public PrescriptionDTO searchPrescription(int prescriptionId) throws ClassNotFoundException, SQLException {
        PrescriptionDTO response = prescriptionModel.searchPrescription(prescriptionId);
        return response;
    }

    public ArrayList<PrescriptionDTO> getAllPrescription() throws ClassNotFoundException, SQLException {
        ArrayList<PrescriptionDTO> prescriptionDTOs = prescriptionModel.getAllPrescription();
        return prescriptionDTOs;
    }
}
