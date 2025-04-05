package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionDashboardController implements Initializable {

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    private AnchorPane recepDashAnc;

    @FXML
    void btnAppointmentOnAction(ActionEvent event) {
        btnAppointment.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnHome.setStyle("-fx-border-color: transparent;");
        btnPatient.setStyle("-fx-border-color: transparent;");
        btnPayment.setStyle("-fx-border-color: transparent;");

        navigateTo("/view/ReceptionAppointment.fxml");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        btnHome.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAppointment.setStyle("-fx-border-color: transparent;");
        btnPatient.setStyle("-fx-border-color: transparent;");
        btnPayment.setStyle("-fx-border-color: transparent;");

        navigateTo("/view/ReceptionHome.fxml");
    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        btnPatient.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAppointment.setStyle("-fx-border-color: transparent;");
        btnHome.setStyle("-fx-border-color: transparent;");
        btnPayment.setStyle("-fx-border-color: transparent;");

        navigateTo("/view/ReceptionPatient.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        btnPayment.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
        btnAppointment.setStyle("-fx-border-color: transparent;");
        btnHome.setStyle("-fx-border-color: transparent;");
        btnPatient.setStyle("-fx-border-color: transparent;");

        navigateTo("/view/ReceptionPayment.fxml");
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
        navigateTo("/view/ReceptionHome.fxml");
        btnHome.setStyle("-fx-background-color: rgba(208,198,198, 0.2)");
    }
}
