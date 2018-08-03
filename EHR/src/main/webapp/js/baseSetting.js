var prefix = window.location.host;
prefix = "http://" + prefix + "/EHR";

$(function() {
	loadBaseSettingInfo();
})

//下拉列表查询人员信息
function searchBaseSettingPerson(){
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_number = $("#base_setting_p_number").val();
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_name = $("#base_setting_p_name").val();
	$('.combogrid-f').combogrid('grid').datagrid('reload');
}

function getBaseSettingTools(){
	var tools = document.createElement("div");
	tools.id = "base_setting_member_bar";
	tools.appendChild(document.createTextNode("工号："));
	var input = document.createElement("input");
	input.type="text";input.classList.add("textbox");input.id="base_setting_p_number";
	input.style="width: 110px;";
	input.value="";
	input.oninput=function(){
		searchBaseSettingPerson();
	};
	tools.appendChild(input);
	tools.appendChild(document.createTextNode(" "));
	tools.appendChild(document.createTextNode("姓名："));
	var input1 = document.createElement("input");
	input1.type="text";input1.classList.add("textbox");input1.id="base_setting_p_name";
	input1.style="width: 110px;";
	input.value="";
	input1.oninput=function(){
		searchBaseSettingPerson();
	};
	tools.appendChild(input1);
	return tools;
}

// 加载基础设置信息
function loadBaseSettingInfo() {
	$("#base_setting_list").datagrid({
		url : prefix + "/basesetting/all",
		type : 'post',
		fit : true,
		fitColumns : true,
		striped : true,
		border : false,
		toolbar : "#base_setting_list_tools",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		pageNumber : 1,
		columns : [ [ {
			field : "id",
			title : "",
			width : 100,
			hidden : "true",
			editor : {
				type : 'text',
				options : {

				},
			},
		}, {
			field : "pNumber",
			title : "员工工号",
			width : 100,
			editor : {
				type : 'text',
				options : {

				},
			},
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
					toolbar:getBaseSettingTools(),
					url:prefix+'/loadPersonInFo',
					columns:[[
						{field:'p_number',title:'员工编号',width:100},
						{field:'p_name',title:'员工名称',width:100},
					]],
					required:true,
					editable:false,
					onSelect:function (index, row){
						var pNumber = $('#base_setting_list').datagrid('getEditor', {index:editBaseSettingIndex,field:'pNumber'});
						var pName = $('#base_setting_list').datagrid('getEditor', {index:editBaseSettingIndex,field:'pName'});
						$(pNumber.target).val(row.p_number); 
						$(pName.target).val(row.p_name); 
					}
				},
			},
		}, {
			field : "workType",
			title : "工作类型",
			width : 100,
			editor : {
				type : 'combobox',
				options : {
					required:true,
					editable:false,
					valueField: 'value',
					textField: 'text',
					data: [{value: '0',text: '计时'},
						{value: '1',text: '计件'}]
				},
			},
			formatter:function(value,row,index){
				if(row.workType == "0"){
					return "计时";
				} else if(row.workType == "1"){
					return "计件";
				}
			}
		}, {
			field : "workPlace",
			title : "工作地区",
			width : 100,
			editor : {
				type : 'combobox',
				options : {
					required:true,
					editable:false,
					valueField: 'value',
					textField: 'text',
					data: [{value: '1',text: '上海'},
					{value: '2',text: '江苏'},
					{value: '3',text: '浙江'},
					{value: '4',text: '广东'},
					{value: '5',text: '深圳'},
					{value: '6',text: '北京'}]
				},
			},
			formatter:function(value,row,index){
				console.log(value);
				console.log(row);
				console.log(index);
				if(row.workPlace == "1"){
					return "上海";
				} else if(row.workPlace == "2"){
					return "江苏";
				} else if(row.workPlace == "3"){
					return "浙江";
				} else if(row.workPlace == "4"){
					return "广东";
				} else if(row.workPlace == "5"){
					return "深圳";
				} else if(row.workPlace == "6"){
					return "北京";
				}
			}
		}, {
			field : "ismeal",
			title : "有无餐补",
			width : 100,
			editor : {
				type : 'combobox',
				options : {
					required:true,
					editable:false,
					valueField: 'value',
					textField: 'text',
					data: [{value: '0',text: '无'},
					{value: '1',text: '有'}]
				},
			},
			formatter:function(value,row,index){
				if(row.ismeal == "0"){
					return "无";
				} else if(row.ismeal == "1"){
					return "有";
				}
			}
		} ] ],
		onDblClickRow:updateEmployeeBaseSetting,
	})
}

//获取参数
function getBaseInfoParams(type,value) {
	var pName = $("#base_setting_name").val();
	var workType = $("#base_setting_type").val();
	var workPlace = $("#base_setting_place").val();
	var ismeal = $("#base_setting_ismeal").val();
	var pNumber = $("#base_setting_number").val();
	if(type == "1"){
		workType = value;
	} else if (type == "2") {
		ismeal = value;
	} else if (type == "3") {
		workPlace = value;
	}
	var data = {
		pName : pName,
		pNumber : pNumber,
		workType : workType,
		workPlace : workPlace,
		ismeal : ismeal
	}
	return data;
}

function reloadBaseSettingInfo(type,value){
	if(!canEditBaseSetting()){
		return ;
	}
	$("#base_setting_list").datagrid("load",getBaseInfoParams(type,value));
}

var editBaseSettingIndex;
// 是否可编辑
function canEditBaseSetting() {
	if (editBaseSettingIndex == undefined) {
		return true
	}
	if ($('#base_setting_list').datagrid('validateRow', editBaseSettingIndex)) {
		$('#base_setting_list').datagrid('endEdit', editBaseSettingIndex);
		editBaseSettingIndex = undefined;
		return true;
	} else {
		return false;
	}
}

// 添加数据
function addBaseSeetingInfo() {
	if (canEditBaseSetting()) {
		$('#base_setting_list').datagrid('appendRow', {});
		editBaseSettingIndex = $('#base_setting_list').datagrid('getRows').length - 1;
		$('#base_setting_list').datagrid('selectRow', editBaseSettingIndex)
				.datagrid('beginEdit', editBaseSettingIndex);
		var editors = $("#base_setting_list").datagrid('getEditors',
				editBaseSettingIndex);
		var sumEditor = editors[1];
		// 设置sum字段为只读属性
		$(sumEditor.target).attr({
			'readonly' : true,
			'unselectable' : 'on'
		});
		$(sumEditor.target).css('background', '#DCDCDC');
		$("#base_setting_p_number").val("");
		$("#base_setting_p_name").val("");
	}
}

// 双击编辑
function updateEmployeeBaseSetting(index) {
	if (editBaseSettingIndex != index) {
		if (canEditBaseSetting()) {
			$('#base_setting_list').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editBaseSettingIndex = index;
			var editors = $("#base_setting_list").datagrid('getEditors',
					editBaseSettingIndex);
			var sumEditor = editors[1];
			// 设置sum字段为只读属性
			$(sumEditor.target).attr({
				'readonly' : true,
				'unselectable' : 'on'
			});
			$(sumEditor.target).css('background', '#DCDCDC');
		} else {
			$('#base_setting_list').datagrid('selectRow',
					editBaseSettingIndex);
		}
	}
}

//保存基础设置信息
function saveBaseSeetingInfo() {
	var rowData = $('#base_setting_list').datagrid('getSelected');
	if (canEditBaseSetting()) {
		$.ajax({
			url : prefix + '/basesetting/save',
			type : 'post',
			data : {
				id:rowData.id,
				pNumber : rowData.pNumber,
				workType : rowData.workType,
				workPlace : rowData.workPlace,
				ismeal : rowData.ismeal,
			},
			contentType : "application/x-www-form-urlencoded",
			beforeSend : function() {
				$.messager.progress({
					text : '保存中......',
				});
			},
			success : function(data) {
				$.messager.progress('close');
				if (data.code == "1") {
					$.messager.show({
						title : '消息提醒',
						msg : '保存成功!',
						timeout : 3000,
						showType : 'slide'
					});
					$('#base_setting_list').datagrid('acceptChanges');
					loadBaseSettingInfo();
				} else {
					$.messager.alert("消息提示！", data.msg, "warning");
				}
			}
		});
	}
}

// 取消操作
function cancelBaseSeetingInfo() {
	$('#base_setting_list').datagrid('rejectChanges');
	editBaseSettingIndex = undefined;
}

function clearSearchBaseSetting(){
	$("#base_setting_number").val("");
	$("#base_setting_name").val("");
	$("#base_setting_type").combobox("clear");
	$("#base_setting_place").combobox("clear");
	$("#base_setting_ismeal").combobox("clear");
	$("#base_setting_list").datagrid("load",{});
}