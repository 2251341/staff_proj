package org.example;

import org.example.container.Container;
import org.example.controller.MemberController;

import java.util.Scanner;

public class App {
    private MemberController memberController;

    public App(MemberController memberController) {
        this.memberController = memberController;
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. 직원 관리");
            System.out.println("2. 출퇴근 기록 조회");
            System.out.println("3. 로그아웃");
            System.out.println("4. 종료");
            System.out.print("메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행문자 제거

            switch (choice) {
                case 1:
                    memberController.manageEmployees();
                    break;
                case 2:
                    memberController.viewAttendance();
                    break;
                case 3:
                    memberController.doAction("member", "logout");
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }
    private boolean isAdmin() {
        return Container.getSession().getLoginedMember().getLoginId().equals("admin");
    }

    public void manageEmployees() {
        if (isAdmin()) {
            System.out.println("직원 관리 기능을 실행합니다.");
            System.out.println("1. 새 직원 등록");
            System.out.println("2. 직원 정보 수정");
            System.out.println("3. 직원 삭제");
            System.out.println("4. 뒤로 가기");
            System.out.print("메뉴를 선택하세요: ");
            int choice = Container.getScanner().nextInt();
            Container.getScanner().nextLine(); // 개행 문자 제거

            switch (choice) {
                case 1:
                    memberController.doAction("member", "join");
                    break;
                case 2:
                    // 직원 정보 수정 기능 호출
                    break;
                case 3:
                    // 직원 삭제 기능 호출
                    break;
                case 4:
                    System.out.println("뒤로 가기");
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("권한이 없습니다.");
        }
    }

    public void viewAttendance() {
        System.out.println("출퇴근 기록 조회 기능을 실행합니다.");
        System.out.println("1. 날짜별 조회");
        System.out.println("2. 기간별 조회");
        System.out.println("3. 뒤로 가기");
        System.out.print("원하는 조회 방법을 선택하세요: ");
        int choice = Container.getScanner().nextInt();
        Container.getScanner().nextLine(); // 개행 문자 제거

        switch (choice) {
            case 1:
                // 날짜별 출퇴근 기록 조회 기능 호출
                break;
            case 2:
                // 기간별 출퇴근 기록 조회 기능 호출
                break;
            case 3:
                System.out.println("뒤로 가기");
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }
}