package lk.ijse.medease.controller;

import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.model.MedicineModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineController {
    private MedicineModel medicineModel;

    public MedicineController() {
        medicineModel = new MedicineModel();
    }

    public String addMedicine(MedicineDTO medicineDTO, InventoryDTO inventoryDTO) throws ClassNotFoundException, SQLException {
        return medicineModel.addMedicine(medicineDTO, inventoryDTO);
    }

    public String updateMedicine(MedicineDTO medicineDTO, InventoryDTO inventoryDTO) throws ClassNotFoundException, SQLException {
        return medicineModel.updateMedicine(medicineDTO, inventoryDTO);
    }

    public String getMedicineIdByMedicineName(String medicineName) throws SQLException, ClassNotFoundException {
        String response = medicineModel.getMedicineIdByMedicineName(medicineName);
        return response;
    }

    public ArrayList<MedicineDTO> getMedicineList() throws SQLException, ClassNotFoundException {
        ArrayList<MedicineDTO> medicineList = medicineModel.getMedicineList();
        return medicineList;
    }

    public String getInventoryIdByMedicineId(String medicineId) throws SQLException, ClassNotFoundException {
        String response = medicineModel.getInventoryIdByMedicineId(medicineId);
        return response;
    }

    public MedicineDTO checkExpiration(String medicineId, int duration, String period) throws SQLException, ClassNotFoundException {
        MedicineDTO response = medicineModel.checkExpiration( medicineId, duration, period);
        return response;
    }

    public ArrayList<MedicineDTO> getAllMedicine() throws ClassNotFoundException, SQLException {
        ArrayList<MedicineDTO> medicineList = medicineModel.getAllMedicine();
        return medicineList;
    }

    public ArrayList<MedicineDTO> searchMedicine(String idOrName) throws SQLException, ClassNotFoundException {
        return medicineModel.searchMedicine(idOrName);
    }

    public ArrayList<MedicineDTO> getNearExpiryMedicine() throws ClassNotFoundException, SQLException{
        return medicineModel.getNearExpiryMedicine();
    }

    public ArrayList<MedicineDTO> getLowStockMedicine() throws ClassNotFoundException, SQLException{
        return medicineModel.getLowStockMedicine();
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        return medicineModel.getNextId();
    }
}
