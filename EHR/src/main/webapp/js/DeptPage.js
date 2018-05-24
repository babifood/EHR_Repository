var prefix = window.location.host;
prefix = "http://" + prefix +"/EHR";

//初始化
$(function(){
//	var datasource;
	//加载树状菜单
	loadDepts();
	//打印功能
	printArea();
	
	//修改部门信息
	$(".dept-update").click(function(){
		updateDept();
	})
	
	//修改部门信息
	$(".dept-add").click(function(){
		addDept();
	})
	
	$(".dept-del").click(function(){
		deleteDept();
	});
});

/**
 * ------------------------------------------部门架构树function-------------------------------------------------
 * @returns
 */
//加载树状菜单
function loadDepts(){
	//加载部门架构树
	$('#tt').tree({    
	    url:prefix+'/dept/loadTree',
//	    checkbox : true,
	    lines:true,
		onClick:function(node){
			loadOrganizeTree(node.deptCode);
		}
	});
	loadOrganizeTree("1000");
}



/**
 * ------------------------------------------组织架构树function-------------------------------------------------
 * @returns
 */

//加载组织机构数
function loadOrganizeTree(deptCode){
	$.ajax({
		url:prefix+'/dept/loadTree',
		type:'post',
		data:{
			deptCode:deptCode,
		},
		contentType:"application/x-www-form-urlencoded",
		beforeSend:function(){
			$.messager.progress({
				text:'加载中......'
			});
		},
		success:function(data){
			$.messager.progress('close');
			if(data == null || data == ""){
				$.messager.progress({
					text:'组织机构信息不存在'
				});
			} else {
				var node = data[0];
				if(node.children){
					//加载组织机构数
					loadTissueArchitectureTree(data[0])
				}
			}
		}
	});
}


//加载部门信息页面的部门架构树状菜单
function loadTissueArchitectureTree(datasource){
	if(datasource != null && datasource != ""){
		$('#chart-container').html("");
		$('#chart-container').orgchart({
	      'data' : datasource,
	      'nodeContent': 'title',
	      'draggable': true,
	      'dropCriteria': function($draggedNode, $dragZone, $dropZone) {
	        if($draggedNode.find('.content').text().indexOf('manager') > -1 && $dropZone.find('.content').text().indexOf('engineer') > -1) {
	          return false;
	        }
	        return true;
	      }
	    })
	    .children('.orgchart').on('nodedropped.orgchart', function(event) {
	      console.log('draggedNode:' + event.draggedNode.children('.title').text()
	        + ', dragZone:' + event.dragZone.children('.title').text()
	        + ', dropZone:' + event.dropZone.children('.title').text()
	      );
	    });
	} else {
		$.messager.progress({
			text:'组织机构信息不存在'
		});
	}
}

/**
 * ------------------------------------------打印功能function-------------------------------------------------
 * @returns
 */
function printArea(){
	$("#printArea").click(function(){  
		var $html = $("#chart-container").html();
		console.log($html);
		if($html){
			$("#chart-container").printArea();
		} else {
			text:'组织机构图不存在'
		}
	     
	  })
}

/**
 * ------------------------------------------增删改查function-------------------------------------------------
 * @returns
 */
function addDept(){
	$("#dept_dog").dialog("open").dialog("center").dialog("setTitle","新增部门");
	$("#dept_id").val("");
	$("#dept_name").val("");
	$("#dept_code").val("");
	$("#dept_code").removeAttr("readonly");
	$("#dept_remark").val("");
	$("#pCode").val("");
	$("#pCode").attr("dept_code","0");
	var node = $('#tt').tree('getSelected');
	if(node){
		console.log(node);
		$("#pCode").attr("dept_code",node.deptCode);
		$("#pCode").val(node.deptName);
	}
	chooseDept();
}


function updateDept(){
	var node = $('#tt').tree('getSelected');
	if(!node){
		$.messager.alert({
			msg:'请选择部门',
			title:'消息提醒',
			icon:'warning',
		});
	} else {
		$("#dept_dog").dialog("open").dialog("center").dialog("setTitle","修改部门信息");
		$("#dept_id").val(node.id);
		$("#dept_name").val(node.deptName);
		$("#dept_code").val(node.deptCode);
		$("#dept_code").attr("readonly","readonly");
		$("#dept_remark").val(node.remark);
		$("#pCode").val("");
		$("#pCode").attr("dept_code","0");
		$.ajax({
			url:prefix + "/dept/findAll",
			type:'get',
			success:function(datasource){
				if(datasource != null){
					$.each(datasource,function(index,value){
						if(node.pCode == value.deptCode){
							$("#pCode").attr("dept_code",node.pCode);
							$("#pCode").val(value.deptName);
							return false;
						}
					})
				}
				
			}
		});
	}
	chooseDept();
}

function chooseDept(){
	$("#pCode").removeAttr("onclick");
	$("#pCode").click(function(){
		selectPDept();
		$("#dept-tree-dog").dialog("open").dialog("center").dialog("setTitle","请选择上级部门");
	});
}

function selectPDept(){
	$("#dept-tree-dog").tree({    
	    url:prefix+"/dept/loadTree",
	    lines:true,
	    onDblClick:function(node){
			$("#pCode").attr("dept_code",node.deptCode);
			$("#pCode").val(node.deptName);
			$("#dept-tree-dog").dialog("close");
		}
	});
}

function deleteDept(){
	var node = $('#tt').tree('getSelected');
	if(!node){
		$.messager.alert({
			msg:'请选择部门',
			title:'消息提醒',
			icon:'warning',
		});
	}
	$.messager.confirm("提示","确定要删除此数据？",function(r){
		if(r){
			var deptCode = node.deptCode;
			$.ajax({
				url:prefix+"/dept/deleteDept",
				data:{
					deptCode:deptCode
				},
				success:function(result){
					if(result.code == 1){
						loadDepts();
					} else {
						$.messager.alert({
							msg:result.msg,
							title:"消息提示",
							icon:'warning',
						});
					}
				}
			});
		}
	});
}

//新增或保存部门信息
function savedept(){
	var deptId = $("#dept_id").val();
	var deptCode = $("#dept_code").val();
	var deptName = $("#dept_name").val();
	var remark = $("#dept_remark").val();
	var pCode = $("#pCode").attr("dept_code");
	data = {
		id:deptId,
		deptName:deptName,
		deptCode:deptCode,
		pCode:pCode,
		remark:remark
	};
	var url ;
	if(deptId){
		url = "/dept/updateDept";
		saveOrUpdate(url,data);
	} else {
		$.ajax({
			url:"dept/queryCount?deptCode="+deptCode,
			success:function(result){
				if(result.code == "1"){
					if(result.count > 0){
						$.messager.alert({
							msg:"部门编号已存在",
							title:'消息提醒',
							icon:'warning',
						});
					} else {
						url = "/dept/addDept";
						saveOrUpdate(url,data);
					}
				} else {
					$.messager.alert({
						msg:result.msg,
						title:'消息提醒',
						icon:'warning',
					});
				}
			}
		});
	}
	
}

function saveOrUpdate(url,data) {
	$.ajax({
		url: prefix + url,
		data:data,
		success:function(result){
			if(result.code == "1"){
				$("#dept_dog").dialog("close");
				loadDepts();
			} else {
				$("#dept_dog").dialog("close");
				$.messager.alert({
					msg:result.msg,
					title:'消息提醒',
					icon:'warning',
				});
			}
		}
	});
}


