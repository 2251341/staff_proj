package org.example.controller;

import org.example.container.Container;
import org.example.dto.Employee;
import org.example.service.EmployeeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    public static final String EMPLOYEE_LOGIN_MESSAGE = "직원 페이지에 오신 것을 환영합니다, %s님!";
    private static final String NOT_FOUND_MESSAGE = "해당 기록이 없습니다.";

    public static void showEmployeePage(String employeeId) {
        Scanner scanner = Container.getScanner();
        int choice;
        do {
            System.out.printf(EMPLOYEE_LOGIN_MESSAGE + "%n", employeeId);
            System.out.println("1. 내 정보 조회");
            System.out.println("2. 기간별 출퇴근 기록 조회");
            System.out.println("3. 날짜별 출퇴근 기록 조회");
            System.out.println("4. 로그아웃");
            System.out.print("선택: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // 버퍼 클리어

                switch (choice) {
                    case 1:
                        viewOwnInfo(employeeId);
                        break;
                    case 2:
                        viewAttendanceByPeriod(employeeId);
                        break;
                    case 3:
                        viewAttendanceByDate(employeeId);
                        break;
                    case 4:
                        logout();
                        break;
                    default:
                        System.out.println("잘못된 선택입니다. 1부터 4까지의 숫자를 입력하세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                scanner.next();  // 잘못된 입력 제거
                choice = 0;  // 무효한 선택으로 초기화하여 루프 계속 유지
            }
        } while (choice != 4);
    }


    private static void logout() {
        System.out.println("로그아웃 되었습니다.");
    }

    private static void viewOwnInfo(String employeeId) {
        Employee employee = EmployeeService.getEmployee(employeeId);
        if (employee != null) {
            System.out.println("직원 정보:");
            System.out.println("이름: " + employee.getName());
            System.out.println("부서: " + employee.getDepartment());
            System.out.println("직급: " + employee.getPosition());
        } else {
            System.out.println("해당 ID의 직원이 존재하지 않습니다.");
        }
    }

    private static void viewAttendanceByDate(String employeeId) {
        LocalDate date = askForDate("조회하고 싶은 날짜를 입력하세요 (yyyy-MM-dd): ");
        List<String> records = EmployeeService.getEmployeeAttendanceByDate(employeeId, date);
        printAttendanceRecords(records, "날짜");
    }

    private static void viewAttendanceByPeriod(String employeeId) {
        LocalDate startDate = askForDate("조회할 기간의 시작일을 입력하세요 (yyyy-MM-dd): ");
        LocalDate endDate = askForDate("조회할 기간의 종료일을 입력하세요 (yyyy-MM-dd): ");
        List<String> records = EmployeeService.getEmployeeAttendanceByPeriod(employeeId, startDate, endDate);
        printAttendanceRecords(records, "기간");
    }

    private static LocalDate askForDate(String prompt) {
        Scanner scanner = Container.getScanner();
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine();
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요 (yyyy-MM-dd).");
            }
        }
        return date;
    }


    private static void printAttendanceRecords(List<String> records, String type) {
        if (records.isEmpty()) {
            System.out.println(NOT_FOUND_MESSAGE);
        } else {
            System.out.println("출퇴근 기록 (" + type + "):");
            records.forEach(System.out::println);
        }
    }


public boolean login(String username, String password) {
        // 직원으로 로그인하는지 확인
        if (EmployeeService.validateEmployeeCredentials(username, password)) {
            return true;
        }
        return false;
    }
}