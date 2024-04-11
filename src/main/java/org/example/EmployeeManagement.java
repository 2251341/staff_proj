package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeManagement {
    private static Map<String, Employee> employees = new HashMap<>();
    private static Map<String, String> employeeCredentials = new HashMap<>();
    public static Map<String, String> attendanceRecords = new HashMap<>();
    private static List<String> employeeList = new ArrayList<>();

    // 직원 계정 정보 초기화
    public static void initializeEmployeeCredentials() {
        employeeCredentials.put("employee1", "password1");
        employeeCredentials.put("employee2", "password2");
        // 추가적인 직원 정보 초기화
    }




    public static boolean validateEmployeeCredentials(String username, String password) {
        return employeeCredentials.containsKey(username) && employeeCredentials.get(username).equals(password);
    }
    public static boolean validateEmployee(String username) {
        return employees.containsKey(username);
    }
    public static void registerEmployee(String id, String password) {
        // 직원 정보를 형식에 맞게 문자열로 만듭니다.
        String employeeInfo = id + ": " + password;
        // 직원 목록에 추가합니다.
        employeeList.add(employeeInfo);
    }

    public static void removeEmployee(String id) {
        // 해당 ID를 가진 직원 정보를 찾아 제거합니다.
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).startsWith(id)) {
                employeeList.remove(i);
                break;
            }
        }
    }

    public static void recordAttendance(String id, String attendanceRecord) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        attendanceRecord = "출퇴근 기록 - " + formattedDateTime;
        attendanceRecords.put(id, attendanceRecord);
    }

    public static String getAttendanceRecord(String id) {
        return attendanceRecords.get(id);
    }

    public static List<String> getEmployeeList() {
        return employeeList;
    }
    public static void registerEmployee(String id, String password, String name, String department, String position) {
        Employee employee = new Employee(id, password, name, department, position);
        employees.put(id, employee);
    }

    public static Employee getEmployee(String id) {
        return employees.get(id);
    }
}
