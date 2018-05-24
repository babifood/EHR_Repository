package com.babifood.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.babifood.dao.HolidayDao;
import com.babifood.entity.HolidayEntity;
import com.babifood.service.HolidayService;
import com.babifood.utils.StringUtil;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayDao holidayDao;

	@Override
	public Map<String, Object> addOrUpdateHoliday(HolidayEntity holidayEntity) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(holidayEntity.getStartDate()) || StringUtil.isEmpty(holidayEntity.getEndDate())) {
			resultMap.put("code", "0");
			resultMap.put("msg", "节假日日期不能为空");
			return resultMap;
		}
		// 节假日日期不能为当月和之前时间
		String[] dates = holidayEntity.getStartDate().split("-");
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH) + 1;
		System.out.println(year + "-" + month);
		if (Integer.valueOf(dates[0]) < year
				|| (Integer.valueOf(dates[0]) <= year && Integer.valueOf(dates[1]) <= month)) {
			resultMap.put("code", "0");
			resultMap.put("msg", "节假日日期不能为当月及之前时间");
			return resultMap;
		}

		int count = 0;
		if (StringUtil.isEmpty(holidayEntity.getId()+"")) {
			count = holidayDao.addHoliday(holidayEntity);
		} else {
			count = holidayDao.updateHoliday(holidayEntity);
		}
		if (count > 0) {
			resultMap.put("code", "1");
			resultMap.put("msg", "节假日新增或修改成功");
		} else {
			resultMap.put("code", "0");
			resultMap.put("msg", "节假日新增或修改失败");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findHolidayEntity(String date) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(date)) {
			resultMap.put("code", "0");
			resultMap.put("msg", "节假日新增或修改失败");
			return resultMap;
		}
		int count = 0;
		try {
			count = holidayDao.findHolidayNumByDate(date);
			if (count > 0) {
				resultMap.put("code", "0");
				resultMap.put("msg", "当天已有节假日存在，不能新建");
			} else {
				resultMap.put("code", "1");
				resultMap.put("msg", "无假日存在，可创建");
			}
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "数据异常，请重试");
		}
		return resultMap;
	}

	
	@Override
	public Map<String, Object> deleteHolidayById(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		id = id == null ? 0 : id;
		Map<String, Object> holidayMap = null;
		try {
			holidayMap = holidayDao.findHolidayById(id);
			if(MapUtils.isEmpty(holidayMap)){
				resultMap.put("code", "0");
				resultMap.put("msg", "该节假日不存在");
				return resultMap;
			}
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "查询数据异常");
			return resultMap;
		}
		// 删除节假日日期不能为当月和之前时间
		String endDate = holidayMap.get("endDate") + "";
		String[] dates = endDate.split("-");
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH) + 1;
		System.out.println(year + "-" + month);
		if (Integer.valueOf(dates[0]) < year
				|| (Integer.valueOf(dates[0]) <= year && Integer.valueOf(dates[1]) <= month)) {
			resultMap.put("code", "0");
			resultMap.put("msg", "该节日不能被删除");
			return resultMap;
		}
		int count = holidayDao.deleteHolidayById(id);
		if (count > 0) {
			resultMap.put("code", "1");
			resultMap.put("msg", "删除节日成功");
		} else {
			resultMap.put("code", "0");
			resultMap.put("msg", "删除节日失败");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findHolidayListByDate(String year,String month) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DATE);
		String date = year + "-" + (month.length() == 1? "0"+month : month);
		List<Map<String,Object>> holidayList = null;
		try {
			holidayList = holidayDao.findHolidayListByDate(date);
			if(!CollectionUtils.isEmpty(holidayList)){
				for(Map<String, Object> map : holidayList){
					String end = (map.get("endDate")+"").split("-")[2];
					String start = (map.get("startDate")+"").split("-")[2];
					if(Integer.valueOf(end) <= 7 && Integer.valueOf(start) > Integer.valueOf(end)){
						start = "0";
					}
					if(Integer.valueOf(start) >= days -7 && Integer.valueOf(start) > Integer.valueOf(end)){
						end = days + "";
					}
					map.put("start", start);
					map.put("end", end);
				}
			}
			resultMap.put("holidays", holidayList);
			resultMap.put("code", "1");
			resultMap.put("msg", "获取节假日列表成功");
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "获取节假日列表失败");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findHolidayEntityById(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		id = id == null ? 0 : id;
		Map<String, Object> holidayMap = null;
		try {
			holidayMap = holidayDao.findHolidayById(id);
			if(MapUtils.isEmpty(holidayMap)){
				resultMap.put("code", "0");
				resultMap.put("msg", "该节假日不存在");
			} else {
				resultMap.put("code", "1");
				resultMap.put("msg", "查询成功");
				resultMap.put("holiday", holidayMap);
			}
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "查询数据异常");
		}
		return resultMap;
	}
	

}
