//@ sourceURL=personSelectionWindow.js
var selectInputId;
function selectPerson(inputId){
	selectInputId=inputId;
	//选择人员窗口
	$('#selectPerson_dog').dialog({
	    title: '选择人员',
	    width: 600,
	    height: 500,
	    closed: false,
	    cache: false,
	    modal: true
	});
	var unSelectDurl;
	unSelectDurl=prefix+'/personSelectionWindowDept';
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
	//生成空的待选人员datagrid
	$('#unSelectP').datagrid({
	    url:null, 
	    columns:[[
			{
				field:"p_name",
				title:"已选员工姓名",
				width:193,
			},
		]],
		//双击人员到右边选中datagrid
		onDblClickRow:function(rowIndex,rowData){ 
						//如果没有重复，则新增行
						if(checkRepetitionOnDblClick(rowIndex)){
							$('#inSelectP').datagrid("appendRow", {  
								p_id: rowData.p_id,  
								p_name: rowData.p_name
							});
						}		
			}
	});
	//每次打开，先清空待选框数据
	$('#unSelectP').datagrid('loadData', { total: 0, rows: [] });
	//清空查询框数据
	$("#serch_unselect").textbox('setValue','')
	$("#serch_inselect").textbox('setValue','')
	//显示已选人员，根据每个不同的使用场景更换前面jsp里链接的js
	selectInSelectPerson(inputId);
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
		var node = toDo.shift();// the parent node
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
/**
 * -----------------------------------------待选框的function-------------------------------------------
 * @returns
 */
//待选人员查询按钮
function searchunselect(){
	var p_name=$("#serch_unselect").val();
	selectUnSelectPersonByPersonName(p_name);
}
//查找待选人员by部门id(点击部门，直接显示部门下属人员）
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
//查找待选人员by人员姓名（在搜索框里输入模糊查询,如果不输入则查询所有人员）
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
}
//点击“>>”选中人员到右边框里
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
  //数据到右边之后，清除选择行（不是清除数据，只是变成不选中状态）
  $('#unSelectP').datagrid('clearSelections'); 
}
//添加人员到已选框
function addPersonInSelectedBox(jsonString) {  
	var jsondata = JSON.parse(jsonString);
	var rowdata=jsondata.rows;
  for (var m = 0; m < jsondata.total; m++) { 
  	//如果没有重复，则新增行
  	if(checkRepetition(m)){
  		$('#inSelectP').datagrid("appendRow", {  
              p_id: rowdata[m].p_id,  
              p_name: rowdata[m].p_name  
          });
  	}         
  }
}
//双击时，检查待选框中，选中的人员在右边已选框中是否已经存在，已经存在则不再添加
function checkRepetitionOnDblClick(m){
	var data1 = $('#unSelectP').datagrid('getData');
	var data2 = $('#inSelectP').datagrid('getData');
	var p_id1=data1.rows[m].p_id;
	for(var i=0;i<data2.total;i++)
	{
		var p_id2=data2.rows[i].p_id;
		if(p_id1==p_id2){
			//重复，返回false
			return false;
		}
	}
	return true;
}
//点“>>”按钮时，检查待选框中，选中的人员在右边已选框中是否已经存在，已经存在则不再添加
function checkRepetition(m){
	var data1 = $('#unSelectP').datagrid('getSelections');
	var data2 = $('#inSelectP').datagrid('getData');
	var p_id1=data1[m].p_id;
	for(var i=0;i<data2.total;i++)
	{
		var p_id2=data2.rows[i].p_id;
		if(p_id1==p_id2){
			//重复，返回false
			return false;
		}
	}
	return true;
}
/**
 * -----------------------------------------已选框的function-------------------------------------------
 * @returns
 */
//已选人员查询按钮
function searchinselect(){
	var p_name=$("#serch_inselect").val();
	selectInSelectPersonByPersonName(p_name);
}
//在已选人员框里标黄(选中)查询的人员
function selectInSelectPersonByPersonName(p_name){
	if(p_name!=null&&p_name.length!=0){
		var data = $('#inSelectP').datagrid('getData');
		var length=data.total;
		for(var i=0;i<length;i++)
		{
			var p=data.rows[i].p_name;
			if(p.indexOf(p_name)>=0){
				var id=data.rows[i].p_id;
				var rowIndex=$('#inSelectP').datagrid('getRowIndex',data.rows[i]);
				$('#inSelectP').datagrid('unselectAll');
				$('#inSelectP').datagrid('selectRow',rowIndex);
			}
		}
	}
	//清空选中
	else{
		$('#inSelectP').datagrid('clearSelections'); 
	}
}
//点击“<<”按钮取消右边的人员选中
function $subtract() {
	var row = $('#inSelectP').datagrid('getSelections');  
    // 获取选中行的Index的值  
	for(var i=0;i<row.length;i++)
	{
		var rowIndex=$('#inSelectP').datagrid('getRowIndex',row[i]);  
		subtractPersonInSelectedBox(rowIndex);
	}
}
//取消已选框的数据
function subtractPersonInSelectedBox(rowIndex) {  
	$('#inSelectP').datagrid('deleteRow', rowIndex);
}
/**
 * -----------------------------------------选择人员框暂时保存-需要根据不同的使用场景，自己更换-------------------------------------------
 * @returns
 */
//选择人员框暂时保存，调RewardAndPunishmentSelectPerson
function saveSelectPerson1(){
	saveSelectPerson2(selectInputId);
}