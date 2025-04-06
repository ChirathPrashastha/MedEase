package lk.ijse.medease.dto;

import java.sql.Date;
import java.sql.Time;

public class AppointmentDTO {
    private int appointmentId;
    private int patientId;
    private String doctorId;
    private Date date;
    private int checkInNo;
    private Time time;

    public AppointmentDTO(int patientId, String doctorId, Date date, int checkInNo, Time time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public AppointmentDTO(int appointmentId, int patientId, String doctorId, Date date, int checkInNo, Time time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

}
