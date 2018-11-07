package com.babifood.clocked.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.PersonDao;
import com.babifood.clocked.entrty.Person;
import com.babifood.dao.impl.AuthorityControlDaoImpl;
import com.babifood.utils.UtilDateTime;
@Repository
public class PersonDaoImpl extends AuthorityControlDaoImpl implements PersonDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<Map<String,Object>> loadPeraonClockedInfo(int year,int month) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfWhere = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("p_id,p_number as workNum,p_name as userName,p_company_id as companyCode,");
		sql.append("p_company_name as company,p_organization_id as organCode,p_organization as organ, ");
		sql.append("p_department_id as deptCode,p_department as dept,p_section_office_id as officeCode, ");
		sql.append("p_section_office as office,p_group_id as groupCode,p_group as groupName,p_post_id as postCode, ");
		sql.append("p_post as post,p_checking_in as daKaType,p_in_date as inDate,p_turn_date as turnDate, ");
		sql.append("p_out_date as outDate");
		sql.append(" from ehr_person_basic_info where p_in_date<=? and p_oa_and_ehr = 'OA'");
		StringBuffer returnSQL = super.jointDataAuthoritySql("p_company_id","p_organization_id",sql);
		Object[] params=new Object[1];
		params[0]=sdfWhere.format(UtilDateTime.getMonthEndSqlDate(year,month));
		return jdbctemplate.queryForList(returnSQL.toString(),params);
	}

}
