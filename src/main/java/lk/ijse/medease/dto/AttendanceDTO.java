package lk.ijse.medease.dto;

import java.sql.Date;

public class AttendanceDTO {
    private Date attendDate;
    private String employeeId;
    private String status;

    public AttendanceDTO(String employeeId, String status) {
        this.employeeId = employeeId;
        this.status = status;
    }

    public AttendanceDTO(Date attendDate, String employeeId, String status) {
        this.attendDate = attendDate;
        this.employeeId = employeeId;
        this.status = status;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
