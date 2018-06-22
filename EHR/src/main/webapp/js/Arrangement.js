var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";
var firstadd; 
$(function(){
	loadArrangementTabs();
	firstadd = $("#add_arrangement_table").attr("dept-first");
});

//加载Tabs
function loadArrangementTabs(){
	$("#arrangement_tabs").tabs({
		fit:true,
		onSelect:function(title,index){
			if(title=="基本出勤"){
				//加载基本出勤时间列表
				loadBaseTimes();
			}else if(title=="排班"){
				//排班列表
				loadArrangementDeptTree();
			}
		}
	})
}

/**------------------------------------基本作息时间相关----------------------------------------------------------**/
function loadBaseTimes() {
	$("#arrangement_base_time").datagrid({
		url:prefix + "/arrangement/baseTime/findAll",
		type:'post',
		fit:true,
		striped:true,
		border:false,
		toolbar:"#base_time_tools",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"id",
				title:"基础作息时间Id",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
				},
			},
			{
				field:"arrangementName",
				title:"基础作息时间名称",
				width:300,
				editor:{
					type:'text',
					options:{
						required:true,
					},
				},
			},
			{
				field:"startTime",
				title:"开始时间",
				width:200,
				editor:{
					type:'timespinner',
					options:{
						min: '7:00',    
					    required: true,
					    showSeconds: false
					},
					default:'8:00',
				}
			},
			{
				field:"endTime",
				title:"结束时间",
				width:200,
				editor:{
					type:'timespinner',
					options:{
						min: '07:00',    
					    required: true,
					    showSeconds: false
					},
				},
			},
			{
				field:"remark",
				title:"备注",
				width:300,
				editor:{
					type:'text',
				},
			}
		]],
		onDblClickRow:updateArrangementBaseTime,
	});
}

var editIndex;
function addArrangementBaseTime(){
	if (endEditing()){
		$('#arrangement_base_time').datagrid('appendRow',{});
		editIndex = $('#arrangement_base_time').datagrid('getRows').length-1;
		$('#arrangement_base_time').datagrid('selectRow', editIndex)
		.datagrid('beginEdit', editIndex);
		var editors = $("#arrangement_base_time").datagrid('getEditors',editIndex);  
//		var sumEditor = editors[1];  
//		//设置sum字段为只读属性  
//		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
//		$(sumEditor.target).css('background','#DCDCDC'); 
	}
}

//结束编辑
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#arrangement_base_time').datagrid('validateRow', editIndex)){
		$('#arrangement_base_time').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

//删除
function removeArrangementBaseTime(){
	var rowData =$('#arrangement_base_time').datagrid('getSelected');
	var index = $("#arrangement_base_time").datagrid("getRowIndex",rowData);
	var node = $("#arrangement_base_time").datagrid("getChecked");
	if(index>=0){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			if(r){
				$.ajax({
					url:prefix+'/arrangement/baseTime/remove',
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
							$('#arrangement_base_time').datagrid('cancelEdit', index)
							.datagrid('deleteRow', index).datagrid('clearSelections',node);
							$('#arrangement_base_time').datagrid('acceptChanges');
							editIndex = undefined;
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

//保存
function saveArrangementBaseTime(){
	if (endEditing()){
		$.ajax({
			url:prefix+'/arrangement/baseTime/save',
			type:'post',
			data:JSON.stringify(setData()),
			contentType:"application/json",
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
					$('#arrangement_base_time').datagrid('acceptChanges');
					loadCertificaten(null,null);
				}else{
					$.messager.alert("消息提示！","保存失败!","warning");
				}
			}
		});
	}
}

function setData(){
	var rowData =$('#arrangement_base_time').datagrid('getSelected');
	var data={
		id:rowData.id,
		arrangementName:rowData.arrangementName,
		startTime:rowData.startTime,
		endTime:rowData.endTime,
		remark:rowData.remark
	}
	return data;
}

//取消
function cancelArrangementBaseTime(){
	$('#arrangement_base_time').datagrid('rejectChanges');
	editIndex = undefined;
}

//双击编辑
function updateArrangementBaseTime(index){
	if (editIndex != index){
		if (endEditing()){
			$('#arrangement_base_time').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
			var editors = $("#arrangement_base_time").datagrid('getEditors',editIndex);  
//			var sumEditor = editors[1];  
//			//设置sum字段为只读属性  
//			$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
//			$(sumEditor.target).css('background','#DCDCDC');
		} else {
			$('#certificaten_grid').datagrid('selectRow', editIndex);
		}
	}
}




/**------------------------------------排班相关----------------------------------------------------------**/
function loadArrangementDeptTree() {
	
	$("#arrangement_list").datagrid({
		url:prefix + "/arrangement/findList",
		type:'post',
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#arrangement_tools",
		singleSelect:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{
				field:"id",
				title:"主键",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
				},
			},
			{
				field:"date",
				title:"日期",
				width:100,
				editor:{
					type:'text',
					options:{
						required:true,
					},
				},
			},
			{
				field:"time",
				title:"班次",
				width:150,
				editor:{
					type:'text',
					options:{
					    required: true,
					},
				}
			},
			{
				field:"attendance",
				title:"班次类型",
				width:100,
				editor:{
					type:'text',
					options:{
					    required: true,
					},
				},
			},{
				field:"isAttend",
				title:"是否考勤",
				width:100,
				editor:{
					type:'text',
					options:{
					    required: true,
					},
				},
			},{
				field:"targetType",
				title:"考勤策略",
				width:100,
				editor:{
					type:'text',
					options:{
					    required: true,
					},
				},
			},{
				field:"targetName",
				title:"机构/员工名称",
				width:100,
				editor:{
					type:'text',
					options:{
					    required: true,
					},
				},
			},{
				field:"creatorName",
				title:"创建人",
				width:100,
				editor:{
					type:'text',
					options:{
					    required: true,
					},
				},
			},
			{
				field:"remark",
				title:"备注",
				width:100,
				editor:{
					type:'text',
				},
			}
		]]
	});
}

//排班新增
function addArrangement(){
	$("#arrangement_operate").dialog("open").dialog("center").dialog("setTitle","新增排班");
	$("#arrangement_time").combobox({//班次选择（基础时间）   
	    url:prefix + "/arrangement/baseTime/findAll",    
	    valueField:'id',
	    textField:'time'
	});
	$("#attendance_datebox").datebox({
		required:true
	})
	$("#arrangement_target").unbind("click").click(function(){
		$("#arrangement_target_select").dialog("open").dialog("center").dialog("setTitle","请选择机构/人员");
		$("#arrangement_target_dept").tree({
			url:prefix+'/dept/loadTree',
			checkbox : true,
			cascadeCheck:false,
			lines:true,
			onClick:function(node){
				loadEmployeeList(node.deptCode);
			},
			onDblClick:function(node){
				addDept(node);
			}
		})
	})
	init();
	//添加部门信息
	$("#arrangement_target_dept_add").unbind("click").click(function() {
		addSelectedDept();
	});
	//移除部门信息
	$("#arrangement_target_dept_remove").unbind("click").click(function() {
		removeSelectedDept();
	});
}

//初始化已选择部门、员工列表
function init(){
	$("#arrangement_target").val("");
	$("#add_arrangement_table").find("input[name='baseTimeId']").val("");
	$("#add_arrangement_table").find("input[name='isAttend']").val("");
	$("#arrangement_target").attr("data-deptCode","");
	$("#arrangement_target").attr("data-pId","");
	$("#add_arrangement_table").find("input[name='remark']").val("");
	$("#attendance_date_message").html("");
	$("#attendance_baseTimeId_message").html("");
	$("#attendance_target_id_message").html("");
	$("#attendance_select").combobox('setValue', '大小周');
	
//	if(firstadd){
		firstload();
//		firstadd = false;
//	} else {
//		$("#arrangement_target_emploee").datagrid('loadData', { total: 0, rows: [] });
//		$("#arrangement_target_dept_select").datagrid('loadData', { total: 0, rows: [] });
//		$("#arrangement_target_emploee_select").datagrid('loadData', { total: 0, rows: [] });
//	}
}

function firstload() {
	$("#arrangement_target_emploee").datagrid({
		url:null,
		fitColumns:true,
		columns:[[
			{field:'ck',checkbox:'true'},{field:"p_number",title:"员工编号",width:80},{field:"p_name",title:"员工姓名",width:100},
		]],
		//双击添加机构
		onDblClickRow:function(rowIndex,rowData){ 
			addEmployee(rowData);
        }
	});
	$("#arrangement_target_dept_select").datagrid({
		url:null,
		fitColumns:true,
		columns:[[
			{field:'ck',checkbox:'true'},{field:"deptCode",title:"部门编号",width:80},{field:"deptName",title:"部门名称",width:100},
		]],
		//双击取消选中机构
		onDblClickRow:function(rowIndex,rowData){ 
			$('#arrangement_target_dept_select').datagrid('deleteRow', rowIndex);
        }
	});
	$("#arrangement_target_emploee_select").datagrid({
		url:null,
		fitColumns:true,
		columns:[[
			{field:'ck',checkbox:'true'},{field:"p_number",title:"员工编号",width:80},{field:"p_name",title:"员工姓名",width:100},
		]],
		//双击取消人员选中
		onDblClickRow:function(rowIndex,rowData){ 
			$('#arrangement_target_emploee_select').datagrid('deleteRow', rowIndex);
        }
	});
}

//删除排班
function removeArrangement(){
	var rowData = $("#arrangement_list").datagrid("getSelected");
	var index = $("#arrangement_list").datagrid("getRowIndex",rowData);
	if(index >= 0){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			if(r){
				var node = $("#arrangement_list").datagrid("getChecked");
				$.ajax({
					url:prefix + "/arrangement/remove",
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
							$('#arrangement_list').datagrid('cancelEdit', index)
							.datagrid('deleteRow', index).datagrid('clearSelections',node);
							$('#arrangement_list').datagrid('acceptChanges');
							editIndex = undefined;
						}else{
							$.messager.alert("消息提示！","删除失败!","warning");
						}
					}
				})
			}
		});
	} else {
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}

//获取部门员工
function loadEmployeeList(deptCode){
	$("#arrangement_target_emploee").datagrid({
		url:prefix+'/unSelectPersonByDeptID?dept_id='+deptCode,
		checkbox:true,
		checkOnSelect:false,
		fitColumns:true,
	    columns:[[    
	    	{field:'ck',checkbox:'true'},
	        {field:'p_number',title:'工号',width:80},
	        {field:'p_name',title:'姓名',width:100}
	    ]]
	})
	//添加选中员工
	$("#arrangement_target_emploee_add").unbind("click").click(function() {
		addSelectedEmps();
	})
	//删除已选中的员工
	$("#arrangement_target_emploee_remove").unbind("click").click(function() {
		removeSelectedEmps();
	})
}

//移除按钮移除已选择员工
function removeSelectedEmps(){
	var rows = $("#arrangement_target_emploee_select").datagrid("getSelections");
	if(rows && rows.length > 0){
		$.each(rows,function(index,row){
			if(row){
				var rowIndex =  $('#arrangement_target_emploee_select').datagrid('getRowIndex', row);
				$('#arrangement_target_emploee_select').datagrid('deleteRow', rowIndex);  
			}
		})
	}
}

//选择按钮添加选中员工
function addSelectedEmps() {
	var emps = $("#arrangement_target_emploee").datagrid("getChecked");
	if(emps && emps.length > 0){
		var nodes = $("#arrangement_target_emploee_select").datagrid('getRows');
		$.each(emps,function(index,emp){
			if(isUnSelectEmp(emp,nodes)){
				$("#arrangement_target_emploee_select").datagrid("appendRow",{
					p_number:emp.p_number,
					p_name:emp.p_name
				})
			}
		})
	}
	
}

//检查是否未选中
function isUnSelectEmp(emp,nodes){
	var isUnSelect = true;
	if(nodes && nodes.length > 0){
		$.each(nodes,function(index,node){
			if(emp.p_number == node.p_number){
				isUnSelect = false;
				return isUnSelect;
			}
		})
	}
	return isUnSelect;
}

//双击选择员工
function addEmployee(field){
	var nodes = $("#arrangement_target_emploee_select").datagrid('getRows');
	var data = [];
	if(isUnSelectEmp(field,nodes)){
		$("#arrangement_target_emploee_select").datagrid("appendRow",{
			p_number:field.p_number,
			p_name:field.p_name
		})
	}
}

//双击添加部门
function addDept(node){
	var rows = $("#arrangement_target_dept_select").datagrid('getRows');
	var data = [];
	if(isUnSelectDept(node,rows)){
		$("#arrangement_target_dept_select").datagrid("appendRow",{
			deptCode:node.deptCode,
			deptName:node.deptName
		})
	}
}

//选择排班部门
function addSelectedDept(){
	var rows = $("#arrangement_target_dept").tree('getChecked');
	var selectedNodes = $("#arrangement_target_dept_select").datagrid('getRows');
	if(rows && rows.length > 0){
		$.each(rows,function(index,row){
			if(isUnSelectDept(row,selectedNodes)){
				$("#arrangement_target_dept_select").datagrid("appendRow",{
					deptCode:row.deptCode,
					deptName:row.deptName
				})
			}
		})
	}
}

//删除选中的部门信息
function removeSelectedDept(){
	var rows = $("#arrangement_target_dept_select").datagrid("getSelections");
	if(rows && rows.length > 0){
		$.each(rows,function(index,row){
			if(row){
				var rowIndex =  $('#arrangement_target_dept_select').datagrid('getRowIndex', row);
				$('#arrangement_target_dept_select').datagrid('deleteRow', rowIndex);  
			}
		})
	}
};

//检查是否为选中的部门
function isUnSelectDept(dept,rows){
	var isUnSelect = true;
	if(rows && rows.length > 0){
		$.each(rows,function(index,row){
			if(row.deptCode == dept.deptCode){
				isUnSelect = false;
				return isUnSelect;
			}
		})
	}
	return isUnSelect;
}

//保存已选择的机构/人员
function selectArrangementTarget(){
	var depts = $("#arrangement_target_dept_select").datagrid("getRows");
	var emps = $("#arrangement_target_emploee_select").datagrid("getRows");
	var target = "";
	var deptCodes = "";
	var pIds = "";
	if(depts && depts.length > 0){
		$.each(depts,function(index,dept){
			target += dept.deptName + ",";
			deptCodes += dept.deptCode + ",";
		})
	}
	if(emps && emps.length > 0){
		$.each(emps,function(index,emp){
			target += emp.p_name + ",";
			pIds += emp.p_number + ",";
		})
	}
	if(target && target.length > 0){
		target = target.substring(0,target.length-1);
	}
	if(target && target.length > 10){
		target = target.substring(0,10) + "..."
	}
	if(deptCodes && deptCodes.length > 0){
		deptCodes = deptCodes.substring(0,deptCodes.length-1);
	}
	if(pIds && pIds.length > 0){
		pIds = pIds.substring(0,pIds.length-1);
	}
	$("#arrangement_target").val(target);
	$("#arrangement_target").attr("data-deptCode",deptCodes);
	$("#arrangement_target").attr("data-pId",pIds);
	$("#arrangement_target_select").dialog("close");
}

//检查飞空、获取参数
function getDate(){
	var date = $("#add_arrangement_table").find("input[name='date']").val();
	var baseTimeId = $("#add_arrangement_table").find("input[name='baseTimeId']").val();
	var attendance = $("#add_arrangement_table").find("input[name='attendance']").val();
	var isAttend = $("#add_arrangement_table").find("input[name='isAttend']").val();
	var deptCodes = $("#arrangement_target").attr("data-deptCode");
	var pIds = $("#arrangement_target").attr("data-pId");
	var remark = $("#add_arrangement_table").find("input[name='remark']").val();
	var isNull = false;
	if(!date || date.length <= 0){
		$("#attendance_date_message").html("日期不能为空");
		isNull = true;
	}
	if(!baseTimeId || baseTimeId.length <= 0){
		$("#attendance_baseTimeId_message").html("班次不能为空");
		isNull = true;
	}
	if((!deptCodes || deptCodes.length <= 0)&&(!pIds || pIds.length <= 0)){
		$("#attendance_target_id_message").html("考勤机构/员工不能为空");
		isNull = true;
	}
	var arrangementDate = {isNull:isNull,data:{
		date:date,
		baseTimeId:baseTimeId,
		attendance:attendance,
		isAttend:isAttend,
		deptCodes:deptCodes,
		remark:remark,
		pIds:pIds
	}}
	return arrangementDate;
}

//保存排班信息
function saveArrangements(){
	if(!getDate().isNull){
		$.ajax({
			url:"arrangement/save",
			type:"post",
			data:getDate().data,
			contentType:"application/x-www-form-urlencoded",
			success:function(result){
				if(result.code == 1){
					$("#arrangement_operate").dialog("close");
					$("#arrangement_list").datagrid("reload");
				} else {
					$.messager.alert('提示消息','新增排班失败','error');
				}
			}
		})
	}
}
