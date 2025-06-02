package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.dto.PatientOrderDTO;
import lk.ijse.medease.dto.PatientOrderDetailsDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;
import lk.ijse.medease.dto.tm.PatientOrderDetailsTM;
import lk.ijse.medease.dto.tm.PrescriptionMedicineTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseIssueMedicationController implements Initializable {

    private final String prescriptionIdPattern = "^RX\\d{4}$";

    private boolean isRXIdValid = true;

    public static NurseIssueMedicationController nurseIssueMedicationController;

    private PrescriptionController prescriptionController;
    private MedicineController medicineController;
    private InventoryController inventoryController;
    private PatientOrderController patientOrderController;

    public ObservableList<PatientOrderDetailsTM> orderDetailsList; // for the table
    private ArrayList<PatientOrderDetailsDTO> orderDetailsArray; // for the database

    private int qty;

    private boolean perDay = false;
    private boolean perWeek = false;
    private boolean perMonth = false;

    private boolean forDays = false;
    private boolean forWeeks = false;
    private boolean forMonths = false;

    private int frequency = 0;
    private int duration = 0;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colDosage;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colDuration;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colFrequency;

    @FXML
    private TableColumn<PrescriptionMedicineTM, String> colName;

    @FXML
    private Label lblItemCount;

    @FXML
    private Label lblMedId;

    @FXML
    private Label lblMedName;

    @FXML
    private Label lblSubTotal;

    @FXML
    private TableView<PrescriptionMedicineTM> tblPresMed;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFrequency;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPrescriptionId;

    @FXML
    private RadioButton radioBtnPerDay;

    @FXML
    private RadioButton radioBtnPerMonth;

    @FXML
    private RadioButton radioBtnPerWeek;


    @FXML
    private RadioButton radioBtnForDays;

    @FXML
    private RadioButton radioBtnForMonths;

    @FXML
    private RadioButton radioBtnForWeeks;

    @FXML
    void presIdOnKeyReleased(KeyEvent event) {
        txtPrescriptionId.setStyle(txtPrescriptionId.getStyle() + "-fx-text-fill: white;");
        if (!(txtPrescriptionId.getText().matches(prescriptionIdPattern))) {
            txtPrescriptionId.setStyle(txtPrescriptionId.getStyle() + "-fx-text-fill: red;");
            isRXIdValid = false;
        }else {
            isRXIdValid = true;
        }
    }

    @FXML
    void btnAddToOrderOnAction(ActionEvent event) {

        double totalPrice = 0.0;
        double unitPrice = 0.0;
        try {
            totalPrice = patientOrderController.calculateTotalPrice(lblMedId.getText(), qty);
        } catch (Exception e) {
            e.printStackTrace();
        }

        unitPrice = totalPrice / qty;

        PatientOrderDetailsDTO orderDetailsDTO = new PatientOrderDetailsDTO(txtOrderId.getText(), lblMedId.getText(), unitPrice, qty, totalPrice);
        PatientOrderDetailsTM patientOrderDetailsTM = new PatientOrderDetailsTM(orderDetailsDTO.getOrderId(), orderDetailsDTO.getMedicineId(), orderDetailsDTO.getUnitPrice(), orderDetailsDTO.getQuantity(), orderDetailsDTO.getTotalPrice());

        orderDetailsList.add(patientOrderDetailsTM); // for the table

        int itemCount = orderDetailsList.size();

        lblItemCount.setText(String.valueOf(itemCount));

        double subTotal = 0.0;

        for (PatientOrderDetailsTM tm : orderDetailsList) {
            subTotal += tm.getTotalPrice();
        }

        lblSubTotal.setText(String.valueOf(subTotal));

        orderDetailsArray.add(orderDetailsDTO); // for the db

        if (itemCount > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ADDING TO ORDER");
            alert.setHeaderText("SUCCESS");
            alert.setContentText("Medicine Added Successfully to the Order");
            alert.showAndWait();

            fullyClearFields();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        placeOrder();
    }

    @FXML
    void btnViewOrderOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/PatientOrderDetails.fxml"));
        Stage stage = new Stage();

        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("Order Details");
        stage.show();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void durationDaysOnAction(ActionEvent event) {
        radioBtnForWeeks.setDisable(true);
        radioBtnForMonths.setDisable(true);

        forDays = true;
        duration = Integer.parseInt(txtDuration.getText());

        checkExpiration();
        checkStockAvailability();
    }

    @FXML
    void durationMonthsOnAction(ActionEvent event) {
        radioBtnForDays.setDisable(true);
        radioBtnForWeeks.setDisable(true);

        forMonths = true;
        duration = Integer.parseInt(txtDuration.getText());

        checkExpiration();
        checkStockAvailability();
    }

    @FXML
    void durationWeeksOnAction(ActionEvent event) {
        radioBtnForDays.setDisable(true);
        radioBtnForMonths.setDisable(true);

        forWeeks = true;
        duration = Integer.parseInt(txtDuration.getText());

        checkExpiration();
        checkStockAvailability();
    }

    @FXML
    void frequencyPerMonthOnAction(ActionEvent event) {
        radioBtnPerDay.setDisable(true);
        radioBtnPerWeek.setDisable(true);

        perMonth = true;
        frequency = Integer.parseInt(txtFrequency.getText());
    }

    @FXML
    void frequencyPerWeekOnAction(ActionEvent event) {
        radioBtnPerDay.setDisable(true);
        radioBtnPerMonth.setDisable(true);

        perWeek = true;
        frequency = Integer.parseInt(txtFrequency.getText());
    }

    @FXML
    void frequnecyPerDayOnAction(ActionEvent event) {
        radioBtnPerMonth.setDisable(true);
        radioBtnPerWeek.setDisable(true);

        perDay = true;
        frequency = Integer.parseInt(txtFrequency.getText());
    }

    @FXML
    void searchPrescriptionOnAction(ActionEvent event) {
        if (isRXIdValid){
            loadPrescriptionTable(txtPrescriptionId.getText());
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Prescription ID").showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nurseIssueMedicationController = this;

        orderDetailsList = FXCollections.observableArrayList();
        orderDetailsArray = new ArrayList<>();

        prescriptionController = new PrescriptionController();
        medicineController = new MedicineController();
        inventoryController = new InventoryController();
        patientOrderController = new PatientOrderController();

        try {
            String orderId = patientOrderController.getNextId();
            txtOrderId.setText(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tblPresMed.setRowFactory(tv -> {
            TableRow<PrescriptionMedicineTM> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    String medicineName = row.getItem().getName();
                    loadMedicineDetails(medicineName);
                }
            });
            return row;
        });
    }

    private void loadMedicineDetails(String medicineName) {

        try {
            String medicineId = medicineController.getMedicineIdByMedicineName(medicineName);
            if (medicineId != null) {
                lblMedId.setText(medicineId);
                lblMedName.setText(medicineName);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("FAILED TO LOAD MEDICINE");
                alert.setContentText("Medicine not found");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPrescriptionTable(String prescriptionId) {
        try {
            ArrayList<PrescriptionMedicineDTO> presMedDTOs = prescriptionController.getPrescriptionById(prescriptionId);
            ObservableList<PrescriptionMedicineTM> presMedList = FXCollections.observableArrayList();

            for (PrescriptionMedicineDTO dto : presMedDTOs){
                PrescriptionMedicineTM prescriptionMedicineTM = new PrescriptionMedicineTM(dto.getName(), dto.getDosage(), dto.getFrequency(), dto.getDuration());
                presMedList.add(prescriptionMedicineTM);
            }

            tblPresMed.setItems(presMedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkExpiration() {
        if (forDays) {
            try {
                MedicineDTO medicineDTO = medicineController.checkExpiration(lblMedId.getText(), Integer.parseInt(txtDuration.getText()), "days");
                if (medicineDTO != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("EXPIRATION");
                    alert.setContentText( medicineDTO.getBrand() +" is about to be expired between the duration \n" + medicineDTO.getExpirationDate());
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (forWeeks) {
            try {
                MedicineDTO medicineDTO = medicineController.checkExpiration(lblMedId.getText(), Integer.parseInt(txtDuration.getText()), "weeks");
                if (medicineDTO != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("EXPIRATION");
                    alert.setContentText( medicineDTO.getBrand() +" is about to be expired between the duration \n" + medicineDTO.getExpirationDate());
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (forMonths) {
            try {
                MedicineDTO medicineDTO = medicineController.checkExpiration(lblMedId.getText(), Integer.parseInt(txtDuration.getText()), "months");
                if (medicineDTO != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("EXPIRATION");
                    alert.setContentText( medicineDTO.getBrand() +" is about to be expired between the duration \n" + medicineDTO.getExpirationDate());
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void checkStockAvailability() {
        qty = 0;
        String inventoryId;
        int availableQty = 0;

        if ((perDay == true && forDays == true) || (perWeek == true && forWeeks == true) || (perMonth == true && forMonths == true)) {
            qty = frequency * duration;
            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(lblMedId.getText());
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (perDay == true && forWeeks == true) { // per day, for weeks
            qty = (frequency * 7) * duration;
            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(lblMedId.getText());
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (perDay == true && forMonths == true) { // per day, for months
            qty = (frequency * 30) * duration;

            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(lblMedId.getText());
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (perWeek == true && forMonths == true) { // per week, for months
            qty = (frequency * 4) * duration;
            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(lblMedId.getText());
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("FAILED TO CHECK STOCK AVAILABILITY");
            alert.setContentText("Please check your information");
            alert.showAndWait();
        }

        if (availableQty <= qty) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("OUT OF QUANTITY");
            alert.setContentText("Reached out the current quantity" + "\n" + "Available Quantity : " + availableQty + "\n" + "Required Quantity : " + qty);
            alert.showAndWait();

        } else if ((availableQty == -1) || (qty == -1)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("FAILED TO CHECK AVAILABILITY");
            alert.setContentText("Please check your information");
            alert.showAndWait();
        }
    }

    private void placeOrder() {
        PatientOrderDTO orderDTO = new PatientOrderDTO(txtOrderId.getText(), txtPrescriptionId.getText(), Double.parseDouble(lblSubTotal.getText()));
        try {
            String response = patientOrderController.placeOrder(orderDTO, orderDetailsArray);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ORDER PLACING");
            alert.setHeaderText("ORDER");
            alert.setContentText(response);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        format();
    }

    private void clearFields(){
        txtDuration.clear();
        txtFrequency.clear();

        radioBtnPerDay.setSelected(false);
        radioBtnPerMonth.setSelected(false);
        radioBtnPerWeek.setSelected(false);

        perDay = false;
        perMonth = false;
        perWeek = false;

        radioBtnPerDay.setDisable(false);
        radioBtnPerMonth.setDisable(false);
        radioBtnPerWeek.setDisable(false);

        radioBtnForDays.setSelected(false);
        radioBtnForMonths.setSelected(false);
        radioBtnForWeeks.setSelected(false);

        forDays = false;
        forWeeks = false;
        forMonths = false;

        radioBtnForDays.setDisable(false);
        radioBtnForWeeks.setDisable(false);
        radioBtnForMonths.setDisable(false);
    }

    private void fullyClearFields(){
        lblMedId.setText("");
        lblMedName.setText("");

        clearFields();
    }

    private void format(){
        orderDetailsList.clear();
        orderDetailsArray.clear();

        tblPresMed.getItems().clear();

        fullyClearFields();
        txtOrderId.clear();
        txtPrescriptionId.clear();

        lblItemCount.setText("");
        lblSubTotal.setText("");
    }
}
