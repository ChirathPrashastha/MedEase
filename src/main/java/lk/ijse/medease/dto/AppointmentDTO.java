package lk.ijse.medease.dto;

import javafx.scene.control.Button;

import java.sql.Date;
import java.sql.Time;

public class AppointmentDTO {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private Date date;
    private int checkInNo;
    private String time;

    public AppointmentDTO(String appointmentId, String patientId, Date date, int checkInNo, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public AppointmentDTO(String appointmentId, String patientId, String doctorId, Date date, int checkInNo, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCheckInNo() {
        return checkInNo;
    }

    public void setCheckInNo(int checkInNo) {
        this.checkInNo = checkInNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String  getPatientId() {
        return patientId;
    }

    public void setPatientId(String  patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }
}
