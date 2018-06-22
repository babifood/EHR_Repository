package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.ArrangementEntity;

public interface ArrangementDao {

	public int queryArrangementsCount();

	public List<Map<String, Object>> findListArrangements(Map<String, Object> map);

	public int removeArrangement(Integer id);

	public int saveArrangement(ArrangementEntity arrangement);

	public int[] saveArrangementList(List<Object[]> arrangementList);

}
