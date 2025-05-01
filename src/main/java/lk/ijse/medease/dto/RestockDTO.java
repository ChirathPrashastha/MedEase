package lk.ijse.medease.dto;

public class RestockDTO {
    private String restockId;
    private String medicineId;
    private int requestedQuantity;
    private String status;

    public RestockDTO(String restockId, String medicineId, int requestedQuantity, String status) {
        this.restockId = restockId;
        this.medicineId = medicineId;
        this.requestedQuantity = requestedQuantity;
        this.status = status;
    }

    public String getRestockId() {
        return restockId;
    }

    public void setRestockId(String restockId) {
        this.restockId = restockId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
