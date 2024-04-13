package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.example.service.EmployeeService.employees;

@Setter
@Getter
public class Employee {
    @Getter
    private String id;
    private String password;
    // Setters if necessary
    // 직원 이름 반환
    @Setter
    @Getter
    private String name;
    // 직원 부서 반환
    @Setter
    @Getter
    private String department;
    // 직원 직급 반환
    @Setter
    @Getter
    private String position;
    private static List<String> attendanceRecords; // 출퇴근 기록 저장
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





    public List<String> getAttendanceRecords() {
        return attendanceRecords;
    }


    // 각 필드의 getter 메서드들 생략
}