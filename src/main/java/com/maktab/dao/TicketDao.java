package com.maktab.dao;

import com.maktab.models.Ticket;
import com.maktab.models.TicketDto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.DistinctResultTransformer;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;

public class TicketDao extends AccessDao {
    public List<TicketDto> getTicketsByCondition(String origin, String destination, String date) {
        Session session = sessionFactory.openSession();
        List<TicketDto> ticketDto = new ArrayList<>();
        List<Ticket> tickets=new ArrayList<>();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Ticket.class,"ticket");
        criteria.createAlias("ticket.bus", "bus");
        SimpleExpression eqOrigin = Restrictions.eq("ticket.origin", origin);
        SimpleExpression eqDestination = Restrictions.eq("ticket.destination", destination);
        criteria.add(Restrictions.and(eqDestination,eqOrigin));
        if(date!=null && date!=""){
            criteria.add(Restrictions.and(Restrictions.eq("ticket.date",date)));
        }

        criteria.setProjection(Projections.distinct(Projections.projectionList()
                .add(Projections.property("bus.chairReminding").as("chairReminding"))
                .add(Projections.property("bus.company").as("companyName"))
                .add(Projections.property("bus.type").as("busType"))
                .add(Projections.property("ticket.date").as("date"))
                .add(Projections.property("ticket.time").as("time"))
                .add(Projections.property("ticket.price").as("price"))));

        criteria.setResultTransformer(Transformers.aliasToBean(TicketDto.class));
        List list = criteria.list();
        ticketDto=list;


        /*  Query<Ticket> query=  session.createQuery("from Ticket t join fetch t.bus where t.origin=:origin and t.destination=:destination ");
          query.setParameter("origin", origin);
            query.setParameter("destination", destination);*/
      //  NativeQuery<Ticket> nq=session.createNativeQuery("select * from ticket ");


       // Query<Ticket> query=session.createQuery("from Ticket ");
       /* tickets=query.list();
        transaction.commit();
        session.close();
        return tickets;*/

return  ticketDto;
    }

}
