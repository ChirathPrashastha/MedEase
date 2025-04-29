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

public class NurseUpdateMedController implements Initializable {

    private MedicineController medicineController;

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
    void btnUpdateOnAction(ActionEvent event) {
        updateMedicine();
    }

    private void updateMedicine() {
        InventoryDTO inventoryDTO = new InventoryDTO(txtInventoryId.getText(), Integer.parseInt(txtQuantity.getText()), txtSupplier.getText(), txtSection.getText());
        MedicineDTO medicineDTO = new MedicineDTO(txtMedId.getText(), txtGenericName.getText(), txtBrand.getText(), txtCategory.getText(), Double.parseDouble(txtPrice.getText()), Date.valueOf(txtEXP.getText()), txtInventoryId.getText());

        try {
            String response = medicineController.updateMedicine(medicineDTO, inventoryDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Medicine");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();

            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getInventoryIdOnAction(ActionEvent event) {
        try {
            String inventoryId = medicineController.getInventoryIdByMedicineId(txtMedId.getText());
            txtInventoryId.setText(inventoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        medicineController = new MedicineController();
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
}
