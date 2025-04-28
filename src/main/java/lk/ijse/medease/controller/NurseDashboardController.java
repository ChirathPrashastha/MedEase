package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NurseDashboardController implements Initializable {

    @FXML
    private Button btnIssueMedication;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRequestStocks;

    @FXML
    private Button btnManageInventory;

    @FXML
    private Label lblID;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    void btnIssueMedicationOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnManageInventoryOnAction(ActionEvent event) {
        navigateTo("/view/NurseManageInventory.fxml");
        btnManageInventory.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnIssueMedication.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnRequestStocksOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/NurseIssueMedication.fxml");
        btnIssueMedication.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
    }

    private void navigateTo(String path) {
        try {
            mainContainerAnc.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(mainContainerAnc.widthProperty());
            anchorPane.prefHeightProperty().bind(mainContainerAnc.heightProperty());

            mainContainerAnc.getChildren().add(anchorPane);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
