<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎注册肯打鸡会员</title>
    <link type="text/css" href="resources/css/thomestyle.css"
	rel="stylesheet" media="all" />

  </head>
  
  <body style="background-color:#444">
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
	<div class="login-box">
		<div class="mbr-login-box">
  	${info}
  	<form name="registForm" action="service/regist" method="post">
    	帐&nbsp;&nbsp;&nbsp;号:
    	<input type="text" name="no" class="txt" maxlength="11" value=""
				placeholder="请创建你的帐号" aria-label="请创建你的帐号" aria-required="true" /><br/>
				<font color="red"><span>${noError}</span></font><br/><br/>
    	用户名:
    	<input type="text" name="name" class="txt" maxlength="20" value=""
				placeholder="请创建用户名称" aria-label="请创建用户名称" aria-required="true" /><br/>
				<font color="red"><span>${nameError}</span></font><br/><br/>
    	密&nbsp;&nbsp;&nbsp;码:
    	<input type="text" name="password" class="txt" maxlength="20" value=""
				placeholder="请输入你的密码" aria-label="请输入你的密码" aria-required="true" /><br/>
				<font color="red"><span>${passwordError}</span></font><br/><br/>
				<br/>
				<br/>
		<input class="button" type="submit" value="注册" />
		<br/><br/><br/><br/><br/><br/>
		<center><a href="service/login">已有账户？点我登录</a></center>
    </form>
    </div>
	</div>
  </body>
</html>
