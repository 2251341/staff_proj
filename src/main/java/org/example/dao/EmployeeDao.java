package org.example.dao;

import org.example.dto.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao {
    private List<Employee> employees; // 직원 정보를 저장할 리스트

    public EmployeeDao() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee findEmployeeById(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    public Optional<Employee> findEmployeeByLoginId(String loginId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(loginId))
                .findFirst();
    }

    public boolean updateEmployee(String employeeId, String name, String department, String position) {
        Employee employee = findEmployeeById(employeeId);
        if (employee != null) {
            employee.setName(name);
            employee.setDepartment(department);
            employee.setPosition(position);
            return true;
        }
        return false;
    }

    public boolean removeEmployee(String employeeId) {
        return employees.removeIf(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}
