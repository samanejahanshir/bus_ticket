package com.maktab.dao;

import com.maktab.models.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import javax.persistence.ManyToMany;

public class getManagerTest {
    @Test
    public void getManagerTest(){
        String userName="admin";
        String password="admin";
        ManagerDao managerDao=new ManagerDao();
        Manager manager=managerDao.getManager(userName,password);
        Assertions.assertEquals(manager.getUserName(),"admin");
    }

    @Test
    public void getManagerNullTest(){
        String userName="adm";
        String password="1234";
        ManagerDao managerDao=new ManagerDao();
        Manager manager=managerDao.getManager(userName,password);
        Assertions.assertNull(manager);
    }
}
