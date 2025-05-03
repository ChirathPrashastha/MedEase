package lk.ijse.medease.dto.tm;

import lk.ijse.medease.dto.JobRole;

import java.sql.Date;

public class EmployeeTM {
    private String employeeId;
    private String name;
    private JobRole jobRole;
    private Date recruitedDate;
    private String address;
    private String contact;
    private String email;

    public EmployeeTM(String employeeId, String name, JobRole jobRole, Date recruitedDate, String address, String contact, String email) {
        this.employeeId = employeeId;
        this.name = name;
        this.jobRole = jobRole;
        this.recruitedDate = recruitedDate;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobRole getJobRole() {
        return jobRole;
    }

    public void setJobRole(JobRole jobRole) {
        this.jobRole = jobRole;
    }

    public Date getRecruitedDate() {
        return recruitedDate;
    }

    public void setRecruitedDate(Date recruitedDate) {
        this.recruitedDate = recruitedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setEmail(String email) {
        this.email = email;
    }
}
