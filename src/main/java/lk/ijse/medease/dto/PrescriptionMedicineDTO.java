package lk.ijse.medease.dto;

public class PrescriptionMedicineDTO {
    private int prescriptionId;
    private int medicineId;
    private String name;
    private String dosage;
    private String frequency;

    public PrescriptionMedicineDTO(int prescriptionId, int medicineId, String name, String dosage, String frequency) {
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public PrescriptionMedicineDTO(int prescriptionId, String name, String dosage, String frequency) {
        this.prescriptionId = prescriptionId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
