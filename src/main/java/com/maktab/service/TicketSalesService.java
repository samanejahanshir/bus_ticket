package com.maktab.service;

import com.maktab.dao.TicketDao;
import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TicketSalesService {
    public List<TicketDto> filterByCompany(List<TicketDto> ticketDtos,String nameCompany) {
        List<TicketDto> list = ticketDtos.stream().filter(ticketDto -> ticketDto.getCompanyName()
                .equalsIgnoreCase(nameCompany)).collect(Collectors.toList());
       return  list;
    }

    public List<TicketDto> filterByBusType(List<TicketDto> ticketDtos,String type) {
        List<TicketDto> list = ticketDtos.stream().filter(ticketDto -> ticketDto.getBusType().name().equalsIgnoreCase(type))
                .collect(Collectors.toList());
       return list;

    }

    public List<TicketDto> filterByTimes(List<TicketDto> ticketDtos, String minTime, String maxTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date minDate = sdf.parse(minTime);
        Date maxDate = sdf.parse(maxTime);
        List<TicketDto> list = ticketDtos.stream().filter(ticketDto -> {
                    boolean b = false;
                    try {
                        b = sdf.parse(ticketDto.getTime()).compareTo(minDate) == 1 &&
                                sdf.parse(ticketDto.getTime()).compareTo(maxDate) == -1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return b;
                })
                .collect(Collectors.toList());
       return list;
    }

    public List<TicketDto> filterByPrice(List<TicketDto> ticketDtos,long minPrice,long maxPrice) {
        List<TicketDto> list = ticketDtos.stream().filter(ticketDto -> ticketDto.getPrice() > minPrice && ticketDto.getPrice() < maxPrice)
                .collect(Collectors.toList());
      return list;
    }

    public  void updateTicketsForSale(List<Ticket> tickets){
        TicketDao ticketDao=new TicketDao();
        ticketDao.updateTicketForSale(tickets);
    }
}
