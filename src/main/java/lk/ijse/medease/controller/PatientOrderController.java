package lk.ijse.medease.controller;

import lk.ijse.medease.dto.PatientOrderDTO;
import lk.ijse.medease.dto.PatientOrderDetailsDTO;
import lk.ijse.medease.model.PatientOrderModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class PatientOrderController {

    private PatientOrderModel patientOrderModel;

    PatientOrderController() {
        patientOrderModel = new PatientOrderModel();
    }

    public String placeOrder(PatientOrderDTO orderDTO, ArrayList<PatientOrderDetailsDTO> orderDetailsArray) throws SQLException, ClassNotFoundException {
        String response = patientOrderModel.placeOrder(orderDTO, orderDetailsArray);
        return response;
    }

    public double calculateTotalPrice(String medicineId, int quantity) throws SQLException, ClassNotFoundException {
        double totalPrice = patientOrderModel.calculateTotalPrice(medicineId, quantity);
        return totalPrice;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return patientOrderModel.getNextId();
    }
}
