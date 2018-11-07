package com.babifood.clocked.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.WorkCalendarDao;
import com.babifood.clocked.entrty.BasicWorkCalendar;
import com.babifood.clocked.entrty.SpecialWorkCalendar;
import com.babifood.utils.CustomerContextHolder;
@Repository
public class WorkCalendarDaoImpl implements WorkCalendarDao {
	public static final Logger log = Logger.getLogger(WorkCalendarDaoImpl.class);
	@Autowired
	JdbcTemplate jdbctemplate;
	/**
	 * 基础排班
	 */
	@Override
	public List<BasicWorkCalendar> loadBasicWorkCalendarDatas(int year, int month) {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		List<BasicWorkCalendar> basicList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("t.target_id,t.target_type,b.arrangement_name,b.start_time,b.end_time,b.arrangement_type ");
		sql.append("from ");
		sql.append("ehr_arrangement_target t ");
		sql.append("inner join ehr_base_arrangement b on t.arrangement_id = b.id ");
		sql.append("where b.isDelete = '0' ");
		try {
			List<Map<String,Object>> list = jdbctemplate.queryForList(sql.toString());
			if(list.size()>0){
				basicList = new ArrayList<BasicWorkCalendar>();
				for (Map<String, Object> map : list) {
					BasicWorkCalendar basic = new BasicWorkCalendar();
					basic.setTarget_id(map.get("target_id").toString());
					basic.setTarget_type(map.get("target_type").toString());
					basic.setArrangement_name(map.get("arrangement_name").toString());
					basic.setStart_time(map.get("start_time").toString());
					basic.setEnd_time(map.get("end_time").toString());
					basic.setArrangement_type(map.get("arrangement_type").toString());
					basicList.add(basic);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询错误："+e.getMessage());
		}
		return basicList;
	}
	/**
	 * 特殊排班
	 */
	@Override
	public List<SpecialWorkCalendar> loadSpecialWorkCalendarDatas(int year, int month) {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		List<SpecialWorkCalendar> specialList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("t.target_id,t.target_type,s.DATE,s.start_time,s.end_time,b.arrangement_type,s.is_attend ");
		sql.append("from ");
		sql.append("ehr_arrangement_target t ");
		sql.append("inner join ehr_special_arrangement s on t.arrangement_id = s.arrangement_id ");
		sql.append("inner join ehr_base_arrangement b on t.arrangement_id= b.ID ");
		sql.append("where  date_format(s.date,'%Y-%m')=? and s.isDelete = '0'");
		String strMonth = "";
		if(month<10){
			strMonth = "0"+month;
		}else{
			strMonth = month+"";
		}
		String paarm = year+"-"+strMonth;
		try {
			List<Map<String,Object>> list = jdbctemplate.queryForList(sql.toString(),paarm);
			if(list.size()>0){
				specialList = new ArrayList<SpecialWorkCalendar>();
				for (Map<String, Object> map : list) {
					SpecialWorkCalendar special = new SpecialWorkCalendar();
					special.setTarget_id(map.get("target_id").toString());
					special.setDate(map.get("date").toString());
					special.setTarget_type(map.get("target_type").toString());
					special.setStart_time(map.get("start_time").toString());
					special.setEnd_time(map.get("end_time").toString());
					special.setArrangement_type(map.get("arrangement_type").toString());
					special.setIs_attend(map.get("is_attend").toString());
					specialList.add(special);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询错误："+e.getMessage());
		}
		return specialList;
	}
	
}
