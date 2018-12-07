<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
	        <shiro:hasPermission name="check:init">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="initData()">数据初始化</a>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="cherk:execute">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="executeData()">数据归集</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="cherk:lockData">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="lockData()">数据封存</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="cherk:push">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="pushData()">数据推送</a>
	        </shiro:hasPermission>
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	公司名称：<input type="text" class="textbox" id="Search_Company" name="Search_Company" style="width: 110px;"/>
	        	中心机构：<input type="text" class="textbox" id="Search_Organ" name="Search_Organ" style="width: 110px;"/>
	        	部门名称：<input type="text" class="textbox" id="Search_Dept" name="Search_Dept" style="width: 110px;"/>
	        	<!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="clockedReset()">重置</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="clockedSearch()">查询</a>
	        	 -->
	        	<input id="clocked_Search" class="easyui-searchbox" style="width:300px" data-options="searcher:clockedSearch,prompt:'请输入......',menu:'#clocked_Search_menu'"></input> 
				<div id="clocked_Search_menu" style="width:120px"> 
					<div data-options="name:'WorkNum'">员工编号</div> 
					<div data-options="name:'UserName'">员工姓名</div>
					<div data-options="name:'Post'">岗位名称</div>
				</div> 
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
	<div id="clocked_menu" class="easyui-menu" style="width: 30px; display: none;">  
          <!--放置一个隐藏的菜单Div-->  
        <div id="btn_More" data-options="iconCls:'icon-remove'" onclick="exportClockedSumInfo()">导出汇总数据</div>        
    </div>  
    <div id="clocked_minxi_menu" class="easyui-menu" style="width: 30px; display: none;">  
          <!--放置一个隐藏的菜单Div-->  
        <div id="btn_More" data-options="iconCls:'icon-remove'" onclick="exportClockedDetailsInfo()">导出个人明细数据</div>        
    </div>  
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/ClockedResult.js"></script>
</body>
</html>