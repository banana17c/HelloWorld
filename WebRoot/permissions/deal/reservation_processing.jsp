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
    
    <title>����Ա��������</title>
    
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
				placeholder="�����ѯ���" aria-label="�����ѯ���" aria-required="true" /><br/>
    	<div>
    		<select name="theWay" id="selectAge">
    		<option value="">��ѯ����</option>
    		<option value="0">δ���</option>
    		<option value="1">���</option>
    		</select>
    	</div>  
    	<input type="submit" value="��ѯ">
    	
    	<p>&nbsp;</p>
    	<c:choose>
    	<c:when test="${output != null && fun:length(output) > 0}">
    	<c:forEach items="${output}" 
    				var="os">
    		<p>�����߱��:${os.peo}<p>
    		<c:forEach items="${os._foodInfo}" 
    					var="food">
    			������ʳ������:${food._foodName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			������ʳ������:${food._foodNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			���״̬(1 -> ���, 0 -> δ���):${food._active}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
    		</c:forEach>
    		<input type="submit" name="" value="${os.peo}�������"/>
    		<hr/>
    	</c:forEach>
    	</c:when>
    	<c:otherwise>
    		<p>û�����в�ѯ�Ķ�����Ϣ</p>
    	</c:otherwise>
    	</c:choose>
    </form>
    <form name="setMoneyForm" action="service/set_money" method="post">
    	<input type="submit" value="��ֵ" />
    </form>
    </div>
  </body>
</html>
