package com.babifood.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.PunchTimeDao;
import com.babifood.entity.PunchTimeEntity;
import com.babifood.service.PunchTimeService;
import com.babifood.service.scheduling.DakaRecordService;
import com.babifood.service.scheduling.DakaSourceService;

@Service
public class PunchTimeServiceImpl implements PunchTimeService {
	
	@Autowired
	private DakaSourceService dakaSourceService;
	
	@Autowired
	private DakaRecordService dakaRecordService;
	
	@Autowired
	private PunchTimeDao punchTimeDao;

	public Map<String, Object> findPagePunchTimeInfo(Integer page, Integer rows, String pNumber, String pName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = Integer.valueOf(page == null ? 1 : page.intValue());
		Integer pageSize = Integer.valueOf(rows == null ? 10 : rows.intValue());
		params.put("start", Integer.valueOf((pageNum.intValue() - 1) * pageSize.intValue()));
		params.put("pageSize", pageSize);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		try {
			Integer count = punchTimeDao.getPagePunchTimeInfoCount(params);
			List<Map<String, Object>> punchTimeList = punchTimeDao.findPunchTimeInfo(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("rows", punchTimeList);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "分页查询打卡记录失败");
		}
		return result;
	}

	public Map<String, Object> savePagePunchTimeInfo(PunchTimeEntity punchTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			punchTimeDao.savePagePunchTimeInfo(punchTime);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存打卡记录失败");
		}
		return result;
	}

	public Map<String, Object> removePagePunchTimeInfo(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			punchTimeDao.removePagePunchTimeInfo(id);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除打卡记录失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> syncPunchTimeInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dakaSourceService.getDakaSource();
			dakaRecordService.checkDakaRecord();
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("code", "同步打卡记录失败");
			e.printStackTrace();
		}
		return result;
	}
}
