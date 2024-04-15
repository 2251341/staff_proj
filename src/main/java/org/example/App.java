package org.example;

import org.example.container.Container;
import org.example.controller.AdminController;
import org.example.controller.EmployeeController;
import org.example.db.DBConnection;
import org.example.service.EmployeeService;

import java.util.InputMismatchException;
import java.util.Scanner;


import static org.example.controller.EmployeeController.EMPLOYEE_LOGIN_MESSAGE;

public class App {
    // 생성자
    private static Scanner scanner = new Scanner(System.in);
    private static EmployeeController employeeController;
    private static AdminController adminController;
    public EmployeeService employeeService;

    public App() {
        DBConnection.DB_NAME = "emp_proj";
        DBConnection.DB_USER = "sbsst";
        DBConnection.DB_PASSWORD = "sbs123414";
        DBConnection.DB_PORT = 3306;

        Container.getDBConnection().connect();
    }




    public void start() {
        employeeService = new EmployeeService(); // Assume EmployeeService is properly configured
        employeeController = new EmployeeController();
        adminController = new AdminController();
        EmployeeService employeeService = new EmployeeService();

        showLoginPage();
    }

    public static void showLoginPage() {
        System.out.println("직원 관리 시스템에 오신 것을 환영합니다.");



        while (true) {
            System.out.println("로그인 유형을 선택하세요:");
            System.out.println("1. 관리자");
            System.out.println("2. 직원");
            System.out.println("3. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 소비

            switch (choice) {
                case 1:
                    loginProcess("관리자");
                    break;
                case 2:
                    loginProcess("직원");
                    break;
                case 3:
                    System.out.println("시스템을 종료합니다...");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

    private static void loginProcess(String userType) {
        System.out.print(userType + " 사용자 이름을 입력하세요: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        if (userType.equals("관리자")) {
            if (AdminController.login(username, password)) {
                System.out.println(userType + " 로그인 성공.");
                showAdminPage(username);
            } else {
                System.out.println(userType + " ID가 아닙니다. 다시 시도하세요.");
            }
        } else if (userType.equals("직원")) {
            if (employeeController.login(username, password)) {
                System.out.println(userType + " 로그인 성공.");
                showEmployeePage(username);
            } else {
                System.out.println(userType + " ID가 아닙니다. 다시 시도하세요.");
            }
        }
    }
    public static void showEmployeePage(String username) {
        int choice;
        do {
            System.out.printf((EMPLOYEE_LOGIN_MESSAGE) + "%n", username);
            System.out.println("1. 내 정보 조회");
            System.out.println("2. 기간별 출퇴근 기록 조회");
            System.out.println("3. 날짜별 출퇴근 기록 조회");
            System.out.println("4. 로그아웃"); // 로그아웃 옵션 추가
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    EmployeeController.viewOwnInfo(username);
                    break;
                case 2:
                    EmployeeController.viewOwnAttendanceByPeriod(username);
                    break;
                case 3:
                    EmployeeController.viewOwnAttendanceByDate(username);
                    break;
                case 4:
                    EmployeeController.logout(); // 로그아웃 메서드 호출
                    return; // 메서드 종료 및 로그인 페이지로 돌아가기
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }
    public static void showAdminPage(String username) {

        boolean logout = false;

        while (!logout) {
            try {
                System.out.println("관리자 페이지에 오신 것을 환영합니다, " + username + "님!");
                System.out.println("1. 직원 관리");
                System.out.println("2. 출퇴근 기록 조회");
                System.out.println("3. 출퇴근 기록하기");
                System.out.println("4. 로그아웃");
                System.out.print("선택: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        AdminController.manageEmployees();
                        break;
                    case 2:
                        AdminController.viewAttendanceRecords(EmployeeService.attendanceRecords);
                        break;
                    case 3:
                        System.out.println("기록할 직원 ID를 입력하세요:");
                        String employeeId = scanner.nextLine();
                        if (EmployeeService.validateEmployee(employeeId)) {
                            System.out.println("출퇴근 유형을 입력하세요 (예: 출근, 퇴근):");
                            String attendanceType = scanner.nextLine();
                            AdminController.recordEmployeeAttendance(employeeId, attendanceType);
                        } else {
                            System.out.println("유효하지 않은 직원 ID입니다.");
                        }
                        break;
                    case 4:
                        System.out.println("로그아웃합니다.");
                        logout = true;
                        break;
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요.");
                }
            } catch (InputMismatchException ime) {
                System.out.println("올바르지 않은 입력입니다. 숫자를 입력해 주세요.");
                scanner.nextLine();
            }
        }

    }


}
