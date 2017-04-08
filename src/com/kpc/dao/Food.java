package com.kpc.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kpc.beans.OrderInputBean;
import com.kpc.beans.OrderOutputBean;
import com.kpc.beans.SelectOrderBean;
import com.kpc.services.Kpc;
import com.kpc.tools.DBUtil;

/**
 * 数据处理
 * 
 * @author cgw
 *
 */
public class Food implements Kpc {
    
    /* 操作成功 */
    private static String success = "1";
    
    /* 操作异常 */
    private static String except = "0";
    
    // 数据库操作对象
    DBUtil dbu = new DBUtil();

    // 创建订单
    public OrderOutputBean Order(OrderInputBean input, String no) {
	
	// 创建连接对象
	Connection conn = DBUtil.getConnection();
	// active -> 1
	String active = success;
	Integer carriedOut = 0;
	// 订单操作
	for (FoodDTO dto : nullVerification(input.get_foodInfo())) {
	 // sql语句的拼装
	String sql = 
		"insert into orderInfo(peo, foodName, foodNumber, time, active) values(?, ?, ?, ?, ?);";
	// 数据库添加操作
	carriedOut = DBUtil.carriedOut(conn, sql, no, dto.get_foodId(), dto.get_foodNumber(), toTime(), active);
	if (carriedOut == 0) {
	    // 操作失败
	    break;
	    }
	}
	OrderOutputBean output = new OrderOutputBean();
	output.setCommit(carriedOut);
	
	return output;
    }

    // 用户登录和区分
    public boolean Exist(String no, String psd) {

	// 创建连接对象
	Connection conn = DBUtil.getConnection();
	// sql语句的拼装
	String sql = 
		"select * from USE_MAP where no = ? and password = ?;";
	// 查询数据操作
	Map<String, Object> resultMap = DBUtil.queryForRow(conn, sql, no, psd);
	// 关闭连接对象
	DBUtil.closeConnection(conn);
	
	if (!resultMap.isEmpty()) {
	    // 用户存在
	    return true;
	}
	
	// 用户不存在
	return false;
    }
    
    // 临时替代ajax
    public void statusChange(String active) {
	
	// 创建连接对象
	Connection conn = DBUtil.getConnection();
	
	// sql语句的拼装
	String sql = 
		"update orderInfo set active = ? where no = ?";
	int fuck = DBUtil.carriedOut(conn, sql, except, active);
	if (fuck > 0) System.out.println("ajax -> OK");
	System.out.println("ajax -> Except");
    }

    // 查询订单信息
    public List<SelectOrderBean> SelectOrders(String theWay, String no) {
	
	// 创建连接对象
	Connection conn = DBUtil.getConnection();
	
	// sql语句的拼装
	String sql = null;
	
	// 不指定查询状态(处理和未处理的状态)
	Integer theWayStatus = 0;
	
	// 不指定查询No
	Integer noStatus = 1;
	
	// 指定查询状态(处理或未处理的状态)
	if (theWay != null && theWay.trim().length() > 0) theWayStatus = 2;
	
	// 指定查询No
	if (no != null && no.trim().length() > 0) noStatus = 4;
	
	// 查询结果缓存
	List<Map<String,Object>> results = null;
	     
	switch (theWayStatus + noStatus) {
	case 1:
	    // 查询所有状态下(处理和未处理的状态)的订单信息
	    sql = 
	     "select * from orderInfo;";
	    results = DBUtil.queryForRows(conn, sql);
	    break;
	case 4:
	    // 查询指定no下所有状态下(处理和未处理的状态)的订单信息
	    sql = 
	     "select * from orderInfo where peo = ?;";
	    results = DBUtil.queryForRows(conn, sql, no);
	    break;
	case 3:
	    // 查询不指定No下指定查询状态(处理或未处理的状态)的订单信息
	    sql = 
	     "select * from orderInfo where active = ?;";
	    results = DBUtil.queryForRows(conn, sql, theWay);
	    break;
	case 6:
	    // 查询指定No下指定查询状态(处理或未处理的状态)的订单信息
	    sql = 
	     "select * from orderInfo where peo = ? and active = ?;";
	    results = DBUtil.queryForRows(conn, sql, no, theWay);
	    break;

	default:
	    return new LinkedList<SelectOrderBean>();
	}
	
	// 关闭连接对象
	DBUtil.closeConnection(conn);
	
	List<FoodDTO> resultList = new LinkedList<FoodDTO>();
	for (Map<String, Object> foodDTO : nullCheck(results)) {
	    resultList.add(MapsToList(foodDTO));
	}
	
	List<SelectOrderBean> outputList = new LinkedList<SelectOrderBean>();
	
	boolean forFlag = false;
	for (FoodDTO dto : resultList) {
	    
	    if (!forFlag) {
		addBean(dto, outputList);
		forFlag = true;
		continue;
	    }
	    
	    // 默认不等
	    boolean flag = false;
	    for (SelectOrderBean bean : outputList) {
		if (dto.get_peo().equals(bean.getPeo())) {
		    flag = true;
		    
		    bean.get_foodInfo().add(addDTO(dto));
		    break;
		}
	    }
	    
	    if (!flag) {
		
		addBean(dto, outputList);
	    }
	}
	
	return outputList;
    }
    
    // 充值操作
    public boolean setMoney(String no, double money) {
	
	// 创建连接对象
	Connection conn = DBUtil.getConnection();
	
	// sql语句的拼装
	String sqlbase = 
		"select money from USE_MAP where no = ?";
	Map<String, Object> result = DBUtil.queryForRow(conn, sqlbase, no);
	double difference = (Double) result.get("money");
	
	// sql语句的拼装
	String sql = 
		"update USE_MAP set money = ? where no = ?";
	int fuck = DBUtil.carriedOut(conn, sql, difference + money, no);
	
	// 关闭连接对象
	DBUtil.closeConnection(conn);
	
	if (!(fuck > 0)) return false;
	return true;
    }
    
    // 消费操作
    public boolean commonMoney(String no, double money, String status) {
	
	// 创建连接对象
	Connection conn = DBUtil.getConnection();
		
	// sql语句的拼装
	String sql = 
		"select money from USE_MAP where no = ?";
	Map<String, Object> result = DBUtil.queryForRow(conn, sql, no);
	double difference = nullVerification((Double) result.get("money"));
	if (success.equals(status) && difference < money) {
	    // 关闭连接对象
	    DBUtil.closeConnection(conn);
	    // 钱不够订单
	    return false;
	}
	
	if (success.equals(status)) {
	    // sql语句的拼装
	    String sqlto = 
		    "update USE_MAP set money = ? where no = ?";
	    int consumption = DBUtil.carriedOut(conn, sqlto, difference - money, no);
	    // 关闭连接对象
	    DBUtil.closeConnection(conn);
	    if (consumption > 0) return true;
	}else if (except.equals(status)) {
	    // sql语句的拼装
	    String sqlback = 
		    "update USE_MAP set money = ? where no = ?";
	    int consumptionback = DBUtil.carriedOut(conn, sqlback, difference + money, no);
	    if (consumptionback > 0) return true;
	}
	
	// 心疼你的钱
	return false;
    }
    
    // 注册处理
    public boolean regist(String no, String name, String password) {
	
	// 创建连接对象
	Connection conn = DBUtil.getConnection();
		
	// sql语句的拼装
	String sql = 
		"insert into USE_MAP(no, name, password) values(?, ?, ?);";
	int registStatus = DBUtil.carriedOut(conn, sql, no, name, password);
	// 关闭连接对象
	DBUtil.closeConnection(conn);
	if (registStatus > 0) return true;
	
	return false;
    }
    
    // 添加时间属性，预留给乐观锁使用
    private Object toTime() {
	
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	return dateFormat.format(date);
    }
    
    private void addBean(FoodDTO init, List<SelectOrderBean> outputList) {
	
	SelectOrderBean outputBean = new SelectOrderBean();
	List<FoodDTO> list = new LinkedList<FoodDTO>();
	list.add(addDTO(init));
	
	outputBean.setPeo(init.get_peo());
	outputBean.set_foodInfo(list);
	outputList.add(outputBean);
	
    }

    private FoodDTO addDTO(FoodDTO init) {
	
	FoodDTO setTo = new FoodDTO();
	setTo.set_foodNumber(init.get_foodNumber());
	setTo.set_foodName(init.get_foodName());
	setTo.set_active(init.get_active());
	
	return setTo;
    }

    private List<Map<String, Object>> nullCheck(List<Map<String, Object>> results) {
	
	return results == null ? new ArrayList<Map<String, Object>>() : results;
    }

    private FoodDTO MapsToList(Map<String, Object> foodDTO) {
	
	FoodDTO dto = new FoodDTO();
	dto.set_foodNumber(foodDTO.get("foodNumber").toString());
	dto.set_foodName(foodDTO.get("foodName").toString());
	dto.set_active(foodDTO.get("active").toString());
	dto.set_peo(foodDTO.get("peo").toString());
	
	return dto;
    }
    
    private double nullVerification(Double vf) {
	
	return vf == null ? 0 : vf;
    }
    
    @SuppressWarnings("unchecked")
    private <T> T nullVerification(T vf) {
	
	return vf == null ? (T) new LinkedList<T>() : vf;
    }

}
