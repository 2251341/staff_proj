package org.example.container;

import java.util.Scanner;

public class Container {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }
}
