package com.kpc.services;

import java.util.List;

import com.kpc.beans.OrderInputBean;
import com.kpc.beans.OrderOutputBean;
import com.kpc.beans.SelectOrderBean;

public interface Kpc {

    /* ���Ͳ��� */
    OrderOutputBean Order(OrderInputBean input, String no);
    
    /* �ж�no�Ƿ���� */
    boolean Exist(String no, String password);
    
    /* ��ѯ������Ϣ */
    List<SelectOrderBean> SelectOrders(String theWay, String no);
    
    void statusChange(String active);
    
    /* ��ֵ���� */
    boolean setMoney(String no, double money);
    
    /* ���Ѳ��� */
    boolean commonMoney(String no, double money, String status);
    
    /* ע����� */
    boolean regist(String no, String name, String password);
    
}
