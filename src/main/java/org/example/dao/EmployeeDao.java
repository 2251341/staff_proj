package org.example.dao;

import org.example.Employee;
import org.example.db.DBConnection;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDao {
    private static DBConnection dbConnection;

    public EmployeeDao() {
        dbConnection = new DBConnection();
        dbConnection.connect();
    }

    // 직원 자격 증명을 검증합니다 (데이터베이스 인증 모의)
    public boolean validateEmployeeCredentials(String username, String password) {
        String sql = "SELECT * FROM employees WHERE username = '" + username + "' AND password = '" + password + "'";
        return dbConnection.selectRow(sql).size() > 0;
    }

    // 사용자 이름에 따라 직원을 검색합니다 (데이터베이스 쿼리 모의)
    public static Employee getEmployeeByUsername(String username) {
        String sql = "SELECT * FROM employees WHERE username = '" + username + "'";
        Map<String, Object> row = dbConnection.selectRow(sql);

        if (row.size() > 0) {
            String id = row.get("id").toString();
            String name = (String) row.get("name");
            String department = (String) row.get("department");
            String position = (String) row.get("position");
            // Employee 객체를 생성하고 반환합니다
            return new Employee(id, username, name, department, position);
        }
        return null; // 직원을 찾지 못한 경우 null을 반환합니다
    }

    // CRUD 작업을 위한 다른 메서드 (이 예제에서는 구현되지 않음)
}
