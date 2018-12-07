package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.WorkshopClockedDao;
@Repository
public class WorkshopClockedDaoImpl extends AuthorityControlDaoImpl implements WorkshopClockedDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<Map<String, Object>> loadWorkshopClocked(String workNumber, String userName,String comp,String organ,String dept) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("w.worknum,w.workname,w.standardWorkDateLength,w.practicalWorkDateLength,w.workshop_year,w.workshop_month,");
		sql.append("p.p_company_name,p.p_organization,p.p_department,p.p_section_office,p.p_group,p.p_post");
		sql.append(" from ehr_workshop_clocked w INNER JOIN ehr_person_basic_info p on w.worknum = p.p_number where 1=1");
		if(!"".equals(workNumber)){
			sql.append(" and w.workname like '%"+workNumber+"%'");
		}
		if(!"".equals(userName)){
			sql.append(" and w.worknum like '%"+userName+"%'");
		}
		if(!"".equals(comp)){
			sql.append(" and p.p_company_name like '%"+comp+"%'");
		}
		if(!"".equals(organ)){
			sql.append(" and p.p_organization like '%"+organ+"%'");
		}
		if(!"".equals(dept)){
			sql.append(" and p.p_department like '%"+dept+"%'");
		}
		StringBuffer returnSQL = super.jointDataAuthoritySql("p.p_company_id","p.p_organization_id",sql);
		return jdbctemplate.queryForList(returnSQL.toString());
	}
	@Override
	public void saveimportExcelWorkshopClocked(List<Object[]> workshopClockedParam) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_workshop_clocked (workshop_year,workshop_month,worknum,workname,standardWorkDateLength,practicalWorkDateLength) ");
		sql.append(" values(?,?,?,?,?,?)");
		jdbctemplate.batchUpdate(sql.toString(),workshopClockedParam);
	}

}
