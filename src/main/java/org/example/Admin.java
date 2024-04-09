package org.example;

import org.example.container.Container;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Admin {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private AttendanceRecord[] attendanceRecords;

    public void makeTestData() {
        System.out.println("테스트를 위한 관리자 및 직원 데이터를 생성합니다.");

        members.add(new Member("admin", "admin", "관리자"));
        members.add(new Member("user1", "password1", "홍길동"));
        members.add(new Member("user2", "password2", "김철수"));

        System.out.println("테스트 데이터 생성이 완료되었습니다.");
    }

    private void setAttendanceRecords(AttendanceRecord[] attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    public void addEmployee() {
        System.out.println("\n====== 직원 추가 ======");
        System.out.print("이름: ");
        String name = Container.getScanner().nextLine();
        System.out.print("직급: ");
        String position = Container.getScanner().nextLine();
        System.out.print("아이디: ");
        String id = Container.getScanner().nextLine();
        System.out.print("비밀번호: ");
        String password = Container.getScanner().nextLine();

        Member member = new Member(id, password, name);

        Employee newEmployee = new Employee(name, position, member);
        employeeList.add(newEmployee);
        System.out.println("직원이 추가되었습니다.");
    }

    public void showEmployeeList() {
        System.out.println("\n====== 직원 목록 보기 ======");
        if (employeeList.isEmpty()) {
            System.out.println("등록된 직원이 없습니다.");
        } else {
            for (Employee employee : employeeList) {
                System.out.println("이름: " + employee.getName() + ", 직급: " + employee.getPosition() +
                        ", 아이디: " + employee.getId());
            }
        }
    }

    public void deleteEmployee() {
        System.out.println("\n====== 직원 삭제 ======");
        if (employeeList.isEmpty()) {
            System.out.println("등록된 직원이 없습니다.");
        } else {
            System.out.print("삭제할 직원의 아이디를 입력하세요: ");
            String id = Container.getScanner().nextLine();
            boolean found = false;
            for (Employee employee : employeeList) {
                if (employee.getId().equals(id)) {
                    employeeList.remove(employee);
                    System.out.println("직원이 삭제되었습니다.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("해당 아이디의 직원을 찾을 수 없습니다.");
            }
        }
    }

    public void viewAttendanceRecords() {
        System.out.println("1. 직원별 조회");
        System.out.println("2. 날짜별 조회");
        System.out.println("3. 기간별 조회");
        System.out.print("원하는 조회 방법을 선택하세요: ");
        int choice = Container.getScanner().nextInt();
        Container.getScanner().nextLine(); // 개행 문자 제거

        switch (choice) {
            case 1:
                viewAttendanceRecordsByEmployee();
                break;
            case 2:
                viewAttendanceRecordsByDate();
                break;
            case 3:
                viewAttendanceRecordsByPeriod();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void viewAttendanceRecordsByEmployee() {
        System.out.print("조회할 직원의 ID를 입력하세요: ");
        String employeeId = Container.getScanner().nextLine();

        for (Employee employee : employeeList) {
            if (employee.getId().equals(employeeId)) {
                System.out.println(employee.getName() + "의 출퇴근 기록:");
                for (AttendanceRecord record : attendanceRecords) {
                    if (record.getEmployeeId().equals(employeeId)) {
                        System.out.println(record);
                    }
                }
                return;
            }
        }
        System.out.println("해당 ID의 직원을 찾을 수 없습니다.");
    }

    private void viewAttendanceRecordsByDate() {
        System.out.print("조회할 날짜를 입력하세요 (YYYY-MM-DD): ");
        String dateString = Container.getScanner().nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(dateString);
            for (AttendanceRecord record : attendanceRecords) {
                if (record.getDate().equals(date)) {
                    System.out.println("날짜: " + dateString + ", 출근: " +
                            (record.isAttendanceIn() ? "O" : "X"));
                }
            }
        } catch (Exception e) {
            System.out.println("날짜 형식이 잘못되었습니다.");
        }
    }

    private void viewAttendanceRecordsByPeriod() {
        System.out.print("조회할 시작 날짜를 입력하세요 (YYYY-MM-DD): ");
        String startDateString = Container.getScanner().nextLine();
        System.out.print("조회할 종료 날짜를 입력하세요 (YYYY-MM-DD): ");
        String endDateString = Container.getScanner().nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);
            for (AttendanceRecord record : attendanceRecords) {
                Date recordDate = record.getDate();
                if (!recordDate.before(startDate) && !recordDate.after(endDate)) {
                    System.out.println("날짜: " + dateFormat.format(recordDate) + ", 출근: " +
                            (record.isAttendanceIn() ? "O" : "X"));
                }
            }
        } catch (Exception e) {
            System.out.println("날짜 형식이 잘못되었습니다.");
        }
    }

    public boolean isAdmin(String adminId, String adminPassword) {
        return adminId.equals("admin") && adminPassword.equals("adminpassword");
    }

    public Employee findEmployee(String employeeId) {
        for (Employee employee : employeeList) {
            if (employee.getId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    public void adminMenu(AttendanceRecord[] attendanceRecords) {
        while (true) {
            System.out.println("\n====== 관리자 모드 ======");
            System.out.println("1. 직원 추가");
            System.out.println("2. 직원 목록 보기");
            System.out.println("3. 출퇴근 기록 조회");
            System.out.println("4. 종료");

            int choice = Container.getScanner().nextInt();
            Container.getScanner().nextLine(); // 개행 문자 제거
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    showEmployeeList();
                    break;
                case 3:
                    viewAttendanceRecords();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
