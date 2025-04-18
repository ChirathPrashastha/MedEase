package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseIssueMedicationController implements Initializable {

    private PrescriptionController prescriptionController;
    private MedicineController medicineController;
    private InventoryController inventoryController;

    private boolean perDay = false;
    private boolean perWeek = false;
    private boolean perMonth = false;

    private boolean forDays = false;
    private boolean forWeeks = false;
    private boolean forMonths = false;

    private int frequency = 0;
    private int duration = 0;

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

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOrderOnAction(ActionEvent event) {

    }

    @FXML
    void durationDaysOnAction(ActionEvent event) {
        radioBtnForWeeks.setDisable(true);
        radioBtnForMonths.setDisable(true);

        forDays = true;
        duration = Integer.parseInt(txtDuration.getText());

        checkStockAvailability();
    }

    @FXML
    void durationMonthsOnAction(ActionEvent event) {
        radioBtnForDays.setDisable(true);
        radioBtnForWeeks.setDisable(true);

        forMonths = true;
        duration = Integer.parseInt(txtDuration.getText());

        checkStockAvailability();
    }

    @FXML
    void durationWeeksOnAction(ActionEvent event) {
        radioBtnForDays.setDisable(true);
        radioBtnForMonths.setDisable(true);

        forWeeks = true;
        duration = Integer.parseInt(txtDuration.getText());

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
        prescriptionController = new PrescriptionController();
        medicineController = new MedicineController();
        inventoryController = new InventoryController();

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

    private void checkStockAvailability() {
        int qty = 0;
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
}
