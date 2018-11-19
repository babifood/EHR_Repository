//@ sourceURL=RewardPunishment.js
var objRowsRewardPunishment;
var objRAPItem;
//初始化
$(function(){
	loadTabs();
});
//加载Tabs
function loadTabs(){
	$("#rewardRoPunish_Tabs").tabs({
		fit:true,
		onSelect:function(title,index){
			if(title=="奖惩记录"){
				//加载奖惩记录列表
				loadRewardPunishment("","");
				//加载奖惩项目下拉框
				loadComboboxRAPItemData();
			}else if(title=="奖惩项目"){
				//加载奖惩项目列表
				loadRAPItem("","");
			}
		}
	})
}
/**
 * -----------------------------------------奖惩记录列表function-------------------------------------------
 * @returns
 */
//奖惩记录列表
function loadRewardPunishment(searchKey,searchVal){
	var url=prefix+"/loadRewardPunishment?searchKey="+searchKey+"&searchVal="+searchVal;
	//奖惩项目列表
	$("#RewardPunishment_tbo").datagrid({
		url:url,
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize : 20,
		pageList : [20, 30, 50 ],
		pageNumber:1,
		toolbar:"#RewardPunishment_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"rap_category_name",
				title:"奖惩项目类别",
				width:200,
			},
			{
				field:"rap_item_name",
				title:"奖惩项目名称",
				width:200,
			},
			{
				field:"rap_date",
				title:"奖惩日期",
				width:200,
			},
			{
				field:"rap_reason",
				title:"奖惩原因",
				width:200,
			},
			{
				field:"rap_money",
				title:"奖惩金额",
				width:200,
			},
			{
				field:"rap_p",
				title:"奖惩人员",
				width:200,
			},
			{
				field:"rap_proposer",
				title:"提议人",
				width:200,
			},
			{
				field:"rap_desc",
				title:"其他说明",
				width:200,
			},
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
//添加奖惩记录
function addRewardPunishment(){
	//从新加载奖惩项目
	loadComboboxRAPItemData();
	$("#RewardPunishment_dog").dialog("open").dialog("center").dialog("setTitle","添加奖惩记录");
	$("#rap_category").combobox('setValue','');
	$("#rap_item").combobox('setValue','');
	$('#rap_date').datebox('setValue','');
	$("#rap_reason").val("");
	$("#rap_money").numberbox('setValue','');
	$("#rap_proposer_id").val("");
	$("#rap_proposer").val("");
	$("#rap_p_id").val("");
	$("#rap_p").val("");
	$("#rap_desc").val("");
	$('#rap_category').focus();
}
//修改奖惩记录
function editRewardPunishment(){
	var row = $("#RewardPunishment_tbo").datagrid("getSelected");
	objRowsRewardPunishment =row;
	if(row){
		//从新加载奖惩项目
		loadComboboxRAPItemData();
		$("#RewardPunishment_dog").dialog("open").dialog("center").dialog("setTitle","修改奖惩记录");
		$("#rap_category").combobox('setValue',row.rap_category_id);
		$("#rap_category").combobox('setText',row.rap_category_name);
		$("#rap_item").combobox('setValue',row.rap_item_id);
		$("#rap_item").combobox('setText',row.rap_item_name);
		$('#rap_date').datebox('setValue',row.rap_date);
		$("#rap_reason").val(row.rap_reason);
		$("#rap_money").numberbox('setValue',row.rap_money);
		$("#rap_proposer_id").val(row.rap_proposer_id);//提议人ID
		$("#rap_proposer").val(row.rap_proposer);//提议人，显示人名
		$("#rap_p_id").val(row.rap_p_id);//奖惩人ID		
		$("#rap_p").val(row.rap_p);//奖惩人，显示人名
		$("#rap_desc").val(row.rap_desc);
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//奖惩记录目查询
function searchRewardPunishment(value,name){
	if(value!=""){
		if(value=="奖"){
			value = "0";
		}else if(value=="惩"){
			value = "1";
		}
		loadRewardPunishment(name,value);
	}
}
//奖惩记录文本校验
function checkDataRewardPunishment(){
	if($("#rap_category").combobox('getValue')==""){
		$.messager.alert("消息提示！","奖惩类别不能为空!","warning");
		return false;
	}
	if($("#rap_item").combobox('getValue')==""){
		$.messager.alert("消息提示！","奖惩项目不能为空!","warning");
		return false;
	}
	if($("#rap_date").datebox('getValue')==""){
		$.messager.alert("消息提示！","奖惩日期不能为空!","warning");
		return false;
	}
	if($("#rap_reason").val()==""){
		$.messager.alert("消息提示！","奖惩原因不能为空!","warning");
		$('#rap_reason').focus();
		return false;
	}
	if($("#rap_money").numberbox('getValue')==""){
		$.messager.alert("消息提示！","奖惩金额不能为空!","warning");
		return false;
	}
	if($("#rap_proposer").val()==""){
		$.messager.alert("消息提示！","提议人不能为空!","warning");
		return false;
	}
	if($("#rap_p").val()==""){
		$.messager.alert("消息提示！","奖惩人员不能为空!","warning");
		return false;
	}
	return true;
}
//保存奖惩记录
function saveRewardPunishment(){
	var dog_title=$('#RewardPunishment_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加奖惩记录"){
		data={			
				rap_category:$("#rap_category").combobox('getValue',''),
				rap_item:$("#rap_item").combobox('getValue',''),
				rap_date:$('#rap_date').datebox('getValue'),
				rap_reason:$('#rap_reason').val(),
				rap_money:Number($('#rap_money').numberbox('getValue')),
				rap_proposer_id:$('#rap_proposer_id').val(),//提议人ID
				rap_proposer:$('#rap_proposer').val(),//提议人姓名
				rap_p_id:$('#rap_p_id').val(),//奖惩人员ID
				rap_p:$('#rap_p').val(),//奖惩人员姓名
				rap_desc:$('#rap_desc').val(),
		};
		url = prefix+'/saveRewardPunishment';
		msg = "添加奖惩记录成功!";
	}else if(dog_title=="修改奖惩记录"){
		data={
				rap_id:objRowsRewardPunishment.rap_id,
				rap_category:$("#rap_category").combobox('getValue',''),
				rap_item:$("#rap_item").combobox('getValue',''),
				rap_date:$('#rap_date').datebox('getValue'),
				rap_reason:$("#rap_reason").val(),
				rap_money:Number($("#rap_money").numberbox('getValue')),
				rap_proposer_id:$('#rap_proposer_id').val(),//提议人ID
				rap_proposer:$('#rap_proposer').val(),//提议人姓名
				rap_p_id:$('#rap_p_id').val(),//奖惩人员ID
				rap_p:$('#rap_p').val(),//奖惩人员姓名
				rap_desc:$("#rap_desc").val(),
			};
		url = prefix+'/editRewardPunishment';
		msg = "修改奖惩记录成功!";
	}
	if(checkDataRewardPunishment()){
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
					$('#RewardPunishment_dog').dialog('close');
					loadRewardPunishment("","");
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
}
//删除奖惩记录
function removeRewardPunishment(){
	var row = $("#RewardPunishment_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			$.ajax({
				url:prefix+'/removeRewardPunishment',
				type:'post',
				data:{
					rap_id:row.rap_id
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
							msg:'奖惩记录删除成功！',
							timeout:3000,
							showType:'slide'
						});
						loadRewardPunishment("","");
					}else if(data.status=="error"){
						$.messager.alert("消息提示！","该奖惩记录已归档，不允许删除！","warning");
					}else{
						$.messager.alert("消息提示！","奖惩记录删除失败，请联系管理员！","warning");
					}
				}
			});
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//加载奖惩项目下拉框
function loadComboboxRAPItemData(){
	$("#rap_item").combobox({
		valueField:'id',
		textField:'text',
		url:prefix+"/loadComboboxRAPItemData",
		editable:false,
	});
}
//调personSelectionWindow
function saveSelectPerson(){
	saveSelectPerson1();
}
/** -----------------------------------------奖惩项目列表function-------------------------------------------
 * @returns
 */
//奖惩项目列表
function loadRAPItem(searchKey,searchVal){
	var url=prefix+"/loadRAPItemAll?searchKey="+searchKey+"&searchVal="+searchVal;
	//奖惩项目列表
	$("#RAPItem_tbo").datagrid({
		url:url,
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize : 20,
		pageList : [20, 30, 50 ],
		pageNumber:1,
		toolbar:"#RAPItem_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"item_name",
				title:"奖惩项目名称",
				width:200,
			},
			{
				field:"category_name",
				title:"奖惩项目类别",
				width:200,
			},
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
//添加奖惩项目
function addRAPItem(){
	$("#RAPItem_dog").dialog("open").dialog("center").dialog("setTitle","添加奖惩项目");
	$("#item_name").val("");
	$("#category").combobox('setValue','');
	$('#category').focus();
}
//修改奖惩项目
function editRAPItem(){
	var row = $("#RAPItem_tbo").datagrid("getSelected");
	objRowsRAPItem =row;
	if(row){
		$("#RAPItem_dog").dialog("open").dialog("center").dialog("setTitle","修改奖惩项目");
		$("#item_id").val(row.item_id);
		$("#item_name").val(row.item_name);
		$("#category").combobox('setValue',row.category_id);//奖惩类别
		$("#category").combobox('setText',row.category_name);//奖惩类别
		$('#item_id').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//奖惩类别查询
function searchRAPItem(value,name){
	if(value!=""){
		if(value=="奖"){
			value = "0";
		}else if(value=="惩"){
			value = "1";
		}
		loadRAPItem(name,value);
	}
}

//奖惩类别文本框失去焦点事件
function noBlurRAPItem(){
	
}
//保存奖惩类别
function saveRAPItem(){
	var dog_title=$('#RAPItem_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加奖惩项目"){
		data={
				category_id:$("#category").combobox('getValue',''),
				category_name:$("#category").combobox('getText',''),
				item_name:$('#item_name').val(),
		};
		url = prefix+'/saveRAPItem';
		msg = "奖惩项目保存成功!";
	}else if(dog_title=="修改奖惩项目"){
		data={
				item_id:objRowsRAPItem.item_id,
				category_id:$("#category").combobox('getValue',''),
				category_name:$("#category").combobox('getText',''),
				item_name:$('#item_name').val(),
			};
		url = prefix+'/editRAPItem';
		msg = "奖惩项目修改成功!";
	}
	if($("#category").combobox('getValue','')==""){
		$.messager.alert("消息提示！","奖惩类别不能为空！","warning");
		$('#category').focus();
	}else if($("#item_name").val()==""){
		$.messager.alert("消息提示！","奖惩项目不能为空！","warning");
		$('#item_name').focus();
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
				//alert("I am an alert box!!"+data.status);
				if(data.status=="success"){
					$.messager.show({
						title:'消息提醒',
						msg:msg,
						timeout:3000,
						showType:'slide'
					});
					$('#RAPItem_dog').dialog('close');
					loadRAPItem(null,null);
					//loadComboboxRAPItemData();
				}else if(data.status=="update-error"){
					$.messager.alert("消息提示！","该奖惩类别已有奖惩记录，不允许修改奖惩类别，只能修改“奖惩项目名称”！","warning");
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
}
//删除奖惩项目
function removeRAPItem(){
	var row = $("#RAPItem_tbo").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			$.ajax({
				url:prefix+'/removeRAPItem',
				type:'post',
				data:{
					item_id:row.item_id
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
							msg:'奖惩类别删除成功！',
							timeout:3000,
							showType:'slide'
						});
						loadRAPItem(null,null);
						//loadComboboxRAPItemData();
					}else if(data.status=="remove-error"){
						$.messager.alert("消息提示！","该奖惩类别已有奖惩记录，不允许删除！","warning");
					}else{
						$.messager.alert("消息提示！","奖惩类别删除失败，请联系管理员！","warning");
					}
				}
			});
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}



