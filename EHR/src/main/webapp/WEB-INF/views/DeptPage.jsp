<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/organize/examples/css/jquery.orgchart.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/organize/examples/css/style.css"/>
<div class="easyui-layout" data-options="fit:true">   
    <div data-options="region:'west',title:'部门信息',split:true" style="width:200px;">
    	<ul id="dept_tree"></ul>
    </div>
    <div id="dept-tools" class="easyui-menu" closed="true"> 
			<div data-options="iconCls:'icon-add'" class="dept-add">新增</div>   
			<div data-options="iconCls:'icon-edit'" class="dept-update">修改</div>   
			<div data-options="iconCls:'icon-remove'" class="dept-del">删除</div>   
	</div>  
    <div data-options="region:'center',title:'组织架构图',tools:'#print-tool'" style="padding:5px;">
    	<div id ="chart-container" style="height: 100%"></div>
    </div>  
    <div id="print-tool">
		<a href="#" class="icon-print" style="width :30px;" id ="printArea" ></a>
		<a target="_blank" style="width :50px;" id="dept_export">导出</a>
	</div> 
	<div id="dept_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#dept_dlg_buttons" style="width: 450px;">
		<div style="margin: 0;padding: 20px 50px;">
			<div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">部门信息</span></div>
			<input type="hidden" name="id" id="dept_id">
			<div style="margin-bottom: 10px;">
			          部门名称：<input type="text" id="dept_name" name="dept_name" class="textbox" data-options="required:true" style="width: 180px;"/>
			   <span id="dept_name_span" style="color: red"></span>	
			</div>
			<div style="margin-bottom: 10px;">
			           部门编号：<input type="text" id="dept_code" name="dept_code" class="textbox" data-options="required:true" style="width: 180px;"/>
			    <span id="dept_name_span" style="color: red"></span>	
			</div>
			<div style="margin-bottom: 10px;">
			           上级部门：<input type="text" id="pCode" name="pCode" class="textbox" data-options="required:true" style="width: 180px;"/>
			    <span id="pCode_span" style="color: red"></span>
			</div>
			<div style="margin-bottom: 10px;">
			            备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：<textarea name="remark" rows="3" class="textbox" cols="25" id ="dept_remark"/>
			</div>
		 </div> 		
		 <div id="dept_dlg_buttons" style="text-align: center;">
		    <a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savedept()" style="width: 90px;">保存</a>
		    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dept_dog').dialog('close')" style="width: 90px;">取消</a>
		 </div>  
	</div>
	<div id="dept-tree-dog" class="easyui-dialog" closed="true" style="width: 350px;" title="My Dialog">
	</div>     	
</div>  
<script type="text/javascript" src="${pageContext.request.contextPath}/organize/examples/js/jquery.orgchart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/DeptPage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.PrintArea.min.js"></script>