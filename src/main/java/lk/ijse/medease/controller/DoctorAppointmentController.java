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

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorAppointmentController implements Initializable {

    private int appId;
    private String employeeId = LogInPageController.userDTO.getEmployeeId();
    private String doctorId;

    private AppointmentController appointmentController;
    private  DoctorController doctorController;

    @FXML
    private AnchorPane mainContainerAnc;

    @FXML
    private TableColumn<AppointmentDTO, Number> colAID;

    @FXML
    private TableColumn<AppointmentDTO, Date> colDate;

    @FXML
    private TableColumn<AppointmentDTO, Number> colNo;

    @FXML
    private TableColumn<AppointmentDTO, Number> colPID;

    @FXML
    private TableView<AppointmentDTO> tblAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentController = new AppointmentController();
        doctorController = new DoctorController();

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
            TableRow<AppointmentDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    appId = row.getItem().getAppointmentId();
                    checkIn();
                }
            });
            return row;
        });

        loadData();
    }

    private void loadData() {

        try {
            ArrayList<AppointmentDTO> appointmentDTOs = appointmentController.getAppointmentsByDoctor(doctorId);
            ObservableList<AppointmentDTO> allAppointments = FXCollections.observableArrayList(appointmentDTOs);
            tblAppointment.setItems(allAppointments);
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
            DoctorPrescriptionController.controller.txtAID.setText(String.valueOf(appId));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
