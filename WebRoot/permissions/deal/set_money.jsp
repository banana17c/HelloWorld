<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�˻���ֵ</title>
    
    <link type="text/css" href="resources/css/thomestyle.css" rel="stylesheet" media="all" />

  </head>
  
  <body style="background-color:#444">
  <div class="content">
		<nav class="wrap">
		<ul class="nav_list">
			<li><a href="welcome/navigation.html">�ҵ���ҳ</a>
			</li>
			<li><a href="welcome/navigation.html">�����ҳ</a>
			</li>
			<li><a href="welcome/navigation.html">������ҳ</a>
			</li>
		</ul>
		<ul class="account_desc">
			<li><a href="welcome/general.html">����</a>
			</li>
			<li><a href="welcome/general.html">����</a>
			</li>
			<li><a href="welcome/general.html">��ϵ</a>
			</li>
			<li><a href="welcome/general.html">�ͷ�</a>
			</li>
			<li><a href="service/loginOut">�˳�</a>
			</li>
		</ul>
		</nav>
	</div>
	<div class="clear"></div>
	<div class="login-box">
		<div class="mbr-login-box">
  	<font color="red"><span>${prompt}</span></font>
  	<form name="moneyForm" action="service/money" method="post">
    	�ʺ�:
    	<input type="text" name="moneyNo" class="txt" maxlength="11" value=""
				placeholder="�������ֵ�ʺ�" aria-label="�������ֵ�ʺ�" aria-required="true" /><br/><br/><br/>
    	��ֵ:
    	<input type="text" name="setMoney" class="txt" maxlength="20" value=""
				placeholder="�������ֵ���" aria-label="�������ֵ���" aria-required="true" /><br/><br/>
				<font color="red"><span>${nameError}</span></font><br/><br/>
				<br/>
				<br/>
		<input class="button" type="submit" value="ȷ�ϳ�ֵ" />
    </form>
    </div>
	</div>
  </body>
</html>
