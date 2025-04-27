package lk.ijse.medease.dto.tm;

import java.sql.Date;

public class AppointmentTM {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private Date date;
    private int checkInNo;
    private String time;

    public AppointmentTM(String appointmentId, String patientId, String doctorId, Date date, int checkInNo, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public AppointmentTM(String appointmentId, String patientId, Date date, int checkInNo, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.date = date;
        this.checkInNo = checkInNo;
        this.time = time;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
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
