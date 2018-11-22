//初始化
$(function(){
	var myDate = new Date();
	$("#year").val(myDate.getFullYear());
	$("#month").val(myDate.getMonth()+1);
	loadSumClockedResult("","",$("#year").val(),$("#month").val());
});
//条件查询
function clockedSearch(value,name){
	loadSumClockedResult(name,value,$("#year").val(),$("#month").val());
}
//加载考勤汇总数据
function loadSumClockedResult(searchKey,searchVal,myYear,myMonth){
	var	url=prefix+"/clocked/loadSumClockedResult?searchKey="+searchKey+"&searchVal="+searchVal+"&myYear="+myYear+"&myMonth="+myMonth;
	$("#clocked_grid").datagrid({
		url:url,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize : 20,
		pageList : [20, 30, 50 ],
		pageNumber:1,
		toolbar:"#clocked_tbar",
		singleSelect:true,
		//rownumbers:true,
		frozenColumns:[[
			{
				field:"period",
				title:"日期区间",
				
			},
			{
				field:"WorkNum",
				title:"工号",
				
			},
			{
				field:"UserName",
				title:"姓名",
				
			}
		]],
		columns:[[
			{
				field:"Company",
				title:"公司名称",
				
			},
			{
				field:"Organ",
				title:"中心机构名称",
				
			},
			{
				field:"Dept",
				title:"部门名称",
				
			},
			{
				field:"Office",
				title:"科室名称",
				
			},
			{
				field:"GroupName",
				title:"班组名称",
				
				
			},
			{
				field:"Post",
				title:"岗位名称",
				
				
			},
			{
				field:"CheckingType",
				title:"考勤方式",
				formatter:function(value){
					if(value=="0"){
						return "固定考勤";
					}else if(value=="1"){
						return "移动考勤";
					}else if(value=="2"){
						return "不打卡";
					}
				}
				
			},
			{
				field:"PaiBanType",
				title:"排班类型",
				formatter:function(value){
					if(value=="1"){
						return "大小周";
					}else if(value=="2"){
						return "1.5天休";
					}else if(value=="3"){
						return "双休";
					}else if(value=="4"){
						return "单休";
					}
				}
				
			},
			{
				field:"standardWorkLength",
				title:"标准工作时长",
				
				
			},
			//人事要求不显示
//			{
//				field:"originalCheckingLength",
//				title:"打卡原始时长",
//				
//				
//			},
			{
				field:"actualWorkLength",
				title:"工作时长",
				
				
			},
			{
				field:"chiDao",
				title:"迟到",
				
				
			},
			{
				field:"zaoTui",
				title:"早退",
				
				
			},
			{
				field:"kuangGongCiShu",
				title:"旷工次数",
				
				
			},
			{
				field:"kuangGong",
				title:"旷工时长",
				
				
			},
			{
				field:"Queqin",
				title:"缺勤小时数",
				
				
			},
			{
				field:"Qingjia",
				title:"请假小时数",
				
				
			},
			{
				field:"nianJia",
				title:"年假",
				
				
			},
			{
				field:"tiaoXiu",
				title:"调休",
				
				
			},
			{
				field:"shiJia",
				title:"事假",
				
				
			},
			{
				field:"bingJia",
				title:"病假",
				
				
			},
			{
				field:"peixunJia",
				title:"培训假",
				
				
			},
			{
				field:"hunJia",
				title:"婚假",
				
				
			},
			{
				field:"chanJia",
				title:"产假",
				
				
			},
			{
				field:"PeiChanJia",
				title:"陪产假",
				
				
			},
			{
				field:"SangJia",
				title:"丧假",
				
				
			},
			{
				field:"Yidong",
				title:"异动小时数",
				
				
			},
			{
				field:"Jiaban",
				title:"加班小时数",
				
				
			},
			{
				field:"Chuchai",
				title:"出差小时数",
				
				
			},
			{
				field:"Canbu",
				title:"餐补个数",
				
				
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
		},
		onDblClickRow:onDblClickRowFunction,//双击事件
	});
}
//双击查看明细
function onDblClickRowFunction(){
	var row = $("#clocked_grid").datagrid("getSelected");
	$("#clocked_win").window("open").window("setTitle","员工考勤明细");
	loadClockedResult(row.Year,row.Month,row.WorkNum,row.periodEndDate);
}
////加载考勤明细数据
function loadClockedResult(year,month,workNum,periodEndDate){
	$("#clocked_minxi_grid").datagrid({
		url:prefix+'/clocked/loadClockedResult?year='+year+'&month='+month+'&workNum='+workNum+'&periodEndDate='+periodEndDate,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize : 30,
		pageList : [20, 30, 50 ],
		pageNumber:1,
//		toolbar:"#clocked_tbar",
		singleSelect:true,
		//rownumbers:true,
		frozenColumns:[[
			{
				field:"Year",
				title:"年度",
				
			},
			{
				field:"Month",
				title:"月份",
				
			},
			{
				field:"WorkNum",
				title:"工号",
				
			},
			{
				field:"UserName",
				title:"姓名",
				
			},
		]],
		columns:[[
			{
				field:"Company",
				title:"公司名称",
				
			},
			{
				field:"Organ",
				title:"中心机构名称",
				
			},
			{
				field:"Dept",
				title:"部门名称",
				
			},
			{
				field:"Office",
				title:"科室名称",
				
			},
			{
				field:"GroupName",
				title:"班组名称",
				
				
			},
			{
				field:"Post",
				title:"岗位名称",
				
				
			},
			{
				field:"CheckingType",
				title:"考勤方式",
				formatter:function(value){
					if(value=="0"){
						return "固定考勤";
					}else if(value=="1"){
						return "移动考勤";
					}
				}
				
			},
			{
				field:"PaiBanType",
				title:"排班类型",
				formatter:function(value){
					if(value=="1"){
						return "大小周";
					}else if(value=="2"){
						return "1.5天休";
					}else if(value=="3"){
						return "双休";
					}else if(value=="4"){
						return "单休";
					}
				}
				
			},
			{
				field:"checkingDate",
				title:"日期",
				
				
			},
			{
				field:"Week",
				title:"星期",
				
				
			},
			{
				field:"beginTime",
				title:"标准上班时间",
				
				
			},
			{
				field:"endTime",
				title:"标准下班时间",
				
				
			},
			{
				field:"standardWorkLength",
				title:"标准工作时长",
				
				
			},
			{
				field:"checkingBeginTime",
				title:"打卡起始时间",
				
				
			},
			{
				field:"checkingEndTime",
				title:"打卡结束时间",
				
				
			},
			//人事要求不显示
//			{
//				field:"originalCheckingLength",
//				title:"打卡原始时长",
//				
//				
//			},
			{
				field:"actualWorkLength",
				title:"工作时长",
				
				
			},
			{
				field:"chiDao",
				title:"迟到",
				
				
			},
			{
				field:"zaoTui",
				title:"早退",
				
				
			},
			{
				field:"kuangGongCiShu",
				title:"旷工次数",
				
				
			},
			{
				field:"kuangGong",
				title:"旷工时长",
				
				
			},
			{
				field:"Queqin",
				title:"缺勤小时数",
				
				
			},
			{
				field:"Qingjia",
				title:"请假小时数",
				
				
			},
			{
				field:"nianJia",
				title:"年假",
				
				
			},
			{
				field:"tiaoXiu",
				title:"调休",
				
				
			},
			{
				field:"shiJia",
				title:"事假",
				
				
			},
			{
				field:"bingJia",
				title:"病假",
				
				
			},
			{
				field:"peixunJia",
				title:"培训假",
				
				
			},
			{
				field:"hunJia",
				title:"婚假",
				
				
			},
			{
				field:"chanJia",
				title:"产假",
				
				
			},
			{
				field:"PeiChanJia",
				title:"陪产假",
				
				
			},
			{
				field:"SangJia",
				title:"丧假",
				
				
			},
			{
				field:"Yidong",
				title:"异动小时数",
				
				
			},
			{
				field:"Jiaban",
				title:"加班小时数",
				
				
			},
			{
				field:"Chuchai",
				title:"出差小时数",
				
				
			},
			{
				field:"Canbu",
				title:"餐补个数",
				
				
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
//考勤数据初始化
function initData(){
	$.ajax({
		url:prefix+'/clocked/initClockedData',
		type:'post',
		data:{
			year:$("#year").val()==""?0:$("#year").val(),
			month:$("#month").val()==""?0:$("#month").val()
		},
		contentType:"application/x-www-form-urlencoded",
		beforeSend:function(){
			$.messager.progress({
				text:'初始化中......',
			});
		},
		success:function(data){
			$.messager.progress('close');
			if(data.status=="success"){
				$.messager.show({
					title:'消息提醒',
					msg:"初始化成功",
					timeout:3000,
					showType:'slide'
				});
				loadSumClockedResult("","",$("#year").val(),$("#month").val());
			}else{
				$.messager.show({
					title:'消息提醒',
					msg:"初始化失败",
					timeout:3000,
					showType:'slide'
				});
			}
		}
	});
}
//考勤数据归集
function executeData(){
	$.ajax({
		url:prefix+'/clocked/executeClockedData',
		type:'post',
		data:{
			year:$("#year").val()==""?0:$("#year").val(),
			month:$("#month").val()==""?0:$("#month").val()
		},
		contentType:"application/x-www-form-urlencoded",
		beforeSend:function(){
			$.messager.progress({
				text:'数据归集中......',
			});
		},
		success:function(data){
			$.messager.progress('close');
			if(data.status=="success"){
				$.messager.show({
					title:'消息提醒',
					msg:"数据归集成功",
					timeout:3000,
					showType:'slide'
				});
				loadSumClockedResult("","",$("#year").val(),$("#month").val());
			}else{
				$.messager.show({
					title:'消息提醒',
					msg:"数据归集失败",
					timeout:3000,
					showType:'slide'
				});
			}
		}
	});
}
//考勤数据推送
function pushData(){
	$.ajax({
		url:prefix+'/clocked/pushClockedData',
		type:'post',
		data:{
			year:$("#year").val()==""?0:$("#year").val(),
			month:$("#month").val()==""?0:$("#month").val()
		},
		contentType:"application/x-www-form-urlencoded",
		beforeSend:function(){
			$.messager.progress({
				text:'推送中......',
			});
		},
		success:function(data){
			$.messager.progress('close');
			if(data.status=="success"){
				$.messager.show({
					title:'消息提醒',
					msg:"数据推送成功",
					timeout:3000,
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:'消息提醒',
					msg:"数据推送失败",
					timeout:3000,
					showType:'slide'
				});
			}
		}
	});
}