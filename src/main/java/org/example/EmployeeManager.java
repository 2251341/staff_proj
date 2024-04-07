package org.example;


import java.util.ArrayList;

public class EmployeeManager {
    private ArrayList<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println(employee.getName() + "이(가) 추가되었습니다.");
    }

    public void listEmployees() {
        System.out.println("직원 목록:");
        for (Employee employee : employees) {
            System.out.println("이름: " + employee.getName() + ", 나이: " + employee.getAge() + ", 직책: " + employee.getPosition() + ", 연봉: " + employee.getSalary());
        }
    }
}
