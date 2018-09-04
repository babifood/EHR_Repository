var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";


$(function() {
	loadDate();
	initDormitoryImportExcel();
	console.log("11111111111111");
});

function loadDate(){
	$("#dormitory_tabs").tabs({
		fit:true,
		onSelect:function (title,index){
			if(title == "住宿管理"){
				loadDormitoryInfo();
			} else if(title == "住宿记录"){
				loadStayDormitoryInfo();
			} else if (title == "宿舍费用管理") {
				loadDormitoryCostList();
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
						valueField: 'value',
						required:true,
						editable:false,
						textField: 'label',
						data:[{label:"一楼",value:"1"},{label:"二楼",value:"2"},{label:"三楼",value:"3"},
							{label:"四楼",value:"4"},{label:"五楼",value:"5"},{label:"六楼",value:"6"}]
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
				field : "pNumber",
				title : "员工工号",
				width : 100,
			}, {
				field : "pName",
				title : "员工姓名",
				width : 100,
			},
			{
				field:"stayTime",
				title:"入住时间",
				width:100,
				editor:{
					type:'text',
					options:{
						
					},
				},
			},
			{
				field:"outTime",
				title:"搬出时间",
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

//新增宿舍信息
function addDormitoryInfo(){
	if (!editDormitoryIndex){
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
	if(!editDormitoryIndex){
		var rowData =$('#dormitory_list').datagrid('getSelected');
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
								$('#dormitory_list').datagrid('reload')
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

//保存宿舍信息
function saveDormitoryInfo(){
	var rowData =$('#dormitory_list').datagrid('getSelected');
	if ($('#dormitory_list').datagrid('validateRow', editDormitoryIndex)){
		$('#dormitory_list').datagrid('endEdit', editDormitoryIndex);
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
				editDormitoryIndex = undefined;
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
	if (!editDormitoryIndex){
		var rowDate = $('#dormitory_list').datagrid('getSelected');
		if(!rowDate){
			$.messager.alert("消息提示！","请选择一条数据","warning");
		} else if (rowDate.pNumber != null && rowDate.pNumber != "") {
			$.messager.alert("消息提示！","该床位已有员工入住","warning");
		} else {
			$("#stay_dormitory_pnumber").val("");
			$("#stay_dormitory_pname").val("");
			loadPersonList();
			$("#dormitory_dialog").dialog("open").dialog("center");
		}
	}
}

//条件查询员工信息
function searchDormitory(){
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
	var pSex = empRowDate.p_sex;
	if(pSex != dormitoryRowDate.sex){
		var message = pSex == '0' ? '男员工不能入住女宿舍' : '女同事不能入住男宿舍';
		$.messager.alert("消息提示！",message,"warning");
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
				},
				formatter:function(value,row,index){
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
				field:"outTime",
				title:"搬出时间",
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

//搬出手续办理
function moveOutDormitoryFormalities(){
	var rowData = $("#dormitory_list").datagrid("getSelected");
	if(!rowData || !rowData.pNumber){
		$.messager.alert("消息提示！","请选择已入住员工","warning");
	} else {
		$("#dormitory_outtime_dialog").dialog("open");
		$("#dormitory_moveout_time").datebox('setValue', '');
	}
}

//实际搬出宿舍
function moveOutDormitory(){
	var rowData = $("#dormitory_list").datagrid("getSelected");
	if(!rowData || !rowData.pNumber){
		$.messager.alert("消息提示！","请选择已入住员工","warning");
	} else if (rowData.outTime == null || rowData.outTime == "") {
		$.messager.alert("消息提示！","请选先办理搬出手续","warning");
	}else {
		selectDormitoryCost(1);
	}
}

function selectDormitoryCost(type){
	var outTime = $("#dormitory_moveout_time").val();
	if(!outTime){
		$.messager.alert("消息提示！","请选择搬出时间","warning");
	} else {
		$.messager.confirm("提示","确定搬出宿舍吗？",function(r){
			if(r){
				var rowData = $("#dormitory_list").datagrid("getSelected");
				$.ajax({
					url:prefix + "/dormitory/checkout",
					type:"post",
					data:{
						dormitoryId:rowData.id,
						pNumber:rowData.pNumber,
						outTime:outTime,
						type:type
					},
					success:function(result){
						if(result.code == "1"){
							$("#dormitory_list").datagrid("reload");
						} else {
							$.messager.alert("消息提示！",result.msg,"warning");
						}
					}
				});
				$("#dormitory_outtime_dialog").dialog("close");
			}
		})
	}
}

//清空搜索条件
function clearSearchCheckingEmployee(){
	$("#checking_dormitory_pnumber").val("");
	$("#checking_dormitory_pname").val("");
	$("#checking_dormitory_roomNo").val("");
	$("#checking_dormitory_sex").combobox("clear");
	$("#checking_dormitory_floor").combobox("clear");
	$("#stay_dormitory_list").datagrid("load",{});
}

/**---------------------------------------住宿费用相关-------------------------------------------*/
//加载费用信息
function loadDormitoryCostList() {
	$("#dormitory_cost_list").datagrid({
		url:prefix + "/dormitory/cost",
		type:'post',
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		toolbar:"#dormitory_cost_list_tools",
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		columns:[[
			{
				field:"id",
				title:"费用id",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
				},
			},
			{
				field:"year",
				title:"年",
				width:100,
				editor:{
					type:'numberspinner',
					options:{
						min: 2018,
						required:true,
					}
				},
			},
			{
				field:"month",
				title:"月",
				width:100,
				editor:{
					type:'combobox',
					options:{
						valueField: 'value',
						required:true,
						textField: 'lable',
						data:[{lable:"01",value:"01"},{lable:"02",value:"02"},{lable:"03",value:"03"},
							{lable:"04",value:"04"},{lable:"05",value:"05"},{lable:"06",value:"06"},
							{lable:"07",value:"07"},{lable:"08",value:"08"},{lable:"09",value:"09"},
							{lable:"10",value:"10"},{lable:"11",value:"11"},{lable:"12",value:"12"}]
					}
				},
			},
			{
				field:"pNumber",
				title:"员工工号",
				width:100,
				editor:{
					type:'text',
					options : {

					},
				},
			},
			{
				field:"pName",
				title:"员工姓名",
				width:100,
				editor : {
					type:'combogrid',
					options:{
						panelWidth:400,
						idField:'p_number',
						textField:'p_name',
						toolbar:getDormitotyCostTools(),
						url:prefix+'/loadPersonInFo',
						columns:[[
							{field:'p_number',title:'员工编号',width:100},
							{field:'p_name',title:'员工名称',width:100},
						]],
						required:true,
						editable:false,
						onSelect:function (index, row){
							var pNumber = $('#dormitory_cost_list').datagrid('getEditor', {index:editDormitoryCostIndex,field:'pNumber'});
							var pName = $('#dormitory_cost_list').datagrid('getEditor', {index:editDormitoryCostIndex,field:'pName'});
							console.log(row.p_number);
							$(pNumber.target).val(row.p_number); 
							$(pName.target).val(row.p_name); 
						}
					},
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
				field:"dormBonus",
				title:"宿舍奖励",
				width:100,
				editor:{
					type:'numberbox',options:{precision:2,required:true}
				},
			},
			{
				field:"dormDeduction",
				title:"住宿扣款",
				width:100,
				editor:{
					type:'numberbox',options:{precision:2,required:true,groupSeparator:','}
				},
			}
		]]
	})	
}

var editDormitoryCostIndex
//新增费用信息
function addDormitoryCost(){
	if (editDormitoryCostIndex != 0 && !editDormitoryCostIndex){
		$('#dormitory_cost_list').datagrid('appendRow',{});
		editDormitoryCostIndex= $('#dormitory_cost_list').datagrid('getRows').length-1;
		$('#dormitory_cost_list').datagrid('selectRow', editDormitoryCostIndex).datagrid('beginEdit', editDormitoryCostIndex);
		var editors = $("#dormitory_cost_list").datagrid('getEditors',editDormitoryCostIndex);  
		var sumEditor = editors[3];  
		//设置sum字段为只读属性  
		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
		$(sumEditor.target).css('background','#DCDCDC'); 
		sumEditor = editors[5];  
		//设置sum字段为只读属性  
		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
		$(sumEditor.target).css('background','#DCDCDC');
		sumEditor = editors[6];  
		//设置sum字段为只读属性  
		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
		$(sumEditor.target).css('background','#DCDCDC');
		sumEditor = editors[7];  
		//设置sum字段为只读属性  
		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
		$(sumEditor.target).css('background','#DCDCDC');
		$("#dormitory_cost_p_number").val("");
		$("#dormitory_cost_p_name").val("");
	}
}
//编辑费用
function updateDormitoryCost(){
	if (editDormitoryCostIndex != 0 && !editDormitoryCostIndex){
		var row = $('#dormitory_cost_list').datagrid("getSelected");
		if(!row){
			$.messager.alert("消息提示！","请选择一条数据","warning");
		} else {
			var index = $('#dormitory_cost_list').datagrid("getRowIndex", row);	
			if (editDormitoryCostIndex != index){
				$('#dormitory_cost_list').datagrid('selectRow', index).datagrid('beginEdit', index);
				editDormitoryCostIndex = index;
			}
		}
	}
}

//删除费用信息
function removeDormitoryCost(){
	if(editDormitoryCostIndex != 0 && !editDormitoryCostIndex){
		var rowData =$('#dormitory_cost_list').datagrid('getSelected');
		if(rowData){
			$.messager.confirm("提示","确定要删除此数据？",function(r){
				if(r){
					$.ajax({
						url:prefix + "/dormitory/removeCost",
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
								$('#dormitory_cost_list').datagrid('reload')
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

//保存费用信息
function saveDormitoryCost(){
	if(editDormitoryCostIndex == 0 || editDormitoryCostIndex){
		if ($('#dormitory_cost_list').datagrid('validateRow', editDormitoryCostIndex)){
			var rowData =$('#dormitory_cost_list').datagrid('getSelected');
			$('#dormitory_cost_list').datagrid('endEdit', editDormitoryCostIndex);
			$.ajax({
				url:prefix+'/dormitory/saveCost',
				type:'post',
				data:{
					id:rowData.id,
					year:rowData.year,
					month:rowData.month,
					pNumber:rowData.pNumber,
					dormBonus:rowData.dormBonus,
					dormDeduction:rowData.dormDeduction
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
						$('#dormitory_cost_list').datagrid('reload');
						editDormitoryCostIndex = undefined;
					}else{
						$.messager.alert("消息提示！",data.msg,"warning");
					}
				}
			});
		}
	}
}

//取消操作
function cancelDormitoryCost(){
	$('#dormitory_cost_list').datagrid('rejectChanges');
	editDormitoryCostIndex = undefined;
}

//人员搜索框
function getDormitotyCostTools(){
	var tools = document.createElement("div");
	tools.id = "dormitory_cost_member_bar";
	tools.appendChild(document.createTextNode("工号："));
	var input = document.createElement("input");
	input.type="text";input.classList.add("textbox");input.id="dormitory_cost_p_number";
	input.style="width: 110px;";
	input.value="";
	input.oninput=function(){
		searchBaseSalaryPerson();
	};
	tools.appendChild(input);
	tools.appendChild(document.createTextNode(" "));
	tools.appendChild(document.createTextNode("姓名："));
	var input1 = document.createElement("input");
	input1.type="text";input1.classList.add("textbox");input1.id="dormitory_cost_p_name";
	input1.style="width: 110px;";
	input.value="";
	input1.oninput=function(){
		searchDormitoryCostPerson();
	};
	tools.appendChild(input1);
	return tools;
}

//条件搜索人员信息
function searchDormitoryCostPerson(){
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_number = $("#dormitory_cost_p_number").val();
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_name = $("#dormitory_cost_p_name").val();
	$('.combogrid-f').combogrid('grid').datagrid('reload');
}

//条件搜索费用信息
function searchDormitoryCostList(type,value){
	var pNumber = $("#dormitory_cost_pnumber").val();
	var pName = $("#dormitory_cost_pname").val();
	var floor = $("#dormitory_cost_floor").val();
	var roomNo = $("#dormitory_cost_roomNo").val();
	if(type == "1"){
		floor = value;
	}
	var data = {
		pNumber:pNumber,
		pName:pName,
		floor:floor,
		roomNo:roomNo,
	}
	$("#dormitory_cost_list").datagrid("load",data);
}

//清空费用搜索条件
function clearSearchDormitoryCost() {
	$("#dormitory_cost_pnumber").val("");
	$("#dormitory_cost_pname").val("");
	$("#dormitory_cost_roomNo").val("");
	$("#dormitory_cost_floor").combobox("clear");
	$("#dormitory_cost_list").datagrid("load",{});
}

//导出
function exportDormitoryCost(type){
	window.location.href = prefix + "/dormitory/export?type=" + type;
}

//导入excel的初始条件
function initDormitoryImportExcel(){
	$("#dormitory_cost_dialog").importExcel1({
		url:prefix + "/dormitory/importExcel",
		success:function(result){
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功','info',
						function() {
							$('#dormitory_cost_dialog').dialog('close');
							$("#dormitory_cost_list").datagrid("reload");
					    });
			} else {
				$.messager.confirm('提示', "导入失败!");
			}
		}
	});
}

//导入弹框
function dormitoryCostImport(){
	$("#dormitory_cost_dialog").importExcel1.dialog();
//	$('#dormitory_cost_file').filebox('clear');
//	$("#dormitory_cost_dialog").dialog("open");
//	$("#dormitory_cost_booten").linkbutton('enable');
}

//导入Excel
//function importPerformanceInfos(){
//	$("#dormitory_cost_uploadExcel").form({
//		type : 'post',
//		url : prefix + "/dormitory/importExcel",
//		dataType : "json",
//		onSubmit: function() {
//			var fileName= $('#dormitory_cost_file').filebox('getValue'); 
//			//对文件格式进行校验  
//            var d1=/\.[^\.]+$/.exec(fileName);
//			if (fileName == "") {  
//			      $.messager.alert('Excel批量绩效导入', '请选择将要上传的文件!'); 
//			      return false;  
//			 }else if(d1!=".xls"){
//				 $.messager.alert('提示','请选择xls格式文件！','info');  
//				 return false; 
//			 }
//			 $("#dormitory_cost_booten").linkbutton('disable');
//            return true;  
//        }, 
//		success : function(result) {
//			var obj = JSON.parse(result);
//			if (obj.code == "1") {
//				$.messager.alert('提示!', '导入成功','info',
//					function() {
//						$('#dormitory_cost_dialog').dialog('close');
//						$("#dormitory_cost_list").datagrid("reload");
//				    });
//			} else {
//				$.messager.confirm('提示',"导入失败!");
//			}
//			$("#dormitory_cost_booten").linkbutton('enable');
//		}
//	})
//	$('#dormitory_cost_uploadExcel').submit();
//}
