package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.MedicineDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineModel {

    public String getMedicineIdByMedicineName(String medicineName) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT medicine_id FROM medicine WHERE generic_name = ? OR brand = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, medicineName);
        statement.setString(2, medicineName);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("medicine_id");
        }
        return null;
    }

    public ArrayList<MedicineDTO> getMedicineList() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT generic_name, brand, category FROM medicine";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<MedicineDTO> medicineList = new ArrayList<>();
        while (rst.next()) {
            MedicineDTO medicineDTO = new MedicineDTO(rst.getString("generic_name"), rst.getString("brand"), rst.getString("category"));
            medicineList.add(medicineDTO);
        }
        return medicineList;
    }

    public String getInventoryIdByMedicineId(String medicineId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT inventory_id FROM medicine WHERE medicine_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, medicineId);

        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return rst.getString("inventory_id");
        }
        return null;
    }

    public MedicineDTO checkExpiration(String medicineId, int duration, String period) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = null;

        if (period.equals("days")) {
            sql = "SELECT * FROM medicine WHERE medicine_id = ? AND expiration_date BETWEEN CURDATE() AND CURDATE() + INTERVAL ? DAY";
        } else if (period.equals("weeks")) {
            sql = "SELECT * FROM medicine WHERE medicine_id = ? AND expiration_date BETWEEN CURDATE() AND CURDATE() + INTERVAL ? WEEK";
        } else if (period.equals("months")) {
            sql = "SELECT * FROM medicine WHERE medicine_id = ? AND expiration_date BETWEEN CURDATE() AND CURDATE() + INTERVAL ? MONTH";
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, medicineId);
        statement.setInt(2, duration);

        ResultSet rst = statement.executeQuery();

        MedicineDTO medicineDTO = null;

        while (rst.next()){
            medicineDTO = new MedicineDTO(rst.getString("medicine_id"), rst.getString("generic_name"), rst.getString("brand"), rst.getString("category"), rst.getDouble("price"), rst.getDate("expiration_date"), rst.getString("inventory_id"));
        }

        if (medicineDTO != null){
            return medicineDTO;
        } else {
            return null;
        }
    }
}
