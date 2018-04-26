<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui-js/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui-js/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/textbox.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<title>用户登录</title>
</head>

<body>
<div id="login">
	<p>用户名：<input type="text" id="userName" class="textbox" data-options="required:true"/></p>
	<p>密&nbsp;&nbsp;&nbsp;码：<input type="password" id ="password" class="textbox" data-options="required:true"></p>
</div>
<div id="login-btn">
	<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">登录</a> 
</div>
<!--<a href="${pageContext.request.contextPath}/login">跳转</a>-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>