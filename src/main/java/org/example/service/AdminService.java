package org.example.service;

import org.example.dao.EmployeeDao;
import org.example.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.example.service.EmployeeService.attendanceRecords;

public class AdminService {
    private EmployeeDao employeeDao;
    private static Map<String, String> attendanceRecords;

    public AdminService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
        this.attendanceRecords = new HashMap<>();
    }

    public HashMap<String, String> getAttendanceRecords() {
        return (HashMap<String, String>) attendanceRecords;
    }
    public static void recordEmployeeAttendance(String employeeId, String attendanceType) {
        String dateTimeKey = Util.getNowDateStr();
        String recordKey = employeeId + ":" + dateTimeKey;

        if (attendanceRecords != null) {
            if (attendanceRecords.containsKey(recordKey)) {
                System.out.println("직원 " + employeeId + "의 출석 기록을 업데이트합니다.");
                attendanceRecords.put(recordKey, attendanceType);
            } else {
                System.out.println("직원 " + employeeId + "의 새로운 출석을 기록합니다.");
                attendanceRecords.put(recordKey, attendanceType);
            }
        } else {
            System.out.println("출퇴근 기록이 초기화되지 않았습니다.");
        }
    }


    public static void viewAttendanceByEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("출퇴근 기록을 조회할 직원 ID를 입력하세요: ");
        String employeeId = scanner.nextLine().trim();

        boolean found = false;
        for (String key : attendanceRecords.keySet()) {
            if (key.startsWith(employeeId + ":")) {
                String date = key.substring(key.indexOf(':') + 1);
                String record = attendanceRecords.get(key);
                System.out.println(employeeId + " on " + date + " - " + record);
                found = true;
            }
        }

        if (!found) {
            System.out.println("해당 직원의 출퇴근 기록이 없습니다.");
        }
    }

    public static void viewAttendanceByPeriod(HashMap<String, String> attendanceRecords) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("조회할 기간의 시작일을 입력하세요 (yyyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("조회할 기간의 종료일을 입력하세요 (yyyy-MM-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());

        boolean found = false;
        for (Map.Entry<String, String> entry : attendanceRecords.entrySet()) {
            String dateStr = entry.getKey().split(":")[1];
            LocalDateTime date = Util.parseDateTime(dateStr);
            if (!date.toLocalDate().isBefore(startDate) && !date.toLocalDate().isAfter(endDate)) {
                System.out.println(entry.getKey().split(":")[0] + " on " + dateStr + " - " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("조회된 기간 동안의 출퇴근 기록이 없습니다.");
        }
    }

    public static void viewAttendanceByDate(HashMap<String, String> attendanceRecords) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("조회할 날짜를 입력하세요 (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        boolean found = false;
        for (Map.Entry<String, String> entry : attendanceRecords.entrySet()) {
            String dateStr = entry.getKey().split(":")[1];
            LocalDateTime dateTime = Util.parseDateTime(dateStr);
            if (dateTime.toLocalDate().equals(date)) {
                System.out.println(entry.getKey().replaceFirst(":", " on ") + " - " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("해당 날짜의 출퇴근 기록이 없습니다.");
        }
    }

}
