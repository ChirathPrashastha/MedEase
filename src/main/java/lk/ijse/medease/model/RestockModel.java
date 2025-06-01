package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.RestockDTO;
import lk.ijse.medease.dto.RestockStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestockModel {

    public String requestRestock(RestockDTO restockDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO restock VALUES (?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, restockDTO.getRestockId());
        statement.setString(2, restockDTO.getMedicineId());
        statement.setInt(3, restockDTO.getRequestedQuantity());
        statement.setString(4, restockDTO.getStatus().name());

        return statement.executeUpdate() > 0 ? "Stock Requested Successfully" : "Failed to Request Stock";
    }

    public ArrayList<RestockDTO> getRestockList() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM restock";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<RestockDTO> restockList = new ArrayList<>();

        while (rst.next()) {
            RestockDTO restockDTO = new RestockDTO(rst.getString("restock_id"),rst.getString("medicine_id"), rst.getInt("requested_quantity"), RestockStatus.valueOf(rst.getString("status")));
            restockList.add(restockDTO);
        }
        return restockList;
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

    public String orderStock(String restockId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE restock SET status = 'ORDERED' WHERE restock_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, restockId);

        return statement.executeUpdate() > 0 ? "success" : "failed";
    }

    public String getSupplierEmail(String medicineId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT supplier.email FROM restock JOIN medicine ON restock.medicine_id = medicine.medicine_id JOIN inventory ON medicine.inventory_id = inventory.inventory_id JOIN supplier ON inventory.supplier_id = supplier.supplier_id WHERE restock.medicine_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, medicineId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return rst.getString("email");
        }
        return null;
    }
}
