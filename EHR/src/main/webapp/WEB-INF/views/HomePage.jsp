<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui-js/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui-js/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/HomePage.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/textbox.css"/>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/HomePage.js"></script>
<title>首页</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:false,noheader:true" style="height:60px;background-color:#ECF5FF;">
		<div id="logo"><span>企业人力资源系统</span></div>
		<div id="logoout">您好：${activeUser.show_name}/<a href="#" onclick="logoout()">安全退出</a></div>
	</div>   
    <div data-options="region:'south',split:false,noheader:true" style="height:30px;line-height: 30px;text-align: center;background-color:#ECF5FF;">
    	版权所有 &copy 2018 中饮食品科技股份有限公司
    </div>   
    <div data-options="region:'west',title:'导航菜单',split:true" style="width:250px;">
    	<ul id="menu"></ul>
    </div>   
    <div data-options="region:'center'" style="overflow: hidden;">
	    <div id="tabs">
	    	<div title="起始页" style="display: block;">
			    <div id="beginPage" class="easyui-layout" data-options="fit:true">   
				    <div data-options="region:'west',split:true,noheader:true,border:false," style="width:50%;">
				    	<div id="left_layout" class="easyui-layout" data-options="fit:true">   
						    <div data-options="region:'north',title:'员工生日提醒',split:true,collapsible:false," style="height:50%;"></div>   
						    <div data-options="region:'center',title:'员工转正提醒',split:true," style="height:50%;"></div>   
						</div>  
				    </div>   
				    <div data-options="region:'center',split:true,noheader:true,border:false," style="width:50%;">
				    	<div id="right_layout" class="easyui-layout" data-options="fit:true">   
						    <div data-options="region:'north',title:'证件到期提醒',split:true,collapsible:false," style="height:50%;"></div>   
						    <div data-options="region:'center',title:'员工入离职统计',split:true," style="height:50%;"></div>   
						</div>  
				    </div>   
				</div>  
		    </div>
	    </div>
    </div>  
</body>
</html>