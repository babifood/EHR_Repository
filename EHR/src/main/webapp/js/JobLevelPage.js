var objRowsJobLevel;
var objRowsPosition;
var objRowsPost;
//初始化
$(function(){
	//加载职务级别列表
	loadJobLevel(null,null);
	//加载职位列表
	loadPosition(null,null,null);
	//加载岗位列表
	loadPost(null,null);
	//加载职级下拉框
	loadComboboxJobLevelData();
	//加载职位下拉框
	loadComboboxPositionData();
});
/**
 * -----------------------------------------职级列表function-------------------------------------------
 * @returns
 */
//职级列表
function loadJobLevel(joblevel_name,position_name){
	var url;
	if(joblevel_name!=null&&position_name!=null){
		url=prefix+"/loadJobLevelAll?joblevel_name="+joblevel_name+"&position_name="+position_name;
	}else if(joblevel_name!=null&&position_name==null){
		url=prefix+"/loadJobLevelAll?joblevel_name="+joblevel_name+"&position_name=";
	}else if(joblevel_name==null&&position_name!=null){
		url=prefix+"/loadJobLevelAll?joblevel_name=&position_name="+position_name;
	}else{
		url=prefix+"/loadJobLevelAll?joblevel_name=&position_name=";
	}
	//职级列表
	$("#JobLevel_tbo").datagrid({
		url:url,
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#JobLevel_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"joblevel_id",
				title:"职务级别ID",
				width:100,
			},
			{
				field:"joblevel_name",
				title:"职务级别名称",
				width:200,
			},
			{
				field:"position_name",
				title:"包含职位",
				width:200,
			},
		]],
	});
}
//添加职级
function addJobLevel(){
	$("#JobLevel_dog").dialog("open").dialog("center").dialog("setTitle","添加职级");
	$("#JobLevel_id").val("");
	$("#JobLevel_name").val("");
	$('#JobLevel_id').focus();
}
//修改职级
function editJobLevel(){
	var row = $("#JobLevel_tbo").datagrid("getSelected");
	objRowsJobLevel =row;
	if(row){
		$("#JobLevel_dog").dialog("open").dialog("center").dialog("setTitle","修改职级");
		$("#JobLevel_id").val(row.joblevel_id);
		$("#JobLevel_name").val(row.joblevel_name);
		$('#JobLevel_id').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//删除职级
function removeJobLevel(){
	var row = $("#JobLevel_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			$.ajax({
				url:prefix+'/removeJobLevel',
				type:'post',
				data:{
					joblevel_id:row.joblevel_id
				},
				contentType:"application/x-www-form-urlencoded",
				beforeSend:function(){
					$.messager.progress({
						text:'删除中......',
					});
				},
				success:function(data){
					$.messager.progress('close');
					if(data.status=="success"){
						$.messager.show({
							title:'消息提醒',
							msg:'职级删除成功！',
							timeout:3000,
							showType:'slide'
						});
						loadJobLevel(null,null);
					}else if(data.status=="error"){
						$.messager.alert("消息提示！","该职级下包含职位信息，不允许删除！","warning");
					}else{
						$.messager.alert("消息提示！","职级删除失败，请联系管理员！","warning");
					}
				}
			});
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//职级查询
function searchJobLevel(){
	loadJobLevel($("#search_joblevel_name").val(),$("#search_positionofjoblevel_name").val())
}
//重置职位查询条件
function resetJobLevel(){
	$("#search_joblevel_name").val("");
	$("#search_positionofjoblevel_name").val("");
}
//职级文本框失去焦点事件
function noBlurJoblevel(){
	if(!$("#JobLevel_id").val()==""){
		$("#JobLevel_id_span").html("");
	}
	if(!$("#JobLevel_name").val()==""){
		$("#JobLevel_name_span").html("");		
	}
}
//保存职级
function saveJobLevel(){
	var dog_title=$('#JobLevel_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加职级"){
		data={
				joblevel_id:$('#JobLevel_id').val(),
				joblevel_name:$('#JobLevel_name').val()
		};
		url = prefix+'/saveJobLevel';
		msg = "职级保存成功!";
	}else if(dog_title=="修改职级"){
		data={
				joblevel_id:objRowsJobLevel.joblevel_id,
				joblevel_name:$('#JobLevel_name').val()	
			};
		url = prefix+'/editJobLevel';
		msg = "职级修改成功!";
	}
	if($("#JobLevel_id").val()==""){
		$("#JobLevel_id_span").html("ID不能为空");
		$('#JobLevel_id').focus();
	}else if($("#JobLevel_name").val()==""){
		$("#JobLevel_name_span").html("名称不能为空");
		$('#JobLevel_name').focus();
	}else{
		$.ajax({
			url:url,
			type:'post',
			data:data,
			contentType:"application/x-www-form-urlencoded",
			beforeSend:function(){
				$.messager.progress({
					text:'保存中......',
				});
			},
			success:function(data){
				$.messager.progress('close');
				if(data.status=="success"){
					$.messager.show({
						title:'消息提醒',
						msg:msg,
						timeout:3000,
						showType:'slide'
					});
					$('#JobLevel_dog').dialog('close');
					loadJobLevel(null,null);
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
	
}


/**
 * ---------------------------------------职位列表function---------------------------------------
 * @returns
 */
//加载职位列表
function loadPosition(position_name,JobLevel_name,post_name){
	var url;
	//非非非
	if(position_name!=null&&JobLevel_name!=null&&post_name!=null){
		url=prefix+"/loadPositionAll?Position_name="+position_name+"&JobLevel_name="+JobLevel_name+"&post_name="+post_name;
	}
	//非非空
	else if(position_name!=null&&JobLevel_name!=null&&post_name==null){
		url=prefix+"/loadPositionAll?Position_name="+position_name+"&JobLevel_name="+JobLevel_name+"&post_name=";
	}
	//非空非
	else if(position_name!=null&&JobLevel_name==null&&post_name!=null){
		url=prefix+"/loadPositionAll?Position_name="+position_name+"&JobLevel_name=&post_name="+post_name;
	}
	//非空空
	else if(position_name!=null&&JobLevel_name==null&&post_name==null){
		url=prefix+"/loadPositionAll?Position_name="+position_name+"&JobLevel_name=&post_name=";
	}
	//空非非
	else if(position_name!=null&&JobLevel_name==null&&post_name==null){
		url=prefix+"/loadPositionAll?Position_name=&JobLevel_name="+JobLevel_name+"&post_name="+post_name;
	}
	//空非空
	else if(position_name!=null&&JobLevel_name==null&&post_name==null){
		url=prefix+"/loadPositionAll?Position_name=&JobLevel_name="+JobLevel_name+"&post_name=";
	}
	//空空非
	else if(position_name==null&&JobLevel_name!=null&&post_name==null){
		url=prefix+"/loadPositionAll?Position_name=&JobLevel_name=&post_name="+post_name;
	}
	//空空空
	else{
		url=prefix+"/loadPositionAll?Position_name=&JobLevel_name=&post_name=";
	}
	$("#position_tbo").datagrid({
		url:url,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#position_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			//{
			//	field:"position_id",
			//	title:"职位ID",
			//	width:100,
			//},
			{
				field:"position_name",
				title:"职务名称",
				width:100,
			},
			//{
			//	field:"joblevel_id",
			//	title:"所属职级ID",
			//	width:100,
			//},
			{
				field:"joblevel_name",
				title:"所属职级名称",
				width:100,
			},
			{
				field:"post_name",
				title:"包含岗位名称",
				width:100,
			}
		]],
	});
}
//加载职级下拉框
function loadComboboxJobLevelData(){
	$("#position_joblevel").combobox({
		valueField:'id',
		textField:'text',
		url:prefix+"/loadComboboxJobLevelData",
		editable:false,
		onHidePanel:function(none){
			$("#position_joblevel_span").html("");
		}
	});
}
//添加职位
function addPosition(){
	$("#position_dog").dialog("open").dialog("center").dialog("setTitle","添加职位");
	//$("#position_id").val("");
	$("#position_name").val("");
	$("#position_joblevel").combobox('setValue','')
	$('#position_name').focus();
}
//修改职位
function editPosition(){
	var row = $("#position_tbo").datagrid("getSelected");
	objRowsPosition =row;
	if(row){
		$("#position_dog").dialog("open").dialog("center").dialog("setTitle","修改职位");
		//$("#position_id").val(row.position_id);
		$("#position_name").val(row.position_name);
		$("#position_joblevel").combobox('setValue',row.joblevel_id);
		$("#position_joblevel").combobox('setText',row.joblevel_name);
		$('#position_id').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//删除职位
function removePosition(){
	var row = $("#position_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			$.ajax({
				url:prefix+'/removePosition',
				type:'post',
				data:{
					Position_id:row.position_id
				},
				contentType:"application/x-www-form-urlencoded",
				beforeSend:function(){
					$.messager.progress({
						text:'删除中......',
					});
				},
				success:function(data){
					$.messager.progress('close');
					if(data.status=="success"){
						$.messager.show({
							title:'消息提醒',
							msg:'职位删除成功！',
							timeout:3000,
							showType:'slide'
						});
						loadPosition(null,null,null);
					}else if(data.status=="error"){
						$.messager.alert("消息提示！","该职位下包含岗位信息，不允许删除！","warning");
					}else{
						$.messager.alert("消息提示！","职位删除失败，请联系管理员！","warning");
					}
				}
			});
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//职位查询
function searchPosition(){
	loadPosition($("#search_position_name").val(),$("#search_joblevelforposition_name").val(),$("#search_postofposition_name").val())
}
//重置职位查询条件
function resetPosition(){
	$("#search_position_name").val("");
	$("#search_joblevelforposition_name").val("");
	$("#search_postofposition_name").val("");
}
//职位文本校验
function checkDataPosition(){
	if($("#position_name").val()==""){
		$("#position_name_span").html("职位名称不能为空");
		$('#position_name').focus();
		return false;
	}
	if($("#position_joblevel").combobox('getValue')==""){
		$("#position_joblevel_span").html("所属职级不能为空");
		$('#position_joblevel').focus();
		return false;
	}
	return true;
	
}
//职位文本框失去焦点事件
function noBlurPosition(){
	if(!$("#position_name").val()==""){
		$("#position_name_span").html("");		
	}
	if(!$("#position_joblevel").val()==""){
		$("#position_joblevel_span").html("");		
	}
}
//保存职位
function savePosition(){
	var dog_title=$('#position_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加职位"){
		data={
				Position_name:$("#position_name").val(),
				joblevel_id:$("#position_joblevel").combobox('getValue'),
		};
		url = prefix+'/savePosition';
		msg = "职位保存成功!";
	}else if(dog_title=="修改职位"){
		data={
				position_id:objRowsPosition.position_id,
				Position_name:$("#position_name").val(),
				joblevel_id:$("#position_joblevel").combobox('getValue'),
			};
		url = prefix+'/editPosition';
		msg = "职位修改成功!";
	}
	if(checkDataPosition()){
		$.ajax({
			url:url,
			type:'post',
			data:data,
			contentType:"application/x-www-form-urlencoded",
			beforeSend:function(){
				$.messager.progress({
					text:'保存中......',
				});
			},
			success:function(data){
				$.messager.progress('close');
				if(data.status=="success"){
					$.messager.show({
						title:'消息提醒',
						msg:msg,
						timeout:3000,
						showType:'slide'
					});
					$('#position_dog').dialog('close');
					loadPosition(null,null,null);
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
	
}/**
 * ---------------------------------------岗位列表function---------------------------------------
 * @returns
 */
//加载岗位列表
function loadPost(post_name,position_name){
	var url;
	if(post_name!=null&&position_name!=null){
		url=prefix+"/loadPostAll?post_name="+post_name+"&position_name="+position_name;
	}else if(post_name!=null&&position_name==null){
		url=prefix+"/loadPostAll?post_name="+post_name+"&position_name=";
	}else if(post_name==null&&position_name!=null){
		url=prefix+"/loadPostAll?post_name=&position_name="+position_name;
	}else{
		url=prefix+"/loadPostAll?post_name=&position_name=";
	}
	$("#post_tbo").datagrid({
		url:url,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#post_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			//{
			//	field:"post_id",
			//	title:"岗位ID",
			//	width:100,
			//},
			{
				field:"post_name",
				title:"岗位名称",
				width:100,
			},
			//{
			//	field:"position_id",
			//	title:"所属职位ID",
			//	width:100,
			//},
			{
				field:"position_name",
				title:"所属职位名称",
				width:100,
			}
		]],
	});
}
//加载职位下拉框
function loadComboboxPositionData(){
	$("#post_position").combobox({
		valueField:'id',
		textField:'text',
		url:prefix+"/loadComboboxPositionData",
		editable:false,
		onHidePanel:function(none){
			$("#post_position_span").html("");
		}
	});
}
//添加岗位
function addPost(){
	$("#post_dog").dialog("open").dialog("center").dialog("setTitle","添加岗位");
	$("#post_name").val("");
	$("#post_position").combobox('setValue','')
	$('#post_name').focus();
}
//修改岗位
function editPost(){
	var row = $("#post_tbo").datagrid("getSelected");
	objRowsPost =row;
	if(row){
		$("#post_dog").dialog("open").dialog("center").dialog("setTitle","修改岗位");
		$("#post_name").val(row.post_name);
		$("#post_position").combobox('setValue',row.position_id);
		$("#post_position").combobox('setText',row.position_name);
		$('#post_name').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//删除岗位
function removePost(){
	var row = $("#post_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			$.ajax({
				url:prefix+'/removePost',
				type:'post',
				data:{
					post_id:row.post_id
				},
				contentType:"application/x-www-form-urlencoded",
				beforeSend:function(){
					$.messager.progress({
						text:'删除中......',
					});
				},
				success:function(data){
					$.messager.progress('close');
					if(data.status=="success"){
						$.messager.show({
							title:'消息提醒',
							msg:'岗位删除成功！',
							timeout:3000,
							showType:'slide'
						});
						loadPost(null,null);
					}else{
						$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
					}
				}
			});
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//岗位查询
function searchPost(){
	loadPost($("#search_post_name").val(),$("#search_positionforpost_name").val())
}
//重置岗位查询条件
function resetPost(){
	$("#search_post_name").val("");
	$("#search_positionforpost_name").val("");
}
//岗位文本校验
function checkDataPost(){
	if($("#post_name").val()==""){
		$("#post_name_span").html("岗位名称不能为空");
		$('#post_name').focus();
		return false;
	}
	if($("#post_position").combobox('getValue')==""){
		$("#post_position_span").html("所属职位不能为空");
		$('#post_position').focus();
		return false;
	}
	return true;
	
}
//岗位文本框失去焦点事件
function noBlurPost(){
	if(!$("#post_name").val()==""){
		$("#post_name_span").html("");		
	}
	if(!$("#post_position").val()==""){
		$("#post_position_span").html("");		
	}
}
//保存岗位
function savePost(){
	var dog_title=$('#post_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加岗位"){
		data={
				post_name:$("#post_name").val(),
				position_id:$("#post_position").combobox('getValue'),
		};
		url = prefix+'/savePost';
		msg = "岗位保存成功!";
	}else if(dog_title=="修改岗位"){
		data={
				post_id:objRowsPost.post_id,
				post_name:$("#post_name").val(),
				position_id:$("#post_position").combobox('getValue'),
			};
		url = prefix+'/editPost';
		msg = "岗位修改成功!";
	}
	if(checkDataPost()){
		$.ajax({
			url:url,
			type:'post',
			data:data,
			contentType:"application/x-www-form-urlencoded",
			beforeSend:function(){
				$.messager.progress({
					text:'保存中......',
				});
			},
			success:function(data){
				$.messager.progress('close');
				if(data.status=="success"){
					$.messager.show({
						title:'消息提醒',
						msg:msg,
						timeout:3000,
						showType:'slide'
					});
					$('#post_dog').dialog('close');
					loadPost(null,null);
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
	
}