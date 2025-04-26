package lk.ijse.medease.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.medease.dto.AppointmentDTO;
import lk.ijse.medease.dto.CheckInDTO;
import lk.ijse.medease.dto.DoctorDTO;
import lk.ijse.medease.dto.PatientDTO;
import lk.ijse.medease.dto.tm.AppointmentTM;
import lk.ijse.medease.dto.tm.CheckInTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorAppointmentController implements Initializable {

    public static DoctorAppointmentController doctorAppointmentController; // for DoctorPrescription

    private int appointmentId;
    private int patientId;
    private String employeeId = LogInPageController.userDTO.getEmployeeId();
    public String doctorId;
    public int checkInNo;

    private AppointmentController appointmentController;
    private  DoctorController doctorController;
    private PatientController patientController;
    private CheckInController checkInController;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    private TableColumn<CheckInTM, Number> colNum;

    @FXML
    private TableColumn<CheckInTM, String> colStatus;

    @FXML
    private TableView<CheckInTM> tblCheckIn;

    @FXML
    private TableColumn<AppointmentTM, Number> colAID;

    @FXML
    private TableColumn<AppointmentTM, Date> colDate;

    @FXML
    private TableColumn<AppointmentTM, Number> colNo;

    @FXML
    private TableColumn<AppointmentTM, Number> colPID;

    @FXML
    private TableView<AppointmentTM> tblAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        doctorAppointmentController = this;
        appointmentController = new AppointmentController();
        doctorController = new DoctorController();
        patientController = new PatientController();
        checkInController = new CheckInController();

        try {
            doctorId = doctorController.getDoctorId(employeeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        colAID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colPID.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNo.setCellValueFactory(new PropertyValueFactory<>("checkInNo"));

        tblAppointment.setRowFactory(tv -> {
            TableRow<AppointmentTM> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    appointmentId = row.getItem().getAppointmentId();
                    patientId = row.getItem().getPatientId();
                    checkInNo = row.getItem().getCheckInNo();

                    checkIn();
                }
            });
            return row;
        });
        loadData();

        colNum.setCellValueFactory(new PropertyValueFactory<>("checkInNo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadCheckInTable();
    }

    private void loadData() {

        try {
            ArrayList<AppointmentDTO> appointmentDTOs = appointmentController.getAppointmentsByDoctor(doctorId);
            ObservableList<AppointmentTM> appointmentTMObList = FXCollections.observableArrayList();

            for (AppointmentDTO dto : appointmentDTOs){
                AppointmentTM appointmentTM = new AppointmentTM(dto.getAppointmentId(), dto.getPatientId(), dto.getDate(), dto.getCheckInNo(), dto.getTime());
                appointmentTMObList.add(appointmentTM);
            }

            tblAppointment.setItems(appointmentTMObList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkIn() {
        try {
            mainContainerAnc.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DoctorPrescription.fxml"));
            anchorPane.prefWidthProperty().bind(mainContainerAnc.widthProperty());
            anchorPane.prefHeightProperty().bind(mainContainerAnc.heightProperty());

            mainContainerAnc.getChildren().add(anchorPane);
            DoctorDashboardController.controller.setBtnPrescription();

            //Updated details in Doctor Prescription
            updatePrescription();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePrescription() {
        try {
            DoctorPrescriptionController.controller.txtAID.setText(String.valueOf(appointmentId));
            DoctorPrescriptionController.controller.txtPID.setText(String.valueOf(patientId));

            PatientDTO patientDTO = patientController.searchPatient(patientId);

            DoctorPrescriptionController.controller.txtPatientName.setText(patientDTO.getName());
            DoctorPrescriptionController.controller.txtAllergies.setText(patientDTO.getAllergies());

            LocalDate localBirthDate = patientDTO.getBirthDate().toLocalDate(); // Converting sql date to localDate
            LocalDate currentDate = LocalDate.now();

            Period period = Period.between(localBirthDate, currentDate);
            DoctorPrescriptionController.controller.txtAge.setText(String.valueOf(period.getYears()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCheckInTable() {
        try {
            ArrayList<CheckInDTO> checkInDTOs = checkInController.getDoctorCheckInList(doctorId);
            ObservableList<CheckInTM> checkInObList = FXCollections.observableArrayList();

            for (CheckInDTO dto : checkInDTOs){
                CheckInTM checkInTM = new CheckInTM(dto.getCheckInNo(), dto.getStatus());
                checkInObList.add(checkInTM);
            }

            tblCheckIn.setItems(checkInObList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
