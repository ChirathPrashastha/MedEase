package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.dto.MedicineDTO;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NurseAddMedController implements Initializable {

    private MedicineController medicineController;
    private InventoryController inventoryController;

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
    void btnAddOnAction(ActionEvent event) {
        addMedicine();
    }

    private void addMedicine() {
        InventoryDTO inventoryDTO = new InventoryDTO(txtInventoryId.getText(), Integer.parseInt(txtQuantity.getText()), txtSupplier.getText(), txtSection.getText());
        MedicineDTO medicineDTO = new MedicineDTO(txtMedId.getText(), txtGenericName.getText(), txtBrand.getText(), txtCategory.getText(), Double.parseDouble(txtPrice.getText()), Date.valueOf(txtEXP.getText()), txtInventoryId.getText());

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
