package lk.ijse.medease.controller;

import lk.ijse.medease.dto.RestockDTO;
import lk.ijse.medease.model.RestockModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class RestockController {

    private RestockModel restockModel;

    public RestockController() {
        restockModel = new RestockModel();
    }

    public String requestRestock(RestockDTO restockDTO) throws SQLException {
        return restockModel.requestRestock(restockDTO);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return restockModel.getNextId();
    }

    public ArrayList<RestockDTO> getRestockList() throws SQLException {
        return restockModel.getRestockList();
    }

    public String orderStock(String restockId) throws SQLException {
        return restockModel.orderStock(restockId);
    }

    public String getSupplierEmail(String medicineId) throws SQLException {
        return restockModel.getSupplierEmail(medicineId);
    }
}
