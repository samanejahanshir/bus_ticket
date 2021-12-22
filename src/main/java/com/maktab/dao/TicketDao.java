package com.maktab.dao;

import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketDao extends AccessDao {
    public List<TicketDto> getTicketsByCondition(String origin, String destination, String date, int count) {
        Session session = sessionFactory.openSession();
        List<TicketDto> ticketDto = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Ticket.class, "ticket");
        criteria.createAlias("ticket.bus", "bus");
        SimpleExpression eqOrigin = Restrictions.eq("ticket.origin", origin);
        SimpleExpression eqDestination = Restrictions.eq("ticket.destination", destination);
        criteria.add(Restrictions.and(eqDestination, eqOrigin));
        if (date != null) {
            criteria.add(Restrictions.and(Restrictions.eq("ticket.date", date)));
        }

        criteria.setProjection(Projections.distinct(Projections.projectionList()
                .add(Projections.property("ticket.origin").as("origin"))
                .add(Projections.property("ticket.destination").as("destination"))
                .add(Projections.property("bus.chairReminding").as("chairReminding"))
                .add(Projections.property("bus.company").as("companyName"))
                .add(Projections.property("bus.type").as("busType"))
                .add(Projections.property("ticket.date").as("date"))
                .add(Projections.property("ticket.time").as("time"))
                .add(Projections.property("ticket.price").as("price"))));
        criteria.setMaxResults(count);
        criteria.setResultTransformer(Transformers.aliasToBean(TicketDto.class));

        List list = criteria.list();
        ticketDto = list;
        transaction.commit();
        session.close();
        return ticketDto;
    }

    public List<Ticket> getDetailsOfTicket(TicketDto ticketDto) {
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = new ArrayList<>();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Ticket.class, "ticket");
        criteria.createAlias("ticket.bus", "bus");
        SimpleExpression eqOrigin = Restrictions.eq("ticket.origin", ticketDto.getOrigin());
        SimpleExpression eqDestination = Restrictions.eq("ticket.destination", ticketDto.getDestination());
        SimpleExpression eqCompany = Restrictions.eq("bus.company", ticketDto.getCompanyName());
        SimpleExpression eqDate = Restrictions.eq("ticket.date", ticketDto.getDate());
        SimpleExpression eqTime = Restrictions.eq("ticket.time", ticketDto.getTime());
        SimpleExpression eqBus = Restrictions.eq("bus.type", ticketDto.getBusType());
        criteria.add(Restrictions.and(eqDestination, eqOrigin, eqBus, eqCompany, eqDate, eqTime));
        List list = criteria.list();
        tickets = list;
        transaction.commit();
        session.close();
        return tickets;
    }

    public int updateTicketForSale(Ticket ticket) {
        //TODO
        return 0;
    }
}
