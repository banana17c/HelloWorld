<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员操作界面</title>
    
    <link type="text/css" href="resources/css/templatemo_style.css" rel="stylesheet" />

  </head>
  
  <body id="home">

	<div id="templatemo_header_wrapper">
		<div id="site_title">
			<h1>
				<a href="welcome/navigation.html"></a>
			</h1>
		</div>
    <form name="dealForm" action="service/select" method="post">
    	<input type="text" name="no" class="txt" maxlength="11" value=""
				placeholder="输入查询编号" aria-label="输入查询编号" aria-required="true" /><br/>
    	<div>
    		<select name="theWay" id="selectAge">
    		<option value="">查询所有</option>
    		<option value="0">未完成</option>
    		<option value="1">完成</option>
    		</select>
    	</div>  
    	<input type="submit" value="查询">
    	
    	<p>&nbsp;</p>
    	<c:choose>
    	<c:when test="${output != null && fun:length(output) > 0}">
    	<c:forEach items="${output}" 
    				var="os">
    		<p>订餐者编号:${os.peo}<p>
    		<c:forEach items="${os._foodInfo}" 
    					var="food">
    			订购的食物名称:${food._foodName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			订购的食物数量:${food._foodNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			完成状态(1 -> 完成, 0 -> 未完成):${food._active}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
    		</c:forEach>
    		<input type="submit" name="" value="${os.peo}交易完成"/>
    		<hr/>
    	</c:forEach>
    	</c:when>
    	<c:otherwise>
    		<p>没有所有查询的订单信息</p>
    	</c:otherwise>
    	</c:choose>
    </form>
    <form name="setMoneyForm" action="service/set_money" method="post">
    	<input type="submit" value="充值" />
    </form>
    </div>
  </body>
</html>
