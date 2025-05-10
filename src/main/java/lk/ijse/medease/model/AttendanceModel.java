package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.AttendStatus;
import lk.ijse.medease.dto.AttendanceDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceModel {

    public String markAttendance(String employeeId, Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE attendance SET status = 'PRESENT' WHERE employee_id = ? AND attend_date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employeeId);
        statement.setDate(2, date);

        return statement.executeUpdate() > 0 ? "success" : "failed";
    }

    public String updateAttendance(AttendanceDTO attendanceDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE attendance SET status = ? WHERE employee_id = ? AND attend_date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, attendanceDTO.getStatus().name());
        statement.setString(2, attendanceDTO.getEmployeeId());
        statement.setDate(3, attendanceDTO.getAttendDate());

        return statement.executeUpdate() > 0 ? "Attendance Updated Successfully" : "Failed to Update Attendance";
    }

    public String deleteAttendance(AttendanceDTO attendanceDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM attendance WHERE attend_date = ? AND employee_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, attendanceDTO.getAttendDate());
        statement.setString(2, attendanceDTO.getEmployeeId());

        return statement.executeUpdate() > 0 ? "Attendance Deleted Successfully" : "Failed to Delete Attendance";
    }

    public String searchAttendance(String employeeId, Date date) throws SQLException {
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

    public ArrayList<AttendanceDTO> getAllAttendance() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<AttendanceDTO> attendanceList = new ArrayList<>();

        while (rst.next()) {
            AttendanceDTO attendanceDTO = new AttendanceDTO(rst.getDate("attend_date"), rst.getString("employee_id"), AttendStatus.valueOf(rst.getString("status")));
            attendanceList.add(attendanceDTO);
        }
        return attendanceList;
    }

    public ArrayList<AttendanceDTO> getAttendanceByDate(Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance WHERE attend_date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, date);

        ResultSet rst = statement.executeQuery();
        ArrayList<AttendanceDTO> attendanceList = new ArrayList<>();

        while (rst.next()) {
            AttendanceDTO attendanceDTO = new AttendanceDTO(rst.getDate("attend_date"), rst.getString("employee_id"), AttendStatus.valueOf(rst.getString("status")));
            attendanceList.add(attendanceDTO);
        }
        return attendanceList;
    }

    public ArrayList<AttendanceDTO> filterAttendance(AttendStatus attendStatus, Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance WHERE status = ? AND attend_date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, attendStatus.name());
        statement.setDate(2, date);

        ResultSet rst = statement.executeQuery();

        ArrayList<AttendanceDTO> attendanceList = new ArrayList<>();
        while (rst.next()) {
            AttendanceDTO attendanceDTO = new AttendanceDTO(rst.getDate("attend_date"), rst.getString("employee_id"), AttendStatus.valueOf(rst.getString("status")));
            attendanceList.add(attendanceDTO);
        }
        return attendanceList;
    }

    public String addDayOff(String employeeId, Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        LocalDate localDate = date.toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();

        String checkingSQL = "SELECT COUNT(*) AS dayOffCount FROM day_off WHERE YEAR(date) = ? AND MONTH(date) = ? AND employee_id = ?";

        PreparedStatement checkingStatement = connection.prepareStatement(checkingSQL);
        checkingStatement.setInt(1, year);
        checkingStatement.setInt(2, month);
        checkingStatement.setString(3, employeeId);

        ResultSet checkingRst = checkingStatement.executeQuery();
        if (checkingRst.next()) {
            int dayOffCount = checkingRst.getInt("dayOffCount");

            if (dayOffCount <= 5) {
                String dayOffAddingSQL = "INSERT INTO day_off VALUES (?,?)";

                PreparedStatement dayOffAddingStatement = connection.prepareStatement(dayOffAddingSQL);
                dayOffAddingStatement.setString(1, employeeId);
                dayOffAddingStatement.setDate(2, date);


                return dayOffAddingStatement.executeUpdate() > 0 ? "success" : "failed";
            }else {
                return "ineligible";
            }
        }
        return null;
    }

    public boolean checkEmployeeIsOffOn(String employeeId, Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM day_off WHERE employee_id = ? AND date = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employeeId);
        statement.setDate(2, date);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return true;
        }
        return false;
    }
}