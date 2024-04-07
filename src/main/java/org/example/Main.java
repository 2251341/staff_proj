package org.example;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static AttendanceManager attendanceManager = new AttendanceManager();
    private static Employee loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            if (loggedInUser == null) {
                login();
            } else {
                if (loggedInUser.getRole().equals("admin")) {
                    adminMenu();
                } else {
                    employeeMenu();
                }
            }
        }
    }

    private static void login() {
        System.out.print("사용자 이름: ");
        String username = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();

        for (Employee employee : employees) {
            if (employee.getName().equals(username) && employee.getPassword().equals(password)) {
                loggedInUser = employee;
                System.out.println("로그인 성공!");
                return;
            }
        }

        System.out.println("사용자 이름 또는 비밀번호가 잘못되었습니다.");
    }

    private static void adminMenu() {
        System.out.println("\n===== 관리자 메뉴 =====");
        System.out.println("1. 직원 등록");
        System.out.println("2. 직원 삭제");
        System.out.println("3. 직원 출퇴근 로그 조회");
        System.out.println("4. 로그아웃");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                registerEmployee();
                break;
            case 2:
                deleteEmployee();
                break;
            case 3:
                showAttendanceLog();
                break;
            case 4:
                loggedInUser = null;
                System.out.println("로그아웃 되었습니다.");
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private static void registerEmployee() {
        System.out.print("직원 이름: ");
        String name = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();
        String role = "employee"; // 관리자가 직접 등록하므로 권한은 "직원"으로 고정
        employees.add(new Employee(name, password, role));
        System.out.println(name + "님이 등록되었습니다.");
    }

    private static void deleteEmployee() {
        System.out.print("삭제할 직원 이름: ");
        String name = scanner.next();
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                employees.remove(employee);
                System.out.println(name + "님이 삭제되었습니다.");
                return;
            }
        }
        System.out.println("해당 직원을 찾을 수 없습니다.");
    }

    private static void showAttendanceLog() {
        System.out.print("출퇴근 기록을 조회할 직원 이름: ");
        String name = scanner.next();
        attendanceManager.showAttendanceLog(name);
    }

    private static void employeeMenu() {
        System.out.println("\n===== 직원 메뉴 =====");
        System.out.println("1. 출근 기록");
        System.out.println("2. 퇴근 기록");
        System.out.println("3. 본인 출퇴근 기록 조회");
        System.out.println("4. 로그아웃");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                clockIn();
                break;
            case 2:
                clockOut();
                break;
            case 3:
                showOwnAttendanceLog();
                break;
            case 4:
                loggedInUser = null;
                System.out.println("로그아웃 되었습니다.");
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private static void clockIn() {
        String currentTime = getCurrentTime();
        attendanceManager.clockIn(loggedInUser.getName(), currentTime);
    }

    private static void clockOut() {
        String currentTime = getCurrentTime();
        attendanceManager.clockOut(loggedInUser.getName(), currentTime);
    }

    private static void showOwnAttendanceLog() {
        attendanceManager.showAttendanceLog(loggedInUser.getName());
    }

    private static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }
}