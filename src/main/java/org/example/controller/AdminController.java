package org.example.controller;

import org.example.container.Container;
import org.example.dao.EmployeeDao;
import org.example.dto.Employee;
import org.example.service.AdminService;
import org.example.service.EmployeeService;
import org.example.util.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.container.Container.scanner;

public class AdminController {
    private static String ADMIN_USERNAME = "admin";
    private static String ADMIN_PASSWORD = "admin";
    private static Set<String> adminUsernames = new HashSet<>();
    private static AdminService adminService = new AdminService();

    // 관리자 여부 확인
    public static boolean isAdmin(String username) {
        return adminUsernames.contains(username);
    }

    // 관리자 자격증명 설정
    private static void setAdminCredentials(String adminId, String adminPassword) {
        ADMIN_USERNAME = adminId;
        ADMIN_PASSWORD = adminPassword;
        adminUsernames.add(adminId);
    }

    // 유효한 사용자 이름인지 확인
    public static boolean validateUsername(String username) {
        return ADMIN_USERNAME.equals(username) || EmployeeService.validateEmployee(username);
    }

    // 로그인 검증
    public static boolean login(String username, String password) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            return true;
        } else {
            return EmployeeService.validateEmployeeCredentials(username, password);
        }
    }

    // 관리자 페이지 표시
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

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        manageEmployees();
                        break;
                    case 2:
                        viewAttendanceRecords();
                        break;
                    case 3:
                        recordAttendanceProcess(scanner);
                        break;
                    case 4:
                        System.out.println("로그아웃합니다.");
                        logout = true;
                        break;
                    default:
                        System.out.println("잘못된 선택입니다. 1부터 4까지의 숫자를 입력하세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해야 합니다.");
                scanner.nextLine();
            }
        }
    }


    // 출퇴근 기록 처리
    private static void recordAttendanceProcess(Scanner scanner) {
        System.out.println("기록할 직원 ID를 입력하세요:");
        String employeeId = scanner.nextLine();
        Employee employee = EmployeeService.getEmployee(employeeId);

        if (employee != null) {
            if (EmployeeDao.hasCheckedInToday(employeeId)) {
                // 이미 출근 기록이 있으므로 퇴근 처리를 시도합니다.
                if (EmployeeDao.recordCheckOut(employeeId)) {
                    System.out.println("직원 ID: " + employeeId + "의 퇴근 기록이 완료되었습니다.");
                } else {
                    System.out.println("퇴근 기록 실패: 이미 퇴근 처리가 되었거나 기록할 수 없습니다.");
                }
            } else {
                // 출근 기록이 없으므로 출근 처리를 시도합니다.
                if (EmployeeDao.recordCheckIn(employeeId)) {
                    System.out.println("직원 ID: " + employeeId + "의 출근 기록이 완료되었습니다.");
                } else {
                    System.out.println("출근 기록 실패: 이미 출근 처리가 되었거나 기록할 수 없습니다.");
                }
            }
        } else {
            System.out.println("유효하지 않은 직원 ID입니다.");
        }
    }




    // 출퇴근 기록 처리
//    private static void recordAttendanceProcess(Scanner scanner) {
//
//        System.out.println("기록할 직원 ID를 입력하세요:");
//        String employeeId = scanner.nextLine();
//        Employee employee = EmployeeService.getEmployee(employeeId);
//        if (employee != null) {
//            String attendanceType = scanner.nextLine();
//            AdminService.recordEmployeeAttendance(employee, attendanceType);
//        } else {
//            System.out.println("유효하지 않은 직원 ID입니다.");
//        }
//    }

    // 직원 관리
    public static void manageEmployees() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("직원 관리 기능을 선택하세요:");
            System.out.println("1. 직원 추가");
            System.out.println("2. 직원 삭제");
            System.out.println("3. 직원 정보 수정");
            System.out.println("4. 직원 목록 조회");
            System.out.println("5. 뒤로 가기");
            System.out.print("선택: ");

            try {
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
                        System.out.println("잘못된 선택입니다. 1부터 5까지의 숫자를 입력하세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해야 합니다.");
                scanner.nextLine(); // 잘못된 입력을 버리고 다음 입력을 위해 준비
                return;
            }
        }
    }

    // 직원 추가
    private static void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();
        System.out.print("추가할 직원의 비밀번호를 입력하세요: ");
        String password = scanner.nextLine();
        System.out.print("추가할 직원의 이름을 입력하세요: ");
        String name = scanner.nextLine();
        System.out.print("추가할 직원의 부서를 입력하세요: ");
        String department = scanner.nextLine();
        System.out.print("추가할 직원의 직급을 입력하세요: ");
        String position = scanner.nextLine();

        EmployeeService.addEmployee(new Employee(id, password, name, department, position));
        System.out.println("직원이 추가되었습니다.");
    }

    // 직원 삭제
    private static void removeEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 직원의 ID를 입력하세요: ");
        String id = scanner.nextLine();
        EmployeeService.removeEmployee(id);
        System.out.println("직원이 삭제되었습니다.");
    }

    // 직원 정보 수정
    private static void modifyEmployeeInfo() {
        Scanner scanner = Container.getScanner();
        System.out.print("수정할 직원의 ID를 입력하세요: ");
        String employeeId = scanner.nextLine();

        Employee employee = EmployeeService.getEmployee(employeeId);
        if (employee != null) {
            System.out.println("현재 직원 정보:");
            System.out.println("이름: " + (employee.getName() != null ? employee.getName() : "정보 없음"));
            System.out.println("부서: " + (employee.getDepartment() != null ? employee.getDepartment() : "정보 없음"));
            System.out.println("직급: " + (employee.getPosition() != null ? employee.getPosition() : "정보 없음"));

            System.out.println("수정할 정보를 입력하세요:");
            System.out.print("이름: ");
            String name = scanner.nextLine();
            System.out.print("부서: ");
            String department = scanner.nextLine();
            System.out.print("직급: ");
            String position = scanner.nextLine();

            if (!name.isEmpty() && !department.isEmpty() && !position.isEmpty()) {
                employee.setName(name);
                employee.setDepartment(department);
                employee.setPosition(position);

                boolean updated = EmployeeService.updateEmployee(employee);
                if (updated) {
                    System.out.println("직원 정보가 성공적으로 수정되었습니다.");
                } else {
                    System.out.println("직원 정보 수정 실패.");
                }
            } else {
                System.out.println("입력된 정보가 유효하지 않습니다.");
            }
        } else {
            System.out.println("해당 ID의 직원을 찾을 수 없습니다.");
        }
    }


    // 직원 목록 조회
    private static void listEmployees() {
        List<Employee> employeeList = EmployeeService.getAllEmployees();
        System.out.println("직원 목록:");
        for (Employee employee : employeeList) {
            System.out.println("ID: " + employee.getEmployeeId());
            System.out.println("이름: " + employee.getName());
            System.out.println("부서: " + employee.getDepartment());
            System.out.println("직급: " + employee.getPosition());
            System.out.println("------------------------");
        }
    }

    // 출퇴근 기록 조회
    public static void viewAttendanceRecords() {
        System.out.println("조회할 출퇴근 기록의 유형을 선택하세요:");
        System.out.println("1. 직원별 조회");
        System.out.println("2. 날짜별 조회");
        System.out.println("3. 기간별 조회");
        System.out.println("4. 이전 메뉴로 돌아가기");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // 남은 줄바꿈 문자 제거

        try {
            switch (choice) {
                case 1:
                    System.out.print("직원 ID를 입력하세요: ");
                    String employeeId = scanner.nextLine();
                    AdminService.viewAttendanceByEmployee(employeeId);
                    break;
                case 2:
                    LocalDate date = askForDate("조회할 날짜를 입력하세요 (yyyy-MM-dd): ");
                    AdminService.viewAttendanceByDate(date);
                    break;
                case 3:
                    LocalDate startDate = askForDate("조회 시작 날짜를 입력하세요 (yyyy-MM-dd): ");
                    LocalDate endDate = askForDate("조회 종료 날짜를 입력하세요 (yyyy-MM-dd): ");
                    AdminService.viewAttendanceByPeriod(startDate, endDate);
                    break;
                case 4:
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 1에서 4 사이의 숫자를 입력하세요.");
            }
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해야 합니다.");
            scanner.next(); // 잘못된 입력 제거
        }
    }

    private static LocalDate askForDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine();
            try {
                return LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. 올바른 형식 (yyyy-MM-dd)을 사용해 주세요.");
            }
        }
    }
}
