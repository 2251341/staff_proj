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
    private static EmployeeDao employeeDao = new EmployeeDao();

    public static void addEmployee(Employee employee) {
        if (employeeDao.findEmployeeById(employee.getEmployeeId()) == null) {
            employeeDao.addEmployee(employee);
            System.out.println("직원이 성공적으로 추가되었습니다.");
        } else {
            System.out.println("직원 ID가 이미 존재합니다.");
        }
    }

    public static Employee getEmployee(String employeeId) {
        return employeeDao.findEmployeeById(employeeId);
    }

    public static void removeEmployee(String employeeId) {
        employeeDao.removeEmployee(employeeId);
    }

    public static void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee.getEmployeeId(), employee.getName(), employee.getDepartment(), employee.getPosition());
    }

    public static List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }
    public static boolean validateEmployee(String employeeId) {
        return employeeDao.findEmployeeById(employeeId) != null;
    }
    public static boolean validateEmployeeCredentials(String employeeId, String password) {
        Employee employee = employeeDao.findEmployeeById(employeeId);
        return employee != null && employee.getPassword().equals(password);
    }
}