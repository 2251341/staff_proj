package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager empManager = new EmployeeManager();
        AttendanceManager attManager = new AttendanceManager();

        while (true) {
            System.out.println("\n====== 직원 관리 프로그램 ======");
            System.out.println("1. 직원 추가");
            System.out.println("2. 직원 목록 보기");
            System.out.println("3. 출근 기록");
            System.out.println("4. 퇴근 기록");
            System.out.println("5. 출퇴근 기록 조회");
            System.out.println("6. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("이름: ");
                    String name = scanner.next();
                    System.out.print("나이: ");
                    int age = scanner.nextInt();
                    System.out.print("직책: ");
                    String position = scanner.next();
                    System.out.print("연봉: ");
                    double salary = scanner.nextDouble();
                    Employee employee = new Employee(name, age, position, salary);
                    empManager.addEmployee(employee);
                    break;
                case 2:
                    empManager.listEmployees();
                    break;
                case 3:
                    System.out.print("출근할 직원 이름: ");
                    String inName = scanner.next();
                    System.out.print("출근 시간: ");
                    String inTime = scanner.next();
                    attManager.clockIn(inName, inTime);
                    break;
                case 4:
                    System.out.print("퇴근할 직원 이름: ");
                    String outName = scanner.next();
                    System.out.print("퇴근 시간: ");
                    String outTime = scanner.next();
                    attManager.clockOut(outName, outTime);
                    break;
                case 5:
                    System.out.print("조회할 직원 이름: ");
                    String searchName = scanner.next();
                    attManager.showAttendanceLog(searchName);
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
    }
}