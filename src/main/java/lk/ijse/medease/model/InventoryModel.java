package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.InventoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {

    public int getQuantityByInventoryId(String inventoryId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT quantity FROM inventory WHERE inventory_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, inventoryId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return rst.getInt("quantity");
        }else {
            return -1;
        }
    }

    public ArrayList<InventoryDTO> viewInventory() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM inventory";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<InventoryDTO> inventoryDTOs = new ArrayList<>();
        while (rst.next()) {
            InventoryDTO inventoryDTO = new InventoryDTO(rst.getString("inventory_id"), rst.getInt("quantity"), rst.getString("supplier_id"), rst.getString("section"));
            inventoryDTOs.add(inventoryDTO);
        }
        return inventoryDTOs;
    }

    public InventoryDTO searchInventory(String inventoryId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM inventory WHERE inventory_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, inventoryId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            InventoryDTO inventoryDTO = new InventoryDTO(rst.getString("inventory_id"), rst.getInt("quantity"), rst.getString("supplier_id"), rst.getString("section"));
            return inventoryDTO;
        }
        return null;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("inventory_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("I"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "I0001";
    }
}
