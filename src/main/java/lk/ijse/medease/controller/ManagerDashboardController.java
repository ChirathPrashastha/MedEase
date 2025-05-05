package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerDashboardController implements Initializable {

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    private AnchorPane managerDashAnc;

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        navigateTo("/view/ManagerEmployee.fxml");
        btnEmployee.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnHome.setStyle("-fx-border-color: transparent");
        btnInventory.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        navigateTo("/view/ManagerHome.fxml");
        btnHome.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnEmployee.setStyle("-fx-border-color: transparent");
        btnInventory.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {
        navigateTo("/view/ManagerInventory.fxml");
        btnInventory.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnHome.setStyle("-fx-border-color: transparent");
        btnEmployee.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/ManagerHome.fxml");
        btnHome.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
    }
}
