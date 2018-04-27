$(function(){
	//角色列表
	$("#role_tbo").datagrid({
		url:prefix+"//loadRolesAll",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#role_tbar",
		columns:[[
			{
				field:"role_id",
				title:"用户编号",
				width:100,
			},
			{
				field:"role_name",
				title:"角色名称",
				width:100,
			},
			{
				field:"role_desc",
				title:"角色描述",
				width:200,
			},
			{
				field:"state",
				title:"状态",
				width:100,
				formatter:function(value){
					if(value=="0"){
						return "禁用";
					}else if(value=="1"){
						return "启用";
					}
				},
			}
		]],
	});
	//角色名称
	$('#role_name').validatebox({
		required : true,
		missingMessage :'角色名称不能为空!',
	});
	//角色描述
	$('#role_desc').validatebox({
		required : true,
		missingMessage :'角色描述不能为空!',
	});
	
	
	//用户列表
	$("#user_tbo").datagrid({
		url:prefix+"/loadUsersAll",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#user_tbar",
		columns:[[
			{
				field:"user_id",
				title:"用户编号",
				width:100,
			},
			{
				field:"user_name",
				title:"用户账号",
				width:100,
			},
			{
				field:"password",
				title:"用户账号",
				width:100,
				formatter:function(value){
					return "******";
				},
			},
			{
				field:"show_name",
				title:"用户名称",
				width:100,
			},
			{
				field:"e_mail",
				title:"邮箱",
				width:100,
			},
			{
				field:"phone",
				title:"电话",
				width:100,
			},
			{
				field:"state",
				title:"状态",
				width:100,
				formatter:function(value){
					if(value=="0"){
						return "禁用";
					}else if(value=="1"){
						return "启用";
					}
				},
			}
		]],
	});
	//菜单树
	$('#menu_ul').tree({
		url:prefix+"/loadTerr",
		lines:true,
		checkbox:true,
		onClick:function(node){
			
		},
		onLoadSuccess:function(node,data){
		  if (data) {
			  $('#menu_ul').tree('expandAll');
	      }
		}
	});
	//角色列表
	$("#menu_tbo").datagrid({
		url:prefix+"//loadRolesAll",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		columns:[[
			{
				field:"role_id",
				title:"用户编号",
				width:100,
			},
			{
				field:"role_name",
				title:"角色名称",
				width:100,
			},
			{
				field:"role_desc",
				title:"角色描述",
				width:200,
			},
			{
				field:"state",
				title:"状态",
				width:100,
				formatter:function(value){
					if(value=="0"){
						return "禁用";
					}else if(value=="1"){
						return "启用";
					}
				},
			}
		]],
	});
});
//添加角色
function addRole(){
	$("#role_dog").dialog("open").dialog("center").dialog("setTitle","添加角色");
	//页面加载时光标定位到输入框
	if(!$('#role_name').validatebox('isValid')){
		$('#role_name').focus();
	}else if(!$('#role_desc').validatebox('isValid')){
		$('#role_desc').focus();
	}
	
}
//修改角色
function editRole(){
	var row = $("#role_tbo").datagrid("getSelected");
	if(row){
		$("#role_dog").dialog("open").dialog("center").dialog("setTitle","修改角色");
		
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//删除角色
function removeRole(){
	var row = $("#role_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//保存角色
function saveRole(){
	if(!$('#role_name').validatebox('isValid')){
		$('#role_name').focus();
	}else if(!$('#role_desc').validatebox('isValid')){
		$('#role_desc').focus();
	}else{
		$.ajax({
			url:prefix+'/login',
			type:'post',
			data:{
				user_name:$('#userName').val(),
				password:$('#password').val(),
			},
			contentType:"application/x-www-form-urlencoded",
			beforeSend:function(){
				$.messager.progress({
					text:'登陆中......',
				});
			},
			success:function(data){
				$.messager.progress('close');
				if(data.status=="error"){
					$.messager.alert("消息提示！","用户名密码错误！","warning",function(){
						$('#password').select();
					});
				}else if(data.status=="success"){
					window.location.href=prefix+"/redirect?pageName=HomePage";
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
}