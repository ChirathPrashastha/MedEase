package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerDashboardController implements Initializable {

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnSalary;

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
        btnSalary.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        navigateTo("/view/ManagerHome.fxml");
        btnHome.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnEmployee.setStyle("-fx-border-color: transparent");
        btnInventory.setStyle("-fx-border-color: transparent");
        btnSalary.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) {
        navigateTo("/view/ManagerSalary.fxml");
        btnSalary.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnHome.setStyle("-fx-border-color: transparent");
        btnInventory.setStyle("-fx-border-color: transparent");
        btnEmployee.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {
        navigateTo("/view/ManagerInventory.fxml");
        btnInventory.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnHome.setStyle("-fx-border-color: transparent");
        btnEmployee.setStyle("-fx-border-color: transparent");
        btnSalary.setStyle("-fx-border-color: transparent");
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
            managerDashAnc.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            managerDashAnc.getChildren().add(parent);
        }
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
