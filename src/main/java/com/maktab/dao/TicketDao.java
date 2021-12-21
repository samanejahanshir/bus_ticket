package com.maktab.dao;

import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TicketDao extends AccessDao {
    public List<Ticket> getTicketsByCondition(String origin, String destination, String date) {
        Session session = sessionFactory.openSession();
        List<TicketDto> ticketDto = new ArrayList<>();
        List<Ticket> tickets=new ArrayList<>();
        Transaction transaction = session.beginTransaction();
       /* Criteria criteria = session.createCriteria(Ticket.class,"ticket");
        criteria.createAlias("ticket.bus", "bus");
        SimpleExpression eqOrigin = Restrictions.eq("ticket.origin", origin);
        SimpleExpression eqDestination = Restrictions.eq("ticket.destination", destination);

       *//* if (origin != null && origin.length() != 0) {
            originCond = Restrictions.eq("origin", origin);
        }
        Criterion destinationCond = null;
        if (destination != null && destination.length() != 0) {
            destinationCond = Restrictions.eq("destination", destination);
        }*//*
        Criterion dateCond = null;
        if (date != null && date.length() != 0) {
            dateCond = Restrictions.eq("date", date);
        }

        LogicalExpression resultAnd = Restrictions.and(eqOrigin,eqDestination);

        if (dateCond != null) {
            criteria.add(Restrictions.and(resultAnd,dateCond));
        }else {
            criteria.add(resultAnd);
        }
       *//* if (originCond != null && destinationCond != null && dateCond!=null)
            criteria.add(Restrictions.or(originCond, destinationCond,dateCond));
        if (originCond != null && destinationCond == null && dateCond==null)
            criteria.add(originCond);
        if (originCond == null && destinationCond != null && dateCond==null)
            criteria.add(destinationCond);
        if(origin!=null && destination!=null && dateCond==null){
            criteria.add(Restrictions.or(originCond, destinationCond));
        }
        if(origin!=null && destination!=null && dateCond!=null){
            criteria.add(Restrictions.or(originCond, dateCond));
        }*//*
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("ticket.id"))
                .add(Projections.property("bus.company"))
                .add(Projections.property("bus.type"))
                .add(Projections.property("ticket.date"))
                .add(Projections.property("ticket.time"))
                .add(Projections.property("ticket.price"))
                .add(Projections.count("ticket.bus")));
        criteria.setResultTransformer(Transformers.aliasToBean(TicketDto.class));
        List list = criteria.list();
        ticketDto=list;*/


          Query<Ticket> query=  session.createQuery("from Ticket t join fetch t.bus where t.origin=:origin and t.destination=:destination ");
          query.setParameter("origin", origin);
            query.setParameter("destination", destination);
      //  NativeQuery<Ticket> nq=session.createNativeQuery("select * from ticket ");


       // Query<Ticket> query=session.createQuery("from Ticket ");
        tickets=query.list();
        transaction.commit();
        session.close();
        return tickets;


    }

}
