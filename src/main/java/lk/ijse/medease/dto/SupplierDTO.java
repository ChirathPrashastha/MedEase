package lk.ijse.medease.dto;

public class SupplierDTO {
    private String supplierId;
    private String name;
    private String address;
    private String contact;
    private String email;

    public SupplierDTO(String supplierId, String name, String address, String contact, String email) {
        this.supplierId = supplierId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
