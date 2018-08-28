package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.BaseArrangementEntity;
import com.babifood.entity.SpecialArrangementEntity;

public interface BaseArrangementDao {

	public List<Map<String, Object>> findAllBaseArrangement();

	public int updateBaseArrangement(BaseArrangementEntity arrangement);

	public int addBaseArrangement(BaseArrangementEntity arrangement);

	public int removeBaseArrangement(Integer id);

	public List<Map<String, Object>> findSpecialArrangementList(String date, String arrangementId);

	public int addSpecialArrangement(SpecialArrangementEntity arrangement);

	public int updateSpecialArrangement(SpecialArrangementEntity arrangement);

	public int updateSpecialArrangement(String id);

	public Map<String, Object> findSpecialArrangementById(String id);

	public List<Map<String, Object>> findArrangementByTargetId(List<String> targetIds);

	public int bindArrangement(String targetId, String type, String arrangementId);

	public void deleteSettedArrangement(String targetId);

	public List<Map<String, Object>> findCurrentMonthAllSpecialArrangement(String start ,String endTime);

	public List<Map<String, Object>> findSpecialArrangementOfMonth(String startDay, String endDay);

	public Map<String, Object> findBaseArrangementById(String arrangementId);

	public List<Map<String, Object>> findArrangementTargets(Integer id);

}
