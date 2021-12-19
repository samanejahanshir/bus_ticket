package com.maktab.dao;

import com.maktab.models.Manager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ManagerDao extends AccessDao {
    public Manager getManager(String userName,String password){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Manager where userName=:userName and password=:password",Manager.class);
        query.setParameter("userName",userName);
        query.setParameter("password",password);
        List<Manager> managers=query.list();
        return managers.get(0);
    }

}
