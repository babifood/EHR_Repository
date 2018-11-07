var education = 0;//教育
var editIndex = undefined;
var cultivate = 0;//培训
var editIndex_cf = undefined;
var editIndex_cl = undefined;
var work = 0;//工作
var editIndex_wf = undefined;
var editIndex_wl = undefined;
var certificate = 0;//证书
var editIndex_c = undefined;
var family = 0;//家庭
var editIndex_f = undefined;
//检验员工编号或创建员工编号
var checkOrCreate = "";
//新增和修改标识
var insertOrUpdate = "";
//人员编号
var p_number="";
//窗体状态
var win_title;
//初始化
$(function(){
	//加载人员档案列表
	loadPersonGrid("","");
	loadAccordion();
	loadPostComboBox();
	//初始加载公司
	insertLoadCompanyComboBox();
	updateLoadCompanyComboBox();
	//初始加载机构
	insertLoadOrganizationCombotree(null);
	updateLoadOrganizationCombotree(null);
	//初始加载部门
	insertLoadDepartmentCombotree(null);
	updateLoadDepartmentCombotree(null);
	//初始加载科室
	insertLoadSectionCombotree(null);
	updateLoadSectionCombotree(null);
	//初始加载班组
	insertLoadGroupCombotree(null);
	updateLoadGroupCombotree(null);
	//计算公司司龄（按月计算）
	inDateChange();
	//初始化OA员工信息
	loadOaWorkNumInFo();
});
//OA同步员工编号
function oaSyncWorkNum(){
	checkOrCreate="OA";
	document.getElementById("oa_div_woekName").style.display="inline";
    document.getElementById("ehr_div_woekName").style.display="none";
    document.getElementById("oa_div_woekName").style.cssText="margin-bottom:10px;";
	$("#check_workNum_dog").dialog("open").dialog("center").dialog("setTitle","校验员工编号");
	$("#check_workNum_dog_form").form('clear');
	
}
//加载OA员工编号信息
function loadOaWorkNumInFo(){
	var url=prefix+"/loadOaSyncWorkNum?workNum=&userName=";
	$('#check_dog_workName').combogrid({    
		idField: 'NAME',
		textField: 'NAME',
		url:url,
		method: 'get',
		columns: [[
			{field:'CODE',title:'工号',width:60},
			{field:'NAME',title:'姓名',width:60},
		]],
		fitColumns: true,
		onHidePanel:function(){
			var row = $('#check_dog_workName').combogrid('grid').datagrid('getSelected');	// 获取数据表格对象
			$("#check_dog_workNum").val(row.CODE);//编号
		}
	}); 
}
//校验OA的员工编号是否合法，并赋值给主页面的对应字段
function checkOAworkNem(){
	var zygfMin = 0;
	var zygfMax = 0;
	if(checkOrCreate=="OA"){
		zygfMin = 100001;
		zygfMax = 119999;
	}else if(checkOrCreate=="EHR"){
		zygfMin = 200000;
		zygfMax = 209999;
	}
	var company_id = $('#check_dog_company_id').val();
	var organization_id = $('#check_dog_organization_id').val();
	var woek_num = $('#check_dog_workNum').val();
	if(company_id==null||company_id==""||organization_id==null||organization_id==""||woek_num==null||woek_num==""){
		$.messager.alert('警告','请输入完整数据!','warning');
		return;
	}
	var row = $('#check_dog_workName').combogrid('grid').datagrid('getSelected');
	var checkWorkNum = false;
	if(company_id=="000000010002"&&woek_num>=zygfMin&&woek_num<=zygfMax){//中饮股份
		checkWorkNum = true;
	}else if(company_id=="000000010011"&&woek_num>=120000&&woek_num<=129999){//中饮管理
		checkWorkNum = true;
	}else if(company_id=="000000010012"&&organization_id!="0000000100120003"&&woek_num>=130000&&woek_num<=134999){//良星餐饮运营中心
		checkWorkNum = true;
	}else if(company_id=="000000010012"&&organization_id=="0000000100120003"&&woek_num>=135000&&woek_num<=139999){//良星餐饮生产中心
		checkWorkNum = true;
	}else if(company_id=="000000010004"&&woek_num>=140001&&woek_num<=149999){//北京中饮
		checkWorkNum = true;
	}else if(company_id=="000000010003"&&woek_num>=150001&&woek_num<=154999){//杭州中巴
		checkWorkNum = true;
	}else if(company_id=="000000010001"&&woek_num>=155001&&woek_num<=159999){//南京巴比
		checkWorkNum = true;
	}
	if(checkWorkNum){
		//查询数据库看号段有没有被占用
		$.post(prefix+"/getPersonByPnumber",{pNumber:woek_num},
		    function(data,status){
				if(data==null||data==""){
					if(checkOrCreate=="OA"){
						$("#p_id").val(row.member_id);//OA员工编号id
						$("#p_number").val(row.CODE);//编号
						$("#p_name").val(row.NAME);//名称
						$("#p_oa_and_ehr").val("OA");//区分那个系统创建的员工号段
					}else if(checkOrCreate=="EHR"){
						$("#p_number").val(woek_num);//编号
						$("#p_name").val($('#check_dog_EHR_workName').val());//名称
						$("#p_oa_and_ehr").val("EHR");//区分那个系统创建的员工号段
					}
					$("#insert_p_company_id").val($('#check_dog_company_id').val());
					$("#insert_p_company_name").val($("#check_dog_company").combobox("getText"));
					$("#insert_p_organization_id").val($('#check_dog_organization_id').val());
					$("#insert_p_organization").val($("#check_dog_organization").combotree("getText"));
					$("#insert_p_department_id").val($('#check_dog_department_id').val());
					$("#insert_p_department").val($("#check_dog_department").combotree("getText"));
					$("#insert_p_section_office_id").val($('#check_dog_section_office_id').val());
					$("#insert_p_section_office").val($("#check_dog_section_office").combotree("getText"));
					$("#insert_p_group_id").val($('#check_dog_group_id').val());
					$("#insert_p_group").val($("#check_dog_group").combotree("getText"));
					$('#check_workNum_dog').dialog('close');
				}else{
					$.messager.alert('警告','当前员工编号已被占用,请更改!','warning');   
				}
		    });
	}else{
		$.messager.alert('警告','员工编号不符合规则!\n提示:\n中饮股份从100001~119999\n中饮管理从120000~129999\n良星餐饮(营运)从130000~134999\n良星餐饮(生产)从135000~139999\n北京中饮从140001~149999\n杭州中巴从150001~154999\n南京巴比从155001~159999','warning');   
	}
}
//EHR自动发号
function ehrAotuWorkNum(){
	checkOrCreate="EHR";
	document.getElementById("oa_div_woekName").style.display="none";
	document.getElementById("ehr_div_woekName").style.display="inline";
	document.getElementById("ehr_div_woekName").style.cssText="margin-bottom:10px;";
	$("#check_workNum_dog").dialog("open").dialog("center").dialog("setTitle","创建员工编号");
	$("#check_workNum_dog_form").form('clear');
	notNullvalidate("check_dog_EHR_workName",true);
}
//EHR根据公司机构信息自动生成员工编号
function createWorkNum(){
	var company_id = $('#check_dog_company_id').val();
	var organization_id = $('#check_dog_organization_id').val();
	var woek_Name = $('#check_dog_EHR_workName').val();
	if(company_id==null||company_id==""||organization_id==null||organization_id==""||woek_Name==null||woek_Name==""){
		$.messager.alert('警告','请输入完整数据!','warning');
		return;
	}
	$.post(prefix+"/getEhrWorkNum",{companyId:company_id,organizationId:organization_id},
		    function(data,status){
				$('#check_dog_workNum').val(data);
		    });
}
//加载岗位combobox
function loadPostComboBox(){
	var url=prefix+"/loadComboboxPostData";
	$('#p_post').combogrid({    
		idField: 'post_name',
		textField: 'post_name',
		url:url,
		method: 'get',
		columns: [[
			{field:'post_name',title:'岗位名称',width:60},
			{field:'position_name',title:'职等名称',width:60},
			{field:'joblevel_name',title:'职级名称',width:60},
		]],
		fitColumns: true,
		onHidePanel:function(){
			var row = $('#p_post').combogrid('grid').datagrid('getSelected');	// 获取数据表格对象
			$("#p_post_id").val(row.post_id);//编号
			$("#p_title").val(row.position_name);
			$("#p_level_id").val(row.joblevel_id);
			$("#p_level_name").val(row.joblevel_name);
		}
	}); 
}
//新增加载公司combobox
function insertLoadCompanyComboBox(){
	$('#check_dog_company').combobox({    
	    url:prefix+'/loadComboboxCompanyData',    
	    valueField:'dept_code',    
	    textField:'dept_name',
	    onSelect: function(rec){
	    	$("#check_dog_company_id").val(rec.dept_code);
	    },
	    onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#check_dog_organization_id").val("");//单位机构编号
	    	$("#check_dog_organization").combotree("setValue","");//单位机构
	    	$("#check_dog_department_id").val("");//所属部门编号
	    	$("#check_dog_department").combotree("setValue","");//所属部门
	    	$("#check_dog_section_office_id").val("");//科室编号
	    	$("#check_dog_section_office").combotree("setValue","");//科室
	    	$("#check_dog_group_id").val("");//班组编号
	    	$("#check_dog_group").combotree("setValue","");//班组
	    	insertLoadOrganizationCombotree(newValue);
	    }
	});
}
//新增加载单位机构下拉树
function insertLoadOrganizationCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#check_dog_organization').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
	    	$("#check_dog_organization_id").val(rec.id);
	    },
	    onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#check_dog_department_id").val("");//所属部门编号
	    	$("#check_dog_department").combotree("setValue","");//所属部门
	    	$("#check_dog_section_office_id").val("");//科室编号
	    	$("#check_dog_section_office").combotree("setValue","");//科室
	    	$("#check_dog_group_id").val("");//班组编号
	    	$("#check_dog_group").combotree("setValue","");//班组
	    	insertLoadDepartmentCombotree(newValue);
	    }
	});  
}
//新增加载部门下拉树
function insertLoadDepartmentCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#check_dog_department').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
	    	$("#check_dog_department_id").val(rec.id);
	    },
	    onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#check_dog_section_office_id").val("");//科室编号
	    	$("#check_dog_section_office").combotree("setValue","");//科室
	    	$("#check_dog_group_id").val("");//班组编号
	    	$("#check_dog_group").combotree("setValue","");//班组
	    	insertLoadSectionCombotree(newValue);
	    }
	});  
}
//新增加载科室下拉树
function insertLoadSectionCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#check_dog_section_office').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
//	    	alert(rec);
//	    	console.log(rec);
	    	$("#check_dog_section_office_id").val(rec.id);
	    },onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#check_dog_group_id").val("");//班组编号
	    	$("#check_dog_group").combotree("setValue","");//班组
	    	insertLoadGroupCombotree(newValue);
	    }  
	});  
}
//新增加载班组下拉树
function insertLoadGroupCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#check_dog_group').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
//	    	alert(rec);
//	    	console.log(rec);
	    	$("#check_dog_group_id").val(rec.id);
	    }  
	});  
}
//修改时加载公司combobox
function updateLoadCompanyComboBox(){
	$('#update_p_company_name').combobox({    
	    url:prefix+'/loadComboboxCompanyData',    
	    valueField:'dept_code',    
	    textField:'dept_name',
	    onSelect: function(rec){
	    	$("#update_p_company_id").val(rec.dept_code);
	    },
	    onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#update_p_organization_id").val("");//单位机构编号
	    	$("#update_p_organization").combotree("setValue","");//单位机构
	    	$("#update_p_department_id").val("");//所属部门编号
	    	$("#update_p_department").combotree("setValue","");//所属部门
	    	$("#update_p_section_office_id").val("");//科室编号
	    	$("#update_p_section_office").combotree("setValue","");//科室
	    	$("#update_p_group_id").val("");//班组编号
	    	$("#update_p_group").combotree("setValue","");//班组
	    	updateLoadOrganizationCombotree(newValue);
	    }
	});
}
//修改加载单位机构下拉树
function updateLoadOrganizationCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#update_p_organization').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
	    	$("#update_p_organization_id").val(rec.id);
	    },
	    onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#update_p_department_id").val("");//所属部门编号
	    	$("#update_p_department").combotree("setValue","");//所属部门
	    	$("#update_p_section_office_id").val("");//科室编号
	    	$("#update_p_section_office").combotree("setValue","");//科室
	    	$("#update_p_group_id").val("");//班组编号
	    	$("#update_p_group").combotree("setValue","");//班组
	    	updateLoadDepartmentCombotree(newValue);
	    }
	});  
}
//修改加载部门下拉树
function updateLoadDepartmentCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#update_p_department').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
	    	$("#update_p_department_id").val(rec.id);
	    },
	    onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#update_p_section_office_id").val("");//科室编号
	    	$("#update_p_section_office").combotree("setValue","");//科室
	    	$("#update_p_group_id").val("");//班组编号
	    	$("#update_p_group").combotree("setValue","");//班组
	    	updateLoadSectionCombotree(newValue);
	    }
	});  
}
//修改时加载科室下拉树
function updateLoadSectionCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#update_p_section_office').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
//	    	alert(rec);
//	    	console.log(rec);
	    	$("#update_p_section_office_id").val(rec.id);
	    },onChange:function(newValue,oldValue){
//	    	console.log(newValue);
	    	$("#update_p_group_id").val("");//班组编号
	    	$("#update_p_group").combotree("setValue","");//班组
	    	updateLoadGroupCombotree(newValue);
	    }  
	});  
}
//修改加载班组下拉树
function updateLoadGroupCombotree(newValue){
	var url=prefix+"/loadCombotreeDeptData?id="+newValue;
	$('#update_p_group').combotree({    
	    url: url,
	    valueField:'id',    
	    textField:'text',
	    editable:false,
	    onSelect: function(rec){
//	    	alert(rec);
//	    	console.log(rec);
	    	$("#update_p_group_id").val(rec.id);
	    }  
	});  
}
////加载职级ComboGrid
//function loadLevelComboBox(){
//	$('#p_level_name').combobox({    
//		url:prefix+'/loadComboboxJobLevelData',    
//	    valueField:'id',    
//	    textField:'text',
//	    onSelect: function(rec){
//	    	$("#p_level_id").val(rec.id);
//	    } 
//	}); 
//}
//表单验证
function checkForm(tRf){
	var box =["p_title"]
	for(var i=0;i<box.length;i++){
		notNullvalidate(box[i],tRf);
	}
	idcardValidate("p_id_num",tRf);
	idcardValidate("p_urgency_id_nub",false);
	mobileValidate("p_phone",false);
	mobileValidate("p_urgency_phone",false);
}
//检查身份证格式
function checkIdNumFormat(){
	if($('#p_id_num').validatebox('isValid')){
		var idNum = $('#p_id_num').val();
		var year = idNum.substring(6,10);
		var month = idNum.substring(10,12);
		var day = idNum.substring(12,14);	
		var birthday = year+'-'+month+'-'+day;
		var age = jsGetAge(birthday);
		var sex = idNum.substring(16,17);
		$('#p_birthday').val(birthday);
		$('#p_age').val(age);
		if(sex%2==0){
			$('#p_sex').val("女");
		}else{
			$('#p_sex').val("男");
		}
	}
}
//表单提交验证
function checkFormData(){
	var check = true;
	var box =["p_title","p_id_num"]
	var uasyUIbox =["p_sex","p_age","p_state","p_property","p_post_property","p_in_date","p_turn_date","p_checking_in","p_marriage","p_politics","p_company_age","p_hukou_xingzhi"]
	for(var i=0;i<box.length;i++){
		if(!$('#'+box[i]).validatebox('isValid')){
			check = false;
			$('#'+box[i]).focus();
			break;
		}
	}
	for(var j=0;j<uasyUIbox.length;j++){
		if($('#'+uasyUIbox[j]).val()==""||$('#'+uasyUIbox[j]).val()==null){
			check = false;
			break;
		}
	}
	return check;
}
//非空验证
function notNullvalidate(id,trueOrfalse){
	$('#'+id).validatebox({
		required : trueOrfalse,
		missingMessage :'该输入项为必输项!',
	});
}
//验证身份证
function idcardValidate(id,required){
	$('#'+id).validatebox({
		required : required,
		validType :'idcard',
		missingMessage :'身份证不能为空!',
		invalidMessage :'身份证格式错误!',
	});
}
//验证电话号码格式
function mobileValidate(id,required){
	$('#'+id).validatebox({
		required : required,
		validType :'mobile',
		missingMessage :'电话号码不能为空!',
		invalidMessage :'电话号码格式错误!',
	});
}
//加载人员档案列表
function loadPersonGrid(searchKey,searchVal){
	var url;
	url=prefix+"/loadPersonInFo?searchKey="+searchKey+"&searchVal="+searchVal;
	$("#person_grid").datagrid({
		url:url,
		fit:true,
		fitColumns:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#person_tbar",
		singleSelect:true,
		rownumbers:true,
		frozenColumns:[[
			{
				field:"p_number",
				title:"工号",
			},
			{
				field:"p_name",
				title:"姓名",
			},
			{
				field:"p_company_name",
				title:"所属公司",
			},
			{
				field:"p_organization",
				title:"中心机构",
			},
			{
				field:"p_department",
				title:"所属部门",
			},
			{
				field:"p_section_office",
				title:"所属科室",
			},
			{
				field:"p_group",
				title:"所属班组",
			},
		]],
		columns:[[
			{
				field:"p_sex",
				title:"性别",
			},
			{
				field:"p_age",
				title:"年龄",
			},
			{
				field:"p_id_num",
				title:"身份证号",
			},
			{
				field:"p_post",
				title:"岗位名称",
			},
			{
				field:"p_level_name",
				title:"职级名称",
			},
			{
				field:"p_birthday",
				title:"员工出生年月日",
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
				field:"p_phone",
				title:"联系电话",
			},
			{
				field:"p_checking_in",
				title:"考勤方式",
				formatter:function(value){
					if(value=="0"){
						return "行政考勤";
					}else if(value=="1"){
						return "移动考勤";
					}else if(value=="2"){
						return "不考勤";
					}
				},
			},
			{
				field:"p_use_work_form",
				title:"用工形式",
				formatter:function(value){
					if(value=="0"){
						return "劳动合同制";
					}else if(value=="1"){
						return "劳务派遣制";
					}else if(value=="2"){
						return "其他";
					}
				},
			},
			{
				field:"p_nationality",
				title:"国籍",
				formatter:function(value){
					if(value=="1"){
						return "中国";
					}else if(value=="2"){
						return "其他";
					}
				},
			},
			{
				field:"p_huji",
				title:"户籍",
			},
			{
				field:"p_nation",
				title:"民族",
			},
			{
				field:"p_hukou_xingzhi",
				title:"户口性质",
				formatter:function(value){
					if(value=="0"){
						return "农业户口";
					}else if(value=="1"){
						return "城镇户口";
					}
				},
			},
			{
				field:"p_marriage",
				title:"婚姻状况",
				formatter:function(value){
					if(value=="0"){
						return "已婚";
					}else if(value=="1"){
						return "未婚";
					}
				},
			},
			{
				field:"p_politics",
				title:"政治面貌",
				formatter:function(value){
					if(value=="0"){
						return "党员";
					}else if(value=="1"){
						return "团员";
					}else if(value=="2"){
						return "群众";
					}else if(value=="3"){
						return "其他";
					}
				},
			},
			{
				field:"p_zuigao_xueli",
				title:"最高学历",
				formatter:function(value){
					if(value=="1"){
						return "初中";
					}else if(value=="2"){
						return "高中";
					}else if(value=="3"){
						return "中专";
					}else if(value=="4"){
						return "大专";
					}else if(value=="5"){
						return "本科";
					}else if(value=="6"){
						return "研究生";
					}else if(value=="7"){
						return "硕士";
					}else if(value=="8"){
						return "博士";
					}
				},
			},{
				field:"p_state",
				title:"员工状态",
				formatter:function(value){
					if(value=="0"){
						return "在职";
					}else if(value=="1"){
						return "离职";
					}
				},
			},
			{
				field:"p_property",
				title:"员工性质",
				formatter:function(value){
					if(value=="0"){
						return "全职";
					}else if(value=="1"){
						return "兼职";
					}
				},
			},
			{
				field:"p_post_property",
				title:"岗位性质",
				formatter:function(value){
					if(value=="0"){
						return "管理者";
					}else if(value=="1"){
						return "员工";
					}
				},
			}
		]],
		onDblClickRow:function(rowIndex, rowData){
			lookPersonInFo(rowData);
		},
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
//新增和修改是显示和隐藏公司机构部门科室班组信息
function onneOrInline(){
	if(insertOrUpdate=="insert"){
		document.getElementById("insert_companyAndOrg").style.display="inline";
		document.getElementById("insert_depaAndOffice").style.display="inline";
		document.getElementById("insert_group").style.display="inline";
		document.getElementById("update_companyAndOrg").style.display="none";//隐藏
		document.getElementById("update_depaAndOffice").style.display="none";
		document.getElementById("update_group").style.display="none";
		//设置显示控件的样式
		document.getElementById("insert_companyAndOrg").style.cssText="background: #fff;text-align: right;font-weight: 900;";
		document.getElementById("insert_depaAndOffice").style.cssText="background: #F5FAFA;text-align: right;font-weight: 900;";
		document.getElementById("insert_group").style.cssText="background: #fff;text-align: right;font-weight: 900;";
	}else if(insertOrUpdate=="update"){
		document.getElementById("insert_companyAndOrg").style.display="none";
		document.getElementById("insert_depaAndOffice").style.display="none";
		document.getElementById("insert_group").style.display="none";
		document.getElementById("update_companyAndOrg").style.display="inline";//显示
		document.getElementById("update_depaAndOffice").style.display="inline";
		document.getElementById("update_group").style.display="inline";
		//设置显示控件的样式
		document.getElementById("update_companyAndOrg").style.cssText="background: #fff;text-align: right;font-weight: 900;";
		document.getElementById("update_depaAndOffice").style.cssText="background: #F5FAFA;text-align: right;font-weight: 900;";
		document.getElementById("update_group").style.cssText="background: #fff;text-align: right;font-weight: 900;";
	}else if(insertOrUpdate=="look"){
		document.getElementById("insert_companyAndOrg").style.display="none";
		document.getElementById("insert_depaAndOffice").style.display="none";
		document.getElementById("insert_group").style.display="none";
		document.getElementById("update_companyAndOrg").style.display="inline";//显示
		document.getElementById("update_depaAndOffice").style.display="inline";
		document.getElementById("update_group").style.display="inline";
		//设置显示控件的样式
		document.getElementById("update_companyAndOrg").style.cssText="background: #fff;text-align: right;font-weight: 900;";
		document.getElementById("update_depaAndOffice").style.cssText="background: #F5FAFA;text-align: right;font-weight: 900;";
		document.getElementById("update_group").style.cssText="background: #fff;text-align: right;font-weight: 900;";
	}
	
}
//添加人员信息
function addPersonInFo(){
	insertOrUpdate="insert";
	onneOrInline();	
	$("#person_win").window("open").window("setTitle","添加人员");
	$("#person_form").form('clear');
	var index = $("#person_accordion").accordion('getPanelIndex',$("#person_accordion").accordion('getSelected'));
	if(index==0){
		//教育背景
		$('#education_grid').datagrid('loadData', { total: 0, rows: [] });
	}else if(index==1){
		//培训经历
		$('#cultivate_front_grid').datagrid('loadData', { total: 0, rows: [] });
		$('#cultivate_later_grid').datagrid('loadData', { total: 0, rows: [] });
	}else if(index==2){
		//工作经历
		$('#work_front_grid').datagrid('loadData', { total: 0, rows: [] });
		$('#work_later_grid').datagrid('loadData', { total: 0, rows: [] });
	}else if(index==3){
		//获得证书
		$('#certificate_grid').datagrid('loadData', { total: 0, rows: [] });
	}else if(index==4){
		//家庭背景
		$('#family_grid').datagrid('loadData', { total: 0, rows: [] });
	}
	$("#oaSync").linkbutton('enable');
	$("#ehrAot").linkbutton('enable');
	$("#save_linkbutton").linkbutton('enable');
	checkForm(true);
}
//修改人员信息
function editPersonInFo(){
	insertOrUpdate="update";
	var row = $("#person_grid").datagrid("getSelected");
	if(row){
		checkForm(false);
		$("#person_form").form('clear');
		p_number=row.p_number;
		$.post(prefix+"/getPersonFoPid",{p_number:row.p_number},function(data){
			if(data){
				onneOrInline();	
				$("#person_win").window("open").window("setTitle","修改人员");
				editFromSetValues(data);
				var index = $("#person_accordion").accordion('getPanelIndex',$("#person_accordion").accordion('getSelected'));
				if(index==0){
					//教育背景
					loadEducation();
				}else if(index==1){
					//培训经历
					loadCultivateFront();
					loadCultivateLater();
				}else if(index==2){
					//工作经历
					loadWorkFront();
					loadWorkLater();
				}else if(index==3){
					//获得证书
					loadCertificate();
				}else if(index==4){
					//家庭背景
					loadFamily();
				}
			}			
		  });
//		$("#p_number").attr("disabled","disabled");
//		$("#p_name").attr("disabled","disabled");
		$("#oaSync").linkbutton('disable');
		$("#ehrAot").linkbutton('disable');
		$("#save_linkbutton").linkbutton('enable');
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//查看员工信息
function lookPersonInFo(rowData){
	insertOrUpdate="look";
	checkForm(false);
	$("#person_form").form('clear');
	p_number=rowData.p_number;
	$.post(prefix+"/getPersonFoPid",{p_number:rowData.p_number},function(data){
		if(data){
			onneOrInline();	
			$("#person_win").window("open").window("setTitle","查看人员");
			editFromSetValues(data);
			var index = $("#person_accordion").accordion('getPanelIndex',$("#person_accordion").accordion('getSelected'));
			if(index==0){
				//教育背景
				loadEducation();
			}else if(index==1){
				//培训经历
				loadCultivateFront();
				loadCultivateLater();
			}else if(index==2){
				//工作经历
				loadWorkFront();
				loadWorkLater();
			}else if(index==3){
				//获得证书
				loadCertificate();
			}else if(index==4){
				//家庭背景
				loadFamily();
			}
		}			
	  });
	$("#oaSync").linkbutton('disable');
	$("#ehrAot").linkbutton('disable');
	$("#save_linkbutton").linkbutton('disable');
}
//编辑时给页面内容赋值
function editFromSetValues(data){
	$("#p_id").val(data.p_id);//人员ID
	$("#p_number").val(data.p_number);//编号
	$("#p_name").val(data.p_name);//名称
	$("#p_id_num").val(data.p_id_num);//身份证号码
	$("#p_birthday").val(data.p_birthday);//出生年月日
	$("#p_sex").val(data.p_sex);//性别
	$("#p_age").val(data.p_age);//年龄
	$("#p_title").val(data.p_title);//职称
	$("#p_post_id").val(data.p_post_id);//岗位编号
	$("#p_post").combogrid('setValue',data.p_post);//岗位名称
	$("#p_level_id").val(data.p_level_id);//职级编号
	$("#p_level_name").val(data.p_level_name);//职级名称
	
	$("#update_p_company_id").val(data.p_company_id);//公司编码
	console.log("company"+$("#update_p_company_id").val());
	$("#update_p_company_name").combobox('setValue',data.p_company_name);//公司名称
	$("#update_p_organization_id").val(data.p_organization_id);//单位机构编号
	$("#update_p_organization").combotree("setValue",data.p_organization);//单位机构
	$("#update_p_department_id").val(data.p_department_id);//所属部门编号
	$("#update_p_department").combotree("setValue",data.p_department);//所属部门
	$("#update_p_section_office_id").val(data.p_section_office_id);//科室编号
	$("#update_p_section_office").combotree("setValue",data.p_section_office);//科室
	$("#update_p_group_id").val(data.p_group_id);//班组编号
	$("#update_p_group").combotree("setValue",data.p_group);//班组
	
	$("#p_state").combobox('setValue',data.p_state);//员工状态
	$("#p_property").combobox('setValue',data.p_property);//员工性质
	$("#p_post_property").combobox('setValue',data.p_post_property);//岗位性质
	$("#p_in_date").datebox('setValue',data.p_in_date);//入职日期
	$("#p_turn_date").datebox('setValue',data.p_turn_date);//转正日期
	$("#p_out_date").datebox('setValue',data.p_out_date);//离职日期
	$("#p_out_describe").val(data.p_out_describe);//离职原因
	$("#p_checking_in").combobox('setValue',data.p_checking_in);//考勤方式
	$("#p_contract_begin_date").val(data.p_contract_begin_date);//劳动合同开始日期
	$("#p_contract_end_date").datebox('setValue',data.p_contract_end_date);//劳动合同结束日期
	$("#p_shebao_begin_month").datebox('setValue',data.p_shebao_begin_month);//社保购买起始月
	$("#p_shebao_end_month").datebox('setValue',data.p_shebao_end_month);//社保购买终止月
	$("#p_gjj_begin_month").datebox('setValue',data.p_gjj_begin_month);//公积金购买起始月
	$("#p_gjj_end_month").datebox('setValue',data.p_gjj_end_month);//公积金购买终止月
    $("#p_nationality").combobox('setValue',data.p_nationality);//国籍
	$("#p_nation").val(data.p_nation);//民族
	$("#p_marriage").combobox('setValue',data.p_marriage);//婚否
	$("#p_politics").combobox('setValue',data.p_politics);//政治面貌
	$("#p_phone").val(data.p_phone);//联系电话
	$("#p_bank_nub").val(data.p_bank_nub);//银行卡号
	$("#p_bank_name").val(data.p_bank_name);//开户行
	$("#p_huji_add").val(data.p_huji_add);//户籍地址
	$("#p_changzhu_add").val(data.p_changzhu_add);//常住联系地址
	$("#p_urgency_name").val(data.p_urgency_name);//紧急联系人名称
	$("#p_urgency_relation").combobox('setValue',data.p_urgency_relation);//紧急联系人关系
	$("#p_urgency_phone").val(data.p_urgency_phone);//紧急联系人电话
	$("#p_urgency_id_nub").val(data.p_urgency_id_nub);//紧急联系人身份证号码（改）
	$("#p_urgency_add").val(data.p_urgency_add);//紧急联系人地址（加）
	$("#p_use_work_form").combobox('setValue',data.p_use_work_form),//用工形式
	$("#p_contract_count").spinner('setValue',data.p_contract_count),//合同签订次数
	$("#p_oa_and_ehr").val(data.p_oa_and_ehr);//区分OA或者EHR创建的员工号段
	$("#p_huji").val(data.p_huji);//户籍
	$("#p_hukou_xingzhi").combobox('setValue',data.p_hukou_xingzhi);//户口性质
	$("#p_age_qujian").combobox('setValue',data.p_age_qujian);//年龄区间
	$("#p_zuigao_xueli").combobox('setValue',data.p_zuigao_xueli);//最高学历
	$("#p_recommend_person").val(data.p_recommend_person);//推荐人名称
	$("#p_recommend_relation").combobox('setValue',data.p_recommend_relation);//推荐人关系
	
	setFromCheckbox("p_c_yingpin_table",data.p_c_yingpin_table);//应聘申请表
	setFromCheckbox("p_c_interview_tab",data.p_c_interview_tab);//面谈记录表
	setFromCheckbox("p_c_id_copies",data.p_c_id_copies);//身份证复印件
	setFromCheckbox("p_c_xueli",data.p_c_xueli);//学历证书
	setFromCheckbox("p_c_xuewei",data.p_c_xuewei);//学位证书
	setFromCheckbox("p_c_bank_nub",data.p_c_bank_nub);//银行卡号
	setFromCheckbox("p_c_tijian_tab",data.p_c_tijian_tab);//体检表
	setFromCheckbox("p_c_health",data.p_c_health);//健康证
	setFromCheckbox("p_c_img",data.p_c_img);//照片
	setFromCheckbox("p_c_welcome",data.p_c_welcome);//欢迎词
	setFromCheckbox("p_c_staff",data.p_c_staff);//员工手册回执
	setFromCheckbox("p_c_admin",data.p_c_admin);//管理者回执
	setFromCheckbox("p_c_shebao",data.p_c_shebao);//社保同意书
	setFromCheckbox("p_c_shangbao",data.p_c_shangbao);//商保申请书
	setFromCheckbox("p_c_secrecy",data.p_c_secrecy);//保密协议
	setFromCheckbox("p_c_prohibida",data.p_c_prohibida);//禁业限制协议
	setFromCheckbox("p_c_contract",data.p_c_contract);//劳动合同
	setFromCheckbox("p_c_post",data.p_c_post);//岗位说明书
	setFromCheckbox("p_c_corruption",data.p_c_corruption);//反贪腐承诺书
	setFromCheckbox("p_c_probation",data.p_c_probation);//试用期考核表
}
//根据数据库查询到的值设置控件是否选中
function setFromCheckbox(id,val){
	if(val==1){
		//设置checkbox为选中状态  
		$("#"+id).prop("checked",true);  
	}else{
		//设置checkbox为不选中状态  
		$("#"+id).prop("checked",false);  
	}
}
//删除人员信息
function removePersonInFo(){
	var row = $("#person_grid").datagrid("getSelected");
	if(row){
		$.messager.confirm("提示","确定要删除此数据？",function(r){
			if(r){
				$.ajax({
					url:prefix+'/removePersonInFo',
					type:'post',
					data:{
						p_number:row.p_number
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
								msg:'人员删除成功！',
								timeout:3000,
								showType:'slide'
							});
							$("#person_grid").datagrid('reload');
						}else{
							$.messager.alert("消息提示！","数据删除失败！","warning");
						}
					}
				});
			}
		});
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//入职日期内容改变时计算司龄（司龄是的单位是按月计算）
function inDateChange(){
	$("#p_in_date").datebox({
		onChange:function(newValue, oldValue){
			var date = $("#p_in_date").val();
			if(date==""){
				return;
			}else{
				var startDate = new Date();
				var endDate = new Date(Date.parse(date));
			    var intervalMonth = (startDate.getFullYear()*12+startDate.getMonth()) - (endDate.getFullYear()*12+endDate.getMonth());
			    $("#p_company_age").val(intervalMonth<0?0:intervalMonth);//司龄
			    $("#p_contract_begin_date").val(date);
			}
		}
	})
}
//查询条件重置
function resetPersonInFo(){
	$("#search_p_number").val("");
	$("#search_p_name").val("");
}
//查询人员信息
function searchPersonInFo(value,name){
	if(value!=""){
		loadPersonGrid(name,value);
	}
};
//获取教育背景Grid数据转成数组
function getEducationGridToArr(){
	 var educationArr = [];//教育背景  
	 if(education==1){
		 var educationRows = $("#education_grid").datagrid("getRows");
		 $.each(educationRows,function(i,obj){  
			 educationArr.push({
				 	e_p_id:obj.e_p_id, 
				 	e_begin_date:obj.e_begin_date,
		    		e_end_date:obj.e_end_date,
		    		e_organization_name:obj.e_organization_name,
		    		e_specialty:obj.e_specialty,
		    		e_xueli:obj.e_xueli,
		    		e_xuewei:obj.e_xuewei,
		    		e_property:obj.e_property,
		    		e_desc:obj.e_desc,
		    	 });  
		 });
	 }
	 return educationArr; 
}
//培训经历_入职前Grid数据转成数组
function getCultivateFrontGridToArr(){
	 var cultivateFrontArr = [];//教育背景  
	 if(cultivate==1){
		 var cultivateFrontRows = $("#cultivate_front_grid").datagrid("getRows");
		 $.each(cultivateFrontRows,function(i,obj){  
			 cultivateFrontArr.push({
				 	c_p_id:obj.c_p_id,
				 	c_begin_date:obj.c_begin_date,
					c_end_date:obj.c_end_date,
					c_education:obj.c_education,
					c_training_content:obj.c_training_content,
					c_certificate:obj.c_certificate,
					c_desc:obj.c_desc,
		    	 });  
		 });
	 }
	 return cultivateFrontArr; 
}
//培训经历_入职后Grid数据转成数组
function getCultivateLaterGridToArr(){
	 var cultivateLaterArr = [];//教育背景  
	 if(cultivate==1){
		 var cultivateLaterRows = $("#cultivate_later_grid").datagrid("getRows");
		 $.each(cultivateLaterRows,function(i,obj){  
			 cultivateLaterArr.push({
				 	c_p_id:obj.c_p_id,
				 	c_begin_date:obj.c_begin_date,
				 	c_end_date:obj.c_end_date,
				 	c_training_institution:obj.c_training_institution,
				 	c_training_course:obj.c_training_course,
				 	c_training_add:obj.c_training_add,
				 	c_certificate:obj.c_certificate,
				 	c_training_type:obj.c_training_type,
				 	c_training_begin_date:obj.c_training_begin_date,
				 	c_training_end_date:obj.c_training_end_date,
				 	c_training_cost:obj.c_training_cost,
					c_desc:obj.c_desc,
		    	 });  
		 });
	 }
	 return cultivateLaterArr; 
}
//工作经历_入职前Grid数据转成数组
function getWorkFrontGridToArr(){
	 var workFrontArr = [];//教育背景  
	 if(work==1){
		 var workFrontRows = $("#work_front_grid").datagrid("getRows");
		 $.each(workFrontRows,function(i,obj){  
			 workFrontArr.push({
				 	w_p_id:obj.w_p_id,
				 	w_begin_date:obj.w_begin_date,
					w_end_date:obj.w_end_date,
					w_company_name:obj.w_company_name,
					w_type:obj.w_type,
					w_post_name:obj.w_post_name,
					w_prove:obj.w_prove,
					w_prove_post:obj.w_prove_post,
					w_prove_phone:obj.w_prove_phone,
					w_demission_desc:obj.w_demission_desc,
					w_desc:obj.w_desc,
		    	 });  
		 });
	 }
	 return workFrontArr; 
}
//工作经历_入职后Grid数据转成数组
function getWorkLaterGridToArr(){
	 var workLaterArr = [];//教育背景  
	 if(work==1){
		 var workLaterRows = $("#work_later_grid").datagrid("getRows");
		 $.each(workLaterRows,function(i,obj){  
			 workLaterArr.push({
				 	w_p_id:obj.w_p_id,
				 	w_begin_date:obj.w_begin_date,
					w_end_date:obj.w_end_date,
					w_company_name:obj.w_company_name,
					w_post_name:obj.w_post_name,
					w_desc:obj.w_desc,
		    	 });  
		 });
	 }
	 return workLaterArr; 
}
//获得证书Grid数据转成数组
function getCertificateGridToArr(){
	 var certificateArr = [];//教育背景  
	 if(certificate==1){
		 var certificateRows = $("#certificate_grid").datagrid("getRows");
		 $.each(certificateRows,function(i,obj){  
			 certificateArr.push({
				 	c_p_id:obj.c_p_id,
				 	c_name:obj.c_name,
					c_organization:obj.c_organization,
					c_begin_date:obj.c_begin_date,
					c_end_date:obj.c_end_date,
					c_desc:obj.c_desc,
		    	 });  
		 });
	 }
	 return certificateArr; 
}
//家庭背景Grid数据转成数组
function getFamilyGridToArr(){
	 var familyArr = [];//教育背景  
	 if(family==1){
		 var familyRows = $("#family_grid").datagrid("getRows");
		 $.each(familyRows,function(i,obj){  
			 familyArr.push({
				 	f_p_id:obj.f_p_id,
				 	f_relation:obj.f_relation,
					f_name:obj.f_name,
					f_date:obj.f_date,
					f_company:obj.f_company,
					f_duty:obj.f_duty,
					f_desc:obj.f_desc,
		    	 });  
		 });
	 }
	 return familyArr; 
}
//赋值
function setData(){
	var p_company_id ="",p_company_name="",p_department_id="",p_department="",p_organization_id="",p_organization="",p_section_office_id="",p_section_office="",p_group_id="",p_group="";
	if(insertOrUpdate == "insert"){
		p_company_id=$("#insert_p_company_id").val();//公司编码
		p_company_name=$("#insert_p_company_name").val();//公司名称
		p_department_id=$("#insert_p_department_id").val();//所属部门编号
		p_department=$("#insert_p_department").val();//所属部门
		p_organization_id=$("#insert_p_organization_id").val();//单位机构编码
		p_organization=$("#insert_p_organization").val();//单位机构
		p_section_office_id=$("#insert_p_section_office_id").val();//科室
		p_section_office=$("#insert_p_section_office").val();//科室
		p_group_id=$("#insert_p_group_id").val();//班组
		p_group=$("#insert_p_group_id").val();//班组
	}else if(insertOrUpdate == "update"){
		p_company_id=$("#update_p_company_id").val();//公司编码
		p_company_name=$("#update_p_company_name").combobox("getText");//公司名称
		p_department_id=$("#update_p_department_id").val();//所属部门编号
		p_department=$("#update_p_department").combotree("getText");//所属部门
		p_organization_id=$("#update_p_organization_id").val();//单位机构编码
		p_organization=$("#update_p_organization").combotree("getText");//单位机构
		p_section_office_id=$("#update_p_section_office_id").val();//科室
		p_section_office=$("#update_p_section_office").combotree("getText");//科室
		p_group_id=$("#update_p_group_id").val();//班组
		p_group=$("#update_p_group").combotree("getText");//班组
	}
	var data={
			p_id:$("#p_id").val(),//人员ID
			p_number:$("#p_number").val(),//编号
			p_name:$("#p_name").val(),//名称
			p_id_num:$("#p_id_num").val(),//身份证号
			p_birthday:$("#p_birthday").val(),//出生年月日
			p_name:$("#p_name").val(),//名称
			p_sex:$("#p_sex").val(),//性别
			p_age:$("#p_age").val(),//年龄
			p_title:$("#p_title").val(),//职称
			p_post_id:$("#p_post_id").val(),//岗位编号
			p_post:$('#p_post').combogrid('getText'),//岗位名称
			p_level_id:$("#p_level_id").val(),//职级编号
			p_level_name:$("#p_level_name").val(),//职级名称
			
			p_company_id:p_company_id,//公司编码
			p_company_name:p_company_name,//公司名称
			p_department_id:p_department_id,//所属部门编号
			p_department:p_department,//所属部门
			p_organization_id:p_organization_id,//单位机构编码
			p_organization:p_organization,//单位机构
			p_section_office_id:p_section_office_id,//科室
			p_section_office:p_section_office,//科室
			p_group_id:p_group_id,//班组
			p_group:p_group,//班组
			
			p_state:$("#p_state").val(),//员工状态
			p_property:$("#p_property").val(),//员工性质
			p_post_property:$("#p_post_property").val(),//岗位性质
			p_in_date:$("#p_in_date").val(),//入职日期
			p_turn_date:$("#p_turn_date").val(),//转正日期
			p_out_date:$("#p_out_date").val(),//离职日期
			p_out_describe:$("#p_out_describe").val(),//离职原因
			p_checking_in:$("#p_checking_in").val(),//考勤方式
			p_contract_begin_date:$("#p_contract_begin_date").val(),//劳动合同开始日期
			p_contract_end_date:$("#p_contract_end_date").val(),//劳动合同结束日期
			p_shebao_begin_month:$("#p_shebao_begin_month").val(),//社保购买起始月
			p_shebao_end_month:$("#p_shebao_end_month").val(),//社保购买终止月
			p_gjj_begin_month:$("#p_gjj_begin_month").val(),//公积金购买起始月
			p_gjj_end_month:$("#p_gjj_end_month").val(),//公积金购买终止月
		    p_nationality:$("#p_nationality").val(),//国籍
			p_nation:$("#p_nation").val(),//民族
			p_marriage:$("#p_marriage").val(),//婚否
			p_politics:$("#p_politics").val(),//政治面貌
			p_phone:$("#p_phone").val(),//联系电话
			p_bank_nub:$("#p_bank_nub").val(),//银行卡号
			p_bank_name:$("#p_bank_name").val(),//开户行
			p_huji_add:$("#p_huji_add").val(),//户籍地址
			p_changzhu_add:$("#p_changzhu_add").val(),//常住联系地址
			p_urgency_name:$("#p_urgency_name").val(),//紧急联系人名称
			p_urgency_relation:$("#p_urgency_relation").val(),//紧急联系人关系
			p_urgency_phone:$("#p_urgency_phone").val(),//紧急联系人电话
			p_urgency_id_nub:$("#p_urgency_id_nub").val(),//紧急联系人身份证号码
			p_urgency_add:$("#p_urgency_add").val(),//紧急联系人住址
			p_company_age:$("#p_company_age").val(),//司龄
			p_use_work_form:$("#p_use_work_form").val(),//用工形式
			p_contract_count:$("#p_contract_count").val(),//合同签订次数
			p_oa_and_ehr:$("#p_oa_and_ehr").val(),//区分OA或者EHR创建的员工号段
			
			p_huji:$("#p_huji").val(),//户籍
			p_hukou_xingzhi:$("#p_hukou_xingzhi").val(),//户口性质
			p_age_qujian:$("#p_age_qujian").val(),//年龄区间
			p_zuigao_xueli:$("#p_zuigao_xueli").val(),//最高学历
			p_recommend_person:$("#p_recommend_person").val(),//推荐人名称
			p_recommend_relation:$("#p_recommend_relation").val(),//推荐人关系
			
			p_c_yingpin_table:getCheckBoxValue("p_c_yingpin_table"),//应聘申请表
			p_c_interview_tab:getCheckBoxValue("p_c_interview_tab"),//面谈记录表
			p_c_id_copies:getCheckBoxValue("p_c_id_copies"),//身份证复印件
			p_c_xueli:getCheckBoxValue("p_c_xueli"),//学历证书
			p_c_xuewei:getCheckBoxValue("p_c_xuewei"),//学位证书
			p_c_bank_nub:getCheckBoxValue("p_c_bank_nub"),//银行卡号
			p_c_tijian_tab:getCheckBoxValue("p_c_tijian_tab"),//体检表
			p_c_health:getCheckBoxValue("p_c_health"),//健康证
			p_c_img:getCheckBoxValue("p_c_img"),//照片
			p_c_welcome:getCheckBoxValue("p_c_welcome"),//欢迎词
			p_c_staff:getCheckBoxValue("p_c_staff"),//员工手册回执
			p_c_admin:getCheckBoxValue("p_c_admin"),//管理者回执
			p_c_shebao:getCheckBoxValue("p_c_shebao"),//社保同意书
			p_c_shangbao:getCheckBoxValue("p_c_shangbao"),//商保申请书
			p_c_secrecy:getCheckBoxValue("p_c_secrecy"),//保密协议
			p_c_prohibida:getCheckBoxValue("p_c_prohibida"),//禁业限制协议
			p_c_contract:getCheckBoxValue("p_c_contract"),//劳动合同
			p_c_post:getCheckBoxValue("p_c_post"),//岗位说明书
			p_c_corruption:getCheckBoxValue("p_c_corruption"),//反贪腐承诺书
			p_c_probation:getCheckBoxValue("p_c_probation"),//试用期考核表
			
			//列表信息
			education:getEducationGridToArr(),//教育背景
			cultivateFront:getCultivateFrontGridToArr(),//培训经历_入职前
			cultivateLater:getCultivateLaterGridToArr(),//培训经历_入职后
			workFront:getWorkFrontGridToArr(),//工作经历_入职前
			workLater:getWorkLaterGridToArr(),//工作经历_入职后
			certificate:getCertificateGridToArr(),//获得证书
			family:getFamilyGridToArr(),//家庭背景
	}
	return data;
}
//判断复选框是否选中
function getCheckBoxValue(id){
	var checked_val=0;
	if($("#"+id).is(":checked")){
		checked_val=1;
	}
	return checked_val;
}

//保存角色
function savePersonInFo(){
	var data = setData();
	console.log(data);
	if(checkFormData()){
		$.ajax({
			url:prefix+'/savePersonInFo',
			type:'post',
			data:JSON.stringify(data),
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
					$('#person_win').window('close');
					$("#person_grid").datagrid('reload');
				}else{
					$.messager.alert("消息提示！","添加数据失败！","warning");
				}
			}
		});
	}
}
/**********************************window-function*********************************************/
//加载(教育背景、培训经历、工作经历、获得证书、家庭背景)列表
function loadAccordion(){
	$("#person_accordion").accordion({
		onSelect:function(title,index){
			if(title=="教育背景"){
				if(education==0){
					//加载教育背景列表
					loadEducation();
					education = 1;
				}
			}else if(title=="培训经历"){
				if(cultivate==0){
					loadCultivateFront();
					loadCultivateLater();
					cultivate = 1;
				}
			}else if(title=="工作经历"){
				if(work==0){
					loadWorkFront();
					loadWorkLater();
					work = 1;
				}
			}else if(title=="获得证书"){
				if(certificate==0){
					loadCertificate();
					certificate = 1;
				}
			}else if(title=="家庭背景"){
				if(family==0){
					loadFamily();
					family = 1;
				}
			}
		}
	});
}
/****************************************教育背景function******************************************************/
//加载教育背景列表
function loadEducation(){
	var url;
	if(p_number==""){
		url=prefix+'/loadEducation?e_p_id=';
	}else{
		url=prefix+'/loadEducation?e_p_id='+p_number;
	}
	$("#education_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#education_btn",
		singleSelect:true,
		columns:[[
			{
				field:"e_begin_date",
				title:"开始日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"e_end_date",
				title:"结束日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"e_organization_name",
				title:"机构名称",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"e_property",
				title:"性质",
				width:100,
				editor:{
					type:'combobox',
					options:{
						data:[{e_property:'0',e_property_name:'全日制'},{e_property:'1',e_property_name:'非日制'}],
						valueField:'e_property',
						textField:'e_property_name',
						editable: false,
						required:true
					},
				},
				formatter:function(value,row){
					if(value==0||row.e_property==0){
						return "全日制";
					}else{
						return "非日制";
					}
				},
			},
			{
				field:"e_specialty",
				title:"专业",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"e_xueli",
				title:"学历",
				width:100,
				editor:{
					type:'combobox',
					options:{
						data:[
							{e_xueli:'1',e_xueli_name:'初中'},
							{e_xueli:'2',e_xueli_name:'高中'},
							{e_xueli:'3',e_xueli_name:'中专'},
							{e_xueli:'4',e_xueli_name:'大专'},
							{e_xueli:'5',e_xueli_name:'本科'},
							{e_xueli:'6',e_xueli_name:'研究生'},
							{e_xueli:'7',e_xueli_name:'硕士'},
							{e_xueli:'8',e_xueli_name:'博士'}],
						valueField:'e_xueli',
						textField:'e_xueli_name',
						editable: false,
						required:true
					},
				},
				formatter:function(value,row){
					if(value==1||row.e_property==1){
						return "初中";
					}else if(value==2||row.e_property==2){
						return "高中";
					}else if(value==3||row.e_property==3){
						return "中专";
					}else if(value==4||row.e_property==4){
						return "大专";
					}else if(value==5||row.e_property==5){
						return "本科";
					}else if(value==6||row.e_property==6){
						return "研究生";
					}else if(value==7||row.e_property==7){
						return "硕士";
					}else if(value==8||row.e_property==8){
						return "博士";
					}
				},
			},
			{
				field:"e_xuewei",
				title:"学位",
				width:100,
				editor:{
					type:'combobox',
					options:{
						data:[
							{e_xuewei:'1',e_xuewei_name:'学士学位'},
							{e_xuewei:'2',e_xuewei_name:'硕士学位'},
							{e_xuewei:'3',e_xuewei_name:'博士学位'}],
						valueField:'e_xuewei',
						textField:'e_xuewei_name',
						editable: false,
						required:true
					},
				},
				formatter:function(value,row){
					if(value==1||row.e_xuewei==1){
						return "学士学位";
					}else if(value==2||row.e_xuewei==2){
						return "硕士学位";
					}else if(value==3||row.e_xuewei==3){
						return "博士学位";
					}
				},
			},
			{
				field:"e_desc",
				title:"备注",
				width:200,
				editor:{
					type:'text',
					options:{
						
					},
				},
			}
		]],
		onDblClickRow:onDblClickRowEducation,
	});
}
//结束编辑
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#education_grid').datagrid('validateRow', editIndex)){
		$('#education_grid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addEducation(){
	if (endEditing()){
		$('#education_grid').datagrid('appendRow',{});
		editIndex = $('#education_grid').datagrid('getRows').length-1;
		$('#education_grid').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
//删除
function removeEducation(){
	var index = $("#education_grid").datagrid("getRowIndex",$('#education_grid').datagrid('getSelected'));
	var node = $("#education_grid").datagrid("getChecked");
	if(index>=0){
		$('#education_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		
		editIndex = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}	
}
//确定
function acceptEducation(){
	if (endEditing()){
		$('#education_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectEducation(){
	$('#education_grid').datagrid('rejectChanges');
	editIndex = undefined;
}
//双击编辑
function onDblClickRowEducation(index){
	if (editIndex != index){
		if (endEditing()){
			$('#education_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#education_grid').datagrid('selectRow', editIndex);
		}
	}
}
/******************************************培训经历function*************************************************/
//加载培训经历入职前的列表
function loadCultivateFront(){
	var url;
	if(p_number==""){
		url=prefix+'/loadCultivateFront?c_p_id=';
	}else{
		url=prefix+'/loadCultivateFront?c_p_id='+p_number;
	}
	$("#cultivate_front_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#cultivate_front_btn",
		singleSelect:true,
		columns:[[
			{
				field:"c_begin_date",
				title:"开始日期",
				width:100,
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
				title:"结束日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"c_education",
				title:"教育机构",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_training_content",
				title:"培训内容",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_certificate",
				title:"获得证书",
				width:100,
				editor:{
					type:'validatebox',
					options:{
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
		onDblClickRow:onDblClickRowCultivateFront,
	});
}
//结束编辑
function endEditingCultivateFront(){
	if (editIndex_cf == undefined){
		return true
	}
	if ($('#cultivate_front_grid').datagrid('validateRow', editIndex_cf)){
		$('#cultivate_front_grid').datagrid('endEdit', editIndex_cf);
		editIndex_cf = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addCultivateFront(){
	if(endEditingCultivateFront()){
		$('#cultivate_front_grid').datagrid('appendRow',{});
		editIndex_cf = $('#cultivate_front_grid').datagrid('getRows').length-1;
		$('#cultivate_front_grid').datagrid('selectRow', editIndex_cf)
				.datagrid('beginEdit', editIndex_cf);
	}
	
}
//删除
function removeCultivateFront(){
	var index = $("#cultivate_front_grid").datagrid("getRowIndex",$('#cultivate_front_grid').datagrid('getSelected'));
	var node = $("#cultivate_front_grid").datagrid("getChecked");
	if(index>=0){
		$('#cultivate_front_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		editIndex_cf = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//确定
function acceptCultivateFront(){
	if(endEditingCultivateFront()){
		$('#cultivate_front_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectCultivateFront(){
	$('#cultivate_front_grid').datagrid('rejectChanges');
	editIndex_cf = undefined;
}
//双击编辑
function onDblClickRowCultivateFront(index){
	if (editIndex_cf != index){
		if (endEditingCultivateFront()){
			$('#cultivate_front_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex_cf = index;
		} else {
			$('#cultivate_front_grid').datagrid('selectRow', editIndex_cf);
		}
	}
}
/***************培训经历入职后function*****************/
//加载培训经历入职后的列表
function loadCultivateLater(){
	var url;
	if(p_number==""){
		url=prefix+'/loadCultivateLater?c_p_id=';
	}else{
		url=prefix+'/loadCultivateLater?c_p_id='+p_number;
	}
	$("#cultivate_later_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#cultivate_later_btn",
		singleSelect:true,
		columns:[[
			{
				field:"c_begin_date",
				title:"开始日期",
				width:100,
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
				title:"结束日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"c_training_institution",
				title:"培训机构",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_training_course",
				title:"培训课程",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_training_add",
				title:"培训地点",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_certificate",
				title:"获得证书",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_training_type",
				title:"培训类别",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_training_begin_date",
				title:"培训协议开始日期",
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
				field:"c_training_end_date",
				title:"培训协议截止日期",
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
				field:"c_training_cost",
				title:"培训费用",
				width:100,
				editor:{
					type:'numberbox',
					options:{
						
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
		onDblClickRow:onDblClickRowCultivateLater,
	});
}
//结束编辑
function endEditingCultivateLater(){
	if (editIndex_cl == undefined){return true}
	if ($('#cultivate_later_grid').datagrid('validateRow', editIndex_cl)){
		$('#cultivate_later_grid').datagrid('endEdit', editIndex_cl);
		editIndex_cl = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addCultivateLater(){
	if (endEditingCultivateLater()){
		$('#cultivate_later_grid').datagrid('appendRow',{});
		editIndex_cl = $('#cultivate_later_grid').datagrid('getRows').length-1;
		$('#cultivate_later_grid').datagrid('selectRow', editIndex_cl)
				.datagrid('beginEdit', editIndex_cl);
	}
}
//删除
function removeCultivateLater(){
	var index = $("#cultivate_later_grid").datagrid("getRowIndex",$('#cultivate_later_grid').datagrid('getSelected'));
	var node = $("#cultivate_later_grid").datagrid("getChecked");
	if(index>=0){
		$('#cultivate_later_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		editIndex_cl = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//确定
function acceptCultivateLater(){
	if (endEditingCultivateLater()){
		$('#cultivate_later_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectCultivateLater(){
	$('#cultivate_later_grid').datagrid('rejectChanges');
	editIndex_cl = undefined;
}
//双击编辑
function onDblClickRowCultivateLater(index){
	if (editIndex_cl != index){
		if (endEditingCultivateLater()){
			$('#cultivate_later_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex_cl = index;
		} else {
			$('#cultivate_later_grid').datagrid('selectRow', editIndex_cl);
		}
	}
}
/*****************************************工作经历function**************************************************/
//加载工作经历入职前的列表
function loadWorkFront(){
	var url;
	if(p_number==""){
		url=prefix+'/loadWorkFront?w_p_id=';
	}else{
		url=prefix+'/loadWorkFront?w_p_id='+p_number;
	}
	$("#work_front_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#work_front_btn",
		singleSelect:true,
		columns:[[
			{
				field:"w_begin_date",
				title:"开始日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"w_end_date",
				title:"结束日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"w_company_name",
				title:"单位名称",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_type",
				title:"所属行业",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_post_name",
				title:"任职岗位",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_prove",
				title:"证明人",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_prove_post",
				title:"证明人岗位",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_prove_phone",
				title:"证明人联系电话",
				width:120,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_demission_desc",
				title:"离职原因",
				width:200,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_desc",
				title:"备注",
				width:200,
				editor:{
					type:'text',
					options:{
						
					},
				},
			}
		]],
		onDblClickRow:onDblClickRowWorkFront
	});
}
//结束编辑
function endEditingWorkFront(){
	if (editIndex_wf == undefined){return true}
	if ($('#work_front_grid').datagrid('validateRow', editIndex_wf)){
		$('#work_front_grid').datagrid('endEdit', editIndex_wf);
		editIndex_wf = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addWorkFront(){
	if(endEditingWorkFront()){
		$('#work_front_grid').datagrid('appendRow',{});
		editIndex_wf = $('#work_front_grid').datagrid('getRows').length-1;
		$('#work_front_grid').datagrid('selectRow', editIndex_wf)
				.datagrid('beginEdit', editIndex_wf);
	}
}
//删除
function removeWorkFront(){
	var index = $("#work_front_grid").datagrid("getRowIndex",$('#work_front_grid').datagrid('getSelected'));
	var node = $("#work_front_grid").datagrid("getChecked");
	if(index>=0){
		$('#work_front_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		editIndex_wf = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//确定
function acceptWorkFront(){
	if(endEditingWorkFront()){
		$('#work_front_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectWorkFront(){
	$('#work_front_grid').datagrid('rejectChanges');
	editIndex_wf = undefined;
}
//双击编辑
function onDblClickRowWorkFront(index){
	if (editIndex_wf != index){
		if (endEditingWorkFront()){
			$('#work_front_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex_wf = index;
		} else {
			$('#work_front_grid').datagrid('selectRow', editIndex_wf);
		}
	}
}
/***************************工作经历入职后function*******************************/
//加载工作经历入职后的列表
function loadWorkLater(){
	var url;
	if(p_number==""){
		url=prefix+'/loadWorkLater?w_p_id=';
	}else{
		url=prefix+'/loadWorkLater?w_p_id='+p_number;
	}
	$("#work_later_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#work_later_btn",
		singleSelect:true,
		columns:[[
			{
				field:"w_begin_date",
				title:"开始日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"w_end_date",
				title:"结束日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"w_company_name",
				title:"单位名称",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_post_name",
				title:"任职岗位",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"w_desc",
				title:"备注",
				width:200,
				editor:{
					type:'text',
					options:{
						
					},
				},
			}
		]],
		onDblClickRow:onDblClickRowWorkLater
	});
}
//结束编辑
function endEditingWorkLater(){
	if (editIndex_wl == undefined){return true}
	if ($('#work_later_grid').datagrid('validateRow', editIndex_wl)){
		$('#work_later_grid').datagrid('endEdit', editIndex_wl);
		editIndex_wl = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addWorkLater(){
	if(endEditingWorkLater()){
		$('#work_later_grid').datagrid('appendRow',{});
		editIndex_wl = $('#work_later_grid').datagrid('getRows').length-1;
		$('#work_later_grid').datagrid('selectRow', editIndex_wl)
				.datagrid('beginEdit', editIndex_wl);
	}
}
//删除
function removeWorkLater(){
	var index = $("#work_later_grid").datagrid("getRowIndex",$('#work_later_grid').datagrid('getSelected'));
	var node = $("#work_later_grid").datagrid("getChecked");
	if(index>=0){
		$('#work_later_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		editIndex_wl = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//确定
function acceptWorkLater(){
	if(endEditingWorkLater()){
		$('#work_later_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectWorkLater(){
	$('#work_later_grid').datagrid('rejectChanges');
	editIndex_wl = undefined;
}
//双击编辑
function onDblClickRowWorkLater(index){
	if (editIndex_wl != index){
		if (endEditingWorkLater()){
			$('#work_later_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex_wl = index;
		} else {
			$('#work_later_grid').datagrid('selectRow', editIndex_wl);
		}
	}
}
/***************************************获得证书function***********************************************/
//加载获得证书的列表
function loadCertificate(){
	var url;
	if(p_number==""){
		url=prefix+'/loadCertificate?c_p_id=';
	}else{
		url=prefix+'/loadCertificate?c_p_id='+p_number;
	}
	$("#certificate_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#certificate_btn",
		singleSelect:true,
		columns:[[
			{
				field:"c_name",
				title:"证书名称",
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
				title:"发证机构",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"c_begin_date",
				title:"发证日期",
				width:100,
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
				width:100,
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
		onDblClickRow:onDblClickRowCertificate
	});
}
//结束编辑
function endEditingCertificate(){
	if (editIndex_c == undefined){return true}
	if ($('#certificate_grid').datagrid('validateRow', editIndex_c)){
		$('#certificate_grid').datagrid('endEdit', editIndex_c);
		editIndex_c = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addCertificate(){
	if(endEditingCertificate()){
		$('#certificate_grid').datagrid('appendRow',{});
		editIndex_c = $('#certificate_grid').datagrid('getRows').length-1;
		$('#certificate_grid').datagrid('selectRow', editIndex_c)
				.datagrid('beginEdit', editIndex_c);
	}
}
//删除
function removeCertificate(){
	var index = $("#certificate_grid").datagrid("getRowIndex",$('#certificate_grid').datagrid('getSelected'));
	var node = $("#certificate_grid").datagrid("getChecked");
	if(index>=0){
		$('#certificate_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		editIndex_c = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//确定
function acceptCertificate(){
	if(endEditingCertificate()){
		$('#certificate_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectCertificate(){
	$('#certificate_grid').datagrid('rejectChanges');
	editIndex_c = undefined;
}
//双击编辑
function onDblClickRowCertificate(index){
	if (editIndex_c != index){
		if (endEditingCertificate()){
			$('#certificate_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex_c = index;
		} else {
			$('#certificate_grid').datagrid('selectRow', editIndex_c);
		}
	}
}
/***************************************家庭背景function**********************************************/
//加载家庭背景的列表
function loadFamily(){
	var url;
	if(p_number==""){
		url=prefix+'/loadFamily?f_p_id=';
	}else{
		url=prefix+'/loadFamily?f_p_id='+p_number;
	}
	$("#family_grid").datagrid({
		url:url,
		fit:true,
		striped:true,
		border:false,
		pagination:true,
		pageSize:10,
		pageList:[10,20,30],
		pageNumber:1,
		toolbar:"#family_btn",
		singleSelect:true,
		columns:[[
			{
				field:"f_relation",
				title:"与员工关系",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"f_name",
				title:"姓名",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"f_date",
				title:"出生日期",
				width:100,
				editor:{
					type:'datebox',
					options:{
						editable: false,
						required:true
					},
				},
			},
			{
				field:"f_company",
				title:"工作单位",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"f_duty",
				title:"职务",
				width:100,
				editor:{
					type:'validatebox',
					options:{
						required:true
					},
				},
			},
			{
				field:"f_desc",
				title:"备注",
				width:200,
				editor:{
					type:'text',
					options:{
						
					},
				},
			}
		]],
		onDblClickRow:onDblClickRowFamily
	});
}
//结束编辑
function endEditingFamily(){
	if (editIndex_f == undefined){return true}
	if ($('#family_grid').datagrid('validateRow', editIndex_f)){
		$('#family_grid').datagrid('endEdit', editIndex_f);
		editIndex_f = undefined;
		return true;
	} else {
		return false;
	}
}
//添加
function addFamily(){
	if(endEditingFamily()){
		$('#family_grid').datagrid('appendRow',{});
		editIndex_f = $('#family_grid').datagrid('getRows').length-1;
		$('#family_grid').datagrid('selectRow', editIndex_f)
				.datagrid('beginEdit', editIndex_f);
	}	
}
//删除
function removeFamily(){
	var index = $("#family_grid").datagrid("getRowIndex",$('#family_grid').datagrid('getSelected'));
	var node = $("#certificate_grid").datagrid("getChecked");
	if(index>=0){
		$('#family_grid').datagrid('cancelEdit', index)
		.datagrid('deleteRow', index).datagrid('clearSelections',node).datagrid('acceptChanges');
		editIndex_f = undefined;
	}else{
		$.messager.alert("消息提示！","请选择一条数据！","info");
	}
}
//确定
function acceptFamily(){
	if(endEditingFamily()){
		$('#family_grid').datagrid('acceptChanges');
	}
}
//取消
function rejectFamily(){
	$('#family_grid').datagrid('rejectChanges');
	editIndex_f = undefined;
}
//双击编辑
function onDblClickRowFamily(index){
	if (editIndex_f != index){
		if (endEditingFamily()){
			$('#family_grid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex_f = index;
		} else {
			$('#family_grid').datagrid('selectRow', editIndex_f);
		}
	}
}