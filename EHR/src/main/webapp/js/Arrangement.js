var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";
var firstadd; 
$(function(){
	loadArrangementTabs();
});

//加载Tabs
function loadArrangementTabs(){
	$("#arrangement_tabs").tabs({
		fit:true,
		onSelect:function(title,index){
			if(title=="排班列表"){
				//加载基本出勤时间列表
				loadBaseTimes();
			}else if(title=="排班设置"){
				//排班列表
				loadArrangementDeptTree();
				targetArrangementInit();
			}
		}
	})
}

/**------------------------------------基本作息时间相关----------------------------------------------------------**/
function loadBaseTimes() {
	$("#arrangement_base_time").datagrid({
		url:prefix + "/arrangement/base/findAll",
		type:'post',
		fitColumns:true,
		striped:true,
		border:false,
		toolbar:"#base_time_tools",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"id",
				title:"排班ID",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
				},
			},
			{
				field:"arrangementName",
				title:"排班名称",
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
				title:"标准上班时间",
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
				title:"标准下班时间",
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
				field:"arrangementType",
				title:"排班类型",
				width:100,
				editor:{
					type:'combobox',
					options:{
						valueField: 'id',
						textField: 'text',
						data:[{"id":1,"text":"大小周","selected":true},{"id":2,"text":"1.5休"},{"id":3,"text":"双休"},{"id":4,"text":"单休"}]
					},
				},
				formatter: function(value,row,index){
	                if (value == 1){
	                    return '大小周';
	                } if (value == 2){
	                    return '1.5休';
	                } if (value == 3){
	                    return '双休';
	                } if (value == 4){
	                    return '单休';
	                }
	            }
			},
			{
				field:"remark",
				title:"备注",
				width:200,
				editor:{
					type:'text',
				},
			}
		]],
//		onDblClickRow:updateArrangementBaseTime,
	});
}

var editIndex1;
function addArrangementBaseTime(){
	if (endEditingArrangement()){
		$('#arrangement_base_time').datagrid('appendRow',{});
		editIndex1 = $('#arrangement_base_time').datagrid('getRows').length-1;
		$('#arrangement_base_time').datagrid('selectRow', editIndex1)
		.datagrid('beginEdit', editIndex1); 
	}
}

//结束编辑
function endEditingArrangement(){
	if (editIndex1 == undefined){return true}
	if ($('#arrangement_base_time').datagrid('validateRow', editIndex1)){
		$('#arrangement_base_time').datagrid('endEdit', editIndex1);
		editIndex1 = undefined;
		return true;
	} else {
		return false;
	}
}

//删除
function removeArrangementBaseTime(){
	if(editIndex1 == undefined){
		var rowData =$('#arrangement_base_time').datagrid('getSelected');
		var index = $("#arrangement_base_time").datagrid("getRowIndex",rowData);
		var node = $("#arrangement_base_time").datagrid("getChecked");
		if(index>=0){
			$.messager.confirm("提示","确定要删除此数据？",function(r){
				if(r){
					$.ajax({
						url:prefix+'/arrangement/base/remove',
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
								editIndex1 = undefined;
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

//保存
function saveArrangementBaseTime(){
	if (endEditingArrangement()){
		$.ajax({
			url:prefix+'/arrangement/base/save',
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
					$('#arrangement_base_time').datagrid('reload');
				}else{
					$.messager.alert("消息提示！","保存失败!","warning");
				}
			}
		});
	}
}

//设置参数
function setData(){
	var rowData =$('#arrangement_base_time').datagrid('getSelected');
	var data={
		id:rowData.id,
		arrangementName:rowData.arrangementName,
		arrangementType:rowData.arrangementType,
		startTime:rowData.startTime,
		endTime:rowData.endTime,
		remark:rowData.remark
	}
	return data;
}

//取消
function cancelArrangementBaseTime(){
	$('#arrangement_base_time').datagrid('rejectChanges');
	editIndex1 = undefined;
}

//编辑
function updateArrangementBaseTime(){
	var row = $('#arrangement_base_time').datagrid("getSelected");
	if(!row){
		$.messager.alert("消息提示！","请选择一条数据","warning");
	} else {
		var index = $('#arrangement_base_time').datagrid("getRowIndex", row);	
		if (editIndex1 != index){
			if (endEditingArrangement()){
				$('#arrangement_base_time').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex1 = index;
				var editors = $("#arrangement_base_time").datagrid('getEditors',editIndex1);  
			} else {
				$('#arrangement_base_time').datagrid('selectRow', editIndex1);
			}
		}
	}
}

//设置特殊排班
function foundArrangement(){
	var rowData = $("#arrangement_base_time").datagrid('getSelected');
	if(rowData && rowData.id){
		$("#arrangement_calender").dialog("open").dialog("setTitle","排班设置");
		$("#arrangement_name").text(rowData.arrangementName);
		$("#arrangement_time").text(rowData.startTime + " ~ " + rowData.endTime);
		arrangeInit(rowData.id);
	} else {
		$.messager.alert("消息提示！","请选择排班!","warning");
	}
}


/**------------------------------------排班相关----------------------------------------------------------**/
//加载部门数据
function loadArrangementDeptTree() {
	document.getElementById("arrangement_dept_tree").oncontextmenu = function(e){
	　　return false;
	}
	$('#arrangement_dept_tree').tree({    
	    url:prefix+'/dept/loadTree',
	    lines:true,
		onClick:function(node){
			getEmploys(node.deptCode);
			finadSpecialArrengementByDeptCode(node.deptCode);
		},
		onContextMenu:function(e,node){
			settingArrangement(node.deptCode,node.type,e.clientX-e.offsetX,e.clientY-e.offsetY);
		}
	})
}

//获取对应部门的员工
function getEmploys(deptCode) {
	$("#emploee_arrangement").datagrid({
		url:prefix+'/unSelectPersonByDeptID?dept_id='+deptCode,
		fitColumns:true,
		singleSelect:true,
	    columns:[[    
	        {field:'p_number',title:'工号',width:80},
	        {field:'p_name',title:'姓名',width:100}
	    ]],
	    onClickRow:function(rowIndex, rowData){
	    	finadSpecialArrengementByPnumber(rowIndex, rowData);
	    },
	    onRowContextMenu:function(e,rowIndex, rowData){
	    	settingArrangement(rowData.p_number,"10",e.clientX-e.offsetX,e.clientY-e.offsetY);
		}
	});
	document.oncontextmenu = function(e){
		　　return false;
		}
}

//获取部门对应排班数据
function finadSpecialArrengementByDeptCode(deptCode) {
	setArrangementData(null,deptCode);
}

//获取员工对应排班数据
function finadSpecialArrengementByPnumber(rowIndex, rowData) {
	setArrangementData(rowData.p_number,null);
}

//绑定特殊排班弹框
function settingArrangement(targetId,type,clientX,clientY){
	var isClose = true;
	var arrangementId;
	var data;
	if(type == 10){
		data = {"pNumber":targetId,"deptCode":null}
	} else {
		data = {"pNumber":null,"deptCode":targetId}
	}
	$.ajax({
		url:prefix + "/arrangement/base/specialArrangementId",
		data:data,
		type:'post',
		success:function(result){
			if(result){
				arrangementId = result.arrangementId;
			}
			$("#arrangement_select_list").combobox('setValue', arrangementId);
		}
	});
	$('#arrangement_select').window('open').window('resize',{width:'330px',height:'68px',top:clientY+18 ,left:clientX});
	$("#arrangement_target_id").val(targetId);
	$("#arrangement_target_type").val(type);
}

//保存特殊绑定的特殊排班信息
function settingArangement(){
	$.messager.confirm('确认对话框', '更改信息下月生效，确定更改吗?', function(r){
		if (r){
			var targetId = $("#arrangement_target_id").val();
			var type = $("#arrangement_target_type").val();
			var arrangementId = $("#arrangement_select_list").val();
			$.ajax({
				url:prefix + "/arrangement/base/bindArrangement",
				type:'post',
				contentType:"application/x-www-form-urlencoded",
				data:{
					targetId:targetId,
					type:type,
					arrangementId:arrangementId
				},
				success:function(result){
					if(result.code == "1"){
						if(type == '10'){
							setArrangementData(targetId,null);
						} else {
							setArrangementData(null,targetId);
						}
						$('#arrangement_select').window('close');
					} else {
						$.messager.alert("消息提示！",result.msg,"warning");
					}
				}
			});
		}
	});
}
