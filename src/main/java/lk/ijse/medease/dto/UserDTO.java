package lk.ijse.medease.dto;

public class UserDTO {
    private int authId;
    private String employeeId;
    private String password;
    private String username;

    public UserDTO(String employeeId, String password, String username) {
        this.employeeId = employeeId;
        this.password = password;
        this.username = username;
    }

    public UserDTO(String employeeId) {
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
