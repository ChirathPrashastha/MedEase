package lk.ijse.medease.dto;

public class InventoryDTO {
    private String inventoryId;
    private int quantity;
    private String supplierId;
    private String section;

    public InventoryDTO(String inventoryId, int quantity, String supplierId, String section) {
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.section = section;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
