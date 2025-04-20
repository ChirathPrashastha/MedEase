package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PatientOrderDTO;
import lk.ijse.medease.dto.PatientOrderDetailsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientOrderModel {

    public String placeOrder(PatientOrderDTO orderDTO, ArrayList<PatientOrderDetailsDTO> orderDetailsArray) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            double subTotal = 0.0;
            for (PatientOrderDetailsDTO dto : orderDetailsArray) {
                subTotal += dto.getTotalPrice();
            }

            orderDTO.setSubTotal(subTotal);

            String orderAddingSql = "INSERT INTO patient_order (order_id, prescription_id, sub_total) VALUES (?,?,?)";

            PreparedStatement orderAddingStatement = connection.prepareStatement(orderAddingSql);
            orderAddingStatement.setInt(1, orderDTO.getOrderId());
            orderAddingStatement.setInt(2, orderDTO.getPrescriptionId());
            orderAddingStatement.setDouble(3, orderDTO.getSubTotal());

            boolean isOrderAdded = orderAddingStatement.executeUpdate() > 0 ? true : false;

            if (isOrderAdded) {
                boolean isOrderDetailsAdded = true;

                String orderDetailsAddingSql = "INSERT INTO patient_order_details VALUES (?,?,?,?)";

                for (PatientOrderDetailsDTO dto : orderDetailsArray) {
                    PreparedStatement orderDetailsAddingStatement = connection.prepareStatement(orderDetailsAddingSql);
                    orderDetailsAddingStatement.setInt(1, dto.getOrderId());
                    orderDetailsAddingStatement.setInt(2, dto.getMedicineId());
                    orderDetailsAddingStatement.setInt(3, dto.getQuantity());
                    orderDetailsAddingStatement.setDouble(4, dto.getTotalPrice());

                    if (!(orderDetailsAddingStatement.executeUpdate() > 0)) {
                        isOrderDetailsAdded = false;
                    }
                }

                if (isOrderDetailsAdded) {
                    return "Order Placed Successfully";
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

    public double calculateTotalPrice(int medicineId, int quantity) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        double unitPrice = 0.0;
        double totalPrice = 0.0;

        String sql = "SELECT price FROM medicine WHERE medicine_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, medicineId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            unitPrice = rst.getDouble("price");
        }

        totalPrice = unitPrice * quantity;
        return totalPrice;
    }
}
