package org.example;

import org.example.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static org.example.App.scanner;

public class Admin {
    private ArrayList<Employee> employeeList = new ArrayList<>();

    public void addEmployee() {
        System.out.println("\n====== 직원 추가 ======");
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("직급: ");
        String position = scanner.nextLine();
        System.out.print("아이디: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        Employee newEmployee = new Employee(name, position, id, password);
        employeeList.add(newEmployee);
        System.out.println("직원이 추가되었습니다.");
    }

    public void showEmployeeList() {
        System.out.println("\n====== 직원 목록 보기 ======");
        if (employeeList.isEmpty()) {
            System.out.println("등록된 직원이 없습니다.");
        } else {
            for (Employee employee : employeeList) {
                System.out.println("이름: " + employee.getName() + ", 직급: " + employee.getPosition() +
                        ", 아이디: " + employee.getId());
            }
        }
    }
    public void deleteEmployee() {
        System.out.println("\n====== 직원 삭제 ======");
        if (employeeList.isEmpty()) {
            System.out.println("등록된 직원이 없습니다.");
        } else {
            System.out.print("삭제할 직원의 아이디를 입력하세요: ");
            String id = scanner.nextLine();
            boolean found = false;
            for (Employee employee : employeeList) {
                if (employee.getId().equals(id)) {
                    employeeList.remove(employee);
                    System.out.println("직원이 삭제되었습니다.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("해당 아이디의 직원을 찾을 수 없습니다.");
            }
        }
    }

    public void viewAttendanceRecords() {
        Scanner scanner = new Scanner(System.in);

        // 직원별 조회, 날짜별 조회, 기간별 조회 중 선택
        System.out.println("1. 직원별 조회");
        System.out.println("2. 날짜별 조회");
        System.out.println("3. 기간별 조회");
        System.out.print("원하는 조회 방법을 선택하세요: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 제거

        switch (choice) {
            case 1:
                viewAttendanceRecordsByEmployee();
                break;
            case 2:
                viewAttendanceRecordsByDate();
                break;
            case 3:
                viewAttendanceRecordsByPeriod();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void viewAttendanceRecordsByEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("조회할 직원의 ID를 입력하세요: ");
        String employeeId = scanner.nextLine();

        for (Employee employee : employeeList) {
            if (employee.getId().equals(employeeId)) {
                System.out.println(employee.getName() + "의 출퇴근 기록:");
                ArrayList<AttendanceRecord> attendanceRecords = employee.getAttendanceRecords();
                for (AttendanceRecord record : attendanceRecords) {
                    System.out.println(record);
                }
                return;
            }
        }
        System.out.println("해당 ID의 직원을 찾을 수 없습니다.");
    }

    private void viewAttendanceRecordsByDate() {
        System.out.print("조회할 날짜를 입력하세요 (YYYY-MM-DD): ");
        Scanner scanner = new Scanner(System.in);
        String dateString = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(dateString);
            for (AttendanceRecord record : attendanceRecords) {
                if (record.getDate().equals(date)) {
                    System.out.println("날짜: " + dateString + ", 출근: " +
                            (record.isAttendanceIn() ? "O" : "X"));
                }
            }
        } catch (Exception e) {
            System.out.println("날짜 형식이 잘못되었습니다.");
        }
    }

    private void viewAttendanceRecordsByPeriod() {
        System.out.print("조회할 시작 날짜를 입력하세요 (YYYY-MM-DD): ");
        Scanner scanner = new Scanner(System.in);
        String startDateString = scanner.nextLine();
        System.out.print("조회할 종료 날짜를 입력하세요 (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);
            for (AttendanceRecord record : attendanceRecords) {
                Date recordDate = record.getDate();
                if (!recordDate.before(startDate) && !recordDate.after(endDate)) {
                    System.out.println("날짜: " + dateFormat.format(recordDate) + ", 출근: " +
                            (record.isAttendanceIn() ? "O" : "X"));
                }
            }
        } catch (Exception e) {
            System.out.println("날짜 형식이 잘못되었습니다.");
        }
    }



    public boolean isAdmin(String adminId, String adminPassword) {
        // 관리자 여부 확인하는 로직
        return adminId.equals("admin") && adminPassword.equals("adminpassword");
    }
}