<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui-js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui-js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css">
<title>用户登录</title>
</head>

<body>
	<div class="second_body">
		<div class="logo"></div>
		<div class="title-zh">企业人力资源系统</div>
		<div class="title-en" style="">Electronic Human Resource System</div>
		<div class="message" data-bind="html:message"></div>
		<form id="loginform" name="loginform" action="${pageContext.request.contextPath}/login" method="post">
			<table border="0" style="width: 300px;">
				<tr>
					<td style="white-space: nowrap; padding-bottom: 5px; width: 55px;">用户名：</td>
					<td colspan="2"><input type="text" id="username" name="username" class="login" /></td>
				</tr>
				<tr>
					<td class="lable" style="white-space: nowrap; letter-spacing: 0.5em; vertical-align: middle">密码：</td>
					<td colspan="2"><input type="password" id="password" name="password" class="login" onkeydown="loginButton()"/></td>
				</tr>
				<tr>
					<td><input type="hidden" id="msg" value="${status}"/></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="3" style="text-align: center"><input type="button"
						value="登录" class="login_button" onclick="loginButton()"/> 
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.cookie.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.easyui.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>