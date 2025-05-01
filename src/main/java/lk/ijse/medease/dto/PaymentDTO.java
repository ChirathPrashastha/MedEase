package lk.ijse.medease.dto;

import java.sql.Date;

public class PaymentDTO {
   private String paymentId;
   private String appointmentId;
   private double amount;
   private Date paidDate;
   private String paymentMethod;

    public PaymentDTO(String paymentId, String appointmentId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public PaymentDTO(String paymentId, String appointmentId, double amount, Date paidDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.paidDate = paidDate;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentStatus) {
        this.paymentMethod = paymentStatus;
    }
}
