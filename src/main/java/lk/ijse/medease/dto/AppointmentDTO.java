package lk.ijse.medease.dto;

import java.sql.Date;
import java.sql.Time;

public class AppointmentDTO {
    private Integer appointmentId;
    private Integer patientId;
    private String doctorId;
    private Date date;
    private Integer checkInNo;
    private Time time;

    public AppointmentDTO(Integer patientId, String doctorId, Date date, Integer checkInNo, Time time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public AppointmentDTO(Integer appointmentId, Integer patientId, String doctorId, Date date, Integer checkInNo, Time time) {
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

    public Integer getCheckInNo() {
        return checkInNo;
    }

    public void setCheckInNo(Integer checkInNo) {
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

}
