<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<div id="dormitory_tabs" class="easyui-tabs" data-options="fit:true">
	<div title="住宿管理"  style="display:none;">
		<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="dormitory_list"></table>
			<div style="margin-bottom: 5px;" id="dormitory_list_tools">
				<div>
					<shiro:hasPermission name="dormitory:add">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDormitoryInfo()">添加</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="dormitory:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDormitoryInfo()">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="dormitory:operate">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDormitoryInfo()">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelDormitoryInfo()">取消</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="dormitory:checking">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="checkingDormitory()">入住</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="moveOutDormitoryFormalities()">办理手续</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="moveOutDormitory()">搬出</a>
					</shiro:hasPermission>
				</div>
	        	<div style="margin-left: 10px">
	        		楼层：<input type="text" class="easyui-combobox" id="dormitory_floor" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '1',text: '一楼'}, {value: '2',text: '二楼'}, {value: '3',text: '三楼'}, {value: '4',text: '四楼'}, 
						{value: '5',text: '五楼'}, {value: '6',text: '六楼'}],editable:false,onSelect:function(rec){searchEmployeeList(1,rec.value)}"> &nbsp;&nbsp;&nbsp;
	        		房间号：<input type="text" class="textbox" id="dormitory_roomNo" oninput="searchEmployeeList()"> &nbsp;&nbsp;&nbsp;
	        		宿舍类型：<input type="text" class="easyui-combobox" id="dormitory_sex" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '0',text: '男宿舍'}, {value: '1',text: '女宿舍'}],editable:false,onSelect:function(rec){searchEmployeeList(2,rec.value)}"> &nbsp;&nbsp;&nbsp;
	        		是否入住：<input type="text" class="easyui-combobox" id="dormitory_stay" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '0',text: '未入住'}, {value: '1',text: '已入住'}],editable:false,onSelect:function(rec){searchEmployeeList(3,rec.value)}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchEmployee()">重置</a>
	        	</div>
	        </div>
		</div>
	</div>
	<div title="住宿记录"  style="display:none;">
		<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="stay_dormitory_list"></table>
			<div style="margin-bottom: 5px;" id="stay_dormitory_list_tools">
<!-- 				<div> -->
<!-- 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="moveOutDormitory()">搬出</a> -->
<!-- 				</div> -->
	        	<div style="margin-left: 10px">
	        		<div>
	        			工号：<input type="text" class="textbox" id="checking_dormitory_pnumber" oninput="searchCheckingEmployeeList()"/> &nbsp;&nbsp;&nbsp;
		       			姓名：<input type="text" class="textbox" id="checking_dormitory_pname" oninput="searchCheckingEmployeeList()"/> &nbsp;&nbsp;&nbsp;
		        		房间号：<input type="text" class="textbox" id="checking_dormitory_roomNo" oninput="searchCheckingEmployeeList()">
	        		</div>
	        		<div style="margin-top: 5px">
		        		楼层：<input type="text" class="easyui-combobox" id="checking_dormitory_floor" style="width: 164px" data-options="valueField: 'value',textField: 'text', 
							data: [{value: '1',text: '一楼'}, {value: '2',text: '二楼'}, {value: '3',text: '三楼'}, {value: '4',text: '四楼'}, 
							{value: '5',text: '五楼'}, {value: '6',text: '六楼'}],editable:false,onSelect:function(rec){searchCheckingEmployeeList(1,rec.value)}"> &nbsp;&nbsp;&nbsp;
		        		宿舍类型：<input type="text" class="easyui-combobox" id="checking_dormitory_sex" style="width: 164px" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '0',text: '男宿舍'}, {value: '1',text: '女宿舍'}],editable:false,onSelect:function(rec){searchCheckingEmployeeList(2,rec.value)}">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchCheckingEmployee()">重置</a>
	        		</div>
	        	</div>
			</div>
		</div>
	</div>
	<div title="宿舍费用管理"  style="display:none;">
		<div data-options="fit:true,border:false,noheader:true" class="easyui-panel">
			<table class="easyui-datagrid" id="dormitory_cost_list"></table>
			<div style="margin-bottom: 5px;" id="dormitory_cost_list_tools">
				<div>
					<a href="javascript:void(0)" id="mb" class="easyui-menubutton" data-options="menu:'#dormitory_cost_menubutton',iconCls:'icon-edit'">导入/导出</a>
					<div id="dormitory_cost_menubutton" style="width:150px;">   
						<shiro:hasPermission name="cost:import">
						    <div data-options="iconCls:'icon-load'" onclick="exportDormitoryCost(0)">下载模板</div>   
						    <div data-options="iconCls:'icon-redo'" onclick="dormitoryCostImport()">导入</div>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="cost:export">   
						    <div data-options="iconCls:'icon-remove'" onclick="exportDormitoryCost(1)">导出</div>  
					    </shiro:hasPermission> 
					</div> 
					<shiro:hasPermission name="cost:add">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDormitoryCost()">添加</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cost:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDormitoryCost()">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cost:edit">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="updateDormitoryCost()">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cost:operate">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDormitoryCost()">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelDormitoryCost()">取消</a>
					</shiro:hasPermission>
				</div>
				<div>
        			工号：<input type="text" class="textbox" id="dormitory_cost_pnumber" oninput="searchDormitoryCostList()"/> &nbsp;&nbsp;&nbsp;
	       			姓名：<input type="text" class="textbox" id="dormitory_cost_pname" oninput="searchDormitoryCostList()"/> &nbsp;&nbsp;&nbsp;
	        		房间号：<input type="text" class="textbox" id="dormitory_cost_roomNo" oninput="searchDormitoryCostList()"> &nbsp;&nbsp;&nbsp;
	        		楼层：<input type="text" class="easyui-combobox" id="dormitory_cost_floor" style="width: 164px" data-options="valueField: 'value',textField: 'text', 
						data: [{value: '1',text: '一楼'}, {value: '2',text: '二楼'}, {value: '3',text: '三楼'}, {value: '4',text: '四楼'}, 
						{value: '5',text: '五楼'}, {value: '6',text: '六楼'}],editable:false,onSelect:function(rec){searchDormitoryCostList(1,rec.value)}"> &nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchDormitoryCost()">重置</a>
        		</div>
        		<div style="margin-top: 5px">
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
<div class="easyui-dialog" title="搬出时间" closed="true"  data-options="modal: true" id="dormitory_outtime_dialog" buttons="#dormitory_outtime_buttons" style="height: 110px">
	<div style="margin : 5px;width: 300px">
		请选择搬出时间：<input class="easyui-datebox" id="dormitory_moveout_time" style="width: 200px"/>
	</div>
</div>
<div id="dormitory_outtime_buttons" style="text-align: center;">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectDormitoryCost(0)" style="width: 90px;">保存</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dormitory_outtime_dialog').dialog('close')" style="width: 90px;">取消</a>
</div>
<div class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 130px;" data-options="modal:true" id="dormitory_cost_dialog" closed="true" buttons="#dormitory_cost_dialog_buttons">
<!-- 	<div style="text-align: center;"> -->
<!-- 		<form id="dormitory_cost_uploadExcel"  method="post" enctype="multipart/form-data" style="margin-top: 20px">   -->
<!--   				选择文件：　<input id = "dormitory_cost_file" name = "excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'" accept=".xls,.xlsx">   -->
<!-- 		</form> -->
<!-- 	</div>   -->
<!-- 	<div style="text-align: center; padding: 5px 0;" id="dormitory_cost_dialog_buttons"> -->
<!-- 		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="importPerformanceInfos()" style="width: 90px;" id="dormitory_cost_booten">导入</a> -->
<!-- 		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dormitory_cost_dialog').dialog('close')" style="width: 90px;">取消</a> -->
<!-- 	</div> -->
</div>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/excel.js"/>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/dormitory.js"/>