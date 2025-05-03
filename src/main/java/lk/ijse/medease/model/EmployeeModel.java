package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.EmployeeDTO;
import lk.ijse.medease.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {

    public String addEmployee(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String addEmployeeSQL = "INSERT INTO employee VALUES (?,?,?,?,?,?,?)";

            PreparedStatement addingEmployeeStatement = connection.prepareStatement(addEmployeeSQL);
            addingEmployeeStatement.setString(1, employeeDTO.getEmployeeId());
            addingEmployeeStatement.setString(2, employeeDTO.getName());
            addingEmployeeStatement.setString(3, employeeDTO.getJobRole().name());
            addingEmployeeStatement.setDate(4, employeeDTO.getRecruitedDate());
            addingEmployeeStatement.setString(5, employeeDTO.getAddress());
            addingEmployeeStatement.setString(6, employeeDTO.getContact());
            addingEmployeeStatement.setString(7, employeeDTO.getEmail());

            boolean isEmployeeAdded = addingEmployeeStatement.executeUpdate() > 0;

            if (isEmployeeAdded) {

                String addingUserSQL = "INSERT INTO authentication (employee_id, username, password) VALUES (?,?,?)";

                PreparedStatement addingUserStatement = connection.prepareStatement(addingUserSQL);
                addingUserStatement.setString(1, userDTO.getEmployeeId());
                addingUserStatement.setString(2, userDTO.getUsername());
                addingUserStatement.setString(3, userDTO.getPassword());

                boolean isUserAdded = addingUserStatement.executeUpdate() > 0;

                if (isUserAdded) {
                    return "Employee Added Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Process Authentication";
                }

            }else {
                connection.rollback();
                return "Failed to Save Employee";
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public String addEmployee(EmployeeDTO employeeDTO, DoctorDTO doctorDTO, UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String addEmployeeSQL = "INSERT INTO employee VALUES (?,?,?,?,?,?,?)";

            PreparedStatement addingEmployeeStatement = connection.prepareStatement(addEmployeeSQL);
            addingEmployeeStatement.setString(1, employeeDTO.getEmployeeId());
            addingEmployeeStatement.setString(2, employeeDTO.getName());
            addingEmployeeStatement.setString(3, employeeDTO.getJobRole().name());
            addingEmployeeStatement.setDate(4, employeeDTO.getRecruitedDate());
            addingEmployeeStatement.setString(5, employeeDTO.getAddress());
            addingEmployeeStatement.setString(6, employeeDTO.getContact());
            addingEmployeeStatement.setString(7, employeeDTO.getEmail());

            boolean isEmployeeAdded = addingEmployeeStatement.executeUpdate() > 0;

            if (isEmployeeAdded) {

                String addingUserSQL = "INSERT INTO authentication (employee_id, username, password) VALUES (?,?,?)";

                PreparedStatement addingUserStatement = connection.prepareStatement(addingUserSQL);
                addingUserStatement.setString(1, userDTO.getEmployeeId());
                addingUserStatement.setString(2, userDTO.getUsername());
                addingUserStatement.setString(3, userDTO.getPassword());

                boolean isUserAdded = addingUserStatement.executeUpdate() > 0;

                if (isUserAdded) {

                    String addDoctorSQL = "INSERT INTO doctor VALUES (?,?,?,?,?,?)";

                    PreparedStatement addingDoctorStatement = connection.prepareStatement(addDoctorSQL);
                    addingDoctorStatement.setString(1, doctorDTO.getDoctorId());
                    addingDoctorStatement.setString(2, doctorDTO.getEmployeeId());
                    addingDoctorStatement.setString(3, doctorDTO.getSpecialty());
                    addingDoctorStatement.setString(4, doctorDTO.getRegistrationNumber());
                    addingDoctorStatement.setString(5, doctorDTO.getHospital());
                    addingDoctorStatement.setString(6, doctorDTO.getShift().getTime());

                    boolean isDoctorAdded = addingDoctorStatement.executeUpdate() > 0;

                    if (isDoctorAdded) {
                        return "Employee Added Successfully";
                    }else {
                        connection.rollback();
                        return "Failed to Add Doctor";
                    }
                }else {
                    connection.rollback();
                    return "Failed to Process Authentication";
                }

            }else {
                connection.rollback();
                return "Failed to Save Employee";
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public String updateEmployee(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String updateEmployeeSQL = "UPDATE employee SET name = ?, job_role = ?, recruited_date = ?, address = ?, contact = ?, email = ? WHERE employee_id = ?";

            PreparedStatement updateEmployeeStatement = connection.prepareStatement(updateEmployeeSQL);
            updateEmployeeStatement.setString(1, employeeDTO.getName());
            updateEmployeeStatement.setString(2, employeeDTO.getJobRole().name());
            updateEmployeeStatement.setDate(3, employeeDTO.getRecruitedDate());
            updateEmployeeStatement.setString(4, employeeDTO.getAddress());
            updateEmployeeStatement.setString(5, employeeDTO.getContact());
            updateEmployeeStatement.setString(6, employeeDTO.getEmail());
            updateEmployeeStatement.setString(7, employeeDTO.getEmployeeId());

            boolean isEmployeeUpdated = updateEmployeeStatement.executeUpdate() > 0;

            if (isEmployeeUpdated) {

                String updateUserSQL = "UPDATE authentication SET username = ?, password = ? WHERE employee_id = ?";

                PreparedStatement updateUserStatement = connection.prepareStatement(updateUserSQL);
                updateUserStatement.setString(1, userDTO.getUsername());
                updateUserStatement.setString(2, userDTO.getPassword());
                updateUserStatement.setString(3, userDTO.getEmployeeId());

                boolean isUserUpdated = updateUserStatement.executeUpdate() > 0;

                if (isUserUpdated) {
                    return "Employee Updated Successfully";
                }else {
                    connection.rollback();
                    return "Failed to Update Authentication";
                }

            }else {
                connection.rollback();
                return "Failed to Update Employee";
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public String updateEmployee(EmployeeDTO employeeDTO, DoctorDTO doctorDTO, UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String updateEmployeeSQL = "UPDATE employee SET name = ?, job_role = ?, recruited_date = ?, address = ?, contact = ?, email = ? WHERE employee_id = ?";

            PreparedStatement updateEmployeeStatement = connection.prepareStatement(updateEmployeeSQL);
            updateEmployeeStatement.setString(1, employeeDTO.getName());
            updateEmployeeStatement.setString(2, employeeDTO.getJobRole().name());
            updateEmployeeStatement.setDate(3, employeeDTO.getRecruitedDate());
            updateEmployeeStatement.setString(4, employeeDTO.getAddress());
            updateEmployeeStatement.setString(5, employeeDTO.getContact());
            updateEmployeeStatement.setString(6, employeeDTO.getEmail());
            updateEmployeeStatement.setString(7, employeeDTO.getEmployeeId());

            boolean isEmployeeUpdated = updateEmployeeStatement.executeUpdate() > 0;

            if (isEmployeeUpdated) {

                String updateUserSQL = "UPDATE authentication SET username = ?, password = ? WHERE employee_id = ?";

                PreparedStatement updateUserStatement = connection.prepareStatement(updateUserSQL);
                updateUserStatement.setString(1, userDTO.getUsername());
                updateUserStatement.setString(2, userDTO.getPassword());
                updateUserStatement.setString(3, userDTO.getEmployeeId());

                boolean isUserUpdated = updateUserStatement.executeUpdate() > 0;

                if (isUserUpdated) {

                    String updateDoctorSQL = "UPDATE doctor SET speciality = ?, registration_no = ?, hospital = ?, shift = ? WHERE doctor_id = ?";

                    PreparedStatement updateDoctorStatement = connection.prepareStatement(updateDoctorSQL);
                    updateDoctorStatement.setString(1, doctorDTO.getSpecialty());
                    updateDoctorStatement.setString(2, doctorDTO.getRegistrationNumber());
                    updateDoctorStatement.setString(3, doctorDTO.getHospital());
                    updateDoctorStatement.setString(4, doctorDTO.getShift().getTime());
                    updateDoctorStatement.setString(5, doctorDTO.getDoctorId());

                    boolean isDoctorUpdated = updateDoctorStatement.executeUpdate() > 0;

                    if (isDoctorUpdated) {
                        return "Employee Updated Successfully";
                    }else {
                        connection.rollback();
                        return "Failed to Update Doctor";
                    }

                }else {
                    connection.rollback();
                    return "Failed to Update Authentication";
                }

            }else {
                connection.rollback();
                return "Failed to Update Employee";
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public String getNextId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            String lastAppId = rst.getString("employee_id");
            String lastIdNumberString = lastAppId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("E"+"%04d", nextIdNumber);
            return nextIdString;
        }
        return "E0001";
    }
}
