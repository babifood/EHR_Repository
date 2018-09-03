package com.babifood.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.HolidayDao;
import com.babifood.entity.HolidayEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.service.HolidayService;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

@Service
public class HolidayServiceImpl implements HolidayService {
	
	private Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);

	@Autowired
	private HolidayDao holidayDao;

	/**
	 * 保存节假日信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.HOLIDAY)
	public Map<String, Object> addOrUpdateHoliday(HolidayEntity holidayEntity) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		if (UtilString.isEmpty(holidayEntity.getStartDate()) || UtilString.isEmpty(holidayEntity.getEndDate())) {
			resultMap.put("code", "0");
			resultMap.put("msg", "节假日日期不能为空");
			logger.error("保存节假日信息失败,错误信息：节假日日期不能为空");
			LogManager.putContectOfLogInfo("保存节假日信息失败,错误信息：节假日日期不能为空");
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
			logger.error("保存节假日信息失败,错误信息：节假日日期不能为当月及之前时间");
			LogManager.putContectOfLogInfo("保存节假日信息失败,错误信息：节假日日期不能为当月及之前时间");
			return resultMap;
		}
		try {
			if (UtilString.isEmpty(""+holidayEntity.getId())) {
				holidayDao.addHoliday(holidayEntity);
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
			} else {
				holidayDao.updateHoliday(holidayEntity);
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
			}
			resultMap.put("code", "1");
			resultMap.put("msg", "保存节假日成功");
			LogManager.putContectOfLogInfo("保存节假日信息");
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "保存节假日失败");
			logger.error("保存节假日信息失败", e);
			LogManager.putContectOfLogInfo("保存节假日信息失败,错误信息：" + e.getMessage());
		}
		return resultMap;
	}

//	@Override
//	@LogMethod(module = ModuleConstant.HOLIDAY)
//	public Map<String, Object> findHolidayEntity(String date) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		if (UtilString.isEmpty(date)) {
//			resultMap.put("code", "0");
//			resultMap.put("msg", "节假日新增或修改失败");
//			return resultMap;
//		}
//		int count = 0;
//		try {
//			count = holidayDao.findHolidayNumByDate(date);
//			if (count > 0) {
//				resultMap.put("code", "0");
//				resultMap.put("msg", "当天已有节假日存在，不能新建");
//			} else {
//				resultMap.put("code", "1");
//				resultMap.put("msg", "无假日存在，可创建");
//			}
//		} catch (Exception e) {
//			resultMap.put("code", "0");
//			resultMap.put("msg", "数据异常，请重试");
//		}
//		return resultMap;
//	}

	/**
	 * 删除节假日信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.HOLIDAY)
	public Map<String, Object> deleteHolidayById(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		id = id == null ? 0 : id;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
			Map<String, Object> holidayMap = holidayDao.findHolidayById(id);
			// 删除节假日日期不能为当月和之前时间
			String endDate = holidayMap.get("endDate") + "";
			String[] dates = endDate.split("-");
			Calendar calendar = Calendar.getInstance();
			Integer year = calendar.get(Calendar.YEAR);
			Integer month = calendar.get(Calendar.MONTH) + 1;
			if (Integer.valueOf(dates[0]) < year
					|| (Integer.valueOf(dates[0]) <= year && Integer.valueOf(dates[1]) <= month)) {
				resultMap.put("code", "0");
				resultMap.put("msg", "该节日不能被删除");
				logger.error("删除节假日信息失败，错误信息：该节日不能被删除");
				LogManager.putContectOfLogInfo("删除节假日信息失败,错误信息：该节日不能被删除");
			} else {
				holidayDao.deleteHolidayById(id);
				resultMap.put("code", "1");
				resultMap.put("msg", "删除节日成功");
				LogManager.putContectOfLogInfo("删除节假日信息");
			}
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "删除节日失败");
			logger.error("删除节假日信息失败", e);
			LogManager.putContectOfLogInfo("删除节假日信息失败,错误信息：" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 查询对应年月的节假日
	 */
	@Override
	@LogMethod(module = ModuleConstant.HOLIDAY)
	public Map<String, Object> findHolidayListByDate(String year,String month) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(month.length() == 1){
			month = "0"+month;
		}
		int days = UtilDateTime.getDaysOfCurrentMonth(Integer.valueOf(year), Integer.valueOf(month));
		String startDay = year + "-" + month + "-01";
		String endDay = year + "-" + month + "-" + days;
		List<Map<String,Object>> holidayList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			holidayList = holidayDao.findHolidayListByDate(startDay,endDay);
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
			LogManager.putContectOfLogInfo("查询对应年月的节假日");
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "获取节假日列表失败");
			logger.error("查询对应年月的节假日失败", e);
			LogManager.putContectOfLogInfo("查询对应年月的节假日失败,错误信息：" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 根据id查询节加日信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.HOLIDAY)
	public Map<String, Object> findHolidayEntityById(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		id = id == null ? 0 : id;
		Map<String, Object> holidayMap = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			holidayMap = holidayDao.findHolidayById(id);
			if(MapUtils.isEmpty(holidayMap)){
				resultMap.put("code", "0");
				resultMap.put("msg", "该节假日不存在");
				logger.error("根据id查询节加日信息失败，错误信息：该节假日不存在");
				LogManager.putContectOfLogInfo("根据id查询节加日信息失败,错误信息：该节假日不存在");
			} else {
				resultMap.put("code", "1");
				resultMap.put("msg", "查询成功");
				resultMap.put("holiday", holidayMap);
				LogManager.putContectOfLogInfo("根据id查询节加日信息");
			}
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "查询数据异常");
			logger.error("根据id查询节加日信息失败", e);
			LogManager.putContectOfLogInfo("根据id查询节加日信息失败,错误信息：" + e.getMessage());
		}
		return resultMap;
	}
	

}
