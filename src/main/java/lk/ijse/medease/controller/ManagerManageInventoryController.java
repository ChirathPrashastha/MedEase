package lk.ijse.medease.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManagerManageInventoryController {

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colEXP;

    @FXML
    private TableColumn<?, ?> colInventoryId;

    @FXML
    private TableColumn<?, ?> colMedId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSection;

    @FXML
    private TableColumn<?, ?> colSupplier;

    @FXML
    private TableView<?> tblMedicine;

}
