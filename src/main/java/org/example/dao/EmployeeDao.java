package org.example.dao;

import org.example.dto.Employee;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String query = "SELECT * FROM employee WHERE employeeId = ?";
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getString("employeeId"),
                        rs.getString("password"),  // 비밀번호도 필요하다고 가정
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("position")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employee: " + e.getMessage());
        }
        return null;
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
    public int updateEmployee(String employeeId, String name, String department, String position) {
        String sql = "UPDATE employee SET name = ?, department = ?, position = ? WHERE employeeId = ?;";

        // `update` 메서드를 호출하면서 파라미터를 전달
        int result = dbConnection.update(sql, name, department, position, employeeId);
        if (result > 0) {
            System.out.println("직원 정보가 성공적으로 업데이트 되었습니다.");
        } else {
            System.out.println("업데이트 실패: 해당 직원 정보가 존재하지 않습니다.");
        }
        return result;
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
        sb.append("SELECT employeeId AS 'ID', `name` AS 'name', `department` AS 'department', `position` AS 'position' FROM employee;");

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
    public static boolean hasCheckedInToday(String employeeId) {
        String sql = "SELECT * FROM attendance WHERE employeeId = ? AND DATE(checkIn) = CURDATE();";
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("오류 발생: " + e.getMessage());
            return false;
        }
    }

    public static boolean recordCheckOut(String employeeId) {
        String sql = "UPDATE attendance SET checkOut = NOW() " +
                "WHERE employeeId = ? AND DATE(checkIn) = CURDATE() AND checkOut IS NULL;";
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("퇴근 기록 시 SQL 예외 발생: " + e.getMessage());
            return false;
        }
    }
    public static boolean recordCheckIn(String employeeId) {
        String sql = "INSERT INTO attendance (employeeId, checkIn) " +
                "SELECT ?, NOW() " +
                "FROM DUAL " +
                "WHERE NOT EXISTS (SELECT * FROM attendance WHERE employeeId = ? AND DATE(checkIn) = CURDATE() AND checkOut IS NULL);";
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("출근 기록 시 SQL 예외 발생: " + e.getMessage());
            return false;
        }
    }

    //직원별
    public List<String> getAttendanceRecordsByEmployee(String employeeId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT checkIn, checkOut FROM attendance WHERE employeeId = ?");
        List<String> attendanceRecords = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.prepareStatement(sb.toString())) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String checkIn = rs.getString("checkIn");
                String checkOut = rs.getString("checkOut");
                attendanceRecords.add("출근: " + checkIn + ", 퇴근: " + checkOut);
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
        }
        return attendanceRecords;
    }

    //날짜별
    public List<String> getAttendanceRecordsByDate(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT employeeId, checkIn, checkOut FROM attendance WHERE DATE(checkIn) = ?");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> attendanceRecords = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.prepareStatement(sb.toString())) {
            stmt.setString(1, date.format(formatter));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String employeeId = rs.getString("employeeId");
                String checkIn = rs.getString("checkIn");
                String checkOut = rs.getString("checkOut");
                attendanceRecords.add("ID: " + employeeId + ", 출근: " + checkIn + ", 퇴근: " + checkOut);
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
        }
        return attendanceRecords;
    }

    //기간별
    public List<String> getAttendanceRecordsByPeriod(LocalDate startDate, LocalDate endDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT employeeId, checkIn, checkOut FROM attendance WHERE DATE(checkIn) BETWEEN ? AND ?");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> attendanceRecords = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.prepareStatement(sb.toString())) {
            stmt.setString(1, startDate.format(formatter));
            stmt.setString(2, endDate.format(formatter));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String employeeId = rs.getString("employeeId");
                String checkIn = rs.getString("checkIn");
                String checkOut = rs.getString("checkOut");
                attendanceRecords.add(" ID: " + employeeId + ", 출근: " + checkIn + ", 퇴근: " + checkOut);
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
        }
        return attendanceRecords;
    }


    public static List<String> getAttendanceRecordsByDateForEmployee(String employeeId, LocalDate date) {
        String sql = "SELECT checkIn, checkOut FROM attendance WHERE employeeId = ? AND DATE(checkIn) = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> records = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, date.format(formatter));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                records.add("출근 : " + rs.getString("checkIn") + ", 퇴근 : " + rs.getString("checkOut"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
        }
        return records;
    }

    public static List<String> getAttendanceRecordsByPeriodForEmployee(String employeeId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT checkIn, checkOut FROM attendance WHERE employeeId = ? AND DATE(checkIn) BETWEEN ? AND ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> records = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, startDate.format(formatter));
            stmt.setString(3, endDate.format(formatter));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                records.add("출근 : " + rs.getString("checkIn") + ", 퇴근 : " + rs.getString("checkOut"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
        }
        return records;
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