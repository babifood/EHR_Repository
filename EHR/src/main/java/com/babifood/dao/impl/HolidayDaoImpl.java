package com.babifood.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.HolidayDao;
import com.babifood.entity.HolidayEntity;
import com.babifood.utils.CustomerContextHolder;
import com.babifood.utils.UtilString;

@Repository
public class HolidayDaoImpl implements HolidayDao {

	private static Logger logger = Logger.getLogger(HolidayDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbctemplate;

	/**
	 * 新增节假日信息
	 */
	@Override
	public int addHoliday(HolidayEntity holidayEntity) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_holiday (holiday_name,start_date,end_date,remark) values(?,?,?,?)");
		int count = 0;
		try {
			count = jdbctemplate.update(sql.toString(), holidayEntity.getHolidayName(), holidayEntity.getStartDate(),
					holidayEntity.getEndDate(), holidayEntity.getRemark());
		} catch (Exception e) {
			logger.error("新增节假日失败",e);
		}
		return count;
	}

	/**
	 * 修改节假日信息
	 */
	@Override
	public int updateHoliday(HolidayEntity holidayEntity) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_holiday set holiday_name = ?,start_date = ?,end_date = ?,remark = ? where id = ?");
		int count = 0;
		try {
			count = jdbctemplate.update(sql.toString(), holidayEntity.getHolidayName(), holidayEntity.getStartDate(),
					holidayEntity.getEndDate(), holidayEntity.getRemark(), holidayEntity.getId());
		} catch (Exception e) {
			System.out.println();
		}
		return count;
	}

//	@Override
//	public Integer findHolidayNumByDate(String date) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("select count(*) from ehr_holiday where start_date <= ? and end_date >= ?");
//		return jdbctemplate.queryForInt(sql.toString(), date, date);
//	}

	/**
	 * 根据id 查询节假日信息
	 */
	@Override
	public Map<String, Object> findHolidayById(Integer id) {
		String sql = "select id as id ,holiday_name as holidayName ,start_date as startDate,end_date as endDate,remark as remark from ehr_holiday where id = ? ";
		return jdbctemplate.queryForMap(sql, id);
	}

	/**
	 * 删除节假日信息
	 */
	@Override
	public int deleteHolidayById(Integer id) {
		String sql = "delete from ehr_holiday where id = ? ";
		int count = 0;
		try {
			count = jdbctemplate.update(sql, id);
		} catch (Exception e) {

		}
		return count;
	}

	/**
	 * 查询对应年月的节假日
	 */
	@Override
	public List<Map<String, Object>> findHolidayListByDate(String startDay, String endDay) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select id as id ,holiday_name as holidayName ,start_date as startDate,end_date as endDate,remark as remark from ehr_holiday");
		sql.append(" where start_date <= ? and end_date >= ?");
		return jdbctemplate.queryForList(sql.toString(), endDay, startDay);
	}

	@Override
	public List<Map<String, Object>> findOAHolidays(String year) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		StringBuffer sql = new StringBuffer();
		sql.append("select id, typeName,year,begindate,enddate,days,remark from a8xholiday where year = ? ");
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString(),year);
		} catch (Exception e) {
			logger.error("同步OA节假日数据失败",e);
			throw e;
		}
		return list;
	}

	@Override
	public void saveBacthHolidays(List<Map<String, Object>> holidays) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		List<Object[]> params = new ArrayList<Object[]>();
		for(Map<String, Object> map : holidays){
			Object[] obj = new Object[] { map.get("id"), map.get("typeName"), getDate(map.get("begindate") + ""),
					getDate(map.get("enddate") + ""), map.get("remark") };
			params.add(obj);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("replace into ehr_holiday (oa_id,holiday_name,start_date,end_date,remark) values(?,?,?,?,?)");
		try {
			jdbctemplate.batchUpdate(sql.toString(),params);
		} catch (Exception e) {
			logger.error("保存OA节假日数据失败",e);
			throw e;
		}
		
	}

	
	public String getDate(String date) {
		if(!UtilString.isEmpty(date)){
			date = date.substring(0, 10);
		}
		return date;
	}
}
