package com.maktab.dao;

import com.maktab.models.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;

public class BusDao extends AccessDao {
    public void save(Bus bus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(bus);
        transaction.commit();
        session.close();
    }

    public List<BusDto> getDetailsOfBus() {
        Session session = sessionFactory.openSession();
        List<BusDto> busDtos = new ArrayList<>();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Bus.class, "bus");
        criteria.createAlias("bus.tickets", "ticket");
        criteria.add(Restrictions.eq("ticket.statusTicket", StatusTicket.SALE));
        criteria.addOrder(Order.desc("ticket.date"));
       /* criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("bus.type"))
                .add(Projections.count("ticket.statusTicket").as("ticketSale"))
                        .add(Projections.property("chairReminding").as("chairReminding"))
                );
        criteria.setResultTransformer(Transformers.aliasToBean(BusDto.class));*/
/*criteria.setProjection( Projections.projectionList()
                .add( Projections.rowCount(),"ticket.statusTicket" )
                .add( Property.forName("bus.chairReminding").avg().as("chairReminding") )
                .add( Property.forName("bus.type").group().as("type" )
                ));*/
        // criteria.addOrder(Order.asc("name"));
        //select name,min,max,avg,rows
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("bus.type"))
                .add(Projections.property("bus.company"), "companyName")
                .add(Projections.property("ticket.date"), "date")
                .add(Projections.property("bus.chairReminding"), "chairReminding")
                .add(Projections.rowCount(), "ticketSale"));
        criteria.setResultTransformer(Transformers.aliasToBean(BusDto.class));

        List list = criteria.list();
        //list.forEach(System.out::println);
        busDtos = list;
        transaction.commit();
        session.close();
        return busDtos;
    }
    public void updateBusChairReminding(Bus bus){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(bus);
        transaction.commit();
        session.close();
    }
}
