package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

    public int getTodayAttendanceCountByJobRole(JobRole jobRole) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(attendance.employee_id) AS count FROM attendance INNER JOIN employee ON attendance.employee_id = employee.employee_id WHERE job_role = ? AND attend_date = CURRENT_DATE AND status = 'PRESENT'";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, jobRole.name());

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return Integer.parseInt(rst.getString("count"));
        }
        return -1;
    }

    public Map<String, Integer> getAttendanceCountOfEachEmployee(Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        LocalDate localDate = date.toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        ArrayList<String> employeeIdList = new ArrayList<>();
        Map<String, Integer> map = new TreeMap<>();

        String employeeSql = "SELECT employee_id FROM employee";
        PreparedStatement employeeStatement = connection.prepareStatement(employeeSql);
        ResultSet employeeRst = employeeStatement.executeQuery();

        while (employeeRst.next()) {
            employeeIdList.add(employeeRst.getString("employee_id"));
        }

        String sql = "SELECT employee_id, COUNT(attendance.attend_date) AS count FROM attendance WHERE employee_id = ? AND attendance.status = 'PRESENT' AND MONTH(attend_date) = ? AND YEAR(attend_date) = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        for(int i = 0; i < employeeIdList.size(); i++) {
            statement.setString(1, employeeIdList.get(i));
            statement.setInt(2, month);
            statement.setInt(3, year);

            ResultSet rst = statement.executeQuery();
            if (rst.next()) {
                map.put(employeeIdList.get(i), Integer.parseInt(rst.getString("count")));
            }
        }
        return map;
    }
}