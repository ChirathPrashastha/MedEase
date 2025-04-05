package lk.ijse.medease.controller;

import lk.ijse.medease.model.AppointmentModel;

import java.sql.Date;
import java.sql.SQLException;

public class AppointmentController {

    private AppointmentModel appointmentModel;

    public AppointmentController() {
        appointmentModel = new AppointmentModel();
    }

    public int checkNo(String doctorId, Date date) throws ClassNotFoundException, SQLException {
        int num = appointmentModel.checkNo(doctorId,date);
        return num;
    }
}
