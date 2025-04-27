package lk.ijse.medease.dto.tm;

public class PrescriptionTM {
    private int prescriptionId;
    private int patientId;
    private String diagnosis;
    private String notes;

    public PrescriptionTM(int prescriptionId, int patientId, String diagnosis, String notes) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.notes = notes;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
