package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.medease.dto.UserDTO;
import lk.ijse.medease.model.AuthenticationModel;

import java.io.IOException;
import java.sql.SQLException;

public class LogInPageController {
    private String jobRole;
    private String username, password;
    public static UserDTO userDTO;

    @FXML
    private AnchorPane ancLoginPage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException {
        username = txtUsername.getText();
        password = txtPassword.getText();


         userDTO = AuthenticationModel.checkCredentials(username, password);

        if (userDTO != null) {
           jobRole = AuthenticationModel.getJobRole(userDTO.getEmployeeId());

            switch (jobRole){
                case "Manager" -> navigateTo("/view/ManagerDashboard.fxml");
                case "Receptionist" -> navigateTo("/view/ReceptionDashboard.fxml");
                case "Doctor" -> navigateTo("/view/DoctorDashboard.fxml");
                case "Nurse" -> navigateTo("/view/NurseDashboard.fxml");
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }
    }

    public void navigateTo(String path) {
        try {
            ancLoginPage.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource(path));
            ancLoginPage.getChildren().add(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}