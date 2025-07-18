package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationModel {
    public static UserDTO checkCredentials(String username, String password) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM authentication WHERE username = ? AND password = ?");
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UserDTO userDto = new UserDTO(resultSet.getString("employee_id"));
            return userDto;
        }
        return null;
    }

    public static JobRole getJobRole(String employeeId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT employee.job_role FROM employee WHERE employee_id = ?");
        statement.setString(1, employeeId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String jobRoleStr = resultSet.getString("job_role");
            return JobRole.valueOf(jobRoleStr);
        }
        return null;
    }

    public static UserDTO getUser(String employeeId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM authentication WHERE employee_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employeeId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            UserDTO userDto = new UserDTO(resultSet.getString("employee_id"), resultSet.getString("password"), resultSet.getString("username"));
            return userDto;
        }
        return null;
    }
}
