package lk.ijse.medease.controller;

import lk.ijse.medease.dto.AttendStatus;
import lk.ijse.medease.dto.AttendanceDTO;
import lk.ijse.medease.model.AttendanceModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceController {
    private AttendanceModel attendanceModel;

    AttendanceController() {
        attendanceModel = new AttendanceModel();
    }

    public String updateAttendance(AttendanceDTO attendanceDTO) throws SQLException {
        String response = attendanceModel.updateAttendance(attendanceDTO);
        return response;
    }

    public String deleteAttendance(AttendanceDTO attendanceDTO) throws SQLException {
        String response = attendanceModel.deleteAttendance(attendanceDTO);
        return response;
    }

    public String searchAttendance(String employeeId , Date date) throws SQLException {
        String response = attendanceModel.searchAttendance(employeeId, date);
        return response;
    }

    public ArrayList<AttendanceDTO> getAllAttendance() throws SQLException {
        ArrayList<AttendanceDTO> attendanceList = attendanceModel.getAllAttendance();
        return attendanceList;
    }

    public ArrayList<AttendanceDTO> getAttendanceByDate(Date date) throws SQLException {
        return attendanceModel.getAttendanceByDate(date);
    }

    public ArrayList<AttendanceDTO> filterAttendance(AttendStatus attendStatus, Date date) throws SQLException {
        return attendanceModel.filterAttendance(attendStatus, date);
    }

    public String markAttendance(String employeeId, Date date) throws SQLException {
        return attendanceModel.markAttendance(employeeId, date);
    }

    public String addDayOff(String employeeId, Date date) throws SQLException {
        return attendanceModel.addDayOff(employeeId, date);
    }
}
