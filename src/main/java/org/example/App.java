package org.example;

import org.example.controller.AdminController;
import org.example.controller.EmployeeController;

import java.util.Scanner;

public class App {
    // 생성자
    private EmployeeController employeeController;
    private AdminController adminController;
    public App() {
        employeeController = new EmployeeController();
        adminController = new AdminController();
    }

    public void start() {

        System.out.println("== 직원 관리 프로그램 ==");
        showLoginPage();
    }

    // 로그인 페이지 표시 및 로그인 시도 메서드
    private void showLoginPage() {
        Scanner scanner = new Scanner(System.in);

        boolean loginSuccess = false;
        do {
            System.out.print("사용자 ID를 입력하세요: ");
            String id = scanner.nextLine(); // Accept id for employee login

            // Validate username (id)
            if (AdminController.validateUsername(id)) {
                System.out.print("비밀번호를 입력하세요: ");
                String password = scanner.nextLine();

                // Admin login
                if (AdminController.login(id, password) && AdminController.isAdmin(id)) {
                    loginSuccess = true;
                    System.out.println("관리자 로그인 성공!");
                    AdminController.showPage(id);
                }
                // Employee login
                else if (EmployeeManagement.validateEmployeeCredentials(id, password)) {
                    loginSuccess = true;
                    System.out.println("직원 로그인 성공!");
                    showPage(id);
                } else {
                    System.out.println("로그인 실패. 잘못된 사용자 이름 또는 비밀번호입니다. 다시 시도하세요.");
                }
            } else {
                System.out.println("잘못된 사용자 ID입니다. 다시 시도하세요.");
            }
        } while (!loginSuccess);

        scanner.close();
    }
    public static void showPage(String username) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println(String.format(EMPLOYEE_LOGIN_MESSAGE, username));
            System.out.println("1. 내 정보 조회");
            System.out.println("2. 기간별 출퇴근 기록 조회");
            System.out.println("3. 날짜별 출퇴근 기록 조회");
            System.out.println("4. 로그아웃"); // 로그아웃 옵션 추가
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
                    logout(); // 로그아웃 메서드 호출
                    return; // 메서드 종료 및 로그인 페이지로 돌아가기
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }
}
