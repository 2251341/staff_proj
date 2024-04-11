package org.example.controller;

import org.example.Employee;
import org.example.EmployeeManagement;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    private static final String EMPLOYEE_LOGIN_MESSAGE = "직원 페이지에 오신 것을 환영합니다, %s님!";
    private static final String NOT_FOUND_MESSAGE = "해당 기록이 없습니다.";



    public static void showPage(String username) {
        System.out.println(String.format(EMPLOYEE_LOGIN_MESSAGE, username));

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 내 정보 조회");
            System.out.println("2. 기간별 출퇴근 기록 조회");
            System.out.println("3. 날짜별 출퇴근 기록 조회");
            System.out.println("4. 뒤로 가기");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    viewOwnInfo(username);
                    break;
                case 2:
                    viewOwnAttendanceByPeriod(username);
                    break;
                case 3:
                    viewOwnAttendanceByDate(username);
                    break;
                case 4:
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }

    private static void viewOwnInfo(String username) {
        Employee employee = EmployeeManagement.getEmployee(username);
        if (employee != null) {
            System.out.println("직원 정보:");
            System.out.println("이름: " + employee.getName());
            System.out.println("부서: " + employee.getDepartment());
            System.out.println("직급: " + employee.getPosition());
            // 필요한 경우 다른 정보도 추가하세요
        } else {
            System.out.println("해당 ID를 가진 직원이 존재하지 않습니다.");
        }
    }

    private static void viewOwnAttendanceByPeriod(String username) {
        Employee employee = EmployeeManagement.getEmployee(username);
        if (employee != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("조회할 기간의 시작일을 입력하세요 (yyyy-mm-dd): ");
            String startDate = scanner.nextLine();
            System.out.print("조회할 기간의 종료일을 입력하세요 (yyyy-mm-dd): ");
            String endDate = scanner.nextLine();

            List<String> attendanceRecords = employee.getAttendanceRecords();
            System.out.println("직원 " + username + "의 출퇴근 기록 (기간: " + startDate + " ~ " + endDate + "):");
            for (String record : attendanceRecords) {
                // 출퇴근 기록 중에서 본인의 것이고 시작일과 종료일 사이에 해당하는 것만 출력
                // 실제로는 날짜를 파싱하여 범위에 해당하는 것만 출력해야 함
                if (record.startsWith(username) && record.startsWith(startDate) && record.startsWith(endDate)) {
                    System.out.println(record);
                }
            }
        } else {
            System.out.println("해당 ID를 가진 직원이 존재하지 않습니다.");
        }
    }

    private static void viewOwnAttendanceByDate(String username) {
        Employee employee = EmployeeManagement.getEmployee(username);
        if (employee != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("조회하고 싶은 날짜를 입력하세요 (yyyy-mm-dd): ");
            String targetDate = scanner.nextLine();

            List<String> attendanceRecords = employee.getAttendanceRecords();
            System.out.println("직원 " + username + "의 출퇴근 기록 (날짜: " + targetDate + "):");
            for (String record : attendanceRecords) {
                // 출퇴근 기록 중에서 본인의 것이고 해당 날짜에 해당하는 것만 출력
                // 실제로는 날짜를 파싱하여 해당 날짜와 일치하는 것만 출력해야 함
                if (record.startsWith(username) && record.startsWith(targetDate)) {
                    System.out.println(record);
                }
            }
        } else {
            System.out.println("해당 ID를 가진 직원이 존재하지 않습니다.");
        }
    }

    public static boolean login(String username, String password) {
        // 직원으로 로그인하는지 확인
        if (EmployeeManagement.validateEmployeeCredentials(username, password)) {
            return true;
        }
        return false;
    }
}
