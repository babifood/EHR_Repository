<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绩效列表</title>
</head>
<body>
	<div class="easyui-panel" data-options="fit : true">
		<table id="performance_list_datagrid" class="easyui-datagrid"></table>
		<div id="performance_list_datagrid_tools">
			<div>
				<div >
					<a href="javascript:void(0)" id="mb" class="easyui-menubutton"data-options="menu:'#performance_menubutton',iconCls:'icon-edit'">导入/导出</a>
					<div id="performance_menubutton" style="width:150px;">
						<shiro:hasPermission name="performance:import">
						    <div data-options="iconCls:'icon-load'" onclick="exportPerformance(0)">下载模板</div>   
						    <div data-options="iconCls:'icon-redo'" onclick="performanceImport()">导入</div>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="performance:export">   
						    <div data-options="iconCls:'icon-remove'" onclick="exportPerformance(1)">导出</div>
					    </shiro:hasPermission>   
					</div> 
					<shiro:hasPermission name="performance:edit">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="updatePerformanceSocre()">修改</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePerformanceSocre()">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelPerformanceSocre()">取消</a>
					</shiro:hasPermission>
				</div>
			</div>
			<div>
				<div style="margin-left: 5px">
					员工工号：<input type="text" class="textbox" id="performance_salary_pNumber" oninput="loadConditionPerformance()"> &nbsp;&nbsp;&nbsp;
	      			员工姓名：<input type="text" class="textbox" id="performance_salary_pName" oninput="loadConditionPerformance()"> &nbsp;&nbsp;&nbsp;
				</div>
				<div style="margin: 5px">
					机构名称：<input type="text" class="textbox" id="performance_salary_organzationName" oninput="loadConditionPerformance()"> &nbsp;&nbsp;&nbsp;
		      		部门名称：<input type="text" class="textbox" id="performancee_salary_deptName" oninput="loadConditionPerformance()"> &nbsp;&nbsp;&nbsp;
		      		科室名称：<input type="text" class="textbox" id="performancee_salary_officeName" oninput="loadConditionPerformance()">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clearSearchPerformance()">重置</a>
				</div>
			</div>
		</div>
	</div>
	<div class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 130px;" data-options="modal:true" id="performance_dialog" closed="true" buttons="#performance_dialog_buttons">
<!-- 		<div style="text-align: center;"> -->
<!-- 			<form id="performance_uploadExcel"  method="post" enctype="multipart/form-data" style="margin-top: 20px">   -->
<!--    				选择文件：　<input id = "performance_file" name = "excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'" accept=".xls,.xlsx">   -->
<!-- 			</form> -->
<!-- 		</div>   -->
<!-- 		<div style="text-align: center; padding: 5px 0;" id="performance_dialog_buttons"> -->
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="importPerformanceInfos()" style="width: 90px;" id="performance_booten">导入</a> -->
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#performance_dialog').dialog('close')" style="width: 90px;">取消</a> -->
<!-- 		</div> -->
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/excel.js"/>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/performance.js"/>
</body>
</html>