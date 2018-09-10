var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";
$(function(){
	$('#tabs').tabs({
		fit:true,
		border:false,
	});
	//导航菜单
	$('#menu').tree({
		url:prefix+"/loadTerr",
		lines:true,
		onClick:function(node){
			if(node.url){
				if($('#tabs').tabs('exists',node.text)){
					$('#tabs').tabs('select',node.text);
				}else{
					$('#tabs').tabs('add',{
						title:node.text,
						closable:true,
						href:prefix+"/redirect?pageName="+node.url,
					});
				}
			}
		}
	});
	//加载生日提醒列表信息
	loadWorkBirthdayRemind();
	//加载转正提醒列表信息
	loadWorkZhuanZhengRemind();
	//证件到期提醒
	loadCertificatenRemind();
	//员工入离职统计
	loadWorkInOutForms();
});
//退出登录
function logoout(){
	location.href = prefix+'/logout';
};
/*------------------------------------------员工生日提醒function--------------------------------------------------*/
//员工生日提醒
function loadWorkBirthdayRemind(){
	$("#birthday_grid").datagrid({
		url:prefix+'/loadBirthday',
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:5,
		pageList:[5,10,15],
		pageNumber:1,
		//toolbar:"#birthday_tbar",
		singleSelect:true,
		//rownumbers:true,
		columns:[[
			{
				field:"p_number",
				title:"员工工号",
				
			},
			{
				field:"p_name",
				title:"员工姓名",
				
			},
			{
				field:"p_company_name",
				title:"公司名称",
				
			},
			{
				field:"p_organization",
				title:"机构名称",
				
			},
			{
				field:"p_department",
				title:"部门名称",
				
			},
			{
				field:"work_birthday",
				title:"员工生日",
				
			},
			{
				field:"p_birthday",
				title:"出生日期",
				
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
//查询条件重置
function resetBirthdayInFo(){
	
}
//按条件查询
function searchBirthdayInFo(){
	
}
/*-------------------------------------------员工转正提醒function------------------------------------------------*/
//员工转正提醒
function loadWorkZhuanZhengRemind(){
	$("#zhuanZheng_grid").datagrid({
		url:prefix+'/loadZhuanZheng',
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:5,
		pageList:[5,10,15],
		pageNumber:1,
		//toolbar:"#zhuanZheng_tbar",
		singleSelect:true,
		//rownumbers:true,
		columns:[[
			{
				field:"p_number",
				title:"员工工号",
				
			},
			{
				field:"p_name",
				title:"员工姓名",
				
			},
			{
				field:"p_company_name",
				title:"公司名称",
				
			},
			{
				field:"p_organization",
				title:"机构名称",
				
			},
			{
				field:"p_department",
				title:"部门名称",
				
			},
			{
				field:"p_in_date",
				title:"入职日期",
				
			},
			{
				field:"p_turn_date",
				title:"转正日期",
				
			},
			{
				field:"shiyongqi",
				title:"试用期(天)",
				
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
//查询条件重置
function resetZhuanZhengInFo(){
	
}
//按条件查询
function searchZhuanZhengInFo(){
	
}
/*-------------------------------------------证件到期提醒function------------------------------------------------*/
//证件到期提醒
function loadCertificatenRemind(){
	$("#certificatenRemind_grid").datagrid({
		url:prefix+'/loadCertificateExpire',
		loadMsg:"数据加载中......",
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:5,
		pageList:[5,10,15],
		pageNumber:1,
		//toolbar:"#zhuanZheng_tbar",
		singleSelect:true,
		//rownumbers:true,
		columns:[[
			{
				field:"c_p_number",
				title:"员工工号",
				
			},
			{
				field:"c_p_name",
				title:"员工姓名",
				
			},
			{
				field:"c_organization",
				title:"签发机构",
				
			},
			{
				field:"c_certificate_name",
				title:"证件名称",
				
			},
			{
				field:"c_certificate_number",
				title:"证件编号",
				
			},
			{
				field:"c_begin_date",
				title:"签发日期",
				
			},
			{
				field:"c_end_date",
				title:"失效日期",
				
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
/*-------------------------------------------员工入离职报表function------------------------------------------------*/
function loadWorkInOutForms(){
	var monthDataArr = new Array()
	var inDataArr = new Array()
	var outDataArr = new Array()
	$.ajax({
		url:prefix+"/loadWorkInOutFormsData",
		type:'post',
		contentType:"application/x-www-form-urlencoded",
		success:function(data){
			for(var i=0;i<data.length;i++){
				monthDataArr.push(data[i].work_month);
				inDataArr.push(data[i].inWork);
				outDataArr.push(data[i].outWork);
			}
			showGraph(monthDataArr,inDataArr,outDataArr);
		}
	});
}
function showGraph(monthDataArr,inDataArr,outDataArr){
	var myChart=echarts.init(document.getElementById('workInOutFrom'));
	option = {
		    title : {
		        //text: '员工入离职统计',
		        //subtext: '本年度'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['入职数','离职数']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            magicType : {show: true, type: ['line', 'bar']},
		        }
		    },
		    calculable : true,
		    color : ['#ff7f50','#87cefa'],
		    xAxis : [
		        {
		            type : 'category',
		            data : monthDataArr,
		        }
		    ],
		    yAxis : [
		    	{

		    		type : 'value',

		    		minInterval : 1,

		    		axisLabel : {

		    		formatter :  '{value}'

		    		},

		    		boundaryGap : [ 0, 0.1 ],

		    		}
		    ],
		    series : [
		        {
		            name:'入职数',
		            type:'bar',
		            data:inDataArr,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            }
		        },
		        {
		            name:'离职数',
		            type:'bar',
		            data:outDataArr,
		            markPoint : {
		                data : [
		                	 {type : 'max', name: '最大值'},
			                 {type : 'min', name: '最小值'}
		                ]
		            }
		        }
		    ]
		};
	myChart.setOption(option); 
}