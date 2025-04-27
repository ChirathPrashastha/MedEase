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

        String sql = "INSERT INTO appointment (appointment_id, patient_id, doctor_id, date, check_in_no, time) VALUES (?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, appointmentDTO.getAppointmentId());
        statement.setString(2, appointmentDTO.getPatientId());
        statement.setString(3, appointmentDTO.getDoctorId());
        statement.setDate(4, appointmentDTO.getDate());
        statement.setInt(5, appointmentDTO.getCheckInNo());
        statement.setString(6, appointmentDTO.getTime());

        return statement.executeUpdate() > 0 ? "Appointment Scheduled Successfully" : "Failed to Schedule the Appointment";
    }

    public String updateAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE appointment SET patient_id = ?, doctor_id = ?, date = ?, check_in_no = ?, time = ? WHERE appointment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, appointmentDTO.getPatientId());
        statement.setString(2, appointmentDTO.getDoctorId());
        statement.setDate(3, appointmentDTO.getDate());
        statement.setInt(4, appointmentDTO.getCheckInNo());
        statement.setString(5, appointmentDTO.getTime());
        statement.setString(6, appointmentDTO.getAppointmentId());

        return statement.executeUpdate() > 0 ? "Appointment Updated Successfully" : "Failed to Update the Appointment";
    }

    public String deleteAppointment(String appointmentId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM appointment WHERE appointment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, appointmentId);

        return statement.executeUpdate() > 0 ? "Appointment Deleted Successfully" : "Failed to Delete the Appointment";
    }

    public AppointmentDTO searchAppointment(String appointmentId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment WHERE appointment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, appointmentId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            AppointmentDTO appointmentDTO = new AppointmentDTO(rst.getString("appointment_id"), rst.getString("patient_id"), rst.getString("doctor_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("time"));
            return appointmentDTO;
        }
        return null;
    }

    public ArrayList<AppointmentDTO> getAllAppointments() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<AppointmentDTO> appointmentDTOs = new ArrayList<>();

        while (rst.next()) {
            AppointmentDTO appDto = new AppointmentDTO(rst.getString("appointment_id"),rst.getString("patient_id"), rst.getString("doctor_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("time"));
            appointmentDTOs.add(appDto);
        }
        return appointmentDTOs;
    }

    public ArrayList<AppointmentDTO> getAppointmentsByDoctor(String doctorId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT appointment_id, patient_id, date, check_in_no, time FROM appointment WHERE date = CURDATE() AND doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorId);
        ResultSet rst = statement.executeQuery();

        ArrayList<AppointmentDTO> appointmentDTOs = new ArrayList<>();

        while (rst.next()) {
            AppointmentDTO appDto = new AppointmentDTO(rst.getString("appointment_id"), rst.getString("patient_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("time"));

            appointmentDTOs.add(appDto);
        }
        return appointmentDTOs;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("appointment_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("A"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "A0001";
    }

}
