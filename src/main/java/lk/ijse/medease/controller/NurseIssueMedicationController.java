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
import javafx.stage.Stage;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.dto.PatientOrderDetailsDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseIssueMedicationController implements Initializable {
    public static NurseIssueMedicationController nurseIssueMedicationController;

    private PrescriptionController prescriptionController;
    private MedicineController medicineController;
    private InventoryController inventoryController;
    private PatientOrderController patientOrderController;

    public ObservableList<PatientOrderDetailsDTO> orderDetailsList; // for the table
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
    private int qtyWanted = 0;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colDosage;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colDuration;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colFrequency;

    @FXML
    private TableColumn<PrescriptionMedicineDTO, String> colName;

    @FXML
    private Label lblItemCount;

    @FXML
    private Label lblMedId;

    @FXML
    private Label lblMedName;

    @FXML
    private Label lblSubTotal;

    @FXML
    private TableView<PrescriptionMedicineDTO> tblPresMed;

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
    void btnAddToOrderOnAction(ActionEvent event) {

        double totalPrice = 0.0;
        double unitPrice = 0.0;
        try {
            totalPrice = patientOrderController.calculateTotalPrice(Integer.parseInt(lblMedId.getText()), qty);
        } catch (Exception e) {
            e.printStackTrace();
        }

        unitPrice = totalPrice / qty;

        PatientOrderDetailsDTO orderDetailsDTO = new PatientOrderDetailsDTO(Integer.parseInt(txtOrderId.getText()), Integer.parseInt(lblMedId.getText()), unitPrice, qty, totalPrice);

        orderDetailsList.add(orderDetailsDTO); // for the table

        int itemCount = orderDetailsList.size();

        lblItemCount.setText(String.valueOf(itemCount));

        double subTotal = 0.0;
        for (PatientOrderDetailsDTO dto : orderDetailsList) {
            subTotal += dto.getTotalPrice();
        }

        lblSubTotal.setText(String.valueOf(subTotal));

        orderDetailsArray.add(orderDetailsDTO); // for the db

        if (itemCount > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ADDING TO ORDER");
            alert.setHeaderText("SUCCESS");
            alert.setContentText("Medicine Added Successfully to the Order");
            alert.showAndWait();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

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
        loadPrescriptionTable(Integer.parseInt(txtPrescriptionId.getText()));
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

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tblPresMed.setRowFactory(tv -> {
            TableRow<PrescriptionMedicineDTO> row = new TableRow<>();
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
            int medicineId = medicineController.getMedicineIdByMedicineName(medicineName);
            if (medicineId != -1) {
                lblMedId.setText(String.valueOf(medicineId));
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

    private void loadPrescriptionTable(int prescriptionId) {
        try {
            ArrayList<PrescriptionMedicineDTO> presMedDTOs = prescriptionController.getPrescriptionById(prescriptionId);
            ObservableList<PrescriptionMedicineDTO> presMedList = FXCollections.observableArrayList(presMedDTOs);
            tblPresMed.setItems(presMedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkExpiration() {
        if (forDays) {
            try {
                MedicineDTO medicineDTO = medicineController.checkExpiration(Integer.parseInt(lblMedId.getText()), Integer.parseInt(txtDuration.getText()), "days");
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
                MedicineDTO medicineDTO = medicineController.checkExpiration(Integer.parseInt(lblMedId.getText()), Integer.parseInt(txtDuration.getText()), "weeks");
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
                MedicineDTO medicineDTO = medicineController.checkExpiration(Integer.parseInt(lblMedId.getText()), Integer.parseInt(txtDuration.getText()), "months");
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
        int inventoryId;
        int availableQty = 0;

        if ((perDay == true && forDays == true) || (perWeek == true && forWeeks == true) || (perMonth == true && forMonths == true)) {
            qty = frequency * duration;
            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(Integer.parseInt(lblMedId.getText()));
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (perDay == true && forWeeks == true) { // per day, for weeks
            qty = (frequency * 7) * duration;
            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(Integer.parseInt(lblMedId.getText()));
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (perDay == true && forMonths == true) { // per day, for months
            qty = (frequency * 30) * duration;

            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(Integer.parseInt(lblMedId.getText()));
                availableQty = inventoryController.getQuantityByInventoryId(inventoryId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (perWeek == true && forMonths == true) { // per week, for months
            qty = (frequency * 4) * duration;
            try {
                inventoryId = medicineController.getInventoryIdByMedicineId(Integer.parseInt(lblMedId.getText()));
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
}
