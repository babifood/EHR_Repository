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
		<table>
			<tr>
				<td style="width: 180px;padding-left: 10px">
					<input type="hidden" id="arrangement_target_id"/><input type="hidden" id="arrangement_target_type"/>
					<input id="arrangement_select_list" class="easyui-combobox" data-options="valueField: 'id',textField: 'arrangementName',url: 'arrangement/base/findAll'" />
				</td>
				<td style="width: 150px">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="settingArangement()">设置</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#arrangement_select').panel('close')">取消</a>
				</td>
			</tr>
		</table>
		
		
	</div>
</div>
<div class="easyui-dialog" closed="true" id="arrangement_edit" data-options="modal: true" buttons="#arrangement_edit_buttons">
	<div style="margin: 0; padding: 20px 50px; width: 350px;">
		<form action="#" id="arrangement_form">
			<table id="add_arrangement_table" dept-first="true">
				<thead>
					<tr>
						<div style="margin-bottom: 20px; font-size: 18px; border-bottom: 1px solid #ccc;">
							<span style="color: blue;" id="arrangement_type">添加排班</span>
						</div>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>日期：</td>
						<td><input type="hidden" id="arrangement_id" /> <input
							type="text" name="date" id="attendance_datebox"
							class="easyui-validatebox textbox easyui-datebox"
							required="required" data-options="required:true"></td>
						<td><span style="color: red" id="attendance_date_message"></span></td>
					</tr>
					<tr>
						<td>是否考勤：</td>
						<td><input type="text" name="isAttend" value="1"
							id="attendance_isAttend" class="easyui-validatebox textbox"></td>
						<td></td>
					</tr>
					<tr class="attendance_time">
						<td>开始时间：</td>
						<td><input type="text"
							data-options="min:'07:00',showSeconds:false"
							id="attendance_startTime" class="textbox easyui-timespinner"></td>
						<td></td>
					</tr>
					<tr class="attendance_time">
						<td>结束时间：</td>
						<td><input type="text"
							data-options="min:'07:00',showSeconds:false"
							id="attendance_endTime" class="textbox easyui-timespinner"></td>
						<td></td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input id="attendance_remark" type="text" name="remark"
							class="easyui-validatebox textbox"></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
<div id="arrangement_edit_buttons" style="text-align: center;">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSepcialArrangement()" style="width: 90px;">保存</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#arrangement_edit').dialog('close')" style="width: 90px;">取消</a>
</div>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/Arrangement1.js"></script>
