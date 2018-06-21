<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui-js/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui-js/themes/icon.css"/>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/locale/easyui-lang-zh_CN.js"></script>
<title>年假管理</title>
</head>
<body>
	<div id="annualleave_menu" class="easyui-tabs" data-options="fit:true">   
	    <div title="当前年假记录">   
	        <table id="nannualleave_tbo"></table>  
	        <div id="nannualleave_tbar" style="padding: 5px;">	        	
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		员工姓名：<input type="text" class="textbox" id="search_npname" name="search_npname" style="width: 100px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetnannualleave()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchnannualleave()">查询</a>
	        	</div>
	        </div>	        
	    </div> 
	    
	    <div title="历史年假记录">   
	        <table id="lannualleave_tbo"></table>  
	        <div id="lannualleave_tbar" style="padding: 5px;">	        	
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		员工姓名：<input type="text" class="textbox" id="search_lpname" name="search_lpname" style="width: 100px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetlannualleave()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchlannualleave()">查询</a>
	        	</div>
	        </div>	        
	    </div> 

	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/AnnualLeave.js"></script>
</body>
</html>