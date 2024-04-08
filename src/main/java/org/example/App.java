package org.example;

import org.example.Admin;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static Admin admin = new Admin();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserChoice();
            handleUserChoice(choice);
        }
    }

    private static void showMenu() {
        System.out.println("\n====== 직원 관리 프로그램 ======");
        System.out.println("1. 관리자 모드");
        System.out.println("2. 직원 모드");
        System.out.println("3. 종료");
    }

    private static int getUserChoice() {
        System.out.print("메뉴를 선택하세요: ");
        return scanner.nextInt();
    }

    private static void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                adminMode();
                break;
            case 2:
                employeeMode();
                break;
            case 3:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private static void adminMode() {
        System.out.print("관리자 아이디를 입력하세요: ");
        String adminId = scanner.next();
        System.out.print("비밀번호를 입력하세요: ");
        String adminPassword = scanner.next();

        if (admin.isAdmin(adminId, adminPassword)) {
            adminMenu();
        } else {
            System.out.println("잘못된 관리자 정보입니다.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n====== 관리자 모드 ======");
            System.out.println("1. 직원 추가");
            System.out.println("2. 직원 목록 보기");
            System.out.println("3. 출퇴근 기록 조회");
            System.out.println("4. 종료");
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    admin.addEmployee();
                    break;
                case 2:
                    admin.showEmployeeList();
                    break;
                case 3:
                    admin.viewAttendanceRecords();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void employeeMode() {
        // 직원 모드 구현
    }
}
