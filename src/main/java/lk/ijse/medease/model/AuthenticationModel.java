package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
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
            UserDTO userDto = new UserDTO(resultSet.getInt("auth_id"), resultSet.getString("employee_id"));
            return userDto;
        }
        return null;
    }

    public static String getJobRole(String employeeId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT employee.job_role FROM employee WHERE employee_id = ?");
        statement.setString(1, employeeId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("job_role");
        }
        return null;
    }
}
