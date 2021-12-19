package com.maktab.dao;

import com.maktab.models.Ticket;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class TicketDao extends AccessDao {
    public List<Ticket> getTicketsByCondition(String origin, String destination, String date) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Ticket.class);
        Criterion originCond = null;
       /* if (origin != null && origin.length() != 0) {
            originCond = Restrictions.eq("origin", origin);
        }
        Criterion destinationCond = null;
        if (destination != null && destination.length() != 0) {
            destinationCond = Restrictions.eq("destination", destination);
        }*/
        Criterion dateCond = null;
        if (date != null && date.length() != 0) {
            dateCond = Restrictions.eq("date", date);
        }
        criteria.add(Restrictions.eq("origin", origin));
        criteria.add(Restrictions.eq("destination", destination));
        if (dateCond != null) {
            criteria.add(dateCond);
        }
        List list = criteria.list();
       /* if (originCond != null && destinationCond != null && dateCond!=null)
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
        }*/
        transaction.commit();
        session.close();

        return list;

    }

}
