package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorModel {

    public String addDoctor(DoctorDTO doctorDTO) throws ClassNotFoundException , SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO doctor VALUES (?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorDTO.getDoctorId());
        statement.setString(2, doctorDTO.getEmployeeId());
        statement.setString(3, doctorDTO.getSpecialty());
        statement.setString(4, doctorDTO.getRegistrationNumber());
        statement.setString(5, doctorDTO.getHospital());
        statement.setString(6, doctorDTO.getShift().getTime());

        return statement.executeUpdate() > 0 ? "Doctor Added Successfully" : "Failed to Add Doctor";
    }

    public String updateDoctor(DoctorDTO doctorDTO) throws ClassNotFoundException , SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql ="UPDATE doctor SET employee_id = ?, speciality = ?, registration_no = ?, hospital = ?, shift = ? WHERE doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorDTO.getEmployeeId());
        statement.setString(2, doctorDTO.getSpecialty());
        statement.setString(3, doctorDTO.getRegistrationNumber());
        statement.setString(4, doctorDTO.getHospital());
        statement.setString(5, doctorDTO.getShift().getTime());
        statement.setString(6, doctorDTO.getDoctorId());

        return statement.executeUpdate() > 0 ? "Doctor Updated Successfully" : "Failed to Update Doctor";
    }

    public String deleteDoctor(String doctorId) throws ClassNotFoundException , SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM doctor WHERE doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorId);

        return statement.executeUpdate() > 0 ? "Doctor Deleted Successfully" : "Failed to Delete Doctor";
    }

    public DoctorDTO searchDoctor(String doctorId) throws ClassNotFoundException , SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM doctor WHERE doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            DoctorDTO doctorDTO = new DoctorDTO(rst.getString("doctor_id"), rst.getString("employee_id"), rst.getString("speciality"), rst.getString("registration_no"), rst.getString("hospital"), Shift.valueOf(rst.getString("shift")));
            return doctorDTO;
        }else {
            return null;
        }
    }

    public String getEmployeeIdByDoctorId (String doctorId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT employee_id FROM doctor WHERE doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getString("employee_id");
        }
        return null;
    }

    public String getDoctorId(String employeeId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT doctor_id FROM doctor WHERE employee_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employeeId);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getString("doctor_id");
        }else {
            return null;
        }
    }

    public ArrayList<DoctorDTO> getAllDoctors() throws ClassNotFoundException , SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM doctor";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<DoctorDTO> doctorDTOs = new ArrayList<>();

        while (rst.next()) {
            DoctorDTO doctorDTO = new DoctorDTO(rst.getString("doctor_id"), rst.getString("employee_id"), rst.getString("speciality"), rst.getString("registration_no"), rst.getString("hospital"), Shift.valueOf(rst.getString("shift")));
            doctorDTOs.add(doctorDTO);
        }
        return doctorDTOs;
    }

    public String getNextId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT doctor_id FROM doctor ORDER BY doctor_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("doctor_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("D"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "D0001";
    }
}
