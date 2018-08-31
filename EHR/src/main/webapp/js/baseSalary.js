var prefix = window.location.host;
prefix = "http://" + prefix + "/EHR";

$(function() {
	loadBaseSalary();
	
})

function loadBaseSalary() {
	$("#base_salary_list").datagrid({
		url:prefix + "/baseSalary/page",
		type : 'post',
		fit : true,
		fitColumns : true,
		striped : true,
		border : false,
		toolbar : "#base_salary_list_tools",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		pageNumber : 1,
		columns : [[
			{
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
						toolbar:getBaseSalaryTools(),
						url:prefix+'/loadPersonInFo',
						columns:[[
							{field:'p_number',title:'员工编号',width:100},
							{field:'p_name',title:'员工名称',width:100},
						]],
						required:true,
						editable:false,
						onSelect:function (index, row){
							var pNumber = $('#base_salary_list').datagrid('getEditor', {index:editBaseSalaryIndex,field:'pNumber'});
							var pName = $('#base_salary_list').datagrid('getEditor', {index:editBaseSalaryIndex,field:'pName'});
							$(pNumber.target).val(row.p_number); 
							$(pName.target).val(row.p_name); 
						}
					},
				},
			},
			{
				field : "baseSalary",
				title : "基本工资",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "fixedOverTimeSalary",
				title : "固定加班工资",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "postSalary",
				title : "岗位工资",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "companySalary",
				title : "司龄工资",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "callSubsidies",
				title : "话费补贴",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "singelMeal",
				title : "单个餐补",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "performanceSalary",
				title : "绩效工资",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},{
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
			},
			{
				field : "stay",
				title : "外住补贴",
				width : 100,
				editor : {
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field : "useTime",
				title : "启用时间",
				width : 100,
				editor : {
					type : 'datebox',
					options : {
						required:true,
						editable:false,
						parser: function (s) {
		                    if (!s) return new Date();
		                    var arr = s.split('-');
		                    return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
		                },
		                formatter: function (d) {
		                    var a = parseInt(d.getMonth())<parseInt('9')?'0'+parseInt(d.getMonth()+ 1):d.getMonth() + 1;
		                    return d.getFullYear() + '-' +a; }
					},
				},
			}
		]]
		
	})
}

function getBaseSalaryTools(){
	var tools = document.createElement("div");
	tools.id = "base_salary_member_bar";
	tools.appendChild(document.createTextNode("工号："));
	var input = document.createElement("input");
	input.type="text";input.classList.add("textbox");input.id="base_salary_p_number";
	input.style="width: 110px;";
	input.value="";
	input.oninput=function(){
		searchBaseSalaryPerson();
	};
	tools.appendChild(input);
	tools.appendChild(document.createTextNode(" "));
	tools.appendChild(document.createTextNode("姓名："));
	var input1 = document.createElement("input");
	input1.type="text";input1.classList.add("textbox");input1.id="base_salary_p_name";
	input1.style="width: 110px;";
	input.value="";
	input1.oninput=function(){
		searchBaseSalaryPerson();
	};
	tools.appendChild(input1);
	return tools;
}

var editBaseSalaryIndex;
////是否可编辑
//function canEditBaseSalary() {
//	if (editBaseSalaryIndex == undefined) {
//		return true
//	}
//	if ($('#base_salary_list').datagrid('validateRow', editBaseSalaryIndex)) {
//		$('#base_salary_list').datagrid('endEdit', editBaseSalaryIndex);
//		editBaseSalaryIndex = undefined;
//		return true;
//	} else {
//		return false;
//	}
//}

//添加数据
function addBaseSalaryInfo() {
	if (editBaseSalaryIndex == undefined) {
		$('#base_salary_list').datagrid('appendRow', {});
		editBaseSalaryIndex = $('#base_salary_list').datagrid('getRows').length - 1;
		$('#base_salary_list').datagrid('selectRow', editBaseSalaryIndex)
				.datagrid('beginEdit', editBaseSalaryIndex);
		var editors = $("#base_salary_list").datagrid('getEditors',
				editBaseSalaryIndex);
		var sumEditor = editors[1];
		// 设置sum字段为只读属性
		$(sumEditor.target).attr({
			'readonly' : true,
			'unselectable' : 'on'
		});
		$(sumEditor.target).css('background', '#DCDCDC');
		$("#base_salary_p_number").val("");
		$("#base_salary_p_name").val("");
	}
}

//编辑
function updateEmployeeBaseSalary(index) {
	if (editBaseSalaryIndex != index) {
		if (editBaseSalaryIndex == undefined) {
			$('#base_salary_list').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editBaseSalaryIndex = index;
			var editors = $("#base_salary_list").datagrid('getEditors',
					editBaseSalaryIndex);
			var sumEditor = editors[1];
			// 设置sum字段为只读属性
			$(sumEditor.target).attr({
				'readonly' : true,
				'unselectable' : 'on'
			});
			$(sumEditor.target).css('background', '#DCDCDC');
		} else {
			$('#base_salary_list').datagrid('selectRow',
					editBaseSalaryIndex);
		}
	}
}

//删除宿舍信息
function removeBaseSalaryInfo(){
	if(editBaseSalaryIndex == undefined){
		var rowData =$('#base_salary_list').datagrid('getSelected');
		var index = $("#base_salary_list").datagrid("getRowIndex",rowData);
		var node = $("#base_salary_list").datagrid("getChecked");
		if(index>=0){
			$.messager.confirm("提示","确定要删除此数据？",function(r){
				if(r){
					$.ajax({
						url:prefix + "/baseSalary/remove",
						type:'post',
						data:{
							id:rowData.id,
							pNumber:rowData.pNumber
						},
//					contentType:"application/x-www-form-urlencoded",
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
								$('#base_salary_list').datagrid('cancelEdit', index)
								.datagrid('deleteRow', index).datagrid('clearSelections',node);
								$('#base_salary_list').datagrid('acceptChanges');
								editDormitoryIndex = undefined;
							}else{
								$.messager.alert("消息提示！",data.msg,"warning");
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

//保存基础设置信息
function saveBaseSalaryInfo() {
	var rowData = $('#base_salary_list').datagrid('getSelected');
	if ($('#base_salary_list').datagrid('validateRow', editBaseSalaryIndex)) {
		$('#base_salary_list').datagrid('endEdit', editBaseSalaryIndex);
		editBaseSalaryIndex = undefined;
		$.ajax({
			url : prefix + '/baseSalary/save',
			type : 'post',
			data : {
				id:rowData.id,
				pNumber : rowData.pNumber,
				baseSalary : rowData.baseSalary,
				fixedOverTimeSalary : rowData.fixedOverTimeSalary,
				postSalary : rowData.postSalary,
				companySalary : rowData.companySalary,
				callSubsidies : rowData.callSubsidies,
				singelMeal : rowData.singelMeal,
				performanceSalary : rowData.performanceSalary,
				stay : rowData.stay,
				workType : rowData.workType,
				useTime : rowData.useTime,
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
					$('#base_salary_list').datagrid('acceptChanges');
					loadBaseSalary();
				} else {
					$.messager.alert("消息提示！", data.msg, "warning");
				}
			}
		});
	}
}

//取消操作
function cancelBaseSalaryInfo() {
	$('#base_salary_list').datagrid('rejectChanges');
	editBaseSalaryIndex = undefined;
}

function reloadBaseSalaryInfo(){
	var pNumber = $("#base_salary_number").val();
	var pName = $("#base_salary_name").val();
	$("#base_salary_list").datagrid("load",{pNumber:pNumber,pName:pName});
}

//下拉列表查询人员信息
function searchBaseSalaryPerson(){
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_number = $("#base_salary_p_number").val();
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_name = $("#base_salary_p_name").val();
	$('.combogrid-f').combogrid('grid').datagrid('reload');
}