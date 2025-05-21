module lk.ijse.medease {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;

    opens lk.ijse.medease.controller to javafx.fxml;
    opens lk.ijse.medease.dto to javafx.base;
    opens lk.ijse.medease.dto.tm to javafx.base;
    exports lk.ijse.medease;
}