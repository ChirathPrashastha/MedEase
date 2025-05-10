package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PatientDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientModel {

    public String addPatient(PatientDTO patientDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO patient (patient_id, name, birth_date, contact, email, allergies) VALUES (?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientDTO.getPatientId());
        statement.setString(2, patientDTO.getName());
        statement.setDate(3, patientDTO.getBirthDate());
        statement.setString(4, patientDTO.getContact());
        statement.setString(5, patientDTO.getEmail());
        statement.setString(6, patientDTO.getAllergies());

        return statement.executeUpdate() > 0 ? "Patient Added Successfully" : "Failed to Add Patient";
    }

    public String updatePatient(PatientDTO patientDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE patient SET name = ? , birth_date = ? , contact = ? , email = ? , allergies = ? WHERE patient_id = ? AND status = 'ACTIVE'";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientDTO.getName());
        statement.setDate(2, patientDTO.getBirthDate());
        statement.setString(3, patientDTO.getContact());
        statement.setString(4, patientDTO.getEmail());
        statement.setString(5, patientDTO.getAllergies());
        statement.setString(6, patientDTO.getPatientId());

        return statement.executeUpdate() > 0 ? "Patient Updated Successfully" : "Failed to Update Patient";
    }

    public String deletePatient(String patientId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE patient SET status = 'DELETED' WHERE patient_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientId);

        return statement.executeUpdate() > 0 ? "Patient Deleted Successfully" : "Failed to Delete Patient";
    }

    public PatientDTO searchPatient(String patientId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM patient WHERE patient_id = ? AND status = 'ACTIVE'";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            PatientDTO patientDTO = new PatientDTO(rst.getString("patient_id"), rst.getString("name"), rst.getDate("birth_date"), rst.getString("contact"), rst.getString("email"), rst.getString("allergies"));
            return patientDTO;
        }else {
            return null;
        }
    }

    public ArrayList<PatientDTO> getAllPatients() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM patient WHERE status = 'ACTIVE'";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<PatientDTO> patientDTOs = new ArrayList<>();

        while (rst.next()) {
            PatientDTO patientDTO = new PatientDTO(rst.getString("patient_id"),rst.getString("name"), rst.getDate("birth_date"), rst.getString("contact"), rst.getString("email"), rst.getString("allergies") );
            patientDTOs.add(patientDTO);
        }
        return patientDTOs;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT patient_id FROM patient ORDER BY patient_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("patient_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("P"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "P0001";
    }
}
