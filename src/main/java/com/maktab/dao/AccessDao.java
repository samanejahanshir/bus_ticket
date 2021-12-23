package com.maktab.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AccessDao {
    public static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
}
