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

<title>欢迎登录肯打鸡</title>

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
			<li><a href="welcome/navigation.html">我的主页</a>
			</li>
			<li><a href="welcome/navigation.html">你的主页</a>
			</li>
			<li><a href="welcome/navigation.html">他的主页</a>
			</li>
		</ul>
		<ul class="account_desc">
			<li><a href="welcome/general.html">分享</a>
			</li>
			<li><a href="welcome/general.html">邀请</a>
			</li>
			<li><a href="welcome/general.html">联系</a>
			</li>
			<li><a href="welcome/general.html">客服</a>
			</li>
			<li><a href="welcome/navigation.html">退出</a>
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
				用 户: <input type="text" name="no" class="txt" maxlength="11" value=""
				placeholder="输入你的账户" aria-label="输入你的账户" aria-required="true" /><br/>
				<br/>
				密 码: <input type="password" name="password" class="txt" maxlength="20" value=""
				placeholder="输入你的密码" aria-label="输入你的密码" aria-required="true" /><br/>
				<br/>
				<br/>
				<input class="button" type="submit" value="login" />
				<br/><br/><br/><br/><br/><br/>
				<center><a href="service/regist">没有账户？点我注册</a></center>
			</form>
		</div>
	</div>
</body>
</html>
