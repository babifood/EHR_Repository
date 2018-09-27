var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";

$(function() {
	loadPerformanceList();
	initPerformanceImportExcel();
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
				field : "id",
				title : "年",
				hidden:"true",
				width : 100,
			},{
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
				field : "companyName",
				title : "公司名称",
				width : 100,
			},{
				field : "organzationName",
				title : "中心机构",
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
				editor:{
					type:'text',
					options:{
						required:true,
					},
				},
			},{
				field : "pSalary",
				title : "绩效工资",
				width : 100,
				editor:{
					type:'text',
					options:{
						required:true,
					},
				},
			}
		]]
	})
}

function initPerformanceImportExcel(){
	$("#performance_dialog").importExcel1({
		url:prefix + "/performance/importExcel",
		success:function(result){
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功', 'info', function() {
					$("#performance_dialog").dialog('close');
					$("#performance_list_datagrid").datagrid("reload");
				});
			} else {
				$.messager.confirm('提示', "导入失败!");
			}
		}
	});
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
//	$('#performance_file').filebox('clear');
//	$("#performance_dialog").dialog("open");
//	$("#performance_booten").linkbutton('enable');
	$("#performance_dialog").importExcel1.dialog();
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
			var obj = JSON.parse(result);
			if (obj.code == "1") {
				$.messager.alert('提示!', '导入成功','info',
					function() {
						$('#performance_dialog').dialog('close');
						$("#performance_list_datagrid").datagrid("reload");
				    });
			} else {
				$.messager.confirm('提示',"导入失败!");
			}
			$("#performance_booten").linkbutton('enable');
		}
	})
	$('#performance_uploadExcel').submit();
}

var performanceIndex;
var pSalary;
var performanceScore;

//修改绩效分数
function updatePerformanceSocre() {
	if(performanceIndex == null){
		var row = $('#performance_list_datagrid').datagrid("getSelected");
		if(!row){
			$.messager.alert("消息提示！","请选择一条数据","warning");
		} else {
			var index = $('#performance_list_datagrid').datagrid("getRowIndex", row);
			if (performanceIndex != index){
				$('#performance_list_datagrid').datagrid('selectRow', index)
				.datagrid('beginEdit', index);
				performanceIndex = index;
				pSalary = row.pSalary;
				performanceScore = row.performanceScore;
			} else {
				$('#performance_list_datagrid').datagrid('selectRow', performanceIndex);
			}
		}
	}
}

//取消
function cancelPerformanceSocre(){
	$('#performance_list_datagrid').datagrid('rejectChanges');
	performanceIndex = undefined;
}

//保存绩效分数
function savePerformanceSocre() {
	if (performanceIndex >= 0 && $('#performance_list_datagrid').datagrid('validateRow', performanceIndex)){
		$('#performance_list_datagrid').datagrid('endEdit', performanceIndex);
		var rows = $('#performance_list_datagrid').datagrid("getRows");
		var rowData = rows[performanceIndex];
		if(pSalary != null && pSalary != "" && rowData.performanceScore){
			$('#performance_list_datagrid').datagrid('selectRow', performanceIndex).datagrid('beginEdit', performanceIndex);
			$.messager.alert("消息提示！","绩效分数非导入，不能修改!","warning");
		} else if(performanceScore != null && performanceScore != "" && rowData.pSalary){
			$('#performance_list_datagrid').datagrid('selectRow', performanceIndex).datagrid('beginEdit', performanceIndex);
			$.messager.alert("消息提示！","绩效工资非导入，不能修改!","warning");
		} else {
			$.ajax({
				url:prefix+'/performance/save',
				type:'post',
				data:{
					year:rowData.year,
					month:rowData.month,
					pNumber:rowData.pNumber,
					score:rowData.performanceScore,
					salary:rowData.pSalary
				},
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
						performanceIndex = undefined;
						$('#performance_list_datagrid').datagrid('reload');
					}else{
						$.messager.alert("消息提示！","保存失败!","warning");
						$('#performance_list_datagrid').datagrid('selectRow', performanceIndex).datagrid('beginEdit', performanceIndex);
					}
				}
			});
		}
	}
}

function clearSearchPerformance() {
	$("#performance_salary_pNumber").val("");
	$("#performance_salary_pName").val("");
	$("#performance_salary_organzationName").val("");
	$("#performancee_salary_deptName").val("");
	$("#performancee_salary_officeName").val("");
	$("#performance_list_datagrid").datagrid("load",{});
}


