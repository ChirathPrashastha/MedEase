package lk.ijse.medease.controller;

import lk.ijse.medease.dto.RestockDTO;
import lk.ijse.medease.model.RestockModel;

import java.sql.SQLException;

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
}
