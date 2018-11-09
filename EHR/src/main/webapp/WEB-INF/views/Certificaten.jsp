<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>证件管理</title>
</head>
<body>
	<div ip="certificaten_panel" class="easyui-panel" data-options="fit:true,border:false,noheader:true">
		<table id="certificaten_grid"></table>
		<div id="certificaten_tbar">
			<div style="margin-bottom: 5px;">
				<shiro:hasPermission name="certificate:add">
		        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCertificaten()">添加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="certificate:remove">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeCertificaten()">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="certificate:save">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptCertificaten()">保存</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="certificate:undo">	
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectCertificaten()">取消</a>        		
		        </shiro:hasPermission>
		        	<a href="javascript:void(0)" id="certificaten_btn" class="easyui-menubutton"data-options="menu:'#certificaten_menu',iconCls:'icon-edit'">导入/导出</a>
					<div id="certificaten_menu" style="width:150px;">
						<!--<shiro:hasPermission name="performance:import">-->
						    <div data-options="iconCls:'icon-load'" onclick="exportCertificaten(0)">下载模板</div>   
						    <div data-options="iconCls:'icon-redo'" onclick="certificatenImport()">导入</div>
					    <!--</shiro:hasPermission>-->
					    <!--<shiro:hasPermission name="performance:export">-->
						    <div data-options="iconCls:'icon-remove'" onclick="exportCertificaten(1)">导出</div>
					    <!--</shiro:hasPermission>-->  
					</div> 
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	工号：<input type="text" class="textbox" id="search_p_number" name="search_p_number" style="width: 110px;"/>
	        	姓名：<input type="text" class="textbox" id="search_p_name" name="search_p_name" style="width: 110px;"/>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetCertificaten()">重置</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchCertificaten()">查询</a>
	        </div>
		</div>
	</div>
	<div id="certificaten_dlg" class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 130px;" data-options="modal:true" closed="true" buttons="#certificaten_dialog_buttons"></div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/validatebox.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/excel.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/Certificaten.js"></script>
</body>
</html>