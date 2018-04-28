var objRows;
//初始化
$(function(){
	//加载角色列表
	loadRole();
	//用户列表
	loadUsers();
	//加载树状菜单
	loadMenuTree();
	//加载菜单授权页面的角色列表
	loadMenuGrid();
	
});
/**
 * -----------------------------------------添加角色function-------------------------------------------
 * @returns
 */
//角色列表
function loadRole(){
	//角色列表
	$("#role_tbo").datagrid({
		url:prefix+"//loadRolesAll",
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#role_tbar",
		singleSelect:true,
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
}
//添加角色
function addRole(){
	$("#role_dog").dialog("open").dialog("center").dialog("setTitle","添加角色");
	$("#role_name").val("");
	$("#role_desc").val("");
	$(":radio[name='role_radio'][value='1']").prop("checked", "checked");
	$('#role_name').focus();
	
}
//修改角色
function editRole(){
	var row = $("#role_tbo").datagrid("getSelected");
	objRows =row;
	if(row){
		$("#role_dog").dialog("open").dialog("center").dialog("setTitle","修改角色");
		$("#role_name").val(row.role_name);
		$("#role_desc").val(row.role_desc);
		$(":radio[name='role_radio'][value='" + row.state + "']").prop("checked", "checked");
		$('#role_name').focus();
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
//文本框失去焦点事件
function noBlur(){
	if(!$("#role_name").val()==""){
		$("#role_name_apan").html("");
	}
	if(!$("#role_desc").val()==""){
		$("#role_desc_apan").html("");		
	}
}
//保存角色
function saveRole(){
	var dog_title=$('#role_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加角色"){
		data={
			role_name:$('#role_name').val(),
			role_desc:$('#role_desc').val(),
			state:$("input[name='role_radio']:checked").val()	
		};
		url = prefix+'/saveRole';
		msg = "角色保存成功!";
	}else if(dog_title=="修改角色"){
		data={
				role_id:objRows.role_id,
				role_name:$('#role_name').val(),
				role_desc:$('#role_desc').val(),
				state:$("input[name='role_radio']:checked").val()	
			};
		url = prefix+'/editRole';
		msg = "角色修改成功!";
	}
	if($("#role_name").val()==""){
		$("#role_name_apan").html("名称不能为空");
		$('#role_name').focus();
	}else if($("#role_desc").val()==""){
		$("#role_desc_apan").html("描述不能为空");
		$('#role_desc').focus();
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
					$('#role_dog').dialog('close');
					loadRole();
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
	
}
/**
 * ---------------------------------------添加用户function---------------------------------------
 * @returns
 */
//加载用户列表
function loadUsers(){
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
}
/**
 * ------------------------------------------菜单授权function-------------------------------------------------
 * @returns
 */
//加载菜单授权页面的树状菜单
function loadMenuTree(){
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
}
//加载菜单授权页面的角色列表
function loadMenuGrid(){
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
}