package com.maktab.service;

import com.maktab.dao.TicketDao;
import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;

import java.util.List;

public class UserService {
    public List<TicketDto> getTicketInfo(String origin, String destination, String date){
        TicketDao ticketDao=new TicketDao();
        return ticketDao.getTicketsByCondition(origin,destination,date);

    }
    public Ticket showDetail(int id){

return  null;
    }
}
