<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打卡记录</title>
</head>
<body>
<div class="easyui-panel" data-options="fit : true">  
	<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
		<table class="easyui-datagrid" id="punch_time_list"></table>
		<div style="margin-bottom: 5px;" id="punch_time_list_tools">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPunchTimes()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updatePunchTimes()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removePunchTime()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="syncPunchTimeInfo()">同步打卡记录</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePunchTime()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelPunchTime()">取消</a>
			</div>
        	<div style="padding: 0 0 0 7px;color: #333;padding-left: 10px">
	      		员工工号：<input type="text" class="textbox" id="punch_time_number" oninput="reloadPunchTime()"> &nbsp;&nbsp;&nbsp;
	      		员工姓名：<input type="text" class="textbox" id="punch_time_name" oninput="reloadPunchTime()">
      		</div>
        </div>
	</div>
</div>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/punchTime.js">
</body>
</html>