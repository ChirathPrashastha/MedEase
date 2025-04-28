package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NurseManageInventoryController {

    @FXML
    private AnchorPane pageAnc;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colEXP;

    @FXML
    private TableColumn<?, ?> colInventoryId;

    @FXML
    private TableColumn<?, ?> colMedId;

    @FXML
    private TableColumn<?, ?> colMedName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableView<?> tblMedicine;

    @FXML
    private TextField txtMedIDorName;

    @FXML
    void btnLowStockOnAction(ActionEvent event) {

    }

    @FXML
    void btnManageMedOnAction(ActionEvent event) {
        try {
            pageAnc.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/NurseManageMed.fxml"));
            anchorPane.prefWidthProperty().bind(pageAnc.widthProperty());
            anchorPane.prefHeightProperty().bind(pageAnc.heightProperty());

            pageAnc.getChildren().add(anchorPane);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnNearExpiryOnAction(ActionEvent event) {

    }

    @FXML
    void getMedicineDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void reloadTableOnAction(ActionEvent event) {

    }

    @FXML
    void searchMedOnAction(ActionEvent event) {

    }

}