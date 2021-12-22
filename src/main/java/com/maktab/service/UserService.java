package com.maktab.service;

import com.maktab.dao.TicketDao;
import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserService {
    public List<TicketDto> getTicketInfo(String origin, String destination, String date, int count) {
        TicketDao ticketDao = new TicketDao();
        return ticketDao.getTicketsByCondition(origin, destination, date, count);

    }

    public List<Ticket> showDetail(int rowNumber, List<TicketDto> ticketDtos) {
        TicketDao ticketDao = new TicketDao();
        return ticketDao.getDetailsOfTicket(ticketDtos.get(rowNumber - 1));

    }
}
