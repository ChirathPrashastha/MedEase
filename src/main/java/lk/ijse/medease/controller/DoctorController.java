package lk.ijse.medease.controller;

import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.model.DoctorModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorController {
    private DoctorModel doctorModel;

    DoctorController()  {
        doctorModel = new DoctorModel();
    }

    public String addDoctor(DoctorDTO doctorDTO) throws ClassNotFoundException, SQLException {
        String response = doctorModel.addDoctor(doctorDTO);
        return response;
    }

    public String updateDoctor(DoctorDTO doctorDTO) throws ClassNotFoundException, SQLException {
        String response = doctorModel.updateDoctor(doctorDTO);
        return response;
    }

    public String deleteDoctor(String doctorId) throws ClassNotFoundException, SQLException {
        String response = doctorModel.deleteDoctor(doctorId);
        return response;
    }

    public DoctorDTO searchDoctor(String doctorId) throws ClassNotFoundException, SQLException {
        DoctorDTO response = doctorModel.searchDoctor(doctorId);
        return response;
    }

    public String getDoctorId(String employeeId) throws ClassNotFoundException, SQLException {
        String response = doctorModel.getDoctorId(employeeId);
        return response;
    }

    public ArrayList<DoctorDTO> getAllDoctors() throws ClassNotFoundException, SQLException {
        ArrayList<DoctorDTO> doctorDTOs = doctorModel.getAllDoctors();
        return doctorDTOs;
    }

    public String getNextId() throws SQLException {
        return doctorModel.getNextId();
    }
}
