var editIndex = undefined; 
var combogridUrl;
//初始化
$(function(){
	//加载列表
	loadCertificaten("","","","","");
	//初始化导出导出功能
	initImportExcel();
});
//下拉列表查询人员信息
function searchPerson(pNumber, pName){
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_number = pNumber;
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_name = pName;
	$('.combogrid-f').combogrid('grid').datagrid('reload');
}
//加载教育背景列表
function loadCertificaten(c_p_number,c_p_name,zj_name,zj_code,orga){
	var url;
	url=prefix+"/loadCertificaten?c_p_number="+c_p_number+"&c_p_name="+c_p_name+"&zj_name="+zj_name+"&zj_code="+zj_code+"&orga="+orga;
	$("#certificaten_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize : 20,
		pageList : [20, 30, 50 ],
		pageNumber:1,
		toolbar:"#certificaten_tbar",
		singleSelect:true,
		columns:[[
			{
				field:"c_p_id",
				title:"人员id",
				width:100,
				hidden:"true",
				editor:{
					type:'text',
					options:{
						
					},
				},
			},
			{
				field:"c_p_number",
				title:"人员编号",
				width:100,
				editor:{
					type:'text',
					options:{
						
					},
				},
			},
			{
				field:"c_p_name",
				title:"人员名称",
				width:120,
				editor:{
					type:'combogrid',
					options:{
						panelWidth:320,
						idField:'p_name',
						textField:'p_name',
						url:prefix+'/loadPersonlimit',
						toolbar:createToolbar(),
						loadMsg:'玩命加载中......',
						columns:[[
							{field:'p_number',title:'员工编号',width:100},
							{field:'p_name',title:'员工名称',width:100},
						]],
						required:true,
						onSelect:function (index, row){
							var ed_num = $('#certificaten_grid').datagrid('getEditor', {index:editIndex,field:'c_p_number'});
							var ed_id = $('#certificaten_grid').datagrid('getEditor', {index:editIndex,field:'c_p_id'});
							$(ed_num.target).val(row.p_number); 
							$(ed_id.target).val(row.p_id); 
						}
					},
				}
			},
			{
				field:"c_certificate_name",
				title:"证件名称",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_organization",
				title:"签发机构",
				width:180,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_certificate_number",
				title:"证件编号",
				width:180,
				editor:{
					type:'validatebox',
					options:{
						required:true,
						//validType:'idcard'
					},
				},
			},
			{
				field:"c_begin_date",
				title:"颁发日期",
				width:110,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true,
						onSelect:function(date){
							var editors = $("#certificaten_grid").datagrid('getEditors',editIndex);
							var enddate = editors[7];
							var enddateValue = enddate.target.datebox('getValue');
							if(enddateValue!=null&&enddateValue!=""){
								var beginDate = new Date(date);
							    var endDate = new Date(enddateValue);
							    if(beginDate.getTime()>endDate.getTime()){
							    	$.messager.alert("消息提示！","颁发日期不能大于失效日期!","warning");
							    	editors[6].target.datebox().clear();
							    }
							}
					    }
					},
				},
			},
			{
				field:"c_end_date",
				title:"失效日期",
				width:110,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true,
						onSelect:function(date){
							var editors = $("#certificaten_grid").datagrid('getEditors',editIndex);
							var begdate = editors[6];
							var begdateValue = begdate.target.datebox('getValue');
							if(begdateValue!=null&&begdateValue!=""){
								var beginDate = new Date(begdateValue);
							    var endDate = new Date(date);
							    if(endDate.getTime()<beginDate.getTime()){
							    	$.messager.alert("消息提示！","失效日期不能小于或等于颁发日期!","warning");
							    	editors[7].target.datebox().clear();
							    }
							}
					    }

					},
				},
			},
			{
				field:"c_desc",
				title:"备注",
				width:200,
				editor:{
					type:'text',
					options:{
						
					},
				},
			}
		]],
		onDblClickRow:onDblClickRowCertificaten,
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
//动态创建Toolbar
function createToolbar(){
	var tools = document.createElement("div");
	tools.id = "dormitory_cost_member_bar";
	tools.appendChild(document.createTextNode("工号："));
	var input = document.createElement("input");
	input.type="text";input.classList.add("textbox");input.id="query_p_number";
	input.style="width: 110px;";
	input.value="";
	input.oninput=function(value){
		searchPerson(value.target.value, null);
	};
	tools.appendChild(input);
	tools.appendChild(document.createTextNode(" "));
	tools.appendChild(document.createTextNode("姓名："));
	var input1 = document.createElement("input");
	input1.type="text";input1.classList.add("textbox");input1.id="query_p_name";
	input1.style="width: 110px;";
	input.value="";
	input1.oninput=function(value){
		searchPerson(null, value.target.value);
	};
	tools.appendChild(input1);
	return tools;
}
//查询条件重置
function resetCertificaten(){
	$("#search_p_number").val("");
	$("#search_p_name").val("");
	$("#search_zj_name").val("");
	$("#search_zj_code").val("");
	$("#search_orga").val("");
}
//查询
function searchCertificaten(){
	var numb = $("#search_p_number").val();
	var name = $("#search_p_name").val();
	var zj_name = $("#search_zj_name").val();
	var zj_code = $("#search_zj_code").val();
	var orga = $("#search_orga").val();
	loadCertificaten(numb,name,zj_name,zj_code,orga);
}
//结束编辑
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#certificaten_grid').datagrid('validateRow', editIndex)){
		$('#certificaten_grid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addCertificaten(){
	if (endEditing()){
		$('#certificaten_grid').datagrid('appendRow',{});
		editIndex = $('#certificaten_grid').datagrid('getRows').length-1;
		$('#certificaten_grid').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
		var editors = $("#certificaten_grid").datagrid('getEditors',editIndex);  
		var sumEditor = editors[1];  
		//设置sum字段为只读属性  
		$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
		$(sumEditor.target).css('background','#DCDCDC');
	}
}
//删除
function removeCertificaten(){
	var rowData =$('#certificaten_grid').datagrid('getSelected');
	var index = $("#certificaten_grid").datagrid("getRowIndex",rowData);
	var node = $("#certificaten_grid").datagrid("getChecked");
	if(index>=0){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			if(r){
				$.ajax({
					url:prefix+'/removeCertificaten',
					type:'post',
					data:{
						c_id:rowData.c_id
					},
					contentType:"application/x-www-form-urlencoded",
					beforeSend:function(){
						$.messager.progress({
							text:'删除中......',
						});
					},
					success:function(data){
						$.messager.progress('close');
						if(data.status=="success"){
							$.messager.show({
								title:'消息提醒',
								msg:'删除成功!',
								timeout:3000,
								showType:'slide'
							});
							$('#certificaten_grid').datagrid('cancelEdit', index)
							.datagrid('deleteRow', index).datagrid('clearSelections',node);
							$('#certificaten_grid').datagrid('acceptChanges');
							editIndex = undefined;
							loadCertificaten("","","","","");
						}else{
							$.messager.alert("消息提示！","删除失败!","warning");
						}
					}
				});
			}
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}	
};
//赋值
function setData(){
	var rowData =$('#certificaten_grid').datagrid('getSelected');
	var data={
			c_id:rowData.c_id,
			c_p_id:rowData.c_p_id,
			c_p_number:rowData.c_p_number,
			c_p_name:rowData.c_p_name,
			c_certificate_name:rowData.c_certificate_name,
			c_organization:rowData.c_organization,
			c_certificate_number:rowData.c_certificate_number,
			c_begin_date:rowData.c_begin_date,
			c_end_date:rowData.c_end_date,
			c_desc:rowData.c_desc
	}
	return data;
}
//保存
function acceptCertificaten(){
	if (endEditing()){
		$.ajax({
			url:prefix+'/saveCertificaten',
			type:'post',
			data:JSON.stringify(setData()),
			contentType:"application/json",
			beforeSend:function(){
				$.messager.progress({
					text:'保存中......',
				});
			},
			success:function(data){
				$.messager.progress('close');
				if(data.status=="success"){
					$.messager.show({
						title:'消息提醒',
						msg:'保存成功!',
						timeout:3000,
						showType:'slide'
					});
					$('#certificaten_grid').datagrid('acceptChanges');
					loadCertificaten("","","","","");
				}else{
					$.messager.alert("消息提示！","保存失败!","warning");
				}
			}
		});
	}
}
//取消
function rejectCertificaten(){
	$('#certificaten_grid').datagrid('rejectChanges');
	editIndex = undefined;
}
//双击编辑
function onDblClickRowCertificaten(index){
	if (editIndex != index){
		if (endEditing()){
			$('#certificaten_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
			var editors = $("#certificaten_grid").datagrid('getEditors',editIndex);  
			var sumEditor = editors[1];  
			//设置sum字段为只读属性  
			$(sumEditor.target).attr({'readonly':true,'unselectable':'on'});  
			$(sumEditor.target).css('background','#DCDCDC');
			//从新加载一下选择人员combogrid列表
			$('.combogrid-f').combogrid('grid').datagrid('reload');
		} else {
			$('#certificaten_grid').datagrid('selectRow', editIndex);
		}
	}
}
//----------------------------------导入导出方法---------------------------------------/
function initImportExcel(){
	$("#certificaten_dlg").importExcel1({
		url:prefix + "/CertificatenImportExcel",
		success:function(result){
			if (result.code == "1") {
				$.messager.alert('提示!', '导入成功', 'info', function() {
					$("#certificaten_dlg").dialog('close');
					$("#certificaten_grid").datagrid("reload");
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
function exportCertificaten(type){
	window.location.href = prefix + "/CertificatenExport?type=" + type;
}
/**
 * 导入弹框选择文件
 * @returns
 */
function certificatenImport(){
	$("#certificaten_dlg").importExcel1.dialog();
}