package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Setter
@Getter
public class Employee {

    private String id;
    private String password;

    // 직원 이름 반환
    private String name;
    // 직원 부서 반환
    private String department;
    // 직원 직급 반환
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


    // 각 필드의 getter 메서드들 생략
}