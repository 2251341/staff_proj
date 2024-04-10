package org.example.dao;

import org.example.dto.Attendance;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
    private List<Attendance> attendanceRecords;
    private int lastAttendanceId;

    public AttendanceDao() {
        this.attendanceRecords = new ArrayList<>();
        this.lastAttendanceId = 0;
    }

    public void record(Attendance attendance) {
        lastAttendanceId++;
        attendance.setId(lastAttendanceId);
        attendanceRecords.add(attendance);
    }

    public List<Attendance> getByEmployeeId(int employeeId) {
        List<Attendance> records = new ArrayList<>();
        for (Attendance record : attendanceRecords) {
            if (record.getEmployeeId() == employeeId) {
                records.add(record);
            }
        }
        return records;
    }

    public int getLastAttendanceId() {
        return lastAttendanceId;
    }
}
