var prefix = window.location.host;
prefix = "http://" + prefix + "/EHR";

$(function() {
	loadBasicSalaryDetails();
	initBasicSalaryDetailImportExcel();
})

//加载薪资明细数据
function loadBasicSalaryDetails() {
	$("#basic_salaryDetail_datagrid").datagrid({
		url:prefix + "/basicSalaryDetail/page",
		type : 'post',
		rownumbers : true,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar : "#basic_salaryDetail_datagrid_tools",
		singleSelect : true,
		frozenColumns:[[
			{field : "year",title : "年",width : 40, }, 
			{field : "month",title : "月",width : 30, }, 
			{field : "pNumber",title : "员工工号",width : 60,}, 
			{field : "pName",title : "员工姓名",width : 60,}
			]],
		columns : [[
			{field : "id",title : "",hidden : "true",width : 100, }, 
			{field : "companyName",title : "公司名称",width : 100,},
			{field : "organizationName",title : "中心机构",	width : 100,},
			{field : "deptName",title : "部门信息",width : 100,},
			{field : "officeName",title : "科室名称",	width : 100,},
			{field : "groupName",title : "班组名称",width : 100,},
			{field : "postName",title : "岗位名称",width : 100,},
			{field : "baseSalary",title : "基本工资",width : 100,},
			{field : "fixedOverTimeSalary",title : "固定加班工资",width : 100,},
			{field : "postSalary",title : "岗位工资",width : 100,},
			{field : "companySalary",title : "司龄工资",width : 100,},
			{field : "callSubsidies",title : "话费补贴",width : 100,},
			{field : "performanceBonus",title : "绩效工资",width : 100,},
			{field : "totalDeduction",title : "应扣合计",width : 100,},
			{field : "wagePayable",title : "应发工资",width : 100,},
			{field : "personalTax",title : "代缴税金",width : 100,},
			{field : "realWages",title : "实发工资",width : 100,},
			{field : "attendanceHours",title : "应出勤小时候",width : 100,},
			{field : "absenceHours",title : "缺勤小时数",width : 100,},
			{field : "overSalary",title : "加班工资",width : 100,},
			{field : "riceStick",title : "饭贴",width : 100,},
			{field : "highTem",title : "高温津贴",width : 100,},
			{field : "lowTem",title : "低温津贴",width : 100,},
			{field : "morningShift",title : "早班津贴",width : 100,},
			{field : "nightShift",title : "夜班津贴",width : 100,},
			{field : "stay",title : "驻外/住宿津贴",width : 100,},
			{field : "otherAllowance",title : "其它津贴",width : 100,},
			{field : "security",title : "安全奖金",width : 100,},
			{field : "compensatory",title : "礼金/补偿金",width : 100,},
			{field : "otherBonus",title : "其它奖金",width : 100,},
			{field : "addOther",title : "加其它",width : 100,},
			{field : "mealDeduction",title : "餐费扣款",width : 100,},
			{field : "dormDeduction",title : "住宿扣款",width : 100,},
			{field : "beforeDeduction",title : "其它扣款（税前）",width : 100,},
			{field : "insurance",title : "社保扣款",width : 100,},
			{field : "providentFund",title : "公积金扣款",width : 100,},
			{field : "mealDeduction",title : "餐费扣款",width : 100,},
			{field : "afterDeduction",title : "其它扣款（税后）",width : 100,},
			{field : "laterAndLeaveDeduction",title : "迟到和早退扣款",width : 100,},
			{field : "completionDeduction",title : "旷工扣款",width : 100, },
			{field : "yearDeduction",title : "年假扣款",width : 100,},
			{field : "relaxation",title : "调休扣款",width : 100,},
			{field : "thingDeduction",title : "事假扣款",width : 100,},
			{field : "sickDeduction",title : "病假扣款",width : 100,},
			{field : "trainDeduction",title : "培训假扣款",width : 100,},
			{field : "parentalDeduction",title : "产假扣款",width : 100,},
			{field : "marriageDeduction",title : "婚假扣款",width : 100, },
			{field : "companionParentalDeduction",title : "陪产假扣款",width : 100, },
			{field : "funeralDeduction",title : "丧假扣款",width : 100, },
			{field : "onboarding",title : "月中入职、离职导致缺勤扣款",width : 100, }
		]]
	})
}

//条件查询绩效信息列表
function loadBasicSalaryDetail() {
	var pNumber = $("#basic_salary_detail_number").val();
	var pName = $("#basic_salary_detail_name").val();
	var organzationName = $("#basic_salary_detail_organzationName").val();
	var deptName = $("#basic_salary_detail_deptName").val();
	var officeName = $("#basic_salary_detail_officeName").val();
	var companyCode = $('#basic_salary_calculation_company').val();
	$("#basic_salaryDetail_datagrid").datagrid("load", {
		pNumber : pNumber,
		pName : pName,
		deptName : deptName,
		organzationName : organzationName,
		officeName : officeName,
		companyCode:companyCode
	})
}

//导入excel的初始条件
function initBasicSalaryDetailImportExcel(){
	$("#basic_salaryDetail_dialog").importExcel1({
		url:prefix + "/basicSalaryDetail/importExcel",
		success:function(result){
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功', 'info', function() {
					$("#basic_salaryDetail_dialog").dialog('close');
					$("#basic_salaryDetail_datagrid").datagrid("reload");
				});
			} else {
				$.messager.confirm('提示', result.msg);
			}
		}
	});
}

//导入弹框
function basicSalaryDetailImport() {
	$("#basic_salaryDetail_dialog").importExcel1.dialog();
}

//导出模板
function exportBasicSalaryDetail() {
	window.location.href = prefix + "/basicSalaryDetail/export";
}

function clearSearchBasicSalaryDetail(){
	$("#basic_salary_detail_number").val("");
	$("#basic_salary_detail_name").val("");
	$("#basic_salary_detail_organzationName").val("");
	$("#basic_salary_detail_deptName").val("");
	$("#basic_salary_detail_officeName").val("");
	$("#basic_salaryDetail_datagrid").datagrid("load",{});
}
