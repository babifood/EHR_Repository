<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
	<table class="easyui-datagrid" id="base_setting_list"></table>
	<div id="base_setting_list_tools">
		<div style="margin-bottom: 5px;">
			<shiro:hasPermission name="baseSetting:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBaseSeetingInfo()">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="baseSetting:edit">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateEmployeeBaseSetting()">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="baseSetting:operate">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveBaseSeetingInfo()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelBaseSeetingInfo()">取消</a>
			</shiro:hasPermission>
		</div>
      	<div style="padding: 0 0 0 7px;color: #333;padding-left: 10px">
      		<div>
	      		员工工号：<input type="text" class="textbox" id="base_setting_number" oninput="reloadBaseSettingInfo()"> &nbsp;&nbsp;&nbsp;
	      		员工姓名：<input type="text" class="textbox" id="base_setting_name" oninput="reloadBaseSettingInfo()">
      		</div>
      		<div style="margin-top: 3px">
	      		工作类型：<input type="text" class="textbox easyui-combobox" id="base_setting_type" data-options="valueField: 'value',textField: 'text',
								data: [{value: '0',text: '计时'},{value: '1',text: '计件'}],onSelect:function(rec){reloadBaseSettingInfo(1,rec.value)}"> &nbsp;&nbsp;&nbsp;
	      		是否餐补：<input type="text" class="textbox easyui-combobox" id="base_setting_ismeal" data-options="valueField: 'value',
						textField: 'text', data: [{value: '0',text: '无'}, {value: '1',text: '有'}],onSelect:function(rec){reloadBaseSettingInfo(2,rec.value)}"> &nbsp;&nbsp;&nbsp;
	      		工作地：<input type="text" class="textbox easyui-combobox" id="base_setting_place" data-options="valueField: 'value', textField: 'text',
						data: [{value: '1',text: '上海'}, {value: '2',text: '江苏'}, {value: '3',text: '浙江'}, {value: '4',text: '广东'},
						{value: '5',text: '深圳'}, {value: '6',text: '北京'}],onSelect:function(rec){reloadBaseSettingInfo(3,rec.value)}" >
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchBaseSetting()">重置</a> 
      		</div>
      	</div>
      </div>
</div>

<!-- <div id="base_setting_member_bar" class="datagrid-toolbar"> -->
<!-- 	工号：<input type="text" class="textbox" id="base_setting_p_number" style="width: 110px;" oninput="searchBaseSettingPerson()"/> -->
<!-- 	姓名：<input type="text" class="textbox" id="base_setting_p_name" style="width: 110px;" oninput="searchBaseSettingPerson()"/> -->
<!-- </div> -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/baseSetting.js"></script>