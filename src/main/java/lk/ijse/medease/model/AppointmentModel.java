package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.AppointmentDTO;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentModel {

    public int checkNo(String doctorId, Date date) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT MAX(check_in.check_in_no) AS number FROM check_in WHERE doctor_id = ? AND date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorId);
        statement.setDate(2, date);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return rst.getInt("number");
        }else {
            return 0;
        }
    }

    public String getTime(String doctorId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT doctor.shift AS time FROM doctor WHERE doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, doctorId);
        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return rst.getString("time");
        }else {
            return "Unknown";
        }
    }

    public String addAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO appointment (patient_id, doctor_id, date, check_in_no, time) VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentDTO.getPatientId());
        statement.setString(2, appointmentDTO.getDoctorId());
        statement.setDate(3, appointmentDTO.getDate());
        statement.setInt(4, appointmentDTO.getCheckInNo());
        statement.setString(5, appointmentDTO.getTime());

        return statement.executeUpdate() > 0 ? "Appointment Scheduled Successfully" : "Failed to Schedule the Appointment";
    }

    public String updateAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE appointment SET patient_id = ?, doctor_id = ?, date = ?, check_in_no = ?, time = ? WHERE appointment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentDTO.getPatientId());
        statement.setString(2, appointmentDTO.getDoctorId());
        statement.setDate(3, appointmentDTO.getDate());
        statement.setInt(4, appointmentDTO.getCheckInNo());
        statement.setString(5, appointmentDTO.getTime());
        statement.setInt(6, appointmentDTO.getAppointmentId());

        return statement.executeUpdate() > 0 ? "Appointment Updated Successfully" : "Failed to Update the Appointment";
    }

    public String deleteAppointment(int appointmentId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM appointment WHERE appointment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentId);

        return statement.executeUpdate() > 0 ? "Appointment Deleted Successfully" : "Failed to Delete the Appointment";
    }

    public AppointmentDTO searchAppointment(int appointmentId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment WHERE appointment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            AppointmentDTO appointmentDTO = new AppointmentDTO(rst.getInt("patient_id"), rst.getString("doctor_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("time"));
            return appointmentDTO;
        }else {
            return null;
        }
    }

    public ArrayList<AppointmentDTO> getAllAppointments() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<AppointmentDTO> appointmentDTOs = new ArrayList<>();

        while (rst.next()) {
            AppointmentDTO appDto = new AppointmentDTO(rst.getInt("appointment_id"),rst.getInt("patient_id"), rst.getString("doctor_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("time"));
            appointmentDTOs.add(appDto);
        }
        return appointmentDTOs;
    }


}
