package com.babifood.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.ArrangementBaseTimeDao;
import com.babifood.entity.ArrangementBaseTimeEntity;
import com.babifood.service.ArrangementBaseTimeService;

@Service
public class ArrangementBaseTimeServiceImpl implements ArrangementBaseTimeService {
	
	@Autowired
	private ArrangementBaseTimeDao arrangementDao;

	@Override
	public List<Map<String, Object>> findAllArrangementBaseTimes() {
		List<Map<String, Object>> arrangements = new ArrayList<Map<String, Object>>();
		try {
			arrangements = arrangementDao.findAllArrangementBaseTimes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrangements;
	}

	@Override
	public Map<String, Object> saveArrangementBaseTime(ArrangementBaseTimeEntity arrangement) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		try {
			if(arrangement.getId() != null && arrangement.getId() > 0 ){
				count = arrangementDao.updateArrangementBaseTime(arrangement);
			} else {
				count = arrangementDao.addArrangementBaseTime(arrangement);
			}
			if(count > 0){
				result.put("code", "1");
				result.put("msg", "保存基本作息时间成功");
			} else {
				result.put("code", "0");
				result.put("msg", "保存基本作息时间失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "保存基本作息时间失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> removeArrangementBaseTime(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(id == null){
			result.put("code", "0");
			result.put("msg", "删除基本作息时间失败");
			return result;
		}
		int count = 0;
		try {
			count = arrangementDao.removeArrangementBaseTime(id);
			if(count > 0){
				result.put("code", "1");
				result.put("msg", "删除基本作息时间成功");
			} else {
				result.put("code", "0");
				result.put("msg", "删除基本作息时间失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "删除基本作息时间失败");
		}
		return result;
	}

	@Override
	public Integer getCount() {
		return 3001;
	}

	@Override
	public List<Map<String, Object>> findPageBaseTimes(int pageNum, int pageSize) {
		return arrangementDao.findPageBaseTimes((pageNum-1)*pageSize,pageSize);
	}
}