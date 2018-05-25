<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>证件管理</title>
</head>
<body>
	<div ip="certificaten_panel" class="easyui-panel" data-options="fit:true,border:false,noheader:true">
		<table id="certificaten_grid"></table>
		<div id="certificaten_tbar">
			<div style="margin-bottom: 5px;">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCertificaten()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeCertificaten()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptCertificaten()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectCertificaten()">取消</a>        		
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	工号：<input type="text" class="textbox" id="search_p_number" name="search_p_number" style="width: 110px;"/>
	        	姓名：<input type="text" class="textbox" id="search_p_name" name="search_p_name" style="width: 110px;"/>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetCertificaten()">重置</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchCertificaten()">查询</a>
	        </div>
		</div>
		<div id="Marketer_ID_Member_bar">
			<table cellpadding="0" cellspacing="0" style="width: 100%;">
				<tr>
					<td style="padding: 0 0 0 7px;color: #333;">
						工号：<input type="text" class="textbox" id="p_number" name="p_number" style="width: 110px;"/>
	        			姓名：<input type="text" class="textbox" id="p_name" name="p_name" style="width: 110px;"/>
	        			<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchPerson()">查询</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/Certificaten.js"></script>
</body>
</html>