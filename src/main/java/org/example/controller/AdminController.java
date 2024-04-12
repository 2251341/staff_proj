package org.example.controller;

import org.example.EmployeeManagement;
import org.example.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AdminController {
    private static String ADMIN_USERNAME = "admin";
    private static String ADMIN_PASSWORD = "admin";
    private static Set<String> adminUsernames = new HashSet<>();

    public static void initializeAdminCredentials() {
        // 기본 관리자 ID와 비밀번호 설정
        String adminId = "admin";
        String adminPassword = "admin";
        // 관리자 정보를 설정합니다.
        AdminController.setAdminCredentials(adminId, adminPassword);
    }
    public static boolean isAdmin(String username) {
        return adminUsernames.contains(username);
    }

    private static void setAdminCredentials(String adminId, String adminPassword) {
        ADMIN_USERNAME = adminId;
        ADMIN_PASSWORD = adminPassword;
        adminUsernames.add(adminId); // Adding admin username to the set
    }


    public static boolean validateUsername(String username) {
        return ADMIN_USERNAME.equals(username) || EmployeeManagement.validateEmployee(username);
    }

    public static boolean login(String username, String password) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            return true;
        } else {
            return EmployeeManagement.validateEmployeeCredentials(username, password);
        }
    }

    public static void showPage(String username) {
        Scanner scanner = new Scanner(System.in);

        boolean logout = false;
        do {
            System.out.println("관리자 페이지에 오신 것을 환영합니다, " + username + "님!");
            System.out.println("1. 직원 관리");
            System.out.println("2. 출퇴근 기록 조회");
            System.out.println("3. 출퇴근 기록하기"); // 출퇴근 기록 옵션 추가
            System.out.println("4. 로그아웃");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    manageEmployees();
                    break;
                case 2:
                    viewAttendanceRecords(EmployeeManagement.attendanceRecords); // 가정: 이 메서드가 존재한다
                    break;
                case 3:
                    recordEmployeeAttendance(); // 출퇴근 기록 메서드 호출
                    break;
                case 4:
                    System.out.println("로그아웃합니다.");
                    logout = true; // 로그아웃을 true로 설정하여 반복문 종료
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (!logout);
        Main.showLoginPage();

        // 메서드 종료로 호출 메서드로 돌아갑니다
    }

    private static void manageEmployees() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("직원 관리 기능을 선택하세요:");
            System.out.println("1. 직원 추가");
            System.out.println("2. 직원 삭제");
            System.out.println("3. 직원 정보 수정");
            System.out.println("4. 직원 목록 조회");
            System.out.println("5. 뒤로 가기");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    removeEmployee();
                    break;
                case 3:
                    modifyEmployeeInfo();
                    break;
                case 4:
                    listEmployees();
                    break;
                case 5:
                    System.out.println("직원 관리 기능을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }

    private static void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 직원의 ID를 입력하세요: "); // 사용자로부터 직원 ID 입력 받기
        String id = scanner.nextLine();
        System.out.print("추가할 직원의 비밀번호를 입력하세요: "); // 사용자로부터 직원 비밀번호 입력 받기
        String password = scanner.nextLine();
        System.out.print("추가할 직원의 이름을 입력하세요: "); // 사용자로부터 직원 이름 입력 받기
        String name = scanner.nextLine();
        System.out.print("추가할 직원의 부서를 입력하세요: "); // 사용자로부터 직원 부서 입력 받기
        String department = scanner.nextLine();
        System.out.print("추가할 직원의 직급을 입력하세요: "); // 사용자로부터 직원 직급 입력 받기
        String position = scanner.nextLine();

        // EmployeeManagement의 registerEmployee 메서드를 모든 필요한 정보와 함께 호출합니다.
        EmployeeManagement.registerEmployee(id, password, name, department, position);

        System.out.println("직원이 추가되었습니다."); // 직원 등록 완료 메시지 출력
    }

    private static void removeEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        // 직원 삭제 코드 추가
        EmployeeManagement.removeEmployee(id);

        System.out.println("직원이 삭제되었습니다.");
    }

    private static void modifyEmployeeInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        // 직원 정보 수정 코드 추가

        System.out.println("직원 정보가 수정되었습니다.");
    }

    private static void listEmployees() {
        // EmployeeManagement 클래스의 getEmployeeList() 메서드를 호출하여 직원 목록을 가져옵니다.
        List<String> employeeList = EmployeeManagement.getEmployeeList();

        // 가져온 직원 목록을 출력합니다.
        System.out.println("직원 목록:");
        for (String employee : employeeList) {
            System.out.println(employee);
        }
    }
    private static void recordEmployeeAttendance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("출퇴근 기록할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        // 출퇴근 기록 메서드 호출
        EmployeeManagement.recordAttendance(id);
    }

    private static void viewAttendanceRecords(Map<String, String> attendanceRecords) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("출퇴근 기록을 조회할 방법을 선택하세요:");
        System.out.println("1. 직원별 조회");
        System.out.println("2. 기간별 조회");
        System.out.println("3. 날짜별 조회");
        System.out.println("4. 뒤로 가기");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 엔터키 소비

        switch (choice) {
            case 1:
                viewAttendanceByEmployee();
                break;
            case 2:
                viewAttendanceByPeriod(EmployeeManagement.attendanceRecords);
                break;
            case 3:
                viewAttendanceByDate(EmployeeManagement.attendanceRecords);
                break;
            case 4:
                System.out.println("이전 메뉴로 돌아갑니다.");
                break;
            default:
                System.out.println("잘못된 선택입니다. 다시 선택하세요.");
        }
    }

    private static void viewAttendanceByEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("출퇴근 기록을 조회할 직원 ID를 입력하세요: ");
        String id = scanner.nextLine();

        String attendanceRecord = EmployeeManagement.getAttendanceRecord(id);
        if (attendanceRecord != null) {
            System.out.println("출퇴근 기록: " + attendanceRecord);
        } else {
            System.out.println("해당 직원의 출퇴근 기록이 없습니다.");
        }
    }

    public static void viewAttendanceByPeriod(Map<String, String> attendanceRecords) {
        Scanner scanner = new Scanner(System.in);

        // 기간 입력 받기
        System.out.print("조회할 기간의 시작일을 입력하세요 (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine();
        System.out.print("조회할 기간의 종료일을 입력하세요 (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine();

        // 기간 문자열을 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // 조회 결과 저장할 리스트
        List<String> filteredRecords = new ArrayList<>();

        // 기간에 해당하는 출퇴근 기록 필터링
        for (Map.Entry<String, String> entry : attendanceRecords.entrySet()) {
            String recordDateStr = entry.getValue().split(" - ")[1].split(" ")[0]; // 출퇴근 기록에서 날짜 부분 추출
            LocalDate recordDate = LocalDate.parse(recordDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (!recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                filteredRecords.add(entry.getValue());
            }
        }

        // 조회 결과 출력
        if (filteredRecords.isEmpty()) {
            System.out.println("해당 기간의 출퇴근 기록이 없습니다.");
        } else {
            System.out.println("기간별 출퇴근 기록:");
            for (String record : filteredRecords) {
                System.out.println(record);
            }
        }
    }

    public static void viewAttendanceByDate(Map<String, String> attendanceRecords) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("조회할 날짜를 입력하세요 (yyyy-MM-dd): ");
        String date = scanner.nextLine();

        boolean hasAttendanceRecord = false;
        for (Map.Entry<String, String> entry : attendanceRecords.entrySet()) {
            String recordDate = entry.getValue().split(" - ")[1].split(" ")[0]; // 출퇴근 기록에서 날짜 부분 추출
            if (recordDate.equals(date)) {
                hasAttendanceRecord = true;
                System.out.println("출퇴근 기록: " + entry.getValue());
                break;
            }
        }

        if (!hasAttendanceRecord) {
            System.out.println("해당 날짜의 출퇴근 기록이 없습니다.");
        }
    }
}
