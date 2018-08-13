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
		<table class="easyui-datagrid" id="salaryDetail_datagrid"></table>
		<div id="salaryDetail_datagrid_tools">
			<div>
				<div >
					<shiro:hasPermission name="salaryDetail:export">
						<a href="javascript:void(0)" class="easyui-menubutton"data-options="menu:'#salary_detail_menubutton',iconCls:'icon-edit'">导入/导出</a>
						<div id="salary_detail_menubutton" style="width:150px;">   
						    <div data-options="iconCls:'icon-remove'" onclick="exportSalaryDetail()">导出</div>   
						</div> 
					</shiro:hasPermission>
					<shiro:hasPermission name="salaryDetail:calculate">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="salaryDetail_calculate" onclick="salaryCalculation(1)">试算</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="salaryDetail:accounting">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="salaryDetail_accounting" onclick="salaryCalculation(2)">核算</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="salaryDetail:archive">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="salaryDetail_archive" onclick="salaryCalculation(3)">归档</a>
					</shiro:hasPermission>
				</div>
			</div>
			<div style="margin-left: 5px">
	      		<div style="margin-left: 5px">
					员工工号：<input type="text" class="textbox" id="salary_detail_number" oninput="loadConditionSalaryDetail()"> &nbsp;&nbsp;&nbsp;
	      			员工姓名：<input type="text" class="textbox" id="salary_detail_name" oninput="loadConditionSalaryDetail()"> &nbsp;&nbsp;&nbsp;
				</div>
				<div style="margin: 5px">
					机构名称：<input type="text" class="textbox" id="salary_detail_organzationName" oninput="loadConditionSalaryDetail()"> &nbsp;&nbsp;&nbsp;
		      		部门名称：<input type="text" class="textbox" id="salary_detail_deptName" oninput="loadConditionSalaryDetail()"> &nbsp;&nbsp;&nbsp;
		      		科室名称：<input type="text" class="textbox" id="salary_detail_officeName" oninput="loadConditionSalaryDetail()">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/salaryDetail.js">
</body>
</html>