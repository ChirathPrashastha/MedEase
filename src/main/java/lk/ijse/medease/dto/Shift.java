package lk.ijse.medease.dto;

public enum Shift {
    AM_8_00 ("8.00 a.m."),
    PM_5_00 ("5.00 p.m.");

    private String time;

    Shift(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
