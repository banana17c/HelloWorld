package com.kpc.services;

import java.util.List;

import com.kpc.beans.OrderInputBean;
import com.kpc.beans.OrderOutputBean;
import com.kpc.beans.SelectOrderBean;

public interface Kpc {

    /* 订餐操作 */
    OrderOutputBean Order(OrderInputBean input, String no);
    
    /* 判断no是否存在 */
    boolean Exist(String no, String password);
    
    /* 查询订餐信息 */
    List<SelectOrderBean> SelectOrders(String theWay, String no);
    
    void statusChange(String active);
    
    /* 充值操作 */
    boolean setMoney(String no, double money);
    
    /* 消费操作 */
    boolean commonMoney(String no, double money, String status);
    
    /* 注册操作 */
    boolean regist(String no, String name, String password);
    
}
