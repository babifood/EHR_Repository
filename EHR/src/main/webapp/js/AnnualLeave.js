//@ sourceURL=AnnualLeave.js
var objRowsNowAnnualLeave;
//初始化
$(function(){
	//加载当前年假记录
	loadNowAnnualLeave(null);
	//加载历史年假记录
	loadHistoryAnnualLeave(null);
});
/**
 * -----------------------------------------当前年假记录function-------------------------------------------
 * @returns
 */
//当前年假记录
function loadNowAnnualLeave(npname){
	var url;
	if(npname!=null){
		url=prefix+"/loadNowAnnualLeave?npname="+npname;
	}
	else{
		url=prefix+"/loadNowAnnualLeave?npname=";
	}
	//当前年假记录列表
	$("#nannualleave_tbo").datagrid({
		url:url,
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#nannualleave_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"pname",
				title:"员工姓名",
				width:100,
			},
			{
				field:"pnumber",
				title:"员工工号",
				width:100,
			},
			{
				field:"pinday",
				title:"员工入职日期",
				width:100,
			},
			{
				field:"nowyear",
				title:"年度",
				width:100,
			},
			{
				field:"nannualleave",
				title:"本年年假数",
				width:100,
			},
			{
				field:"nannualleavedeadline",
				title:"本年失效日期",
				width:100,
			},
			{
				field:"lannualleave",
				title:"上年结余数",
				width:100,
			},
			{
				field:"lannualleavedeadline",
				title:"上年失效日期",
				width:100,
			},
			{
				field:"useddata",
				title:"已使用数",
				width:100,
			},
			{
				field:"remaindata",
				title:"剩余年假数",
				width:100,
			},
			{
				field:"disableddata",
				title:"自动作废数",
				width:100,
			},
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
//当前年假记录查询
function searchnannualleave(){
	loadNowAnnualLeave($("#search_npname").val());
}
//重置当前年假记录查询条件
function resetnannualleave(){
	$("#search_npname").val('');//人员
}
/**
 * -----------------------------------------历史年假记录function-------------------------------------------
 * @returns
 */
//历史年假记录
function loadHistoryAnnualLeave(lpname){
	var url;
	if(lpname!=null){
		url=prefix+"/loadHistoryAnnualLeave?lpname="+lpname;
	}
	else{
		url=prefix+"/loadHistoryAnnualLeave?lpname=";
	}
	//当前年假记录列表
	$("#lannualleave_tbo").datagrid({
		url:url,
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#lannualleave_tbar",
		singleSelect:true,
		rownumbers:true,
		columns:[[
			{
				field:"pname",
				title:"员工姓名",
				width:100,
			},
			{
				field:"pnumber",
				title:"员工工号",
				width:100,
			},
			{
				field:"pinday",
				title:"员工入职日期",
				width:100,
			},
			{
				field:"nowyear",
				title:"年度",
				width:100,
			},
			{
				field:"nannualleave",
				title:"本年年假数",
				width:100,
			},
			{
				field:"nannualleavedeadline",
				title:"本年失效日期",
				width:100,
			},
			{
				field:"lannualleave",
				title:"上年结余数",
				width:100,
			},
			{
				field:"lannualleavedeadline",
				title:"上年失效日期",
				width:100,
			},
			{
				field:"useddata",
				title:"已使用数",
				width:100,
			},
			{
				field:"remaindata",
				title:"剩余年假数",
				width:100,
			},
			{
				field:"disableddata",
				title:"自动作废数",
				width:100,
			},
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
//历史年假记录查询
function searchlannualleave(){
	loadHistoryAnnualLeave($("#search_lpname").val());
}
//重置历史年假记录查询条件
function resetlannualleave(){
	$("#search_lpname").val('');//人员
}