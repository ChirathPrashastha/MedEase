package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public String addPayment(PaymentDTO paymentDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment (appointment_id, amount, payment_method) VALUES (?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, paymentDTO.getAppointmentId());
        statement.setDouble(2, paymentDTO.getAmount());
        statement.setString(3, paymentDTO.getPaymentMethod());

        return statement.executeUpdate() > 0 ? "Payment Proceed Successfully" : "Payment Failed";
    }

    public String updatePayment(PaymentDTO paymentDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE payment SET appointment_id = ?, amount = ?, payment_method = ? WHERE payment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, paymentDTO.getAppointmentId());
        statement.setDouble(2, paymentDTO.getAmount());
        statement.setString(3, paymentDTO.getPaymentMethod());
        statement.setInt(4, paymentDTO.getPaymentId());

        return statement.executeUpdate() > 0 ? "Payment Updated Successfully" : "Failed to Update the Payment";
    }

    public String deletePayment(int paymentId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM payment WHERE payment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, paymentId);

        return statement.executeUpdate() > 0 ? "Payment Deleted Successfully" : "Failed to Delete the Payment";
    }

    public PaymentDTO searchPayment(int paymentId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment WHERE payment_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, paymentId);

        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(rst.getInt("payment_id"),rst.getInt("appointment_id"), rst.getDouble("amount"), rst.getDate("paid_date") ,rst.getString("payment_method"));
            return paymentDTO;
        } else {
            return null;
        }
    }

    public ArrayList<PaymentDTO> getAllPayment() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(rst.getInt("payment_id"), rst.getInt("appointment_id"), rst.getDouble("amount"), rst.getDate("paid_date"), rst.getString("payment_method"));
            paymentDTOs.add(paymentDTO);
        }
        return paymentDTOs;
    }
}
