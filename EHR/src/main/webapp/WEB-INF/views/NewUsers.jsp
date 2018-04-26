<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/NewUsers.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/textbox.css"/>
<title>新增用户</title>
</head>
<body>
	<div id="role_user_menu" class="easyui-tabs" data-options="fit:true">   
	    <div title="添加角色">   
	        <table id="role_tbo"></table>  
	        <div id="role_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="">删除</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="">保存</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="">取消</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		用户账号：<input type="text" class="textbox" id="user_name" name="user_name" style="width: 110px;"/>
	        		用户名称：<input type="text" class="textbox" id="show_name" name="show_name" style="width: 110px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="">查询</a>
	        	</div>
	        </div>
	    </div>   
	    <div title="添加用户">   
	        <table id="user_tbo"></table>  
	        <div id="user_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="">删除</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="">保存</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="">取消</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		用户账号：<input type="text" class="textbox" id="user_name" name="user_name" style="width: 110px;"/>
	        		用户名称：<input type="text" class="textbox" id="show_name" name="show_name" style="width: 110px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="">查询</a>
	        	</div>
	        </div>    
	    </div>   
	    <div title="菜单授权">   
	    	<div style="width: 100%;height: 93%;">
		        <div id="menu_div" style="float: left;width: 40%;height: 100%;padding: 0px;margin: 0px;">
		        	<dir class="easyui-panel" title="系统菜单"  data-options="fit:true,"style="padding: 0 0 0 20px;margin: 0px;">
		        		<ul id="menu_ul"></ul>
		        	</dir>
		        </div> 
		        <div id="role_div" style="float: right;width: 60%;height: 100%;">
		        	<div class="easyui-panel" title="系统角色"  data-options="fit:true,">
		        		<table id="menu_tbo"></table>  
		        	</div>
		        </div>  
		     </div>
		     <div style="width: 100%;text-align: center;line-height: 50px;">
		     	<a href="#" class="easyui-linkbutton" iconCls="icon-save" style="width: 80px;">保存</a>  
		     </div>      
	    </div>   
	</div>  
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/NewUsers.js"></script>
</body>
</html>