package org.example.dao;

import org.example.dto.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao {
    private List<Employee> employees; // 직원 정보를 저장할 리스트

    // 생성자: 직원 리스트를 초기화합니다.
    public EmployeeDao() {
        employees = new ArrayList<>();
    }

    // 직원 추가
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // 직원 ID로 검색
    public Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    // 로그인 ID로 직원 검색
    public Optional<Employee> findEmployeeByLoginId(String loginId) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(loginId))
                .findFirst();
    }

    // 직원 정보 수정
    public boolean updateEmployee(String id, String name, String department, String position) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setName(name);
            employee.setDepartment(department);
            employee.setPosition(position);
            return true;
        }
        return false;
    }

    // 직원 삭제
    public boolean removeEmployee(String id) {
        return employees.removeIf(employee -> employee.getId().equals(id));
    }

    // 전체 직원 목록 반환
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}
