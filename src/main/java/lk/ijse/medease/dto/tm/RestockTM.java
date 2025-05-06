package lk.ijse.medease.dto.tm;

import lk.ijse.medease.dto.RestockStatus;

public class RestockTM {
    private String restockId;
    private String medicineId;
    private int requestedQuantity;
    private RestockStatus status;

    public RestockTM(String restockId, String medicineId, int requestedQuantity, RestockStatus status) {
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

    public RestockStatus getStatus() {
        return status;
    }

    public void setStatus(RestockStatus status) {
        this.status = status;
    }
}
