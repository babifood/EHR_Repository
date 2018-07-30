<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤结果</title>
</head>
<body>
	<div ip="clocked_panel" class="easyui-panel" data-options="fit:true,border:false">
		<table id="clocked_grid"></table>
		<div id="clocked_tbar">
			<div style="padding: 0 0 0 7px;color: #333;">
				年份：<input type="text" class="easyui-numberspinner" id="year" name="year" style="width: 110px;"/>
	        	月份：<input type="text" class="easyui-numberspinner" id="month" name="month" style="width: 110px;"
	        			data-options="min:1,max:12"/>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="initData()">数据初始化</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="executeData()">数据归集</a>
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	工号：<input type="text" class="textbox" id="WorkNum" name="WorkNum" style="width: 110px;"/>
	        	姓名：<input type="text" class="textbox" id="UserName" name="UserName" style="width: 110px;"/>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clockedReset()">重置</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="clockedSearch()">查询</a>
	        </div>
		</div>
	</div>
	<div id="clocked_win" class="easyui-window" closed="true" 
        data-options="iconCls:'icon-save',modal:true,fit:true,collapsible:false,minimizable:false,maximizable:false">   
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',split:true">
				<table id="clocked_minxi_grid"></table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
				<!--<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="savePersonInFo()" style="width:80px">保存</a> -->
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#clocked_win').window('close')" style="width:80px">取消</a>
			</div>	
		</div>	
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/ClockedResult.js"></script>
</body>
</html>