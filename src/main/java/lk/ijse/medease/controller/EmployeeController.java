package lk.ijse.medease.controller;

import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.EmployeeDTO;
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

    public String getNextId() throws SQLException {
        return employeeModel.getNextId();
    }
}
