package com.example.demo.util;

import java.util.Scanner;

import org.springframework.stereotype.Component;
@Component
public class Validation {
    public int getValidInt(Scanner sc, String message) {
        while (true) {
            System.out.println(message);
            if (sc.hasNextInt()) {
                int result = sc.nextInt();
                sc.nextLine();
                return result;
            } else {
                System.out.println("Error: Please enter a number, not text!");
                sc.nextLine();
            }
        }
    }

    public boolean valPhone(String phone) {
        if (phone.length() != 10) {
            System.out.println("Error: Phone number must contain 10 digits!");
            return false;
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                System.out.println("Error: Phone number must contain only digits!");
                return false;
            }
        }
        return true;
    }

    public boolean valDate(String date) {
        if (date.length() != 10) return false;
        if (date.charAt(2) != '.' || date.charAt(5) != '.') return false;
        int day = 10 * (date.charAt(0) - '0') + (date.charAt(1) - '0');
        int month = 10 * (date.charAt(3) - '0') + (date.charAt(4) - '0');
        int year = 1000 * (date.charAt(6) - '0') +
                100 * (date.charAt(7) - '0') +
                10 * (date.charAt(8) - '0') +
                (date.charAt(9) - '0');
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        if (year < 2024 || year > 2027) return false;
        return true;
    }

    public boolean valTime(String time) {
        if (time.length() != 5) return false;
        if (time.charAt(2) != ':') return false;
        int hours = 10 * (time.charAt(0) - '0') + (time.charAt(1) - '0');
        int minutes = 10 * (time.charAt(3) - '0') + (time.charAt(4) - '0');
        if (hours < 0 || hours > 23) return false;
        if (minutes < 0 || minutes > 59) return false;
        return true;
    }
}
