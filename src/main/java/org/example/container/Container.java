package org.example.container;

import org.example.controller.Session;
import org.example.dao.EmployeeDao;
import org.example.service.EmployeeService;
import org.example.db.DBConnection;

import java.util.Scanner;

public class Container {
    public static EmployeeService employeeService;
    public static EmployeeDao employeeDao;
    public static DBConnection dbConnection;
    public static Session session;
    public static Scanner scanner;

    static {
        employeeService = new EmployeeService();
        employeeDao= new EmployeeDao();
    }

    public static DBConnection getDBConnection() {
        if ( dbConnection == null ) {
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }
    public static Session getSession() {
        if ( session == null ) {
            session = new Session();
        }

        return session;
    }
    public static Scanner getScanner() {
        if ( scanner == null ) {
            scanner = new Scanner(System.in);
        }

        return scanner;
    }
}
