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

    public int getMedicineIdByMedicineName(String medicineName) throws SQLException, ClassNotFoundException {
        int response = medicineModel.getMedicineIdByMedicineName(medicineName);
        return response;
    }

    public ArrayList<MedicineDTO> getMedicineList() throws SQLException, ClassNotFoundException {
        ArrayList<MedicineDTO> medicineList = medicineModel.getMedicineList();
        return medicineList;
    }
}
