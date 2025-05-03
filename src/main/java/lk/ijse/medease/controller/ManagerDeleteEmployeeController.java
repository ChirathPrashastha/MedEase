package lk.ijse.medease.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.medease.dto.EmployeeDTO;
import lk.ijse.medease.dto.JobRole;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerDeleteEmployeeController implements Initializable {

    private EmployeeController employeeController;

    private EmployeeDTO employeeDTO;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblJobRole;

    @FXML
    private Label lblRecruitedDate;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (lblEmployeeName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please press enter after entering employee ID");
            alert.showAndWait();
        }

        try {
            String response = employeeController.deleteEmployee(employeeDTO.getEmployeeId(), employeeDTO.getJobRole());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Employee");
            alert.setHeaderText(null);
            alert.setContentText(response);
            alert.showAndWait();

            clearFields();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void employeeInfoOnAction(ActionEvent event) {
        try {
            employeeDTO = employeeController.searchEmployee(txtEmployeeId.getText());

            if (employeeDTO != null) {
                lblEmployeeName.setText(employeeDTO.getName());
                lblJobRole.setText(employeeDTO.getJobRole().name());
                lblRecruitedDate.setText(String.valueOf(employeeDTO.getRecruitedDate()));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Employee Not Found");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to retrieve employee information");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeController = new EmployeeController();
    }

    private void clearFields() {
        txtEmployeeId.clear();
        lblEmployeeName.setText("");
        lblJobRole.setText("");
        lblRecruitedDate.setText("");
    }
}