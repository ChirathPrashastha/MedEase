package lk.ijse.medease.controller;

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

    public String markAttendance(AttendanceDTO attendanceDTO) throws ClassNotFoundException, SQLException {
        String response = attendanceModel.markAttendance(attendanceDTO);
        return response;
    }

    public String updateAttendance(AttendanceDTO attendanceDTO) throws ClassNotFoundException, SQLException {
        String response = attendanceModel.updateAttendance(attendanceDTO);
        return response;
    }

    public String deleteAttendance(AttendanceDTO attendanceDTO) throws ClassNotFoundException, SQLException {
        String response = attendanceModel.deleteAttendance(attendanceDTO);
        return response;
    }

    public String searchAttendance(String employeeId , Date date) throws ClassNotFoundException, SQLException {
        String response = attendanceModel.searchAttendance(employeeId, date);
        return response;
    }

    public ArrayList<AttendanceDTO> getAllAttendance() throws ClassNotFoundException, SQLException {
        ArrayList<AttendanceDTO> attendanceList = attendanceModel.getAllAttendance();
        return attendanceList;
    }
}
