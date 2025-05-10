package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.medease.dto.PaymentDTO;
import lk.ijse.medease.dto.tm.PaymentTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceptionPaymentController implements Initializable {

    private PaymentController paymentController;
    private String paymentMethod;

    private final String appointmentIdPattern = "^A\\d{4}$";
    private final String amountPattern = "^\\d+(\\.\\d{1,2})?$";

    @FXML
    private CheckBox checkCard;

    @FXML
    private CheckBox checkCash;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colAppId;

    @FXML
    private TableColumn<PaymentTM, Date> colPaidDate;

    @FXML
    private TableColumn<PaymentTM, String> colPayId;

    @FXML
    private TableColumn<PaymentTM, String> colPayMethod;


    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtAppointmentId;

    @FXML
    private TextField txtPaymentId;

    @FXML
    void appointmentIdOnKeyReleased(KeyEvent event) {
        txtAppointmentId.setStyle(txtAppointmentId.getStyle() + "-fx-text-fill: white;");
        if (!(txtAppointmentId.getText().matches(appointmentIdPattern))){
            txtAppointmentId.setStyle(txtAppointmentId.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void amountOnKeyReleased(KeyEvent event) {
        txtAmount.setStyle(txtAmount.getStyle() + "-fx-text-fill: white;");
        if (!(txtAmount.getText().matches(amountPattern))){
            txtAmount.setStyle(txtAmount.getStyle() + "-fx-text-fill: red;");
        }
    }

    @FXML
    void btnProceedOnAction(ActionEvent event) {
        if (txtAppointmentId.getText().matches(appointmentIdPattern) && txtAmount.getText().matches(amountPattern)){
            addPayment();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Appointment ID Or Amount Entered");
            alert.showAndWait();
        }
    }

    @FXML
    void cardOnAction(ActionEvent event) {
        paymentMethod = "Card";
    }

    @FXML
    void cashOnAction(ActionEvent event) {
        paymentMethod = "Cash";
    }

    private void addPayment() {
        PaymentDTO paymentDTO = new PaymentDTO(txtPaymentId.getText(), txtAppointmentId.getText(), Double.parseDouble(txtAmount.getText()), paymentMethod);

        try {
            String response = paymentController.addPayment(paymentDTO);
            loadData();
            clearFields();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Payment");
            alert.setHeaderText("PAYMENT");
            alert.setContentText(response);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Payment");
            alert.setHeaderText("PAYMENT");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paymentController = new PaymentController();

        try {
            String paymentId = paymentController.getNextId();
            txtPaymentId.setText(paymentId);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to Generate Payment ID");
            alert.showAndWait();
        }

        colPayId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAppId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaidDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        colPayMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        loadData();
    }

    private void loadData() {

        try {
            ArrayList<PaymentDTO> paymentDTOs = paymentController.getAllPayments();
            ObservableList<PaymentTM> paymentTMObList = FXCollections.observableArrayList();

            for (PaymentDTO dto : paymentDTOs){
                PaymentTM paymentTM = new PaymentTM(dto.getPaymentId(), dto.getAppointmentId(), dto.getAmount(), dto.getPaidDate(), dto.getPaymentMethod());
                paymentTMObList.add(paymentTM);
            }

            tblPayment.setItems(paymentTMObList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields(){
        txtPaymentId.clear();
        txtAmount.clear();
        txtAppointmentId.clear();
        checkCard.setSelected(false);
        checkCash.setSelected(false);
    }
}
