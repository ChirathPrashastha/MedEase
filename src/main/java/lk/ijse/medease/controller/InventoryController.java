package lk.ijse.medease.controller;

import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.model.InventoryModel;

import java.sql.SQLException;

public class InventoryController {
    private InventoryModel inventoryModel;

    public InventoryController() {
        inventoryModel = new InventoryModel();
    }

    public int getQuantityByInventoryId(String inventoryId) throws SQLException, ClassNotFoundException {
        int response = inventoryModel.getQuantityByInventoryId(inventoryId);
        return response;
    }

    public InventoryDTO searchInventory(String inventoryId) throws SQLException, ClassNotFoundException {
        InventoryDTO inventoryDTO = inventoryModel.searchInventory(inventoryId);
        return inventoryDTO;
    }

    public String restock(String inventoryId, int quantity, String medicineId) throws SQLException, ClassNotFoundException {
        return inventoryModel.restock(inventoryId, quantity, medicineId);
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        return inventoryModel.getNextId();
    }
}
