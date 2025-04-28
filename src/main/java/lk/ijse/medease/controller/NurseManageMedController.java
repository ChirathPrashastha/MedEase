package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NurseManageMedController implements Initializable {

    @FXML
    private AnchorPane ancPage;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRestock;

    @FXML
    private Button btnSupplier;

    @FXML
    private Button btnUpdate;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        navigateTo("/view/NurseAddMed.fxml");
        btnAdd.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnUpdate.setStyle("-fx-border-color: transparent");
        btnDelete.setStyle("-fx-border-color: transparent");
        btnRestock.setStyle("-fx-border-color: transparent");
        btnSupplier.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        navigateTo("/view/NurseDeleteMed.fxml");
        btnDelete.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAdd.setStyle("-fx-border-color: transparent");
        btnUpdate.setStyle("-fx-border-color: transparent");
        btnRestock.setStyle("-fx-border-color: transparent");
        btnSupplier.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnRestockOnAction(ActionEvent event) {
        navigateTo("/view/NurseRestock.fxml");
        btnRestock.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAdd.setStyle("-fx-border-color: transparent");
        btnUpdate.setStyle("-fx-border-color: transparent");
        btnDelete.setStyle("-fx-border-color: transparent");
        btnSupplier.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        navigateTo("/view/NurseViewSupplier.fxml");
        btnSupplier.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAdd.setStyle("-fx-border-color: transparent");
        btnUpdate.setStyle("-fx-border-color: transparent");
        btnDelete.setStyle("-fx-border-color: transparent");
        btnRestock.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        navigateTo("/view/NurseUpdateMed.fxml");
        btnUpdate.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAdd.setStyle("-fx-border-color: transparent");
        btnDelete.setStyle("-fx-border-color: transparent");
        btnRestock.setStyle("-fx-border-color: transparent");
        btnSupplier.setStyle("-fx-border-color: transparent");
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
        navigateTo("/view/NurseAddMed.fxml");
        btnAdd.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
    }
}
