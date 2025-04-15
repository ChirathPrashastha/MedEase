package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineModel {

    public int getMedicineIdByMedicineName(String medicineName) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT medicine_id FROM medicine WHERE generic_name = ? OR brand = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, medicineName);
        statement.setString(2, medicineName);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("medicine_id");
        }else {
            return -1;
        }
    }
}
