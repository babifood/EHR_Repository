<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/PersonGrid.css"/>
<title>人事档案</title>
</head>
<body>
	<div ip="person_panel" class="easyui-panel" data-options="fit:true,border:false,noheader:true">
		<table id="person_grid"></table>
		<div id="person_tbar">
			<div style="margin-bottom: 5px;">
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPersonInFo()">添加</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPersonInFo()">修改</a>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removePersonInFo()">删除</a>
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	工号：<input type="text" class="textbox" id="search_p_number" name="search_p_number" style="width: 110px;"/>
	        	姓名：<input type="text" class="textbox" id="search_p_name" name="search_p_name" style="width: 110px;"/>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchPersonInFo()">查询</a>
	        </div>
		</div>
	</div>
	<div id="person_win" class="easyui-window" closed="true" 
        data-options="iconCls:'icon-save',modal:true,fit:true,collapsible:false,minimizable:false,maximizable:false">   
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',split:true" style="width:60%">
				<form id="person_form" action="#">
					<table id="person_tab" style="width: 100%;">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="20%">
							<col width="30%">
						</colgroup>
						<thead>
							<tr style="text-align: center;">
								<td colspan="4"><h1>人员档案</h1></td>
							</tr>
						</thead>
						<tbody>
							<tr class="text_tr">
								<td>工号:</td>
								<td>
									<input type="hidden" id="p_id" name="p_id"/>
									<input type="text" id="p_number" name="p_number" class="textbox"/>
								</td>
								<td>姓名:</td>
								<td>
									<input type="text" id="p_name" name="p_name" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>性别:</td>
								<td>
									<select id="p_sex" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">男</option>
										<option value="1">女</option>
									</select>
								</td>
								<td>年龄:</td>
								<td>
									<input type="text" id="p_age" name="p_age" class="easyui-numberspinner" style="width:236px"
									data-options="min:18,max:100" required="required"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>职称:</td>
								<td>
									<input type="text" id="p_title" name="p_title" class="textbox"/>
								</td>
								<td>岗位名称:</td>
								<td>
									<input type="hidden" id="p_post_id" name="p_post_id"/>
									<input type="text" id="p_post" name="p_post" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>职级名称:</td>
								<td>
									<input type="hidden" id="p_level_id" name="p_level_id"/>
									<input type="text" id="p_level_name" name="p_level_name" class="textbox"/>
								</td>
								<td>公司名称:</td>
								<td>
									<input type="hidden" id="p_company_id" name="p_company_id"/>
									<input type="text" id="p_company_name" name="p_company_name" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>单位机构:</td>
								<td>
									<input type="hidden" id="p_organization_id" name="p_organization_id"/>
									<input type="text" id="p_organization" name="p_organization" class="textbox"/>
								</td>
								<td>所属部门:</td>
								<td>
									<input type="hidden" id="p_department_id" name="p_department_id"/>
									<input type="text" id="p_department" name="p_department" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>科室:</td>
								<td>
									<input type="hidden" id="p_section_office_id" name="p_section_office_id"/>
									<input type="text" id="p_section_office" name="p_section_office" class="textbox"/>
								</td>
								<td>员工状态:</td>
								<td>
									<select id="p_state" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">在职</option>
										<option value="1">离职</option>
									</select>
								</td>
							</tr>
							<tr class="text_tr">
								<td>员工性质:</td>
								<td>
									<select id="p_property" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">全职</option>
										<option value="1">兼职</option>
									</select> 
								</td>
								<td>岗位性质:</td>
								<td>
									<select id="p_post_property" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">管理者</option>
										<option value="1">员工</option>
									</select> 
								</td>
							</tr>
							<tr class="text_tr">
								<td>入职日期:</td>
								<td>
									<input type="text" id="p_in_date" name="p_in_date" class="easyui-datebox" style="width:236px"
									 	data-options="editable:false" required="required"/>
								</td>
								<td>转正日期:</td>
								<td>
									<input type="text" id="p_turn_date" name="p_turn_date" class="easyui-datebox" style="width:236px"
									 	data-options="editable:false" required="required"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>离职日期:</td>
								<td>
									<input type="text" id="p_out_date" name="p_out_date" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
								<td>考勤方式:</td>
								<td>
									<select id="p_checking_in" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">行政考勤</option>
										<option value="1">移动考勤</option>
										<option value="2">不考勤</option>
									</select> 
								</td>
							</tr>
							<tr class="text_tr">
								<td>离职原因:</td>
								<td colspan="3">
									<input type="text" id="p_out_describe" name="p_out_describe" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>劳动合同开始日期:</td>
								<td>
									<input type="text" id="p_contract_begin_date" name="p_contract_begin_date" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
								<td>劳动合同结束日期:</td>
								<td>
									<input type="text" id="p_contract_end_date" name="p_contract_end_date" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>社保购买起始日期:</td>
								<td>
									<input type="text" id="p_shebao_begin_month" name="p_shebao_begin_month" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
								<td>社保购买终止日期:</td>
								<td>
									<input type="text" id="p_shebao_end_month" name="p_shebao_end_month" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>公积金购买起始日期:</td>
								<td>
									<input type="text" id="p_gjj_begin_month" name="p_gjj_begin_month" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
								<td>公积金购买终止日期:</td>
								<td>
									<input type="text" id="p_gjj_end_month" name="p_gjj_end_month" class="easyui-datebox" style="width:236px"
										data-options="editable:false"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>国籍:</td>
								<td>
									<input type="text" id="p_nationality" name="p_nationality" class="textbox"/>
								</td>
								<td>民族:</td>
								<td>
									<input type="text" id="p_nation" name="p_nation" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>婚否:</td>
								<td>
									<select id="p_marriage" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">已婚</option>
										<option value="1">未婚</option>
									</select>
								</td>
								<td>政治面貌:</td>
								<td>
									<select id="p_politics" class="easyui-combobox" editable="false" style="width:236px" required="required">
										<option value="0">党员</option>
										<option value="1">团员</option>
										<option value="2">群众</option>
										<option value="3">其他</option>
									</select>
								</td>
							</tr>
							<tr class="text_tr">
								<td>联系电话:</td>
								<td>
									<input type="text" id="p_phone" name="p_phone" class="textbox"/>
								</td>
								<td>银行卡号:</td>
								<td>
									<input type="text" id="p_bank_nub" name="p_bank_nub" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>开户行:</td>
								<td>
									<input type="text" id="p_bank_name" name="p_bank_name" class="textbox"/>
								</td>
								<td>户籍地址:</td>
								<td>
									<input type="text" id="p_huji_add" name="p_huji_add" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>常住联系地址:</td>
								<td>
									<input type="text" id="p_changzhu_add" name="p_changzhu_add" class="textbox"/>
								</td>
								<td>紧急联系人名称:</td>
								<td>
									<input type="text" id="p_urgency_name" name="p_urgency_name" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>紧急联系人关系:</td>
								<td>
									<input type="text" id="p_urgency_relation" name="p_urgency_relation" class="textbox"/>
								</td>
								<td>紧急联系人电话:</td>
								<td>
									<input type="text" id="p_urgency_phone" name="p_urgency_phone" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>是否有亲属同在公司:</td>
								<td>
									<select id="p_kinsfolk_y_n" class="easyui-combobox" editable="false" style="width:236px">
										<option value="0">是</option>
										<option value="1">否</option>
									</select>
								</td>
								<td>亲属关系:</td>
								<td>
									<input type="text" id="p_kinsfolk_relation" name="p_kinsfolk_relation" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>亲属姓名:</td>
								<td>
									<input type="text" id="p_kinsfolk_name" name="p_kinsfolk_name" class="textbox"/>
								</td>
								<td>亲属身份证号码:</td>
								<td>
									<input type="text" id="p_kinsfolk_id_nub" name="p_kinsfolk_id_nub" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>亲属最高学历:</td>
								<td>
									<input type="text" id="p_kinsfolk_xueli" name="p_kinsfolk_xueli" class="textbox"/>
								</td>
								<td>司龄:</td>
								<td>
									<input type="text" id="p_company_age" name="p_company_age" class="easyui-numberspinner" style="width:236px"
									data-options="min:1,max:50" required="required"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td colspan="4" style="text-align: center"><span style="color: red">注:以下资料是否已经提交给人事部</span></td>
							</tr>
							<tr>
								<td colspan="4">
									<input type="checkbox" id="p_c_yingpin_table" name="p_c_yingpin_table"/>应聘申请表
									<input type="checkbox" id="p_c_interview_tab" name="p_c_interview_tab"/>面谈记录表
									<input type="checkbox" id="p_c_id_copies" name="p_c_id_copies"/>身份证复印件
									<input type="checkbox" id="p_c_xueli" name="p_c_xueli"/>学历证书
									<input type="checkbox" id="p_c_xuewei" name="p_c_xuewei"/>学位证书
									<input type="checkbox" id="p_c_bank_nub" name="p_c_bank_nub"/>银行卡号
									<input type="checkbox" id="p_c_tijian_tab" name="p_c_tijian_tab"/>体检表
									<input type="checkbox" id="p_c_health" name="p_c_health"/>健康证
									<input type="checkbox" id="p_c_img" name="p_c_img"/>照片
									<input type="checkbox" id="p_c_welcome" name="p_c_welcome"/>欢迎词
									<input type="checkbox" id="p_c_staff" name="p_c_staff"/>员工手册回执
									<input type="checkbox" id="p_c_admin" name="p_c_admin"/>管理者回执
									<input type="checkbox" id="p_c_shebao" name="p_c_shebao"/>社保同意书
									<input type="checkbox" id="p_c_shangbao" name="p_c_shangbao"/>商保申请书
									<input type="checkbox" id="p_c_secrecy" name="p_c_secrecy"/>保密协议
									<input type="checkbox" id="p_c_prohibida" name="p_c_prohibida"/>禁业限制协议
									<input type="checkbox" id="p_c_contract" name="p_c_contract"/>劳动合同
									<input type="checkbox" id="p_c_post" name="p_c_post"/>岗位说明书
									<input type="checkbox" id="p_c_corruption" name="p_c_corruption"/>反贪腐承诺书
									<input type="checkbox" id="p_c_probation" name="p_c_probation"/>试用期考核表
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div data-options="region:'east'" style="width:40%">
				<div id="person_accordion" class="easyui-accordion" data-options="fit:true">   
				    <div title="教育背景" data-options="selected:true" >   
				       	<table id="education_grid"></table>
				       	<div id="education_btn">
			        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addEducation()">添加</a>
			        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeEducation()">删除</a>
			        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptEducation()">确定</a>
			        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectEducation()">取消</a>
			        	</div>
				    </div>   
				    <div title="培训经历">   
				       <div id="" class="easyui-layout" data-options="fit:true">   
						    <div data-options="region:'center'" style="height:50%">
						     	<div id="p" class="easyui-panel" title="入职前" data-options="fit:true,">   
								     <table id="cultivate_front_grid"></table>
								     <div id="cultivate_front_btn">
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCultivateFront()">添加</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeCultivateFront()">删除</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptCultivateFront()">确定</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectCultivateFront()">取消</a>
							         </div>
								</div>  
						    </div>   
						    <div data-options="region:'south'" style="height:50%">
						    	<div id="p" class="easyui-panel" title="入职后" data-options="fit:true,">   
								     <table id="cultivate_later_grid"></table>
								     <div id="cultivate_later_btn">
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCultivateLater()">添加</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeCultivateLater()">删除</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptCultivateLater()">确定</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectCultivateLater()">取消</a>
							         </div>
								</div>  
						    </div>   
					 	</div>     
				    </div>   
				    <div title="工作经历">   
				       	 <div id="" class="easyui-layout" data-options="fit:true">   
						    <div data-options="region:'center'" style="height:50%">
						     	<div id="p" class="easyui-panel" title="入职前" data-options="fit:true,">   
								     <table id="work_front_grid"></table>
								     <div id="work_front_btn">
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addWorkFront()">添加</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeWorkFront()">删除</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptWorkFront()">确定</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectWorkFront()">取消</a>
							         </div>
								</div>  
						    </div>   
						    <div data-options="region:'south'" style="height:50%">
						    	<div id="p" class="easyui-panel" title="入职后" data-options="fit:true,">   
								     <table id="work_later_grid"></table>
								     <div id="work_later_btn">
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addWorkLater()">添加</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeWorkLater()">删除</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptWorkLater()">确定</a>
							        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectWorkLater()">取消</a>
							         </div>
								</div>  
						    </div>   
					 	</div>         
				    </div>   
				    <div title="获得证书">   
				        <table id="certificate_grid"></table>
				        <div id="certificate_btn">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCertificate()">添加</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeCertificate()">删除</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptCertificate()">确定</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectCertificate()">取消</a>
						</div>         	   
				    </div>  
				    <div title="家庭背景">   
				       	<table id="family_grid"></table>
				       	<div id="family_btn">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addFamily()">添加</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeFamily()">删除</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="acceptFamily()">确定</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="rejectFamily()">取消</a>
						</div>         
				    </div>  
				</div>     
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="savePersonInFo()" style="width:80px">保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#person_win').window('close')" style="width:80px">取消</a>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/PersonGrid.js"></script>
</body>
</html>