<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��ӭע��ϴ򼦻�Ա</title>
    <link type="text/css" href="resources/css/thomestyle.css"
	rel="stylesheet" media="all" />

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
			<li><a href="welcome/navigation.html">�˳�</a>
			</li>
		</ul>
		</nav>
	</div>
	<div class="clear"></div>
	<div class="login-box">
		<div class="mbr-login-box">
  	${info}
  	<form name="registForm" action="service/regist" method="post">
    	��&nbsp;&nbsp;&nbsp;��:
    	<input type="text" name="no" class="txt" maxlength="11" value=""
				placeholder="�봴������ʺ�" aria-label="�봴������ʺ�" aria-required="true" /><br/>
				<font color="red"><span>${noError}</span></font><br/><br/>
    	�û���:
    	<input type="text" name="name" class="txt" maxlength="20" value=""
				placeholder="�봴���û�����" aria-label="�봴���û�����" aria-required="true" /><br/>
				<font color="red"><span>${nameError}</span></font><br/><br/>
    	��&nbsp;&nbsp;&nbsp;��:
    	<input type="text" name="password" class="txt" maxlength="20" value=""
				placeholder="�������������" aria-label="�������������" aria-required="true" /><br/>
				<font color="red"><span>${passwordError}</span></font><br/><br/>
				<br/>
				<br/>
		<input class="button" type="submit" value="ע��" />
		<br/><br/><br/><br/><br/><br/>
		<center><a href="service/login">�����˻������ҵ�¼</a></center>
    </form>
    </div>
	</div>
  </body>
</html>
