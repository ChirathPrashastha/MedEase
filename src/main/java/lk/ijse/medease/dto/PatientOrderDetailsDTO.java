package lk.ijse.medease.dto;

public class PatientOrderDetailsDTO {
    private int orderId;
    private int medicineId;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

    public PatientOrderDetailsDTO(int orderId, int medicineId,double unitPrice, int quantity, double totalPrice) {
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
