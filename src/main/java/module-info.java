module lk.ijse.medease {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens lk.ijse.medease.controller to javafx.fxml;
    opens lk.ijse.medease.dto to javafx.base;
    exports lk.ijse.medease;
}