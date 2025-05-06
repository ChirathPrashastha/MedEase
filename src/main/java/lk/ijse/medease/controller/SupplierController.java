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

    public String addSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierModel.addSupplier(supplierDTO);
    }

    public String updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierModel.updateSupplier(supplierDTO);
    }

    public String deleteSupplier(String supplierID) throws SQLException {
        return supplierModel.deleteSupplier(supplierID);
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        return supplierModel.getAllSuppliers();
    }

    public String getNextId() throws SQLException {
        return supplierModel.getNextId();
    }
}
