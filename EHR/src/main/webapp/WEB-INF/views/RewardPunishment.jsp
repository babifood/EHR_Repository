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
<title>奖惩管理</title>
</head>
<body>
	<div id="JobLevel_menu" class="easyui-tabs" data-options="fit:true">   
	    <div title="奖惩记录">   
	        <table id="RewardPunishment_tbo"></table>  
	        <div id="RewardPunishment_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRewardPunishment()">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRewardPunishment()">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRewardPunishment()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		奖惩类别：<input type="text" class="textbox" id="search_rap_category" name="search_rap_category" style="width: 100px;"/>
	        		奖惩项目：<input type="text" class="textbox" id="search_rap_item" name="search_rap_item" style="width: 100px;"/>
	        		日期：<input class="easyui-datebox" id="search_rap_date_left" name="search_rap_date_left" style="width: 100px;"/>
	        		到<input class="easyui-datebox" id="search_rap_date_right" name="search_rap_date_right" style="width: 100px;"/>
	        		金额：<input type="text" class="textbox" id="search_rap_money" name="search_rap_money" style="width: 100px;"/>
	        		提议人：<input type="text" class="textbox" id="search_rap_proposer_id" name="search_rap_proposer_id" style="width: 100px;"/>
	        		奖惩人员：<input type="text" class="textbox" id="search_rap_p_id" name="search_rap_p_id" style="width: 100px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetRewardPunishment()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchRewardPunishment()">查询</a>
	        	</div>
	        </div>
	        <div id="RewardPunishment_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#RewardPunishment_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">奖惩记录</span></div>
			        <div style="margin-bottom: 10px;">
			        	奖惩类别：<!-- <input type="text" id="rap_category" name="rap_category" class="textbox"  onblur="noBlurRewardPunishment()"style="width: 180px;"/> -->
			       			   <select id="rap_category" class="easyui-combobox" editable="false" style="width:180px" required="required">
										<option value="0">奖</option>
										<option value="1">惩</option>
									</select>
			       			   <span id="rap_category_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	奖惩项目：<input id="rap_item" name="rap_item" style="width: 180px;"/>  
			       			   <span id="rap_item_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	奖惩日期：<input id="rap_date" name="rap_date" class="easyui-datebox" data-options="required:true" onblur="noBlurRewardPunishment()" style="width: 180px;"/>
			       			   <span id="rap_date_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	奖惩原因：<input type="text" id="rap_reason" name="rap_reason" class="textbox" onblur="noBlurRewardPunishment()" style="width: 180px;"/>
			       			   <span id="rap_reason_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	奖惩金额：<input type="text" id="rap_money" name="rap_money" class="textbox" onblur="noBlurRewardPunishment()" style="width: 180px;"/>
			       			   <span id="rap_money_span" style="color: red"></span>
			        </div>
			        <!-- 要弹窗出部门树，选择人员，可多人，记一条数据 -->
			        <div style="margin-bottom: 10px;">
			        	提  议  人：<input id="rap_proposer" name="rap_proposer" class="easyui-textbox" data-options="
			        			prompt:'Input something here!'
			        			,iconWidth: 22
			        			,icons:[
			        				{
			        					iconCls:'icon-search'
			        					,handler:selectPerson
			        				}
			        			]"/>
			        			<span id="rap_proposer_id_span" style="color: red"></span>
			        </div>
			        <!-- 要弹窗出部门树，选择人员，可多人，记多条数据 -->
			        <div style="margin-bottom: 10px;">
			        	奖惩人员：<input id="rap_p" name="rap_p" class="easyui-textbox" data-options="
			        			iconWidth: 22
			        			,icons:[
			        				{
			        					iconCls:'icon-search'
			        					,handler:selectPerson
			        					
			        				}
			        			]"/>
			        			<span id="rap_p_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	其他说明：<input type="text" id="rap_desc" name="rap_desc" class="textbox" onblur="noBlurRewardPunishment()" style="width: 180px;"/>
			       			   <span id="rap_desc_span" style="color: red"></span>
			        </div>
		        </div>
		     </div>	 	         		
	        <div id="RewardPunishment_dlg_buttons" style="text-align: center;">
	        	<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRewardPunishment()" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#RewardPunishment_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div>	
	        
	        
	        <!-- <div id="test_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#test_dlg_buttons" style="width: 600px; height:500px;">
	        	<ul id="tt"></ul>
	        </div>
	        <div id="test_dlg_buttons">
				<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#test_dog').dialog('close')" style="width: 90px;">取消</a>
			</div> -->
			
			<div id="test_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#test_dlg_buttons" style="width: 600px; height:500px;">
	        	<jsp:include page="userTree.jsp" />
	        </div>
	        <div id="test_dlg_buttons">
				<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#test_dog').dialog('close')" style="width: 90px;">取消</a>
			</div>
	    </div> 
	    
	    
	    
	    
	    
	    <div title="奖惩项目">   
	        <table id="RAPItem_tbo"></table>  
	        <div id="RAPItem_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRAPItem()">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRAPItem()">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRAPItem()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		奖惩类别：<input type="text" class="textbox" id="search_category_id" name="search_category_id" style="width: 110px;"/>
	        		项目名称：<input type="text" class="textbox" id="search_item_name" name="search_item_name" style="width: 110px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetRAPItem()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchRAPItem()">查询</a>
	        	</div>
	        </div>  
	        <div id="RAPItem_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#RAPItem_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">奖惩项目信息</span></div>
			        <div style="margin-bottom: 10px;">
			        	奖惩项目ID：<input type="text" id="item_id" name="item_id" class="textbox" data-options="required:true" onblur="noBlurRewardPunishment()" style="width: 180px;"/>
			        		   <span id="item_id_span" style="color: red"></span>	
			        </div>
			        <div style="margin-bottom: 10px;">
			        	奖  惩  类  别：<input type="text" id="category_id" name="category_id" class="textbox"  onblur="noBlurRAPItem()"style="width: 180px;"/>
			       			   <span id="category_id_span" style="color: red"></span>
			        </div>
			        <div style="margin-bottom: 10px;">
			        	项  目  名  称：<input type="text" id="item_name" name="item_name" class="textbox"  onblur="noBlurRAPItem()"style="width: 180px;"/>
			       			   <span id="item_name_span" style="color: red"></span>
			        </div>
		        </div>
		     </div>   		
	        <div id="RAPItem_dlg_buttons" style="text-align: center;">
	        	<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRAPItem()" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#RAPItem_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div> 
	        
	        

	         
	    </div> 

	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/RewardPunishment.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/userTree.js"></script>
</body>
</html>