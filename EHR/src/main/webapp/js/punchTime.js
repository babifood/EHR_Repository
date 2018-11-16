var prefix = window.location.host;
prefix = "http://" + prefix + "/EHR";

$(function() {
	loadPunchTimeInfo();
})


function loadPunchTimeInfo() {
	$("#punch_time_list").datagrid({
		url : prefix + "/punchTime/page",
		fitColumns:true,
		fit : true,
		striped : true,
		border : false,
		toolbar : "#punch_time_list_tools",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		pageNumber : 1,
		columns : [ [ {
			field : "id",
			title : "员工工号",
			width : 100,
			hidden : "true",
		}, {
			field : "pNumber",
			title : "员工工号",
			width : 100,
			editor:{
				type:'text',
				options:{
					required:true,
				},
			}
		}, {
			field : "pName",
			title : "员工姓名",
			width : 100,
			editor : {
				type:'combogrid',
				options:{
					panelWidth:400,
					idField:'p_number',
					textField:'p_name',
					toolbar:getPunchTimeTools(),
					url:prefix+'/loadPersonlimit',
					columns:[[
						{field:'p_number',title:'员工编号',width:100},
						{field:'p_name',title:'员工名称',width:100},
					]],
					required:true,
					editable:false,
					onSelect:function (index, row){
						var pNumber = $('#punch_time_list').datagrid('getEditor', {index:punchTimeIndex,field:'pNumber'});
						var pName = $('#punch_time_list').datagrid('getEditor', {index:punchTimeIndex,field:'pName'});
						console.log(row.p_number);
						$(pNumber.target).val(row.p_number); 
						$(pName.target).val(row.p_name); 
					}
				},
			},
		}, {
			field : "date",
			title : "打卡日期",
			width : 100,
			editor : {
				type : 'datebox',
				options : {
					required:true,
					editable:false,
				},
			},
		}, {
			field : "beginTime",
			title : "开始时间",
			width : 100,
			editor:{
				type:'timespinner',
				options:{
//				    required: true,
				    showSeconds: false
				},
			}
		}, {
			field : "endTime",
			title : "结束时间",
			width : 100,
			editor:{
				type:'timespinner',
				options:{
//				    required: true,
				    showSeconds: false
				},
			}
		}, ] ],
		onDblClickRow : updatePunchTimes,
	});
}

function getPunchTimeTools(){
	var tools = document.createElement("div");
//	tools.id = "dormitory_cost_member_bar";
	tools.appendChild(document.createTextNode("工号："));
	var input = document.createElement("input");
	input.type="text";input.classList.add("textbox");input.id="punch_time_p_number";
	input.style="width: 110px;";
	input.value="";
	input.oninput=function(){
		searchPunchTimePerson();
	};
	tools.appendChild(input);
	tools.appendChild(document.createTextNode(" "));
	tools.appendChild(document.createTextNode("姓名："));
	var input1 = document.createElement("input");
	input1.type="text";input1.classList.add("textbox");input1.id="punch_time_p_name";
	input1.style="width: 110px;";
	input.value="";
	input1.oninput=function(){
		searchPunchTimePerson();
	};
	tools.appendChild(input1);
	return tools;
}

//条件搜索人员信息
function searchPunchTimePerson(){
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_number = $("#punch_time_p_number").val();
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_name = $("#punch_time_p_name").val();
	$('.combogrid-f').combogrid('grid').datagrid('reload');
}

var punchTimeIndex;

function addPunchTimes() {
	if (punchTimeIndex == undefined){
		console.log("111111111111");
		$('#punch_time_list').datagrid('appendRow',{beginTime:'09:00', endTime:'18:00'});
		punchTimeIndex = $('#punch_time_list').datagrid('getRows').length-1;
		$('#punch_time_list').datagrid('selectRow', punchTimeIndex)
		.datagrid('beginEdit', punchTimeIndex);
		var editors = $("#punch_time_list").datagrid('getEditors',punchTimeIndex); 
		var sumEditor = editors[0];
		// 设置sum字段为只读属性
		$(sumEditor.target).attr({'readonly' : true,'unselectable' : 'on'});
		$(sumEditor.target).css('background', '#DCDCDC');
	}
}

function updatePunchTimes(){
	if (punchTimeIndex == undefined){
		var row = $('#punch_time_list').datagrid("getSelected");
		if(!row){
			$.messager.alert("消息提示！","请选择一条数据","warning");
		} else {
			var index = $('#punch_time_list').datagrid("getRowIndex", row);	
			console.log(punchTimeIndex);
			if (punchTimeIndex != index){
				$('#punch_time_list').datagrid('beginEdit', index);
				punchTimeIndex = index;
				var editors = $("#punch_time_list").datagrid('getEditors',punchTimeIndex); 
				var sumEditor = editors[0];
				// 设置sum字段为只读属性
				$(sumEditor.target).attr({'readonly' : true,'unselectable' : 'on'});
				$(sumEditor.target).css('background', '#DCDCDC'); 
			}
		}
	}
}

function removePunchTime() {
	if(punchTimeIndex == undefined){
		var rowData =$('#punch_time_list').datagrid('getSelected');
		var index = $("#punch_time_list").datagrid("getRowIndex",rowData);
		var node = $("#punch_time_list").datagrid("getChecked");
		if(index>=0){
			$.messager.confirm("提示","确定要删除此数据？",function(r){
				if(r){
					$.ajax({
						url:prefix+'/punchTime/remove',
						type:'post',
						data:{
							id:rowData.id
						},
						contentType:"application/x-www-form-urlencoded",
						beforeSend:function(){
							$.messager.progress({
								text:'删除中......',
							});
						},
						success:function(data){
							$.messager.progress('close');
							if(data.code=="1"){
								$.messager.show({
									title:'消息提醒',
									msg:'删除成功!',
									timeout:3000,
									showType:'slide'
								});
								$('#punch_time_list').datagrid('cancelEdit', index)
								.datagrid('deleteRow', index).datagrid('clearSelections',node);
								$('#punch_time_list').datagrid('reload');
							}else{
								$.messager.alert("消息提示！","删除失败!","warning");
							}
						}
					});
				}
			});
		}else{
			$.messager.alert("消息提示！","请选择一条数据！","info");
		}	
	}
}

function savePunchTime() {
	if (punchTimeIndex >= 0 && $('#punch_time_list').datagrid('validateRow', punchTimeIndex)){
		$('#punch_time_list').datagrid('endEdit', punchTimeIndex);
		var rows = $('#punch_time_list').datagrid("getRows");
		var rowData = rows[punchTimeIndex];
		$.ajax({
			url:prefix+'/punchTime/save',
			type:'post',
			data:{
				id : rowData.id,
				workNum : rowData.pNumber,
				userName : rowData.pName,
				clockedDate : rowData.date,
				beginTime : rowData.beginTime,
				endTime : rowData.endTime,
			},
//			contentType:"application/json",
			beforeSend:function(){
				$.messager.progress({
					text:'保存中......',
				});
			},
			success:function(data){
				$.messager.progress('close');
				if(data.code=="1"){
					$.messager.show({
						title:'消息提醒',
						msg:'保存成功!',
						timeout:3000,
						showType:'slide'
					});
					punchTimeIndex = undefined;
					$('#punch_time_list').datagrid('reload');
				}else{
					$.messager.alert("消息提示！","保存失败!","warning");
					$('#punch_time_list').datagrid('beginEdit', punchTimeIndex);
				}
			}
		});
	}
}

function cancelPunchTime() {
	$('#punch_time_list').datagrid('rejectChanges');
	punchTimeIndex = undefined;
}

function reloadPunchTime() {
	console.log("1111111111111");
	var pNumber = $("#punch_time_number").val();
	var pName = $("#punch_time_name").val();
	var data = {
		pNumber:pNumber,
		pName:pName,
	}
	$("#punch_time_list").datagrid("load",data);
}

function syncPunchTimeInfo() {
	$.ajax({
		url : prefix + "/punchTime/sync",
		success:function(result){
			if(result.code == '1'){
				$.messager.show({
					title:'消息提醒',
					msg:'同步数据成功!',
					timeout:3000,
					showType:'slide'
				});
			} else {
				$.messager.alert("消息提示！","同步打卡记录失败!","warning");
			}
		}
	})
}