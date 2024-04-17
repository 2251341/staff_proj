package org.example.controller;

import org.example.container.Container;
import org.example.dao.EmployeeDao;
import org.example.dto.Employee;

import org.example.service.AdminService;
import org.example.service.EmployeeService;
import org.example.util.Util;

import java.time.LocalDate;
import java.util.*;

import static org.example.service.EmployeeService.attendanceRecords;


public class AdminController {
    private static String ADMIN_USERNAME = "admin";
    private static String ADMIN_PASSWORD = "admin";
    private static Set<String> adminUsernames = new HashSet<>();
    private static AdminService adminService = new AdminService(new EmployeeDao());



    public static boolean isAdmin(String username) {
        return adminUsernames.contains(username);
    }

    private static void setAdminCredentials(String adminId, String adminPassword) {
        ADMIN_USERNAME = adminId;
        ADMIN_PASSWORD = adminPassword;
        adminUsernames.add(adminId);
    }


    public static boolean validateUsername(String username) {
        return ADMIN_USERNAME.equals(username) || EmployeeService.validateEmployee(username);
    }

    public static boolean login(String username, String password) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            return true;
        } else {
            return EmployeeService.validateEmployeeCredentials(username, password);
        }
    }
    public static void showAdminPage(String username) {
        Scanner scanner = new Scanner(System.in);
        boolean logout = false;

        while (!logout) {
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
                    manageEmployees();
                    break;
                case 2:
                    viewAttendanceRecords(adminService.getAttendanceRecords());
                    break;
                case 3:
                    recordAttendanceProcess(scanner);
                    break;
                case 4:
                    System.out.println("로그아웃합니다.");
                    logout = true;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
    }

    private static void recordAttendanceProcess(Scanner scanner) {
        System.out.println("기록할 직원 ID를 입력하세요:");
        String employeeId = scanner.nextLine();
        if (EmployeeService.validateEmployee(employeeId)) {
            System.out.println("출퇴근 유형을 입력하세요 (예: 출근, 퇴근):");
            String attendanceType = scanner.nextLine();
            AdminService.recordEmployeeAttendance(employeeId, attendanceType);
        } else {
            System.out.println("유효하지 않은 직원 ID입니다.");
        }
    }

    public static void manageEmployees() {
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
        EmployeeService.registerEmployee(id, password, name, department, position);

        System.out.println("직원이 추가되었습니다."); // 직원 등록 완료 메시지 출력
    }

    private static void removeEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        // 직원 삭제 코드 추가
        EmployeeService.removeEmployee(id);

        System.out.println("직원이 삭제되었습니다.");
    }

    private static void modifyEmployeeInfo() {
        Scanner scanner = Container.getScanner();
        System.out.print("수정할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();

        Employee employee = EmployeeService.getEmployee(id);
        if (employee != null) {
            System.out.println("현재 직원 정보:");
            System.out.println("이름: " + employee.getName());
            System.out.println("부서: " + employee.getDepartment());
            System.out.println("직급: " + employee.getPosition());

            System.out.println("수정할 정보를 입력하세요:");
            System.out.print("이름: ");
            String name = scanner.nextLine();
            System.out.print("부서: ");
            String department = scanner.nextLine();
            System.out.print("직급: ");
            String position = scanner.nextLine();

            // Update employee information
            employee.setName(name);
            employee.setDepartment(department);
            employee.setPosition(position);

            System.out.println("직원 정보가 수정되었습니다.");

            // Display updated information
            System.out.println("수정된 직원 정보:");
            System.out.println("이름: " + employee.getName());
            System.out.println("부서: " + employee.getDepartment());
            System.out.println("직급: " + employee.getPosition());
        } else {
            System.out.println("해당 ID의 직원을 찾을 수 없습니다.");
        }
    }

    private static void listEmployees() {
        // EmployeeManagement 클래스의 getEmployeeList() 메서드를 호출하여 직원 목록을 가져옵니다.
        List<Employee> employeeList = EmployeeService.getEmployeeList();

        // 가져온 직원 목록을 출력합니다.
        System.out.println("직원 목록:");
        for (Employee employee : employeeList) {
            System.out.println("ID: " + employee.getId());
            System.out.println("이름: " + employee.getName());
            System.out.println("부서: " + employee.getDepartment());
            System.out.println("직급: " + employee.getPosition());
            System.out.println("------------------------");
        }
    }


    public static void viewAttendanceRecords(HashMap<String, String> attendanceRecords) {
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
                AdminService.viewAttendanceByEmployee();
                break;
            case 2:
                AdminService.viewAttendanceByPeriod(EmployeeService.attendanceRecords);
                break;
            case 3:
                AdminService.viewAttendanceByDate(EmployeeService.attendanceRecords);
                break;
            case 4:
                System.out.println("이전 메뉴로 돌아갑니다.");
                break;
            default:
                System.out.println("잘못된 선택입니다. 다시 선택하세요.");
        }
    }


}