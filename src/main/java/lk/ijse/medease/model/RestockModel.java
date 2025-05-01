package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.RestockDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestockModel {

    public String requestRestock(RestockDTO restockDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO restock VALUES (?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, restockDTO.getRestockId());
        statement.setString(2, restockDTO.getMedicineId());
        statement.setInt(3, restockDTO.getRequestedQuantity());
        statement.setString(4, restockDTO.getStatus());

        return statement.executeUpdate() > 0 ? "Stock Requested Successfully" : "Failed to Request Stock";
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT restock_id FROM restock ORDER BY restock_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("restock_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("R"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "R0001";
    }
}
