package com.babifood.clocked.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.ClokedHolidayDao;
import com.babifood.clocked.entrty.Holiday;
import com.babifood.utils.CustomerContextHolder;
@Repository
public class ClockedHolidayDaoImpl implements ClokedHolidayDao {
	public static final Logger log = Logger.getLogger(ClockedHolidayDaoImpl.class);
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<Holiday> loadHolidayDatas(int year) {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		List<Holiday> holidayList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select year,begindate,enddate,days,remark from a8xholiday where year = ? ");
		try {
			List<Map<String,Object>> list = jdbctemplate.queryForList(sql.toString(),year);
			if(list.size()>0){
				holidayList = new ArrayList<Holiday>();
				for (Map<String, Object> map : list) {
					Holiday h = new Holiday();
					h.setYear(Integer.parseInt(map.get("year").toString()));
					h.setBeginDate(map.get("begindate").toString());
					h.setEndDate(map.get("enddate").toString());
					h.setDays(Double.parseDouble(map.get("days").toString()));
					h.setRemark(map.get("remark")==null?"":map.get("remark").toString());
					holidayList.add(h);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询错误："+e.getMessage());
		}
		return holidayList;
	}

}
