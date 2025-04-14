package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PrescriptionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionModel {

    public String addPrescription(PrescriptionDTO prescriptionDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO prescription (doctor_id, patient_id, patient_age, diagnosis, notes) VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, prescriptionDTO.getDoctorId());
        statement.setInt(2, prescriptionDTO.getPatientId());
        statement.setInt(3, prescriptionDTO.getAge());
        statement.setString(4, prescriptionDTO.getDiagnosis());
        statement.setString(5, prescriptionDTO.getNotes());

        return statement.executeUpdate() > 0 ? "Prescription Added Successfully" : "Failed to Add Prescription";
    }

    public String updatePrescription(PrescriptionDTO prescriptionDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE prescription SET doctor_id = ?, patient_id = ?, patient_age = ?, diagnosis = ?, notes = ? WHERE prescription_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, prescriptionDTO.getDoctorId());
        statement.setInt(2, prescriptionDTO.getPatientId());
        statement.setInt(3, prescriptionDTO.getAge());
        statement.setString(4, prescriptionDTO.getDiagnosis());
        statement.setString(5, prescriptionDTO.getNotes());
        statement.setInt(6, prescriptionDTO.getPrescriptionId());

        return statement.executeUpdate() > 0 ? "Prescription Updated Successfully" : "Failed to Update Prescription";
    }

    public String deletePrescription(int prescriptionId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM prescription WHERE prescription_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, prescriptionId);

        return statement.executeUpdate() > 0 ? "Prescription Deleted Successfully" : "Failed to Delete the Prescription";
    }

    public PrescriptionDTO searchPrescription(int prescriptionId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prescription WHERE prescription_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, prescriptionId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO(rst.getInt("prescription_id"), rst.getString("doctor_id"), rst.getInt("patient_id"), rst.getInt("patient_age"), rst.getString("diagnosis"), rst.getString("notes"));
            return prescriptionDTO;
        } else {
            return null;
        }
    }

    public ArrayList<PrescriptionDTO> getAllPrescription() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prescription";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();

        while (rst.next()){
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO(rst.getInt("prescription_id"), rst.getString("doctor_id"), rst.getInt("patient_id"), rst.getInt("patient_age"), rst.getString("diagnosis"), rst.getString("notes"));
            prescriptionDTOs.add(prescriptionDTO);
        }
        return prescriptionDTOs;
    }
}
