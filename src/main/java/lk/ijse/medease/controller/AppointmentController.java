package lk.ijse.medease.controller;

import lk.ijse.medease.dto.AppointmentDTO;
import lk.ijse.medease.model.AppointmentModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class AppointmentController {

    private AppointmentModel appointmentModel;

    public AppointmentController() {
        appointmentModel = new AppointmentModel();
    }

    public int checkNo(String doctorId, Date date) throws ClassNotFoundException, SQLException {
        return appointmentModel.checkNo(doctorId,date);
    }

    public String getTime(String doctorId) throws ClassNotFoundException, SQLException {
        return appointmentModel.getTime(doctorId);
    }

    public String addAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        return appointmentModel.addAppointment(appointmentDTO);
    }

    public String updateAppointment(AppointmentDTO appointmentDTO) throws ClassNotFoundException, SQLException {
        return appointmentModel.updateAppointment(appointmentDTO);
    }

    public String deleteAppointment(AppointmentDTO appointmentDTO) throws SQLException {
        return appointmentModel.deleteAppointment(appointmentDTO);
    }

    public AppointmentDTO searchAppointment(String appointmentId) throws ClassNotFoundException, SQLException {
        return appointmentModel.searchAppointment(appointmentId);
    }

    public String getNextId() throws ClassNotFoundException, SQLException {
        return appointmentModel.getNextId();
    }

    public ArrayList<AppointmentDTO> getAllAppointments() throws ClassNotFoundException, SQLException {
        return appointmentModel.getAllAppointments();
    }

    public ArrayList<AppointmentDTO> getAppointmentsByDoctor(String doctorId) throws ClassNotFoundException, SQLException {
        return appointmentModel.getAppointmentsByDoctor(doctorId);
    }

    public int getTodayAppointmentCount() throws SQLException {
        return appointmentModel.getTodayAppointmentCount();
    }

    public Map<String, String> getEmailsOfRemainingPatients(int number) throws SQLException {
        return appointmentModel.getEmailsOfRemainingPatients(number);
    }
}
