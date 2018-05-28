//@ sourceURL=RewardAndPunishmentSelectPerson.js
//已选中人员查询按钮
function searchinselect(){
	var p_name=$("#serch_inselect").val();
	//selectInSelectPersonByPersonName(p_name);
}
//查找已选中人员by奖惩列表中的记录ID(添加为空，修改选记录ID)
function selectInSelectPerson(){
	var dog_title=$('#RewardPunishment_dog').panel('options').title;
	//修改：选中所选奖惩记录，显示
	if(dog_title=="修改奖惩记录"){
		var row = $("#RewardPunishment_tbo").datagrid("getSelected");
	    //生成json
		var data ={total: '1',rows: []};	
		var hawbMapping = new Object();
		//这里要改--按点击的不同“人员”地方，取不同的值	
		hawbMapping.p_id = row.rap_proposer;
		hawbMapping.p_name = row.rap_proposer_name;
		
		data.rows.push(hawbMapping);
		//转换
	    var jsonString = JSON.stringify(data);
		var jsondata = JSON.parse(jsonString);
		//加载数据
		$("#inSelectP").datagrid('loadData',jsondata);
	}
	//添加：清空datagrid
	else{
		$('#inSelectP').datagrid('loadData', { total: 0, rows: [] });
	}
	//生成已选人员datagrid
	$('#inSelectP').datagrid({
	    url:null, 
	    columns:[[
			{
				field:"p_name",
				title:"已选员工姓名",
				width:193,
			},
		]],
		//双击取消人员选中
		onDblClickRow:function(rowIndex,rowData){ 
				$('#inSelectP').datagrid('deleteRow', rowIndex);
            }
	});	
}