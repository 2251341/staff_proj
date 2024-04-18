package org.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.example.dao.EmployeeDao;
import org.example.dto.Employee;

import org.example.util.Util;

public class AdminService {
    private static EmployeeDao employeeDao = new EmployeeDao();

    public static void recordEmployeeAttendance(Employee employee) {
        if (employee != null) {

            String dateTimeKey = Util.getNowDateStr();
            employee.getAttendanceRecords().add(dateTimeKey);
            System.out.println("출퇴근 기록이 추가되었습니다: \n" + employee.getEmployeeId() + " - " + formatDattime(dateTimeKey));

//            String dateTimeKey = Util.getNowDateStr();
//            String record = dateTimeKey  +": " + attendanceType;
//            employee.getAttendanceRecords().add(record);
//            System.out.println("출근 기록이 추가되었습니다: " + employee.getEmployeeId() + " - " + record);
        } else {
            System.out.println("유효하지 않은 직원 ID입니다.");
        }
    }

    public static String formatDattime(String dateTimeString) {
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parseDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return parseDateTime.format(fomatter);
    }

    public static void viewAttendanceByEmployee(String employeeId) {
        Employee employee = EmployeeService.getEmployee(employeeId);
        if (employee != null && !employee.getAttendanceRecords().isEmpty()) {
            System.out.println(employeeId + "의 출퇴근 기록:");

            for (String record : employee.getAttendanceRecords()) {
                System.out.println(formatDattime(record));
            }
        } else {
            System.out.println("해당 직원의 출퇴근 기록이 없습니다.");
        }
    }

    public static void viewAttendanceByDate(LocalDate date) {
        List<Employee> employees = EmployeeService.getAllEmployees();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(date.format(formatter) + "의 출퇴근 기록:");
        boolean found = false;

        for (Employee employee : employees) {
            for (String record : employee.getAttendanceRecords()) {
                if (record.startsWith(date.format(formatter))) {
                    System.out.println(employee.getEmployeeId() + " - " + formatDattime(record));
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("해당 날짜의 출퇴근 기록이 없습니다.");
        }
    }

    public static void viewAttendanceByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Employee> employees = EmployeeService.getAllEmployees();
        System.out.println("기간 " + startDate + "부터 " + endDate + "까지의 출퇴근 기록:");
        boolean found = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Employee employee : employees) {
            for (String record : employee.getAttendanceRecords()) {

                String datePart = record.split("T")[0];
                LocalDate recordDate = LocalDate.parse(datePart, formatter);

                if (!recordDate.isBefore(startDate) && !recordDate.isAfter(endDate)) {
                    System.out.println(employee.getEmployeeId() + " - " + formatDattime(record));
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("조회된 기간 동안의 출퇴근 기록이 없습니다.");
        }
    }

}
