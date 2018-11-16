//初始化
$(function(){
	loadWorkshopClocked();
	initImportExcel();
});
//条件查询
function workshopSearch(){
	loadWorkshopClocked();
}
//重置
function workshopReset(){
	$("#workshop_WorkNum").val("");
	$("#workshop_UserName").val("");
}
//加载考勤汇总数据
function loadWorkshopClocked(){
	var WorkNum = $("#workshop_WorkNum").val();
	var UserName = $("#workshop_UserName").val();
	var url;
	if(WorkNum!=null&&UserName!=null){
		url=prefix+"/loadWorkshopClocked?WorkNum="+WorkNum+"&UserName="+UserName;
	}else if(WorkNum!=null&&UserName==null){
		url=prefix+"/loadWorkshopClocked?WorkNum="+WorkNum+"&UserName=";
	}else if(WorkNum==null&&UserName!=null){
		url=prefix+"/loadWorkshopClocked?WorkNum=&UserName="+UserName;
	}else{
		url=prefix+"/loadWorkshopClocked?WorkNum=&UserName=";
	}
	$("#workshop_grid").datagrid({
		url:url,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#workshop_tbar",
		singleSelect:true,
		columns:[[
			{
				field:"workshop_year",
				title:"年度",
				
			},
			{
				field:"workshop_month",
				title:"月份",
				
			},
			{
				field:"worknum",
				title:"工号",
				
			},
			{
				field:"workname",
				title:"姓名",
				
			},
			{
				field:"p_company_name",
				title:"公司名称",
				
			},
			{
				field:"p_organization",
				title:"单位机构",
				
			},
			{
				field:"p_department",
				title:"部门名称",
				
			},
			{
				field:"p_section_office",
				title:"科室名称",
				
			},
			{
				field:"p_group",
				title:"班组名称",
				
				
			},
			{
				field:"p_post",
				title:"岗位名称",
				
				
			},
			{
				field:"standardWorkDateLength",
				title:"标准工作时长",
				
				
			},
			{
				field:"practicalWorkDateLength",
				title:"实际工作时长",
				
				
			}
		]],
		loadFilter:function(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
	            data = {
	                total: data.length,
	                rows: data
	            }
	        }
	        var dg = $(this);
	        var opts = dg.datagrid('options');
	        var pager = dg.datagrid('getPager');
	        pager.pagination({
	            onSelectPage:function(pageNum, pageSize){
	                opts.pageNumber = pageNum;
	                opts.pageSize = pageSize;
	                pager.pagination('refresh',{
	                    pageNumber:pageNum,
	                    pageSize:pageSize
	                });
	                dg.datagrid('loadData',data);
	            }
	        });
	        if (!data.originalRows){
	            data.originalRows = (data.rows);
	        }
	        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	        var end = start + parseInt(opts.pageSize);
	        data.rows = (data.originalRows.slice(start, end));
	        return data;
		}
	});
}
//----------------------------------导入导出方法---------------------------------------/
function initImportExcel(){
	$("#workshop_dlg").importExcel1({
		url:prefix + "/WorkshopClockedImportExcel",
		success:function(result){
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功', 'info', function() {
					$("#workshop_dlg").dialog('close');
					$("#workshop_grid").datagrid("reload");
				});
			} else {
				$.messager.confirm('提示', result.msg);
			}
		}
	});
}
/**
 * 导出和下载啊模板
 * type=0,下载模板
 * type=1,导出数据
 */
function exportWorkshop(type){
	window.location.href = prefix + "/WorkshopClockedExport?type=" + type;
}
/**
 * 导入弹框选择文件
 * @returns
 */
function WorkshopImport(){
	$("#workshop_dlg").importExcel1.dialog();
}