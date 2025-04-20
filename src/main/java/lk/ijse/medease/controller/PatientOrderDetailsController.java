package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.PatientOrderDetailsDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientOrderDetailsController implements Initializable {

    private ObservableList<PatientOrderDetailsDTO> orderDetailsList;

    @FXML
    private TableColumn<PatientOrderDetailsDTO, Number> colMedicineId;

    @FXML
    private TableColumn<PatientOrderDetailsDTO, Number> colOrderId;

    @FXML
    private TableColumn<PatientOrderDetailsDTO, Number> colUnitPrice;

    @FXML
    private TableColumn<PatientOrderDetailsDTO, Number> colQuantity;

    @FXML
    private TableColumn<PatientOrderDetailsDTO, Number> colTotalPrice;

    @FXML
    private TableView<PatientOrderDetailsDTO> tblOrderDetails;

    @FXML
    void btnDeleteRowOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        orderDetailsList = FXCollections.observableArrayList(NurseIssueMedicationController.nurseIssueMedicationController.orderDetailsList);

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        tblOrderDetails.setItems(orderDetailsList);
    }
}