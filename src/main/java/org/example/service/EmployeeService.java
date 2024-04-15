package org.example.service;

import org.example.dto.Employee;
import org.example.dao.EmployeeDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    public static HashMap<String, String> attendanceRecords;
    public static Map<String, Employee> employees = new HashMap<>();
    private static Map<String, String> employeeCredentials = new HashMap<>();
    private static List<String> employeeList = new ArrayList<>();
    private static EmployeeDao employeeDao;



    public EmployeeService() {
        employeeDao = new EmployeeDao();
        if (attendanceRecords == null) {
            attendanceRecords = new HashMap<>();
        }
    }

    public static boolean validateEmployee(String username) {
        return employeeDao.findEmployeeById(username) != null;
    }

    public static void registerEmployee(String id, String password, String name, String department, String position) {
        Employee existingEmployee = employeeDao.findEmployeeById(id);
        if (existingEmployee == null) {
            Employee newEmployee = new Employee(id, password, name, department, position);
            employeeDao.addEmployee(newEmployee);
            System.out.println("직원이 성공적으로 등록되었습니다.");
        } else {
            System.out.println("직원 ID가 이미 존재합니다.");
        }
    }

    public static boolean validateEmployeeCredentials(String username, String password) {
        Employee employee = employeeDao.findEmployeeById(username);
        return employee != null && employee.getPassword().equals(password);
    }

    public static Employee getEmployee(String id) {
        return employeeDao.findEmployeeById(id);
    }

    public static void removeEmployee(String id) {
        if (employeeDao.removeEmployee(id)) {
            System.out.println("직원이 삭제되었습니다.");
        } else {
            System.out.println("직원을 찾을 수 없습니다.");
        }
    }

    public static List<Employee> getEmployeeList() {
        return employeeDao.getAllEmployees();
    }
}
