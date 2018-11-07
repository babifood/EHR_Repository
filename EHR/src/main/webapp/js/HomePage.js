var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";
$(function(){
	$('#tabs').tabs({
		fit:true,
		border:false,
	});
	//加载导航菜单
	RightAccordion();
	//加载生日提醒列表信息
	loadWorkBirthdayRemind();
	//加载转正提醒列表信息
	loadWorkZhuanZhengRemind();
	//证件到期提醒
	loadCertificatenRemind();
	//合同到期提醒
	loadContractRemind();
});
//导航菜单
function RightAccordion(){
	 $("#RightAccordion").accordion({ //初始化accordion
         fillSpace:true,
         fit:true,
         border:false,
         animate:false  
     });
	 $.post(prefix+"/loadTerr", { "id": "0" }, //获取第一层目录
             function (data) {
                 if (data == "0") {
                     window.location = "/Account";
                 }
                 $.each(data, function (i, e) {//循环创建手风琴的项
                     var id = e.id;
                     $('#RightAccordion').accordion('add', {
                         title: e.text,
                         content: "<ul id='tree"+id+"' ></ul>",
                         selected: true,
                         iconCls:e.iconCls//e.Icon
                     });
                     $.parser.parse();
                     $.post(prefix+"/loadTerr?id="+id,  function(data) {//循环创建树的项
                         $("#tree" + id).tree({
                             data: data,
                             onBeforeExpand:function(node,param){  
                                 $("#tree" + id).tree('options').url = prefix+"/loadTerr?id=" + node.id;
                             },   
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
                     }, 'json');
                 });
             }, "json");
}
//退出登录
function logoout(){
	location.href = prefix+'/logout';
};
//打开修改密码窗口
function updatePassword(){
	$("#updatePsd_dog").dialog("open").dialog("center").dialog("setTitle","修改密码");
	$("#updatePsd_form").form('clear');
};
//更新密码
function updateNewPsd(){
	$.messager.progress();	// 显示进度条
	$('#updatePsd_form').form('submit', {
		url:prefix+"/updatePassword",
		onSubmit: function(param){
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			}
			return isValid;	// 返回false终止表单提交
		},
		success: function(data){
			console.log(data);
			if(data=="succeed"){
				$.messager.show({
					title:'消息提醒',
					msg:"密码修改成功!",
					timeout:3000,
					showType:'slide'
				});
				$('#updatePsd_dog').dialog('close');
			}else if(data=="error"){
				$.messager.alert("消息提示！","用户名或密码错误!","warning");
			}else{
				$.messager.alert("消息提示！","网络连接失败","warning");
			}
			$.messager.progress('close');	// 如果提交成功则隐藏进度条
		}
	});
};
$.extend($.fn.validatebox.defaults.rules, {    
    equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '两次密码不一致!'   
    },
//    username: {// 验证用户名
//        validator: function (value) {
//            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
//        },
//        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
//    },
    pasd: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '密码格式不正确（字母开头，允许6-16字节，允许字母数字下划线）'
    },
}); 
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
				title:"中心机构名称",
				
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
				title:"中心机构名称",
				
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
/*-------------------------------------------员工合同到期提醒function------------------------------------------------*/
function loadContractRemind(){
	$("#contractRemind_grid").datagrid({
		url:prefix+'/loadContractExpire',
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
				title:"中心机构名称",
				
			},
			{
				field:"p_department",
				title:"部门名称",
				
			},
			{
				field:"p_contract_begin_date",
				title:"合同签订日期",
				
			},
			{
				field:"p_contract_end_date",
				title:"合同到期日期",
				
			},
			{
				field:"p_contract_count",
				title:"合同签订次数",
				
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
//function loadWorkInOutForms(){
//	var monthDataArr = new Array()
//	var inDataArr = new Array()
//	var outDataArr = new Array()
//	$.ajax({
//		url:prefix+"/loadWorkInOutFormsData",
//		type:'post',
//		contentType:"application/x-www-form-urlencoded",
//		success:function(data){
//			for(var i=0;i<data.length;i++){
//				monthDataArr.push(data[i].work_month);
//				inDataArr.push(data[i].inWork);
//				outDataArr.push(data[i].outWork);
//			}
//			showGraph(monthDataArr,inDataArr,outDataArr);
//		}
//	});
//}
//function showGraph(monthDataArr,inDataArr,outDataArr){
//	var myChart=echarts.init(document.getElementById('workInOutFrom'));
//	option = {
//		    title : {
//		        //text: '员工入离职统计',
//		        //subtext: '本年度'
//		    },
//		    tooltip : {
//		        trigger: 'axis'
//		    },
//		    legend: {
//		        data:['入职数','离职数']
//		    },
//		    toolbox: {
//		        show : true,
//		        feature : {
//		            magicType : {show: true, type: ['line', 'bar']},
//		        }
//		    },
//		    calculable : true,
//		    color : ['#ff7f50','#87cefa'],
//		    xAxis : [
//		        {
//		            type : 'category',
//		            data : monthDataArr,
//		        }
//		    ],
//		    yAxis : [
//		    	{
//
//		    		type : 'value',
//
//		    		minInterval : 1,
//
//		    		axisLabel : {
//
//		    		formatter :  '{value}'
//
//		    		},
//
//		    		boundaryGap : [ 0, 0.1 ],
//
//		    		}
//		    ],
//		    series : [
//		        {
//		            name:'入职数',
//		            type:'bar',
//		            data:inDataArr,
//		            markPoint : {
//		                data : [
//		                    {type : 'max', name: '最大值'},
//		                    {type : 'min', name: '最小值'}
//		                ]
//		            }
//		        },
//		        {
//		            name:'离职数',
//		            type:'bar',
//		            data:outDataArr,
//		            markPoint : {
//		                data : [
//		                	 {type : 'max', name: '最大值'},
//			                 {type : 'min', name: '最小值'}
//		                ]
//		            }
//		        }
//		    ]
//		};
//	myChart.setOption(option); 
//}