package lk.ijse.medease.dto.tm;

import lk.ijse.medease.dto.AttendStatus;

import java.sql.Date;

public class AttendanceTM {
    private Date attendDate;
    private String employeeId;
    private AttendStatus status;

    public AttendanceTM(Date attendDate, String employeeId, AttendStatus status) {
        this.attendDate = attendDate;
        this.employeeId = employeeId;
        this.status = status;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
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
