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
import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PaymentDTO;
import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.tm.PaymentTM;
//import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReceptionPaymentController implements Initializable {

    private PaymentController paymentController;
    private PrescriptionController prescriptionController;
    private PatientOrderController patientOrderController;

    private String paymentMethod;
    private String prescriptionId;
    private String orderId;
    private String patientName;
    private double subTotal;

    private final String appointmentIdPattern = "^A\\d{4}$";
    private final String amountPattern = "^\\d+(\\.\\d{1,2})?$";

    private double bookingAmount = 1500.0;

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
        }else {
            try {
                PrescriptionDTO prescriptionDTO = prescriptionController.getPrescriptionByAppointmentId(txtAppointmentId.getText());
                if (prescriptionDTO == null) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).showAndWait();
                }else {
                    setAmount(prescriptionDTO.getPrescriptionId());
                    prescriptionId = prescriptionDTO.getPrescriptionId();
                    patientName = prescriptionController.getPatientNameByPrescriptionId(prescriptionId);

                    orderId = patientOrderController.getOrderIdByPrescriptionId(prescriptionId);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
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

            if (response.equals("success")){
                new Alert(Alert.AlertType.INFORMATION, "Payment Added Successfully").showAndWait();
                generateBill();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add the Payment").showAndWait();
            }

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
        prescriptionController = new PrescriptionController();
        patientOrderController = new PatientOrderController();

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

    private void setAmount(String prescriptionId) {
        try {
            double orderAmount = patientOrderController.getAmountByPrescriptionId(prescriptionId);
            subTotal = orderAmount + bookingAmount;
            txtAmount.setText(String.valueOf(subTotal));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateBill() {
        try {
            JasperReport bill = JasperCompileManager.compileReport(getClass().getResourceAsStream("/billing/PatientInvoice.jrxml"));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("pPrescriptionId", prescriptionId);
            parameters.put("pDate", LocalDate.now().toString());
            parameters.put("pOrderId", orderId);
            parameters.put("pName", patientName);
            parameters.put("pSubTotal", String.valueOf(subTotal));

            InputStream logoStream = getClass().getResourceAsStream("/images/billLogo.png");

            parameters.put("pLogo", logoStream);

            JasperPrint print = JasperFillManager.fillReport(bill, parameters, connection);
            JasperViewer.viewReport(print, false );

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
