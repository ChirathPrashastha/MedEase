package lk.ijse.medease.dto.tm;

import lk.ijse.medease.dto.MedicineCategory;

import java.sql.Date;

public class MedicineInventoryTM {
    private String medicineId;
    private String genericName;
    private String brand;
    private MedicineCategory category;
    private double price;
    private Date expirationDate;
    private String inventoryId;
    private int quantity;
    private String supplierId;
    private String section;

    public MedicineInventoryTM(String medicineId, String brand, double price, Date expirationDate, String inventoryId, int quantity, String supplierId, String section) {
        this.medicineId = medicineId;
        this.brand = brand;
        this.price = price;
        this.expirationDate = expirationDate;
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.section = section;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public MedicineCategory getCategory() {
        return category;
    }

    public void setCategory(MedicineCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
