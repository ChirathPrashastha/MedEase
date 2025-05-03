package lk.ijse.medease.controller;

import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.EmployeeDTO;
import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.UserDTO;
import lk.ijse.medease.model.EmployeeModel;

import java.sql.SQLException;

public class EmployeeController {
    private EmployeeModel employeeModel;

    public EmployeeController(){
        employeeModel = new EmployeeModel();
    }

    public String addEmployee(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException {
        return employeeModel.addEmployee(employeeDTO, userDTO);
    }

    public String addEmployee(EmployeeDTO employeeDTO, DoctorDTO doctorDTO, UserDTO userDTO) throws SQLException {
        return employeeModel.addEmployee(employeeDTO, doctorDTO, userDTO);
    }

    public String updateEmployee(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException {
        return employeeModel.updateEmployee(employeeDTO, userDTO);
    }

    public String updateEmployee(EmployeeDTO employeeDTO, DoctorDTO doctorDTO, UserDTO userDTO) throws SQLException {
        return employeeModel.updateEmployee(employeeDTO, doctorDTO, userDTO);
    }

    public String deleteEmployee(String employeeId, JobRole jobRole) throws SQLException {
        return employeeModel.deleteEmployee(employeeId, jobRole);
    }

    public EmployeeDTO searchEmployee(String employeeId) throws SQLException {
        return employeeModel.serachEmployee(employeeId);
    }

    public String getNextId() throws SQLException {
        return employeeModel.getNextId();
    }
}
