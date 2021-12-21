package com.maktab.view;

import com.maktab.dao.AccessDao;
import com.maktab.exceptions.InvalidDateFormat;
import com.maktab.models.MyDate;
import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;
import com.maktab.service.ManagerService;
import com.maktab.service.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("---------- Welcome ----------");
        outer:
        while (true) {
            try {
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
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
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
            String enterDate = scanner.next();
            if (enterDate.equalsIgnoreCase("y")) {
                System.out.println("enter date yy-mm-dd :");
                date = scanner.next();
            }
            checkValidation(date);
            System.out.println("enter count of result in per page :");
            int countResult = scanner.nextInt();
            UserService userService = new UserService();
           /* Session session= AccessDao.sessionFactory.openSession();
            Transaction transaction=session.beginTransaction();
            Query<Ticket> query=  session.createQuery("from Ticket  where origin=:origin and destination=:destination ",Ticket.class);
            query.setParameter("origin", origin);
            query.setParameter("destination", destination);

           List<Ticket> tickets=query.list();
            System.out.println(tickets.get(0));
            transaction.commit();
            session.close();*/
            List<Ticket> ticketList = userService.getTicketInfo(origin, destination, date);
            if (ticketList.size() <= countResult) {
                ticketList.forEach(System.out::println);
            } else {
                int i = 0;
                int page = 1;
                outer:
                while (true) {
                    i = printListTickets(countResult, ticketList, i);
                    if (page > 1) {
                        System.out.println("1.previous page");
                    }
                    if (i < ticketList.size()) {
                        System.out.println("2.next page");
                    }
                    int input = scanner.nextInt();
                    switch (input) {
                        case 1:
                            if (i > countResult) {
                                i -= countResult;
                                printListTickets(countResult, ticketList, i);
                            }
                            break;
                        case 2:
                            printListTickets(countResult, ticketList, i);
                            break;
                        case 0:
                            break outer;
                    }

                }
            }
            System.out.println("1.show detail\n2.exit");
            switch (scanner.nextInt()) {
                case 1:
                    showDetail();
                    break;
                case 2:
                default:
                    throw new NumberFormatException("enter 1 or 2 ");
            }
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
            managerService.saveBus();
            System.out.println("you entered successfully.");
            managerShowMenu();
        } else {
            System.out.println("userName or password is invalid !");
        }

    }

    private static void managerShowMenu() {
        ManagerService managerService = new ManagerService();
        try {
            System.out.println("1.show report travels\n2.exit");
            switch (scanner.nextInt()) {
                case 1:
                    managerService.getReportOfTravels();
                    break;
                case 2:
                    break;
                default:
                    throw new NumberFormatException("enter 1 or 2 please !");
            }
        } catch (NumberFormatException | InputMismatchException exp) {
            System.out.println(exp.getMessage());
        }
    }

    private static int printListTickets(int countResult, List<Ticket> ticketDtoList, int i) {
        for (int j = i; j < countResult + i; j++) {
            System.out.println(ticketDtoList.get(j));
        }
        i += countResult;
        return i;
    }

    private static void showDetail() {
        try {
            System.out.println("enter id ticket :");
            int id = scanner.nextInt();
            UserService userService = new UserService();
            userService.showDetail(id);
            System.out.println("1.sale\n2.exit");
            switch (scanner.nextInt()){
                case 1:
                    saleTicket();
                case 2:
                    break;
                default:
                    throw  new NumberFormatException("enter 1 or 2 !");
            }
        } catch (RuntimeException exp) {
            System.out.println(exp.getMessage());
        }
    }
    private static  void saleTicket(){
        System.out.println("enter number of ticket:");
        int countTicket=scanner.nextInt();
        for(int i=0;i<countTicket;i++){

        }
    }
}
