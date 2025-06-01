package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.dto.RestockDTO;
import lk.ijse.medease.dto.RestockStatus;
import lk.ijse.medease.dto.tm.RestockTM;
import lk.ijse.medease.util.RestockEmailSender;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerStockRequestController implements Initializable {

    private final RestockEmailSender restockEmailSender = new RestockEmailSender();

    private RestockController restockController;
    private MedicineController medicineController;

    String medicineId = null;

    private String orderingRestockId;

    @FXML
    private TableColumn<RestockTM, String> colMedicineId;

    @FXML
    private TableColumn<RestockTM, Number> colReqQTY;

    @FXML
    private TableColumn<RestockTM, String> colRestockId;

    @FXML
    private TableColumn<RestockTM, RestockStatus> colStatus;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblGenericName;

    @FXML
    private TableView<RestockTM> tblRestock;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        orderFromSuppliers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restockController = new RestockController();
        medicineController = new MedicineController();

        colRestockId.setCellValueFactory(new PropertyValueFactory<>("restockId"));
        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colReqQTY.setCellValueFactory(new PropertyValueFactory<>("requestedQuantity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTable();

        tblRestock.setRowFactory(tv -> {
            TableRow<RestockTM> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    medicineId = row.getItem().getMedicineId();
                    String restockId = row.getItem().getRestockId();

                    loadMedicineDetails(medicineId);
                    orderingRestockId = restockId;
                }
            });
            return row;
        });
    }

    private void orderFromSuppliers() {
        try {
            String response = restockController.orderStock(orderingRestockId);

            if (response.equals("success")) {
                restockEmailSender.restockEmail(medicineId);
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Order Restock").showAndWait();
            }

            loadTable();
            clearFields();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadMedicineDetails(String medicineId) {
        try {
            ArrayList<MedicineDTO> medicineDTOS = medicineController.searchMedicine(medicineId);
            lblGenericName.setText(medicineDTOS.getFirst().getGenericName());
            lblBrand.setText(medicineDTOS.getFirst().getBrand());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unable to load medicine details");
            alert.showAndWait();
        }
    }

    private void loadTable() {
        try {
            ArrayList<RestockDTO> restockDTOS = restockController.getRestockList();
            ObservableList<RestockTM> restockObList = FXCollections.observableArrayList();

            for (RestockDTO dto : restockDTOS) {
                RestockTM restockTM = new RestockTM(dto.getRestockId(), dto.getMedicineId(), dto.getRequestedQuantity(), dto.getStatus());
                restockObList.add(restockTM);
            }

            tblRestock.setItems(restockObList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void clearFields(){
        orderingRestockId = null;
        lblGenericName.setText("");
        lblBrand.setText("");
        txtQuantity.setText("");
    }
}
