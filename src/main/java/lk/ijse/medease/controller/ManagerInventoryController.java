package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerInventoryController implements Initializable {

    @FXML
    private AnchorPane ancPage;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnRequest;

    @FXML
    private Button btnSupplier;

    @FXML
    void btnInventoryOnAction(ActionEvent event) {
        navigateTo("/view/ManagerManageInventory.fxml");
        btnInventory.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnSupplier.setStyle("-fx-border-color: transparent");
        btnRequest.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnRequestOnAction(ActionEvent event) {
        navigateTo("/view/ManagerStockRequest.fxml");
        btnRequest.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnSupplier.setStyle("-fx-border-color: transparent");
        btnInventory.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        navigateTo("/view/ManagerSuppliers.fxml");
        btnSupplier.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnInventory.setStyle("-fx-border-color: transparent");
        btnRequest.setStyle("-fx-border-color: transparent");
    }

    private void navigateTo(String path) {
        try {
            ancPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPage.heightProperty());

            ancPage.getChildren().add(anchorPane);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/ManagerManageInventory.fxml");
        btnInventory.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
    }
}