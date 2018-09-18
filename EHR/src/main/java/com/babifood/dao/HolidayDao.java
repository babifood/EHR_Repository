package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.HolidayEntity;

public interface HolidayDao {

	public int addHoliday(HolidayEntity holidayEntity);
	
	public int updateHoliday(HolidayEntity holidayEntity);

//	public Integer findHolidayNumByDate(String date);

	public Map<String, Object> findHolidayById(Integer id);

	public int deleteHolidayById(Integer id);

	public List<Map<String, Object>> findHolidayListByDate(String startDay, String endDay);

	public List<Map<String, Object>> findOAHolidays(String year);

	public void saveBacthHolidays(List<Map<String, Object>> holidays);
	
}
