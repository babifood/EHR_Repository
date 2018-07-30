var editIndex = undefined;   

//初始化
$(function(){
	//重写Text
	overrideGridEditText();
	//加载列表
	loadCertificaten(null,null);
});
//重写Text
function overrideGridEditText(){
	$.extend($.fn.datagrid.defaults.editors, {
		combogrid: {
			init: function(container, options){
				var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container); 
				input.combogrid(options);
				return input;
			},
			destroy: function(target){
				$(target).combogrid('destroy');
			},
			getValue: function(target){
				return $(target).combogrid('getValue');
			},
			setValue: function(target, value){
				$(target).combogrid('setValue', value);
			},
			resize: function(target, width){
				$(target).combogrid('resize',width);
			}
		}
	});
}
//下拉列表查询人员信息
function searchPerson(){
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_number = $("#p_number").val();
	$('.combogrid-f').combogrid('grid').datagrid('options').queryParams.search_p_name = $("#p_name").val();
	$('.combogrid-f').combogrid('grid').datagrid('reload');
}
//加载教育背景列表
function loadCertificaten(c_p_number,c_p_name){
	var url;
	if(c_p_number!=null&&c_p_name!=null){
		url=prefix+"/loadCertificaten?c_p_number="+c_p_number+"&c_p_name="+c_p_name;
	}else if(c_p_number!=null&&c_p_name==null){
		url=prefix+"/loadCertificaten?c_p_number="+c_p_number+"&c_p_name=";
	}else if(c_p_number==null&&c_p_name!=null){
		url=prefix+"/loadCertificaten?c_p_number=&c_p_name="+c_p_name;
	}else{
		url=prefix+"/loadCertificaten?c_p_number=&c_p_name=";
	}
	$("#certificaten_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
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
				width:100,
				editor:{
					type:'combogrid',
					options:{
						panelWidth:400,
						idField:'p_name',
						textField:'p_name',
						toolbar:'#Marketer_ID_Member_bar',
						url:prefix+'/loadPersonInFo',
						columns:[[
							{field:'p_number',title:'员工编号',width:100},
							{field:'p_name',title:'员工名称',width:100},
						]],
						required:true,
						editable:false,
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
						required:true
					},
				},
			},
			{
				field:"c_begin_date",
				title:"颁发日期",
				width:120,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"c_end_date",
				title:"失效日期",
				width:120,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
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
//查询条件重置
function resetCertificaten(){
	$("#search_p_number").val("");$("#search_p_name").val("");
}
//查询
function searchCertificaten(){
	loadCertificaten($("#search_p_number").val(),$("#search_p_name").val());
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
		$('#certificaten_grid').datagrid('selectRow', editIndex)
		.datagrid('beginEdit', editIndex);
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
}
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
					loadCertificaten(null,null);
				}else{
					$.messager.alert("消息提示！","保存失败!","warning");
				}
			}
		});
	}
}
function saveCertificaten(){
	
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
		} else {
			$('#certificaten_grid').datagrid('selectRow', editIndex);
		}
	}
}