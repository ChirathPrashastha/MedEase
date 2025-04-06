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

        String sql = "INSERT INTO patient (name, birth_date, contact, email, allergies) VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientDTO.getName());
        statement.setDate(2, patientDTO.getBirthDate());
        statement.setString(3, patientDTO.getContact());
        statement.setString(4, patientDTO.getEmail());
        statement.setString(5, patientDTO.getAllergies());

        return statement.executeUpdate() > 0 ? "Patient Added Successfully" : "Failed to Add Patient";
    }

    public String updatePatient(PatientDTO patientDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE patient SET name = ? , birth_date = ? , contact = ? , email = ? , allergies = ? WHERE patient_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientDTO.getName());
        statement.setDate(2, patientDTO.getBirthDate());
        statement.setString(3, patientDTO.getContact());
        statement.setString(4, patientDTO.getEmail());
        statement.setString(5, patientDTO.getAllergies());
        statement.setInt(6, patientDTO.getPatientId());

        return statement.executeUpdate() > 0 ? "Patient Updated Successfully" : "Failed to Update Patient";
    }

    public String deletePatient(String patientId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM patient WHERE patient_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientId);

        return statement.executeUpdate() > 0 ? "Patient Deleted Successfully" : "Failed to Delete Patient";
    }

    public PatientDTO searchPatient(String patientId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, patientId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            PatientDTO patientDTO = new PatientDTO(rst.getString("name"), rst.getDate("birth_date"), rst.getString("contact"), rst.getString("email"), rst.getString("allergies"));
            return patientDTO;
        }else {
            return null;
        }
    }

    public ArrayList<PatientDTO> getAllPatients() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM patient";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<PatientDTO> patientDTOs = new ArrayList<>();

        while (rst.next()) {
            PatientDTO patientDTO = new PatientDTO(rst.getInt("patient_id"),rst.getString("name"), rst.getDate("birth_date"), rst.getString("contact"), rst.getString("email"), rst.getString("allergies") );
            patientDTOs.add(patientDTO);
        }
        return patientDTOs;
    }
}
