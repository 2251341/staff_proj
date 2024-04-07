package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Employee {
    private String name;
    private String password;
    private String role; // "employee" or "admin"

    public Employee(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

