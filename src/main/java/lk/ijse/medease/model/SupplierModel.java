package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.SupplierDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
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

    public String addSupplier(SupplierDTO supplierDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDTO.getSupplierId());
        statement.setString(2, supplierDTO.getName());
        statement.setString(3, supplierDTO.getAddress());
        statement.setString(4, supplierDTO.getContact());
        statement.setString(5, supplierDTO.getEmail());

        return statement.executeUpdate() > 0 ? "Supplier Added Successfully" : "Failed to Add Supplier";
    }

    public String updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET name = ?, address = ?, contact = ?, email = ? WHERE supplier_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDTO.getName());
        statement.setString(2, supplierDTO.getAddress());
        statement.setString(3, supplierDTO.getContact());
        statement.setString(4, supplierDTO.getEmail());
        statement.setString(5, supplierDTO.getSupplierId());

        return statement.executeUpdate() > 0 ? "Supplier Updated Successfully" : "Failed to Update Supplier";
    }

    public String deleteSupplier(String supplierID) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE supplier_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierID);

        return statement.executeUpdate() > 0 ? "Supplier Deleted Successfully" : "Failed to Delete Supplier";
    }

    public String getNextId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("supplier_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("S"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "S0001";
    }
}
