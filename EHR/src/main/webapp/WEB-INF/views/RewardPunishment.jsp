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
	<div id="rewardRoPunish_Tabs" class="easyui-tabs" data-options="fit:true">   
	    <div title="奖惩记录">   
	        <table id="RewardPunishment_tbo"></table>  
	        <div id="RewardPunishment_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRewardPunishment()">添加</a>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRewardPunishment()">修改</a>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRewardPunishment()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		<input id="RewardPunishment_search" class="easyui-searchbox" style="width:300px" data-options="searcher:searchRewardPunishment,prompt:'请输入......',menu:'#RewardPunishment_search_menu'"></input> 
					<div id="RewardPunishment_search_menu" style="width:120px"> 
						<div data-options="name:'rap_category_name'">奖惩项目类别</div> 
						<div data-options="name:'rap_item_name'">奖惩项目名称</div>
						<div data-options="name:'rap_reason'">奖惩原因</div> 
						<div data-options="name:'rap_p'">奖惩人员</div> 
					</div> 
	        	</div>
	        </div>
	        <div id="RewardPunishment_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#RewardPunishment_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">奖惩记录</span></div>
			        <table>
			        	<tr style="margin-bottom: 10px;">
			        		<td>奖惩类别：</td>
			        		<td>
			       			   	<select id="rap_category" class="easyui-combobox" editable="false" style="width: 180px;">
									<option value="0">奖</option>
									<option value="1">惩</option>
								</select> 
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>奖惩项目：</td>
			        		<td>
			        			<input id="rap_item" class="easyui-combobox"  style="width: 180px;">
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>奖惩日期：</td>
			        		<td>
			        			<input id="rap_date" name="rap_date" class="easyui-datebox" data-options="editable:false" style="width: 180px;"/>
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>奖惩原因：</td>
			        		<td>
			        			<input type="text" id="rap_reason" name="rap_reason" class="textbox"  style="width: 180px;"/>
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>奖惩金额：</td>
			        		<td>
			        			<input type="text" id="rap_money" class="easyui-numberbox" data-options="min:0,precision:2,prefix:'￥'" style="width: 180px;"/> 
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<!-- 要弹窗出部门树，选择人员，可多人，记一条数据 -->
			        		<td>提议人：</td>
			        		<td>
			        			<input type="hidden" id="rap_proposer_id" name="rap_proposer_id" class="textbox"/>
			        			<input type="text" id="rap_proposer" name="rap_proposer" class="textbox"  onfocus="selectPerson(this.id)" style="width: 180px;"/>
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<!-- 要弹窗出部门树，选择人员，可多人，记多条数据 -->
			        		<td>奖惩人员：</td>
			        		<td>
			        			<input type="hidden" id="rap_p_id" name="rap_p_id" class="textbox"/>
			        			<input type="text" id="rap_p" name="rap_p"  class="textbox"  onfocus="selectPerson(this.id)" style="width: 180px;"/>
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>其他说明：</td>
			        		<td>
			        			<input type="text" id="rap_desc" name="rap_desc" class="textbox"  style="width: 180px;"/>
			        		</td>
			        	</tr>
			        </table>
		        </div>
		     </div>	 	         		
	        <div id="RewardPunishment_dlg_buttons" style="text-align: center;">
	        	<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRewardPunishment()" style="width: 90px;">保存</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#RewardPunishment_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div>	
	
			<!-- 这里是选择人员窗口 -->
			<div id="selectPerson_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#selectPerson_dlg_buttons" style="width: 600px; height:500px;">
	        	<jsp:include page="personSelectionWindow.jsp" />
	        </div>
	        <div id="selectPerson_dlg_buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok"  onclick="saveSelectPerson()" style="width: 90px;">保存</a><!-- 保存1 -->
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#selectPerson_dog').dialog('close')" style="width: 90px;">取消</a><!-- 取消1 -->
			</div>
	    </div> 
	    <div title="奖惩项目">   
	        <table id="RAPItem_tbo"></table>  
	        <div id="RAPItem_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRAPItem()">添加</a>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRAPItem()">修改</a>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRAPItem()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		<input id="RAPItem_search" class="easyui-searchbox" style="width:300px" data-options="searcher:searchRAPItem,prompt:'请输入......',menu:'#RAPItem_search_menu'"></input> 
					<div id="RAPItem_search_menu" style="width:120px"> 
						<div data-options="name:'rap_category_name'">奖惩项目类别</div> 
						<div data-options="name:'rap_item_name'">奖惩项目名称</div>
					</div> 
	        	</div>
	        </div>  
	        <div id="RAPItem_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#RAPItem_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">奖惩项目信息</span></div>
			        <table>
			        	<tr style="margin-bottom: 10px;">
			        		<td>奖惩类别：</td>
			        		<td>
			       			    <select id="category" class="easyui-combobox" editable="false" required="required" style="width: 180px;">
									<option value="0">奖</option>
									<option value="1">惩</option>
								</select> 
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>项目名称：</td>
			        		<td>
			        			<input type="text" id="item_name" name="item_name" class="textbox" style="width: 180px;"/>
			        		</td>
			        	</tr>
			        </table>
		        </div>
		     </div>   		
	        <div id="RAPItem_dlg_buttons" style="text-align: center;">
	        	<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRAPItem()" style="width: 90px;">保存</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#RAPItem_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div> 
	    </div> 
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/RewardPunishment.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/personSelectionWindow.js"></script>
</body>
</html>