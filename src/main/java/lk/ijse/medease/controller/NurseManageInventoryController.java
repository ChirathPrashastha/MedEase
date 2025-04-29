package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.dto.tm.MedicineInventoryTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NurseManageInventoryController implements Initializable {

    private MedicineController medicineController;
    private InventoryController inventoryController;

    private ObservableList<MedicineInventoryTM> medicineObList;

    @FXML
    private AnchorPane pageAnc;

    @FXML
    private TableColumn<MedicineInventoryTM, String> colBrand;

    @FXML
    private TableColumn<MedicineInventoryTM, Date> colEXP;

    @FXML
    private TableColumn<MedicineInventoryTM, String> colInventoryId;

    @FXML
    private TableColumn<MedicineInventoryTM, String> colMedId;

    @FXML
    private TableColumn<MedicineInventoryTM, Double> colPrice;

    @FXML
    private TableColumn<MedicineInventoryTM, Number> colQuantity;

    @FXML
    private TableColumn<MedicineInventoryTM, String> colSection;

    @FXML
    private TableColumn<MedicineInventoryTM, String> colSupplier;

    @FXML
    private TableView<MedicineInventoryTM> tblMedicine;

    @FXML
    private TextField txtMedIDorName;

    @FXML
    void btnLowStockOnAction(ActionEvent event) {
        try {
            ArrayList<MedicineDTO> lowStockmedicineDTOs = medicineController.getLowStockMedicine();
            ObservableList<MedicineInventoryTM> lowStockMedObList = FXCollections.observableArrayList();
            if (lowStockmedicineDTOs.size() > 0) {
                tblMedicine.getItems().clear();

                for (MedicineDTO dto : lowStockmedicineDTOs) {
                    int qty;
                    String supplierId;
                    String section;

                    InventoryDTO inventoryDTO = inventoryController.searchInventory(dto.getInventoryId());
                    qty = inventoryDTO.getQuantity();
                    supplierId = inventoryDTO.getSupplierId();
                    section = inventoryDTO.getSection();

                    MedicineInventoryTM medicineInventoryTM = new MedicineInventoryTM(dto.getMedicineId(), dto.getBrand(), dto.getPrice(), dto.getExpirationDate(), dto.getInventoryId(), qty, supplierId, section );
                    lowStockMedObList.add(medicineInventoryTM);
                }
                tblMedicine.setItems(lowStockMedObList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnManageMedOnAction(ActionEvent event) {
        try {
            pageAnc.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/NurseManageMed.fxml"));
            anchorPane.prefWidthProperty().bind(pageAnc.widthProperty());
            anchorPane.prefHeightProperty().bind(pageAnc.heightProperty());

            pageAnc.getChildren().add(anchorPane);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnNearExpiryOnAction(ActionEvent event) {
        try {
            ArrayList<MedicineDTO> nearExpiryDTOs = medicineController.getNearExpiryMedicine();
            ObservableList<MedicineInventoryTM> nearExpiryMedicineObList = FXCollections.observableArrayList();

            if (nearExpiryDTOs.size() > 0) {
                tblMedicine.getItems().clear();

                for (MedicineDTO dto : nearExpiryDTOs) {
                    int qty;
                    String supplierId;
                    String section;

                    InventoryDTO inventoryDTO = inventoryController.searchInventory(dto.getInventoryId());
                    qty = inventoryDTO.getQuantity();
                    supplierId = inventoryDTO.getSupplierId();
                    section = inventoryDTO.getSection();

                    MedicineInventoryTM medicineInventoryTM = new MedicineInventoryTM(dto.getMedicineId(), dto.getBrand(), dto.getPrice(), dto.getExpirationDate(), dto.getInventoryId(), qty, supplierId, section );
                    nearExpiryMedicineObList.add(medicineInventoryTM);
                }

                tblMedicine.setItems(nearExpiryMedicineObList);
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText("EMPTY");
                alert.setContentText("No medicine found");
                alert.showAndWait();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getMedicineDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void reloadTableOnAction(ActionEvent event) {
        txtMedIDorName.clear();
        loadTable();
    }

    @FXML
    void searchMedOnAction(ActionEvent event) {
        try {
            ArrayList<MedicineDTO> medicineDTOs = medicineController.searchMedicine(txtMedIDorName.getText());
            if (medicineDTOs != null) {
                tblMedicine.getItems().clear();

                ObservableList<MedicineInventoryTM> searchedMedObList = FXCollections.observableArrayList();

                for (MedicineDTO dto : medicineDTOs) {
                    int qty;
                    String supplierId;
                    String section;

                    InventoryDTO inventoryDTO = inventoryController.searchInventory(dto.getInventoryId());
                    qty = inventoryDTO.getQuantity();
                    supplierId = inventoryDTO.getSupplierId();
                    section = inventoryDTO.getSection();

                    MedicineInventoryTM medicineInventoryTM = new MedicineInventoryTM(dto.getMedicineId(), dto.getBrand(), dto.getPrice(), dto.getExpirationDate(), dto.getInventoryId(), qty, supplierId, section );
                    searchedMedObList.add(medicineInventoryTM);
                }

                tblMedicine.setItems(searchedMedObList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("FAILED");
                alert.setContentText("Medicine Not Found");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        medicineController = new MedicineController();
        inventoryController = new InventoryController();

        colMedId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEXP.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        medicineObList = FXCollections.observableArrayList();

        loadTable();
    }

    private void loadTable(){
        try {
            ArrayList<MedicineDTO> medicineDTOs = medicineController.getAllMedicine();

            for (MedicineDTO dto : medicineDTOs) {
                int qty;
                String supplierId;
                String section;

                InventoryDTO inventoryDTO = inventoryController.searchInventory(dto.getInventoryId());
                qty = inventoryDTO.getQuantity();
                supplierId = inventoryDTO.getSupplierId();
                section = inventoryDTO.getSection();

                MedicineInventoryTM medicineInventoryTM = new MedicineInventoryTM(dto.getMedicineId(), dto.getBrand(), dto.getPrice(), dto.getExpirationDate(), dto.getInventoryId(), qty, supplierId, section );
                medicineObList.add(medicineInventoryTM);
            }

            tblMedicine.setItems(medicineObList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}