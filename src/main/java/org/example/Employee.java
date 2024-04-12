package org.example;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String id;
    private String password;
    private String name;
    private String department;
    private String position;
    private List<String> attendanceRecords; // 출퇴근 기록 저장
    private List<String> checkInRecords; // Separate list for check-in records
    private List<String> checkOutRecords; // Separate list for check-out records

    public Employee(String id, String password, String name, String department, String position) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.department = department;
        this.position = position;
        this.attendanceRecords = new ArrayList<>();
        this.checkInRecords = new ArrayList<>();
        this.checkOutRecords = new ArrayList<>();
    }

    // 직원 이름 반환
    public String getName() {
        return name;
    }
    // 직원 부서 반환
    public String getDepartment() {
        return department;
    }

    // 직원 직급 반환
    public String getPosition() {
        return position;
    }
    public void addAttendanceRecord(String record) {
        attendanceRecords.add(record);
    }

    // 출퇴근 기록 조회 메서드
    public List<String> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void addCheckInRecord(String formattedDateTime) {
        attendanceRecords.add("Check-in record - " + formattedDateTime);
    }

    public void addCheckOutRecord(String formattedDateTime) {
        attendanceRecords.add("Check-out record - " + formattedDateTime);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    // 각 필드의 getter 메서드들 생략
}
