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

    public static boolean validateEmployee(String employeeId) {
        return employeeDao.findEmployeeById(employeeId) != null;
    }

    public static void registerEmployee(String employeeId, String password, String name, String department, String position) {
        Employee existingEmployee = employeeDao.findEmployeeById(employeeId);
        if (existingEmployee == null) {
            Employee newEmployee = new Employee(employeeId, password, name, department, position);
            employeeDao.addEmployee(newEmployee);
            System.out.println("직원이 성공적으로 등록되었습니다.");
        } else {
            System.out.println("직원 ID가 이미 존재합니다.");
        }
    }

    public static boolean validateEmployeeCredentials(String employeeId, String password) {
        Employee employee = employeeDao.findEmployeeById(employeeId);
        return employee != null && employee.getPassword().equals(password);
    }

    public static Employee getEmployee(String employeeId) {
        return employeeDao.findEmployeeById(employeeId);
    }

    public static boolean removeEmployee(String employeeId) {
        boolean removed = employeeDao.removeEmployee(employeeId);
        if (removed) {
            System.out.println("직원이 삭제되었습니다.");
        } else {
            System.out.println("직원을 찾을 수 없습니다.");
        }
        return removed;
    }

    public static List<Employee> getEmployeeList() {
        return employeeDao.getAllEmployees();
    }
}
