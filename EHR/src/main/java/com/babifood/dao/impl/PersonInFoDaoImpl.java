package com.babifood.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.dao.PersonInFoDao;
import com.babifood.entity.PersonBasrcEntity;
@Repository
@Transactional
public class PersonInFoDaoImpl extends AuthorityControlDaoImpl implements PersonInFoDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	public static final Logger log = Logger.getLogger(LoginDaoImpl.class);
	@Override
	public List<Map<String, Object>> loadPersonInFo(String searchKey,String searchVal,String comp,String orga,String dept,String inOut) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("p_id,");
		sql.append("p_number,");
		sql.append("p_name,");
		sql.append("p_sex,");
		sql.append("p_age,");
		sql.append("p_title,");
		sql.append("p_post_id,");
		sql.append("p_post,");
		sql.append("p_level_id,");
		sql.append("p_level_name,");
		sql.append("p_company_id,");
		sql.append("p_company_name,");
		sql.append("p_department_id,");
		sql.append("p_department,");
		sql.append("p_organization_id,");
		sql.append("p_organization,");
		sql.append("p_section_office_id,");
		sql.append("p_section_office,");
		sql.append("p_group_id,");
		sql.append("p_group,");
		sql.append("p_state,");
		sql.append("p_property,");
		sql.append("p_post_property,");
		sql.append("p_in_date,");
		sql.append("p_turn_date,");
		sql.append("p_out_date,");
		sql.append("p_out_describe,");
		sql.append("p_checking_in,");
		sql.append("p_contract_begin_date,");
		sql.append("p_contract_end_date,");
		sql.append("p_shebao_begin_month,");
		sql.append("p_shebao_end_month,");
		sql.append("p_gjj_begin_month,");
		sql.append("p_gjj_end_month,");
		sql.append("p_nationality,");
		sql.append("p_nation,");
		sql.append("p_marriage,");
		sql.append("p_politics,");
		sql.append("p_phone,");
		sql.append("p_bank_nub,");
		sql.append("p_bank_name,");
		sql.append("p_huji_add,");
		sql.append("p_changzhu_add,");
		sql.append("p_urgency_name,");
		sql.append("p_urgency_relation,");
		sql.append("p_urgency_phone,");
		sql.append("p_urgency_id_nub,");
		sql.append("p_urgency_add,");
//		sql.append("p_kinsfolk_y_n,");
//		sql.append("p_kinsfolk_relation,");
//		sql.append("p_kinsfolk_name,");
//		sql.append("p_kinsfolk_id_nub,");
		sql.append("p_company_age,");
		sql.append("p_c_yingpin_table,");
		sql.append("p_c_interview_tab,");
		sql.append("p_c_id_copies,");
		sql.append("p_c_xueli,");
		sql.append("p_c_xuewei,");
		sql.append("p_c_bank_nub,");
		sql.append("p_c_tijian_tab,");
		sql.append("p_c_health,");
		sql.append("p_c_img,");
		sql.append("p_c_welcome,");
		sql.append("p_c_staff,");
		sql.append("p_c_admin,");
		sql.append("p_c_shebao,");
		sql.append("p_c_shangbao,");
		sql.append("p_c_secrecy,");
		sql.append("p_c_prohibida,");
		sql.append("p_c_contract,");
		sql.append("p_c_post,");
		sql.append("p_c_corruption,");
		sql.append("p_c_probation,");
		sql.append("p_create_date,");
		sql.append("p_this_dept_code,");
		sql.append("p_id_num,");
		sql.append("p_birthday,");
		sql.append("p_use_work_form,");
		sql.append("p_contract_count,");
		sql.append("p_oa_and_ehr,");
		sql.append("p_huji,");
		sql.append("p_hukou_xingzhi,");
		sql.append("p_age_qujian,");
		sql.append("p_zuigao_xueli,");
		sql.append("p_recommend_person,");
		sql.append("p_recommend_relation");
		sql.append(" from ehr_person_basic_info where 1=1");
		if(!"".equals(comp)){
			sql.append(" and p_company_name like '%"+comp+"%'");
		}
		if(!"".equals(orga)){
			sql.append(" and p_organization like '%"+orga+"%'");
		}
		if(!"".equals(dept)){
			sql.append(" and p_department like '%"+dept+"%'");
		}
		if(!"all".equals(inOut)){
			sql.append(" and p_state = '"+inOut+"'");
		}
		if(!"".equals(searchKey)&&searchKey!=null&&!"".equals(searchVal)&&searchVal!=null){
			sql.append(" and "+searchKey+" like '%"+searchVal+"%'");
		}
		StringBuffer returnsql = super.jointDataAuthoritySql("p_company_id","p_organization_id",sql);
		return jdbctemplate.queryForList(returnsql.toString());

	}
	@Override
	public List<Map<String, Object>> loadEducation(String e_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("e_id,e_p_id,e_begin_date,e_end_date,e_organization_name,e_specialty,e_xueli,e_xuewei,e_property,e_desc");
		sql.append(" from ehr_person_education where 1=1");
		if(e_p_id.equals("")||e_p_id!=null){
			sql.append(" and e_p_id='"+e_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public void savePersonInfo(PersonBasrcEntity personInFo) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql_basrc = new StringBuffer("delete from ehr_person_basic_info where p_number=?");//人员档案信息
		StringBuffer sql_education = new StringBuffer("delete from ehr_person_education where e_p_id=?");//教育背景
		StringBuffer sql_cultivateFront = new StringBuffer("delete from ehr_person_cultivate_front where c_p_id=?");//培训经历-入职前
		StringBuffer sql_cultivateLater = new StringBuffer("delete from ehr_person_cultivate_later where c_p_id=?");//培训经历-入职后
		StringBuffer sql_workFront = new StringBuffer("delete from ehr_person_work_front where w_p_id=?");//工作经历-入职前
		StringBuffer sql_workLater = new StringBuffer("delete from ehr_person_work_later where w_p_id=?");//工作经历-入职后
		StringBuffer sql_certificate = new StringBuffer("delete from ehr_person_certificate where c_p_id=?");//获得证书
		StringBuffer sql_family = new StringBuffer("delete from ehr_person_family where f_p_id=?");//家庭背景
		
		StringBuffer sql_insert_basrc = new StringBuffer();
		sql_insert_basrc.append("insert into ");
		sql_insert_basrc.append("ehr_person_basic_info (");
		sql_insert_basrc.append("p_id,");
		sql_insert_basrc.append("p_number,");
		sql_insert_basrc.append("p_name,");
		sql_insert_basrc.append("p_sex,");
		sql_insert_basrc.append("p_age,");
		sql_insert_basrc.append("p_title,");
		sql_insert_basrc.append("p_post_id,");
		sql_insert_basrc.append("p_post,");
		sql_insert_basrc.append("p_level_id,");
		sql_insert_basrc.append("p_level_name,");
		sql_insert_basrc.append("p_company_id,");
		sql_insert_basrc.append("p_company_name,");
		sql_insert_basrc.append("p_department_id,");
		sql_insert_basrc.append("p_department,");
		sql_insert_basrc.append("p_organization_id,");
		sql_insert_basrc.append("p_organization,");
		sql_insert_basrc.append("p_section_office_id,");
		sql_insert_basrc.append("p_section_office,");
		sql_insert_basrc.append("p_group_id,");
		sql_insert_basrc.append("p_group,");
		sql_insert_basrc.append("p_state,");
		sql_insert_basrc.append("p_property,");
		sql_insert_basrc.append("p_post_property,");
		sql_insert_basrc.append("p_in_date,");
		sql_insert_basrc.append("p_turn_date,");
		sql_insert_basrc.append("p_out_date,");
		sql_insert_basrc.append("p_out_describe,");
		sql_insert_basrc.append("p_checking_in,");
		sql_insert_basrc.append("p_contract_begin_date,");
		sql_insert_basrc.append("p_contract_end_date,");
		sql_insert_basrc.append("p_shebao_begin_month,");
		sql_insert_basrc.append("p_shebao_end_month,");
		sql_insert_basrc.append("p_gjj_begin_month,");
		sql_insert_basrc.append("p_gjj_end_month,");
		sql_insert_basrc.append("p_nationality,");
		sql_insert_basrc.append("p_nation,");
		sql_insert_basrc.append("p_marriage,");
		sql_insert_basrc.append("p_politics,");
		sql_insert_basrc.append("p_phone,");
		sql_insert_basrc.append("p_bank_nub,");
		sql_insert_basrc.append("p_bank_name,");
		sql_insert_basrc.append("p_huji_add,");
		sql_insert_basrc.append("p_changzhu_add,");
		sql_insert_basrc.append("p_urgency_name,");
		sql_insert_basrc.append("p_urgency_relation,");
		sql_insert_basrc.append("p_urgency_phone,");
		//新添加字段
		sql_insert_basrc.append("p_urgency_id_nub,");
		sql_insert_basrc.append("p_urgency_add,");
//		sql_insert_basrc.append("p_kinsfolk_y_n,");
//		sql_insert_basrc.append("p_kinsfolk_relation,");
//		sql_insert_basrc.append("p_kinsfolk_name,");
//		sql_insert_basrc.append("p_kinsfolk_id_nub,");
		sql_insert_basrc.append("p_company_age,");
		sql_insert_basrc.append("p_c_yingpin_table,");
		sql_insert_basrc.append("p_c_interview_tab,");
		sql_insert_basrc.append("p_c_id_copies,");
		sql_insert_basrc.append("p_c_xueli,");
		sql_insert_basrc.append("p_c_xuewei,");
		sql_insert_basrc.append("p_c_bank_nub,");
		sql_insert_basrc.append("p_c_tijian_tab,");
		sql_insert_basrc.append("p_c_health,");
		sql_insert_basrc.append("p_c_img,");
		sql_insert_basrc.append("p_c_welcome,");
		sql_insert_basrc.append("p_c_staff,");
		sql_insert_basrc.append("p_c_admin,");
		sql_insert_basrc.append("p_c_shebao,");
		sql_insert_basrc.append("p_c_shangbao,");
		sql_insert_basrc.append("p_c_secrecy,");
		sql_insert_basrc.append("p_c_prohibida,");
		sql_insert_basrc.append("p_c_contract,");
		sql_insert_basrc.append("p_c_post,");
		sql_insert_basrc.append("p_c_corruption,");
		sql_insert_basrc.append("p_c_probation,");
		sql_insert_basrc.append("p_create_date,");
		sql_insert_basrc.append("p_this_dept_code,");
		sql_insert_basrc.append("p_id_num,");
		sql_insert_basrc.append("p_birthday,");
		sql_insert_basrc.append("p_use_work_form,");
		sql_insert_basrc.append("p_contract_count,");
		sql_insert_basrc.append("p_oa_and_ehr,");
		sql_insert_basrc.append("p_huji,");
		sql_insert_basrc.append("p_hukou_xingzhi,");
		sql_insert_basrc.append("p_age_qujian,");
		sql_insert_basrc.append("p_zuigao_xueli,");
		sql_insert_basrc.append("p_recommend_person,");
		sql_insert_basrc.append("p_recommend_relation)");
		sql_insert_basrc.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Object[] sql_insert_basrc_params=new Object[82];
		sql_insert_basrc_params[0]=personInFo.getP_id();
		sql_insert_basrc_params[1]=personInFo.getP_number();
		sql_insert_basrc_params[2]=personInFo.getP_name();
		sql_insert_basrc_params[3]=personInFo.getP_sex();
		sql_insert_basrc_params[4]=personInFo.getP_age();
		sql_insert_basrc_params[5]=personInFo.getP_title();
		sql_insert_basrc_params[6]=personInFo.getP_post_id();
		sql_insert_basrc_params[7]=personInFo.getP_post();
		sql_insert_basrc_params[8]=personInFo.getP_level_id();
		sql_insert_basrc_params[9]=personInFo.getP_level_name();
		sql_insert_basrc_params[10]=personInFo.getP_company_id();
		sql_insert_basrc_params[11]=personInFo.getP_company_name();
		sql_insert_basrc_params[12]=personInFo.getP_department_id();
		sql_insert_basrc_params[13]=personInFo.getP_department();
		sql_insert_basrc_params[14]=personInFo.getP_organization_id();
		sql_insert_basrc_params[15]=personInFo.getP_organization();
		sql_insert_basrc_params[16]=personInFo.getP_section_office_id();
		sql_insert_basrc_params[17]=personInFo.getP_section_office();
		sql_insert_basrc_params[18]=personInFo.getP_group_id();
		sql_insert_basrc_params[19]=personInFo.getP_group();
		sql_insert_basrc_params[20]=personInFo.getP_state();
		sql_insert_basrc_params[21]=personInFo.getP_property();
		sql_insert_basrc_params[22]=personInFo.getP_post_property();
		sql_insert_basrc_params[23]=personInFo.getP_in_date();
		sql_insert_basrc_params[24]=personInFo.getP_turn_date();
		sql_insert_basrc_params[25]=personInFo.getP_out_date();
		sql_insert_basrc_params[26]=personInFo.getP_out_describe();
		sql_insert_basrc_params[27]=personInFo.getP_checking_in();
		sql_insert_basrc_params[28]=personInFo.getP_contract_begin_date();
		sql_insert_basrc_params[29]=personInFo.getP_contract_end_date();
		sql_insert_basrc_params[30]=personInFo.getP_shebao_begin_month();
		sql_insert_basrc_params[31]=personInFo.getP_shebao_end_month();
		sql_insert_basrc_params[32]=personInFo.getP_gjj_begin_month();
		sql_insert_basrc_params[33]=personInFo.getP_gjj_end_month();
		sql_insert_basrc_params[34]=personInFo.getP_nationality();
		sql_insert_basrc_params[35]=personInFo.getP_nation();
		sql_insert_basrc_params[36]=personInFo.getP_marriage();
		sql_insert_basrc_params[37]=personInFo.getP_politics();
		sql_insert_basrc_params[38]=personInFo.getP_phone();
		sql_insert_basrc_params[39]=personInFo.getP_bank_nub();
		sql_insert_basrc_params[40]=personInFo.getP_bank_name();
		sql_insert_basrc_params[41]=personInFo.getP_huji_add();
		sql_insert_basrc_params[42]=personInFo.getP_changzhu_add();
		sql_insert_basrc_params[43]=personInFo.getP_urgency_name();
		sql_insert_basrc_params[44]=personInFo.getP_urgency_relation();
		sql_insert_basrc_params[45]=personInFo.getP_urgency_phone();
		sql_insert_basrc_params[46]=personInFo.getP_urgency_id_nub();
		sql_insert_basrc_params[47]=personInFo.getP_urgency_add();
//		sql_insert_basrc_params[46]=personInFo.getP_kinsfolk_y_n();
//		sql_insert_basrc_params[47]=personInFo.getP_kinsfolk_relation();
//		sql_insert_basrc_params[48]=personInFo.getP_kinsfolk_name();
//		sql_insert_basrc_params[49]=personInFo.getP_kinsfolk_id_nub();
		sql_insert_basrc_params[48]=personInFo.getP_company_age();
		sql_insert_basrc_params[49]=personInFo.getP_c_yingpin_table();
		sql_insert_basrc_params[50]=personInFo.getP_c_interview_tab();
		sql_insert_basrc_params[51]=personInFo.getP_c_id_copies();
		sql_insert_basrc_params[52]=personInFo.getP_c_xueli();
		sql_insert_basrc_params[53]=personInFo.getP_c_xuewei();
		sql_insert_basrc_params[54]=personInFo.getP_c_bank_nub();
		sql_insert_basrc_params[55]=personInFo.getP_c_tijian_tab();
		sql_insert_basrc_params[56]=personInFo.getP_c_health();
		sql_insert_basrc_params[57]=personInFo.getP_c_img();
		sql_insert_basrc_params[58]=personInFo.getP_c_welcome();
		sql_insert_basrc_params[59]=personInFo.getP_c_staff();
		sql_insert_basrc_params[60]=personInFo.getP_c_admin();
		sql_insert_basrc_params[61]=personInFo.getP_c_shebao();
		sql_insert_basrc_params[62]=personInFo.getP_c_shangbao();
		sql_insert_basrc_params[63]=personInFo.getP_c_secrecy();
		sql_insert_basrc_params[64]=personInFo.getP_c_prohibida();
		sql_insert_basrc_params[65]=personInFo.getP_c_contract();
		sql_insert_basrc_params[66]=personInFo.getP_c_post();
		sql_insert_basrc_params[67]=personInFo.getP_c_corruption();
		sql_insert_basrc_params[68]=personInFo.getP_c_probation();
		sql_insert_basrc_params[69]=personInFo.getP_create_date();
		sql_insert_basrc_params[70]=personInFo.getP_this_dept_code();
		sql_insert_basrc_params[71]=personInFo.getP_id_num();
		sql_insert_basrc_params[72]=personInFo.getP_birthday();
		sql_insert_basrc_params[73]=personInFo.getP_use_work_form();
		sql_insert_basrc_params[74]=personInFo.getP_contract_count();
		sql_insert_basrc_params[75]=personInFo.getP_oa_and_ehr();
		sql_insert_basrc_params[76]=personInFo.getP_huji();
		sql_insert_basrc_params[77]=personInFo.getP_hukou_xingzhi();
		sql_insert_basrc_params[78]=personInFo.getP_age_qujian();
		sql_insert_basrc_params[79]=personInFo.getP_zuigao_xueli();
		sql_insert_basrc_params[80]=personInFo.getP_recommend_person();
		sql_insert_basrc_params[81]=personInFo.getP_recommend_relation();
		//教育背景
		List<Object[]> education_params = new ArrayList<>();
		for(int i=0;i<personInFo.getEducation().size();i++){
			education_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getEducation().get(i).getE_begin_date(),
					personInFo.getEducation().get(i).getE_end_date(),
					personInFo.getEducation().get(i).getE_organization_name(),
					personInFo.getEducation().get(i).getE_specialty(),
					personInFo.getEducation().get(i).getE_xueli(),
					personInFo.getEducation().get(i).getE_xuewei(),
					personInFo.getEducation().get(i).getE_property(),
					personInFo.getEducation().get(i).getE_desc()
					});
		}
		final String education_insert_sql = "insert into ehr_person_education (e_p_id,e_begin_date,e_end_date,e_organization_name,e_specialty,e_xueli,e_xuewei,e_property,e_desc) values(?,?,?,?,?,?,?,?,?)";
		//培训经历-入职前
		List<Object[]> cultivateFront_params = new ArrayList<>();
		for(int i=0;i<personInFo.getCultivateFront().size();i++){
			cultivateFront_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getCultivateFront().get(i).getC_begin_date(),
					personInFo.getCultivateFront().get(i).getC_end_date(),
					personInFo.getCultivateFront().get(i).getC_education(),
					personInFo.getCultivateFront().get(i).getC_training_content(),
					personInFo.getCultivateFront().get(i).getC_certificate(),
					personInFo.getCultivateFront().get(i).getC_desc()
					});
		}
		final String cultivateFront_insert_sql = "insert into ehr_person_cultivate_front (c_p_id,c_begin_date,c_end_date,c_education,c_training_content,c_certificate,c_desc) values(?,?,?,?,?,?,?)";
		//培训经历-入职后
		List<Object[]> cultivateLater_params = new ArrayList<>();
		for(int i=0;i<personInFo.getCultivateLater().size();i++){
			cultivateLater_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getCultivateLater().get(i).getC_begin_date(),
					personInFo.getCultivateLater().get(i).getC_end_date(),
					personInFo.getCultivateLater().get(i).getC_training_institution(),
					personInFo.getCultivateLater().get(i).getC_training_course(),
					personInFo.getCultivateLater().get(i).getC_training_add(),
					personInFo.getCultivateLater().get(i).getC_certificate(),
					personInFo.getCultivateLater().get(i).getC_training_type(),
					personInFo.getCultivateLater().get(i).getC_training_begin_date(),
					personInFo.getCultivateLater().get(i).getC_training_end_date(),
					personInFo.getCultivateLater().get(i).getC_training_cost(),
					personInFo.getCultivateLater().get(i).getC_desc()
					});
		}
		final String cultivateLater_insert_sql = "insert into ehr_person_cultivate_later (c_p_id,c_begin_date,c_end_date,c_training_institution,c_training_course,c_training_add,c_certificate,c_training_type,c_training_begin_date,c_training_end_date,c_training_cost,c_desc) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		//工作经历-入职前
		List<Object[]> workFront_params = new ArrayList<>();
		for(int i=0;i<personInFo.getWorkFront().size();i++){
			workFront_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getWorkFront().get(i).getW_begin_date(),
					personInFo.getWorkFront().get(i).getW_end_date(),
					personInFo.getWorkFront().get(i).getW_company_name(),
					personInFo.getWorkFront().get(i).getW_type(),
					personInFo.getWorkFront().get(i).getW_post_name(),
					personInFo.getWorkFront().get(i).getW_prove(),
					personInFo.getWorkFront().get(i).getW_prove_post(),
					personInFo.getWorkFront().get(i).getW_prove_phone(),
					personInFo.getWorkFront().get(i).getW_demission_desc(),
					personInFo.getWorkFront().get(i).getW_desc()
					});
		}
		final String workFront_insert_sql = "insert into ehr_person_work_front (w_p_id,w_begin_date,w_end_date,w_company_name,w_type,w_post_name,w_prove,w_prove_post,w_prove_phone,w_demission_desc,w_desc) values(?,?,?,?,?,?,?,?,?,?,?)";		
		//工作经历-入职后
		List<Object[]> workLater_params = new ArrayList<>();
		for(int i=0;i<personInFo.getWorkLater().size();i++){
			workLater_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getWorkLater().get(i).getW_begin_date(),
					personInFo.getWorkLater().get(i).getW_end_date(),
					personInFo.getWorkLater().get(i).getW_company_name(),
					personInFo.getWorkLater().get(i).getW_post_name(),
					personInFo.getWorkLater().get(i).getW_desc()
					});
		}	
		final String workLater_insert_sql = "insert into ehr_person_work_later (w_p_id,w_begin_date,w_end_date,w_company_name,w_post_name,w_desc) values(?,?,?,?,?,?)";		
		//获得证书
		List<Object[]> certificate_params = new ArrayList<>();
		for(int i=0;i<personInFo.getCertificate().size();i++){
			certificate_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getCertificate().get(i).getC_name(),
					personInFo.getCertificate().get(i).getC_organization(),
					personInFo.getCertificate().get(i).getC_begin_date(),
					personInFo.getCertificate().get(i).getC_end_date(),
					personInFo.getCertificate().get(i).getC_desc()
					});
		}	
		final String certificate_insert_sql = "insert into ehr_person_certificate (c_p_id,c_name,c_organization,c_begin_date,c_end_date,c_desc) values(?,?,?,?,?,?)";	
		//家庭背景
		List<Object[]> family_params = new ArrayList<>();
		for(int i=0;i<personInFo.getFamily().size();i++){
			family_params.add(new Object[]{
					personInFo.getP_number(),
					personInFo.getFamily().get(i).getF_relation(),
					personInFo.getFamily().get(i).getF_name(),
					personInFo.getFamily().get(i).getF_date(),
					personInFo.getFamily().get(i).getF_company(),
					personInFo.getFamily().get(i).getF_duty(),
					personInFo.getFamily().get(i).getF_desc()
					});
		}	
		final String family_insert_sql = "insert into ehr_person_family (f_p_id,f_relation,f_name,f_date,f_company,f_duty,f_desc) values(?,?,?,?,?,?,?)";						
		
		jdbctemplate.update(sql_basrc.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_education.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_cultivateFront.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_cultivateLater.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_workFront.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_workLater.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_certificate.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_family.toString(),personInFo.getP_number());
		jdbctemplate.update(sql_insert_basrc.toString(),sql_insert_basrc_params);
		jdbctemplate.batchUpdate(education_insert_sql,education_params);
		jdbctemplate.batchUpdate(cultivateFront_insert_sql,cultivateFront_params);
		jdbctemplate.batchUpdate(cultivateLater_insert_sql,cultivateLater_params);
		jdbctemplate.batchUpdate(workFront_insert_sql,workFront_params);
		jdbctemplate.batchUpdate(workLater_insert_sql,workLater_params);
		jdbctemplate.batchUpdate(certificate_insert_sql,certificate_params);
		jdbctemplate.batchUpdate(family_insert_sql,family_params);
	}
	@Override
	public void removePersonInFo(String p_number) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql_basrc = new StringBuffer("delete from ehr_person_basic_info where p_number=?");//人员档案信息
		StringBuffer sql_education = new StringBuffer("delete from ehr_person_education where e_p_id=?");//教育背景
		StringBuffer sql_cultivateFront = new StringBuffer("delete from ehr_person_cultivate_front where c_p_id=?");//培训经历-入职前
		StringBuffer sql_cultivateLater = new StringBuffer("delete from ehr_person_cultivate_later where c_p_id=?");//培训经历-入职后
		StringBuffer sql_workFront = new StringBuffer("delete from ehr_person_work_front where w_p_id=?");//工作经历-入职前
		StringBuffer sql_workLater = new StringBuffer("delete from ehr_person_work_later where w_p_id=?");//工作经历-入职后
		StringBuffer sql_certificate = new StringBuffer("delete from ehr_person_certificate where c_p_id=?");//获得证书
		StringBuffer sql_family = new StringBuffer("delete from ehr_person_family where f_p_id=?");//家庭背景

		jdbctemplate.update(sql_basrc.toString(),p_number);
		jdbctemplate.update(sql_education.toString(),p_number);
		jdbctemplate.update(sql_cultivateFront.toString(),p_number);
		jdbctemplate.update(sql_cultivateLater.toString(),p_number);
		jdbctemplate.update(sql_workFront.toString(),p_number);
		jdbctemplate.update(sql_workLater.toString(),p_number);
		jdbctemplate.update(sql_certificate.toString(),p_number);
		jdbctemplate.update(sql_family.toString(),p_number);

	}
	@Override
	public List<Map<String, Object>> loadCultivateFront(String c_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("c_id,c_p_id,c_begin_date,c_end_date,c_education,c_training_content,c_certificate,c_desc");
		sql.append(" from ehr_person_cultivate_front where 1=1");
		if(c_p_id.equals("")||c_p_id!=null){
			sql.append(" and c_p_id='"+c_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadCultivateLater(String c_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("c_id,c_p_id,c_begin_date,c_end_date,c_training_institution,c_training_course,c_training_add,c_certificate,c_training_type,c_training_begin_date,c_training_end_date,c_training_cost,c_desc");
		sql.append(" from ehr_person_cultivate_later where 1=1");
		if(c_p_id.equals("")||c_p_id!=null){
			sql.append(" and c_p_id='"+c_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadWorkFront(String w_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("w_id,w_p_id,w_begin_date,w_end_date,w_company_name,w_type,w_post_name,w_prove,w_prove_post,w_prove_phone,w_demission_desc,w_desc");
		sql.append(" from ehr_person_work_front where 1=1");
		if(w_p_id.equals("")||w_p_id!=null){
			sql.append(" and w_p_id='"+w_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadWorkLater(String w_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("w_id,w_p_id,w_begin_date,w_end_date,w_company_name,w_post_name,w_desc");
		sql.append(" from ehr_person_work_later where 1=1");
		if(w_p_id.equals("")||w_p_id!=null){
			sql.append(" and w_p_id='"+w_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadCertificate(String c_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("c_id,c_p_id,c_name,c_organization,c_begin_date,c_end_date,c_desc");
		sql.append(" from ehr_person_certificate where 1=1");
		if(c_p_id.equals("")||c_p_id!=null){
			sql.append(" and c_p_id='"+c_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadFamily(String f_p_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("f_id,f_p_id,f_relation,f_name,f_date,f_company,f_duty,f_desc");
		sql.append(" from ehr_person_family where 1=1");
		if(f_p_id.equals("")||f_p_id!=null){
			sql.append(" and f_p_id='"+f_p_id+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public Object getPersonFoPid(String p_number) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("p_id,");
		sql.append("p_number,");
		sql.append("p_name,");
		sql.append("p_sex,");
		sql.append("p_age,");
		sql.append("p_title,");
		sql.append("p_post_id,");
		sql.append("p_post,");
		sql.append("p_level_id,");
		sql.append("p_level_name,");
		sql.append("p_company_id,");
		sql.append("p_company_name,");
		sql.append("p_department_id,");
		sql.append("p_department,");
		sql.append("p_organization_id,");
		sql.append("p_organization,");
		sql.append("p_section_office_id,");
		sql.append("p_section_office,");
		sql.append("p_group_id,");
		sql.append("p_group,");
		sql.append("p_state,");
		sql.append("p_property,");
		sql.append("p_post_property,");
		sql.append("p_in_date,");
		sql.append("p_turn_date,");
		sql.append("p_out_date,");
		sql.append("p_out_describe,");
		sql.append("p_checking_in,");
		sql.append("p_contract_begin_date,");
		sql.append("p_contract_end_date,");
		sql.append("p_shebao_begin_month,");
		sql.append("p_shebao_end_month,");
		sql.append("p_gjj_begin_month,");
		sql.append("p_gjj_end_month,");
		sql.append("p_nationality,");
		sql.append("p_nation,");
		sql.append("p_marriage,");
		sql.append("p_politics,");
		sql.append("p_phone,");
		sql.append("p_bank_nub,");
		sql.append("p_bank_name,");
		sql.append("p_huji_add,");
		sql.append("p_changzhu_add,");
		sql.append("p_urgency_name,");
		sql.append("p_urgency_relation,");
		sql.append("p_urgency_phone,");
		sql.append("p_urgency_id_nub,");
		sql.append("p_urgency_add,");
//		sql.append("p_kinsfolk_y_n,");
//		sql.append("p_kinsfolk_relation,");
//		sql.append("p_kinsfolk_name,");
//		sql.append("p_kinsfolk_id_nub,");
		sql.append("p_company_age,");
		sql.append("p_c_yingpin_table,");
		sql.append("p_c_interview_tab,");
		sql.append("p_c_id_copies,");
		sql.append("p_c_xueli,");
		sql.append("p_c_xuewei,");
		sql.append("p_c_bank_nub,");
		sql.append("p_c_tijian_tab,");
		sql.append("p_c_health,");
		sql.append("p_c_img,");
		sql.append("p_c_welcome,");
		sql.append("p_c_staff,");
		sql.append("p_c_admin,");
		sql.append("p_c_shebao,");
		sql.append("p_c_shangbao,");
		sql.append("p_c_secrecy,");
		sql.append("p_c_prohibida,");
		sql.append("p_c_contract,");
		sql.append("p_c_post,");
		sql.append("p_c_corruption,");
		sql.append("p_c_probation,");
		sql.append("p_create_date,");
		sql.append("p_this_dept_code,");
		sql.append("p_id_num,");
		sql.append("p_birthday,");
		sql.append("p_use_work_form,");
		sql.append("p_contract_count,");
		sql.append("p_oa_and_ehr,");
		sql.append("p_huji,");
		sql.append("p_hukou_xingzhi,");
		sql.append("p_age_qujian,");
		sql.append("p_zuigao_xueli,");
		sql.append("p_recommend_person,");
		sql.append("p_recommend_relation");
		sql.append(" from ehr_person_basic_info where p_number=?");
		return jdbctemplate.queryForObject(sql.toString(),new BeanPropertyRowMapper<>(PersonBasrcEntity.class), p_number);
	}
	@Override
	public List<Map<String, Object>> loadComboboxCompanyData() throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		//type 1代表集团，2代表公司，3代表部门，4代表科室
		sql.append("select dept_code,dept_name from ehr_dept where type = '2'");
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> findPersonListByIds(String[] ids) throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append("select p_id as id,p_number as pNumber,p_name as pName ");
		sql.append(" from ehr_person_basic_info ");
		sql.append(" where p_id in (");
		for(int i = 0;i < ids.length ; i++){
			if(i == ids.length - 1){
				sql.append("?");
			} else {
				sql.append("?,");
			}
		}
		sql.append(")");
		return jdbctemplate.queryForList(sql.toString(), ids);
	}
	@Override
	public Integer getPersonCount(String date, String resourceCode) throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ehr_person_basic_info where p_OA_and_EHR = 'OA' and" );
		sql.append("(p_out_date >= '"+date +"' OR p_out_date is null OR p_out_date = '') ");
		sql.append(" and (p_company_id = '"+resourceCode+"' or p_organization_id = '" + resourceCode + "')");
		sql = super.jointDataAuthoritySql("p_company_id", "P_organization_id", sql);
		return jdbctemplate.queryForInt(sql.toString());
	}
	@Override
	public List<Map<String, Object>> findPagePersonInfo(int startIndex, int pageSize, String date,String resourceCode) throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.p_id as pId,a.p_number as pNumber,a.p_name as pName,b.dept_name as companyName,");
		sql.append("b.dept_code as companyCode,c.dept_name deptName,c.dept_code as deptCode,d.dept_name organizationName,");
		sql.append("d.dept_code as organizationCode,e.dept_name as officeName,e.dept_code as officeCode,f.POST_NAME as postName,");
		sql.append("a.p_in_date as inDate,a.p_nationality as nationality, P_property as property, ");
		sql.append("f.POSITION_ID as positionId from ehr_person_basic_info a ");
		sql.append(" LEFT JOIN ehr_dept b on a.p_company_id = b.dept_code ");
		sql.append(" LEFT JOIN ehr_dept c on a.p_department_id = c.dept_code ");
		sql.append(" LEFT JOIN ehr_dept d on a.P_organization_id = d.dept_code");
		sql.append(" LEFT JOIN ehr_dept e on a.P_section_office_id = e.dept_code");
		sql.append(" LEFT JOIN ehr_post f on a.P_post_id = f.POST_ID ");
		sql.append(" where p_OA_and_EHR = 'OA' and (p_out_date >= ?");
		sql.append(" OR p_out_date is null OR p_out_date = '')");
		sql.append(" and (a.p_company_id = '"+resourceCode+"' or a.P_organization_id = '" + resourceCode + "')");
		sql = super.jointDataAuthoritySql("b.dept_code", "d.dept_code ", sql);
		sql.append(" LIMIT ?,?");
		List<Map<String, Object>> personInfos = null;
		try {
			personInfos = jdbctemplate.queryForList(sql.toString(), date, startIndex, pageSize);
		} catch (Exception e) {
			log.error("查询员工信息失败",e);
		}
		return personInfos;
	}
	@Override
	public Object getPersonByPnumber(String pNumber) throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("p_id,");
		sql.append("p_number,");
		sql.append("p_name,");
		sql.append("p_sex,");
		sql.append("p_age,");
		sql.append("p_title,");
		sql.append("p_post_id,");
		sql.append("p_post,");
		sql.append("p_level_id,");
		sql.append("p_level_name,");
		sql.append("p_company_id,");
		sql.append("p_company_name,");
		sql.append("p_department_id,");
		sql.append("p_department,");
		sql.append("p_organization_id,");
		sql.append("p_organization,");
		sql.append("p_section_office_id,");
		sql.append("p_section_office,");
		sql.append("p_group_id,");
		sql.append("p_group,");
		sql.append("p_state,");
		sql.append("p_property,");
		sql.append("p_post_property,");
		sql.append("p_in_date,");
		sql.append("p_turn_date,");
		sql.append("p_out_date,");
		sql.append("p_out_describe,");
		sql.append("p_checking_in,");
		sql.append("p_contract_begin_date,");
		sql.append("p_contract_end_date,");
		sql.append("p_shebao_begin_month,");
		sql.append("p_shebao_end_month,");
		sql.append("p_gjj_begin_month,");
		sql.append("p_gjj_end_month,");
		sql.append("p_nationality,");
		sql.append("p_nation,");
		sql.append("p_marriage,");
		sql.append("p_politics,");
		sql.append("p_phone,");
		sql.append("p_bank_nub,");
		sql.append("p_bank_name,");
		sql.append("p_huji_add,");
		sql.append("p_changzhu_add,");
		sql.append("p_urgency_name,");
		sql.append("p_urgency_relation,");
		sql.append("p_urgency_phone,");
		sql.append("p_urgency_id_nub,");
		sql.append("p_urgency_add,");
//		sql.append("p_kinsfolk_y_n,");
//		sql.append("p_kinsfolk_relation,");
//		sql.append("p_kinsfolk_name,");
//		sql.append("p_kinsfolk_id_nub,");
		sql.append("p_company_age,");
		sql.append("p_c_yingpin_table,");
		sql.append("p_c_interview_tab,");
		sql.append("p_c_id_copies,");
		sql.append("p_c_xueli,");
		sql.append("p_c_xuewei,");
		sql.append("p_c_bank_nub,");
		sql.append("p_c_tijian_tab,");
		sql.append("p_c_health,");
		sql.append("p_c_img,");
		sql.append("p_c_welcome,");
		sql.append("p_c_staff,");
		sql.append("p_c_admin,");
		sql.append("p_c_shebao,");
		sql.append("p_c_shangbao,");
		sql.append("p_c_secrecy,");
		sql.append("p_c_prohibida,");
		sql.append("p_c_contract,");
		sql.append("p_c_post,");
		sql.append("p_c_corruption,");
		sql.append("p_c_probation,");
		sql.append("p_create_date,");
		sql.append("p_this_dept_code,");
		sql.append("p_id_num,");
		sql.append("p_birthday,");
		sql.append("p_use_work_form,");
		sql.append("p_contract_count,");
		sql.append("p_oa_and_ehr,");
		sql.append("p_huji,");
		sql.append("p_hukou_xingzhi,");
		sql.append("p_age_qujian,");
		sql.append("p_zuigao_xueli,");
		sql.append("p_recommend_person,");
		sql.append("p_recommend_relation");
		sql.append(" from ehr_person_basic_info where p_number=?");
		return jdbctemplate.queryForObject(sql.toString(),new BeanPropertyRowMapper<>(PersonBasrcEntity.class), pNumber);
	}
	@Override
	public List<Map<String, Object>> loadEHRWorkNumInFo(String companyId, String organizationId) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select max(p_number) as p_number from ehr_person_basic_info where 1=1 and p_oa_and_ehr = 'EHR'");
		if(companyId.equals("000000010012")&&!organizationId.equals("0000000100120003")){
			sql.append(" and p_company_id = '"+companyId+"' and p_organization_id <> '"+organizationId+"'");
		}else if(companyId.equals("000000010012")&&organizationId.equals("0000000100120003")){
			sql.append(" and p_company_id = '"+companyId+"' and p_organization_id = '"+organizationId+"'");
		}else{
			sql.append(" and p_company_id = '"+companyId+"'");
		}
		return jdbctemplate.queryForList(sql.toString());
		
	}

	@Override
	public List<Map<String, Object>> loadPersonlimit(String search_p_number, String search_p_name, Integer limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p_id,p_number,p_name,p_sex,p_age,p_title,p_post_id,p_post,p_level_id,");
		sql.append("p_level_name,p_company_id,p_company_name,p_department_id,p_department,p_organization_id,");
		sql.append("p_organization,p_section_office_id,p_section_office,p_group_id,p_group,p_state,p_property,");
		sql.append("p_post_property,p_in_date,p_turn_date,p_out_date,p_out_describe,p_checking_in,");
		sql.append("p_contract_begin_date,p_contract_end_date,p_shebao_begin_month,p_shebao_end_month,");
		sql.append("p_gjj_begin_month,p_gjj_end_month,p_nationality,p_nation,p_marriage,p_politics,p_phone,");
		sql.append("p_bank_nub,p_bank_name,p_huji_add,p_changzhu_add,p_urgency_name,p_urgency_relation,");
		sql.append("p_urgency_phone,p_urgency_id_nub,p_urgency_add,p_company_age,p_c_yingpin_table,p_c_interview_tab,");
		sql.append("p_c_id_copies,p_c_xueli,p_c_xuewei,p_c_bank_nub,p_c_tijian_tab,p_c_health,p_c_img,");
		sql.append("p_c_welcome,p_c_staff,p_c_admin,p_c_shebao,p_c_shangbao,p_c_secrecy,p_c_prohibida,");
		sql.append("p_c_contract,p_c_post,p_c_corruption,p_c_probation,p_create_date,p_this_dept_code,");
		sql.append("p_id_num,p_birthday,p_use_work_form,p_contract_count,p_oa_and_ehr,p_huji,b.dept_name as deptName,");
		sql.append("p_hukou_xingzhi,p_age_qujian,p_zuigao_xueli,p_recommend_person,p_recommend_relation");
		sql.append(" from ehr_person_basic_info a left join ehr_dept b on a.p_this_dept_code = b.dept_code where 1=1");
		if(search_p_number!=null&&!search_p_number.equals("")){
			sql.append(" and p_number like '%"+search_p_number+"%'");
		}
		if(search_p_name!=null&&!search_p_name.equals("")){
			sql.append(" and p_name like '%"+search_p_name+"%'");
		}
		StringBuffer returnsql = super.jointDataAuthoritySql("p_company_id","p_organization_id",sql);
		sql.append(" limit "+ limit);
		return jdbctemplate.queryForList(returnsql.toString());
	}
}
