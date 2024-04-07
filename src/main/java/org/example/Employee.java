package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Employee {
    private String name;
    private int age;
    private String position;
    private double salary;

    public Employee(String name, int age, String position, double salary) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }
}

class AttendanceManager {
    private HashMap<String, ArrayList<String>> attendanceLogs;

    public AttendanceManager() {
        attendanceLogs = new HashMap<>();
    }

    public void clockIn(String name, String time) {
        if (!attendanceLogs.containsKey(name)) {
            attendanceLogs.put(name, new ArrayList<>());
        }
        attendanceLogs.get(name).add("출근: " + time);
        System.out.println(name + "님이 " + time + "에 출근하였습니다.");
    }

    public void clockOut(String name, String time) {
        if (!attendanceLogs.containsKey(name)) {
            System.out.println("출근 기록이 없습니다.");
            return;
        }
        attendanceLogs.get(name).add("퇴근: " + time);
        System.out.println(name + "님이 " + time + "에 퇴근하였습니다.");
    }

    public void showAttendanceLog(String name) {
        if (!attendanceLogs.containsKey(name)) {
            System.out.println(name + "님의 출퇴근 기록이 없습니다.");
            return;
        }
        System.out.println(name + "님의 출퇴근 기록:");
        for (String log : attendanceLogs.get(name)) {
            System.out.println(log);
        }
    }
}