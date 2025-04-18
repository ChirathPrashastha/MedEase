package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryModel {

    public int getQuantityByInventoryId(int inventoryId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT quantity FROM inventory WHERE inventory_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, inventoryId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return rst.getInt("quantity");
        }else {
            return -1;
        }
    }
}
