package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.HomePageDao;
@Repository
public class HomePageDaoImpl implements HomePageDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<Map<String,Object>> LoadTreeMenu(String id,String role_id) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select m.id,m.text,m.state,m.iconCls,m.url,m.nid,m.flag");
		sql.append(" from ehr_menu m inner join ehr_role_menu r");
		sql.append(" on m.id = r.menu_tbo_id");
		sql.append(" where m.nid = ?");
		sql.append(" and r.role_id in("+role_id+")");
		sql.append(" group by r.menu_tbo_id");
		return jdbctemplate.queryForList(sql.toString(),id);
	}
	@Override
	public List<Map<String, Object>> loadBirthday(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT p_number,p_name,p_company_name,p_organization,p_department,MONTH (p_birthday) AS p_month,DAY (p_birthday) AS p_day,p_birthday ");
		sql.append("FROM ehr_person_basic_info ");
		sql.append("where date_format(p_birthday,'%m%d') between date_format(?,'%m%d') and date_format(?,'%m%d') and p_state='0'");
		Object[] params=new Object[2];
		params[0]= beginDate;
		params[1]= endDate;
		return jdbctemplate.queryForList(sql.toString(),params);
	}
	@Override
	public List<Map<String, Object>> loadZhuanZheng(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT p_number,p_name,p_company_name,p_organization,p_department,p_in_date,p_turn_date ");
		sql.append("FROM ehr_person_basic_info ");
		sql.append("where p_turn_date between ? and ? and p_state='0'");
		Object[] params=new Object[2];
		params[0]= beginDate;
		params[1]= endDate;
		return jdbctemplate.queryForList(sql.toString(),params);
	}
	@Override
	public List<Map<String, Object>> loadCertificateExpire(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select c_p_number,c_p_name,c_organization,c_certificate_name,c_certificate_number,c_begin_date,c_end_date from ehr_certificate ");
		sql.append("where c_end_date between ? and ?");
		Object[] params=new Object[2];
		params[0]= beginDate;
		params[1]= endDate;
		return jdbctemplate.queryForList(sql.toString(),params);
	}
	@Override
	public List<Map<String, Object>> loadWorkInOutForms(String thisYear) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		for(int i=1;i<=12;i++){
			sql.append("SELECT '"+i+"月' AS work_month,");
			sql.append("(SELECT count(*) FROM ehr_work_in_out_view WHERE MONTH(work_in_date) = '"+i+"' AND work_in_out_status = '0') AS inWork,");
			sql.append("(SELECT count(*) FROM ehr_work_in_out_view WHERE MONTH(work_out_date) = '"+i+"' AND work_in_out_status = '1') AS outWork");
			sql.append(" FROM ehr_work_in_out_view");
			sql.append(" WHERE YEAR(work_in_date) = '"+thisYear+"' OR YEAR(work_out_date) = '"+thisYear+"' GROUP BY work_month");
			if(i<12){
				sql.append(" union all ");
			}
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadContractExpire(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT p_number,p_name,p_company_name,p_organization,p_department,p_contract_begin_date,p_contract_end_date,p_contract_count ");
		sql.append("FROM ehr_person_basic_info ");
		sql.append("where p_contract_end_date between ? and ? and p_state='0'");
		Object[] params=new Object[2];
		params[0]= beginDate;
		params[1]= endDate;
		return jdbctemplate.queryForList(sql.toString(),params);
	}

}
