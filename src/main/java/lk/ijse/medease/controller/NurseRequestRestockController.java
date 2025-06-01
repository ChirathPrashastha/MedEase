package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.dto.RestockDTO;
import lk.ijse.medease.dto.RestockStatus;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseRequestRestockController implements Initializable {

    private final String medicineIdPattern = "^M\\d{4}$";
    private final String quantityPattern = "^[0-9]+$";

    private MedicineController medicineController;
    private RestockController restockController;
    private InventoryController inventoryController;

    @FXML
    private Label lblCurrentQty;

    @FXML
    private Label lblMed;

    @FXML
    private Label lblInventoryId;

    @FXML
    private TextField txtMedId;

    @FXML
    private TextField txtReqQty;

    @FXML
    void btnRequestOnAction(ActionEvent event) {
        if (txtMedId.getText().equals("") || txtReqQty.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill all the required fields!");
            alert.showAndWait();
        }else {
            requestRestock();
        }
    }

    @FXML
    void medicineDetailsOnKeyReleased(KeyEvent event) {
        txtMedId.setStyle(txtMedId.getStyle() + "-fx-text-fill: white;");
        if (!(txtMedId.getText().matches(medicineIdPattern))){
            txtMedId.setStyle(txtMedId.getStyle() + "-fx-text-fill: red;");
        }else {
            loadMedicineDetails();
        }
    }

    @FXML
    void quantityOnKeyReleased(KeyEvent event) {
        txtReqQty.setStyle(txtReqQty.getStyle() + "-fx-text-fill: white;");
        if (!(txtReqQty.getText().matches(quantityPattern))){
            txtReqQty.setStyle(txtReqQty.getStyle() + "-fx-text-fill: red;");
        }
    }

    private void loadMedicineDetails() {
        try {
            ArrayList<MedicineDTO> medicineDTOs = medicineController.searchMedicine(txtMedId.getText());
            if (medicineDTOs.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Medicine Not Found");
                alert.showAndWait();
            }else {
                MedicineDTO medicineDTO = medicineDTOs.getFirst();

                int currentQty = inventoryController.getQuantityByInventoryId(medicineDTO.getInventoryId());

                lblMed.setText(medicineDTO.getBrand());
                lblCurrentQty.setText(String.valueOf(currentQty));
                lblInventoryId.setText(medicineDTO.getInventoryId());
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("DATABASE ERROR");
            alert.setContentText("Failed to retrieve medicine details!");
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("SYSTEM ERROR");
            alert.setContentText("Required class not found!");
            alert.showAndWait();
        }
    }

    private void requestRestock() {

        try {
            String restockId = restockController.getNextId();

            RestockDTO restockDTO = new RestockDTO(restockId, txtMedId.getText(), Integer.parseInt(txtReqQty.getText()), RestockStatus.PENDING);

            try {
                String response = restockController.requestRestock(restockDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait();

                clearFields();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("DATABASE ERROR");
                alert.setContentText("Failed to Access Restock Table");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Failed to Generate Restock ID");
            alert.showAndWait();
        }catch (ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Failed to Generate Restock ID");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        medicineController = new MedicineController();
        restockController = new RestockController();
        inventoryController = new InventoryController();
    }

    private void clearFields() {
        txtMedId.clear();
        txtReqQty.clear();
        lblMed.setText("");
        lblInventoryId.setText("");
        lblCurrentQty.setText("");
    }
}
