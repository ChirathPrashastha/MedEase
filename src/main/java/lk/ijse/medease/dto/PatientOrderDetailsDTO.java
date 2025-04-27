package lk.ijse.medease.dto;

public class PatientOrderDetailsDTO {
    private String orderId;
    private String medicineId;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

    public PatientOrderDetailsDTO(String orderId, String medicineId,double unitPrice, int quantity, double totalPrice) {
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
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
