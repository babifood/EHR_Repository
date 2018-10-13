package com.babifood.clocked.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.PersonDao;
import com.babifood.clocked.entrty.Person;
import com.babifood.clocked.service.PeraonClockedService;
import com.babifood.utils.UtilDateTime;
@Service
public class PeraonClockedServiceImpl implements PeraonClockedService {
	@Autowired
	PersonDao personDao;
	@Override
	public List<Person> filtrateClockedPeraonData(int yaer, int month) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfWhere = new SimpleDateFormat("yyyy-MM-dd");
		List<Person> personList = null;
		List<Map<String,Object>> list = personDao.loadPeraonClockedInfo(yaer, month);
		if(list.size()>0){
			personList = new ArrayList<Person>();
			for (Map<String, Object> map : list) {
				if(map.get("outDate")!=null&&!map.get("outDate").equals("")){
					Date outDate = sdfWhere.parse(map.get("outDate").toString());
					Date sysStartDate = UtilDateTime.getMonthStartSqlDate(yaer,month);
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
		}
		return personList;
	}

}
