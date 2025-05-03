package lk.ijse.medease.dto;

import java.sql.Date;

public class DayOffDTO {
    private String employeeId;
    private Date date;

    public DayOffDTO(String employeeId, Date date) {
        this.employeeId = employeeId;
        this.date = date;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
