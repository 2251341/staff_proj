package org.example.dao;

import org.example.dto.Employee;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.container.Container.dbConnection;

public class EmployeeDao {
    private List<Employee> employees; // 직원 정보를 저장할 리스트

    public EmployeeDao() {
        employees = new ArrayList<>();
    }
    // 직원 객체를 리스트에 추가하는 메서드
    public int addEmployee(Employee employee) {
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO employee SET ");
        sb.append("regDate = NOW(), ");
        sb.append("dateTimeKey = NOW(), ");
        sb.append(String.format("employeeId = '%s', ", employee.getEmployeeId()));
        sb.append(String.format("`password` = '%s', ", employee.getPassword()));
        sb.append(String.format("`name` = '%s', ", employee.getName()));
        sb.append(String.format("`department` = '%s', ", employee.getDepartment()));
        sb.append(String.format("`position` = '%s' ", employee.getPosition()));

        return dbConnection.insert(sb.toString());
    }


//    public void addEmployee(Employee employee) {
//        employees.add(employee);
//    }

    // 주어진 employeeId를 가진 직원을 찾아 반환하는 메서드
    public Employee findEmployeeById(String employeeId) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `employee` "));
        sb.append(String.format("WHERE employeeId = '%s' ", employeeId));

        Map<String, Object> row = dbConnection.selectRow((sb.toString()));
        if ( row.isEmpty() ) {
            return null;
        }

        return new Employee(row);
    }

//    public Employee findEmployeeById(String employeeId) {
//        for (Employee employee : employees) {
//            if (employee.getEmployeeId().equals(employeeId)) {
//                return employee;
//            }
//        }
//        return null;
//    }


    // 직원의 정보를 업데이트하는 메서드
    public void updateEmployee(String employeeId, String name, String department, String position) {
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE employee SET ");
        sb.append(String.format("name = '%s', ", name));
        sb.append(String.format("department = '%s', ", department));
        sb.append(String.format("position = '%s' ", position));
        sb.append(String.format("WHERE employeeId = '%s';", employeeId));

        dbConnection.update(sb.toString());
    }

//    public void updateEmployee(String employeeId, String name, String department, String position) {
//        Employee employee = findEmployeeById(employeeId);
//        if (employee != null) {
//            employee.setName(name);
//            employee.setDepartment(department); 직원 정보 수정 안됨
//            employee.setPosition(position);
//        }
//    }

    // 주어진 ID의 직원을 리스트에서 제거하는 메서드
    public int removeEmployee(String employeeId) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("DELETE FROM employee "));
        sb.append(String.format("WHERE employeeId = '%s';", employeeId));

        return dbConnection.delete(sb.toString());
    }
    // 모든 직원을 포함하는 새 리스트를 반환하는 메서드
    public List<Employee> getAllEmployees() {
        StringBuilder sb = new StringBuilder();

        // 모든 직원 상세 정보를 가져오기 위한 SQL 쿼리 구성
        sb.append("SELECT employeeId AS 'ID', `name` AS '이름', `department` AS '부서', `position` AS '직급' FROM employee;");

        // 쿼리 실행 및 결과 가져오기
        List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

        // 결과 세트를 반복하여 Employee 객체 생성
        for (Map<String, Object> row : rows) {
            if (!row.isEmpty()) {
                Employee employee = new Employee(row); // Employee 클래스가 Map을 사용하여 인스턴스를 초기화하는 생성자를 가지고 있다고 가정
                employees.add(employee);
            }
        }

        return employees;
    }
}


//    public List<Employee> getAllEmployees() {
//        return new ArrayList<>(employees);
//    }

//public List<Employee> getAllEmployees() {
//    StringBuilder sb = new StringBuilder();
//
//    // 모든 직원 상세 정보를 가져오기 위한 SQL 쿼리 구성
//    sb.append("SELECT employeeId, ");
//    sb.append("`name`, ");
//    sb.append("`department`, ");
//    sb.append("`name`, ");
//    sb.append("FROM employee; ");
//
//    // 쿼리 실행 및 결과 가져오기
//    List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());
//
//    // 결과 세트를 반복하여 Employee 객체 생성
//    for (Map<String, Object> row : rows) {
//        if (!row.isEmpty()) {
//            Employee employee = new Employee(row); // Employee 클래스가 Map을 사용하여 인스턴스를 초기화하는 생성자를 가지고 있다고 가정
//            employees.add(employee);
//        }
//    }