package lk.ijse.medease.dto;

import java.sql.Date;

public class MedicineDTO {
    private String medicineId;
    private String genericName;
    private String brand;
    private MedicineCategory category;
    private double price;
    private Date expirationDate;
    private String inventoryId;

    public MedicineDTO(String medicineId, String genericName, String brand, MedicineCategory category, double price, Date expirationDate, String inventoryId) {
        this.medicineId = medicineId;
        this.genericName = genericName;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.expirationDate = expirationDate;
        this.inventoryId = inventoryId;
    }

    public MedicineDTO(String genericName, String brand, MedicineCategory category, double price, Date expirationDate, String inventoryId) {
        this.genericName = genericName;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.expirationDate = expirationDate;
        this.inventoryId = inventoryId;
    }

    public MedicineDTO(String genericName, String brand, MedicineCategory category) {
        this.genericName = genericName;
        this.brand = brand;
        this.category = category;
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
}
