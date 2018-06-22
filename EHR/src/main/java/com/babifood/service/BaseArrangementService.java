package com.babifood.service;

import java.util.List;
import java.util.Map;

import com.babifood.entity.BaseArrangementEntity;
import com.babifood.entity.SpecialArrangementEntity;

public interface BaseArrangementService {

	public List<Map<String, Object>> findBaseArrangements();

	public Map<String, Object> saveBaseArrangement(BaseArrangementEntity arrangement);

	public Map<String, Object> removeBaseArrangement(Integer id);

	public Map<String, Object> findSpecialArrangementList(String date, String arrangementId);

	public Map<String, Object> saveSpecialArrangement(SpecialArrangementEntity arrangement);

	public Map<String, Object> removeSpecialArrangement(String id);

	public Map<String, Object> findSpecialArrangementById(String id);

	public Map<String, Object> findSpecialArrangementList(String date, String deptCode, String pNumber);

	public Map<String, Object> findSpecialArrangementId(String deptCode, String pNumber);

	public Map<String, Object> bindArrangement(String targetId, String type, String arrangementId) throws Exception;

	public List<Map<String, Object>> findCurrentMonthAllSpecialArrangement(String start ,String endTime);

}
