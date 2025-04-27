package lk.ijse.medease.dto;

import java.sql.Date;

public class PatientOrderDTO {
    private int orderId;
    private String prescriptionId;
    private double subTotal;
    private Date issuedDate;

    public PatientOrderDTO(int orderId, String prescriptionId, double subTotal) {
        this.orderId = orderId;
        this.prescriptionId = prescriptionId;
        this.subTotal = subTotal;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
