package org.example.dao;

import org.example.dto.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDao {
    private Map<String, Employee> employees;
    private int lastEmployeeId;

    public EmployeeDao() {
        this.employees = new HashMap<>();
        this.lastEmployeeId = 0;
    }

    public void register(Employee employee) {
        lastEmployeeId++;
        employee.setId(String.valueOf(lastEmployeeId));
        employees.put(employee.getId(), employee);
    }

    public Employee getById(String id) {
        return employees.get(id);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }

    public void update(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public void delete(String id) {
        employees.remove(id);
    }

    public int getLastEmployeeId() {
        return lastEmployeeId;
    }
}
