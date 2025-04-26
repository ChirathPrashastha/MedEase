package lk.ijse.medease.dto.tm;

import java.sql.Date;

public class CheckInTM {
    private int checkInNo;
    private String status;

    public CheckInTM(int checkInNo, String status) {
        this.checkInNo = checkInNo;
        this.status = status;
    }

    public int getCheckInNo() {
        return checkInNo;
    }

    public void setCheckInNo(int checkInNo) {
        this.checkInNo = checkInNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
