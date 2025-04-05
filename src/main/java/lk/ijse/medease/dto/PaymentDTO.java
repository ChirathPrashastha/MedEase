package lk.ijse.medease.dto;

import java.sql.Date;

public class PaymentDTO {
   private int paymentId;
   private int appointmentId;
   private double amount;
   private Date paidDate;
   private String paymentMethod;

    public PaymentDTO(int appointmentId, double amount, Date paidDate, String paymentStatus) {
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.paidDate = paidDate;
        this.paymentMethod = paymentStatus;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
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

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentStatus) {
        this.paymentMethod = paymentStatus;
    }
}
