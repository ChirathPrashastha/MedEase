package lk.ijse.medease.controller;

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
}
