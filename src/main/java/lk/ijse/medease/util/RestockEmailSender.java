package lk.ijse.medease.util;

import javafx.scene.control.Alert;
import lk.ijse.medease.controller.MedicineController;
import lk.ijse.medease.controller.RestockController;
import lk.ijse.medease.dto.MedicineDTO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class RestockEmailSender {
    private final RestockController restockController = new RestockController();
    private final MedicineController medicineController = new MedicineController();

    public void restockEmail(String medicineId) throws SQLException {

        String medicineBrand;

        String sendingMessage = """
                Dear valued supplier,
                
                I hope this message finds you well.
                
                We would like to request a restock of {{medicine_name}} at your earliest convenience. Please let us know the available quantity, expected delivery time, and any updates regarding pricing or packaging, if applicable.
                
                Looking forward to your prompt response.
                
                Best regards,
                FAMILY MEDI CLINIC,
                Baddegama Road,
                Gonapinuwala.
                """;

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        String from = "chirathprashastha@gmail.com";
        String pw = "njvl srua uuvx zgvs";
        String to = null;

        try {
            String supplierEmail = restockController.getSupplierEmail(medicineId);
            if (supplierEmail != null) {
                to = supplierEmail;
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier Email Not Found").show();
                return;
            }

            ArrayList<MedicineDTO> medicineDTOS = medicineController.searchMedicine(medicineId);
            if (medicineDTOS.size() > 0) {
                medicineBrand = medicineDTOS.getFirst().getBrand();
                sendingMessage = sendingMessage.replace("{{medicine_name}}", medicineBrand);
            }else {
                new Alert(Alert.AlertType.ERROR, "Medicine Not Found").show();
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String subject = "Medicine Supply Request";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pw);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(sendingMessage);

            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Restock Requesting email send to the supplier Successfully").show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
