package com.maktab.service;

import com.maktab.dao.BusDao;
import com.maktab.dao.ManagerDao;
import com.maktab.models.*;

import java.util.List;

public class ManagerService {

    public boolean signInManager(String userName, String password) {
        ManagerDao managerDao = new ManagerDao();
        if (managerDao.getManager(userName, password) != null) {
            return true;
        }
        return false;
    }

    public List<BusDto> getReportOfTravels() {
        BusDao busDao = new BusDao();
        return busDao.getDetailsOfBus();
    }

    public void saveBus() {
        Bus bus = new Bus();
        bus.setCompany("company1");
        bus.setCountChair(5);
        bus.setChairReminding(5);
        bus.setType(BusType.VIP);
        for (int i = 0; i < 5; i++) {
            Ticket ticket = new Ticket();
            ticket.setDate("99-09-09");
            ticket.setDestination("tehran");
            ticket.setOrigin("semnan");
            ticket.setTime("10:00");
            ticket.setSeatNumber(i + 1);
            ticket.setPrice(2000);
            ticket.setStatusTicket(StatusTicket.NOT_SALE);
            ticket.setBus(bus);
            bus.getTickets().add(ticket);
        }

        BusDao busDao = new BusDao();
        busDao.save(bus);
    }
}
