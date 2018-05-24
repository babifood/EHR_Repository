package com.babifood.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.HolidayEntity;
import com.babifood.service.HolidayService;

@Controller
@RequestMapping("holiday")
public class HolidayController {

	@Autowired
	private HolidayService holidayService;
	
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public Map<String, Object> addOrUpdateHoliday(HolidayEntity holiday) {
		return holidayService.addOrUpdateHoliday(holiday);
	}
	
	@RequestMapping("findById")
	@ResponseBody
	public Map<String, Object> findHolidayEntityById(Integer id) {
		return holidayService.findHolidayEntityById(id);
	}
	
	@RequestMapping("findByDate")
	@ResponseBody
	public Map<String, Object> findHolidayEntity(String date) {
		return holidayService.findHolidayEntity(date);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> deleteHolidayById(Integer id) {
		return holidayService.deleteHolidayById(id);
	}
	
	@RequestMapping("findList")
	@ResponseBody
	public Map<String, Object> findHolidayList(String year,String month){
		return holidayService.findHolidayListByDate(year,month);
	}
	
}
