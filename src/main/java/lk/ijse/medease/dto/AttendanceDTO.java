package lk.ijse.medease.dto;

import java.sql.Date;

public class AttendanceDTO {
    private Date attendDate;
    private String employeeId;
    private AttendStatus status;

    public AttendanceDTO(String employeeId, AttendStatus status) {
        this.employeeId = employeeId;
        this.status = status;
    }

    public AttendanceDTO(Date attendDate, String employeeId, AttendStatus status) {
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

    public AttendStatus getStatus() {
        return status;
    }

    public void setStatus(AttendStatus status) {
        this.status = status;
    }
}
