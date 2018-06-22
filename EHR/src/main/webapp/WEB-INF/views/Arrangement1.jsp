<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css" >
	.target_select{
		float: left;
		margin-left: 10px;
	}
</style>
<div id="arrangement_tabs" class="easyui-tabs" data-options="fit:true">
   	<div title="排班"  style="display:none;">
    	<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
    		<table class="easyui-datagrid" id="arrangement_list"></table>
    		<div style="margin-bottom: 5px;" id="arrangement_tools">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addArrangement()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeArrangement()">删除</a>
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveArrangement()">保存</a> -->
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelArrangement()">取消</a>        		 -->
	        </div>
   		</div>
    	<div class="easyui-dialog" closed="true" id="arrangement_operate" data-options="modal: true">
			<div style="margin: 0;padding: 20px 50px;width: 350px;">
				<form action="#">
					<table id="add_arrangement_table" dept-first="true">
						<thead>
							<tr><div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">添加排班</span></div></tr>
						</thead>
						<tbody>
							<tr>
								<td>日期：</td><td><input type="text" name="date" id="attendance_datebox" class="easyui-validatebox textbox" data-options="required:true"></td><td><span style="color:red" id="attendance_date_message"></span></td>
							</tr>
							<tr>
								<td>班次：</td><td><input id="arrangement_time" name="baseTimeId" value="" class="easyui-validatebox textbox" data-options="required:true"></td><td><span style="color:red" id="attendance_baseTimeId_message"></span></td>
							</tr>
							<tr>
								<td>班次简述：</td><td><select class="easyui-combobox easyui-validatebox" style="width: 164px" name="attendance" id="attendance_select">   
													    <option value="大小周">大小周</option>   
													    <option value="1.5休">1.5休</option>   
													    <option value="双休">双休</option>
													    <option value="单休">单休</option>   
													</select></td><td><span style="color:red" id="attendance_message"></span></td>
							</tr>
							<tr>
								<td>考勤机构/人员：</td><td><input type="text" id="arrangement_target" name="target_id" class="easyui-validatebox textbox" data-options="required:true"></td><td><span style="color:red" id="attendance_target_id_message"></span></td>
							</tr>
							<tr>
								<td>是否考勤：</td><td>是:<input type="radio" name="isAttend" value="1" checked>  &nbsp;否:<input type="radio" name="isAttend" value="0"></td><td></td>
							</tr>
							<tr>
								<td>备注：</td><td><input type="text" name="remark" class="easyui-validatebox textbox"></td><td></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div id="arrangement_operate_tools" style="text-align: center;">
			    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveArrangements()" style="width: 90px;margin-bottom: 15px">保存</a>
			    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#arrangement_operate').dialog('close')" style="width: 90px;margin-bottom: 15px">取消</a>
		 	</div>
		</div>
		<div class="easyui-dialog" closed="true" data-options="modal: true" id="arrangement_target_select" style="width: 600px;height: 550px" buttons="arrangement_target_tools">
			<div style="margin: 10px;border:none;">
				<div style="width:40%;height:450px;" class="target_select">
					<div data-options="title:'请选择部门'" style="height:50%;" class="easyui-panel" id="arrangement_target_dept"></div>   
    				<div data-options="title:'请选择人员'" style="height:50%;" class="easyui-panel">
    					<table class="easyui-datagrid" id="arrangement_target_emploee" data-options="fit:true"></table>
    				</div>   
				</div>
				<div style="width:12%;height:450px;" class="target_select">
					<div style="width:100%;height: 50%">
						<div style="margin-top: 100px;margin-left: 5px;position: relative;">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="arrangement_target_dept_add">选择</a> 
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" id="arrangement_target_dept_remove">移除</a> 
						</div>
					</div>
					<div style="width:100%;">
						<div style="margin-left: 5px;position: relative;">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" id="arrangement_target_emploee_add">选择</a> 
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" id="arrangement_target_emploee_remove">移除</a> 	
						</div>
					</div>
				</div>
    			<div style="width:40%;height:450px;" class="target_select">
    				<div data-options="title:'已选择部门'" style="height:50%;" class="easyui-panel">
    					<table class="easyui-datagrid" id="arrangement_target_dept_select" data-options="fit:true"></table>
    				</div>   
    				<div data-options="title:'已选择人员'" style="height:50%;" class="easyui-panel">
    					<table class="easyui-datagrid" id="arrangement_target_emploee_select" data-options="fit:true"></table>
    				</div>
    			</div>
			</div>
			<div id="arrangement_target_tools" style="text-align: center;">
			    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectArrangementTarget()" style="width: 90px;margin-top: 15px">保存</a>
			    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#arrangement_target_select').dialog('close')" style="width: 90px;margin-top: 15px">取消</a>
		 	</div>
		</div>
    </div>   
	<div title="基本出勤" style="display:none;">
		<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="arrangement_base_time"></table>
			<div style="margin-bottom: 5px;" id="base_time_tools">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addArrangementBaseTime()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeArrangementBaseTime()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveArrangementBaseTime()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelArrangementBaseTime()">取消</a>        		
	        </div>
		</div>
    </div>
</div>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/Arrangement.js"></script>
