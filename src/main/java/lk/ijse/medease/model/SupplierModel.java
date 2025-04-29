package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.SupplierDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    public ArrayList<SupplierDTO> getAllSuppliers() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(rst.getString("supplier_id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"), rst.getString("email"));
            supplierDTOs.add(supplierDTO);
        }
        return supplierDTOs;
    }
}
