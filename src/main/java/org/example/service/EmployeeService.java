package org.example.service;

import org.example.dao.EmployeeDao;
import org.example.dto.Employee;

import java.util.List;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public EmployeeService() {
        employeeDao = new EmployeeDao();
    }

    public void registerEmployee(Employee employee) {
        employeeDao.register(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    public void deleteEmployee(String id) {
        employeeDao.delete(id);
    }

    public Employee getEmployeeById(String id) {
        return employeeDao.getById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    public int getLastEmployeeId() {
        return employeeDao.getLastEmployeeId();
    }
}
