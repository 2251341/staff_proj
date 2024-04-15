package org.example.container;

import org.example.dao.EmployeeDao;
import org.example.service.EmployeeService;
import org.example.db.DBConnection;

public class Container {
    public static EmployeeService employeeService;
    public static EmployeeDao employeeDao;
    public static DBConnection dbConnection;

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
}
