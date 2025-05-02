package lk.ijse.medease.dto;

public class DoctorDTO {
    private String doctorId;
    private String employeeId;
    private String specialty;
    private String registrationNumber;
    private String hospital;
    private Shift shift;

    public DoctorDTO(String doctorId, String employeeId, String specialty, String registrationNumber, String hospital, Shift shift) {
        this.doctorId = doctorId;
        this.employeeId = employeeId;
        this.specialty = specialty;
        this.registrationNumber = registrationNumber;
        this.hospital = hospital;
        this.shift = shift;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
