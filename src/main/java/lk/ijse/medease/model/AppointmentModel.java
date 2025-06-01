package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.AppointmentDTO;
import lk.ijse.medease.dto.CheckInDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

        try {
            connection.setAutoCommit(false);

            String addingAppointmentSql = "INSERT INTO appointment (appointment_id, patient_id, doctor_id, date, check_in_no, time) VALUES (?,?,?,?,?,?)";

            PreparedStatement addingAppointmentStatement = connection.prepareStatement(addingAppointmentSql);
            addingAppointmentStatement.setString(1, appointmentDTO.getAppointmentId());
            addingAppointmentStatement.setString(2, appointmentDTO.getPatientId());
            addingAppointmentStatement.setString(3, appointmentDTO.getDoctorId());
            addingAppointmentStatement.setDate(4, appointmentDTO.getDate());
            addingAppointmentStatement.setInt(5, appointmentDTO.getCheckInNo());
            addingAppointmentStatement.setString(6, appointmentDTO.getTime());

            boolean isAppointmentAdded =  addingAppointmentStatement.executeUpdate() > 0;
            if (isAppointmentAdded) {

                String addingCheckInSql = "INSERT INTO check_in (doctor_id, date, check_in_no) VALUES (?, ?, ?)";

                PreparedStatement addingCheckInStatement = connection.prepareStatement(addingCheckInSql);
                addingCheckInStatement.setString(1, appointmentDTO.getDoctorId());
                addingCheckInStatement.setDate(2, appointmentDTO.getDate());
                addingCheckInStatement.setInt(3, appointmentDTO.getCheckInNo());

                boolean isCheckInAdded = addingCheckInStatement.executeUpdate() > 0;
                if (isCheckInAdded) {
                    return "Appointment Added Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Save Check In";
                }
            }else {
                connection.rollback();
                return "Failed to Save Appointment";
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }finally {
            connection.commit();
        }
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

    public String deleteAppointment(AppointmentDTO appointmentDTO) throws  SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String deleteAppointmentSql = "DELETE FROM appointment WHERE appointment_id = ?";

            PreparedStatement deleteAppointmentStatement = connection.prepareStatement(deleteAppointmentSql);
            deleteAppointmentStatement.setString(1, appointmentDTO.getAppointmentId());

            boolean isAppointmentDeleted = deleteAppointmentStatement.executeUpdate() > 0;
            if (isAppointmentDeleted) {

                String deleteCheckInSql = "DELETE FROM check_in WHERE doctor_id = ? AND date = ? AND check_in_no = ?";

                PreparedStatement deleteCheckInStatement = connection.prepareStatement(deleteCheckInSql);
                deleteCheckInStatement.setString(1, appointmentDTO.getDoctorId());
                deleteCheckInStatement.setDate(2, appointmentDTO.getDate());
                deleteCheckInStatement.setInt(3, appointmentDTO.getCheckInNo());

                boolean isCheckInDeleted = deleteCheckInStatement.executeUpdate() > 0;
                if (isCheckInDeleted) {
                    return "Appointment Deleted Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Delete Check In";
                }
            }else {
                connection.rollback();
                return "Failed to delete the Appointment";
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }finally {
            connection.commit();
        }


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

    public int getTodayAppointmentCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(appointment_id) AS count FROM appointment WHERE date = CURRENT_DATE";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return Integer.parseInt(rst.getString("count"));
        }
        return -1;
    }

    public Map<String, String> getEmailsOfRemainingPatients(int number) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT appointment.patient_id, patient.email FROM appointment JOIN patient ON appointment.patient_id = patient.patient_id WHERE appointment.check_in_no > ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, number);

        ResultSet rst = statement.executeQuery();

        Map<String, String> emails = new TreeMap<>();

        while (rst.next()) {
            emails.put(rst.getString("patient_id"), rst.getString("email"));
        }
        return emails;
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
