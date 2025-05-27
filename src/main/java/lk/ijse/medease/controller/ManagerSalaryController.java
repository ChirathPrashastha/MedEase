package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.medease.dto.EmployeeDTO;
import lk.ijse.medease.dto.JobRole;
import lk.ijse.medease.dto.SalaryDTO;
import lk.ijse.medease.dto.tm.SalaryTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ManagerSalaryController implements Initializable {

    private final AttendanceController attendanceController = new AttendanceController();
    private final EmployeeController employeeController = new EmployeeController();
    private final SalaryController salaryController = new SalaryController();

    private double doctorBasicSalary;
    private double nurseBasicSalary;
    private double receptionBasicSalary;
    private double managerBasicSalary;

    private double bonus = 0.0;

    private ArrayList<EmployeeDTO> allEmployees;
    private ObservableList<SalaryTM> salaryObList;
    private Map<String, Double> salaryMap;

    private JobRole jobRole;

    @FXML
    private RadioButton btnDoctor;

    @FXML
    private RadioButton btnManager;

    @FXML
    private RadioButton btnNurse;

    @FXML
    private RadioButton btnReceptionist;

    @FXML
    private TableColumn<SalaryTM, Double> colBasicSalary;

    @FXML
    private TableColumn<SalaryTM, Double> colBonus;

    @FXML
    private TableColumn<SalaryTM, Date> colDate;

    @FXML
    private TableColumn<SalaryTM, String> colEmployeeId;

    @FXML
    private TableColumn<SalaryTM, Double> colNetSalary;

    @FXML
    private TableView<SalaryTM> tblSalary;

    @FXML
    private TextField txtBasicSalary;

    @FXML
    private TextField txtBonus;

    @FXML
    void btnProceedOnAction(ActionEvent event) {
        if (txtBasicSalary.getText() != null) {
            try {
                if (btnDoctor.isSelected()) {
                    String response = salaryController.setSalaryPerDay(JobRole.DOCTOR, Double.parseDouble(txtBasicSalary.getText()));
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                }else if (btnNurse.isSelected()) {
                    String response = salaryController.setSalaryPerDay(JobRole.NURSE, Double.parseDouble(txtBasicSalary.getText()));
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                }else if (btnReceptionist.isSelected()) {
                    String response = salaryController.setSalaryPerDay(JobRole.RECEPTIONIST, Double.parseDouble(txtBasicSalary.getText()));
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                }else if (btnManager.isSelected()) {
                    String response = salaryController.setSalaryPerDay(JobRole.MANAGER, Double.parseDouble(txtBasicSalary.getText()));
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else if (txtBonus.getText() != null) {
            bonus = Double.parseDouble(txtBonus.getText());
        }else {
            new Alert(Alert.AlertType.ERROR, "Please check the fields").show();
            return;
        }

        calculateSalary();
        clearFields();
        addSalaryToDatabase();
        loadTable();
    }

    @FXML
    void radioBtnDoctorOnAction(ActionEvent event) {

        btnNurse.setDisable(true);
        btnManager.setDisable(true);
        btnReceptionist.setDisable(true);
    }

    @FXML
    void radioBtnManagerOnAction(ActionEvent event) {

        btnNurse.setDisable(true);
        btnDoctor.setDisable(true);
        btnReceptionist.setDisable(true);
    }

    @FXML
    void radioBtnNurseOnAction(ActionEvent event) {

        btnDoctor.setDisable(true);
        btnManager.setDisable(true);
        btnReceptionist.setDisable(true);
    }

    @FXML
    void radioBtnReceptionistOnAction(ActionEvent event) {

        btnDoctor.setDisable(true);
        btnManager.setDisable(true);
        btnNurse.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        allEmployees = new ArrayList<>();
        salaryMap = new TreeMap<>();
        salaryObList = FXCollections.observableArrayList();

        loadBasicSalary();
        calculateSalary();
        loadTable();

    }

    private void loadBasicSalary() {
        try {
            Map<JobRole, Double> salaryPerDayMap = salaryController.getSalaryPerDay();
            for (Map.Entry<JobRole, Double> entry : salaryPerDayMap.entrySet()) {
                if (entry.getKey() == JobRole.DOCTOR) {
                    doctorBasicSalary = entry.getValue();
                }else if (entry.getKey() == JobRole.NURSE) {
                    nurseBasicSalary = entry.getValue();
                }else if (entry.getKey() == JobRole.RECEPTIONIST) {
                    receptionBasicSalary = entry.getValue();
                }else if (entry.getKey() == JobRole.MANAGER) {
                    managerBasicSalary = entry.getValue();
                }
            }
            for (Map.Entry<JobRole, Double> entry : salaryPerDayMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to retrieve salary per day", ButtonType.OK).show();
        }
    }

    private void calculateSalary() {
        LocalDate today = LocalDate.now();

        try {
            Map<String, Integer> attendanceMap = attendanceController.getAttendanceCountOfEachEmployee(Date.valueOf(today));

            allEmployees = employeeController.getAllEmployees();

            L1: for (Map.Entry<String, Integer> entry : attendanceMap.entrySet()) {
                String employeeId = entry.getKey();
                int dayCount = entry.getValue();

                for (EmployeeDTO employee : allEmployees) {
                    if (employee.getEmployeeId().equals(employeeId) && employee.getJobRole().equals(JobRole.DOCTOR)) {
                        double netSalary = (dayCount * doctorBasicSalary) + bonus;
                        salaryMap.put(employeeId, netSalary);
                        continue L1;
                    }else if (employee.getEmployeeId().equals(employeeId) && employee.getJobRole().equals(JobRole.NURSE)) {
                        double netSalary = (dayCount * nurseBasicSalary) + bonus;
                        salaryMap.put(employeeId, netSalary);
                        continue L1;
                    }else if (employee.getEmployeeId().equals(employeeId) && employee.getJobRole().equals(JobRole.RECEPTIONIST)) {
                        double netSalary = (dayCount * receptionBasicSalary) + bonus;
                        salaryMap.put(employeeId, netSalary);
                        continue L1;
                    }else if (employee.getEmployeeId().equals(employeeId) && employee.getJobRole().equals(JobRole.MANAGER)) {
                        double netSalary = (dayCount * managerBasicSalary) + bonus;
                        salaryMap.put(employeeId, netSalary);
                        continue L1;
                    }
                }
            }

            for (Map.Entry<String, Integer> entry : attendanceMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {

        for (Map.Entry<String, Double> entry : salaryMap.entrySet()) {
            double basicSalary = 0;
            for (EmployeeDTO employee : allEmployees) {
                if (employee.getEmployeeId().equals(entry.getKey()) && employee.getJobRole().equals(JobRole.DOCTOR)) {
                    basicSalary = doctorBasicSalary;
                    break;
                }else if (employee.getEmployeeId().equals(entry.getKey()) && employee.getJobRole().equals(JobRole.NURSE)) {
                    basicSalary = nurseBasicSalary;
                    break;
                }else if (employee.getEmployeeId().equals(entry.getKey()) && employee.getJobRole().equals(JobRole.RECEPTIONIST)) {
                    basicSalary = receptionBasicSalary;
                    break;
                }else if (employee.getEmployeeId().equals(entry.getKey()) && employee.getJobRole().equals(JobRole.MANAGER)) {
                    basicSalary = managerBasicSalary;
                    break;
                }
            }
            SalaryTM salaryTM = new SalaryTM(entry.getKey(), basicSalary, bonus, entry.getValue(), Date.valueOf(LocalDate.now()));
            salaryObList.add(salaryTM);
        }

        tblSalary.setItems(salaryObList);
    }

    public void loadTableFromDB(){
        salaryObList.clear();
        try {
            ArrayList<SalaryDTO> salaryDTOS = salaryController.getSalary();
            for (SalaryDTO dto : salaryDTOS) {
                SalaryTM salaryTM = new SalaryTM(dto.getEmployeeId(), dto.getBasicSalary(), dto.getBonus(), dto.getNetSalary(), dto.getDate());
                salaryObList.add(salaryTM);
            }
            tblSalary.setItems(salaryObList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addSalaryToDatabase() {
        ArrayList<SalaryDTO> salaryDTOS = new ArrayList<>();
        for (SalaryTM tm : salaryObList) {
            SalaryDTO salaryDTO = new SalaryDTO(tm.getEmployeeId(), tm.getBasicSalary(), tm.getBonus(), tm.getNetSalary(), tm.getDate());
            salaryDTOS.add(salaryDTO);
        }

        try {
            String response = salaryController.addSalary(salaryDTOS);
            new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void clearFields() {
        txtBasicSalary.clear();
        txtBonus.clear();
        btnDoctor.setDisable(false);
        btnManager.setDisable(false);
        btnReceptionist.setDisable(false);
        btnNurse.setDisable(false);
    }

}