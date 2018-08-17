<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/NewUsers.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/textbox.css"/>
<title>新增用户</title>
</head>
<body>
	<div id="role_user_menu">   
	    <div title="添加角色">   
	        <table id="role_tbo"></table>  
	        <div id="role_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<shiro:hasPermission name="role:add">
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRole()">添加</a>
	        		</shiro:hasPermission>
	        		<shiro:hasPermission name="role:edit">
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">修改</a>
	        		</shiro:hasPermission>
	        		<shiro:hasPermission name="role:remove">
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRole()">删除</a>
	        		</shiro:hasPermission>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		角色名称：<input type="text" class="textbox" id="search_role_name" name="search_role_name" style="width: 110px;"/>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchRole()">查询</a>
	        	</div>
	        </div>
	        <div id="role_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#role_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">角色信息</span></div>
			        <div style="margin-bottom: 10px;">
			        	角色名称：<input type="text" id="role_name" name="role_name" class="textbox"  onblur="noBlur()" style="width: 180px;"/>
			        		   <span id="role_name_span" style="color: red"></span>	
			        </div>
			        <div style="margin-bottom: 10px;">
			        	角色描述：<input type="text" id="role_desc" name="role_desc" class="textbox"  onblur="noBlur()" style="width: 180px;"/>
			       			   <span id="role_desc_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	所属机构：<input type="text" id="role_organization" name="role_organization" style="width: 180px;"/>
			       			   <span id="role_organization_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	是否启用：是<input type="radio" id="role_radio_yes" name="role_radio" value="1" />否<input type="radio" id="role_radio_no" name="role_radio" value="0"/>
			        </div>
		        </div>
		     </div>	  		
	        <div id="role_dlg_buttons" style="text-align: center;">
	        	<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRole()" style="width: 90px;">保存</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#role_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div>
	    </div>   
	    <div title="添加用户">   
	        <table id="user_tbo"></table>  
	        <div id="user_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<shiro:hasPermission name="user:add">
	        			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()">添加</a>
	        		</shiro:hasPermission>
	        		<shiro:hasPermission name="user:add">
	        			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
	        		</shiro:hasPermission>
	        		<shiro:hasPermission name="user:add">
	        			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除</a>
	        		</shiro:hasPermission>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		用户账号：<input type="text" class="textbox" id="search_user_name" name="search_user_name" style="width: 110px;"/>
	        		用户名称：<input type="text" class="textbox" id="search_show_name" name="search_show_name" style="width: 110px;"/>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchUser()">查询</a>
	        	</div>
	        </div>  
	        <div id="user_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#user_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">角色信息</span></div>
			        <div style="margin-bottom: 10px;">
			        	用户账号：<input type="text" id="user_name" name="user_name" class="textbox"  onblur="noBlurUser()" style="width: 180px;"/>
			        		   <span id="user_name_span" style="color: red"></span>	
			        </div>
			        <div style="margin-bottom: 10px;">
			        	用户密码：<input type="password" id="password" name="password" class="textbox"  onblur="noBlurUser()" style="width: 180px;"/>
			       			   <span id="password_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	用户名称：<input type="text" id="show_name" name="show_name" class="textbox"  onblur="noBlurUser()" style="width: 180px;"/>
			       			   <span id="show_name_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	用户角色：<input id="user_role" name="user_role" style="width: 180px;"/>  
			       			   <span id="user_role_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	用户手机：<input type="text" id="phone" name="phone" class="textbox" style="width: 180px;"/>
			       			   <span id="phone_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	用户邮箱：<input type="text" id="e_mail" name="e_mail" class="textbox" style="width: 180px;"/>
			       			   <span id="e_mail_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	是否启用：是<input type="radio" id="user_radio_yes" name="user_radio" value="1" />否<input type="radio" id="user_radio_no" name="user_radio" value="0"/>
			        </div>
		        </div>
		     </div>	  		
	        <div id="user_dlg_buttons" style="text-align: center;">
	        	<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width: 90px;">保存</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#user_dog').dialog('close')" style="width: 90px;">取消</a>
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
		     <div style="width: 100%;text-align: center;">
			     <shiro:hasPermission name="menu:save">
			     	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMenuRole()" style="width: 80px;">保存</a>  
			     </shiro:hasPermission>
		     </div>      
	    </div>   
	</div>  
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/NewUsers.js"></script>
</body>
</html>