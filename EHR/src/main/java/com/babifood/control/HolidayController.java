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
	
	/**
	 * 保存节假日
	 * @param holiday
	 * @return
	 */
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public Map<String, Object> addOrUpdateHoliday(HolidayEntity holiday) {
		return holidayService.addOrUpdateHoliday(holiday);
	}
	
	/**
	 * 根据id查询节假日
	 * @param id
	 * @return
	 */
	@RequestMapping("findById")
	@ResponseBody
	public Map<String, Object> findHolidayEntityById(Integer id) {
		return holidayService.findHolidayEntityById(id);
	}
	
//	/**
//	 * 根据时间查询节假日
//	 * @param date
//	 * @return
//	 */
//	@RequestMapping("findByDate")
//	@ResponseBody
//	public Map<String, Object> findHolidayEntity(String date) {
//		return holidayService.findHolidayEntity(date);
//	}
	
	/**
	 * 删除节假日
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> deleteHolidayById(Integer id) {
		return holidayService.deleteHolidayById(id);
	}
	
	/**
	 * 查询对应年月的节假日
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping("findList")
	@ResponseBody
	public Map<String, Object> findHolidayList(String year,String month){
		return holidayService.findHolidayListByDate(year,month);
	}
	
}
