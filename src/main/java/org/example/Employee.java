package org.example;

import java.util.ArrayList;

public class Employee {
    private String name;
    private String position;
    private String id;
    private String password;
    private ArrayList<AttendanceRecord> attendanceRecords;

    public Employee(String name, String position, String id, String password) {
        this.name = name;
        this.position = position;
        this.id = id;
        this.password = password;
        this.attendanceRecords = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public Object getPassword() {
        return password;
    }

    public void viewAttendanceRecords(AttendanceRecord[] attendanceRecords) {
        System.out.println(name + "님의 출퇴근 기록:");
        for (AttendanceRecord record : attendanceRecords) {
            System.out.println(record);
        }
    }

    public ArrayList<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }
}