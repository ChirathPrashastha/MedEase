package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.SupplierDTO;
import lk.ijse.medease.dto.tm.SupplierTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseViewSupplierController implements Initializable {

    private SupplierController supplierController;

    @FXML
    private TableColumn<SupplierTM, String> colAddress;

    @FXML
    private TableColumn<SupplierTM, String> colContact;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private TableColumn<SupplierTM, String> colEmail;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        supplierController = new SupplierController();

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            ArrayList<SupplierDTO> supplierDTOs = supplierController.getAllSuppliers();
            ObservableList<SupplierTM> suppliersObList = FXCollections.observableArrayList();

            for (SupplierDTO dto : supplierDTOs) {
                SupplierTM supplierTM = new SupplierTM(dto.getSupplierId(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getEmail());
                suppliersObList.add(supplierTM);
            }
            tblSupplier.setItems(suppliersObList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
