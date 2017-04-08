package com.kpc.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kpc.dao.FoodDTO;

/**
 * ����������ת
 * 
 * @author cgw
 *
 */
@SuppressWarnings("serial")
public class Helper extends HttpServlet {
    
    KpcOrder kpc = new KpcOrder();
    
    KpcOrder kpcTc = new KpcOrder();
    
    /* ���Ϳ�ѡʳ������ */
    private final Integer count = 10;
    
    /**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 * 
	 *             /servlet/LoginServlet
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	
	this.connection(request, response);
	}
    
    /**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	
	this.connection(request, response);
	}
    
    /**
     * ���Ӵ���
     * 
     * @param request
     * 		the request send by the client to the server
     * @param response
     * 		the response send by the server to the client
     * @throws IOException 
     * @throws ServletException 
     */
    private void connection(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {

	request.setCharacterEncoding("gbk");
	response.setCharacterEncoding("gbk");

	response.setContentType("text/html");
//	PrintWriter pw = response.getWriter();

	String[] address = request.getRequestURI().split("/");
	if ("login".equals(address[3])) {
	    // ��¼����
	    kpc.login(request, response);
	} else if ("order".equals(address[3])) {
	    // ���ʹ���
	    kpc.Order(request, response);
	} else if ("select".equals(address[3])) {
	    // ��ѯ����
	    kpc.SelectOrders(request, response);
	} else if ("deal".equals(address[3])) {
	    // ��ת����
	    kpc.ToSelectOrders(request, response);
	} else if ("set_money".equals(address[3])) {
	    // ��ֵ��ת
	    kpc.setMoney(request, response);
	} else if ("money".equals(address[3])) {
	    // ��ֵ����
	    kpc.money(request, response);
	} else if ("regist".equals(address[3])) {
	    // ע�����
	    kpc.regist(request, response);
	} else if ("loginOut".equals(address[3])) {
	    // �˳�����
	    kpc.loginOut(request, response);
	} else {
	    // navigation
	    kpc.navigation(request, response);
	}
    }
    
    /**
     * �����ύ���ݵ���Ϣ
     * 
     * @param request �ύ����
     * @return ������Ϣ
     */
    public static List<FoodDTO> setFoodDTO(HttpServletRequest request) {
	
	List<FoodDTO> foodList = new LinkedList<FoodDTO>();
	Helper helper = new Helper();
	
	for (int i = 0; i < helper.count; i++) {
	    // ��ȡʳ����
	    String _foodId = request.getParameter("foodId" + (Integer)(i + 1));
			
	    // ��ȡʳ������
	    String _foodNumber = request.getParameter("foodNumber" + (Integer)(i + 1));
	    
	    if (_foodNumber == null || _foodNumber.isEmpty() || _foodNumber.equals("0")) {
		i++;
		}else {
		    foodList.add(helper.setDTO(_foodId, _foodNumber));
		}
	}
		
	return foodList;
    }

    private FoodDTO setDTO(String _foodId, String _foodNumber) {
	
	FoodDTO dto = new FoodDTO();
	dto.set_foodId(_foodId);
	dto.set_foodNumber(_foodNumber);
	
	return dto;
    }
    
    public static void back(HttpServletRequest request, HttpServletResponse response) {
	
	try {
	    request.getRequestDispatcher("/permissions/deal/set_money.jsp").forward(request, response);
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T nullVerification(T vf) {
	
	return vf == null ? (T)"" : vf;
    }

}
