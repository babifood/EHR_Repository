//@ sourceURL=RewardPunishment.js
var objRowsRewardPunishment;
var objRAPItem;
//初始化
$(function(){
	//加载奖惩记录列表
	loadRewardPunishment(null,null);
	//加载奖惩项目列表
	loadRAPItem(null,null);
	//加载奖惩项目下拉框
	loadComboboxRAPItemData();
});
/**
 * -----------------------------------------奖惩记录列表function-------------------------------------------
 * @returns
 */
//奖惩记录列表
function loadRewardPunishment(rap_category,rap_item){
	var url;
	if(rap_category!=null&&rap_item!=null){
		url=prefix+"/loadRewardPunishment?rap_category="+rap_category+"&rap_item="+rap_item;
	}
	else if(rap_category!=null&&rap_item==null){
		url=prefix+"/loadRewardPunishment?rap_category="+rap_category+"&rap_item=";		
	}
	else if(rap_category==null&&rap_item!=null){
		url=prefix+"/loadRewardPunishment?rap_category=&rap_item="+rap_item;		
	}
	else{
		url=prefix+"/loadRewardPunishment?rap_category=&rap_item=";
	}
	//奖惩项目列表
	$("#RewardPunishment_tbo").datagrid({
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
		toolbar:"#RewardPunishment_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"rap_id",
				title:"奖惩记录ID",
				width:100,
			},
			{
				field:"rap_category",
				title:"奖惩项目类别",
				width:200,
				formatter:function(value){
					if(value=="0"){
						return "奖";
					}else if(value=="1"){
						return "惩";
					}
				},
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
	});
}
//添加奖惩记录
function addRewardPunishment(){
	$("#RewardPunishment_dog").dialog("open").dialog("center").dialog("setTitle","添加奖惩记录");
	$("#rap_category").val("");
	$("#rap_item").combobox('setValue','')
	$('#rap_date').datebox('setValue','');
	$("#rap_reason").val("");
	$("#rap_money").val("");
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
		$("#RewardPunishment_dog").dialog("open").dialog("center").dialog("setTitle","修改奖惩记录");
		$("#rap_category").combobox('setValue',row.rap_category);//奖惩类别
		$("#rap_item").combobox('setValue',row.rap_item_id);
		$("#rap_item").combobox('setText',row.rap_item_name);
		$('#rap_date').datebox('setValue',row.rap_date);
		$("#rap_reason").val(row.rap_reason);
		$("#rap_money").val(row.rap_money);
		$("#rap_proposer_id").val(row.rap_proposer_id);//提议人ID
		$("#rap_proposer").val(row.rap_proposer);//提议人，显示人名
		$("#rap_p_id").val(row.rap_p_id);//奖惩人ID		
		$("#rap_p").val(row.rap_p);//奖惩人，显示人名
		$("#rap_desc").val(row.rap_desc);
		$('#rap_category').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
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
						loadRewardPunishment(null,null);
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
//奖惩记录目查询
function searchRewardPunishment(){
	/*var queryPa = {};
	queryPa.search_rap_category=$("#search_rap_category").combobox('getValue','');
	queryPa.search_rap_item=$("#search_rap_item").val();
	queryPa.search_rap_date_left=$('#search_rap_date_left').datebox('getValue');
	queryPa.search_rap_date_right=$('#search_rap_date_right').datebox('getValue');
	queryPa.search_rap_money=$("#search_rap_money").val();
	queryPa.search_rap_proposer=$("#search_rap_proposer").val();
	queryPa.search_rap_p=$("#search_rap_p").val();*/
	loadRewardPunishment($("#search_rap_category").combobox('getValue',''),$("#search_rap_item").val());
}
//重置奖惩记录查询条件
function resetRewardPunishment(){
	$("#search_rap_category").combobox('setValue','');//类别
	$("#search_rap_item").val('');//项目
	$('#search_rap_date_left').datebox('setValue','');//时间
	$('#search_rap_date_right').datebox('setValue','');//时间
	$("#search_rap_money").val('');//金额
	$("#search_rap_proposer").val('');//提议人
	$("#search_rap_p").val('');//奖惩人员
}
//奖惩记录文本框失去焦点事件
function noBlurRewardPunishment(){
	if(!$("#rap_category").val()==""){
		$("#rap_category_span").html("");
	}
}
//奖惩记录文本校验
function checkDataRewardPunishment(){
	if($('#rap_category').val()==""){
		$("#rap_category_span").html("奖惩类别不能为空");
		$('#rap_category').focus();
		return false;
	}
	if($('#rap_item').combobox('getValue')==""){
		$("#rap_item_span").html("奖惩项目不能为空");
		$('#rap_item').focus();
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
				rap_money:$('#rap_money').val(),
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
				rap_money:$("#rap_money").val(),
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
					loadRewardPunishment(null,null);
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
}
//加载奖惩项目下拉框
function loadComboboxRAPItemData(){
	$("#rap_item").combobox({
		valueField:'id',
		textField:'text',
		url:prefix+"/loadComboboxRAPItemData",
		editable:false,
		onHidePanel:function(none){
			$("#rap_item_span").html("");
		}
	});
}
//调personSelectionWindow
function saveSelectPerson(){
	saveSelectPerson1();
}
/**
 * -----------------------------------------奖惩项目列表function-------------------------------------------
 * @returns
 */
//奖惩项目列表
function loadRAPItem(category_id,item_name){
	var url;
	if(category_id!=null&&item_name!=null){
		url=prefix+"/loadRAPItemAll?category_id="+category_id+"&item_name="+item_name;
	}
	else if(category_id!=null&&item_name==null){
		url=prefix+"/loadRAPItemAll?category_id="+category_id+"&item_name=";		
	}
	else if(category_id==null&&item_name!=null){
		url=prefix+"/loadRAPItemAll?category_id=&item_name="+item_name;		
	}
	else{
		url=prefix+"/loadRAPItemAll?category_id=&item_name=";
	}
	//奖惩项目列表
	$("#RAPItem_tbo").datagrid({
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
		toolbar:"#RAPItem_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"item_id",
				title:"奖惩项目ID",
				width:100,
			},
			{
				field:"item_name",
				title:"奖惩项目名称",
				width:200,
			},
			{
				field:"category_id",
				title:"奖惩项目类别",
				width:200,
				formatter:function(value){
					if(value=="0"){
						return "奖";
					}else if(value=="1"){
						return "惩";
					}
				},
			},
		]],
	});
}
//添加奖惩项目
function addRAPItem(){
	$("#RAPItem_dog").dialog("open").dialog("center").dialog("setTitle","添加奖惩项目");
	$("#item_id").val("");
	$("#item_name").val("");
	$("#category_id").val("");
	$('#item_id').focus();
}
//修改奖惩项目
function editRAPItem(){
	var row = $("#RAPItem_tbo").datagrid("getSelected");
	objRowsRAPItem =row;
	if(row){
		$("#RAPItem_dog").dialog("open").dialog("center").dialog("setTitle","修改奖惩项目");
		$("#item_id").val(row.item_id);
		$("#item_name").val(row.item_name);
		$("#category_id").combobox('setValue',row.category_id);//奖惩类别
		$('#item_id').focus();
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
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
						loadComboboxRAPItemData();
					}else if(data.status=="error"){
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
//奖惩类别查询
function searchRAPItem(){
	loadRAPItem($("#search_category_id").val(),$("#search_item_name").val())
}
//重置奖惩类别查询条件
function resetRAPItem(){
	$("#search_category_id").combobox('setValue','');
	$("#search_item_name").val("");
}
//奖惩类别文本框失去焦点事件
function noBlurRAPItem(){
	if(!$("#category_id").val()==""){
		$("#category_id_span").html("");
	}
	if(!$("#item_id").val()==""){
		$("#item_id_span").html("");
	}
	if(!$("#item_name").val()==""){
		$("#item_name_span").html("");		
	}
}
//保存奖惩类别
function saveRAPItem(){
	var dog_title=$('#RAPItem_dog').panel('options').title;
	var data;
	var url;
	var msg;
	if(dog_title=="添加奖惩项目"){
		data={
				item_id:$('#item_id').val(),
				category_id:$("#category_id").combobox('getValue',''),
				item_name:$('#item_name').val(),
		};
		url = prefix+'/saveRAPItem';
		msg = "奖惩项目保存成功!";
	}else if(dog_title=="修改奖惩项目"){
		data={
				item_id:objRowsRAPItem.item_id,
				category_id:$("#category_id").combobox('getValue',''),
				item_name:$('#item_name').val(),
			};
		url = prefix+'/editRAPItem';
		msg = "奖惩项目修改成功!";
	}
	if($("#category_id").combobox('getValue','')==""){
		$("#category_id_span").html("奖惩类别不能为空");
		$('#category_id').focus();
	}else if($("#item_name").val()==""){
		$("#item_name_span").html("奖惩项目不能为空");
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
					loadComboboxRAPItemData();
				}else{
					$.messager.alert("消息提示！","请求异常，请检查网络！","warning");
				}
			}
		});
	}
}



