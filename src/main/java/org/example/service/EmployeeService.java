package org.example.service;

import org.example.dto.Employee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeService {
    public static Map<String, String> attendanceRecords;
    public static Map<String, Employee> employees = new HashMap<>();
    private static Map<String, String> employeeCredentials = new HashMap<>();
    private static List<String> employeeList = new ArrayList<>();

    public EmployeeService() {
        initializeEmployeeCredentials();
    }


    public static boolean validateEmployee(String username) {
        return employees.containsKey(username);
    }

    private void initializeEmployeeCredentials() {
        employeeCredentials.put("user1", "user1");
        employeeCredentials.put("user2", "user2");
        employees.put("user1", new Employee("user1", "user1", "홍길동", "인사", "사원"));
        employees.put("user2", new Employee("user2", "user2", "홍길순", "IT", "대리"));
    }

    public static void registerEmployee(String id, String password, String name, String department, String position) {
        if (!employeeCredentials.containsKey(id)) {
            employeeCredentials.put(id, password);
            Employee newEmployee = new Employee(id, password, name, department, position);
            employees.put(id, newEmployee);
            employeeList.add(String.format("%s, %s, %s, %s", id, name, department, position));
            System.out.println("직원이 성공적으로 등록되었습니다.");
        } else {
            System.out.println("직원 ID가 이미 존재합니다.");
        }
    }

    public static boolean validateEmployeeCredentials(String username, String password) {
        String correctPassword = employeeCredentials.get(username);
        return correctPassword != null && correctPassword.equals(password);
    }

    public static Employee getEmployee(String id) {
        return employees.get(id);
    }

    public static List<Employee> getEmployeeList() {
        return new ArrayList<>(employees.values());
    }

    public static void removeEmployee(String id) {
        if (employees.remove(id) != null) {
            employeeList.removeIf(e -> e.startsWith(id));
            System.out.println("직원이 삭제되었습니다.");
        } else {
            System.out.println("직원을 찾을 수 없습니다.");
        }
    }
}
