package org.example;// 패키지 org.example

import org.example.container.Container;
import java.util.ArrayList;


public class App {
    public static Admin admin;
    public static ArrayList<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public static void main(String[] args) {
        admin = new Admin();
        admin.makeTestData();

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

    static int getUserChoice() {
        System.out.print("메뉴를 선택하세요: ");
        return Container.getScanner().nextInt();
    }

    public static void handleUserChoice(int choice) {
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

    public static void adminMode() {
        System.out.print("관리자 아이디를 입력하세요: ");
        String loginId = Container.getScanner().next();
        System.out.print("비밀번호를 입력하세요: ");
        String loginPw = Container.getScanner().next();

        if (admin.isAdmin(loginId, loginPw)) {
            admin.adminMenu(attendanceRecords.toArray(new AttendanceRecord[0]));
        } else {
            System.out.println("잘못된 관리자 정보입니다.");
        }
    }

    public static void employeeMode() {
        System.out.print("직원 ID를 입력하세요: ");
        String employeeId = Container.getScanner().next();
        System.out.print("비밀번호를 입력하세요: ");
        String password = Container.getScanner().next();

        Employee employee = admin.findEmployee(employeeId);
        if (employee != null && Employee.getPassword().equals(password)) {
            employee.viewAttendanceRecords();
        } else {
            System.out.println("로그인 실패: 잘못된 ID 또는 비밀번호입니다.");
        }
    }
}
