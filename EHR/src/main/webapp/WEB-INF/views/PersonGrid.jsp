<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
    <!--人事档案 -->
    <!-- 引入样式 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/PersonGrid.css"/>
	<!-- 嵌入easyui-tabs主体内容 -->
	<div ip="person_panel" class="easyui-panel" data-options="fit:true,border:false,noheader:true">
		<table id="person_grid"></table>
		<div id="person_tbar">
			<div style="margin-bottom: 5px;">
			<shiro:hasPermission name="person:add">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPersonInFo()">添加</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="person:edit">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPersonInFo()">修改</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="person:remove">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removePersonInFo()">删除</a>
	       </shiro:hasPermission>
	        </div>
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	工号：<input type="text" class="textbox" id="search_p_number" name="search_p_number" style="width: 110px;"/>
	        	姓名：<input type="text" class="textbox" id="search_p_name" name="search_p_name" style="width: 110px;"/>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="resetPersonInFo()">重置</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchPersonInFo()">查询</a>
	        </div>
		</div>
	</div>
	<div id="person_win" class="easyui-window" closed="true" 
        data-options="iconCls:'icon-save',modal:true,fit:true,collapsible:false,minimizable:false,maximizable:false">   
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',split:true" style="width:60%">
				<form id="person_form" action="javascript:void(0)" style="width: 100%">
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
									<input type="hidden" id="p_oa_and_ehr" name="p_oa_and_ehr"/>
									<input type="hidden" id="p_id" name="p_id"/>
									<input type="text" id="p_number" name="p_number" class="textbox"/>
								</td>
								<td>工号创建:</td>
								<td style="text-align: left;">
	        						<a id="oaSync" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" href="javascript:void(0)" onclick="oaSyncWorkNum()" style="width:110px">OA同步工号</a>
									<a id="ehrAot" class="easyui-linkbutton" data-options="iconCls:'icon-add'" href="javascript:void(0)" onclick="ehrAotuWorkNum()" style="width:110px">EHR自动发号</a>
								</td>
							</tr>
							<tr class="text_tr">
								<td>姓名:</td>
								<td>
									<input type="text" id="p_name" name="p_name" class="textbox"/>
								</td>
								<td>职称:</td>
								<td>
									<select id="p_title" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">无职称</option>
										<option value="1">初级职称</option>
										<option value="2">中级职称</option>
										<option value="3">高级职称</option>
									</select> 
								</td>
							</tr>
							<tr class="text_tr">
								<td>员工身份证号码:</td>
								<td>
									<input type="text" id="p_id_num" name="p_id_num" class="textbox" onblur="checkIdNumFormat()"/>
								</td>
								<td>性别:</td>
								<td>
									<input type="text" id="p_sex" name="p_sex" class="textbox" disabled="disabled"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>年龄:</td>
								<td>
									<input type="text" id="p_age" name="p_age" class="textbox" disabled="disabled"/>
								</td>
								<td>员工出生年月日:</td>
								<td>
									<input type="text" id="p_birthday" name="p_birthday" class="textbox" disabled="disabled"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>岗位名称:</td>
								<td>
									<input type="hidden" id="p_post_id" name="p_post_id"/>
									<input type="text" id="p_post" name="p_post" style="width:100%"/>
								</td>
								<td>职级名称:</td>
								<td>
									<input type="hidden" id="p_level_id" name="p_level_id"/>
									<input type="text" id="p_level_name" name="p_level_name" style="width:100%"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>公司名称:</td>
								<td>
									<input type="hidden" id="p_company_id" name="p_company_id"/>
									<input type="text" id="p_company_name" name="p_company_name" editable="false" style="width:100%" required="required"/>
								</td>
								<td>中心机构:</td>
								<td>
									<input type="hidden" id="p_organization_id" name="p_organization_id"/>
									<input type="text" id="p_organization" name="p_organization" editable="false" style="width:100%" required="required"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>所属部门:</td>
								<td>
									<input type="hidden" id="p_department_id" name="p_department_id"/>
									<input type="text" id="p_department" name="p_department" editable="false" style="width:100%" required="required"/>
								</td>
								<td>科室:</td>
								<td>
									<input type="hidden" id="p_section_office_id" name="p_section_office_id"/>
									<input type="text" id="p_section_office" name="p_section_office" style="width:100%"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>班组:</td>
								<td>
									<input type="hidden" id="p_group_id" name="p_group_id"/>
									<input type="text" id="p_group" name="p_group" style="width:100%"/>
								</td>
								<td>员工状态:</td>
								<td>
									<select id="p_state" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">在职</option>
										<option value="1">离职</option>
									</select>
								</td>
							</tr>
							<tr class="text_tr">
								<td>员工性质:</td>
								<td>
									<select id="p_property" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">全职</option>
										<option value="1">兼职</option>
									</select> 
								</td>
								<td>岗位性质:</td>
								<td>
									<select id="p_post_property" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">管理者</option>
										<option value="1">员工</option>
									</select> 
								</td>
							</tr>
							<tr class="text_tr">
								<td>入职日期:</td>
								<td>
									<input type="text" id="p_in_date" name="p_in_date" class="easyui-datebox" style="width:100%"
									 	data-options="editable:false" required="required"/>
								</td>
								<td>转正日期:</td>
								<td>
									<input type="text" id="p_turn_date" name="p_turn_date" class="easyui-datebox" style="width:100%"
									 	data-options="editable:false" required="required"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>联系电话:</td>
								<td>
									<input type="text" id="p_phone" name="p_phone" class="textbox"/>
								</td>
								<td>考勤方式:</td>
								<td>
									<select id="p_checking_in" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">行政考勤</option>
										<option value="1">移动考勤</option>
										<option value="2">不考勤</option>
									</select> 
								</td>
							</tr>
							<tr class="text_tr">
								<td>用工形式:</td>
								<td>
									<select id="p_use_work_form" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">劳动合同制</option>
										<option value="1">劳务派遣制</option>
										<option value="2">其他</option>
									</select> 
								</td>
								<td>劳动合同签订次数:</td>
								<td>
									<input type="text" id="p_contract_count" name="" class="easyui-numberspinner" style="width:100%" required="required"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>劳动合同开始日期:</td>
								<td>
									<input type="text" id="p_contract_begin_date" name="p_contract_begin_date" class="textbox" disabled="disabled"/>
								</td>
								<td>劳动合同结束日期:</td>
								<td>
									<input type="text" id="p_contract_end_date" name="p_contract_end_date" class="easyui-datebox" style="width:100%"
										data-options="editable:false"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>社保购买起始日期:</td>
								<td>
									<input type="text" id="p_shebao_begin_month" name="p_shebao_begin_month" class="easyui-datebox" style="width:100%"
										data-options="editable:false"/>
								</td>
								<td>社保购买终止日期:</td>
								<td>
									<input type="text" id="p_shebao_end_month" name="p_shebao_end_month" class="easyui-datebox" style="width:100%"
										data-options="editable:false"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>公积金购买起始日期:</td>
								<td>
									<input type="text" id="p_gjj_begin_month" name="p_gjj_begin_month" class="easyui-datebox" style="width:100%"
										data-options="editable:false"/>
								</td>
								<td>公积金购买终止日期:</td>
								<td>
									<input type="text" id="p_gjj_end_month" name="p_gjj_end_month" class="easyui-datebox" style="width:100%"
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
							<!-- 2018-9-19号添加 -->
							<tr class="text_tr">
								<td>户籍:</td>
								<td>
									<input type="text" id="p_huji" name="p_huji" class="textbox"/>
								</td>
								<td>户口性质:</td>
								<td>
									<select id="p_hukou_xingzhi" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">农业户口</option>
										<option value="1">城镇户口</option>
									</select>
								</td>
							</tr>
							<tr class="text_tr">
								<td>婚姻状况:</td>
								<td>
									<select id="p_marriage" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">已婚</option>
										<option value="1">未婚</option>
									</select>
								</td>
								<td>政治面貌:</td>
								<td>
									<select id="p_politics" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">党员</option>
										<option value="1">团员</option>
										<option value="2">群众</option>
										<option value="3">其他</option>
									</select>
								</td>
							</tr>
							<!-- 2018-9-19号添加 -->
							<tr class="text_tr">
								<td>年龄区间:</td>
								<td>
									<select id="p_age_qujian" class="easyui-combobox" editable="false" style="width:100%" required="required">
										<option value="0">18至30</option>
										<option value="1">30至40</option>
										<option value="2">40至50</option>
										<option value="3">50至65</option>
									</select>
								</td>
								<td>最高学历:</td>
								<td>
									<!-- p_kinsfolk_xueli -->
									<select id="p_zuigao_xueli" class="easyui-combobox" editable="false" style="width:100%">
										<option value="1">初中</option>
										<option value="2">高中</option>
										<option value="3">中专</option>
										<option value="4">大专</option>
										<option value="5">本科</option>
										<option value="6">研究生</option>
										<option value="7">硕士</option>
										<option value="7">博士</option>
									</select> 
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
								<td>银行卡号:</td>
								<td>
									<input type="text" id="p_bank_nub" name="p_bank_nub" class="textbox"/>
								</td>
								<td>离职日期:</td>
								<td>
									<input type="text" id="p_out_date" name="p_out_date" class="easyui-datebox" style="width:100%"
										data-options="editable:false"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>离职原因:</td>
								<td colspan="3">
									<input type="text" id="p_out_describe" name="p_out_describe" class="textbox"/>
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
									<select id="p_urgency_relation" class="easyui-combobox" editable="false" style="width:100%">
										<option value="1">父亲</option>
										<option value="2">母亲</option>
										<option value="3">朋友</option>
										<option value="4">妻子</option>
										<option value="5">子女</option>
										<option value="6">亲戚</option>
										<option value="7">兄弟</option>
										<option value="8">姐妹</option>
									</select> 
								</td>
								<td>紧急联系人电话:</td>
								<td>
									<input type="text" id="p_urgency_phone" name="p_urgency_phone" class="textbox"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td>是否有亲属同在公司:</td>
								<td>
									<select id="p_kinsfolk_y_n" class="easyui-combobox" editable="false" style="width:100%">
										<option value="0">是</option>
										<option value="1">否</option>
									</select>
								</td>
								<td>亲属关系:</td>
								<td>
									<select id="p_kinsfolk_relation" class="easyui-combobox" editable="false" style="width:100%">
										<option value="1">父亲</option>
										<option value="2">母亲</option>
										<option value="3">妻子</option>
										<option value="4">子女</option>
										<option value="5">亲戚</option>
										<option value="6">兄弟</option>
										<option value="7">姐妹</option>
									</select> 
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
								<td>推荐人姓名:</td>
								<td>
									<input type="text" id="p_recommend_person" name="p_recommend_person" class="textbox"/>
								</td>
								<td>司龄:</td>
								<td>
									<input type="text" id="p_company_age" name="p_company_age" class="textbox" disabled="disabled"/>
								</td>
							</tr>
							<tr class="text_tr">
								<td colspan="4" style="text-align: center"><span style="color: red">注:以下资料是否已经提交给人事部</span></td>
							</tr>
							<tr>
								<td colspan="4" rowspan="3">
									<table style="padding: 0px;margin: 0px;width: 100%;border-collapse: collapse;">
										<colgroup>
											<col width="14%">
											<col width="14%">
											<col width="14%">
											<col width="14%">
											<col width="14%">
											<col width="15%">
											<col width="15%">
										</colgroup>
										<tr>
											<td><input type="checkbox" id="p_c_yingpin_table" name="p_c_yingpin_table"/>应聘申请表</td>
											<td><input type="checkbox" id="p_c_interview_tab" name="p_c_interview_tab"/>面谈记录表</td>
											<td><input type="checkbox" id="p_c_id_copies" name="p_c_id_copies"/>身份证复印件</td>
											<td><input type="checkbox" id="p_c_xueli" name="p_c_xueli"/>学历证书</td>
											<td><input type="checkbox" id="p_c_xuewei" name="p_c_xuewei"/>学位证书</td>
											<td><input type="checkbox" id="p_c_bank_nub" name="p_c_bank_nub"/>银行卡号</td>
											<td><input type="checkbox" id="p_c_tijian_tab" name="p_c_tijian_tab"/>体检表</td>
										</tr>
										<tr>
											<td><input type="checkbox" id="p_c_health" name="p_c_health"/>健康证</td>
											<td><input type="checkbox" id="p_c_img" name="p_c_img"/>照片</td>
											<td><input type="checkbox" id="p_c_welcome" name="p_c_welcome"/>欢迎词</td>
											<td><input type="checkbox" id="p_c_staff" name="p_c_staff"/>员工手册回执</td>
											<td><input type="checkbox" id="p_c_admin" name="p_c_admin"/>管理者回执</td>
											<td><input type="checkbox" id="p_c_shebao" name="p_c_shebao"/>社保同意书</td>
											<td><input type="checkbox" id="p_c_shangbao" name="p_c_shangbao"/>商保申请书</td>
										</tr>
										<tr>
											<td><input type="checkbox" id="p_c_secrecy" name="p_c_secrecy"/>保密协议</td>
											<td><input type="checkbox" id="p_c_prohibida" name="p_c_prohibida"/>竞业限制协议</td>
											<td><input type="checkbox" id="p_c_contract" name="p_c_contract"/>劳动合同</td>
											<td><input type="checkbox" id="p_c_post" name="p_c_post"/>岗位说明书</td>
											<td><input type="checkbox" id="p_c_corruption" name="p_c_corruption"/>反贪腐承诺书</td>
											<td><input type="checkbox" id="p_c_probation" name="p_c_probation"/>试用期考核表</td>
											<td></td>
										</tr>
									</table>
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
	<div id="dlg">
		<table id="dlg_grid"></table>
		<div id="dlg_tbar">
	        <div style="padding: 0 0 0 7px;color: #333;">
	        	工号：<input type="text" class="textbox" id="dlg_workNum" name="dlg_workNum" style="width: 100px;"/>
	        	姓名：<input type="text" class="textbox" id="dlg_userName" name="dlg_userName" style="width: 100px;"/>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="reloadOaWorkNum()">重置</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadOaWorkNumInFo()">查询</a>
	        </div>
		</div>
	</div>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/PersonGrid.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/validatebox.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/utrls.js"></script>
