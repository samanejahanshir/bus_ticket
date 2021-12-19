package com.maktab.view;

import com.maktab.service.ManagerService;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("---------- Welcome ----------");
        System.out.println("1.sign in\n2.sale ticket");
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
