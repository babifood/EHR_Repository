var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";

$(function() {
	loadPerformanceList();
})

//加载绩效信息列表
function loadPerformanceList(){
	$("#performance_list_datagrid").datagrid({
		url : prefix + "/performance/page",
		fit:true,
		fitColumns : true,
		striped : true,
		border : false,
		toolbar : "#performance_list_datagrid_tools",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30 ],
		pageNumber : 1,
		columns : [[
			{
				field : "year",
				title : "年",
				width : 100,
			},{
				field : "month",
				title : "月",
				width : 100,
			},{
				field : "pNumber",
				title : "员工编号",
				width : 100,
			},{
				field : "pName",
				title : "员工姓名",
				width : 100,
			},{
				field : "organzationName",
				title : "组织机构名称",
				width : 100,
			},{
				field : "deptName",
				title : "部门名称",
				width : 100,
			},{
				field : "officeName",
				title : "科室名称",
				width : 100,
			},{
				field : "groupName",
				title : "班组名称",
				width : 100,
			},{
				field : "performanceScore",
				title : "绩效分值",
				width : 100,
			},{
				field : "performanceSalary",
				title : "绩效工资",
				width : 100,
			}
		]]
	})
}

//条件查询绩效信息列表
function loadConditionPerformance(){
	var pNumber = $("#performance_salary_pNumber").val();
	var pName = $("#performance_salary_pName").val();
	var organzationName = $("#performance_salary_organzationName").val();
	var deptName = $("#performancee_salary_deptName").val();
	var officeName = $("#performancee_salary_officeName").val();
	$("#performance_list_datagrid").datagrid("load",{
		pNumber:pNumber,
		pName:pName,
		deptName:deptName,
		organzationName:organzationName,
		officeName:officeName
	})
}

//导出
function exportPerformance(type){
	window.location.href = prefix + "/performance/export?type=" + type;
}

//导入弹框
function performanceImport(){
	$('#performance_file').filebox('clear');
	$("#performance_dialog").dialog("open");
}

//导入Excel
function importPerformanceInfos(){
	$("#performance_uploadExcel").form({
		type : 'post',
		url : prefix + "/performance/importExcel",
		dataType : "json",
		onSubmit: function() {
			var fileName= $('#performance_file').filebox('getValue'); 
			//对文件格式进行校验  
            var d1=/\.[^\.]+$/.exec(fileName);
			if (fileName == "") {  
			      $.messager.alert('Excel批量绩效导入', '请选择将要上传的文件!'); 
			      return false;  
			 }else if(d1!=".xls"){
				 $.messager.alert('提示','请选择xls格式文件！','info');  
				 return false; 
			 }
			 $("#performance_booten").linkbutton('disable');
            return true;  
        }, 
		success : function(result) {
			var result = eval('(' + result + ')');
			console.log("1111111111111");
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功','info',
					function() {
						$("#performance_booten").linkbutton('enable');
						$('#performance_dialog').dialog('close');
						$("#performance_list_datagrid").datagrid("reload");
				    });
			} else {
				$.messager.confirm('提示',"导入失败!");
				$("#performance_booten").linkbutton('enable');
			}
		}
	})
	$('#performance_uploadExcel').submit();
}

