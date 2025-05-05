package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManagerStockRequestController {

    @FXML
    private TableColumn<?, ?> colMedicineId;

    @FXML
    private TableColumn<?, ?> colReqQTY;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblGenericName;

    @FXML
    private TableView<?> tblRestock;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

}
