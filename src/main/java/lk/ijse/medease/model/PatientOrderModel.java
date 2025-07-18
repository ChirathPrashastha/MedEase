package lk.ijse.medease.model;

import lk.ijse.medease.controller.MedicineController;
import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PatientOrderDTO;
import lk.ijse.medease.dto.PatientOrderDetailsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientOrderModel {

    private MedicineController medicineController;

    public PatientOrderModel(){
        medicineController = new MedicineController();
    }

    public String placeOrder(PatientOrderDTO orderDTO, ArrayList<PatientOrderDetailsDTO> orderDetailsArray) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

//            double subTotal = 0.0;
//            for (PatientOrderDetailsDTO dto : orderDetailsArray) {
//                subTotal += dto.getTotalPrice();
//            }

//            orderDTO.setSubTotal(subTotal);

            String orderAddingSql = "INSERT INTO patient_order (order_id, prescription_id, sub_total) VALUES (?,?,?)";

            PreparedStatement orderAddingStatement = connection.prepareStatement(orderAddingSql);
            orderAddingStatement.setString(1, orderDTO.getOrderId());
            orderAddingStatement.setString(2, orderDTO.getPrescriptionId());
            orderAddingStatement.setDouble(3, orderDTO.getSubTotal());

            boolean isOrderAdded = orderAddingStatement.executeUpdate() > 0 ? true : false;

            if (isOrderAdded) {
                boolean isOrderDetailsAdded = true;

                String orderDetailsAddingSql = "INSERT INTO patient_order_details VALUES (?,?,?,?,?)";

                for (PatientOrderDetailsDTO dto : orderDetailsArray) {
                    PreparedStatement orderDetailsAddingStatement = connection.prepareStatement(orderDetailsAddingSql);
                    orderDetailsAddingStatement.setString(1, dto.getOrderId());
                    orderDetailsAddingStatement.setString(2, dto.getMedicineId());
                    orderDetailsAddingStatement.setDouble(3, dto.getUnitPrice());
                    orderDetailsAddingStatement.setInt(4, dto.getQuantity());
                    orderDetailsAddingStatement.setDouble(5, dto.getTotalPrice());

                    if (!(orderDetailsAddingStatement.executeUpdate() > 0)) {
                        isOrderDetailsAdded = false;
                    }
                }

                if (isOrderDetailsAdded) {

                    boolean isQuantityUpdated = true;

                    String updateQuantitySql = "UPDATE inventory SET quantity = quantity - ? WHERE inventory_id = ?";

                    for (PatientOrderDetailsDTO dto : orderDetailsArray) {
                        String inventoryId = medicineController.getInventoryIdByMedicineId(dto.getMedicineId());

                        PreparedStatement updateQuantityStatement = connection.prepareStatement(updateQuantitySql);
                        updateQuantityStatement.setInt(1, dto.getQuantity());
                        updateQuantityStatement.setString(2, inventoryId);

                        if (!(updateQuantityStatement.executeUpdate() > 0)) {
                            isQuantityUpdated = false;
                        }
                    }

                    if (isQuantityUpdated) {
                        return "Order Added Successfully";
                    }else {
                        connection.rollback();
                        return "Failed to Update Quantity";
                    }

                }else {
                    connection.rollback();
                    return "Failed to Add Patient Order Details";
                }

            } else {
                connection.rollback();
                return "Failed to add Patient Order";
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.commit();
        }
    }

    public double calculateTotalPrice(String medicineId, int quantity) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        double unitPrice = 0.0;
        double totalPrice = 0.0;

        String sql = "SELECT price FROM medicine WHERE medicine_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, medicineId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            unitPrice = rst.getDouble("price");
        }

        totalPrice = unitPrice * quantity;
        return totalPrice;
    }

    public double getSubAmountByPrescriptionId(String prescriptionId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT sub_total FROM patient_order WHERE prescription_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, prescriptionId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getDouble("sub_total");
        }
        return 0.0;
    }

    public String getOrderIdByPrescriptionId(String prescriptionId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM patient_order WHERE prescription_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, prescriptionId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getString("order_id");
        }
        return null;
    }

    public double getSubTotalByOrderId(String orderId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT sub_total FROM patient_order WHERE order_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, orderId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getDouble("sub_total");
        }
        return -1;
    }

    public double getTodayTotal() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT SUM(patient_order.sub_total) AS total FROM patient_order WHERE issued_date = CURDATE()";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getDouble("total");
        }
        return -1;
    }

    public int getTodayOrderCount() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(order_id) AS count FROM patient_order WHERE issued_date = CURDATE()";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return rst.getInt("count");
        }
        return -1;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM patient_order ORDER BY order_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("order_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("O"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "O0001";
    }
}
