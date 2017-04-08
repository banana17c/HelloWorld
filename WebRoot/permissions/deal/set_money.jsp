<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>账户充值</title>
    
    <link type="text/css" href="resources/css/thomestyle.css" rel="stylesheet" media="all" />

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
			<li><a href="service/loginOut">退出</a>
			</li>
		</ul>
		</nav>
	</div>
	<div class="clear"></div>
	<div class="login-box">
		<div class="mbr-login-box">
  	<font color="red"><span>${prompt}</span></font>
  	<form name="moneyForm" action="service/money" method="post">
    	帐号:
    	<input type="text" name="moneyNo" class="txt" maxlength="11" value=""
				placeholder="请输入充值帐号" aria-label="请输入充值帐号" aria-required="true" /><br/><br/><br/>
    	充值:
    	<input type="text" name="setMoney" class="txt" maxlength="20" value=""
				placeholder="请输入充值金额" aria-label="请输入充值金额" aria-required="true" /><br/><br/>
				<font color="red"><span>${nameError}</span></font><br/><br/>
				<br/>
				<br/>
		<input class="button" type="submit" value="确认充值" />
    </form>
    </div>
	</div>
  </body>
</html>
