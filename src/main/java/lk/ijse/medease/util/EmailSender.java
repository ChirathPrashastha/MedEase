package lk.ijse.medease.util;

import javafx.scene.control.Alert;
import lk.ijse.medease.controller.AppointmentController;
import lk.ijse.medease.controller.CheckInController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class EmailSender {

    private final CheckInController checkInController = new CheckInController();
    private final AppointmentController appointmentController = new AppointmentController();

    public void sendCheckOutEmail(String doctorId) {
        int count = 0;
        String sendingMessage = null;

        String messageOfFirstBatch = """
                Dear Valued Patients,
                
                We would like to inform you that the first group of patients (1–10) has been successfully checked out.
                
                If you are scheduled after this group, we kindly ask you to remain patient as we proceed with the next set of check-outs. You will receive another notification once your turn approaches.
                
                Thank you for your understanding and cooperation.
                
                Best regards,
                FAMILY MEDI CLINIC.
                """;

        String messageOfSecondBatch = """
                Dear Valued Patients,
                
                This is to inform you that patients numbered 11–20 have now been checked out.
                
                We appreciate your continued patience as we work diligently to assist each patient in a timely manner. You will be notified once we are ready for the next group.
                
                Warm regards,
                FAMILY MEDI CLINIC.
                """;

        String messageOfThirdBatch = """
                Dear Patients,
                
                Please note that the next group of patients, numbered 21–30, has been checked out.
                
                We thank you for your cooperation as we continue to provide service. If your appointment falls in the upcoming batches, please stay alert for further updates.
                
                Sincerely,
                FAMILY MEDI CLINIC.
                """;

        String messageOfFourthBatch = """
                Dear Patients,
                
                We would like to update you that patients numbered 31–40 have now completed their appointments and checked out.
                
                We are progressing smoothly and will notify you again as the next group is processed. We truly appreciate your patience.
                
                With thanks,
                FAMILY MEDI CLINIC.
                """;

        String messageOfFifthBatch = """
                Dear Patients,
                
                We are pleased to inform you that patients 41–50 have been successfully checked out.
                
                We are nearing the final round of appointments. Please continue to stay updated via email for further instructions or updates.
                
                Thank you for your time and understanding.
                
                Best regards,
                FAMILY MEDI CLINIC.
                """;

        try {
            count = checkInController.getCheckOutCount(doctorId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send check out emails").showAndWait();
            return;
        }

        Map<String, String> emails = new TreeMap<>();

        if (count == 10) {
            try {
                emails = appointmentController.getEmailsOfRemainingPatients(10);
                sendingMessage = messageOfFirstBatch;
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to send check out emails").showAndWait();
                return;
            }
        }else if (count == 20) {
            try {
                emails = appointmentController.getEmailsOfRemainingPatients(20);
                sendingMessage = messageOfSecondBatch;
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to send check out emails").showAndWait();
                return;
            }
        } else if (count == 30) {
            try {
                emails = appointmentController.getEmailsOfRemainingPatients(30);
                sendingMessage = messageOfThirdBatch;
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to send check out emails").showAndWait();
                return;
            }
        }else if (count == 40) {
            try {
                emails = appointmentController.getEmailsOfRemainingPatients(40);
                sendingMessage = messageOfFourthBatch;
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to send check out emails").showAndWait();
                return;
            }
        }else if (count == 50) {
            try {
                emails = appointmentController.getEmailsOfRemainingPatients(50);
                sendingMessage = messageOfFifthBatch;
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to send check out emails").showAndWait();
                return;
            }
        }else{
            System.out.println("emails did not send");
            return;
        }

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        String from = "chirathprashastha@gmail.com";
        String pw = "njvl srua uuvx zgvs";
        String to;

        String subject = "Patient Check-Out Progress Update";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pw);
            }
        });

        for (Map.Entry<String, String> entry : emails.entrySet()) {
            to = entry.getValue();

            try {
                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(sendingMessage);

                Transport.send(message);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        new Alert(Alert.AlertType.INFORMATION, "Emails Sent Successfully").show();

    }
}
