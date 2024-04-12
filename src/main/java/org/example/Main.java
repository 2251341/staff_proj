package org.example;

import org.example.controller.AdminController;
import org.example.controller.EmployeeController;
import org.example.dao.EmployeeDao;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 관리자 정보 초기화
        AdminController.initializeAdminCredentials();
        EmployeeManagement.initializeEmployeeCredentials();
        // 로그인 페이지 표시
        showLoginPage();
    }

    // 로그인 페이지 표시 및 로그인 시도 메서드
    public static void showLoginPage() {
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
                    // 로그인 성공 시, 직원 페이지 표시
                    System.out.println("직원 로그인 성공!");
                    EmployeeController.showPage(id);
                } else {
                    System.out.println("로그인 실패. 잘못된 사용자 이름 또는 비밀번호입니다. 다시 시도하세요.");
                }
            } else {
                System.out.println("잘못된 사용자 ID입니다. 다시 시도하세요.");
            }
        } while (!loginSuccess);

        scanner.close();
    }


}
