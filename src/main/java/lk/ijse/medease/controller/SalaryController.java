package lk.ijse.medease.controller;

import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.SalaryDTO;
import lk.ijse.medease.model.SalaryModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class SalaryController {
    private SalaryModel salaryModel;

    public SalaryController() {
        salaryModel = new SalaryModel();
    }

    public Map<JobRole, Double> getSalaryPerDay() throws SQLException {
        return salaryModel.getSalaryPerDay();
    }

    public String setSalaryPerDay(JobRole jobRole, double salaryPerDay) throws SQLException {
        return salaryModel.setSalaryPerDay(jobRole, salaryPerDay);
    }

    public String addSalary(ArrayList<SalaryDTO> salaryDTOS) throws SQLException {
        return salaryModel.addSalary(salaryDTOS);
    }

    public ArrayList<SalaryDTO> getSalary() throws SQLException {
        return salaryModel.getSalary();
    }
}
