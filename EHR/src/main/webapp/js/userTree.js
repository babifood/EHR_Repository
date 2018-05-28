//@ sourceURL=userTree.js
function selectPerson(){
	//选择人员窗口
	$('#test_dog').dialog({
	    title: '选择人员',
	    width: 600,
	    height: 500,
	    closed: false,
	    cache: false,
	    modal: true
	});
	var unSelectDurl;
	unSelectDurl=prefix+'/userTreeDept';
	//部门树
	$('#unSelectD').tree({
		url: unSelectDurl,
		lines:true,
		loadFilter: function(rows){
			return convert(rows);
		},
		//点击部门，显示部门下属人员
		onClick: function(node){
			selectUnSelectPersonByDeptId(node.id);
		}
	});
	//显示已选人员，根据每个不同的使用场景更换前面jsp里链接的js
	selectInSelectPerson();
}
//动态生成部门树
function convert(rows){
	var nodes = [];
	// get the top level nodes
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		var PID;
		PID=row.parentId;
		if (!exists(rows, PID)){
			nodes.push({
				id:row.id,
				text:row.name
			});
		}
	}
	function exists(rows,parentId){
		for(var i=0; i<rows.length; i++){
			if (rows[i].id == parentId) return true;
		}
		return false;
	}
	
	var toDo = [];
	for(var i=0; i<nodes.length; i++){
		toDo.push(nodes[i]);
	}
	while(toDo.length){
		var node = toDo.shift();	// the parent node
		// get the children nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (row.parentId == node.id){
				var child = {id:row.id,text:row.name};
				if (node.children){
					node.children.push(child);
				} else {
					node.children = [child];
				}
				toDo.push(child);
			}
		}
	}
	return nodes;
	
}
//未选中人员查询按钮
function searchunselect(){
	var p_name=$("#serch_unselect").val();
	selectUnSelectPersonByPersonName(p_name);
}
//查找未选中人员by部门id(点击部门，直接显示部门下属人员）
function selectUnSelectPersonByDeptId(dept_id){
	var SelectPurl;
	if(dept_id!=null){	
		SelectPurl=prefix+'/unSelectPersonByDeptID?dept_id='+dept_id;
	}else{
		SelectPurl=prefix+'/unSelectPersonByDeptID?dept_id=';
	}
	$('#unSelectP').datagrid({    
	    url:SelectPurl, 
	    columns:[[
			{
				field:"p_name",
				title:"待选员工姓名",
				width:193,
			},
		]]   
	});
}
//查找未选中人员by人员姓名（在搜索框里输入模糊查询）
function selectUnSelectPersonByPersonName(p_name){
	var SelectPurl;
	if(p_name!=null){	
		SelectPurl=prefix+'/unSelectPersonByPersonName?p_name='+p_name;
	}else{
		SelectPurl=prefix+'/unSelectPersonByPersonName?p_name=';
	}
	$('#unSelectP').datagrid({
	    url:SelectPurl, 
	    columns:[[
			{
				field:"p_name",
				title:"待选员工姓名",
				width:193,
			},
		]] 
	});	
	/*var data=$('#unSelectP').datagrid('getData');
	alert('总数据量:'+data.total);
	alert('当前页数据量:'+data.rows.length);*/
}
//已选中人员查询按钮
function searchinselect(){
	var p_name=$("#serch_inselect").val();
	//selectInSelectPersonByPersonName(p_name);
}
//选中人员到右边
function $add() {
	var nodes = $('#unSelectP').datagrid('getChecked');
    //生成json
	var data ={total: nodes.length,rows: []};
	for (var i = 0; i < nodes.length; i++) {
            var hawbMapping = new Object();
            hawbMapping.p_id = nodes[i].p_id;
            hawbMapping.p_name = nodes[i].p_name;
            data.rows.push(hawbMapping);
        }
    var jsonString = JSON.stringify(data);
    addPersonInSelectedBox(jsonString);
}
//添加选择人员到已选datagrid
function addPersonInSelectedBox(jsonString) {  
	var jsondata = JSON.parse(jsonString);
	//这里要改，现在是loaddata，是直接把原数据覆盖的，要改成添加新行
	$("#inSelectP").datagrid('loadData',jsondata);
	//$('#inSelectP').datagrid('appendRow',jsondata.rows);
	


}
