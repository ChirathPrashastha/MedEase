package lk.ijse.medease.dto;

import java.sql.Date;

public class PatientOrderDTO {
    private int orderId;
    private int prescriptionId;
    private double subTotal;
    private Date issuedDate;

    public PatientOrderDTO(int orderId, int prescriptionId, double subTotal, Date issuedDate) {
        this.orderId = orderId;
        this.prescriptionId = prescriptionId;
        this.subTotal = subTotal;
        this.issuedDate = issuedDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
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
