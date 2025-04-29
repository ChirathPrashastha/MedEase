package lk.ijse.medease.controller;

import lk.ijse.medease.dto.SupplierDTO;
import lk.ijse.medease.model.SupplierModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierController {

    private SupplierModel supplierModel;

    public SupplierController() {
        supplierModel = new SupplierModel();
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        return supplierModel.getAllSuppliers();
    }
}
