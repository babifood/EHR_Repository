var myYear,myMonth;
var searchKey="",searchVal="",comp="",organ="",dept="";
var year=0,month=0;
var workNum="",periodEndDate="";
//初始化
$(function(){
	var myDate = new Date();
	myYear = myDate.getFullYear();
	myMonth = myDate.getMonth()+1;
	$("#year").val(myDate.getFullYear());
	$("#month").val(myDate.getMonth()+1);
	loadSumClockedResult("","",$("#year").val(),$("#month").val(),"","","");
});
//条件查询
function clockedSearch(value,name){
	searchKey = name;
	searchVal = value;
	myYear = $("#year").val();
	myMonth = $("#month").val();
	comp = $("#Search_Company").val();
	organ = $("#Search_Organ").val();
	dept = $("#Search_Dept").val();
	loadSumClockedResult(name,value,myYear,myMonth,comp,organ,dept);
}
//加载考勤汇总数据
function loadSumClockedResult(searchKey,searchVal,myYear,myMonth,comp,organ,dept){
	var	url=prefix+"/clocked/loadSumClockedResult?searchKey="+searchKey+"&searchVal="+searchVal+"&myYear="+myYear+"&myMonth="+myMonth+"&comp="+comp+"&organ="+organ+"&dept="+dept;
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
		onRowContextMenu:function(e,rowIndex,rowData){
			 //右键时触发事件  
            //三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
            e.preventDefault(); //阻止浏览器捕获右键事件  
            $("#clocked_grid").datagrid("clearSelections"); //取消所有选中项  
            $('#clocked_menu').menu('show', {  
                //显示右键菜单  
                left: e.pageX,//在鼠标点击处显示菜单  
                top: e.pageY  
            });  
            e.preventDefault();  //阻止浏览器自带的右键菜单弹出  
		}
	});
}
//双击查看明细
function onDblClickRowFunction(){
	var row = $("#clocked_grid").datagrid("getSelected");
	$("#clocked_win").window("open").window("setTitle","员工考勤明细");
	loadClockedResult(row.Year,row.Month,row.WorkNum,row.periodEndDate);
	year = row.Year;
	month = row.Month;
	workNum = row.WorkNum;
	periodEndDate = row.periodEndDate;
}
//导出汇总数据
function exportClockedSumInfo(){
	window.location.href = prefix + "/clocked/sumClockedResultExport?searchKey="+searchKey+"&searchVal="+searchVal+"&myYear="+myYear+"&myMonth="+myMonth+"&comp="+comp+"&organ="+organ+"&dept="+dept;
}
//导出个人明细数据
function exportClockedDetailsInfo(){
	window.location.href = prefix + "/clocked/detailsClockedResultExport?year="+year+"&month="+month+"&workNum="+workNum+"&periodEndDate="+periodEndDate;
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
		},
		onRowContextMenu:function(e,rowIndex,rowData){
			 //右键时触发事件  
           //三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
           e.preventDefault(); //阻止浏览器捕获右键事件  
           $("#clocked_minxi_grid").datagrid("clearSelections"); //取消所有选中项  
           $('#clocked_minxi_menu').menu('show', {  
               //显示右键菜单  
               left: e.pageX,//在鼠标点击处显示菜单  
               top: e.pageY  
           });  
           e.preventDefault();  //阻止浏览器自带的右键菜单弹出  
		}
	});
}
//考勤数据初始化
function initData(){
	//验证当前年份和月份的数据是否已经封存
//	$.post(prefix+"/",
//			{year:$("#year").val()==""?0:$("#year").val(),
//			 month:$("#month").val()==""?0:$("#month").val()},
//			function(data,status){
//				 if(data=="3"){
//					 $.messager.alert("警告",$("#year").val()+"年"+$("#month").val()+"月的数据已经封存,不能初始化!","warning");   
//				 }else{
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
								loadSumClockedResult("","",$("#year").val(),$("#month").val(),"","","");	
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
//				 }
//			 });
	
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
				loadSumClockedResult("","",$("#year").val(),$("#month").val(),"","","");
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
//数据锁定
function lockData(){
	$.ajax({
		url:prefix+'/clocked/lockClockedData',
		type:'post',
		data:{
			year:$("#year").val()==""?0:$("#year").val(),
			month:$("#month").val()==""?0:$("#month").val()
		},
		contentType:"application/x-www-form-urlencoded",
		beforeSend:function(){
			$.messager.progress({
				text:'封存中......',
			});
		},
		success:function(data){
			$.messager.progress('close');
			if(data.status=="success"){
				$.messager.show({
					title:'消息提醒',
					msg:"数据封存成功",
					timeout:3000,
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:'消息提醒',
					msg:"数据封存失败",
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