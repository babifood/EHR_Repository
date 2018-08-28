<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>津贴/扣款</title>
</head>
<body>
	<div class="easyui-panel" data-options="fit : true">
		<table id="allowance_list_datagrid" class="easyui-datagrid"></table>
		<div id="allowance_list_datagrid_tools">
			<div>
				<div >
					<a href="javascript:void(0)" id="mb" class="easyui-menubutton" data-options="menu:'#allowance_menubutton',iconCls:'icon-edit'">导入/导出</a>
					<div id="allowance_menubutton" style="width:150px;">   
						<shiro:hasPermission name="allowance:import">
						    <div data-options="iconCls:'icon-load'" onclick="exportAllowance(0)">下载模板</div>   
						    <div data-options="iconCls:'icon-redo'" onclick="allowanceImport()">导入</div>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="allowance:export">   
						    <div data-options="iconCls:'icon-remove'" onclick="exportAllowance(1)">导出</div>  
					    </shiro:hasPermission> 
					</div> 
				</div>
			</div>
			<div>
				<div style="margin-left: 5px">
					员工工号：<input type="text" class="textbox" id="allowance_pNumber" oninput="loadConditionAllowance()"> &nbsp;&nbsp;&nbsp;
	      			员工姓名：<input type="text" class="textbox" id="allowance_pName" oninput="loadConditionAllowance()"> &nbsp;&nbsp;&nbsp;
				</div>
				<div style="margin: 5px">
					机构名称：<input type="text" class="textbox" id="allowance_organzationName" oninput="loadConditionAllowance()"> &nbsp;&nbsp;&nbsp;
		      		部门名称：<input type="text" class="textbox" id="allowancee_deptName" oninput="loadConditionAllowance()"> &nbsp;&nbsp;&nbsp;
		      		科室名称：<input type="text" class="textbox" id="allowancee_officeName" oninput="loadConditionAllowance()">
				</div>
			</div>
		</div>
	</div>
	<div class="easyui-dialog" title="导入excel文件" style="width: 400px; height: 130px;" data-options="modal:true" id="allowance_dialog" closed="true" buttons="#allowance_dialog_buttons">
<!-- 		<div style="text-align: center;"> -->
<!-- 			<form id="allowance_uploadExcel"  method="post" enctype="multipart/form-data" style="margin-top: 5px">   -->
<!--    				<table style="width: 100%"> -->
<!--    					<tr> -->
<!--    						<td style="width:20%;">覆盖导入：</td><td style="width:10%;"><input type="radio" name="type" value="1"></td> -->
<!--    						<td style="width:69%">追加导入：<input type="radio" name="type" value="0"></td> -->
<!--    					</tr> -->
<!--    					<tr> -->
<!--    						<td style="width:20%;">选择文件：</td><td style="width:10%;"></td> -->
<!--    						<td style="width:69%"><input id = "allowance_file" name = "excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'" accept=".xls,.xlsx"></td> -->
<!--    					</tr> -->
<!--    				</table> -->
<!-- 			</form> -->
<!-- 			<form id="test_form" enctype="multipart/form-data" action="" method="post">     -->
<!-- 		        <button class="easyui-linkbutton" id="uploadEventBtn" type="button" >择文件</button>   -->
<!-- 		        <input type="file" name="file"  style="width:0px;height:0px;" id="uploadEventFile">   -->
<!-- 		        <input id="uploadEventPath" class="textbox" disabled="disabled"  type="text" placeholder="请择excel表" />                                            -->
<!-- 		    </form> -->
<!-- 		</div>   -->
<!-- 		<div style="text-align: center; padding: 5px 0;" id="allowance_dialog_buttons"> -->
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="user.uploadBtn()" style="width: 90px;" id="allowance_booten">导入</a> -->
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#allowance_dialog').dialog('close')" style="width: 90px;">取消</a> -->
<!-- 		</div> -->
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/excel.js"/>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/allowance.js"/>
</body>
</html>