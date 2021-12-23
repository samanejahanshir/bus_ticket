package com.maktab.dao;

import com.maktab.models.Bus;
import com.maktab.models.BusDto;
import com.maktab.models.StatusTicket;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("bus.type"))
                .add(Projections.property("bus.company"), "companyName")
                .add(Projections.property("ticket.date"), "date")
                .add(Projections.property("bus.chairReminding"), "chairReminding")
                .add(Projections.property("bus.type"), "type")
                .add(Projections.rowCount(), "ticketSale"));
        criteria.setResultTransformer(Transformers.aliasToBean(BusDto.class));

        List list = criteria.list();
        //list.forEach(System.out::println);
        busDtos = list;
        transaction.commit();
        session.close();
        return busDtos;
    }

    public void updateBusChairReminding(Bus bus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(bus);
        transaction.commit();
        session.close();
    }
}
