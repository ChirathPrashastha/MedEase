package lk.ijse.medease.dto;

import java.sql.Date;

public class PatientOrderDTO {
    private String orderId;
    private String prescriptionId;
    private double subTotal;
    private Date issuedDate;

    public PatientOrderDTO(String orderId, String prescriptionId, double subTotal) {
        this.orderId = orderId;
        this.prescriptionId = prescriptionId;
        this.subTotal = subTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
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
