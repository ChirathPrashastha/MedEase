package lk.ijse.medease.dto;

import java.sql.Date;

public class MedicineDTO {
    private String medicineId;
    private String genericName;
    private String brand;
    private String category;
    private double price;
    private Date expirationDate;
    private int inventoryId;

    public MedicineDTO(String medicineId, String genericName, String brand, String category, double price, Date expirationDate, int inventoryId) {
        this.medicineId = medicineId;
        this.genericName = genericName;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.expirationDate = expirationDate;
        this.inventoryId = inventoryId;
    }

    public MedicineDTO(String genericName, String brand, String category, double price, Date expirationDate, int inventoryId) {
        this.genericName = genericName;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.expirationDate = expirationDate;
        this.inventoryId = inventoryId;
    }

    public MedicineDTO(String genericName, String brand, String category) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
}
