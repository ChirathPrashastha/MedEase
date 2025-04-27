package lk.ijse.medease.dto;

import java.sql.Date;

public class PatientDTO {
    private String patientId;
    private String name;
    private Date birthDate;
    private String contact;
    private String email;
    private String allergies;

    public PatientDTO(String name, Date birthDate, String contact, String email, String allergies) {
        this.name = name;
        this.birthDate = birthDate;
        this.contact = contact;
        this.email = email;
        this.allergies = allergies;
    }

    public PatientDTO(String patientId, String name, Date birthDate, String contact, String email, String allergies) {
        this.patientId = patientId;
        this.name = name;
        this.birthDate = birthDate;
        this.contact = contact;
        this.email = email;
        this.allergies = allergies;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

    public String getAllergies() {return allergies;}

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
