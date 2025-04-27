package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NurseManageInventoryController {

    @FXML
    private TableColumn<?, ?> colInventoryId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSection;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private Label lblMID;

    @FXML
    private Label lblMedName;

    @FXML
    private TableView<?> tblInventory;

    @FXML
    private TextField txtInventoryId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSection;

    @FXML
    private TextField txtSupplierId;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnRequestRestockOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void getMedicineDetailsOnAction(ActionEvent event) {

    }

}