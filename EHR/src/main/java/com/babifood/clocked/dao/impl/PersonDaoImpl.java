package com.babifood.clocked.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.PersonDao;
import com.babifood.clocked.entrty.Person;
import com.babifood.utils.UtilDateTime;
@Repository
public class PersonDaoImpl implements PersonDao {
	Logger log = LoggerFactory.getLogger(PersonDaoImpl.class);
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<Person> loadPeraonClockedInfo(int year,int month) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfWhere = new SimpleDateFormat("yyyy-MM-dd");
		List<Person> personList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("p_id,p_number as workNum,p_name as userName,p_company_id as companyCode,");
		sql.append("p_company_name as company,p_organization_id as organCode,p_organization as organ, ");
		sql.append("p_department_id as deptCode,p_department as dept,p_section_office_id as officeCode, ");
		sql.append("p_section_office as office,p_group_id as groupCode,p_group as groupName,p_post_id as postCode, ");
		sql.append("p_post as post,p_checking_in as daKaType,p_in_date as inDate,p_turn_date as turnDate, ");
		sql.append("p_out_date as outDate");
		sql.append(" from ehr_person_basic_info where p_in_date<=?");
		Object[] params=new Object[1];
		params[0]=sdfWhere.format(UtilDateTime.getMonthEndSqlDate(year,month));
		try {
			List<Map<String,Object>> list = jdbctemplate.queryForList(sql.toString(),params);
			if(list.size()>0){
				personList = new ArrayList<Person>();
				for (Map<String, Object> map : list) {
					if(!"YX".equals(map.get("workNum").toString().substring(0, 2))){
						if(map.get("outDate")!=null&&!map.get("outDate").equals("")){
							Date outDate = sdfWhere.parse(map.get("outDate").toString());
							Date sysStartDate = UtilDateTime.getMonthStartSqlDate(year,month);
							if(outDate.getTime()<sysStartDate.getTime()){
								continue;
							}
						}
						Person temp = new Person();
						temp.setP_id(map.get("p_id").toString());
						temp.setWorkNum(map.get("workNum").toString());
						temp.setUserName(map.get("userName").toString());
						temp.setCompanyCode(map.get("companyCode").toString());
						temp.setCompany(map.get("company").toString());
						temp.setOrganCode(map.get("organCode").toString());
						temp.setOrgan(map.get("organ").toString());
						temp.setDeptCode(map.get("deptCode").toString());
						temp.setDept(map.get("dept").toString());
						temp.setOfficeCode(map.get("officeCode")==null?"":map.get("officeCode").toString());
						temp.setOffice(map.get("office")==null?"":map.get("office").toString());
						temp.setGroupCode(map.get("groupCode")==null?"":map.get("groupCode").toString());
						temp.setGroupName(map.get("groupName")==null?"":map.get("groupName").toString());
						temp.setPostCode(map.get("postCode").toString());
						temp.setPost(map.get("post").toString());
						temp.setDaKaType(map.get("daKaType").toString());
						temp.setInDate(map.get("inDate")==null?"":map.get("inDate").toString());
						temp.setTurnDate(map.get("turnDate")==null?"":map.get("turnDate").toString());
						temp.setOutDate(map.get("outDate")==null?"":map.get("outDate").toString());
						personList.add(temp);
					}
					continue;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询错误："+e.getMessage());
		}
		return personList;
	}

}
