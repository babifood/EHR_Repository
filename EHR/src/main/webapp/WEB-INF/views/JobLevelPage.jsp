<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/JobLevelPage.css"/>
<title>岗位管理</title>
</head>
<body>
	<div id="JobLevel_menu" class="easyui-tabs" data-options="fit:true">   
	    <div title="职级信息">   
	        <table id="JobLevel_tbo"></table>  
	        <div id="JobLevel_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addJobLevel()">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editJobLevel()">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeJobLevel()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		职务级别：<input type="text" class="textbox" id="search_joblevel_name" name="search_joblevel_name" style="width: 110px;"/>
	        		包含职位：<input type="text" class="textbox" id="search_positionofjoblevel_name" name="search_positionofjoblevel_name" style="width: 110px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetJobLevel()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchJobLevel()">查询</a>
	        	</div>
	        </div>
	        <div id="JobLevel_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#JobLevel_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">职级信息</span></div>
			        <table>
			        	<tr style="margin-bottom: 10px;">
			        		<td>职务级别：</td>
			        		<td>
			        			<input type="text" id="JobLevel_name" name="JobLevel_name" class="textbox" data-options="required:true" onblur="noBlurJoblevel()" style="width: 180px;"/>
			        			<span id="JobLevel_name_span" style="color: red"></span>
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>职务级别描述：</td>
			        		<td>
			        			<input type="text" id="JobLevel_desc" name="JobLevel_desc" class="textbox" data-options="required:true" onblur="noBlurJoblevel()" style="width: 180px;"/>
			        			<span id="JobLevel_desc_span" style="color: red"></span>
			        		</td>
			        	</tr>
			        </table>
		        </div>
		     </div>		
	        <div id="JobLevel_dlg_buttons" style="text-align: center;">
	        	<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveJobLevel()" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#JobLevel_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div>
	    </div> 
	    <div title="职位信息">   
	        <table id="position_tbo"></table>  
	        <div id="position_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPosition()">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPosition()">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removePosition()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		职位名称：<input type="text" class="textbox" id="search_position_name" name="search_position_name" style="width: 110px;"/>
	        		所属职级：<input type="text" class="textbox" id="search_joblevelforposition_name" name="search_joblevelforposition_name" style="width: 110px;"/>
	        		包含岗位：<input type="text" class="textbox" id="search_postofposition_name" name="search_postofposition_name" style="width: 110px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetPosition()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchPosition()">查询</a>
	        	</div>
	        </div>  
	        <div id="position_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#position_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">职位信息</span></div>
					<table>
						<tr style="margin-bottom: 10px;">
							<td>职位名称：</td>
							<td>
								<input type="text" id="position_name" name="position_name" class="textbox"  onblur="noBlurPosition()"style="width: 180px;"/>
			       			    <span id="position_name_span" style="color: red"></span>
							</td>
						</tr>
						<tr style="margin-bottom: 10px;">
							<td>所属职务级别：</td>
							<td>
								<input id="position_joblevel" name="position_joblevel" style="width: 180px;"/>  
			       			   	<span id="position_joblevel_span" style="color: red"></span>
							</td>
						</tr>
					</table>			        
		        </div>
		     </div>   		
	        <div id="position_dlg_buttons" style="text-align: center;">
	        	<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savePosition()" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#position_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div>  
	    </div> 
	    <div title="岗位信息">   
	        <table id="post_tbo"></table>  
	        <div id="post_tbar" style="padding: 5px;">
	        	<div style="margin-bottom: 5px;">
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPost()">添加</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPost()">修改</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removePost()">删除</a>
	        	</div>
	        	<div style="padding: 0 0 0 7px;color: #333;">
	        		岗位名称：<input type="text" class="textbox" id="search_post_name" name="search_post_name" style="width: 110px;"/>
	        		所属职位：<input type="text" class="textbox" id="search_positionforpost_name" name="search_positionforpost_name" style="width: 110px;"/>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetPost()">重置</a>
	        		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchPost()">查询</a>
	        	</div>
	        </div>  
	        <div id="post_dog" class="easyui-dialog" closed="true" data-options="modal: true" buttons="#post_dlg_buttons" style="width: 450px;">
		      	<div style="margin: 0;padding: 20px 50px;">
			        <div style="margin-bottom: 20px;font-size: 18px;border-bottom: 1px solid #ccc;"><span style="color: blue;">岗位信息</span></div>			    
			        <table>
			        	<tr style="margin-bottom: 10px;">
			        		<td>岗位名称：</td>
			        		<td>
			        			<input type="text" id="post_name" name="post_name" class="textbox"  onblur="noBlurPost()"style="width: 180px;"/>
			       			   	<span id="post_name_span" style="color: red"></span>
			        		</td>
			        	</tr>
			        	<tr style="margin-bottom: 10px;">
			        		<td>所属职位名称：</td>
			        		<td>
			        			<input id="post_position" name="post_position" style="width: 180px;"/>  
			       			   	<span id="post_position_span" style="color: red"></span>
			        		</td>
			        	</tr>
			        </table>
		        </div>
		     </div>	  		
	        <div id="post_dlg_buttons" style="text-align: center;">
	        	<a href="#" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savePost()" style="width: 90px;">保存</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#post_dog').dialog('close')" style="width: 90px;">取消</a>
	        </div>  
	    </div>  
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/JobLevelPage.js"></script>
</body>
</html>