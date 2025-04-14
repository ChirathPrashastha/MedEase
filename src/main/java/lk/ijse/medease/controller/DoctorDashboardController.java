package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.medease.dto.DoctorDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorDashboardController implements Initializable {

    public static DoctorDashboardController controller;

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPrescription;

    @FXML
    private AnchorPane doctorDashAnc;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    void btnAppointmentOnAction(ActionEvent event) {

    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {

    }

    @FXML
    void btnPrescriptionOnAction(ActionEvent event) {

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
