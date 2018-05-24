<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>部门管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/DeptPahe.js"></script> -->
<!-- 	<link rel="icon" type="image/x-icon" href="../img/logo.ico"> -->

</head>
<body>
	<div id="dept_dept_menu" class="easyui-tabs" data-options="fit:true">
	    <div title="部门信息">     
	    	<div style="width: 100%;height: 93%;">
		        <div id="TissueArchitectureTree_div" style="float: left;width: 25%;height: 100%;padding: 0px;margin: 0px;">
		        	<div class="easyui-panel" title="部门架构树" tools='#dept-tools' data-options="fit:true,"style="padding: 0 0 0 20px;margin: 0px;" id="dept-tree">
		        		<!-- <ul id="TissueArchitectureTree_ul"></ul>还没有做js，应为动态树 -->
		        		<ul id="tt"></ul>
		        	<div id="dept-tools">
		        		<a href="javascript:void(0)" class="easyui-menubutton" id="dept-edit"
					        data-options="menu:'#mm'"  style="width: 50px;">Edit</a>   
						<div id="mm">   
						    <div data-options="iconCls:'icon-add'" class="dept-add">新增</div>   
						    <div data-options="iconCls:'icon-edit'" class="dept-update">修改</div>   
						    <div data-options="iconCls:'icon-remove'" class="dept-del">删除</div>   
						</div>
		        	</div>
		        	</div>
		        </div> 
		        <div id="dept_div" style="float: right;width: 75%;height: 100%;">
		        	<div class="easyui-panel" title="组织架构图"  data-options="fit:true," tools='#print-tool' id ="chart-container">
		        		<!-- 还没有做js，应为根据组织架构树生成的图，可打印 -->
		        	</div>
		        	<div id="print-tool">
		        		<a href="#" class="icon-print" style="width :50px;" id ="printArea" ></a>
		        		<a href="dept/export" target="_blank">导出</a>
		        	</div>
		        </div>
		        
		     </div>
		     	 
		    <div id="dept-tree-dog" class="easyui-dialog" closed="true" style="width: 350px;" title="My Dialog">
		    	
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
	    </div> 
	</div>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/organize/examples/js/jquery.min.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/organize/examples/js/jquery.orgchart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DeptPage.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.PrintArea.min.js"></script>
</body>
	
</html>