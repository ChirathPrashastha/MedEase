package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.medease.dto.SupplierDTO;
import lk.ijse.medease.dto.tm.SupplierTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerSuppliersController implements Initializable {

    private final String supplierIdPattern = "^S\\d{4}$";
    private final String contactPattern = "^[0-9]{10}$";
    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private final String namePattern = "^[A-Za-z ]+$";

    private boolean isFieldsValid = true;

    private SupplierController supplierController;

    @FXML
    private TableColumn<SupplierTM, String> colAddress;

    @FXML
    private TableColumn<SupplierTM, String> colContact;

    @FXML
    private TableColumn<SupplierTM, String> colEmail;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSupplierId;

    @FXML
    void contactOnKeyReleased(KeyEvent event) {
        txtContact.setStyle(txtContact.getStyle() + "-fx-text-fill: white;");
        if (!(txtContact.getText().matches(contactPattern))) {
            txtContact.setStyle(txtContact.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void emailOnKeyReleased(KeyEvent event) {
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-text-fill: white;");
        if (!(txtEmail.getText().matches(emailPattern))) {
            txtEmail.setStyle(txtEmail.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void nameOnKeyReleased(KeyEvent event) {
        txtName.setStyle(txtName.getStyle() + "-fx-text-fill: white;");
        if (!(txtName.getText().matches(namePattern))) {
            txtName.setStyle(txtName.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void supplierIdOnKeyReleased(KeyEvent event) {
        txtSupplierId.setStyle(txtSupplierId.getStyle() + "-fx-text-fill: white;");
        if (!(txtSupplierId.getText().matches(supplierIdPattern))) {
            txtSupplierId.setStyle(txtSupplierId.getStyle() + "-fx-text-fill: red;");
            isFieldsValid = false;
        }else {
            isFieldsValid = true;
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (isFieldsValid) {
            addSupplier();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please check the fields").showAndWait();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (isFieldsValid) {
            deleteSupplier();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please check the fields").showAndWait();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (isFieldsValid) {
            updateSupplier();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please check the fields").showAndWait();
        }
    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        SupplierTM selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            txtSupplierId.setText(selectedSupplier.getSupplierId());
            txtName.setText(selectedSupplier.getName());
            txtAddress.setText(selectedSupplier.getAddress());
            txtContact.setText(selectedSupplier.getContact());
            txtEmail.setText(selectedSupplier.getEmail());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        supplierController = new SupplierController();

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadTable();

        setSupplierId();
    }

    private void loadTable() {
        try {
            ArrayList<SupplierDTO> supplierDTOS = supplierController.getAllSuppliers();
            ObservableList<SupplierTM> supplierObList = FXCollections.observableArrayList();

            for (SupplierDTO dto : supplierDTOS) {
                SupplierTM supplierTM = new SupplierTM(dto.getSupplierId(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getEmail());
                supplierObList.add(supplierTM);
            }

            tblSupplier.setItems(supplierObList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Unable to load supplier table");
            alert.showAndWait();
        }
    }

    private void addSupplier() {
        SupplierDTO supplierDTO = null;
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtContact.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSupplierId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else {
            supplierDTO = new SupplierDTO(txtSupplierId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(), txtEmail.getText());
        }

        try {
            String response = supplierController.addSupplier(supplierDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ADDING SUPPLIER");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();

            loadTable();
            clearFields();
            setSupplierId();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void deleteSupplier() {
        if (txtSupplierId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please enter Supplier ID");
            alert.showAndWait();
        }else {
            try {
                String response = supplierController.deleteSupplier(txtSupplierId.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DELETE SUPPLIER");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait();

                loadTable();
                clearFields();
                setSupplierId();

            }catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void updateSupplier() {
        SupplierDTO supplierDTO = null;
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtContact.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSupplierId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else {
            supplierDTO = new SupplierDTO(txtSupplierId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(), txtEmail.getText());
        }

        try {
            String response = supplierController.updateSupplier(supplierDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("UPDATE SUPPLIER");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();

            loadTable();
            clearFields();
            setSupplierId();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void setSupplierId() {
        try {
            String supplierId = supplierController.getNextId();
            txtSupplierId.setText(supplierId);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to load supplier ID");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        txtSupplierId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
    }
}