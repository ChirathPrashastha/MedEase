package lk.ijse.medease.model;

import lk.ijse.medease.controller.AttendanceController;
import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.SalaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalaryModel {
    public Map<JobRole, Double> getSalaryPerDay() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salaryPerDay";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        Map<JobRole, Double> salaryPerDay = new HashMap<>();
        while (rst.next()) {
            salaryPerDay.put(JobRole.valueOf(rst.getString("job_role")), rst.getDouble("salary_per_day"));
        }
        return salaryPerDay;
    }

    public String setSalaryPerDay(JobRole jobRole, double salaryPerDay) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE salaryperday SET salary_per_day = ? WHERE job_role = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, salaryPerDay);
        statement.setString(2, jobRole.toString());

        return statement.executeUpdate() > 0 ? jobRole.toString() + "'s salary per day updated successfully": "Failed to update salary per day";
    }

    public String addSalary(ArrayList<SalaryDTO> salaryDTOS) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isSalaryUpdated = true;

            String sql = "INSERT INTO salary (employee_id, basic_salary, bonus, net_salary, date) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            for (SalaryDTO salaryDTO : salaryDTOS) {
                statement.setString(1, salaryDTO.getEmployeeId());
                statement.setDouble(2, salaryDTO.getBasicSalary());
                statement.setDouble(3, salaryDTO.getBonus());
                statement.setDouble(4, salaryDTO.getNetSalary());
                statement.setDate(5, salaryDTO.getDate());

                isSalaryUpdated = statement.executeUpdate() > 0;
            }

            if (isSalaryUpdated) {
                connection.commit();
                return "Salary Updated Successfully";
            }else {
                connection.rollback();
                return "Failed to Update Salary";
            }

        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return "Failed to Update Salary";
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public ArrayList<SalaryDTO> getSalary() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<SalaryDTO> salaryDTOS = new ArrayList<>();
        while (rst.next()) {
            SalaryDTO salaryDTO = new SalaryDTO(rst.getString("employee_id"), rst.getDouble("basic_salary"), rst.getDouble("bonus"), rst.getDouble("net_salary"), rst.getDate("date"));
            salaryDTOS.add(salaryDTO);
        }
        return salaryDTOS;
    }
}


