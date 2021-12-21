package com.maktab.dao;

import com.maktab.models.Bus;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BusDao extends AccessDao{
    public void save(Bus bus){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        session.save(bus);
        transaction.commit();
        session.close();
    }
}
