package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.dto.tm.MedicineInventoryTM;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerManageInventoryController implements Initializable {

    private MedicineController medicineController;
    private InventoryController inventoryController;
    private ObservableList<MedicineInventoryTM> medicineObList;

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
