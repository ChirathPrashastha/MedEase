package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.InventoryDTO;
import lk.ijse.medease.dto.MedicineDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineModel {

    public String addMedicine(MedicineDTO medicineDTO, InventoryDTO inventoryDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String inventoryAddingSql = "INSERT INTO inventory VALUES (?,?,?,?)";

            PreparedStatement inventoryAddingStatement = connection.prepareStatement(inventoryAddingSql);
            inventoryAddingStatement.setString(1, inventoryDTO.getInventoryId());
            inventoryAddingStatement.setInt(2, inventoryDTO.getQuantity());
            inventoryAddingStatement.setString(3, inventoryDTO.getSupplierId());
            inventoryAddingStatement.setString(4, inventoryDTO.getSection());

            boolean isInventoryAdded = inventoryAddingStatement.executeUpdate() > 0;

            if (isInventoryAdded) {

                String medicineAddingSql = "INSERT INTO medicine VALUES (?,?,?,?,?,?,?)";

                PreparedStatement medicineAddingStatement = connection.prepareStatement(medicineAddingSql);
                medicineAddingStatement.setString(1, medicineDTO.getMedicineId());
                medicineAddingStatement.setString(2, medicineDTO.getGenericName());
                medicineAddingStatement.setString(3, medicineDTO.getBrand());
                medicineAddingStatement.setString(4, medicineDTO.getCategory());
                medicineAddingStatement.setDouble(5, medicineDTO.getPrice());
                medicineAddingStatement.setDate(6, medicineDTO.getExpirationDate());
                medicineAddingStatement.setString(7, medicineDTO.getInventoryId());

                boolean isMedicineAdded = medicineAddingStatement.executeUpdate() > 0;

                if (isMedicineAdded) {
                    return "Medicine Added Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Adding Medicine";
                }
            }else {
                connection.rollback();
                return "Failed to Adding Inventory";
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public String updateMedicine(MedicineDTO medicineDTO, InventoryDTO inventoryDTO) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String updateMedicineSql = "UPDATE medicine SET generic_name = ?, brand = ?, category = ?, price = ?, expiration_date = ? WHERE medicine_id = ? AND inventory_id = ?";

            PreparedStatement updateMedicineStatement = connection.prepareStatement(updateMedicineSql);
            updateMedicineStatement.setString(1, medicineDTO.getGenericName());
            updateMedicineStatement.setString(2, medicineDTO.getBrand());
            updateMedicineStatement.setString(3, medicineDTO.getCategory());
            updateMedicineStatement.setDouble(4, medicineDTO.getPrice());
            updateMedicineStatement.setDate(5, medicineDTO.getExpirationDate());
            updateMedicineStatement.setString(6, medicineDTO.getMedicineId());
            updateMedicineStatement.setString(7, medicineDTO.getInventoryId());

            boolean isMedicineUpdated = updateMedicineStatement.executeUpdate() > 0;

            if (isMedicineUpdated) {

                String inventoryUpdateSql = "UPDATE inventory SET quantity = ?, supplier_id = ?, section = ? WHERE inventory_id = ?";

                PreparedStatement inventoryUpdateStatement = connection.prepareStatement(inventoryUpdateSql);
                inventoryUpdateStatement.setInt(1, inventoryDTO.getQuantity());
                inventoryUpdateStatement.setString(2, inventoryDTO.getSupplierId());
                inventoryUpdateStatement.setString(3, inventoryDTO.getSection());
                inventoryUpdateStatement.setString(4, inventoryDTO.getInventoryId());

                boolean isInventoryUpdated = inventoryUpdateStatement.executeUpdate() > 0;

                if (isInventoryUpdated) {
                    return "Medicine Updated Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Update Inventory";
                }
            }else {
                connection.rollback();
                return "Failed to Update Medicine";
            }
        }catch (Exception e){
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public String deleteMedicine(String medicineId, String inventoryId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String deleteMedicineSql = "DELETE FROM medicine WHERE medicine_id = ?";

            PreparedStatement deleteMedicineStatement = connection.prepareStatement(deleteMedicineSql);
            deleteMedicineStatement.setString(1, medicineId);

            boolean isMedicineDeleted = deleteMedicineStatement.executeUpdate() > 0;

            if (isMedicineDeleted) {

                String deleteInventorySql = "DELETE FROM inventory WHERE inventory_id = ?";

                PreparedStatement deleteInventoryStatement = connection.prepareStatement(deleteInventorySql);
                deleteInventoryStatement.setString(1, inventoryId);

                boolean isInventoryDeleted = deleteInventoryStatement.executeUpdate() > 0;

                if (isInventoryDeleted) {
                    return "Medicine Deleted Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Delete Inventory";
                }
            }else {
                connection.rollback();
                return "Failed to Delete from Medicine";
            }
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

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

    public ArrayList<MedicineDTO> searchMedicine(String idOrName) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine WHERE medicine_id = ? OR brand = ? OR generic_name = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, idOrName);
        statement.setString(2, idOrName);
        statement.setString(3, idOrName);

        ResultSet rst = statement.executeQuery();

        ArrayList<MedicineDTO> medicineList = new ArrayList<>();

        if (rst.equals(null)){
            return medicineList;
        }

        while (rst.next()) {
            MedicineDTO medicineDTO = new MedicineDTO(rst.getString("medicine_id"), rst.getString("generic_name"), rst.getString("brand"), rst.getString("category"), rst.getDouble("price"), rst.getDate("expiration_date"), rst.getString("inventory_id") );
            medicineList.add(medicineDTO);
        }
        return medicineList;
    }

    public ArrayList<MedicineDTO> getAllMedicine() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<MedicineDTO> medicineList = new ArrayList<>();
        while (rst.next()) {
            MedicineDTO medicineDTO = new MedicineDTO(rst.getString("medicine_id"), rst.getString("generic_name"), rst.getString("brand"), rst.getString("category"), rst.getDouble("price"), rst.getDate("expiration_date"), rst.getString("inventory_id") );
            medicineList.add(medicineDTO);
        }
        return medicineList;
    }

    public ArrayList<MedicineDTO> getNearExpiryMedicine() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine WHERE expiration_date BETWEEN CURDATE() AND CURDATE() + INTERVAL 3 MONTH";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<MedicineDTO> medicineList = new ArrayList<>();
        while (rst.next()) {
            MedicineDTO medicineDTO = new MedicineDTO(rst.getString("medicine_id"), rst.getString("generic_name"), rst.getString("brand"), rst.getString("category"), rst.getDouble("price"), rst.getDate("expiration_date"), rst.getString("inventory_id") );
            medicineList.add(medicineDTO);
        }
        return medicineList;
    }

    public ArrayList<MedicineDTO> getLowStockMedicine() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine INNER JOIN inventory ON medicine.inventory_id = inventory.inventory_id WHERE quantity < 100";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<MedicineDTO> medicineList = new ArrayList<>();
        while (rst.next()) {
            MedicineDTO medicineDTO = new MedicineDTO(rst.getString("medicine_id"), rst.getString("generic_name"), rst.getString("brand"), rst.getString("category"), rst.getDouble("price"), rst.getDate("expiration_date"), rst.getString("inventory_id") );
            medicineList.add(medicineDTO);
        }
        return medicineList;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT medicine_id FROM medicine ORDER BY medicine_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("medicine_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("M"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "M0001";
    }
}
