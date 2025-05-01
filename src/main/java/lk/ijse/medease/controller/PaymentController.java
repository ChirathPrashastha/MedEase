package lk.ijse.medease.controller;

import lk.ijse.medease.dto.PaymentDTO;
import lk.ijse.medease.model.PaymentModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentController {
    private PaymentModel paymentModel;

    public PaymentController() {
        paymentModel = new PaymentModel();
    }

    public String addPayment(PaymentDTO paymentDTO) throws SQLException {
        String response = paymentModel.addPayment(paymentDTO);
        return response;
    }

    public String updatePayment(PaymentDTO paymentDTO) throws SQLException {
        String response = paymentModel.updatePayment(paymentDTO);
        return response;
    }

    public String deletePayment(String paymentId) throws SQLException {
        String response = paymentModel.deletePayment(paymentId);
        return response;
    }

    public PaymentDTO searchPayment(String paymentId) throws SQLException {
        PaymentDTO paymentDTO = paymentModel.searchPayment(paymentId);
        return paymentDTO;
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOs = paymentModel.getAllPayment();
        return paymentDTOs;
    }

    public String getNextId() throws SQLException {
        return paymentModel.getNextId();
    }
}
