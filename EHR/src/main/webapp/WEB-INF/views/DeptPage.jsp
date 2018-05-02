<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/DeptPahe.js"></script> -->
<title>部门管理</title>
</head>
<body>
	<div id="role_dept_menu" class="easyui-tabs" data-options="fit:true">   
	    <div title="部门信息">     
	    	<div style="width: 100%;height: 93%;">
		        <div id="TissueArchitectureTree_div" style="float: left;width: 25%;height: 100%;padding: 0px;margin: 0px;">
		        	<dir class="easyui-panel" title="部门架构树"  data-options="fit:true,"style="padding: 0 0 0 20px;margin: 0px;">
		        		<!-- <ul id="TissueArchitectureTree_ul"></ul>还没有做js，应为动态树 -->
		        	</dir>
		        </div> 
		        <div id="role_div" style="float: right;width: 75%;height: 100%;">
		        	<div class="easyui-panel" title="组织架构图"  data-options="fit:true,">
		        		<!-- 还没有做js，应为根据组织架构树生成的图，可打印 -->
		        	</div>
		        </div>  
		    </div>
	    </div> 
	</div>
</body>
</html>