<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订餐界面</title>
    
    <link type="text/css" href="resources/css/templatemo_style.css" rel="stylesheet" />

  </head>
  
<body id="home">

	<div id="templatemo_header_wrapper">
		<div id="site_title">
			<h1>
				<a href="welcome/navigation.html"></a>
			</h1>
		</div>
  Welcome ${_no} <br/>
  <font color="red"><span>${prompt}</span></font><br/>
  <div id="templatemo_menu" class="ddsmoothmenu">
  <ul>
  <li><a href="welcome/navigation.html" class="selected">主页</a></li>
  </ul>
  <ul>
  <li><a href="service/deal" class="selected">查询</a></li>
  </ul>
  <ul>
  <li><a href="service/loginOut" class="selected">退出</a></li>
  </ul>
	</div>
<%--   
    <c:choose>
    	<c:when test="${_no != null}">
    		Welcome ${_no} <br/>
    	</c:when>
    	<c:otherwise>
    		登录
    	</c:otherwise>
    </c:choose>
--%>
    <form name="toDealForm" action="service/deal" method="post">
    	<input type="submit" value="查询" />
    </form>
    <form name="orderForm" action="service/order" method="post">
    <br/>
    	food_1:<input type="hidden" name="foodId1" value="F001" />
    	订购数量:<input type="text" name="foodNumber1" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M001" id="M001" value="0" /><br/>
    	
    	food_2:<input type="hidden" name="foodId2" value="F002" />
    	订购数量:<input type="text" name="foodNumber2" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M002" id="M002" value="0" /><br/>
    	
    	food_3:<input type="hidden" name="foodId3" value="F003" />
    	订购数量:<input type="text" name="foodNumber3" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M003" id="M003" value="0" /><br/>
    	
    	food_4:<input type="hidden" name="foodId4" value="F004" />
    	订购数量:<input type="text" name="foodNumber4" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M004" id="M004" value="0" /><br/>
    	
    	food_5:<input type="hidden" name="foodId5" value="F005" />
    	订购数量:<input type="text" name="foodNumber5" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M005" id="M005" value="0" /><br/>
    	
    	food_6:<input type="hidden" name="foodId6" value="F006" />
    	订购数量:<input type="text" name="foodNumber6" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M006" id="M006" value="0" /><br/>
    	
    	food_7:<input type="hidden" name="foodId7" value="F007" />
    	订购数量:<input type="text" name="foodNumber7" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M007" id="M007" value="0" /><br/>
    	
    	food_8:<input type="hidden" name="foodId8" value="F008" />
    	订购数量:<input type="text" name="foodNumber8" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M008" id="M008" value="0" /><br/>
    	
    	food_9:<input type="hidden" name="foodId9" value="F009" />
    	订购数量:<input type="text" name="foodNumber9" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M009" id="M009" value="0" /><br/>
    	
    	food_0:<input type="hidden" name="foodId10" value="F010" />
    	订购数量:<input type="text" name="foodNumber10" class="txt" maxlength="10" value=""
				placeholder="请输入订购数量" aria-label="请输入订购数量" aria-required="true" />
    	<input type="hidden" name="M010" id="M010" value="0" /><br/>
    	
    	<input type="submit" value="order" />
    	<input type="hidden" name="no" value="${_no}" />
    </form>
    </div>
  </body>
</html>
