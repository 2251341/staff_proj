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

    // viewAttendanceByEmployee 메서드는 getAttendanceRecordsByEmployee를 호출하여 결과를 출력합니다.
    public static void viewAttendanceByEmployee(String employeeId) {
        List<String> attendanceRecords = employeeDao.getAttendanceRecordsByEmployee(employeeId);
        if (!attendanceRecords.isEmpty()) {
            System.out.println(employeeId + "의 출퇴근 기록:");
            for (String record : attendanceRecords) {
                System.out.println(record);
            }
        } else {
            System.out.println("해당 직원의 출퇴근 기록이 없습니다.");
        }
    }

    // viewAttendanceByDate 메서드는 getAttendanceRecordsByDate를 호출하여 결과를 출력합니다.
    public static void viewAttendanceByDate(LocalDate date) {
        List<String> attendanceRecords = employeeDao.getAttendanceRecordsByDate(date);
        if (!attendanceRecords.isEmpty()) {
            System.out.println(date + "의 출퇴근 기록:");
            for (String record : attendanceRecords) {
                System.out.println(record);
            }
        } else {
            System.out.println("해당 날짜의 출퇴근 기록이 없습니다.");
        }
    }

    // viewAttendanceByPeriod 메서드는 getAttendanceRecordsByPeriod를 호출하여 결과를 출력합니다.
    public static void viewAttendanceByPeriod(LocalDate startDate, LocalDate endDate) {
        List<String> attendanceRecords = employeeDao.getAttendanceRecordsByPeriod(startDate, endDate);
        if (!attendanceRecords.isEmpty()) {
            System.out.println("기간 " + startDate + "부터 " + endDate + "까지의 출퇴근 기록:");
            for (String record : attendanceRecords) {
                System.out.println(record);
            }
        } else {
            System.out.println("조회된 기간 동안의 출퇴근 기록이 없습니다.");
        }
    }

}
