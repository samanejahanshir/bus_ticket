package com.maktab.dao;

import com.maktab.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao extends AccessDao {
    public void saveUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }
}
