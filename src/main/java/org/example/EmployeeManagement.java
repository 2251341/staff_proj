package org.example;

import  java.time.LocalDateTime;
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
        // 직원 객체 초기화도 수행
        employees.put("employee1", new Employee("employee1", "password1", "Employee One", "Department One", "Position One"));
        employees.put("employee2", new Employee("employee2", "password2", "Employee Two", "Department Two", "Position Two"));
    }
    public static void addEmployee(Employee employee, String id) {
        employees.put(id, employee);
    }




    public static boolean validateEmployeeCredentials(String username, String password) {
        String correctPassword = employeeCredentials.get(username);
        return correctPassword != null && correctPassword.equals(password);
    }
    public static boolean validateEmployee(String username) {
        return employees.containsKey(username);
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

    public static void recordAttendance(String id) {
        if (employees.containsKey(id)) {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = "Attendance record - " + currentTime.format(formatter);
            employees.get(id).addAttendanceRecord(formattedDateTime);
            System.out.println(id + "의 출퇴근이 기록되었습니다.");
        } else {
            System.out.println("직원을 찾을 수 없습니다.");
        }
    }

    public static String getAttendanceRecord(String id) {
        return attendanceRecords.get(id);
    }

    public static List<String> getEmployeeList() {
        return new ArrayList<>(employeeList); // employeeList의 복사본을 반환합니다.
    }

    public static Employee getEmployee(String id) {
        return employees.get(id);
    }

    public static void registerEmployee(String id, String password, String name, String department, String position) {
        if (!employeeCredentials.containsKey(id)) {
            employeeCredentials.put(id, password);
            Employee newEmployee = new Employee(id, password, name, department, position);
            employees.put(id, newEmployee);
            // 직원 상세 정보를 문자열로 변환하여 employeeList에 추가합니다.
            employeeList.add(String.format("%s, %s, %s, %s", id, name, department, position));
            System.out.println("직원이 성공적으로 등록되었습니다."); // 직원이 성공적으로 등록되었습니다.
        } else {
            System.out.println("직원 ID가 이미 존재합니다."); // 직원 ID가 이미 존재합니다.
        }
    }
}
