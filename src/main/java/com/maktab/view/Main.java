package com.maktab.view;

import com.maktab.dao.BusDao;
import com.maktab.exceptions.InvalidDateFormat;
import com.maktab.exceptions.InvalidInputException;
import com.maktab.models.*;
import com.maktab.service.ManagerService;
import com.maktab.service.TicketSalesService;
import com.maktab.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int countOfPrint = 3;

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
            int i = 0;
            int page = 0;
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
            System.out.println("enter count of result:");
            int countResult = scanner.nextInt();
            UserService userService = new UserService();
            List<TicketDto> ticketList = userService.getTicketInfo(origin, destination, date, countResult);
            if (ticketList.isEmpty()) {
                System.out.println("there is not any ticket to show .");
            } else {
                if (ticketList.size() <= countOfPrint) {
                    ticketList.forEach(System.out::println);
                } else {
                    outer:
                    while (true) {
                        if(i<ticketList.size() && page==0) {
                            i = printListTickets(ticketList, i);
                            page++;
                        }
                        if (page > 1) {
                            System.out.println("1.previous page");
                        }
                        if (i < ticketList.size()) {
                            System.out.println("2.next page");
                        }
                        System.out.println("3.back");
                        int input = scanner.nextInt();
                        switch (input) {
                            case 1:
                                if (i > countOfPrint) {
                                    i -= countOfPrint+countOfPrint;
                                  i=  printListTickets(ticketList, i);
                                  page--;
                                }
                                break;
                            case 2:
                              i=  printListTickets(ticketList, i);
                              page++;
                                if(i>ticketList.size()){
                                    break;
                                }
                                break;
                            case 3:
                                break outer;
                        }

                    }
                }
                outer:
                while (true) {
                    System.out.println("1.show detail\n2.filtering result\n3.exit");
                    switch (scanner.nextInt()) {
                        case 1:
                            showDetail(ticketList,page);
                            break;
                        case 2:
                            List<TicketDto> list = ticketList;
                            filteringResult(list);
                            break;
                        case 3:
                            break outer;
                        default:
                            throw new NumberFormatException("enter 1 or 2 ");
                    }
                }
            }
        }catch
            (InvalidDateFormat | InputMismatchException | NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException
            exp){
                System.out.println(exp.getMessage());
                scanner.nextLine();
            }

    }

    private static void filteringResult(List<TicketDto> ticketDtos) {
        String companyName="", busType="", minTime="", maxTime="";
        long minPrice=0, maxPrice=0;
        TicketSalesService ticketService = new TicketSalesService();
        outer:
        while (true) {
            System.out.println("filter result by : \n1.company name\n2.Bus type\n3.between two price\n4.between two times\n5.confirm filter");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        companyName = filterByCompany();
                        break;
                    case 2:
                        busType = filterByBusType();
                        break;
                    case 3:
                        long[] prices = filterByPrice();
                        minPrice = prices[0];
                        maxPrice = prices[1];
                        break;
                    case 4:
                        String[] times = filterByTimes();
                        minTime = times[0];
                        maxTime = times[1];
                        break;
                    case 5:
                       List<TicketDto> listFiltered= ticketService.filterListTicketDto(ticketDtos,companyName,busType,minTime,maxTime,minPrice,maxPrice);
                       if(listFiltered.isEmpty()){
                           System.out.println("not found any thing to show");
                       }else {
                           listFiltered.forEach(System.out::println);
                       }
                        break outer;
                    default:
                        throw new InvalidInputException("enter 1 - 5 please !");
                }

            } catch (NumberFormatException | InputMismatchException | ParseException exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    private static String filterByCompany() {
        System.out.println("enter company name :");
        /* TicketSalesService ticketService = new TicketSalesService();
       // List<TicketDto> list = ticketService.filterByCompany(ticketDtos, nameCompany);
        if (list.isEmpty()) {
            System.out.println("not found any thing to show !");
        } else {
            list.forEach(System.out::println);
        }*/
        return scanner.next();
    }

    private static String filterByBusType() {
        System.out.println("enter Bus type:");
        /*TicketSalesService ticketService = new TicketSalesService();
        List<TicketDto> list = ticketService.filterByBusType(ticketDtos, type);
        if (list.isEmpty()) {
            System.out.println("not found any thing to show !");
        } else {
            list.forEach(System.out::println);
        }*/
        return scanner.next();
    }

    private static String[] filterByTimes() throws ParseException {
        System.out.println("enter min times : 00:00");
        String minTime = scanner.next();
        System.out.println("enter max times : 00:00");
        String maxTime = scanner.next();
       /* TicketSalesService ticketService = new TicketSalesService();
        List<TicketDto> list = ticketService.filterByTimes(ticketDtos, minTime, maxTime);
        if (list.isEmpty()) {
            System.out.println("not found any thing to show !");
        } else {
            list.forEach(System.out::println);
        }*/
        String[] times = new String[2];
        times[0] = minTime;
        times[1] = maxTime;
        return times;
    }

    private static long[] filterByPrice() {
        System.out.println("enter min price :");
        long minPrice = scanner.nextInt();
        System.out.println("enter max price :");
        long maxPrice = scanner.nextInt();
       /* TicketSalesService ticketService = new TicketSalesService();
        List<TicketDto> list = ticketService.filterByPrice(ticketDtos, minPrice, maxPrice);
        if (list.isEmpty()) {
            System.out.println("not found any thing to show !");
        } else {
            list.forEach(System.out::println);
        }*/
        long[] prices = new long[2];
        prices[0] = minPrice;
        prices[1] = maxPrice;
        return prices;
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
            // managerService.saveBus();
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
                    managerService.getReportOfTravels().forEach(System.out::println);
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

    private static int printListTickets(List<TicketDto> ticketDtoList, int i) {
        if (i < ticketDtoList.size()-countOfPrint) {
            for (int j = i; j < countOfPrint + i; j++) {
                System.out.println(ticketDtoList.get(j));
            }
        } else {
            for (int j = i; j < ticketDtoList.size(); j++) {
                System.out.println(ticketDtoList.get(j));
            }
        }
        i += countOfPrint;
        return i;
    }

    private static void showDetail(List<TicketDto> ticketDtos,int page) {
        try {
            List<Ticket> tickets=new ArrayList<>();
            UserService userService = new UserService();
            System.out.println("enter row number of ticket :");
            int rowNumber = scanner.nextInt();
            if(page==1) {
                 tickets = userService.showDetail(rowNumber, ticketDtos);
            }else {
                tickets=userService.showDetail((page-1)*countOfPrint+rowNumber,ticketDtos);
            }
            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }
            System.out.println("1.sale\n2.exit");
            switch (scanner.nextInt()) {
                case 1:
                    saleTicket(tickets);
                case 2:
                    break;
                default:
                    throw new NumberFormatException("enter 1 or 2 !");
            }
        } catch (RuntimeException exp) {
            System.out.println(exp.getMessage());
        }
    }

    private static void saleTicket(List<Ticket> tickets) {
        UserService userService = new UserService();
        TicketSalesService ticketSalesService = new TicketSalesService();
        BusDao busDao = new BusDao();
        System.out.println("enter number of ticket:");
        int countTicket = scanner.nextInt();
        if (tickets.stream().filter(ticket -> ticket.getStatusTicket().equals(StatusTicket.NOT_SALE)).count() >= countTicket) {
            List<Ticket> ticketsNoSale = tickets.stream().filter(ticket -> ticket.getStatusTicket().equals(StatusTicket.NOT_SALE)).collect(Collectors.toList());
            for (int i = 0; i < countTicket; i++) {
                System.out.println("enter first name:");
                String firstName = scanner.next();
                System.out.println("enter last name:");
                String lastName = scanner.next();
                System.out.println("enter national code:");
                String nationalCode = scanner.next();
                System.out.println("enter mobile:");
                String mobile = scanner.next();
                System.out.println("enter age:");
                int age = scanner.nextInt();
                System.out.println("enter gender: 1.male  2.female");
                int gender = scanner.nextInt();
                User user = new User();
                user.setAge(age);
                user.setFamily(lastName);
                user.setName(firstName);
                user.setMobile(mobile);
                if (gender == 1) {
                    user.setGender(Gender.MALE);
                } else {
                    user.setGender(Gender.FEMALE);
                }
                user.setNationalCode(nationalCode);
                userService.saveUser(user);
                ticketsNoSale.get(i).setUser(user);
                ticketsNoSale.get(i).setStatusTicket(StatusTicket.SALE);
                ticketsNoSale.get(i).getBus().setChairReminding(ticketsNoSale.get(i).getBus().getChairReminding() - countTicket);
                busDao.updateBusChairReminding(ticketsNoSale.get(i).getBus());
            }
            ticketSalesService.updateTicketsForSale(ticketsNoSale);
            ticketsNoSale.stream().filter(ticket -> ticket.getStatusTicket().equals(StatusTicket.SALE)).forEach(ticket -> System.out.println(ticket.printDetail()));
        } else {
            System.out.println("sorry : this amount of tickets is not available .");
        }
    }
}
