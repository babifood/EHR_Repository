var prefix = window.location.host;
prefix = "http://" + prefix + "/EHR";

var user;
$(function() {
	loadAllowanceList();
	initAllowanceImportExcel();
})

// 加载绩效信息列表
function loadAllowanceList() {
	$("#allowance_list_datagrid").datagrid({
		url : prefix + "/allowance/page",
		fit : true,
		striped : true,
		border : false,
		toolbar : "#allowance_list_datagrid_tools",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		pageNumber : 1,
		frozenColumns:[[
			{field : "year",title : "年",width : 40, }, 
			{field : "month",title : "月",width : 30, }, 
			{field : "pNumber",title : "员工工号",width : 60,}, 
			{field : "pName",title : "员工姓名",width : 60,}
			]],
		columns : [ [
		{
			field : "companyName",
			title : "公司名称",
			width : 100,
		}, {
			field : "organzationName",
			title : "中心机构",
			width : 100,
		}, {
			field : "deptName",
			title : "部门名称",
			width : 100,
		}, {
			field : "officeName",
			title : "科室名称",
			width : 100,
		}, {
			field : "groupName",
			title : "班组名称",
			width : 100,
		}, {
			field : "highTem",
			title : "高温津贴",
			width : 100,
		}, {
			field : "lowTem",
			title : "低温津贴",
			width : 100,
		}, {
			field : "morningShift",
			title : "早班津贴",
			width : 100,
		}, {
			field : "nightShift",
			title : "夜班津贴",
			width : 100,
		}, {
			field : "stay",
			title : "驻外/住宿津贴",
			hidden:"true",
			width : 100,
		}, {
			field : "otherAllowance",
			title : "其它津贴",
			width : 100,
		}, {
			field : "performanceBonus",
			title : "绩效奖金",
			hidden:"true",
			width : 100,
		}, {
			field : "security",
			title : "安全奖金",
			width : 100,
		}, {
			field : "compensatory",
			title : "礼金/补偿金",
			width : 100,
		}, {
			field : "otherBonus",
			title : "其它奖金",
			width : 100,
		}, {
			field : "addOther",
			title : "加其它",
			width : 100,
		}, {
			field : "mealDeduction",
			title : "餐费扣除",
			hidden:"true",
			width : 100,
		}, {
			field : "dormDeduction",
			title : "住宿扣除",
			hidden:"true",
			width : 100,
		}, {
			field : "beforeDeduction",
			title : "税前扣款",
			width : 100,
		}, {
			field : "insurance",
			title : "代缴社保",
			width : 100,
		}, {
			field : "overSalary",
			title : "加班工资",
			width : 100,
		}, {
			field : "providentFund",
			title : "公积金",
			width : 100,
		}, {
			field : "afterOtherDeduction",
			title : "税后扣款",
			width : 100,
		}, {
			field : "reserved1",
			title : "预留字段1",
			width : 100,
		}, {
			field : "reserved2",
			title : "预留字段2",
			width : 100,
		}, {
			field : "reserved3",
			title : "预留字段3",
			width : 100,
		}, {
			field : "reserved4",
			hidden:"true",
			title : "预留字段4",
			width : 100,
		}, {
			field : "reserved5",
			title : "预留字段5",
			hidden:"true",
			width : 100,
		}, {
			field : "reserved6",
			title : "预留字段6",
			hidden:"true",
			width : 100,
		}, {
			field : "reserved7",
			title : "预留字段7",
			hidden:"true",
			width : 100,
		}, {
			field : "reserved8",
			title : "预留字段8",
			hidden:"true",
			width : 100,
		}, {
			field : "reserved9",
			title : "预留字段9",
			hidden:"true",
			width : 100,
		}, {
			field : "reserved10",
			title : "预留字段10",
			hidden:"true",
			width : 100,
		} ] ]
	})
}

//导入excel的初始条件
function initAllowanceImportExcel(){
	$("#allowance_dialog").importExcel1({
		url:prefix + "/allowance/importExcel",
		success:function(result){
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功', 'info', function() {
					$("#allowance_dialog").dialog('close');
					$("#allowance_list_datagrid").datagrid("reload");
				});
			} else {
				$.messager.confirm('提示', result.msg);
			}
		}
	});
}

//导入弹框
function allowanceImport() {
	$("#allowance_dialog").importExcel1.dialog();
}

// 条件查询绩效信息列表
function loadConditionAllowance() {
	var pNumber = $("#allowance_pNumber").val();
	var pName = $("#allowance_pName").val();
	var organzationName = $("#allowance_organzationName").val();
	var deptName = $("#allowancee_deptName").val();
	var officeName = $("#allowancee_officeName").val();
	$("#allowance_list_datagrid").datagrid("load", {
		pNumber : pNumber,
		pName : pName,
		deptName : deptName,
		organzationName : organzationName,
		officeName : officeName
	})
}

// 导出
function exportAllowance(type) {
	window.location.href = prefix + "/allowance/export?type=" + type;
}

function importAllowanceInfos1(){
	console.log("11111111111111111111");
	if(canSubmit()){
		console.log("222222222222222222222");
		
		var file = $('#allowance_file').filebox("getText");
		console.log(file);
	    //判断文件上传是否为空
	    if (file == null || file == "") {
	        $.messager.alert('系统提示', '请选择将要上传的文件!');
	        return;
	    }
	    //分割文件的类型
	    var file_typename = file.substring(file.lastIndexOf('.'), file.length);
	    if (file_typename == '.xlsx' || file_typename == '.xls') {
	        var options = {
	            method: 'POST',
	            url: prefix + "/allowance/improtExcel",
	            data: file,
	            dataType: 'text',
	            success: function (data) {
	            	console.log("3333333333333333333333333");
	            	consolo.log(data);
	                if (data == 'True') {
	                    $.messager.show({
	                        title: '提示',
	                        msg: '用户批量导入成功',
	                        showType: 'slide'
	                    })
	                    $('#allowance_file').filebox("setText", "");

	                } else {
	                    //$.messager.alert('警告', '导入异常，请检查是否正确使用模板！')
	                    $.messager.alert('提示', data)
	                }
	            }
	        }
	        $('#allowance_file').ajaxSubmit(options);
	    } else {
	        $.messager.alert('提示', '请选择正确的文件类型')
	    }
//		$("#allowance_booten").linkbutton('disable');
	}
}


function canSubmit() {
	var fileName = $('#allowance_file').filebox('getValue');
	// 对文件格式进行校验
	var d1 = /\.[^\.]+$/.exec(fileName);
	if (fileName == "") {
		$.messager.alert('Excel批量绩效导入', '请选择将要上传的文件!');
		return false;
	} else if (d1 != ".xls") {
		$.messager.alert('提示', '请选择xls格式文件！', 'info');
		return false;
	}
	return true;
}

// 导入Excel
function importAllowanceInfos11111() {
	console.log("11111111111111111111");
	$("#allowance_uploadExcel").form({
		type : 'post',
		url : prefix + "/allowance/improtExcel",
		dataType : "json",
		onSubmit : function() {
			var fileName = $('#allowance_file').filebox('getValue');
			// 对文件格式进行校验
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (fileName == "") {
				$.messager.alert('Excel批量绩效导入', '请选择将要上传的文件!');
				return false;
			} else if (d1 != ".xls") {
				$.messager.alert('提示', '请选择xls格式文件！', 'info');
				return false;
			}
			$("#allowance_booten").linkbutton('disable');
			return true;
		},
		success : function(result) {
			console.log(result);
			var obj = JSON.parse(result);
			if (obj.code == "1") {
				$.messager.alert('提示!', '导入成功', 'info', function() {
					$("#allowance_booten").linkbutton('enable');
					$('#allowance_dialog').dialog('close');
					$("#allowance_list_datagrid").datagrid("reload");
				});
			} else {
				$.messager.confirm('提示', "导入失败!");
				$("#allowance_booten").linkbutton('enable');
			}
		}
	})
	$('#allowance_uploadExcel').submit();
}

function clearSearchAllowance() {
	$("#allowance_pNumber").val("");
	$("#allowance_pName").val("");
	$("#allowance_organzationName").val("");
	$("#allowancee_deptName").val("");
	$("#allowancee_officeName").val("");
	$("#allowance_list_datagrid").datagrid("load",{});
}