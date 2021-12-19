package com.maktab.view;

import com.maktab.exceptions.InvalidDateFormat;
import com.maktab.models.MyDate;
import com.maktab.service.ManagerService;
import com.maktab.service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("---------- Welcome ----------");
        outer:
        try {
            while (true) {
                System.out.println("1.sign in\n2.sale ticket\n3.exit");
                switch (scanner.nextInt()) {
                    case 1:
                        managerSignIn();
                        break;
                    case 2:
                        showTicketsList();
                        break;
                    case 3:
                        break outer;
                    default:
                        throw new RuntimeException("enter 1-3 please !");
                }

            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showTicketsList() {
        try {
            String date = "";
            System.out.println("enter origin city :");
            String origin = scanner.next();
            System.out.println("enter destination city :");
            String destination = scanner.next();
            System.out.println("do you enter date? y/n");
            String enterDate=scanner.next();
            if(enterDate.equalsIgnoreCase("y")) {
                System.out.println("enter date yy-mm-dd :");
                date = scanner.next();

            }
            checkValidation(date);
            System.out.println("enter count of result in per page :");
            int countResult = scanner.nextInt();
            UserService userService = new UserService();
            userService.getTicketInfo(origin, destination, date, countResult);
        } catch (InvalidDateFormat | InputMismatchException | NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException exp) {
            System.out.println(exp.getMessage());
            scanner.nextLine();
        }

    }

    private static void checkValidation(String date) {
        MyDate myDate = new MyDate();
        if (!date.isEmpty()) {
            String[] dateSplit = date.split("-");
            myDate.isValidDate(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
        }
    }

    private static void managerSignIn() {
        System.out.println("user name :");
        String userName = scanner.next();
        System.out.println("password :");
        String password = scanner.next();
        ManagerService managerService = new ManagerService();
        if (managerService.signInManager(userName, password)) {
            managerService.getMenuManager();
        } else {
            System.out.println("userName or password is invalid !");
        }

    }
}
