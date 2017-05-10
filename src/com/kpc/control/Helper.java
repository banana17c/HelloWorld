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
 * 鎺у埗杩炴帴璺宠浆
 * 
 * @author cgw
 *
 */
@SuppressWarnings("serial")
public class Helper extends HttpServlet {
    
    KpcOrder kpc = new KpcOrder();
<<<<<<< Upstream, based on repository/m3

=======
    
    KpcOrder kpcTct1 = new KpcOrder();

    /* 璁㈤鍙�夐鐗╂�绘�? */
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
     * 杩炴帴澶勭悊
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
	    // 鐧诲綍澶勭悊
	    kpc.login(request, response);
	} else if ("order".equals(address[3])) {
	    // 璁㈤澶勭悊
	    kpc.Order(request, response);
	} else if ("select".equals(address[3])) {
	    // 鏌ヨ澶勭悊
	    kpc.SelectOrders(request, response);
	} else if ("deal".equals(address[3])) {
	    // 璺宠浆澶勭悊
	    kpc.ToSelectOrders(request, response);
	} else if ("set_money".equals(address[3])) {
	    // 鍏呭�艰烦杞�?
	    kpc.setMoney(request, response);
	} else if ("money".equals(address[3])) {
	    // 鍏呭�兼搷浣�?
	    kpc.money(request, response);
	} else if ("regist".equals(address[3])) {
	    // 娉ㄥ唽鎿嶄綔
	    kpc.regist(request, response);
	} else if ("loginOut".equals(address[3])) {
	    // 閫�鍑烘搷浣�?
	    kpc.loginOut(request, response);
	} else {
	    // navigation
	    kpc.navigation(request, response);
	}
    }
    
    /**
     * 瑙ｆ瀽鎻愪氦鍐呭鐨勪俊鎭�
     * 
     * @param request 鎻愪氦鍐呭
     * @return 璁㈤淇℃伅
     */
    public static List<FoodDTO> setFoodDTO(HttpServletRequest request) {
	
	List<FoodDTO> foodList = new LinkedList<FoodDTO>();
	Helper helper = new Helper();
	
	for (int i = 0; i < helper.count; i++) {
	    // 鑾峰彇椋熺墿缂栧�?
	    String _foodId = request.getParameter("foodId" + (Integer)(i + 1));
			
	    // 鑾峰彇椋熺墿鏁伴�?
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
