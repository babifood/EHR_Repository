//@ sourceURL=RewardAndPunishmentSelectPerson.js
/**
 * -----------------------------------------奖惩记录里保存提议人、奖惩人员-------------------------------------------
 */
//查找已选中人员by奖惩列表中的记录ID(添加为空；修改选记录ID)
function selectInSelectPerson(inputId){
	var dog_title=$('#RewardPunishment_dog').panel('options').title;
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
	//修改：选中所选奖惩记录，显示
	if(dog_title=="修改奖惩记录"){
	    //生成json
		var data;
		//提议人：一条奖惩记录中可以有说个提议人，但是点击出人员选框的时候，要分开显示人员（一条一条的）
		if(inputId=='rap_proposer'){
			//逗号分割
			var a=$("#rap_proposer_id").val();
			var b=$("#rap_proposer").val();
			var mid = a.split(",");
			var mname = b.split(",");
			var mlenght=mid.length;
			data ={total: mlenght,rows: []};
			for (var i = 0; i < mlenght; i++) {
				var hawbMapping = new Object();
            	hawbMapping.p_id = mid[i];
            	hawbMapping.p_name = mname[i];
            	data.rows.push(hawbMapping);
        	}	

		}
		//奖惩人员：一条奖惩记录中只会有一个奖惩人员，如果没有修改过，直接显示数据库的数据，如果修改过（未完全保存），显示input里显示的数据
		else if(inputId=='rap_p'){
			var hawbMapping = new Object();
			data={total: 1,rows: []};
			var a=$("#rap_p_id").val();
			var b=$("#rap_p").val();
			hawbMapping.p_id = a;
			hawbMapping.p_name = b;
			data.rows.push(hawbMapping);
		}
		//转换
	    var jsonString = JSON.stringify(data);
		var jsondata = JSON.parse(jsonString);
		//加载数据
		$("#inSelectP").datagrid('loadData',jsondata);
	}
	//添加：清空datagrid
	else{
		$('#inSelectP').datagrid('loadData', { total: 0, rows: [] });
		//如果之前已经有暂存数据，则显示暂存input的数据
		//提议人
		if(inputId=='rap_proposer'){
			var a=$("#rap_proposer_id").val();
			var b=$("#rap_proposer").val();
		}
		//奖惩人员
		else if(inputId=='rap_p'){
			var a=$("#rap_p_id").val();
			var b=$("#rap_p").val();
		}
		if(a!=null&&a.length!=0){
			var data;
			var mid = a.split(",");
			var mname = b.split(",");
			var mlenght=mid.length;
			data ={total: mlenght,rows: []};
			for (var i = 0; i < mlenght; i++) {
				var hawbMapping = new Object();
            	hawbMapping.p_id = mid[i];
            	hawbMapping.p_name = mname[i];
            	data.rows.push(hawbMapping);
        	}	
			//转换
			var jsonString = JSON.stringify(data);
			var jsondata = JSON.parse(jsonString);
			//加载数据
			$("#inSelectP").datagrid('loadData',jsondata);
		}
	}
}
//选择人员框暂时保存
function saveSelectPerson2(selectInputId){
	var datainSelectP = $('#inSelectP').datagrid('getData');
	var hawbMappingrap_proposerid = new Array();
	var hawbMappingrap_proposername = new Array();
	var proposer_id,proposer;
	var hawbMappingrap_pid = new Array();
	var hawbMappingrap_pname = new Array();
	var p_id,p;
	//提议人：人员选框中可以选择多人，显示出来显示“A,B,C”这种逗号分隔的，按显示的格式存数据库
	if(selectInputId=='rap_proposer'){
		for(var i=0;i<datainSelectP.total;i++)
		{
			hawbMappingrap_proposerid[i] = datainSelectP.rows[i].p_id;
			hawbMappingrap_proposername[i] = datainSelectP.rows[i].p_name;
		}
		proposer_id=hawbMappingrap_proposerid.join(",");
		proposer=hawbMappingrap_proposername.join(",");
		$("#"+selectInputId+"_id").val(proposer_id);
		$("#"+selectInputId).val(proposer);	
		$('#selectPerson_dog').dialog('close');
	}
	//奖惩人员：添加时，人员选框中可以选择多人，保存到添加记录界面显示“A,B,C”这种逗号分隔的，再保存，则根据人员数，生成多条记录；修改时，不允许选择多人。
	else if(selectInputId=='rap_p'){
		var dog_title=$('#RewardPunishment_dog').panel('options').title;
		if(dog_title=="添加奖惩记录"){
			for(var i=0;i<datainSelectP.total;i++)
			{
				hawbMappingrap_pid[i] = datainSelectP.rows[i].p_id;
				hawbMappingrap_pname[i] = datainSelectP.rows[i].p_name;
			}
			p_id=hawbMappingrap_pid.join(",");
			p=hawbMappingrap_pname.join(",");
			$("#"+selectInputId+"_id").val(p_id);
			$("#"+selectInputId).val(p);	
			$('#selectPerson_dog').dialog('close');
		}else if(dog_title=="修改奖惩记录"){
			//修改时，不允许选择多人
			if(datainSelectP.total>1){
				$.messager.alert("消息提示！","修改奖惩记录，不允许选择多奖惩人员！","warning");
			}else{
				$("#"+selectInputId+"_id").val(datainSelectP.rows[0].p_id);
				$("#"+selectInputId).val(datainSelectP.rows[0].p_name);	
				$('#selectPerson_dog').dialog('close');
			}
		}
	}
}