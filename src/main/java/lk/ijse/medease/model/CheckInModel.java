package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.CheckInDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckInModel {

    public ArrayList<CheckInDTO> getDoctorCheckInList(String doctorId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM check_in  WHERE doctor_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, doctorId);

        ResultSet rst = statement.executeQuery();

        ArrayList<CheckInDTO> checkInList = new ArrayList<>();
        while (rst.next()) {
            CheckInDTO checkInDTO = new CheckInDTO(rst.getInt("check_in_id"), rst.getString("doctor_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("status"));
            checkInList.add(checkInDTO);
        }
        return checkInList;
    }

    public ArrayList<CheckInDTO> getAllCheckIn() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM check_in";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<CheckInDTO> checkInList = new ArrayList<>();
        while (rst.next()) {
            CheckInDTO checkInDTO = new CheckInDTO(rst.getInt("check_in_id"), rst.getString("doctor_id"), rst.getDate("date"), rst.getInt("check_in_no"), rst.getString("status"));
            checkInList.add(checkInDTO);
        }
        return checkInList;
    }
}
