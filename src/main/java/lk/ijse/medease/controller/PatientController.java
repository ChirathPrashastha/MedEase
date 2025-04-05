package lk.ijse.medease.controller;

import lk.ijse.medease.dto.PatientDTO;
import lk.ijse.medease.model.PatientModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class PatientController {
    private PatientModel patientModel;

    public PatientController() {
        this.patientModel = new PatientModel();
    }

    public String addPatient(PatientDTO patientDTO) throws ClassNotFoundException, SQLException {
        String response = patientModel.addPatient(patientDTO);
        return response;
    }

    public String updatePatient(PatientDTO patientDTO) throws ClassNotFoundException, SQLException {
        String response = patientModel.updatePatient(patientDTO);
        return response;
    }

    public String deletePatient(String patientId) throws ClassNotFoundException, SQLException {
        String response = patientModel.deletePatient(patientId);
        return response;
    }

    public PatientDTO searchPatient(String patientId) throws ClassNotFoundException, SQLException {
        PatientDTO patientDTO = patientModel.searchPatient(patientId);
        return patientDTO;
    }

    public ArrayList<PatientDTO> getAllPatients() throws ClassNotFoundException, SQLException {
        ArrayList<PatientDTO> patientDTOs = patientModel.getAllPatients();
        return patientDTOs;
    }
}
