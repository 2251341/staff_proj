package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Setter
@Getter
public class Employee {

    private int id;
    private String regDate;
    private String employeeId;
    private String password;

    // 직원 이름 반환
    private String name;
    // 직원 부서 반환
    private String department;
    // 직원 직급 반환
    private String position;


    private List<String> attendanceRecords; // 출퇴근 기록 저장


    public Employee(String employeeId, String password, String name, String department, String position) {

        this.employeeId = employeeId;
        this.password = password;
        this.name = name;
        this.department = department;
        this.position = position;

        attendanceRecords = new ArrayList<>();
    }
    public Employee(Map<String, Object> row) {
        super();
        this.employeeId = (String) row.get("ID");
        this.password = (String) row.get("password");
        this.name = (String) row.get("name");
        this.department = (String) row.get("department");
        this.position = (String) row.get("position");

    }
    // 각 필드의 getter 메서드들 생략
}