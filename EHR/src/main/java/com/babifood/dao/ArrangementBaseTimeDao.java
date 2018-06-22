package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.ArrangementBaseTimeEntity;

public interface ArrangementBaseTimeDao {

	public List<Map<String, Object>> findAllArrangementBaseTimes();

	public int addArrangementBaseTime(ArrangementBaseTimeEntity arrangement);
	
	public Map<String, Object> findArrangementBaseTimeById(Integer id);

	public int updateArrangementBaseTime(ArrangementBaseTimeEntity arrangement);

	public int removeArrangementBaseTime(Integer id);

	public List<Map<String, Object>> findPageBaseTimes(int start, int pageSize);

}
