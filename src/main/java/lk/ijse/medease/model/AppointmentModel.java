package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.AppointmentDTO;

import java.sql.*;

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

    public String addAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO appointment (patient_id, doctor_id, date, check_in_no, time) VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentDTO.getPatientId());
        statement.setString(2, appointmentDTO.getDoctorId());
        statement.setDate(3, appointmentDTO.getDate());
        statement.setInt(4, appointmentDTO.getCheckInNo());
        statement.setTime(5, appointmentDTO.getTime());

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
        statement.setTime(5, appointmentDTO.getTime());
        statement.setInt(6, appointmentDTO.getAppointmentId());

        return statement.executeUpdate() > 0 ? "Appointment Updated Successfully" : "Failed to Update the Appointment";
    }
}
