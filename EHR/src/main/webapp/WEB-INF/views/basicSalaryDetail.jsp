<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>薪资明细列表</title>
</head>
<body>
	<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
		<table class="easyui-datagrid" id="basic_salaryDetail_datagrid"></table>
		<div id="basic_salaryDetail_datagrid_tools">
			<div style="margin-left: 5px">
				<div style="margin: 5px">
					机构名称：<input type="text" class="textbox" id="basic_salary_detail_organzationName" oninput="loadBasicSalaryDetail()"> &nbsp;&nbsp;&nbsp;
		      		部门名称：<input type="text" class="textbox" id="basic_salary_detail_deptName" oninput="loadBasicSalaryDetail()"> &nbsp;&nbsp;&nbsp;
		      		科室名称：<input type="text" class="textbox" id="basic_salary_detail_officeName" oninput="loadBasicSalaryDetail()"> &nbsp;&nbsp;&nbsp;
				</div>
	      		<div style="margin-left: 5px">
					员工工号：<input type="text" class="textbox" id="basic_salary_detail_number" oninput="loadBasicSalaryDetail()"> &nbsp;&nbsp;&nbsp;
	      			员工姓名：<input type="text" class="textbox" id="basic_salary_detail_name" oninput="loadBasicSalaryDetail()"> &nbsp;&nbsp;&nbsp;
		      		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchBasicSalaryDetail()">重置</a>
					<%-- 					<shiro:hasPermission name="basic_salaryDetail:import"> --%>
						<a href="javascript:void(0)" class="easyui-menubutton"data-options="menu:'#basic_salary_detail_menubutton',iconCls:'icon-edit'">导入</a>
						<div id="basic_salary_detail_menubutton" style="width:150px;">   
						    <div data-options="iconCls:'icon-remove'" onclick="exportBasicSalaryDetail()">下载模板</div>
						    <div data-options="iconCls:'icon-remove'" onclick="basicSalaryDetailImport()">导入</div> 
						</div> 
<%-- 					</shiro:hasPermission> --%>
				</div>
			</div>
		</div>
	</div>
	<div class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 130px;" data-options="modal:true" id="basic_salaryDetail_dialog" closed="true" buttons="#dormitory_cost_dialog_buttons">
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/excel.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/basicSalaryDetail.js"></script>
</body>
</html>