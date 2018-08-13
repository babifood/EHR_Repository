<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
	<table class="easyui-datagrid" id="base_salary_list"></table>
	<div id="base_salary_list_tools">
		<div style="margin-bottom: 5px;">
			<shiro:hasPermission name="baseSalary:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBaseSalaryInfo()">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="baseSalary:remove">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBaseSalaryInfo()">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="baseSalary:operate">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveBaseSalaryInfo()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelBaseSalaryInfo()">取消</a>
			</shiro:hasPermission>
		</div>
      	<div style="padding: 0 0 0 7px;color: #333;padding-left: 10px">
      		员工工号：<input type="text" class="textbox" id="base_salary_number" oninput="reloadBaseSalaryInfo()"> &nbsp;&nbsp;&nbsp;
      		员工姓名：<input type="text" class="textbox" id="base_salary_name" oninput="reloadBaseSalaryInfo()">
      	</div>
      </div>
</div>
<!-- <div id="base_salary_member_bar" class="datagrid-toolbar"> -->
<!-- 	工号：<input type="text" class="textbox" id="base_salary_p_number" style="width: 110px;" oninput="searchBaseSalaryPerson()"/> -->
<!-- 	姓名：<input type="text" class="textbox" id="base_salary_p_name" style="width: 110px;" oninput="searchBaseSalaryPerson()"/> -->
<!-- </div> -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/baseSalary.js"></script>