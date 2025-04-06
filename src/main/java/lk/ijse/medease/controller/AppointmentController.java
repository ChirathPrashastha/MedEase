package lk.ijse.medease.controller;

import lk.ijse.medease.dto.AppointmentDTO;
import lk.ijse.medease.model.AppointmentModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentController {

    private AppointmentModel appointmentModel;

    public AppointmentController() {
        appointmentModel = new AppointmentModel();
    }

    public int checkNo(String doctorId, Date date) throws ClassNotFoundException, SQLException {
        int num = appointmentModel.checkNo(doctorId,date);
        return num;
    }

    public String addAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.addAppointment(appointmentDTO);
        return response;
    }

    public String updateAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.updateAppointment(appointmentDTO);
        return response;
    }

    public String deleteAppointment(int appointmentId) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.deleteAppointment(appointmentId);
        return response;
    }

    public AppointmentDTO searchAppointment(int appointmentId) throws ClassNotFoundException, SQLException {
        AppointmentDTO appointmentDTO = appointmentModel.searchAppointment(appointmentId);
        return appointmentDTO;
    }

    public ArrayList<AppointmentDTO> getAllAppointments() throws ClassNotFoundException, SQLException {
        ArrayList<AppointmentDTO> appointmentDTOs = appointmentModel.getAllAppointments();
        return appointmentDTOs;
    }
}
