var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";


$(function() {
	loadDate();
});

function loadDate(){
	$("#dormitory_tabs").tabs({
		fit:true,
		onSelect:function (title,index){
			if(title == "床位管理"){
				loadDormitoryInfo();
			} else if(title == "入住员工"){
				loadStayDormitoryInfo();
			}
		}
	});
}


/**--------------------------------------宿舍信息操作--------------------------------------------------------*/
//加载所有床位信息
function loadDormitoryInfo(){
	$("#dormitory_list").datagrid({
		url:prefix + "/dormitory/all",
		type:'post',
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		toolbar:"#dormitory_list_tools",
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		columns:[[
			{
				field:"id",
				title:"床位",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
					options:{
						
					},
				},
			},
			{
				field:"floor",
				title:"楼层",
				width:100,
				editor:{
					type:'combobox',
					options:{
						valueField: 'label',
						required:true,
						editable:false,
						textField: 'value',
						data:[{label:"一楼",value:"一楼"},{label:"二楼",value:"二楼"},{label:"三楼",value:"三楼"},
							{label:"四楼",value:"四楼"},{label:"五楼",value:"五楼"},{label:"六楼",value:"六楼"}]
					},
				},
				formatter:function(value,row,index){
					if(row.floor == "1"){
						return "一楼";
					} else if (row.floor == "2") {
						return "二楼";
					} else if (row.floor == "3") {
						return "三楼";
					} else if (row.floor == "4") {
						return "四楼";
					} else if (row.floor == "5") {
						return "五楼";
					} else if (row.floor == "6") {
						return "六楼";
					}
				}
			},
			{
				field:"roomNo",
				title:"房间号",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"bedNo",
				title:"床位号",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true,
					},
				},
			},
			{
				field:"sex",
				title:"宿舍类型",
				width:100,
				editor:{
					type:'combobox',
					options:{
						valueField: 'label',
						required:true,
						editable:false,
						textField: 'value',
						data:[{label:"0",value:"男宿舍"},{label:"1",value:"女宿舍"}]
					},
				},formatter:function(value,row,index){
					if(row.sex == "1"){
						return "女宿舍";
					} else if (row.sex == "0") {
						return "男宿舍";
					}
				}
			},
			{
				field:"stay",
				title:"是否入住",
				width:100,
				editor:{
					type:'text',
					options:{
						
					},
				},
			},
			{
				field:"remark",
				title:"备注",
				width:100,
				editor:{
					type:'text',
					options:{
						
					},
				},
			}
		]]
	})
}

var editDormitoryIndex;

function canEditDormitory() {
	if (editDormitoryIndex == undefined){return true}
	if ($('#dormitory_list').datagrid('validateRow', editDormitoryIndex)){
		$('#dormitory_list').datagrid('endEdit', editDormitoryIndex);
		editDormitoryIndex = undefined;
		return true;
	} else {
		return false;
	}
}

//新增宿舍信息
function addDormitoryInfo(){
	if (canEditDormitory()){
		$('#dormitory_list').datagrid('appendRow',{});
		editDormitoryIndex= $('#dormitory_list').datagrid('getRows').length-1;
		$('#dormitory_list').datagrid('selectRow', editDormitoryIndex).datagrid('beginEdit', editDormitoryIndex);
		var editors = $("#dormitory_list").datagrid('getEditors',editDormitoryIndex);  
		var sumEditor = editors[5];  
		//设置sum字段为只读属性  
		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
		$(sumEditor.target).css('background','#DCDCDC'); 
	}
}

//删除宿舍信息
function removeDormitoryInfo(){
	var rowData =$('#dormitory_list').datagrid('getSelected');
	var index = $("#dormitory_list").datagrid("getRowIndex",rowData);
	var node = $("#dormitory_list").datagrid("getChecked");
	if(index>=0){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			if(r){
				$.ajax({
					url:prefix + "/dormitory/remove",
					type:'post',
					data:{
						id:rowData.id
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
							$('#dormitory_list').datagrid('cancelEdit', index)
							.datagrid('deleteRow', index).datagrid('clearSelections',node);
							$('#dormitory_list').datagrid('acceptChanges');
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

//保存宿舍信息
function saveDormitoryInfo(){
	var rowData =$('#dormitory_list').datagrid('getSelected');
	if (canEditDormitory()){
		$.ajax({
			url:prefix+'/dormitory/save',
			type:'post',
			data:{
				floor:rowData.floor,
				roomNo:rowData.roomNo,
				bedNo:rowData.bedNo,
				sex:rowData.sex,
				remark:rowData.remark
			},
			contentType:"application/x-www-form-urlencoded",
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
					$('#dormitory_list').datagrid('acceptChanges');
					loadDormitoryInfo();
				}else{
					$.messager.alert("消息提示！",data.msg,"warning");
				}
			}
		});
	}
}

//取消操作
function cancelDormitoryInfo(){
	$('#dormitory_list').datagrid('rejectChanges');
	editDormitoryIndex = undefined;
}

//入住
function checkingDormitory() {
	var rowDate = $('#dormitory_list').datagrid('getSelected');
	if(!rowDate){
		$.messager.alert("消息提示！","请选择一条数据","warning");
	} else if (rowDate.stay == "已入住") {
		$.messager.alert("消息提示！","该床位已有员工入住","warning");
	} else {
		$("#stay_dormitory_pnumber").val("");
		$("#stay_dormitory_pname").val("");
		loadPersonList();
		$("#dormitory_dialog").dialog("open").dialog("center");
	}
}

//条件查询员工信息
function searchDormitory(){
	console.log("111111111111");
	var pnumber = $("#stay_dormitory_pnumber").val();
	var pname = $("#stay_dormitory_pname").val();
	var data = {
		search_p_number:pnumber,
		search_p_name:pname
	}
	$("#emp_stay_dormitory_list").datagrid("load",data);
}

//条件查询宿舍信息
function searchEmployeeList(type,value){
	var floor = $("#dormitory_floor").val();
	var roomNo = $("#dormitory_roomNo").val();
	var sex = $("#dormitory_sex").val();
	var stay = $("#dormitory_stay").val();
	if(type == "1"){
		floor = value;
	} else if (type == "2") {
		sex = value;
	} else if (type == "3") {
		stay = value;
	}
	var data = {
			floor:floor,
			roomNo:roomNo,
			sex:sex,
			stay:stay
	}
	$("#dormitory_list").datagrid("load",data);
}

//条件清除
function clearSearchEmployee(){
	$("#dormitory_floor").combobox("clear");
	$("#dormitory_roomNo").val("");
	$("#dormitory_sex").combobox("clear");
	$("#dormitory_stay").combobox("clear");
	$("#dormitory_list").datagrid("load",{});
}
//员工入住
function stayEmployDormitory(){
	var dormitoryRowDate = $('#dormitory_list').datagrid('getSelected');
	var empRowDate = $("#emp_stay_dormitory_list").datagrid('getSelected');
	if(!empRowDate){
		$.messager.alert("消息提示！","请选择入住员工","warning");
		return ;
	}
	var stayTime = $("#dormitory_stay_time").val();
	if(!stayTime){
		$.messager.alert("消息提示！","请选择入住时间","warning");
		return ;	
	}
	$.ajax({
		url:prefix + "/dormitory/checking",
		type:"post",
		data:{
			dormitoryId:dormitoryRowDate.id,
			pNumber:empRowDate.p_number,
			stayTime:stayTime
		},
		success:function(result){
			if(result.code == "1"){
				$("#dormitory_dialog").dialog("close");
				$('#dormitory_list').datagrid("reload");
			} else {
				$.messager.alert("消息提示！",result.msg,"warning");
			}
		}
	})
}

//加载人员档案列表
function loadPersonList(){
	$("#emp_stay_dormitory_list").datagrid({
		url:prefix+"/loadPersonInFo",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#emp_stay_dormitory_list_tools",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"p_number",
				title:"工号",
				width:100,
			},
			{
				field:"p_name",
				title:"姓名",
				width:100,
			},
			{
				field:"p_age",
				title:"年龄",
				width:100,
			},
			{
				field:"p_sex",
				title:"性别",
				width:100,
				formatter:function(value){
					if(value=="0"){
						return "男";
					}else if(value=="1"){
						return "女";
					}
				},
			},
			{
				field:"p_department",
				title:"所属部门",
				width:100,
			}
		]],
	});
}

/**--------------------------------------已入住操作------------------------------------------------------*/

//加载入住信息
function loadStayDormitoryInfo(){
	$("#stay_dormitory_list").datagrid({
		url:prefix + "/dormitory/stay",
		type:'post',
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		toolbar:"#stay_dormitory_list_tools",
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		columns:[[
			{
				field:"id",
				title:"入住信息Id",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
				},
			},
			{
				field:"pNumber",
				title:"员工工号",
				width:100,
				editor:{
					type:'text',
				},
			},
			{
				field:"pName",
				title:"员工姓名",
				width:100,
				editor:{
					type:'text',
				},
			},
			{
				field:"floor",
				title:"楼层",
				width:100,
				editor:{
					type:'text',
				},
				formatter:function(value,row,index){
					if(row.floor == "1"){
						return "一楼";
					} else if (row.floor == "2") {
						return "二楼";
					} else if (row.floor == "3") {
						return "三楼";
					} else if (row.floor == "4") {
						return "四楼";
					} else if (row.floor == "5") {
						return "五楼";
					} else if (row.floor == "6") {
						return "六楼";
					}
				}
			},
			{
				field:"roomNo",
				title:"房间号",
				width:100,
				editor:{
					type:'text',
				},
			},
			{
				field:"bedNo",
				title:"床位号",
				width:100,
				editor:{
					type:'text',
				},
			},
			{
				field:"sex",
				title:"宿舍类型",
				width:100,
				editor:{
					type:'text',
				},formatter:function(value,row,index){
					if(row.sex == "1"){
						return "女宿舍";
					} else if (row.sex == "0") {
						return "男宿舍";
					}
				}
			},
			{
				field:"stayTime",
				title:"入住时间",
				width:100,
				editor:{
					type:'text',
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
	})	
}

//条件搜索入住人员
function searchCheckingEmployeeList(type,value){
	var pNumber = $("#checking_dormitory_pnumber").val();
	var pName = $("#checking_dormitory_pname").val();
	var floor = $("#checking_dormitory_floor").val();
	var roomNo = $("#checking_dormitory_roomNo").val();
	var sex = $("#checking_dormitory_sex").val();
	if(type == "1"){
		floor = value;
	} else if (type == "2") {
		sex = value;
	}
	var data = {
		pNumber:pNumber,
		pName:pName,
		floor:floor,
		roomNo:roomNo,
		sex:sex
	}
	$("#stay_dormitory_list").datagrid("load",data);
}

//搬出宿舍
function moveOutDormitory(){
	var rowData = $("#stay_dormitory_list").datagrid("getSelected");
	if(!rowData){
		$.messager.alert("消息提示！","请选择已入组员工","warning");
		return ;
	}
	$.ajax({
		url:prefix + "/dormitory/checkout",
		type:"post",
		data:{
			dormitoryId:rowData.id,
			pNumber:rowData.pNumber
		},
		success:function(result){
			if(result.code == "1"){
				$("#stay_dormitory_list").datagrid("reload");
			} else {
				$.messager.alert("消息提示！",result.msg,"warning");
			}
		}
	})
}

function clearSearchCheckingEmployee(){
	$("#checking_dormitory_pnumber").val("");
	$("#checking_dormitory_pname").val("");
	$("#checking_dormitory_roomNo").val("");
	$("#checking_dormitory_sex").combobox("clear");
	$("#checking_dormitory_floor").combobox("clear");
	$("#stay_dormitory_list").datagrid("load",{});
}
