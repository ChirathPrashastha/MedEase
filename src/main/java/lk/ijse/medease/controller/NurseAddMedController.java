package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.dto.MedicineCategory;
import lk.ijse.medease.dto.MedicineDTO;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NurseAddMedController implements Initializable {

    private MedicineController medicineController;
    private InventoryController inventoryController;

    private final String medicineIdPattern = "^M\\d{4}$";
    private final String quantityPattern = "^[0-9]+$";
    private final String pricePattern = "^\\d+(\\.\\d{1,2})?$";
    private final String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    private boolean isInputsValid = true;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtEXP;

    @FXML
    private TextField txtGenericName;

    @FXML
    private TextField txtInventoryId;

    @FXML
    private TextField txtMedId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSection;

    @FXML
    private TextField txtSupplier;

    @FXML
    void expDateOnKeyReleased(KeyEvent event) {
        txtEXP.setStyle(txtEXP.getStyle() + "-fx-text-fill: white;");
        if (!(txtEXP.getText().matches(datePattern))) {
            txtEXP.setStyle(txtEXP.getStyle() + "-fx-text-fill: red;");
            isInputsValid = false;
        }else {
            isInputsValid = true;
        }
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        txtQuantity.setStyle(txtQuantity.getStyle() + "-fx-text-fill: white;");
        if (!(txtQuantity.getText().matches(quantityPattern))) {
            txtQuantity.setStyle(txtQuantity.getStyle() + "-fx-text-fill: red;");
            isInputsValid = false;
        }else {
            isInputsValid = true;
        }
    }

    @FXML
    void medicineIdOnKeyReleased(KeyEvent event) {
        txtMedId.setStyle(txtMedId.getStyle() + "-fx-text-fill: white;");
        if (!(txtMedId.getText().matches(medicineIdPattern))) {
            txtMedId.setStyle(txtMedId.getStyle() + "-fx-text-fill: red;");
            isInputsValid = false;
        }else {
            isInputsValid = true;
        }
    }

    @FXML
    void priceOnKeyReleased(KeyEvent event) {
        txtPrice.setStyle(txtPrice.getStyle() + "-fx-text-fill: white;");
        if(!(txtPrice.getText().matches(pricePattern))) {
            txtPrice.setStyle(txtPrice.getStyle() + "-fx-text-fill: red;");
            isInputsValid = false;
        }else {
            isInputsValid = true;
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!isInputsValid || txtMedId.getText().isEmpty() || txtGenericName.getText().isEmpty() || txtBrand.getText().isEmpty() || txtCategory.getText().isEmpty() || txtQuantity.getText().isEmpty() || txtSection.getText().isEmpty() || txtSupplier.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please check the fields", ButtonType.OK).showAndWait();
        }else {
            addMedicine();
        }
    }

    private void addMedicine() {

        InventoryDTO inventoryDTO = new InventoryDTO(txtInventoryId.getText(), Integer.parseInt(txtQuantity.getText()), txtSupplier.getText(), txtSection.getText());
        MedicineDTO medicineDTO = new MedicineDTO(txtMedId.getText(), txtGenericName.getText(), txtBrand.getText(), MedicineCategory.valueOf(txtCategory.getText()), Double.parseDouble(txtPrice.getText()), Date.valueOf(txtEXP.getText()), txtInventoryId.getText());

        try {
            String response = medicineController.addMedicine(medicineDTO, inventoryDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Medicine Adding");
            alert.setContentText(response);
            alert.showAndWait();

            clearFields();
            getNextIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        medicineController = new MedicineController();
        inventoryController = new InventoryController();

        getNextIds();
    }

    private void clearFields() {
        txtMedId.clear();
        txtGenericName.clear();
        txtBrand.clear();
        txtCategory.clear();
        txtPrice.clear();
        txtEXP.clear();
        txtInventoryId.clear();
        txtQuantity.clear();
        txtSupplier.clear();
        txtSection.clear();
    }

    private void getNextIds() {
        try {
            String inventoryId = inventoryController.getNextId();
            String medicineId = medicineController.getNextId();

            txtInventoryId.setText(inventoryId);
            txtMedId.setText(medicineId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
