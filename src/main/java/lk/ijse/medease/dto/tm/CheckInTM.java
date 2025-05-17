package lk.ijse.medease.dto.tm;

import lk.ijse.medease.dto.CheckInStatus;

import java.sql.Date;

public class CheckInTM {
    private int checkInNo;
    private CheckInStatus status;

    public CheckInTM(int checkInNo, CheckInStatus status) {
        this.checkInNo = checkInNo;
        this.status = status;
    }

    public int getCheckInNo() {
        return checkInNo;
    }

    public void setCheckInNo(int checkInNo) {
        this.checkInNo = checkInNo;
    }

    public CheckInStatus getStatus() {
        return status;
    }

    public void setStatus(CheckInStatus status) {
        this.status = status;
    }
}
