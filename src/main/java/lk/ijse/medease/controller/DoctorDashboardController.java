package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorDashboardController implements Initializable {

    public static DoctorDashboardController controller;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPrescription;

    @FXML
    private AnchorPane doctorDashAnc;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    void btnAppointmentOnAction(ActionEvent event) {
        navigateTo("/view/DoctorAppointment.fxml");
        btnAppointment.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnPrescription.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        doctorDashAnc.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        doctorDashAnc.getChildren().add(parent);
    }


    @FXML
    void btnPrescriptionOnAction(ActionEvent event) {
        navigateTo("/view/DoctorPrescription.fxml");
        btnPrescription.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnLogout.setStyle("-fx-border-color: transparent");
        btnAppointment.setStyle("-fx-border-color: transparent");
        btnLogout.setStyle("-fx-border-color: transparent");
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
        navigateTo("/view/DoctorAppointment.fxml");
        btnAppointment.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");

        controller = this;
    }

    public void setBtnPrescription() {
        btnPrescription.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAppointment.setStyle("-fx-border-color: transparent");
    }
}
