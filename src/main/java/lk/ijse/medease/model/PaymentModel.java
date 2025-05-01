package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public String addPayment(PaymentDTO paymentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment (payment_id, appointment_id, amount, payment_method) VALUES (?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentDTO.getPaymentId());
        statement.setString(2, paymentDTO.getAppointmentId());
        statement.setDouble(3, paymentDTO.getAmount());
        statement.setString(4, paymentDTO.getPaymentMethod());

        return statement.executeUpdate() > 0 ? "Payment Proceed Successfully" : "Payment Failed";
    }

    public String updatePayment(PaymentDTO paymentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE payment SET appointment_id = ?, amount = ?, payment_method = ? WHERE payment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentDTO.getAppointmentId());
        statement.setDouble(2, paymentDTO.getAmount());
        statement.setString(3, paymentDTO.getPaymentMethod());
        statement.setString(4, paymentDTO.getPaymentId());

        return statement.executeUpdate() > 0 ? "Payment Updated Successfully" : "Failed to Update the Payment";
    }

    public String deletePayment(String paymentId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM payment WHERE payment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentId);

        return statement.executeUpdate() > 0 ? "Payment Deleted Successfully" : "Failed to Delete the Payment";
    }

    public PaymentDTO searchPayment(String paymentId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment WHERE payment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(rst.getString("payment_id"),rst.getString("appointment_id"), rst.getDouble("amount"), rst.getDate("paid_date") ,rst.getString("payment_method"));
            return paymentDTO;
        } else {
            return null;
        }
    }

    public ArrayList<PaymentDTO> getAllPayment() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(rst.getString("payment_id"), rst.getString("appointment_id"), rst.getDouble("amount"), rst.getDate("paid_date"), rst.getString("payment_method"));
            paymentDTOs.add(paymentDTO);
        }
        return paymentDTOs;
    }

    public String getNextId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("payment_id");
            String lastIdNumberString = lastAppId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("PY"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "PY0001";
    }
}
