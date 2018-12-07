<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车间考勤</title>
</head>
<body>
	<div ip="workshop_panel" class="easyui-panel" data-options="fit:true,border:false">
		<table id="workshop_grid"></table>
		<div id="workshop_tbar">
			<div style="margin-bottom: 5px;">
		        <a href="javascript:void(0)" id="workshop_btn" class="easyui-menubutton"data-options="menu:'#workshop_menu',iconCls:'icon-edit'">导入/导出</a>
				<div id="workshop_menu" style="width:150px;">
					<div data-options="iconCls:'icon-load'" onclick="exportWorkshop(0)">下载模板</div>   
					<div data-options="iconCls:'icon-redo'" onclick="WorkshopImport()">导入</div>
					<div data-options="iconCls:'icon-remove'" onclick="exportWorkshop(1)">导出</div>
				</div> 		
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	公司名称：<input type="text" class="textbox" id="workshop_Comp" name="workshop_Comp" style="width: 110px;"/>
	        	中心机构：<input type="text" class="textbox" id="workshop_Organ" name="workshop_Organ" style="width: 110px;"/>
	        	部门名称：<input type="text" class="textbox" id="workshop_Dept" name="workshop_Dept" style="width: 110px;"/>
	        	员工工号：<input type="text" class="textbox" id="workshop_WorkNum" name="workshop_WorkNum" style="width: 110px;"/>
	        	员工姓名：<input type="text" class="textbox" id="workshop_UserName" name="workshop_UserName" style="width: 110px;"/>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="workshopReset()">重置</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="workshopSearch()">查询</a>
	        </div>
		</div>
	</div>
	<div id="workshop_dlg" class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 130px;" data-options="modal:true" closed="true" buttons="#workshop_dialog_buttons"></div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/excel.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/WorkshopClocked.js"></script>
</body>
</html>