package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    private AnchorPane nurseDashAnc;

    @FXML
    void btnIssueMedicationOnAction(ActionEvent event) {
        navigateTo("/view/NurseIssueMedication.fxml");
        btnIssueMedication.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnManageInventory.setStyle("-fx-border-color: transparent");
        btnRequestStocks.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure you want to logout?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            nurseDashAnc.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            nurseDashAnc.getChildren().add(parent);
        }
    }

    @FXML
    void btnManageInventoryOnAction(ActionEvent event) {
        navigateTo("/view/NurseManageInventory.fxml");
        btnManageInventory.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnIssueMedication.setStyle("-fx-border-color: transparent");
        btnRequestStocks.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnRequestStocksOnAction(ActionEvent event) {
        navigateTo("/view/NurseRequestRestock.fxml");
        btnRequestStocks.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnIssueMedication.setStyle("-fx-border-color: transparent");
        btnManageInventory.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
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
