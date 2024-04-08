package org.example;

import java.util.Date;

public class AttendanceRecord {
    private Date dateTime;
    private boolean isAttendanceIn;

    public AttendanceRecord(Date dateTime, boolean isAttendanceIn) {
        this.dateTime = dateTime;
        this.isAttendanceIn = isAttendanceIn;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public boolean isAttendanceIn() {
        return isAttendanceIn;
    }

    @Override
    public String toString() {
        String action = isAttendanceIn ? "출근" : "퇴근";
        return "[" + dateTime + "] " + action;
    }
}