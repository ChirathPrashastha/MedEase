package lk.ijse.medease.dto;

public class PrescriptionMedicineDTO {
    private String prescriptionId;
    private String medicineId;
    private String name;
    private String dosage;
    private String frequency;
    private String duration;

    public PrescriptionMedicineDTO(String prescriptionId, String medicineId, String name, String dosage, String frequency, String duration) {
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
    }

    public PrescriptionMedicineDTO(String prescriptionId, String name, String dosage, String frequency, String duration) {
        this.prescriptionId = prescriptionId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
    }

    public PrescriptionMedicineDTO(String name, String dosage, String frequency, String duration) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
