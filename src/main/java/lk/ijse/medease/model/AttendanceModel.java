package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.AttendanceDTO;

import java.sql.*;
import java.util.ArrayList;

public class AttendanceModel {

    public String markAttendance(AttendanceDTO attendanceDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO attendance (employee_id, status) VALUES (?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, attendanceDTO.getEmployeeId());
        statement.setString(2, attendanceDTO.getStatus());

        return statement.executeUpdate() > 0 ? "Attendance Marked Successfully" : "Failed to Mark Attendance";
    }

    public String updateAttendance(AttendanceDTO attendanceDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE attendance SET status = ? WHERE employee_id = ? AND attend_date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, attendanceDTO.getStatus());
        statement.setString(2, attendanceDTO.getEmployeeId());
        statement.setDate(3, attendanceDTO.getAttendDate());

        return statement.executeUpdate() > 0 ? "Attendance Updated Successfully" : "Failed to Update Attendance";
    }

    public String deleteAttendance(AttendanceDTO attendanceDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM attendance WHERE attend_date = ? AND employee_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, attendanceDTO.getAttendDate());
        statement.setString(2, attendanceDTO.getEmployeeId());

        return statement.executeUpdate() > 0 ? "Attendance Deleted Successfully" : "Failed to Delete Attendance";
    }

    public String searchAttendance(String employeeId, Date date) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT status FROM attendance WHERE attend_date = ? AND employee_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, date);
        statement.setString(2, employeeId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("status");
        }else {
            return "Attendance Not Found";
        }
    }

    public ArrayList<AttendanceDTO> getAllAttendance() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<AttendanceDTO> attendanceList = new ArrayList<>();

        while (rst.next()) {
            AttendanceDTO attendanceDTO = new AttendanceDTO(rst.getDate("attend_date"), rst.getString("employee_id"), rst.getString("status"));
            attendanceList.add(attendanceDTO);
        }
        return attendanceList;
    }
}