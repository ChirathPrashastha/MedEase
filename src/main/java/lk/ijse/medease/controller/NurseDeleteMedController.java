package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.medease.dto.MedicineDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseDeleteMedController implements Initializable {

    private MedicineController medicineController;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblInventoryId;

    @FXML
    private Label lblMedName;

    @FXML
    private TextField txtMedId;

    @FXML
    void btnDeleteMedicineOnAction(ActionEvent event) {
        try {
            String response = medicineController.deleteMedicine(txtMedId.getText(), lblInventoryId.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Medicine Deletion");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();

            clearFields();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void medicineDetailsOnAction(ActionEvent event) {
        try {
            ArrayList<MedicineDTO> medicineDTOs = medicineController.searchMedicine(txtMedId.getText());
            MedicineDTO medicineDTO = medicineDTOs.get(0);
            lblMedName.setText(medicineDTO.getGenericName());
            lblBrand.setText(medicineDTO.getBrand());
            lblInventoryId.setText(medicineDTO.getInventoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        medicineController = new MedicineController();
    }

    private void clearFields(){
        txtMedId.clear();
        lblMedName.setText("");
        lblBrand.setText("");
        lblInventoryId.setText("");
    }

}
