package org.example;

import org.example.container.Container;
import org.example.controller.AdminController;
import org.example.controller.EmployeeController;
import org.example.controller.Session;
import org.example.db.DBConnection;
import org.example.service.EmployeeService;

import java.util.InputMismatchException;
import java.util.Scanner;


import static org.example.controller.EmployeeController.EMPLOYEE_LOGIN_MESSAGE;

public class App {
    // 생성자
    private static final Scanner scanner = Container.getScanner();
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

        boolean loginSuccess = false;
        if (userType.equals("관리자")) {
            loginSuccess = AdminController.login(username, password);
        } else if (userType.equals("직원")) {
            loginSuccess = employeeController.login(username, password);
        }

        if (loginSuccess) {
            System.out.println(userType + " 로그인 성공.");
            Session.setCurrentUser(username, userType);
            if (userType.equals("관리자")) {
                AdminController.showAdminPage(username);
            } else {
                EmployeeController.showEmployeePage(username);
            }
        } else {
            System.out.println(userType + " ID가 아닙니다. 다시 시도하세요.");
        }
    }
}
