package lk.ijse.medease.dto.tm;

public class PrescriptionTM {
    private String prescriptionId;
    private String patientId;
    private String diagnosis;
    private String appointmentId;

    public PrescriptionTM(String prescriptionId, String patientId, String diagnosis, String appointmentId) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.appointmentId = appointmentId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
