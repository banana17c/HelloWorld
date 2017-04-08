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
 * 订餐处理控制
 * 
 * @author cgw
 *
 */
public class KpcOrder {

    Kpc kpc = new Food();
    
    /* 操作成功 */
    private static String success = "1";
    
    /* 操作异常 */
    private static String except = "0";
    
    /**
     * 登录处理
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
	
	// 获取jsp中的参数
	String _no = request.getParameter("no"); // 用户id
	String _pwd = request.getParameter("password"); // 用户密码
	// 判断是否存在
	if (kpc.Exist(_no, _pwd)) {
	    // 用户编号
	    request.setAttribute("_no", _no);
	    request.getSession().setAttribute("SESSION_NO", _no);
	    // 存在 -> 跳转到订餐页面
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}else {
	    // chrome 弹窗 warning
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('登录id或密码错误!');</script>");
	    // 不存在 -> 回到登录页面
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	}
    }
    
    /**
     * 订餐处理
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
	// 将订餐信息存放到FoodDTO中
	List<FoodDTO> foodList = Helper.setFoodDTO(request);
	// 计算订购价格(2块钱一件)
	double money = foodList.size() << 1;
	
	// 获取jsp中的参数
//	String _no = request.getParameter("no"); // 用户id
	String _no = (String) request.getSession().getAttribute("SESSION_NO");
	request.setAttribute("_no", _no);
	input.set_foodInfo(foodList);
	
	// 消费(分开写，回滚)
	if (_no == null || "".equals(_no)) {
	    // 未登录
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('订餐请先登录!');</script>");
	    // 未登录 -> 回到登录页面
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	    }
	
	// 消费(分开写，回滚)
	if (!(kpc.commonMoney(_no, money, success))) {
	    request.setAttribute("prompt", "钱不够，回家要去!");
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}
	
	// 提交订餐
	OrderOutputBean output = kpc.Order(input, _no);
	if (output.getCommit() > 0) {
	    // 订餐提交OK
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}else {
	    // 回滚
	    if (kpc.commonMoney(_no, money, except)) {
		request.setAttribute("prompt", "订餐失败，钱没少");
	    }else {
		request.setAttribute("prompt", "订餐失败，但是你的钱少了");
	    }
	    // 订餐提交失败
	    request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	}
    }
    
    /**
     * 订单查询
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
	
	// 交易状态改变
	String active = request.getParameter("statusChange");
	if (!(active == null || "".equals(active))) {
	    kpc.statusChange(active);
	}else {
	    // 获取查询的方式
	    String _theWay = request.getParameter("theWay");
		
	    // 获取订单者的编号
	    String _no = request.getParameter("no");
		
	    // 查询订餐信息
	    List<SelectOrderBean> output = kpc.SelectOrders(_theWay, _no);
	    
	    request.setAttribute("output", output);
	}
	
	request.getRequestDispatcher("/permissions/deal/reservation_processing.jsp").forward(request, response);
    }

    /**
     * 查询跳转
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
	
	// 获取jsp中的参数
	String _no = (String) request.getSession().getAttribute("SESSION_NO");
	if (!("0".equals(_no))) {
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('请用管理员帐号登录!');</script>");
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	    }
	// 跳转到处理
	request.getRequestDispatcher("/permissions/deal/reservation_processing.jsp").forward(request, response);
    }

    /**
     * 充值跳转
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
	
	// 获取jsp中的参数
	String _no = (String) request.getSession().getAttribute("SESSION_NO");
	if (!("0".equals(_no))) {
	    request.setAttribute("info",
			"<script type='text/javascript'>window.alert('请用管理员帐号登录!');</script>");
	    request.getRequestDispatcher("/welcome/login.jsp").forward(request, response);
	    }
	// 跳转到处理
	request.getRequestDispatcher("/permissions/deal/set_money.jsp").forward(request, response);
    }

    /**
     * 充值操作
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
	
	// 获取充值的编号
	String no = request.getParameter("moneyNo");
	
	// 获取充值大小
	String money = request.getParameter("setMoney");
	if (money == null && "0".equals(money)) {
	    request.setAttribute("prompt", "充值OK");
	    Helper.back(request, response);
	}

	double moneyDouble = 0;
	try {
	    moneyDouble = Double.parseDouble(money);
	} catch (NumberFormatException e) {
	    request.setAttribute("prompt", "请输入数字");
	    Helper.back(request, response);
	}
	if (moneyDouble > 0) {
	    // 充值操作
	    if (kpc.setMoney(no, moneyDouble)) {
		request.setAttribute("prompt", "充值OK");
		Helper.back(request, response);
	    }else {
		request.setAttribute("prompt", "充值错误");
		Helper.back(request, response);
	    }
	}else {
	    request.setAttribute("prompt", "请输入正数");
	    Helper.back(request, response);
	}
    }

    /**
     * 注册处理
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
	// 验证注册编号
	boolean noRegex = Pattern.matches("[0-9]{1,11}", Helper.nullVerification(no));
	// 验证注册姓名
	boolean nameRegex = Pattern.matches("[0-9a-zA-Z]{1,20}", Helper.nullVerification(name));
	// 验证注册密码
	boolean passwordRegex = Pattern.matches("[0-9a-zA-Z]{1,20}", Helper.nullVerification(password));
	
	if (!noRegex) request.setAttribute("noError", "请输入1-11位的数字");
	if (!nameRegex) request.setAttribute("nameError", "请输入1-20位的数字或字母");
	if (!passwordRegex) request.setAttribute("passwordError", "请输入1-20位的数字或字母");
	
	if (noRegex && nameRegex && passwordRegex) {
	    // 注册处理
	    if (kpc.regist(no, name, password)) {
		// 注册成功
		request.setAttribute("_no", no);
		request.setAttribute("prompt", "注册成功，欢迎订餐");
		// 跳转到注册成功页面
		request.getRequestDispatcher("/permissions/order.jsp").forward(request, response);
	    }else {
		// chrome 弹窗 warning
		request.setAttribute("info", 
			"<script type='text/javascript'>window.alert('Congratulations you, 注册失败!');</script>");
		// 用户已经存在
		request.setAttribute("noError", "用户" + no + "已存在");
		// 注册失败，回到注册页面
		request.getRequestDispatcher("/welcome/regist.jsp").forward(request, response);
	    };
	}
	// 注册失败，回到注册页面
	request.getRequestDispatcher("/welcome/regist.jsp").forward(request, response);
    }
    
    /**
     * 用户退出
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
	
	// 跳转到导航页面
	request.getRequestDispatcher("/welcome/navigation.html").forward(request, response);
    }

    /**
     * 跳转到导航页面
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
	
	// 跳转到导航页面
	request.getRequestDispatcher("/welcome/navigation.html").forward(request, response);
    }
    
}
