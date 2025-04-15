package lk.ijse.medease.controller;

import lk.ijse.medease.model.MedicineModel;

import java.sql.SQLException;

public class MedicineController {
    private MedicineModel medicineModel;

    public MedicineController() {
        medicineModel = new MedicineModel();
    }

    public int getMedicineIdByMedicineName(String medicineName) throws SQLException, ClassNotFoundException {
        int response = medicineModel.getMedicineIdByMedicineName(medicineName);
        return response;
    }
}
