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
}
