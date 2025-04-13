package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class DoctorPrescriptionController {

    @FXML
    private TableColumn<?, ?> colDosage;

    @FXML
    private TableColumn<?, ?> colFrequency;

    @FXML
    private TableColumn<?, ?> colMedId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPresId;

    @FXML
    private ImageView imgAlert;

    @FXML
    private ImageView imgAvailability;

    @FXML
    private TableView<?> tblPresMed;

    @FXML
    private Label txtAge;

    @FXML
    private Label txtAllergies;

    @FXML
    private Label txtAvailability;

    @FXML
    private Label txtPatientName;

    @FXML
    void btnAddMedOnAction(ActionEvent event) {

    }

    @FXML
    void btnRXOnAction(ActionEvent event) {

    }

    @FXML
    void checkHistoryOnAction(ActionEvent event) {

    }

}
