<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="dormitory_tabs" class="easyui-tabs" data-options="fit:true">
	<div title="床位管理"  style="display:none;">
		<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="dormitory_list"></table>
			<div style="margin-bottom: 5px;" id="dormitory_list_tools">
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDormitoryInfo()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDormitoryInfo()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDormitoryInfo()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelDormitoryInfo()">取消</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="checkingDormitory()">入住</a>
				</div>
	        	<div style="margin-left: 10px">
	        		楼层：<input type="text" class="easyui-combobox" id="dormitory_floor" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '1',text: '一楼'}, {value: '2',text: '二楼'}, {value: '3',text: '三楼'}, {value: '4',text: '四楼'}, 
						{value: '5',text: '五楼'}, {value: '6',text: '六楼'}],onSelect:function(rec){searchEmployeeList(1,rec.value)}"> &nbsp;&nbsp;&nbsp;
	        		房间号：<input type="text" class="textbox" id="dormitory_roomNo" oninput="searchEmployeeList()"> &nbsp;&nbsp;&nbsp;
	        		宿舍类型：<input type="text" class="easyui-combobox" id="dormitory_sex" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '0',text: '男宿舍'}, {value: '1',text: '女宿舍'}],onSelect:function(rec){searchEmployeeList(2,rec.value)}"> &nbsp;&nbsp;&nbsp;
	        		是否入住：<input type="text" class="easyui-combobox" id="dormitory_stay" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '0',text: '未入住'}, {value: '1',text: '已入住'}],onSelect:function(rec){searchEmployeeList(3,rec.value)}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchEmployee()">重置</a>
	        	</div>
	        </div>
		</div>
	</div>
	<div title="入住员工"  style="display:none;">
		<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="stay_dormitory_list"></table>
			<div style="margin-bottom: 5px;" id="stay_dormitory_list_tools">
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="moveOutDormitory()">搬出</a>
				</div>
	        	<div style="margin-left: 10px">
	        		<div>
	        			工号：<input type="text" class="textbox" id="checking_dormitory_pnumber" oninput="searchCheckingEmployeeList()"/> &nbsp;&nbsp;&nbsp;
		       			姓名：<input type="text" class="textbox" id="checking_dormitory_pname" oninput="searchCheckingEmployeeList()"/> &nbsp;&nbsp;&nbsp;
		        		房间号：<input type="text" class="textbox" id="checking_dormitory_roomNo" oninput="searchCheckingEmployeeList()">
	        		</div>
	        		<div style="margin-top: 5px">
		        		楼层：<input type="text" class="easyui-combobox" id="checking_dormitory_floor" style="width: 164px" data-options="valueField: 'value',textField: 'text', 
							data: [{value: '1',text: '一楼'}, {value: '2',text: '二楼'}, {value: '3',text: '三楼'}, {value: '4',text: '四楼'}, 
							{value: '5',text: '五楼'}, {value: '6',text: '六楼'}],onSelect:function(rec){searchCheckingEmployeeList(1,rec.value)}"> &nbsp;&nbsp;&nbsp;
		        		宿舍类型：<input type="text" class="easyui-combobox" id="checking_dormitory_sex" style="width: 164px" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '0',text: '男宿舍'}, {value: '1',text: '女宿舍'}],onSelect:function(rec){searchCheckingEmployeeList(2,rec.value)}">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchCheckingEmployee()">重置</a>
	        		</div>
	        	</div>
			</div>
		</div>
	</div>
</div>    
<div class="easyui-dialog" title="请选择入住人员" closed="true"  data-options="modal: true" id="dormitory_dialog" style="height: 450px">
	<table class="easyui-datagrid" id="emp_stay_dormitory_list"></table>
	<div id="emp_stay_dormitory_list_tools">
		<div style="padding: 0 0 0 7px;color: #333;width: 100%;">
			<font style="font-size: 20px;font-weight: bold ">搜索：</font>
			工号：<input type="text" class="textbox" id="stay_dormitory_pnumber" style="width: 110px;" oninput="searchDormitory()"/> &nbsp;&nbsp;&nbsp;
	       	姓名：<input type="text" class="textbox" id="stay_dormitory_pname" style="width: 110px;" oninput="searchDormitory()"/>
	    </div>
	    <hr style="width: 100%;">
	    <div style="padding-left: 10px;margin-bottom: 5px">
	    	请选择入住时间：<input id="dormitory_stay_time" type="text" class="easyui-datebox" required="required"></input>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dormitory_dialog').dialog('close')" style="float: right;margin-right: 20px">取消</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="stayEmployDormitory()" style="float: right; margin-right: 20px">选择</a>
	    </div>
	</div>
</div>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/dormitory.js">