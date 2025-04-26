package lk.ijse.medease.dto.tm;

import java.sql.Date;

public class PaymentTM {
    private int paymentId;
    private int appointmentId;
    private double amount;
    private Date paidDate;
    private String paymentMethod;

    public PaymentTM(int paymentId, int appointmentId, double amount, Date paidDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.paidDate = paidDate;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
