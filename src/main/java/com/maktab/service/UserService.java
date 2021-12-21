package com.maktab.service;

import com.maktab.dao.TicketDao;
import com.maktab.models.Ticket;

import java.util.List;

public class UserService {
    public List<Ticket> getTicketInfo(String origin, String destination, String date){
        TicketDao ticketDao=new TicketDao();
        return ticketDao.getTicketsByCondition(origin,destination,date);

    }
    public Ticket showDetail(int id){

return  null;
    }
}
