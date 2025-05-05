package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.medease.dto.MedicineDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseRestockController implements Initializable {

    private MedicineController medicineController;
    private InventoryController inventoryController;

    @FXML
    private TextField txtInventoryId;

    @FXML
    private TextField txtMedId;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnProceedOnAction(ActionEvent event) {
        restock();
    }

    private void restock() {
        try {
            if (txtInventoryId.getText().isEmpty() || txtMedId.getText().isEmpty() || txtQuantity.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("FAILED TO RESTOCK");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();

            }else {
                String response = inventoryController.restock(txtInventoryId.getText(), Integer.parseInt(txtQuantity.getText()), txtMedId.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Restock");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait();

                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getInventoryIdOnAction(ActionEvent event) {
        try {
            ArrayList<MedicineDTO> medicineDTOs = medicineController.searchMedicine(txtMedId.getText());
            txtInventoryId.setText(medicineDTOs.get(0).getInventoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        medicineController = new MedicineController();
        inventoryController = new InventoryController();
    }

    private void clearFields() {
        txtMedId.clear();
        txtInventoryId.clear();
        txtQuantity.clear();
    }
}
