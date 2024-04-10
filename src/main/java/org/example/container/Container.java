package org.example.container;

import org.example.controller.MemberController;
import org.example.controller.Session;
import org.example.dao.AttendanceDao;
import org.example.dao.EmployeeDao;
import org.example.dto.Member;
import org.example.service.AttendanceService;
import org.example.service.EmployeeService;

import java.util.Scanner;

public class Container {
    private static MemberController memberController;
    private static Session session;
    private static Scanner scanner;
    public static EmployeeDao employeeDao;
    public static AttendanceDao attendanceDao;
    public static EmployeeService employeeService;
    public static AttendanceService attendanceService;

    public static MemberController getMemberController() {
        if (memberController == null) {
            memberController = new MemberController(getScanner());
        }
        return memberController;
    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public static void init() {
        employeeDao = new EmployeeDao();
        attendanceDao = new AttendanceDao();
        employeeService = new EmployeeService();
        attendanceService = new AttendanceService();
    }
}
