package com.babifood.service;

import java.util.List;
import java.util.Map;

import com.babifood.entity.ArrangementBaseTimeEntity;

public interface ArrangementBaseTimeService {

	public List<Map<String, Object>> findAllArrangementBaseTimes();

	public Map<String, Object> saveArrangementBaseTime(ArrangementBaseTimeEntity arrangement);

	public Map<String, Object> removeArrangementBaseTime(Integer id);
	
	public Integer getCount();

	public List<Map<String, Object>> findPageBaseTimes(int pageNum,int pageSize);
	
}
