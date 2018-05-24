package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.HolidayDao;
import com.babifood.entity.HolidayEntity;

@Repository
public class HolidayDaoImpl implements HolidayDao {

	@Autowired
	private JdbcTemplate jdbctemplate;

	@Override
	public int addHoliday(HolidayEntity holidayEntity) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_holiday (holiday_name,start_date,end_date,remark) values(?,?,?,?)");
		int count = 0;
		try {
			count = jdbctemplate.update(sql.toString(), holidayEntity.getHolidayName(), holidayEntity.getStartDate(),
					holidayEntity.getEndDate(), holidayEntity.getRemark());
		} catch (Exception e) {
			System.out.println();
		}
		return count;
	}

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

	@Override
	public Integer findHolidayNumByDate(String date) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ehr_holiday where start_date <= ? and end_date >= ?");
		return jdbctemplate.queryForInt(sql.toString(), date, date);
	}

	@Override
	public Map<String, Object> findHolidayById(Integer id) {
		String sql = "select id as id ,holiday_name as holidayName ,start_date as startDate,end_date as endDate,remark as remark from ehr_holiday where id = ? ";
		return jdbctemplate.queryForMap(sql, id);
	}

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

	@Override
	public List<Map<String, Object>> findHolidayListByDate(String date) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select id as id ,holiday_name as holidayName ,start_date as startDate,end_date as endDate,remark as remark from ehr_holiday");
		sql.append(" where start_date like ? or end_date like ?");
		return jdbctemplate.queryForList(sql.toString(), date + "%", date + "%");
	}

}
