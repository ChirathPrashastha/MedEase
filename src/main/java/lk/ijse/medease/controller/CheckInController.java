package lk.ijse.medease.controller;

import lk.ijse.medease.dto.CheckInDTO;
import lk.ijse.medease.model.CheckInModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class CheckInController {
    CheckInModel checkInModel;

    public CheckInController(){
        checkInModel = new CheckInModel();
    }

    public ArrayList<CheckInDTO> getDoctorCheckInList(String doctorId) throws ClassNotFoundException, SQLException {
        ArrayList<CheckInDTO> doctorCheckInList = checkInModel.getDoctorCheckInList(doctorId);
        return doctorCheckInList;
    }
}
