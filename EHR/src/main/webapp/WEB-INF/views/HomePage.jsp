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
<!--<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/echarts.common.min.js"></script> -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/HomePage.js"></script>
<title>首页</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:false,noheader:true" style="height:60px;background-color:#ECF5FF;">
		<div id="logo"><span>企业人力资源系统</span></div>
		<div id="userinfo">您好：${activeUser.show_name}</div>
		<div id="outOrUpdate">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="logoout()" style="color: blue;">安全退出</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-lock" 	plain="true" onclick="updatePassword()" style="color: blue;">修改密码</a>
		</div>
	</div>   
    <div data-options="region:'south',split:false,noheader:true" style="height:32px;line-height: 30px;text-align: center;background-color:#ECF5FF;">
    	版权所有 &copy 2018 中饮食品科技股份有限公司
    </div>   
    <div data-options="region:'west',title:'导航菜单',split:true" style="width:250px;">
		<div id="RightAccordion" class="easyui-accordion" ></div>
    </div>   
    <div data-options="region:'center'" style="overflow: hidden;">
	    <div id="tabs">
	    	<div title="起始页" style="display: block;">
			    <div id="beginPage" class="easyui-layout" data-options="fit:true">   
				    <div data-options="region:'west',split:true,noheader:true,border:false," style="width:50%;">
				    	<div id="left_layout" class="easyui-layout" data-options="fit:true">   
						    <div data-options="region:'north',title:'员工转正提醒',split:true," style="height:50%;">
						    	<table id="zhuanZheng_grid"></table>  
						    	<!-- <div id="zhuanZheng_tbar">
							    	工号：<input type="text" class="textbox" id="zz_p_number" name="zz_p_number" style="width: 110px;"/>
						        	姓名：<input type="text" class="textbox" id="zz_p_name" name="zz_p_name" style="width: 110px;"/>
						        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetZhuanZhengInFo()">重置</a>
						        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchZhuanZhengInFo()">查询</a>
						   		</div>-->
						    </div>
						    <div data-options="region:'center',title:'员工生日提醒',split:true,collapsible:false," style="height:50%;">
						    	<table id="birthday_grid"></table>  
						    	<!-- <div id="birthday_tbar">
							    	工号：<input type="text" class="textbox" id="birthday_p_number" name="search_p_number" style="width: 110px;"/>
						        	姓名：<input type="text" class="textbox" id="birthday_p_name" name="search_p_name" style="width: 110px;"/>
						        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetBirthdayInFo()">重置</a>
						        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchBirthdayInFo()">查询</a>
						   		</div> -->
						    </div>    
						</div>  
				    </div>   
				    <div data-options="region:'center',split:true,noheader:true,border:false," style="width:50%;">
				    	<div id="right_layout" class="easyui-layout" data-options="fit:true">   
						    <div data-options="region:'north',title:'合同到期续签提醒',split:true," style="height:50%;">
						    	<table id="contractRemind_grid"></table>
						    </div>
						    <div data-options="region:'center',title:'证件到期提醒',split:true,collapsible:false," style="height:50%;">
						    	<table id="certificatenRemind_grid"></table>
						    </div> 
						</div>  
				    </div>   
				</div>  
		    </div>
	    </div>
    </div>  
    <div id="updatePsd_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#updatePsd_dog_buttons" style="width: 450px;">
		<form id="updatePsd_form" method="post">
			<div style="margin: 0;padding: 20px 50px;">
				<div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">修改密码</span></div>
				    <div style="margin-bottom: 10px;">
				    	<label for="psd_account">用户账号：</label>
				        <input type="text" name="psd_account" class="easyui-validatebox" data-options="required:true" validType="username" style="width: 180px;"/>
				    </div>
				    <div style="margin-bottom: 10px;">
				    	<label for="psd_original">原始密码：</label>
				        <input type="password"  name="psd_original" class="easyui-validatebox" data-options="required:true"  style="width: 180px;"/>
				    </div>
				    <div style="margin-bottom: 10px;">
				    	<label for="psd_new">新改密码：</label>
				        <input type="password" id="psd_new" name="psd_new" class="easyui-validatebox" data-options="required:true"  validType="pasd" style="width: 180px;"/>
				    </div>
				    <div style="margin-bottom: 10px;">
				    	<label for="psd_affirm">确认密码：</label>
				        <input type="password" name="psd_affirm"  class="easyui-validatebox"  required="required" validType="equals['#psd_new']"  style="width: 180px;"/>  
				    </div>
			 </div>
		 </form>
	</div>	
	<div id="updatePsd_dog_buttons" style="text-align: center;">
	     <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateNewPsd()" style="width: 90px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updatePsd_dog').dialog('close')" style="width: 90px;">取消</a>
	</div> 	      	  		
</body>
</html>