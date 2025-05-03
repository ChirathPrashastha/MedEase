package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.EmployeeDTO;
import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.tm.EmployeeTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerViewEmployee implements Initializable {

    private EmployeeController employeeController;

    @FXML
    private TableColumn<EmployeeTM, String> colAddress;

    @FXML
    private TableColumn<EmployeeTM, String> colContact;

    @FXML
    private TableColumn<EmployeeTM, String> colEID;

    @FXML
    private TableColumn<EmployeeTM, String> colEmail;

    @FXML
    private TableColumn<EmployeeTM, JobRole> colJobRole;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, Date> colRecruitedDate;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        employeeController = new EmployeeController();

        colEID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colRecruitedDate.setCellValueFactory(new PropertyValueFactory<>("recruitedDate"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            ArrayList<EmployeeDTO> employeeDTOs = employeeController.getAllEmployees();
            ObservableList<EmployeeTM> employeeTMObList = FXCollections.observableArrayList();

            for (EmployeeDTO dto : employeeDTOs) {
                EmployeeTM employeeTM = new EmployeeTM(dto.getEmployeeId(), dto.getName(), dto.getJobRole(), dto.getRecruitedDate(), dto.getAddress(), dto.getContact(), dto.getEmail());
                employeeTMObList.add(employeeTM);
            }

            tblEmployee.setItems(employeeTMObList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
        }
    }
}