package lk.ijse.medease.dto;

public class UserDTO {
    private int authId;
    private String employeeId;

    public UserDTO(int authId, String employeeId) {
        this.authId = authId;
        this.employeeId = employeeId;
    }
    public int getAuthId() {
        return authId;
    }
    public void setAuthId(int authId) {
        this.authId = authId;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
