<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
	.arrangement_list{
		float: left;
		margin: 1px;
	}
</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/calendarAll.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/skin.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/fontSize12.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/calendar/css/calendar.css" />
<div id="arrangement_tabs" class="easyui-tabs" data-options="fit:true">
   	<div title="排班列表"  style="display:none;">
    	<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="arrangement_base_time"></table>
			<div style="margin-bottom: 5px;" id="base_time_tools">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addArrangementBaseTime()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeArrangementBaseTime()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveArrangementBaseTime()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelArrangementBaseTime()">取消</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="foundArrangement()">设置排班</a>     		
	        </div>
		</div>
    </div>   
	<div title="排班设置" style="display:none;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',title:'部门信息',split:true" style="width:20%;">
				<div style="width: 100%;height: 60%;border-bottom: 2px solid #eee;overflow:auto" id="arrangement_cancel_">
					<ul id="arrangement_dept_tree"></ul>
				</div>
				<div style="width: 100%;height: 39%">
					<table class="easyui-datagrid" id="emploee_arrangement" data-options="fit:true"></table>
				</div>
			</div>
	   		<div data-options="region:'center',title:'对应排班'" style="padding:5px;background:#eee;">
	   			<jsp:include page="targetArrangement.jsp"></jsp:include>
	   		</div>
		</div>
    </div>
</div>
<div class="easyui-dialog" closed="true" id="arrangement_calender" data-options="modal: true" style="height: 100%;width: 100%">
	<jsp:include page="arrangementCalender.jsp"></jsp:include>
</div>
<div class="easyui-dialog" id="arrangement_select" closed="true" title="排班设置" data-options="modal:true">
	<div>
		<input type="hidden" id="arrangement_target_id"/><input type="hidden" id="arrangement_target_type"/>
		<input id="arrangement_select_list" class="easyui-combobox" data-options="valueField: 'id',textField: 'arrangementName',url: 'arrangement/base/findAll'" />
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="settingArangement()">设置</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#arrangement_select').panel('close')">取消</a>
	</div>
</div>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/Arrangement1.js"></script>
