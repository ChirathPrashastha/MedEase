package lk.ijse.medease.dto.tm;

import java.sql.Date;

public class AppointmentTM {
    private int appointmentId;
    private int patientId;
    private String doctorId;
    private Date date;
    private int checkInNo;
    private String time;

    public AppointmentTM(int appointmentId, int patientId, String doctorId, Date date, int checkInNo, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public AppointmentTM(int appointmentId, int patientId, Date date, int checkInNo, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
