package org.example;

import java.util.ArrayList;

public class Employee {
    private String name;
    private String position;
    private static String id; // 인스턴스 변수로 변경
    private static String password; // 인스턴스 변수로 변경
    private ArrayList<AttendanceRecord> attendanceRecords;

    public Employee(String name, String position, Member member) {
        this.name = name;
        this.position = position;
        id = member.loginId; // Member 클래스의 아이디 정보 사용
        password = member.loginPw; // Member 클래스의 비밀번호 정보 사용
        this.attendanceRecords = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public static String getId() {
        return id;
    }

    public static String getPassword() {
        return password;
    }

    public void viewAttendanceRecords() {
        System.out.println(name + "님의 출퇴근 기록:");
        for (AttendanceRecord record : attendanceRecords) {
            System.out.println(record);
        }
    }

    public ArrayList<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }
}
