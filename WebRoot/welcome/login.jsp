<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
		    + request.getServerName() + ":" + request.getServerPort()
		    + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>��ӭ��¼�ϴ�</title>

<link type="text/css" href="resources/css/thomestyle.css"
	rel="stylesheet" media="all" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

</head>

<body>
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
	<hr/>
	<div class="login-box">
		<div class="mbr-login-box">
			${info}
			<form name="loginForm" action="service/login" method="post">
				�� ��: <input type="text" name="no" class="txt" maxlength="11" value=""
				placeholder="��������˻�" aria-label="��������˻�" aria-required="true" /><br/>
				<br/>
				�� ��: <input type="password" name="password" class="txt" maxlength="20" value=""
				placeholder="�����������" aria-label="�����������" aria-required="true" /><br/>
				<br/>
				<br/>
				<input class="button" type="submit" value="login" />
				<br/><br/><br/><br/><br/><br/>
				<center><a href="service/regist">û���˻�������ע��</a></center>
			</form>
		</div>
	</div>
</body>
</html>
