package com.babifood.clocked.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.OfficeDaKaDao;
import com.babifood.clocked.entrty.OfficeDaKaRecord;
@Repository
public class OfficeDaKaDaoImpl implements OfficeDaKaDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<OfficeDaKaRecord> loadOfficeDaKaData(int year, int month) throws Exception{
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = String.valueOf(year);
		if (month < 10) {
			s = s + "-0" + month;
		} else {
			s = s + "-" + month;
		}
		List<OfficeDaKaRecord> officeList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("r.WorkNum,r.UserName,");
		sql.append("b.p_company_id,b.p_company_name,");
		sql.append("b.p_organization_id,b.p_organization,");
		sql.append("b.p_department_id,b.p_department,");
		sql.append("b.p_section_office_id,b.p_section_office,");
		sql.append("b.p_group_id,b.p_group,");
		sql.append("r.ClockedDate,r.BeginTime,r.EndTime ");
		sql.append("from ehr_daka_record r ");
		sql.append("inner join ehr_person_basic_info b ");
		sql.append("on r.WorkNum = b.p_number ");
		sql.append("where DATE_FORMAT(r.ClockedDate,'%Y-%m') =?");
		List<Map<String,Object>> list = jdbctemplate.queryForList(sql.toString(),s);
		if(list.size()>0){
			officeList = new ArrayList<OfficeDaKaRecord>();
			for (Map<String, Object> map : list) {
				OfficeDaKaRecord f = new OfficeDaKaRecord();
				f.setWorkNum(map.get("WorkNum")==null?"":map.get("WorkNum").toString());
				f.setUserName(map.get("UserName")==null?"":map.get("UserName").toString());
				f.setCompanyCode(map.get("p_company_id")==null?"":map.get("p_company_id").toString());
				f.setCompany(map.get("p_company_name")==null?"":map.get("p_company_name").toString());
				f.setOrganCode(map.get("p_organization_id")==null?"":map.get("p_organization_id").toString());
				f.setOrgan(map.get("p_organization")==null?"":map.get("p_organization").toString());
				f.setDeptCode(map.get("p_department_id")==null?"":map.get("p_department_id").toString());
				f.setDept(map.get("p_department")==null?"":map.get("p_department").toString());
				f.setOfficeCode(map.get("p_section_office_id")==null?"":map.get("p_section_office_id").toString());
				f.setOffice(map.get("p_section_office")==null?"":map.get("p_section_office").toString());
				f.setOrganCode(map.get("p_group_id")==null?"":map.get("p_group_id").toString());
				f.setOrgan(map.get("p_group")==null?"":map.get("p_group").toString());
				f.setClockedDate(map.get("ClockedDate")==null?null:df.parse(map.get("ClockedDate").toString()));
				f.setBeginTime(map.get("BeginTime")==null?"":map.get("BeginTime").toString());
				f.setEndTime(map.get("EndTime")==null?"":map.get("EndTime").toString());
				officeList.add(f);
			}
		}
		return officeList;
	}

}
