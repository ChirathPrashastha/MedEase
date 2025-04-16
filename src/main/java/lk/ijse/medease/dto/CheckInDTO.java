package lk.ijse.medease.dto;

import java.sql.Date;

public class CheckInDTO {
    private int checkInId;
    private String doctorId;
    private Date date;
    private int checkInNo;
    private String status;

    public CheckInDTO(int checkInId, String doctorId, Date date, int checkInNo, String status) {
        this.checkInId = checkInId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.status = status;
    }

    public CheckInDTO(int checkInNo, String status) {
        this.checkInNo = checkInNo;
        this.status = status;
    }

    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCheckInNo() {
        return checkInNo;
    }

    public void setCheckInNo(int checkInNo) {
        this.checkInNo = checkInNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
