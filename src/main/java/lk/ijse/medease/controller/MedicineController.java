package lk.ijse.medease.controller;

import lk.ijse.medease.dto.MedicineDTO;
import lk.ijse.medease.model.MedicineModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineController {
    private MedicineModel medicineModel;

    public MedicineController() {
        medicineModel = new MedicineModel();
    }

    public String getMedicineIdByMedicineName(String medicineName) throws SQLException, ClassNotFoundException {
        String response = medicineModel.getMedicineIdByMedicineName(medicineName);
        return response;
    }

    public ArrayList<MedicineDTO> getMedicineList() throws SQLException, ClassNotFoundException {
        ArrayList<MedicineDTO> medicineList = medicineModel.getMedicineList();
        return medicineList;
    }

    public int getInventoryIdByMedicineId(String medicineId) throws SQLException, ClassNotFoundException {
        int response = medicineModel.getInventoryIdByMedicineId(medicineId);
        return response;
    }

    public MedicineDTO checkExpiration(String medicineId, int duration, String period) throws SQLException, ClassNotFoundException {
        MedicineDTO response = medicineModel.checkExpiration( medicineId, duration, period);
        return response;
    }
}
