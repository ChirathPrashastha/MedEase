package lk.ijse.medease.dto;

public class PrescriptionDTO {
    private String  prescriptionId;
    private String doctorId;
    private String patientId;
    private int age;
    private String diagnosis;
    private String appointmentId;

    public PrescriptionDTO(String prescriptionId, String doctorId, String patientId, int age, String diagnosis, String appointmentId) {
        this.prescriptionId = prescriptionId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.age = age;
        this.diagnosis = diagnosis;
        this.appointmentId = appointmentId;
    }

    public PrescriptionDTO(String doctorId, String patientId, int age, String diagnosis, String appointmentId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.age = age;
        this.diagnosis = diagnosis;
        this.appointmentId = appointmentId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setNotes(String notes) {
        this.appointmentId = appointmentId;
    }
}
