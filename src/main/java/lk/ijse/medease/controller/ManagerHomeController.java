package lk.ijse.medease.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerHomeController implements Initializable {

    private final AppointmentController appointmentController = new AppointmentController();
    private final PatientOrderController patientOrderController = new PatientOrderController();
    private final PaymentController paymentController = new PaymentController();

    @FXML
    private Label lblIncome;

    @FXML
    private Label lblLastDayAppCount;

    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblTdyAppCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            double subPayment = paymentController.getTodayPaymentAmount();
            double subOrderAmount = patientOrderController.getTodayTotal();
            double income = subPayment + subOrderAmount;
            lblIncome.setText("RS : " + income);

            int orderCount = patientOrderController.getTodayOrderCount();
            lblOrderCount.setText(String.valueOf(orderCount));

            int lastDayApp = appointmentController.getLastDayAppointmentCount();
            int todayApp = appointmentController.getTodayAppointmentCount();
            lblLastDayAppCount.setText(String.valueOf(lastDayApp));
            lblTdyAppCount.setText(String.valueOf(todayApp));


        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load home page elements").showAndWait();
        }
    }

}
