package org.example;

import java.util.Date;

public class AttendanceRecord {
    private Date dateTime;
    private boolean isAttendanceIn;
    private String employeeId; // 직원 ID 추가

    public AttendanceRecord(Date dateTime, boolean isAttendanceIn, String employeeId) {
        this.dateTime = dateTime;
        this.isAttendanceIn = isAttendanceIn;
        this.employeeId = employeeId;
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

    public Date getDate() {
        return dateTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
