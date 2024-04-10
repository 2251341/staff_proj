package org.example.service;

import org.example.dao.AttendanceDao;
import org.example.dto.Attendance;

import java.util.List;

public class AttendanceService {
    private AttendanceDao attendanceDao;

    public AttendanceService() {
        attendanceDao = new AttendanceDao();
    }

    public void recordAttendance(Attendance attendance) {
        attendanceDao.record(attendance);
    }

    public List<Attendance> getAttendanceByEmployeeId(int employeeId) {
        return attendanceDao.getByEmployeeId(employeeId);
    }

    public int getLastAttendanceId() {
        return attendanceDao.getLastAttendanceId();
    }
}
