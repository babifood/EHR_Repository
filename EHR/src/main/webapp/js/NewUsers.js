var objRowsRole;
var objRowsUser;
var objMenuID;
//初始化
$(function(){
	//加载Tabs
	loadTabs();
});
//加载Tabs
function loadTabs(){
	$("#role_user_menu").tabs({
		fit:true,
		onSelect:function(title,index){
			if(title=="添加角色"){
				//加载角色列表
				loadRole(null);
				loadCombotree();
			}else if(title=="添加用户"){
				//用户列表
				loadUsers(null,null);
				//加载角色下拉框
				loadCombobox();
			}else if(title=="菜单授权"){
				//加载树状菜单
				loadMenuTree();
				//加载菜单授权页面的角色列表
				loadMenuGrid();
			}
		}
	})
}
/**
 * -----------------------------------------添加角色function-------------------------------------------
 * @returns
 */
//角色列表
function loadRole(data){
	var url;
	if(data){
		url=prefix+"//loadRolesAll?role_name="+data;
	}else{
		url=prefix+"//loadRolesAll?role_name=";
	}
	//角色列表
	$("#role_tbo").datagrid({
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
		toolbar:"#role_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
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
				field:"organization_name",
				title:"所属机构",
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
		loadFilter:function(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
	            data = {
	                total: data.length,
	                rows: data
	            }
	        }
	        var dg = $(this);
	        var opts = dg.datagrid('options');
	        var pager = dg.datagrid('getPager');
	        pager.pagination({
	            onSelectPage:function(pageNum, pageSize){
	                opts.pageNumber = pageNum;
	                opts.pageSize = pageSize;
	                pager.pagination('refresh',{
	                    pageNumber:pageNum,
	                    pageSize:pageSize
	                });
	                dg.datagrid('loadData',data);
	            }
	        });
	        if (!data.originalRows){
	            data.originalRows = (data.rows);
	        }
	        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	        var end = start + parseInt(opts.pageSize);
	        data.rows = (data.originalRows.slice(start, end));
	        return data;
		}
	});
}
//加载下拉树
function loadCombotree(){
	$('#role_organization').combobox({    
	    url: prefix+"/loadComboboxOrga",
	    editable:false,
	    valueField:'dept_code',    
	    textField:'dept_name',
		onHidePanel:function(none){
			$("#role_organization_span").html("");
		},
	});
}
//添加角色
function addRole(){
	$("#role_dog").dialog("open").dialog("center").dialog("setTitle","添加角色");
	$("#role_name").val("");
	$("#role_desc").val("");
	$("#role_organization").combobox("setValue",""),
	$("#role_organization").combobox("setText","")
	$("#role_radio_yes").prop("checked", "checked");
	$('#role_name').focus();
}
//修改角色
function editRole(){
	var row = $("#role_tbo").datagrid("getSelected");
	objRowsRole =row;
	if(row){
		$("#role_dog").dialog("open").dialog("center").dialog("setTitle","修改角色");
		$("#role_name").val(row.role_name);
		$("#role_desc").val(row.role_desc);
		$("#role_organization").combobox("setValue",row.organization_code),
		$("#role_organization").combobox("setText",row.organization_name)
		if(row.state=='0'){
			$("#role_radio_no").prop("checked", "checked");
		}else if(row.state=='1'){
			$("#role_radio_yes").prop("checked", "checked");
		}
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
			if(r){
				$.ajax({
					url:prefix+'/removeRole',
					type:'post',
					data:{
						role_id:row.role_id
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
								msg:'角色删除成功！',
								timeout:3000,
								showType:'slide'
							});
							loadRole(null);
						}else{
							$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
						}
					}
				});
			}
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//角色查询
function searchRole(){
	loadRole($("#search_role_name").val())
}
//文本框失去焦点事件
function noBlur(){
	if(!$("#role_name").val()==""){
		$("#role_name_span").html("");
	}
	if(!$("#role_desc").val()==""){
		$("#role_desc_span").html("");		
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
			state:$("input[name='role_radio']:checked").val(),
			organization_code:$("#role_organization").combotree("getValue"),
			organization_name:$("#role_organization").combotree("getText")
		};
		url = prefix+'/saveRole';
		msg = "角色保存成功!";
	}else if(dog_title=="修改角色"){
		data={
				role_id:objRowsRole.role_id,
				role_name:$('#role_name').val(),
				role_desc:$('#role_desc').val(),
				state:$("input[name='role_radio']:checked").val(),
				organization_code:$("#role_organization").combotree("getValue"),
				organization_name:$("#role_organization").combotree("getText")
			};	
		url = prefix+'/editRole';
		msg = "角色修改成功!";
	}
	if($("#role_name").val()==""){
		$("#role_name_span").html("名称不能为空");
		$('#role_name').focus();
	}else if($("#role_desc").val()==""){
		$("#role_desc_span").html("描述不能为空");
		$('#role_desc').focus();
	}else if($("#role_organization").combotree("getValue")==""){
		$("#role_organization_span").html("机构不能为空");
		$('#role_organization').focus();
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
					loadRole(null);
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
function loadUsers(account,username){
	var url;
	if(account!=null&&username!=null){
		url=prefix+"/loadUsersAll?user_name="+account+"&show_name="+username;
	}else if(account!=null&&username==null){
		url=prefix+"/loadUsersAll?user_name="+account+"&show_name=";
	}else if(account==null&&username!=null){
		url=prefix+"/loadUsersAll?user_name=&show_name="+username;
	}else{
		url=prefix+"/loadUsersAll?user_name=&show_name=";
	}
	$("#user_tbo").datagrid({
		url:url,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#user_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"user_name",
				title:"用户账号",
				width:80,
			},
			{
				field:"show_name",
				title:"用户名称",
				width:80,
			},
			{
				field:"role_name",
				title:"用户角色",
				width:200,
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
				width:80,
				formatter:function(value){
					if(value=="0"){
						return "禁用";
					}else if(value=="1"){
						return "启用";
					}
				},
			}
		]],
		loadFilter:function(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
	            data = {
	                total: data.length,
	                rows: data
	            }
	        }
	        var dg = $(this);
	        var opts = dg.datagrid('options');
	        var pager = dg.datagrid('getPager');
	        pager.pagination({
	            onSelectPage:function(pageNum, pageSize){
	                opts.pageNumber = pageNum;
	                opts.pageSize = pageSize;
	                pager.pagination('refresh',{
	                    pageNumber:pageNum,
	                    pageSize:pageSize
	                });
	                dg.datagrid('loadData',data);
	            }
	        });
	        if (!data.originalRows){
	            data.originalRows = (data.rows);
	        }
	        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	        var end = start + parseInt(opts.pageSize);
	        data.rows = (data.originalRows.slice(start, end));
	        return data;
		}
	});
}
//加载角色下拉框
function loadCombobox(){
//	$("#user_role").combobox({
//		valueField:'id',
//		textField:'text',
//		url:prefix+"/loadComboboxData",
//		editable:false,
//		onHidePanel:function(none){
//			$("#user_role_span").html("");
//		},
//		separator:'/',
//		multiple:true
//	});
	$('#user_role').combogrid({    
	    panelWidth:230,    
	    idField:'role_id',    
	    textField:'role_name',    
	    url:prefix+"/loadComboboxData",    
	    columns:[[    
	        {
	        	field:'role_name',
	        	title:'角色名称',
	        	width:100
	        },    
	        {
	        	field:'organization_name',
	        	title:'机构名称',
	        	width:100
	        }   
	    ]],
	    separator:'/',
	    multiple:true
	});  

}
//添加用户
function addUser(){
	$("#user_dog").dialog("open").dialog("center").dialog("setTitle","添加用户");
	$("#user_name").val("");
	$("#password").val("");
	$("#show_name").val("");
	$("#user_role").combogrid('clear');
	$("#user_role").combogrid('setValue','');
	$("#phone").val("");
	$("#e_mail").val("");
	$("#user_radio_yes").prop("checked", "checked");
	$('#user_name').focus();
}
//修改用户
function editUser(){
	var row = $("#user_tbo").datagrid("getSelected");
	objRowsUser =row;
	if(row){
		$("#user_dog").dialog("open").dialog("center").dialog("setTitle","修改用户");
		$("#user_name").val(row.user_name);
		$("#password").val('000000');
		$("#show_name").val(row.show_name);
		$("#user_role").combogrid('clear');
		$("#user_role").combogrid('setValues',row.role_id);
		$("#user_role").combogrid('setText',row.role_name);
		$("#phone").val(row.phone);
		$("#e_mail").val(row.e_mail);
		if(row.state=='0'){
			$("#user_radio_no").prop("checked", "checked");
		}else if(row.state=='1'){
			$("#user_radio_yes").prop("checked", "checked");
		}
		$('#user_name').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//删除角色
function removeUser(){
	var row = $("#user_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			if(r){
				$.ajax({
					url:prefix+'/removeUser',
					type:'post',
					data:{
						user_id:row.user_id
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
								msg:'用户删除成功！',
								timeout:3000,
								showType:'slide'
							});
							loadUsers(null,null);
						}else{
							$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
						}
					}
				});
			}
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//角色查询
function searchUser(){
	loadUsers($("#search_user_name").val(),$("#search_show_name").val())
}
//文本校验
function checkData(){
	if($("#user_name").val()==""){
		$("#user_name_span").html("账号不能为空");
		$('#user_name').focus();
		return false;
	}
	if($("#password").val()==""){
		$("#password_span").html("密码不能为空");
		$('#password').focus();
		return false;
	}
	if($("#show_name").val()==""){
		$("#show_name_span").html("名称不能为空");
		$('#show_name').focus();
		return false;
	}
	if($("#user_role").combogrid('getValues')==""){
		$("#user_role_span").html("角色不能为空");
		$('#user_role').focus();
		return false;
	}
//	alert($("#user_role").val());
//	alert($("#user_role").combobox('getText'));
//	console.log($("#user_role").combobox('getValues'));
	return true;
	
}
//文本框失去焦点事件
function noBlurUser(){
	if(!$("#user_name").val()==""){
		$("#user_name_span").html("");
	}
	if(!$("#password").val()==""){
		$("#password_span").html("");		
	}
	if(!$("#show_name").val()==""){
		$("#show_name_span").html("");		
	}
}
//获取Combobox的值
function getComboboxValues(){
	var boxValArr = [];
	var boxVal = $('#user_role').combogrid('getValues');
	var boxtext = $('#user_role').combogrid('getText');
	var valarr = boxVal.join(',').split(',');
	var textarr = boxtext.split('/');
	for(var i = 0;i<valarr.length;i++){
		if(valarr[i]==""){
			continue;
		}else{
			boxValArr.push(
				{role_id:valarr[i],role_name:textarr[i]}
			);
		}
	}
	console.log(boxValArr);
	return boxValArr;
}
//保存角色
function saveUser(){	
	var dog_title=$('#user_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加用户"){
		data={
				user_id:"",
				user_name:$("#user_name").val(),
				password:$("#password").val(),
				show_name:$("#show_name").val(),
				roleList:getComboboxValues(),
				phone:$("#phone").val(),
				e_mail:$("#e_mail").val(),
				state:$("input[name='user_radio']:checked").val()
		};
		url = prefix+'/saveUser';
		msg = "用户保存成功!";
	}else if(dog_title=="修改用户"){
		data={
				user_id:objRowsUser.user_id,
				user_name:$("#user_name").val(),
				password:$("#password").val()=="000000"?objRowsUser.password+"/"+objRowsUser.password:objRowsUser.password+"/"+$("#password").val(),
				show_name:$("#show_name").val(),
				roleList:getComboboxValues(),
				phone:$("#phone").val(),
				e_mail:$("#e_mail").val(),
				state:$("input[name='user_radio']:checked").val()
			};
		url = prefix+'/editUser';
		msg = "用户修改成功!";
	}
	if(checkData()){
		$.ajax({
			url:url,
			type:'post',
			data:JSON.stringify(data),
			contentType:"application/json",
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
					$('#user_dog').dialog('close');
					loadUsers(null,null);
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
	
}
/**
 * ------------------------------------------菜单授权function-------------------------------------------------
 * @returns
 */
//加载菜单授权页面的树状菜单
function loadMenuTree(){
	//菜单树
	$('#menu_ul').tree({
		url:prefix+"/loadCheckTreeMenu",
		lines:true,
		checkbox:true,
		toggle:true,
		cascadeCheck:false,
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
		url:prefix+"/loadRolesAll?role_name=",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		rownumbers:true,
		singleSelect:true,
		columns:[[
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
				field:"organization_name",
				title:"机构名称",
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
		onClickRow:function(rowIndex, rowData){
			//取消所有菜单选中
			if(objMenuID){
				for(var j=0;j<objMenuID.length;j++){
					var node = $('#menu_ul').tree('find', objMenuID[j].menu_tbo_id); 
					$("#menu_ul").tree('uncheck',node.target);
				}
			}
			//选中对应角色授权菜单
			$.post(prefix+"/getMenuIdsCheck",{role_id:rowData.role_id},function(data){
				if(data){
					for(var i=0;i<data.length;i++){
						var node = $('#menu_ul').tree('find', data[i].menu_tbo_id); 
			             $('#menu_ul').tree('check',node.target);
					}
					objMenuID=data;
				}			
			  });
		},loadFilter:function(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
	            data = {
	                total: data.length,
	                rows: data
	            }
	        }
	        var dg = $(this);
	        var opts = dg.datagrid('options');
	        var pager = dg.datagrid('getPager');
	        pager.pagination({
	            onSelectPage:function(pageNum, pageSize){
	                opts.pageNumber = pageNum;
	                opts.pageSize = pageSize;
	                pager.pagination('refresh',{
	                    pageNumber:pageNum,
	                    pageSize:pageSize
	                });
	                dg.datagrid('loadData',data);
	            }
	        });
	        if (!data.originalRows){
	            data.originalRows = (data.rows);
	        }
	        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	        var end = start + parseInt(opts.pageSize);
	        data.rows = (data.originalRows.slice(start, end));
	        return data;
		}
	});
}
//检查数据是否选中
function ckedDataMenuRole(row,nodes){
	if(row==null){
		$.messager.alert("消息提示！","请选择一个角色！","info");
		return false;
	}
	if(nodes.length<=0){
		$.messager.alert("消息提示！","请勾选对应的菜单！","info");
		return false;
	}
	return true;
}
//保存授权菜单
function saveMenuRole(){
	var nodes = $('#menu_ul').tree('getChecked');
	var row = $("#menu_tbo").datagrid("getSelected");
	if(ckedDataMenuRole(row,nodes)){
		var params = [];
		var param =[];
		for(var i=0;i<nodes.length;i++){
			var param ={};
			param.id=nodes[i].id;
			param.text=nodes[i].text;
			param.authority_code=nodes[i].authority_code;
			param.flag=nodes[i].flag;
			param.role_id=row.role_id;
			param.role_name=row.role_name;
			params.push(param);
		}
		$.ajax({
			url:prefix+"/saveMenuRole",
			type:'post',
			data:JSON.stringify(params),
			contentType:"application/json",
			dataTupe:"json",
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
						msg:"菜单授权成功!",
						timeout:3000,
						showType:'slide'
					});
					$('#user_dog').dialog('close');
					loadUsers(null,null);
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
}