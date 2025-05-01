package lk.ijse.medease.dto.tm;

import java.sql.Date;

public class PaymentTM {
    private String paymentId;
    private String appointmentId;
    private double amount;
    private Date paidDate;
    private String paymentMethod;

    public PaymentTM(String paymentId, String appointmentId, double amount, Date paidDate, String paymentMethod) {
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

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
