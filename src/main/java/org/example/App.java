package org.example;

import org.example.controller.AdminController;
import org.example.controller.EmployeeController;
import org.example.service.EmployeeService;

import java.util.Scanner;

public class App {
    // 생성자
    private static Scanner scanner = new Scanner(System.in);
    private static EmployeeController employeeController;
    private static AdminController adminController;
    private static EmployeeService employeeService;

    public void start() {
        employeeService = new EmployeeService(); // Assume EmployeeService is properly configured
        employeeController = new EmployeeController();
        adminController = new AdminController();

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
                AdminController.showPage(username);
            } else {
                System.out.println(userType + " 로그인 실패.");
            }
        } else if (userType.equals("직원")) {
            if (employeeController.login(username, password)) {
                System.out.println(userType + " 로그인 성공.");
                employeeController.showEmployeePage(username);
            } else {
                System.out.println(userType + " 로그인 실패.");
            }
        }
    }


}
