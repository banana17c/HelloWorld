package com.kpc.control;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kpc.beans.OrderInputBean;
import com.kpc.beans.OrderOutputBean;
import com.kpc.beans.SelectOrderBean;
import com.kpc.dao.Food;
import com.kpc.dao.FoodDTO;
import com.kpc.services.Kpc;

/**
 * ���ʹ������
 * 
 * @author cgw
 *
 */
public class KpcOrder {

    Kpc kpc = new Food();
    
    /* �����ɹ� */
    private static String success = "1";
    
    /* �����쳣 */
    private static String except = "0";
    
    /**
     * ��¼����
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void login(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	// ��ȡjsp�еĲ���
	String _no = request.getParameter("no"); // �û�id
	String _pwd = request.getParameter("password"); // �û�����
	// �ж��Ƿ����
	if (kpc.Exist(_no, _pwd)) {
	    // �û����
	    request.setAttribute("_no", _no);
	    request.getSession().setAttribute("SESSION_NO", _no);
	    // ���� -> ��ת������ҳ��
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}else {
	    // chrome ���� warning
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('��¼id���������!');</script>");
	    // ������ -> �ص���¼ҳ��
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	}
    }
    
    /**
     * ���ʹ���
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void Order(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	OrderInputBean input = new OrderInputBean();
	// ��������Ϣ��ŵ�FoodDTO��
	List<FoodDTO> foodList = Helper.setFoodDTO(request);
	// ���㶩���۸�(2��Ǯһ��)
	double money = foodList.size() << 1;
	
	// ��ȡjsp�еĲ���
//	String _no = request.getParameter("no"); // �û�id
	String _no = (String) request.getSession().getAttribute("SESSION_NO");
	request.setAttribute("_no", _no);
	input.set_foodInfo(foodList);
	
	// ����(�ֿ�д���ع�)
	if (_no == null || "".equals(_no)) {
	    // δ��¼
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('�������ȵ�¼!');</script>");
	    // δ��¼ -> �ص���¼ҳ��
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	    }
	
	// ����(�ֿ�д���ع�)
	if (!(kpc.commonMoney(_no, money, success))) {
	    request.setAttribute("prompt", "Ǯ�������ؼ�Ҫȥ!");
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}
	
	// �ύ����
	OrderOutputBean output = kpc.Order(input, _no);
	if (output.getCommit() > 0) {
	    // �����ύOK
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}else {
	    // �ع�
	    if (kpc.commonMoney(_no, money, except)) {
		request.setAttribute("prompt", "����ʧ�ܣ�Ǯû��");
	    }else {
		request.setAttribute("prompt", "����ʧ�ܣ��������Ǯ����");
	    }
	    // �����ύʧ��
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}
    }
    
    /**
     * ������ѯ
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void SelectOrders(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	// ����״̬�ı�
	String active = request.getParameter("statusChange");
	if (!(active == null || "".equals(active))) {
	    kpc.statusChange(active);
	}else {
	    // ��ȡ��ѯ�ķ�ʽ
	    String _theWay = request.getParameter("theWay");
		
	    // ��ȡ�����ߵı��
	    String _no = request.getParameter("no");
		
	    // ��ѯ������Ϣ
	    List<SelectOrderBean> output = kpc.SelectOrders(_theWay, _no);
	    
	    request.setAttribute("output", output);
	}
	
	request.getRequestDispatcher("/permissions/deal/reservation_processing.jsp").forward(request, response);
    }

    /**
     * ��ѯ��ת
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void ToSelectOrders(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	// ��ȡjsp�еĲ���
	String _no = (String) request.getSession().getAttribute("SESSION_NO");
	if (!("0".equals(_no))) {
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('���ù���Ա�ʺŵ�¼!');</script>");
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	    }
	// ��ת������
	request.getRequestDispatcher("/permissions/deal/reservation_processing.jsp").forward(request, response);
    }

    /**
     * ��ֵ��ת
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void setMoney(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	// ��ȡjsp�еĲ���
	String _no = (String) request.getSession().getAttribute("SESSION_NO");
	if (!("0".equals(_no))) {
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('���ù���Ա�ʺŵ�¼!');</script>");
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	    }
	// ��ת������
	request.getRequestDispatcher("/permissions/deal/set_money.jsp").forward(request, response);
    }

    /**
     * ��ֵ����
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void money(HttpServletRequest request, HttpServletResponse response) {
	
	// ��ȡ��ֵ�ı��
	String no = request.getParameter("moneyNo");
	
	// ��ȡ��ֵ��С
	String money = request.getParameter("setMoney");
	if (money == null && "0".equals(money)) {
	    request.setAttribute("prompt", "��ֵOK");
	    Helper.back(request, response);
	}

	double moneyDouble = 0;
	try {
	    moneyDouble = Double.parseDouble(money);
	} catch (NumberFormatException e) {
	    request.setAttribute("prompt", "����������");
	    Helper.back(request, response);
	}
	if (moneyDouble > 0) {
	    // ��ֵ����
	    if (kpc.setMoney(no, moneyDouble)) {
		request.setAttribute("prompt", "��ֵOK");
		Helper.back(request, response);
	    }else {
		request.setAttribute("prompt", "��ֵ����");
		Helper.back(request, response);
	    }
	}else {
	    request.setAttribute("prompt", "����������");
	    Helper.back(request, response);
	}
    }

    /**
     * ע�ᴦ��
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	
	String no = request.getParameter("no");
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	// ��֤ע����
	boolean noRegex = Pattern.matches("[0-9]{1,11}", Helper.nullVerification(no));
	// ��֤ע������
	boolean nameRegex = Pattern.matches("[0-9a-zA-Z]{1,20}", Helper.nullVerification(name));
	// ��֤ע������
	boolean passwordRegex = Pattern.matches("[0-9a-zA-Z]{1,20}", Helper.nullVerification(password));
	
	if (!noRegex) request.setAttribute("noError", "������1-11λ������");
	if (!nameRegex) request.setAttribute("nameError", "������1-20λ�����ֻ���ĸ");
	if (!passwordRegex) request.setAttribute("passwordError", "������1-20λ�����ֻ���ĸ");
	
	if (noRegex && nameRegex && passwordRegex) {
	    // ע�ᴦ��
	    if (kpc.regist(no, name, password)) {
		// ע��ɹ�
		request.setAttribute("_no", no);
		request.setAttribute("prompt", "ע��ɹ�����ӭ����");
		// ��ת��ע��ɹ�ҳ��
		request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	    }else {
		// chrome ���� warning
		request.setAttribute("info", 
			"<script type='text/javascript'>window.alert('Congratulations you, ע��ʧ��!');</script>");
		// �û��Ѿ�����
		request.setAttribute("noError", "�û�" + no + "�Ѵ���");
		// ע��ʧ�ܣ��ص�ע��ҳ��
		request.getRequestDispatcher("/welcome/regist.jsp").forward(request, response);
	    };
	}
	// ע��ʧ�ܣ��ص�ע��ҳ��
	request.getRequestDispatcher("/welcome/regist.jsp").forward(request, response);
    }
    
    /**
     * �û��˳�
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void loginOut(HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException {
	
	request.getSession().removeAttribute("SESSION_NO");
	request.getSession().setMaxInactiveInterval(1);
	
	// ��ת������ҳ��
	request.getRequestDispatcher("/welcome/navigation.html").forward(request, response);
    }

    /**
     * ��ת������ҳ��
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws ServletException
     * 		if an error occurred
     * @throws IOException
     * 		if an error occurred
     */
    public void navigation(HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException {
	
	// ��ת������ҳ��
	request.getRequestDispatcher("/welcome/navigation.html").forward(request, response);
    }
    
}
