package com.maktab.service;

import com.maktab.dao.ManagerDao;

public class ManagerService {
    public boolean signInManager(String userName,String password){
        ManagerDao managerDao=new ManagerDao();
        if(managerDao.getManager(userName,password)!=null){
            return  true;
        }
        return false;
    }
    public void getMenuManager(){
        //TODO
    }
}
