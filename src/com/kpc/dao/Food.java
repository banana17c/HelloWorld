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
 * ���ݴ���
 * 
 * @author cgw
 *
 */
public class Food implements Kpc {
    
    /* �����ɹ� */
    private static String success = "1";
    
    /* �����쳣 */
    private static String except = "0";
    
    // ���ݿ��������
    DBUtil dbu = new DBUtil();

    // ��������
    public OrderOutputBean Order(OrderInputBean input, String no) {
	
	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
	// active -> 1
	String active = success;
	Integer carriedOut = 0;
	// ��������
	for (FoodDTO dto : nullVerification(input.get_foodInfo())) {
	 // sql����ƴװ
	String sql = 
		"insert into orderInfo(peo, foodName, foodNumber, time, active) values(?, ?, ?, ?, ?);";
	// ���ݿ���Ӳ���
	carriedOut = DBUtil.carriedOut(conn, sql, no, dto.get_foodId(), dto.get_foodNumber(), toTime(), active);
	if (carriedOut == 0) {
	    // ����ʧ��
	    break;
	    }
	}
	OrderOutputBean output = new OrderOutputBean();
	output.setCommit(carriedOut);
	
	return output;
    }

    // �û���¼������
    public boolean Exist(String no, String psd) {

	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
	// sql����ƴװ
	String sql = 
		"select * from USE_MAP where no = ? and password = ?;";
	// ��ѯ���ݲ���
	Map<String, Object> resultMap = DBUtil.queryForRow(conn, sql, no, psd);
	// �ر����Ӷ���
	DBUtil.closeConnection(conn);
	
	if (!resultMap.isEmpty()) {
	    // �û�����
	    return true;
	}
	
	// �û�������
	return false;
    }
    
    // ��ʱ���ajax
    public void statusChange(String active) {
	
	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
	
	// sql����ƴװ
	String sql = 
		"update orderInfo set active = ? where no = ?";
	int fuck = DBUtil.carriedOut(conn, sql, except, active);
	if (fuck > 0) System.out.println("ajax -> OK");
	System.out.println("ajax -> Except");
    }

    // ��ѯ������Ϣ
    public List<SelectOrderBean> SelectOrders(String theWay, String no) {
	
	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
	
	// sql����ƴװ
	String sql = null;
	
	// ��ָ����ѯ״̬(�����δ�����״̬)
	Integer theWayStatus = 0;
	
	// ��ָ����ѯNo
	Integer noStatus = 1;
	
	// ָ����ѯ״̬(�����δ�����״̬)
	if (theWay != null && theWay.trim().length() > 0) theWayStatus = 2;
	
	// ָ����ѯNo
	if (no != null && no.trim().length() > 0) noStatus = 4;
	
	// ��ѯ�������
	List<Map<String,Object>> results = null;
	     
	switch (theWayStatus + noStatus) {
	case 1:
	    // ��ѯ����״̬��(�����δ�����״̬)�Ķ�����Ϣ
	    sql = 
	     "select * from orderInfo;";
	    results = DBUtil.queryForRows(conn, sql);
	    break;
	case 4:
	    // ��ѯָ��no������״̬��(�����δ�����״̬)�Ķ�����Ϣ
	    sql = 
	     "select * from orderInfo where peo = ?;";
	    results = DBUtil.queryForRows(conn, sql, no);
	    break;
	case 3:
	    // ��ѯ��ָ��No��ָ����ѯ״̬(�����δ�����״̬)�Ķ�����Ϣ
	    sql = 
	     "select * from orderInfo where active = ?;";
	    results = DBUtil.queryForRows(conn, sql, theWay);
	    break;
	case 6:
	    // ��ѯָ��No��ָ����ѯ״̬(�����δ�����״̬)�Ķ�����Ϣ
	    sql = 
	     "select * from orderInfo where peo = ? and active = ?;";
	    results = DBUtil.queryForRows(conn, sql, no, theWay);
	    break;

	default:
	    return new LinkedList<SelectOrderBean>();
	}
	
	// �ر����Ӷ���
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
	    
	    // Ĭ�ϲ���
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
    
    // ��ֵ����
    public boolean setMoney(String no, double money) {
	
	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
	
	// sql����ƴװ
	String sqlbase = 
		"select money from USE_MAP where no = ?";
	Map<String, Object> result = DBUtil.queryForRow(conn, sqlbase, no);
	double difference = (Double) result.get("money");
	
	// sql����ƴװ
	String sql = 
		"update USE_MAP set money = ? where no = ?";
	int fuck = DBUtil.carriedOut(conn, sql, difference + money, no);
	
	// �ر����Ӷ���
	DBUtil.closeConnection(conn);
	
	if (!(fuck > 0)) return false;
	return true;
    }
    
    // ���Ѳ���
    public boolean commonMoney(String no, double money, String status) {
	
	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
		
	// sql����ƴװ
	String sql = 
		"select money from USE_MAP where no = ?";
	Map<String, Object> result = DBUtil.queryForRow(conn, sql, no);
	double difference = nullVerification((Double) result.get("money"));
	if (success.equals(status) && difference < money) {
	    // �ر����Ӷ���
	    DBUtil.closeConnection(conn);
	    // Ǯ��������
	    return false;
	}
	
	if (success.equals(status)) {
	    // sql����ƴװ
	    String sqlto = 
		    "update USE_MAP set money = ? where no = ?";
	    int consumption = DBUtil.carriedOut(conn, sqlto, difference - money, no);
	    // �ر����Ӷ���
	    DBUtil.closeConnection(conn);
	    if (consumption > 0) return true;
	}else if (except.equals(status)) {
	    // sql����ƴװ
	    String sqlback = 
		    "update USE_MAP set money = ? where no = ?";
	    int consumptionback = DBUtil.carriedOut(conn, sqlback, difference + money, no);
	    if (consumptionback > 0) return true;
	}
	
	// �������Ǯ
	return false;
    }
    
    // ע�ᴦ��
    public boolean regist(String no, String name, String password) {
	
	// �������Ӷ���
	Connection conn = DBUtil.getConnection();
		
	// sql����ƴװ
	String sql = 
		"insert into USE_MAP(no, name, password) values(?, ?, ?);";
	int registStatus = DBUtil.carriedOut(conn, sql, no, name, password);
	// �ر����Ӷ���
	DBUtil.closeConnection(conn);
	if (registStatus > 0) return true;
	
	return false;
    }
    
    // ���ʱ�����ԣ�Ԥ�����ֹ���ʹ��
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
