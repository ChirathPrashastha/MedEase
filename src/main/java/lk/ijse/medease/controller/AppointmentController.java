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

    public String getTime(String doctorId) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.getTime(doctorId);
        return response;
    }

    public String addAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.addAppointment(appointmentDTO);
        return response;
    }

    public String updateAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.updateAppointment(appointmentDTO);
        return response;
    }

    public String deleteAppointment(String appointmentId) throws ClassNotFoundException, SQLException {
        String response = appointmentModel.deleteAppointment(appointmentId);
        return response;
    }

    public AppointmentDTO searchAppointment(String appointmentId) throws ClassNotFoundException, SQLException {
        AppointmentDTO appointmentDTO = appointmentModel.searchAppointment(appointmentId);
        return appointmentDTO;
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        return appointmentModel.getNextId();
    }

    public ArrayList<AppointmentDTO> getAllAppointments() throws ClassNotFoundException, SQLException {
        ArrayList<AppointmentDTO> appointmentDTOs = appointmentModel.getAllAppointments();
        return appointmentDTOs;
    }

    public ArrayList<AppointmentDTO> getAppointmentsByDoctor(String doctorId) throws ClassNotFoundException, SQLException {
        ArrayList<AppointmentDTO> appointmentDTOs = appointmentModel.getAppointmentsByDoctor(doctorId);
        return appointmentDTOs;
    }

    public int getTodayAppointmentCount() throws SQLException {
        return appointmentModel.getTodayAppointmentCount();
    }
}
