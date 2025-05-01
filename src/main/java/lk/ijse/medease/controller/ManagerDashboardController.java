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
    private Button btnOthers;

    @FXML
    private Button btnReports;

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
        btnReports.setStyle("-fx-border-color: transparent");
        btnOthers.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {

    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnOthersOnActon(ActionEvent event) {

    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {

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
