package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.MedicineDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MedicineListController implements Initializable {

    private MedicineController medicineController;

    @FXML
    private TableColumn<MedicineDTO, String> colBrand;

    @FXML
    private TableColumn<MedicineDTO, String> colCategory;

    @FXML
    private TableColumn<MedicineDTO, String> colName;

    @FXML
    private TableView<MedicineDTO> tblMedicineList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        medicineController = new MedicineController();

        colName.setCellValueFactory(new PropertyValueFactory<>("genericName"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        try {
            ArrayList<MedicineDTO> medicineDTOs = medicineController.getMedicineList();
            ObservableList<MedicineDTO> medicineList = FXCollections.observableArrayList(medicineDTOs);
            tblMedicineList.setItems(medicineList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
