package com.babifood.service;

import java.util.Map;

import com.babifood.entity.HolidayEntity;

public interface HolidayService {

	public Map<String, Object> addOrUpdateHoliday(HolidayEntity holidayEntity);
	
//	public Map<String, Object> findHolidayEntity(String date);

	public Map<String, Object> deleteHolidayById(Integer id);

	public Map<String, Object> findHolidayListByDate(String year,String month);

	public Map<String, Object> findHolidayEntityById(Integer id);
	
}
