package lk.ijse.medease.dto;

import java.sql.Date;

public class SalaryDTO {
    private int salaryId;
    private String employeeId;
    private double basicSalary;
    private double bonus;
    private double netSalary;
    private Date date;

    public SalaryDTO(int salaryId, String employeeId, double basicSalary, double bonus, double netSalary, Date date) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.netSalary = netSalary;
        this.date = date;
    }

    public SalaryDTO(String employeeId, double basicSalary, double bonus, double netSalary, Date date) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.netSalary = netSalary;
        this.date = date;
    }

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
